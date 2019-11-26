package com.andersenbel.magnit.challenge;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

import com.andersenbel.magnit.challenge.app.TestApp;
import com.andersenbel.magnit.challenge.app.impl.TestAppImpl;
import com.andersenbel.magnit.challenge.utility.SettingsConstant;

/**
 * Start class for creating and initializing the main class of application.
 * 
 * @author Vadim
 *
 */
public class MagnitChallengeApplication {
	private final static Logger log = Logger.getLogger(MagnitChallengeApplication.class.getName());

	private final static String APPLICATION_STARTED_MESSAGE = "The application started.";
	private final static String APPLICATION_FINISHED_MESSAGE = "The application finished.";
	private final static String INITIALIZATION_FAILED_MESSAGE = "The application is failed during initialization phase.";
	private final static String SHUTDOWN_MESSAGE = "The application will be forced to shut down.";
	private final static String EXECUTION_TIME_MESSAGE = "Execution time (seconds) is: ";

	public static void main(String[] args) {
		log.info(APPLICATION_STARTED_MESSAGE);
		final long started = System.currentTimeMillis();

		createTestApp().launch();

		printElapsedTime(started);
		log.info(APPLICATION_FINISHED_MESSAGE);
	}

	/**
	 * Create TestAppImpl instance and fill it with required dependencies.
	 * 
	 * @return application's interface
	 */
	private static TestApp createTestApp() {
		final TestAppImpl app = new TestAppImpl();

		try {
			final Properties properties = loadProperties();
			app.setRdbms(properties.getProperty(SettingsConstant.DATASOURCE_RDBMS_KEY));
			app.setSocket(properties.getProperty(SettingsConstant.DATASOURCE_SOCKET_KEY));
			app.setLogin(properties.getProperty(SettingsConstant.DATASOURCE_LOGIN_KEY));
			app.setPassword(properties.getProperty(SettingsConstant.DATASOURCE_PASSWORD_KEY));
			app.setSchema(properties.getProperty(SettingsConstant.DATASOURCE_SCHEMA_KEY));
			app.setJdbcOptions(properties.getProperty(SettingsConstant.DATASOURCE_JDBC_OPTIONS));
			app.setN(Long.valueOf(properties.getProperty(SettingsConstant.N_KEY)));
			app.setFirstFile(loadSystemFile(properties.getProperty(SettingsConstant.FILE_NAME_FIRST_KEY)));
			app.setSecondFile(loadSystemFile(properties.getProperty(SettingsConstant.FILE_NAME_SECOND_KEY)));
			app.setXsltFile(loadXsltFile());
		} catch (Exception err) {
			log.severe(INITIALIZATION_FAILED_MESSAGE);
			log.severe(SHUTDOWN_MESSAGE);
			log.severe(err.getMessage());
			System.exit(1);
		}

		return app;
	}

	/**
	 * Load properties file which located on classpath. The file contains all
	 * settings for the application.
	 * 
	 * @return application settings as properties
	 * @throws Exception in case of impossibility to find or parse required file.
	 */
	private static Properties loadProperties() throws Exception {
		final Properties properties = new Properties();
		try (final InputStream inputStream = new FileInputStream(
				loadClasspathFile(SettingsConstant.SETTINGS_PROPERTIES_FILE_NAME))) {
			properties.load(inputStream);
		}
		return properties;
	}

	/**
	 * Load XSLT file which located on classpath and contains rules for optimizing
	 * XML data structure.
	 * 
	 * @return XSLT file
	 * @throws Exception in case of impossibility to find or parse required file.
	 */
	private static File loadXsltFile() throws Exception {
		return loadClasspathFile(SettingsConstant.FILE_XSLT_KEY);
	}

	/**
	 * Common method for retrieving any file on classpath.
	 * 
	 * @param pPath path to file.
	 * @return required file.
	 * @throws Exception in case of impossibility to find or parse required file.
	 */
	private static File loadClasspathFile(final String pPath) throws Exception {
		return Paths.get(ClassLoader.getSystemResource(pPath).toURI()).toFile();
	}

	/**
	 * Common method for retrieving any file located on system path.
	 * 
	 * @param pPath path to file.
	 * @return required file.
	 */
	private static File loadSystemFile(final String pPath) {
		return new File(pPath);
	}

	/**
	 * Calculate the difference between timestamps and print it. The formula of
	 * calculating: difference = (now - <b>pStarted</b>) / 1000;
	 * 
	 * @param pStarted starting time.
	 */
	private static void printElapsedTime(final long pStarted) {
		final StringBuilder result = new StringBuilder();
		result.append(EXECUTION_TIME_MESSAGE);
		result.append((System.currentTimeMillis() - pStarted) / 1000);
		log.info(result.toString());
	}

}
