package eu.mapidev.pi.htclient.service;

import eu.mapidev.pi.htclient.util.ClientPropertiesUtil;
import java.util.Collections;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;

public class HumTempJsonParserImpl implements HumTempJsonParser {

    private final JsonBuilderFactory factory = Json.createBuilderFactory(Collections.emptyMap());
    private final JsonObjectBuilder builder = factory.createObjectBuilder();

    @Override
    public HumTempJsonParserImpl addTemperature(int temp) {
	builder.add(ClientPropertiesUtil.getProperty("htclient.temp.label"), temp);
	return this;
    }

    @Override
    public HumTempJsonParserImpl addHumidity(int hum) {
	builder.add(ClientPropertiesUtil.getProperty("htclient.hum.label"), hum);
	return this;
    }

    @Override
    public String parse() {
	return builder.build().toString();
    }

}
