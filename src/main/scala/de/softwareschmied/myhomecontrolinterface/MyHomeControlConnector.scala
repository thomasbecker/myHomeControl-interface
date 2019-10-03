package de.softwareschmied.myhomecontrolinterface

import ch.bootup.{SOAP, SOAPSoap}
import com.typesafe.scalalogging.Logger

import scala.util.Try


/**
 * Created by Thomas Becker (thomas.becker00@gmail.com) on 11.11.17.
 */
class MyHomeControlConnector {
  val logger = Logger[MyHomeControlConnector]

  def mHCSoapService: SOAPSoap = new SOAP().getSOAPSoap12

  def getEnergyMeterCurrentValue(guid: String): BigDecimal =
    logResult(mHCSoapService.energyMeterGetCurrentValuekW, guid, "current consumption")

  def getEnergyMeterCumulativeValue(guid: String): BigDecimal = {
    val consumption = mHCSoapService.energyMeterGetCumulativeValuekWh(guid, 0)
    logger.debug(s"guid: $guid, consumption: $consumption")
    consumption
  }

  def getTemperatureCurrentValue(guid: String): BigDecimal = logResult(mHCSoapService.roomTemperatureControlGetTemperatureActualValue, guid, "temperature")

  def getHumidityCurrentValue(guid: String): BigDecimal = {
    logResult(mHCSoapService.roomTemperatureControlGetActualHumitiyValue,guid, "humidtiy")
  }

  def getCo2CurrentValue(guid: String): BigDecimal = logResult(mHCSoapService.roomTemperatureControlGetActualCO2Value, guid, "co2")

  def getProjectStructure() = {
    mHCSoapService.getProjectStructure
  }

  def logResult(f: String => BigDecimal, guid: String, identifier: String): BigDecimal = {
    val result = f(guid)
    logger.info(s"$guid, $identifier: $result")
    result
  }

}
