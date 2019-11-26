package com.andersenbel.magnit.challenge.dao.test.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.andersenbel.magnit.challenge.dao.test.TestDao;

/**
 * DAO class for working with entries in a database.
 * 
 * @author Vadim
 *
 */
public class TestDaoImpl implements TestDao, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(TestDaoImpl.class.getName());

	private static final String TEST_TABLE_NAME = "TEST";
	private static final String FIELD_COLUMN_NAME = "FIELD";

	/**
	 * Established connection with database.
	 */
	private Connection dbConnection;

	/**
	 * Insert into database required number of entries.
	 * 
	 * @param pCount - number of required entries.
	 */
	public void createNTests(final long pCount) throws SQLException {
		if (pCount > 0) {
			dbConnection.setAutoCommit(false);
			final StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("INSERT INTO ");
			sqlQuery.append(TEST_TABLE_NAME);
			sqlQuery.append("(");
			sqlQuery.append(FIELD_COLUMN_NAME);
			sqlQuery.append(") VALUES(?)");

			final PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sqlQuery.toString());

			for (int i = 0; i < pCount; i++) {
				preparedStatement.setLong(1, i);
				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
			dbConnection.commit();
			preparedStatement.close();

			log.info(pCount + " rows are inserted.");
		}
	}

	/**
	 * Read all entries from database.
	 * 
	 * @return - an array of entries.
	 */
	public long[] readAllTests() throws SQLException {
		long[] result = null;
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ");
		sqlQuery.append(FIELD_COLUMN_NAME);
		sqlQuery.append(" FROM ");
		sqlQuery.append(TEST_TABLE_NAME);

		final PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sqlQuery.toString());
		final ResultSet resultSet = preparedStatement.executeQuery();

		int numberOfRows = 0;
		if (resultSet.last()) {
			numberOfRows = resultSet.getRow();
			resultSet.beforeFirst();
		}
		result = new long[numberOfRows];
		for (int i = 0; i < numberOfRows; i++) {
			resultSet.next();
			result[i] = resultSet.getLong(1);
		}

		resultSet.close();
		preparedStatement.close();

		log.info(String.valueOf((result != null ? result.length : 0) + " rows are read."));

		return result;
	}

	/**
	 * Delete all entries from a table.
	 */
	@Override
	public void truncateTest() throws SQLException {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("TRUNCATE ");
		sqlQuery.append(TEST_TABLE_NAME);
		sqlQuery.append(";");
		final PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sqlQuery.toString());
		preparedStatement.execute();
		preparedStatement.close();
		log.info(TEST_TABLE_NAME + " table is truncated.");
	}

	public Connection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}
}
