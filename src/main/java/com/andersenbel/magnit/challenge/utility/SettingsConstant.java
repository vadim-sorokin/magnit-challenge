package com.andersenbel.magnit.challenge.utility;

/**
 * Utility class for storing properties and files names.
 * 
 * @author Vadim
 *
 */
public final class SettingsConstant {
	// File contains main settings for Application
	public final static String SETTINGS_PROPERTIES_FILE_NAME = "settings.properties";

	// Datasource
	public final static String DATASOURCE_RDBMS_KEY = "datasource.rdbms";
	public final static String DATASOURCE_SOCKET_KEY = "datasource.socket";
	public final static String DATASOURCE_SCHEMA_KEY = "datasource.schema";
	public final static String DATASOURCE_LOGIN_KEY = "datasource.login";
	public final static String DATASOURCE_PASSWORD_KEY = "datasource.password";
	public final static String DATASOURCE_JDBC_OPTIONS = "datasource.options";

	// Number of entries
	public final static String N_KEY = "n";

	// Files
	public final static String FILE_NAME_FIRST_KEY = "file.name.first";
	public final static String FILE_NAME_SECOND_KEY = "file.name.second";

	// XSLT
	public final static String FILE_XSLT_KEY = "optimized.xslt";

	private SettingsConstant() {

	}
}
