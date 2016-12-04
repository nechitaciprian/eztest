package net.eztest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EzTestContext {

	private static final String SELENIUM_WEBDRIVER_CLASS = "eztest.WEBDRIVER_CLASS";
	private static final String DEFAULT_SELENIUM_WEBDRIVER_CLASS = "org.openqa.selenium.chrome.ChromeDriver";
	private static final String SELENIUM_WEBDRIVER_CAPABILITIES = "eztest.WEBDRIVER_CAPABILITIES";
	private static final String DEFAULT_SELENIUM_WEBDRIVER_CAPABILITIES = "version:'',platform:'ANY',browserName:'chrome',javascriptEnabled:true";
	private static final String SELENIUM_WEBDRIVER_WAIT_TIMEOUT = "eztest.WEBDRIVER_WAIT_TIMEOUT";
	private static final String SELENIUM_WEBDRIVER_WAIT_TIMEOUT_DEFAULT = "10000";
	private static final String SELENIUM_WEBDRIVER_POLL_INTERVAL = "eztest.WEBDRIVER_POLL_INTERVAL";
	private static final String SELENIUM_WEBDRIVER_POLL_INTERVAL_DEFAULT = "250";

	private static Logger log = Logger.getLogger(EzTest.class.getCanonicalName());

	private static final Capabilities capabilities;
	static {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		try {
			String strCapabilities = System.getProperty(SELENIUM_WEBDRIVER_CAPABILITIES,
					DEFAULT_SELENIUM_WEBDRIVER_CAPABILITIES);
			log.info("EzTest: Init WebDriver Capabilities: " + strCapabilities);
			HashMap<String, Object> rawMap = mapper.readValue("{" + strCapabilities + "}", HashMap.class);
			capabilities = new DesiredCapabilities(rawMap);

		} catch (Exception e) {
			throw new Error("Parsing capabilities error.", e);
		}
	}

	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private static final ThreadLocal<FluentWait<WebDriver>> wait = new ThreadLocal<FluentWait<WebDriver>>();

	public static void init() {
		log.fine("EzTestContext.init()");

		try {
			WebDriver aDriver = (WebDriver) Class
					.forName(System.getProperty(SELENIUM_WEBDRIVER_CLASS, DEFAULT_SELENIUM_WEBDRIVER_CLASS))
					.getConstructor(Capabilities.class).newInstance(capabilities);

			long waitTimeout = Long.parseLong(
					System.getProperty(SELENIUM_WEBDRIVER_WAIT_TIMEOUT, SELENIUM_WEBDRIVER_WAIT_TIMEOUT_DEFAULT));
			long pollInterval = Long.parseLong(
					System.getProperty(SELENIUM_WEBDRIVER_POLL_INTERVAL, SELENIUM_WEBDRIVER_POLL_INTERVAL_DEFAULT));

			log.info(String.format(
					"EzTest: Init WebDriver (Implicit Wait Timeout(milliseconds): %d, Polling interval(milliseconds): %d)",
					waitTimeout, pollInterval));

			FluentWait<WebDriver> aWait = new FluentWait<WebDriver>(aDriver);

			aWait.withTimeout(waitTimeout, TimeUnit.MILLISECONDS);
			aWait.pollingEvery(pollInterval, TimeUnit.MILLISECONDS);
			aWait.ignoring(NoSuchElementException.class);

			driver.set(aDriver);
			wait.set(aWait);

		} catch (Exception e) {
			throw new Error("Error init driver.", e);
		}
	}

	public static void quit() {
		
		log.fine("EzTestContext.quit()");

		if (driver.get() != null) {
			driver.get().quit();
		}
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static FluentWait<WebDriver> getWait() {
		return wait.get();
	}

}
