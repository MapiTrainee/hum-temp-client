package eu.mapidev.pi.htclient.service;

import static eu.mapidev.pi.htclient.util.ClientPropertiesUtil.getProperty;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdafruitPythonDHTReader implements HumTempReader {

    private static final String PYTHON_CMD = getPythonCmd();
    private static final String REGEX = "Temp=(\\d+)\\.\\d\\*\\s+Humidity=(\\d+)\\.\\d%";
    private static final Logger LOGGER = Logger.getLogger(AdafruitPythonDHTReader.class.getName());

    private static String getPythonCmd() {
	String pinNumber = getProperty("htclient.dht.pin");
	String sensorNumber = getProperty("htclient.dht.sensor");
	return "sudo python AdafruitDHT.py " + sensorNumber + " " + pinNumber;
    }

    @Override
    public int[] readData() {
	Process process = runProcessAndWaitForResult();
	String outputMessage = getOutputMessageFromProcess(process);
	int data[] = parseOutputMessageToData(outputMessage);
	return data;
    }

    private Process runProcessAndWaitForResult() {
	try {
	    Process process = Runtime.getRuntime().exec(PYTHON_CMD);
	    process.waitFor();
	    return process;
	} catch (IOException | InterruptedException ex) {
	    LOGGER.log(Level.SEVERE, null, ex);
	}
	throw new IllegalStateException("Cannot run python process: " + PYTHON_CMD);
    }

    private String getOutputMessageFromProcess(Process process) {
	StringBuilder builder = new StringBuilder();
	try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
	    String line;
	    while ((line = reader.readLine()) != null) {
		builder.append(line).append('\n');
	    }
	} catch (IOException ex) {
	    LOGGER.log(Level.SEVERE, null, ex);
	}
	return builder.toString();
    }

    private int[] parseOutputMessageToData(String outputMessage) {
	int[] data = new int[2];
	Matcher matcher = Pattern.compile(REGEX).matcher(outputMessage);
	while (matcher.find()) {
	    data[0] = Integer.parseInt(matcher.group(1));
	    data[1] = Integer.parseInt(matcher.group(2));
	}
	return data;
    }

}
