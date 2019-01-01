package eu.mapidev.pi.htclient.service;

import static eu.mapidev.pi.htclient.util.ClientPropertiesUtil.getProperty;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HumTempRequestSenderImpl implements HumTempRequestSender {

    HttpURLConnection connection;
    private final static Logger LOGGER = Logger.getLogger(HumTempRequestSenderImpl.class.getName());

    public HumTempRequestSenderImpl() {
	try {
	    URL url = new URL(getProperty("htclient.url"));
	    connection = (HttpURLConnection) url.openConnection();
	} catch (IOException ex) {
	    throw new IllegalStateException("Cannot create request sender!");
	}
    }

    @Override
    public boolean send(String content) {
	boolean isResponseStatusCorrect = false;
	try {
	    connection.setDoOutput(true);
	    connection.setDoInput(true);
	    applyPostRequestHeaderWithBasicProperties();
	    addRequestBody(content);
	    int responseCode = connection.getResponseCode();
	    isResponseStatusCorrect = (responseCode == 201);
	} catch (IOException ex) {
	    LOGGER.log(Level.SEVERE, null, ex);
	}
	return isResponseStatusCorrect;
    }

    private void applyPostRequestHeaderWithBasicProperties() throws ProtocolException {
	String userCredentials = getProperty("htclient.username") + ":" + getProperty("htclient.password");
	byte[] encodedAuth = Base64.getEncoder().encode(userCredentials.getBytes());
	String basicAuth = "Basic " + new String(encodedAuth);
	connection.setRequestProperty("Authorization", basicAuth);
	connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	connection.setRequestMethod("POST");
    }

    private void addRequestBody(String content) {
	try (OutputStream os = connection.getOutputStream()) {
	    os.write(content.getBytes(StandardCharsets.UTF_8));
	} catch (IOException ex) {
	    LOGGER.log(Level.SEVERE, null, ex);
	}
    }

}
