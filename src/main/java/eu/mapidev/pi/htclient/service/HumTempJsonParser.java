package eu.mapidev.pi.htclient.service;

import eu.mapidev.pi.htclient.util.ClientPropertiesUtil;
import java.util.Collections;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;

public class HumTempJsonParser {

    private final JsonBuilderFactory factory = Json.createBuilderFactory(Collections.emptyMap());
    private final JsonObjectBuilder builder = factory.createObjectBuilder();

    public HumTempJsonParser addTemperature(int temp) {
	builder.add(ClientPropertiesUtil.getProperty("htclient.temp.label"), temp);
	return this;
    }

    public HumTempJsonParser addHumidity(int hum) {
	builder.add(ClientPropertiesUtil.getProperty("htclient.hum.label"), hum);
	return this;
    }

    public String parse() {
	return builder.build().toString();
    }

}
