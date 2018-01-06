package de.softwareschmied.myhomecontrolinterface

import java.time.Instant

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by Thomas Becker (thomas.becker00@gmail.com) on 17.11.17.
  */
case class MyHomeControlData(heatpumpPowerConsumption: Double, livingRoomTemp: Double, sleepingRoomCo2: Double, timestamp: Long = Instant.now.getEpochSecond)

class MyHomeControlCollector {
  def myHomeControlConnector = new MyHomeControlConnector

  def collectMyHomeControlData(): MyHomeControlData = {
    val start = System.currentTimeMillis()
    val sleepingRoomCo2Future: Future[Double] = Future {
      myHomeControlConnector.getCo2CurrentValue("82c64b73-0746-4dbd-9e06-34c280eb0db6")
    }
    val livingRoomTempFuture: Future[Double] = Future {
      myHomeControlConnector.getTemperatureCurrentValue("e93aae51-9e87-495a-bd20-9b141413ba48")
    }
    val heatpumpPowerConsumptionFuture: Future[Double] = Future {
      myHomeControlConnector.getEnergyMeterCurrentValue("f0c09c4a-b545-4ca0-99bd-830ebfc46145")
    }

    // TODO: replace with some non blocking way
    val sleepingRoomCo2 = Await.result(sleepingRoomCo2Future, 30 seconds)
    val heatpumpPowerConsumption = Await.result(heatpumpPowerConsumptionFuture, 30 seconds)
    val livingRoomTemp = Await.result(livingRoomTempFuture, 30 seconds)
    val end = System.currentTimeMillis()
    val myHomeControlData = MyHomeControlData(heatpumpPowerConsumption, livingRoomTemp, sleepingRoomCo2)
    println(s"myHomeControl Result in ${end - start} millis: $myHomeControlData")
    myHomeControlData
  }
}
