package com.andersenbel.magnit.challenge.service.test;

import java.io.File;

/**
 * Business logic service for working with entries.
 * 
 * @author Vadim
 *
 */
public interface TestService {
	/**
	 * Delete all entries and then insert N entries.
	 * 
	 * @param pN - number of entries.
	 */
	public void createNTests(final long pN);

	/**
	 * Get all entries from database.
	 * 
	 * @return an array of entries.
	 */
	public long[] readAllTests();

	/**
	 * Save entries to target file in XML format.
	 * 
	 * @param pTargetFile - target XML file.
	 * @param pEntries    - entries for writing to a file.
	 */
	public void saveToXML(final File pTargetFile, final long[] pEntries);

	/**
	 * Optimize XML data from the first file to the second file.
	 * 
	 * @param pFatFile       - XML file with data which will be optimized.
	 * @param pOptimizedFile - XML file for saving optimized XML data.
	 */
	public void optimizeXML(final File pFatFile, final File pOptimizedFile);

	/**
	 * Calculate a sum of all entries' fields from a file.
	 * 
	 * @param pFile - file which contains entries.
	 */
	public long calculateSumOfEntryFields(final File pFile);
}
