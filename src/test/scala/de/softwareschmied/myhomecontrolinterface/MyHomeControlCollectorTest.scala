package de.softwareschmied.myhomecontrolinterface

import com.typesafe.scalalogging.Logger
import org.specs2.mutable.Specification

/**
  * Created by Thomas Becker (thomas.becker00@gmail.com) on 17.11.17.
  */
class MyHomeControlCollectorTest extends Specification {
  val logger = Logger[MyHomeControlCollectorTest]

  def myHomeControlCollector = new MyHomeControlCollector

  "MyHomeControlCollectorTest" should {
    "collect should return a filled MyHomeControlPowerData case class" in {
      val data = myHomeControlCollector.collectMyHomeControlPowerData
      logger.info(s"$data")
      data.heatpumpCurrentPowerConsumption !== 0
      data.heatpumpCumulativePowerConsumption !== 0
    }

    "return environment data when collectMyHomeControlEnvironmentData" in {
      val data = myHomeControlCollector.collectMyHomeControlEnvironmentData
      logger.info(s"$data")
      data.livingRoomCo2 > 0
      data.livingRoomHumidity > 0
      data.livingRoomTemp > 0
      data.officeTemp > 0
      data.sleepingRoomCo2 > 0
      data.sleepingRoomHumidity > 0
      data.sleepingRoomTemp > 0
    }
  }

}
