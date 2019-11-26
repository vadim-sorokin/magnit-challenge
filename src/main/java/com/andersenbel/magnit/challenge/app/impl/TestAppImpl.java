package com.andersenbel.magnit.challenge.app.impl;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.andersenbel.magnit.challenge.app.TestApp;
import com.andersenbel.magnit.challenge.dao.test.TestDao;
import com.andersenbel.magnit.challenge.dao.test.impl.TestDaoImpl;
import com.andersenbel.magnit.challenge.service.test.TestService;
import com.andersenbel.magnit.challenge.service.test.impl.TestServiceImpl;
import com.andersenbel.magnit.challenge.service.xml.XmlService;
import com.andersenbel.magnit.challenge.service.xml.impl.XmlServiceImpl;
import com.andersenbel.magnit.challenge.utility.TestUtility;

/**
 * The main application class. Java-bean compatible.
 * 
 * @author Vadim
 *
 */
public class TestAppImpl implements TestApp, Serializable {
	private final static long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(TestAppImpl.class.getName());

	private final static String DB_CONNECTION_FAILED_MESSAGE = "Unable to connect to a database. The application will be forced to shut down.";

	/**
	 * RDMBS's name. JDBC compatible.
	 */
	private String rdbms;
	/**
	 * Database's host and port.
	 */
	private String socket;
	/**
	 * Database's schema.
	 */
	private String schema;
	/**
	 * Database's login.
	 */
	private String login;
	/**
	 * Database's password.
	 */
	private String password;
	/**
	 * Any additional JDBC parameters presented in one row and separated by &.
	 */
	private String jdbcOptions;
	/**
	 * Number of entries from the technical documentation.
	 */
	private long n;
	/**
	 * 1.xml from the technical documentation.
	 */
	private File firstFile;
	/**
	 * 2.xml from the technical documentation.
	 */
	private File secondFile;
	/**
	 * XSLT file with rules for optimizing XML structure.
	 */
	private File xsltFile;

	/**
	 * Established connection with database.
	 */
	private Connection dbConnection;

	/**
	 * Start an execution of the application.
	 */
	@Override
	public void launch() {
		run();
		finish();
	}

	/**
	 * Create all required dependencies and then execute all points from the
	 * technical description.
	 */
	private void run() {
		final TestService testBean = createTestBean();

		// Point #2 from the technical description: Truncate a table and then insert
		// N entries.
		testBean.createNTests(this.n);

		// Point #3 from the technical description: Read all rows from a table and save
		// them into xml file.
		long[] fields = testBean.readAllTests();
		testBean.saveToXML(firstFile, fields);

		// Point #4 from the technical description: Optimize XML structure with help of
		// XSTL and then save it to a new file.
		testBean.optimizeXML(this.firstFile, this.secondFile);

		// Point #5 from the technical description: Calculate sum of entries' fields and
		// print it to the console
		long sum = testBean.calculateSumOfEntryFields(secondFile);
		log.info("Sum of entries fields is: " + sum);
	}

	/**
	 * Finish the execution and close all opened resources.
	 */
	private void finish() {
		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException err) {
				log.severe(err.getMessage());
			}
		}
	}

	/**
	 * Instantiate business logic bean and fill it with required dependencies.
	 * 
	 * @return filled and ready for using business logic bean.
	 */
	private TestService createTestBean() {
		final TestServiceImpl testBean = new TestServiceImpl();
		final TestDao testDAO = createTestDAO();
		final XmlService xmlService = createXmlService();
		testBean.setTestDAO(testDAO);
		testBean.setXmlService(xmlService);
		return testBean;
	}

	/**
	 * Instantiate DAO bean and fill it with required dependencies.
	 * 
	 * @return filled and ready for using DAO bean.
	 */
	private TestDao createTestDAO() {
		final TestDaoImpl testDAO = new TestDaoImpl();
		try {
			this.dbConnection = DriverManager.getConnection(TestUtility.createJDBCUrl(this.rdbms, this.socket,
					this.schema, this.login, this.password, this.jdbcOptions));
			testDAO.setDbConnection(this.dbConnection);
		} catch (Exception e) {
			log.severe(DB_CONNECTION_FAILED_MESSAGE);
			log.severe(e.getMessage());
			System.exit(1);
		}
		return testDAO;
	}

	/**
	 * Instantiate bean for working with XML and fill it with required dependencies.
	 * 
	 * @return filled and ready bean for working with XML.
	 */
	private XmlService createXmlService() {
		final XmlServiceImpl xmlServiceImpl = new XmlServiceImpl();
		xmlServiceImpl.setXsltFile(this.xsltFile);
		return xmlServiceImpl;
	}

	public String getRdbms() {
		return rdbms;
	}

	public void setRdbms(String rdbms) {
		this.rdbms = rdbms;
	}

	public String getSocket() {
		return socket;
	}

	public void setSocket(String socket) {
		this.socket = socket;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getN() {
		return n;
	}

	public void setN(long n) {
		this.n = n;
	}

	public File getFirstFile() {
		return firstFile;
	}

	public void setFirstFile(File firstFile) {
		this.firstFile = firstFile;
	}

	public File getSecondFile() {
		return secondFile;
	}

	public void setSecondFile(File secondFile) {
		this.secondFile = secondFile;
	}

	public File getXsltFile() {
		return xsltFile;
	}

	public void setXsltFile(File xsltFile) {
		this.xsltFile = xsltFile;
	}

	public String getJdbcOptions() {
		return jdbcOptions;
	}

	public void setJdbcOptions(String jdbcOptions) {
		this.jdbcOptions = jdbcOptions;
	}

}
