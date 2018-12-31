package eu.mapidev.pi.htclient.service;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class HumTempReaderTest {

    private HumTempReader reader;

    @Before
    public void setUp() {
	reader = new HumTempReader();
    }

    @Test
    public void shouldReadCurrentTemperature() {
	assertThat(reader.readTemperature(), is(both(lessThanOrEqualTo(100)).and(greaterThan(-100))));
    }

    @Test
    public void shouldReadCurrentHumidityPercante() {
	assertThat(reader.readHumidityPercentage(), is(both(lessThanOrEqualTo(100)).and(greaterThanOrEqualTo(0))));
    }

}
