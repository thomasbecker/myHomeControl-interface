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
    "collect should return a filled MyHomeControlData case class" in {
      val data = myHomeControlCollector.collectMyHomeControlData
      logger.info(s"$data")
      data.heatpumpPowerConsumption must not be(BigDecimal(0))
      data.sleepingRoomCo2 must not be(BigDecimal(0))
      data.livingRoomTemp must not be(BigDecimal(0))
    }

  }
}
