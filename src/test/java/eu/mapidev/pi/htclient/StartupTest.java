package eu.mapidev.pi.htclient;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class StartupTest {
    
    @Test
    public void shouldHaveMainMethod() throws NoSuchMethodException {
	Class<?> startupClass = Startup.class;
	Method method = startupClass.getMethod("main", String[].class);
	assertThat(method, notNullValue());
    }
    
}
