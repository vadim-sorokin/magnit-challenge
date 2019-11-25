package com.andersenbel.magnit.challenge.dao.test;

import java.sql.SQLException;

public interface TestDao {
	public void createNTests(final long pCount) throws SQLException;

	public long[] readAllTests() throws SQLException;
}
