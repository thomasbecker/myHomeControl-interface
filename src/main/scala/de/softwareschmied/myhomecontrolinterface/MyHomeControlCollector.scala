package de.softwareschmied.myhomecontrolinterface

import java.time.Instant

import com.typesafe.scalalogging.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by Thomas Becker (thomas.becker00@gmail.com) on 17.11.17.
  */
case class MyHomeControlPowerData(heatpumpCurrentPowerConsumption: Double,
                                  heatpumpCumulativePowerConsumption: Double,
                                  timestamp: Long = Instant.now.getEpochSecond)

case class MyHomeControlEnvironmentData(
                                         officeTemp: Double,
                                         sleepingRoomCo2: Double,
                                         sleepingRoomTemp: Double,
                                         sleepingRoomHumidity: Double,
                                         livingRoomCo2: Double,
                                         livingRoomTemp: Double,
                                         livingRoomHumidity: Double,
                                         timestamp: Long = Instant.now.getEpochSecond)

class MyHomeControlCollector {
  val logger = Logger[MyHomeControlCollector]

  def myHomeControlConnector = new MyHomeControlConnector

  private val timeout = 180 seconds

  private val heatpumpEnergyMeterId = "f0c09c4a-b545-4ca0-99bd-830ebfc46145"
  private val sleepingRoomCo2SensorId = "82c64b73-0746-4dbd-9e06-34c280eb0db6"
  private val livingRoomCo2SensorId = "7db05d49-11f4-446c-82a9-c5c223f4068e"

  def collectMyHomeControlPowerData(): MyHomeControlPowerData = {
    val start = System.currentTimeMillis()
    val heatpumpPowerConsumptionFuture = result(myHomeControlConnector.getEnergyMeterCurrentValue, heatpumpEnergyMeterId)
    val heatpumpPowerConsumption = Await.result(heatpumpPowerConsumptionFuture, timeout)
    val heatpumpCumulativePowerConsumptionFuture = result(myHomeControlConnector.getEnergyMeterCumulativeValue, heatpumpEnergyMeterId)

    val heatpumpCumulativePowerConsumption = Await.result(heatpumpCumulativePowerConsumptionFuture, timeout)
    val end = System.currentTimeMillis()
    val myHomeControlPowerData = MyHomeControlPowerData(heatpumpPowerConsumption, heatpumpCumulativePowerConsumption)
    println(s"myHomeControl power data result in ${end - start} millis: $myHomeControlPowerData")
    myHomeControlPowerData
  }

  def collectMyHomeControlEnvironmentData(): MyHomeControlEnvironmentData = {
    val start = System.currentTimeMillis()
    // we need to block every call as more calls in parallel seem to break myHomeControl, need to find the right pattern here...
    val sleepingRoomCo2Future = result(myHomeControlConnector.getCo2CurrentValue, sleepingRoomCo2SensorId)
    val sleepingRoomCo2 = Await.result(sleepingRoomCo2Future, timeout)

    val livingRoomCo2Future = result(myHomeControlConnector.getCo2CurrentValue, livingRoomCo2SensorId)
    val livingRoomCo2 = Await.result(livingRoomCo2Future, timeout)

    val sleepingRoomTempFuture = result(myHomeControlConnector.getTemperatureCurrentValue, sleepingRoomCo2SensorId)
    val sleepingRoomTemp = Await.result(sleepingRoomTempFuture, timeout)

    val livingRoomTempFuture = result(myHomeControlConnector.getTemperatureCurrentValue, livingRoomCo2SensorId)
    val livingRoomTemp = Await.result(livingRoomTempFuture, timeout)

    val livingRoomHumidityFuture = result(myHomeControlConnector.getHumidityCurrentValue, livingRoomCo2SensorId)
    val livingRoomHumidity = Await.result(livingRoomHumidityFuture, timeout)

    val sleepingRoomHumidityFuture = result(myHomeControlConnector.getHumidityCurrentValue, sleepingRoomCo2SensorId)
    val sleepingRoomHumidity = Await.result(sleepingRoomHumidityFuture, timeout)

    // doesn't work, maybe sensor not supported or wrong api method?!
    //    val cellarTempFuture = result(myHomeControlConnector.getTemperatureCurrentValue, "09156ab5-e771-44fe-b03c-2c5ceea70ee0")
    //    val cellarTemp = Await.result(cellarTempFuture, timeout)

    val officeTempFuture = result(myHomeControlConnector.getTemperatureCurrentValue, "e93aae51-9e87-495a-bd20-9b141413ba48")
    val officeTemp = Await.result(officeTempFuture, timeout)

    // TODO: replace with some non blocking way
    val end = System.currentTimeMillis()
    val myHomeControlEnvironmentData = MyHomeControlEnvironmentData(officeTemp, sleepingRoomCo2, sleepingRoomTemp, sleepingRoomHumidity, livingRoomCo2,
      livingRoomTemp, livingRoomHumidity)
    println(s"myHomeControl power data result in ${end - start} millis: $myHomeControlEnvironmentData")
    myHomeControlEnvironmentData
  }

  def result(f: String => BigDecimal, id: String): Future[Double] = {
    Future {
      logger.debug(s"calling function")
      val result = f(id)
      logger.debug(s"result: $result")
      result.doubleValue()
    }
  }

}
