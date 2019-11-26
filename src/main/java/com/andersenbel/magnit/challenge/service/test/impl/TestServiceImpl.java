package com.andersenbel.magnit.challenge.service.test.impl;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.andersenbel.magnit.challenge.dao.test.TestDao;
import com.andersenbel.magnit.challenge.service.test.TestService;
import com.andersenbel.magnit.challenge.service.xml.XmlService;

/**
 * Business logic service for working with entries.
 * 
 * @author Vadim
 *
 */
public class TestServiceImpl implements TestService, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(TestServiceImpl.class.getName());

	/**
	 * DAO bean for working with entries.
	 */
	private TestDao testDAO;
	/**
	 * Bean for working with XML data structures.
	 */
	private XmlService xmlService;

	/**
	 * Delete all entries and then insert N entries.
	 * 
	 * @param pN - number of entries.
	 */
	@Override
	public void createNTests(final long pN) {
		try {
			this.testDAO.truncateTest();
			this.testDAO.createNTests(pN);
		} catch (SQLException err) {
			log.severe(err.getMessage());
		}
	}

	/**
	 * Get all entries from database.
	 * 
	 * @return an array of entries.
	 */
	@Override
	public long[] readAllTests() {
		long[] tests = null;

		try {
			tests = this.testDAO.readAllTests();
		} catch (SQLException err) {
			log.severe(err.getMessage());
		}

		return tests;
	}

	/**
	 * Save entries to target file in XML format.
	 * 
	 * @param pTargetFile - target XML file.
	 * @param pEntries    - entries for writing to a file.
	 */
	@Override
	public void saveToXML(final File pTargetFile, final long[] pEntries) {
		this.xmlService.writetToXml(pTargetFile, pEntries);
	}

	/**
	 * Optimize XML data from the first file to the second file.
	 * 
	 * @param pFatFile       - XML file with data which will be optimized.
	 * @param pOptimizedFile - XML file for saving optimized XML data.
	 */
	@Override
	public void optimizeXML(final File pFatFile, final File pOptimizedFile) {
		this.xmlService.optimizeXml(pFatFile, pOptimizedFile);
	}

	/**
	 * Calculate a sum of all entries' fields from a file.
	 * 
	 * @param pFile - file which contains entries.
	 */
	@Override
	public long calculateSumOfEntryFields(final File pFile) {
		long result = 0;
		final Document document = this.xmlService.readXml(pFile);
		final Element root = document.getDocumentElement();
		if (root != null) {
			final NodeList nodeList = root.getChildNodes();
			if (nodeList != null) {
				final int size = nodeList.getLength();
				if (size > 0) {
					for (int i = 0; i < size; i++) {
						final Node node = nodeList.item(i);
						if (node != null) {
							final Node field = node.getAttributes().getNamedItem("field");
							try {
								long number = Long.valueOf(field.getTextContent());
								result += number;
							} catch (NumberFormatException err) {
								log.severe(err.getMessage());
							}
						}
					}
				}
			}
		}
		return result;
	}

	public TestDao getTestDAO() {
		return testDAO;
	}

	public void setTestDAO(TestDao testDAO) {
		this.testDAO = testDAO;
	}

	public XmlService getXmlService() {
		return xmlService;
	}

	public void setXmlService(XmlService xmlService) {
		this.xmlService = xmlService;
	}
}
