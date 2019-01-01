package eu.mapidev.pi.htclient.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.atLeastOnce;
import static org.mockito.BDDMockito.any;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HumTempRequestSenderTest {

    @Mock
    private HttpURLConnection connection;

    private static final String CONTENT_TO_SEND = "{\"humidityPercentage\":20,\"temperatureInCelsius\":20}";
    private HumTempRequestSenderImpl sender;

    @Before
    public void setUp() {
	sender = new HumTempRequestSenderImpl();
	sender.connection = connection;
    }

    @Test
    public void shouldSendPostRequestWithPropertiesAndAddedContent() throws IOException {
	//given
	ByteArrayOutputStream os = new ByteArrayOutputStream();
	given(connection.getResponseCode()).willReturn(201);
	given(connection.getOutputStream()).willReturn(os);
	ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

	//when
	boolean isSent = sender.send(CONTENT_TO_SEND);

	//then
	assertThat(isSent, is(true));
	verify(connection, atLeastOnce()).setRequestMethod(eq("POST"));
	verify(connection, atLeastOnce()).setRequestProperty(argument.capture(), any());
	assertThat(argument.getAllValues(), Matchers.contains("Authorization", "Content-Type"));
	verify(connection, atLeastOnce()).getResponseCode();
	verify(connection, atLeastOnce()).getOutputStream();
	assertThat(new String(os.toByteArray(), StandardCharsets.UTF_8), is(CONTENT_TO_SEND));
    }

}
