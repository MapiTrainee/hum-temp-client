package eu.mapidev.pi.htclient.service;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HumTempReaderTest {

    @Mock
    private AdafruitPythonDHTReader reader;

    @Test
    public void shouldReadCurrentTemperature() {
	BDDMockito.given(reader.readData()).willReturn(new int[]{20, 20});
	assertThat(reader.readData()[1], is(both(lessThanOrEqualTo(100)).and(greaterThan(-100))));
    }

    @Test
    public void shouldReadCurrentHumidityPercante() {
	BDDMockito.given(reader.readData()).willReturn(new int[]{20, 20});
	assertThat(reader.readData()[0], is(both(lessThanOrEqualTo(100)).and(greaterThanOrEqualTo(0))));
    }

}
