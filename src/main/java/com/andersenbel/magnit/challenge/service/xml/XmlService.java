package com.andersenbel.magnit.challenge.service.xml;

import java.io.File;

import org.w3c.dom.Document;

public interface XmlService {
	/**
	 * Write entries to XML file.
	 * 
	 * @param pFile    - target file.
	 * @param pEntries - entries for writing.
	 */
	public void writeToXml(final File pFile, final long[] pEntries);

	/**
	 * Optimize first XML data from a file and save it in separated file.
	 * 
	 * @param pFrom - file for getting not optimized XML data.
	 * @param pTo   - file for saving optimized XML data.
	 */
	public void optimizeXml(final File pFatFile, final File pOptimizedFile);

	/**
	 * Read XML data from a file.
	 * 
	 * @param pFile - file for parsing.
	 */
	public Document readXml(final File pFile);
}
