package eu.mapidev.pi.htclient.service;

public interface HumTempJsonParser {

    HumTempJsonParserImpl addHumidity(int hum);

    HumTempJsonParserImpl addTemperature(int temp);

    String parse();

}
