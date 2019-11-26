package com.andersenbel.magnit.challenge.dao.test.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.andersenbel.magnit.challenge.dao.test.TestDao;

/**
 * Test class for Test DAO.
 * 
 * @author Vadim
 *
 */
public class TestDaoImplTest {
	private static Connection databaseConnection;
	private static TestDao testDao;

	/**
	 * Establish connection to database and initialize Test DAO.
	 * 
	 * @throws SQLException in case of any exception related to communication with
	 *                      database.
	 */
	@BeforeClass
	public static void init() throws SQLException {
		// establish connection to database
		final String jdbcUrl = "jdbc:mysql://localhost:3306/magnit?user=root&password=root&useServerPrepStmts=false&rewriteBatchedStatements=true&useCompression=true";
		databaseConnection = DriverManager.getConnection(jdbcUrl);

		// Create Test DAO bean and fill it with sucesfull database connection.
		final TestDaoImpl testDaoImpl = new TestDaoImpl();
		testDaoImpl.setDbConnection(databaseConnection);
		testDao = testDaoImpl;
	}

	/**
	 * Check successful creating of N rows.
	 * 
	 * @throws SQLException in case of any exception related to communication with
	 *                      database.
	 */
	@Test
	public void checkCreateNTests() throws SQLException {
		// Prepare
		long expectedNumberOfInsertedRows = 100;
		// Truncate table before inserting
		databaseConnection.createStatement().execute("TRUNCATE TEST");

		// Act
		testDao.createNTests(expectedNumberOfInsertedRows);

		// Check
		final ResultSet resultSet = databaseConnection.createStatement().executeQuery("SELECT COUNT(*) FROM TEST");
		resultSet.next();
		final long realNumberOfInsertedRows = resultSet.getLong(1);
		Assert.assertEquals(expectedNumberOfInsertedRows, realNumberOfInsertedRows);
	}

	/**
	 * Check successful reading of inserted rows.
	 * 
	 * @throws SQLException in case of any exception related to communication with
	 *                      database.
	 */
	@Test
	public void checkReadAllTests() throws SQLException {
		// Prepare
		final long[] expectedEntries = new long[] { 1, 2, 3 };
		final String truncate = "TRUNCATE TEST";
		databaseConnection.createStatement().execute(truncate);
		for (long entry : expectedEntries) {
			databaseConnection.createStatement().execute("INSERT INTO TEST(FIELD) VALUE(" + entry + ")");
		}

		// Act
		final long[] actualEntries = testDao.readAllTests();

		// Check
		Assert.assertArrayEquals(expectedEntries, actualEntries);
	}

	/**
	 * Check successful table truncating.
	 * 
	 * @throws SQLException in case of any exception related to communication with
	 *                      database.
	 */
	@Test
	public void checkTruncate() throws SQLException {
		// Prepare
		final long expectedNumberOfRows = 0;
		final String truncate = "TRUNCATE TEST";
		databaseConnection.createStatement().execute(truncate);
		for (int i = 0; i < 10; i++) {
			databaseConnection.createStatement().execute("INSERT INTO TEST(FIELD) VALUE(" + i + ")");
		}

		// Act
		testDao.truncateTest();

		// Check
		final ResultSet resultSet = databaseConnection.createStatement().executeQuery("SELECT COUNT(*) FROM TEST");
		resultSet.next();
		final long realNumberOfInsertedRows = resultSet.getLong(1);
		Assert.assertEquals(expectedNumberOfRows, realNumberOfInsertedRows);
	}

	/**
	 * Close opened connection to database.
	 * 
	 * @throws SQLException in case of any exception related to communication with
	 *                      database.
	 */
	@AfterClass
	public static void destroy() throws SQLException {
		if (databaseConnection != null) {
			databaseConnection.close();
		}
	}
}
