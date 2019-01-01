package eu.mapidev.pi.htclient.util;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ClientPropertiesUtilTest {

    @Test
    public void shouldContainPropertiesLikeUsernamePasswordAndURL() {
	assertThat(ClientPropertiesUtil.CLIENT_PROPERTIES.keySet(),
		hasItems("htclient.username",
			"htclient.password",
			"htclient.url",
			"htclient.dht.sensor",
			"htclient.dht.pin",
			"htclient.sleep.millis",
			"htclient.temp.label",
			"htclient.hum.label"));
    }

    @Test
    public void shouldReturnPropertyForKeyUsername() {
	String expectedString = "admin";
	String actualString = ClientPropertiesUtil.getProperty("htclient.username");
	assertThat(expectedString, is(actualString));
    }

}
