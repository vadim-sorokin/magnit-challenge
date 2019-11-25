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

public class TestAppImpl implements TestApp, Serializable {
	private final static long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(TestAppImpl.class.getName());

	private String rdbms;
	private String socket;
	private String schema;
	private String login;
	private String password;
	private String jdbcOptions;
	private long n;
	private File firstFile;
	private File secondFile;
	private File xsltFile;

	private Connection dbConnection;

	@Override
	public void launch() {
		log.info("started");
		run();
		finish();
		log.info("finished");
	}

	private void run() {
		final TestService testBean = createTestBean();
		testBean.createNTests(this.n);
		long[] fields = testBean.readAllTests();
		testBean.saveToXML(firstFile, fields);
		testBean.optimizeXML(this.firstFile, this.secondFile);
		long sum = testBean.calclateSum(secondFile);
		log.info("Sum is " + sum);
	}

	private void finish() {
		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException err) {
				log.severe(err.getMessage());
			}
		}
	}

	private TestService createTestBean() {
		final TestServiceImpl testBean = new TestServiceImpl();
		final TestDao testDAO = createTestDAO();
		final XmlService xmlService = createXmlService();
		testBean.setTestDAO(testDAO);
		testBean.setXmlService(xmlService);
		return testBean;
	}

	private TestDao createTestDAO() {
		final TestDaoImpl testDAO = new TestDaoImpl();
		try {
			this.dbConnection = DriverManager.getConnection(TestUtility.createJDBCUrl(this.rdbms, this.socket,
					this.schema, this.login, this.password, this.jdbcOptions));
			testDAO.setDbConnection(this.dbConnection);
		} catch (SQLException e) {
			log.severe(e.getMessage());
		}
		return testDAO;
	}

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
