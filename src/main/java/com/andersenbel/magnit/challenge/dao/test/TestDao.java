package com.andersenbel.magnit.challenge.dao.test;

import java.sql.SQLException;

/**
 * DAO class for working with entries in a database.
 * 
 * @author Vadim
 *
 */
public interface TestDao {
	/**
	 * Insert into database required number of entries.
	 * 
	 * @param pCount - number of required entries.
	 */
	public void createNTests(final long pCount) throws SQLException;

	/**
	 * Read all entries from database.
	 * 
	 * @return - an array of entries.
	 */
	public long[] readAllTests() throws SQLException;

	/**
	 * Delete all entries from a table.
	 */
	public void truncateTest() throws SQLException;
}
