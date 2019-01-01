package eu.mapidev.pi.htclient.service;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class HumTempJsonParserTest {

    private static final int INPUT_HUM = 50;
    private static final int INPUT_TEMP = 30;
    private HumTempJsonParserImpl parser;

    @Before
    public void setUp() {
	parser = new HumTempJsonParserImpl();
    }

    @Test
    public void shouldParseInputHumidityAndTemperature() {
	//when
	String actualContent = parser.addHumidity(INPUT_HUM).addTemperature(INPUT_TEMP).parse();

	//then
	assertThat(actualContent,
		is(both(containsString("\"humidityPercentage\":" + INPUT_HUM))
			.and(containsString("\"temperatureInCelsius\":" + INPUT_TEMP))));
    }

    @Test
    public void shouldOverwriteWhenAddingTheSameValueToParse() {
	//when
	String actualContent = parser.addHumidity(INPUT_HUM)
		.addTemperature(INPUT_TEMP)
		.addTemperature(INPUT_TEMP + 10).parse();

	//then 
	// regex: {"someName":number,"someName":number}
	assertThat(actualContent.matches("^\\{(\\\"\\w*\\\":\\d*.+){2}\\}$"), is(true));
	assertThat(actualContent, containsString("\"temperatureInCelsius\":" + (INPUT_TEMP + 10)));
    }

}
