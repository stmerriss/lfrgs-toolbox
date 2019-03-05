package com.liferay.gs.test.framework;

import com.liferay.gs.test.framework.constants.Drivers;
import com.liferay.gs.test.framework.constants.SystemProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.openqa.selenium.Platform;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author Shane Merriss
 */
public class BaseRunner {

	@BeforeClass
	public static void setDrivers(){
		switch (Platform.getCurrent()) {
			case LINUX:
				setResourceDriver(SystemProperties.WEBDRIVER_CHROME_DRIVER, Drivers.CHROME_LINUX64);
				setResourceDriver(SystemProperties.WEBDRIVER_FIREFOX_DRIVER, Drivers.FIREFOX_LINUX32);
				break;

			case MAC:
				setResourceDriver(SystemProperties.WEBDRIVER_CHROME_DRIVER, Drivers.CHROME_MAC64);
				setResourceDriver(SystemProperties.WEBDRIVER_FIREFOX_DRIVER, Drivers.FIREFOX_MAC);
				set(SystemProperties.WEBDRIVER_SAFARI_DRIVER, Drivers.SAFARI_MAC);
				break;

			case WINDOWS:
				setResourceDriver(SystemProperties.WEBDRIVER_CHROME_DRIVER, Drivers.CHROME_WIN32);
				setResourceDriver(SystemProperties.WEBDRIVER_CHROME_DRIVER, Drivers.FIREFOX_LINUX32);
				break;
		}
	}

	protected static void set(String key, String value) {
		System.setProperty(key, value);
	}

	protected static void setResourceDriver(String key, String driverName) {

		ClassLoader classLoader = BaseRunner.class.getClassLoader();

		URL resource = classLoader.getResource(Drivers.BASE_DRIVER_PATH + driverName);

		if (Objects.isNull(resource)) {
			_log.error("Unable to locate driver: " + driverName);
			return;
		}

		try {
			Path path = Paths.get(resource.toURI());

			set(key, path.toFile().getAbsolutePath());
		}
		catch (URISyntaxException urise) {
			_log.error("Unable to load driver from resources: " + driverName, urise);
		}
	}

	private static Log _log = LogFactory.getLog(BaseRunner.class);

}
