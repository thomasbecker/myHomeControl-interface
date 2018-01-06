package de.softwareschmied.myhomecontrolinterface

import com.typesafe.scalalogging.Logger
import org.specs2.mutable.Specification

/**
  * Created by Thomas Becker (thomas.becker00@gmail.com) on 11.11.17.
  */
class MyHomeControlConnectorTest extends Specification {
  val logger = Logger[MyHomeControlConnectorTest]

  def myHomeControlConnector = new MyHomeControlConnector()

  "MyHomeControlConnectorTest" should {
    "getEnergyMeterCurrentValue returns valid value" in {
      val consumption = myHomeControlConnector.getEnergyMeterCurrentValue("f0c09c4a-b545-4ca0-99bd-830ebfc46145")
      consumption must not be (BigDecimal(0))
    }

    "getTemperature returns valid value" in {
      val temperature = myHomeControlConnector.getTemperatureCurrentValue("e93aae51-9e87-495a-bd20-9b141413ba48")
      logger.info(s"temp: $temperature")
      temperature must not be (BigDecimal(0))
    }

    "getHumidity returns valid value" in {
      val humidity = myHomeControlConnector.getHumidityCurrentValue("09156ab5-e771-44fe-b03c-2c5ceea70ee0")
      logger.info(s"temp: $humidity")
      humidity must not be (BigDecimal(0))
    }

    "getCo2 returns valid value" in {
      val co2 = myHomeControlConnector.getCo2CurrentValue("82c64b73-0746-4dbd-9e06-34c280eb0db6")
      logger.info(s"co2: $co2")
      co2 must not be (BigDecimal(0))
    }

  }
}
