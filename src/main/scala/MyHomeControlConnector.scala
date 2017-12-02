import ch.bootup.SOAP
import com.typesafe.scalalogging.Logger


/**
  * Created by Thomas Becker (thomas.becker00@gmail.com) on 11.11.17.
  */
@Singleton
class MyHomeControlConnector {
  val logger = Logger[MyHomeControlConnector]
  def mHCSoapService = new SOAP().getSOAPSoap12

  def getEnergyMeterCurrentValue(guid: String): BigDecimal = {
    val consumption = mHCSoapService.energyMeterGetCurrentValuekW(guid)
    logger.debug(s"guid: $guid, consumption: $consumption ")
    consumption
  }

  def getTemperatureCurrentValue(guid: String): BigDecimal = {
    val temperature = mHCSoapService.roomTemperatureControlGetTemperatureActualValue(guid)
    logger.debug(s"guid: $guid, temperature: $temperature")
    temperature
  }

  def getHumidityCurrentValue(guid: String): BigDecimal = {
    // doesn't work (yet)
    val humidity = mHCSoapService.roomTemperatureControlGetActualHumitiyValue(guid)
    logger.debug(s"guid: $guid, humidity: $humidity")
    humidity
  }

  def getCo2CurrentValue(guid: String): BigDecimal = {
    val co2 = mHCSoapService.roomTemperatureControlGetActualCO2Value(guid)
    logger.debug(s"guid: $guid, co2: $co2")
    co2
  }
}
