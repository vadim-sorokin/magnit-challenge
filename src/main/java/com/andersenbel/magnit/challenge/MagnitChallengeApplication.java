package com.andersenbel.magnit.challenge;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

import com.andersenbel.magnit.challenge.app.TestApp;
import com.andersenbel.magnit.challenge.app.impl.TestAppImpl;
import com.andersenbel.magnit.challenge.utility.SettingsConstant;

public class MagnitChallengeApplication {
	private final static Logger log = Logger.getLogger(MagnitChallengeApplication.class.getName());

	public static void main(String[] args) {
		createTestApp().launch();
	}

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
			app.setXsltFile(loadClasspathFile(SettingsConstant.FILE_XSLT_KEY));
		} catch (IOException err) {
			log.severe(err.getMessage());
		}

		return app;
	}

	private static Properties loadProperties() throws IOException {
		final Properties properties = new Properties();
		final InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(SettingsConstant.SETTINGS_PROPERTIES_FILE_NAME);

		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} finally {
				inputStream.close();
			}
		}
		return properties;
	}

	private static File loadClasspathFile(final String pPath) {
		File file = null;

		try {
			file = Paths.get(ClassLoader.getSystemResource(pPath).toURI()).toFile();
		} catch (URISyntaxException err) {
			log.severe(err.getMessage());
		}

		return file;
	}

	private static File loadSystemFile(final String pPath) {
		return new File(pPath);
	}

}
