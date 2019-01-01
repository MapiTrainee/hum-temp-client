package eu.mapidev.pi.htclient;

import eu.mapidev.pi.htclient.service.AdafruitPythonDHTReader;
import eu.mapidev.pi.htclient.service.HumTempJsonParser;
import eu.mapidev.pi.htclient.service.HumTempJsonParserImpl;
import eu.mapidev.pi.htclient.service.HumTempReader;
import eu.mapidev.pi.htclient.service.HumTempRequestSender;
import eu.mapidev.pi.htclient.service.HumTempRequestSenderImpl;
import eu.mapidev.pi.htclient.util.ClientPropertiesUtil;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Startup {

    private static final Logger LOGGER = Logger.getLogger(Startup.class.getName());

    public static void main(String[] args) {
	runAsInfiniteLoop();
    }

    public static void run() {
	HumTempReader reader = new AdafruitPythonDHTReader();
	int[] data = reader.readData();
	int temp = data[0];
	int hum = data[1];
	LOGGER.log(Level.INFO, "Humidity:{0}[%], Temperature:{1}[C]", new Object[]{hum, temp});

	HumTempJsonParser parser = new HumTempJsonParserImpl();
	String htJsonObject = parser
		.addTemperature(temp)
		.addHumidity(hum)
		.parse();

	HumTempRequestSender sender = new HumTempRequestSenderImpl();
	if (sender.send(htJsonObject)) {
	    LOGGER.info("... have been sent!");
	}
    }

    public static void runAsInfiniteLoop() {
	long millis = Long.parseLong(ClientPropertiesUtil.getProperty("htclient.sleep.millis"));
	LOGGER.log(Level.INFO, "Report every {0} [ms]", millis);
	while (true) {
	    try {
		run();
		TimeUnit.MILLISECONDS.sleep(millis);
	    } catch (InterruptedException ex) {
		LOGGER.log(Level.SEVERE, null, ex);
	    }
	}
    }
}
