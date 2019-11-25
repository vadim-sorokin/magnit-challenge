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

public class TestServiceImpl implements TestService, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(TestServiceImpl.class.getName());

	private TestDao testDAO;
	private XmlService xmlService;

	@Override
	public void createNTests(final long pN) {
		try {
			this.testDAO.createNTests(pN);
		} catch (SQLException err) {
			log.severe(err.getMessage());
		}
	}

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

	@Override
	public void saveToXML(final File pTargetFile, final long[] pEntries) {
		this.xmlService.writetToXml(pTargetFile, pEntries);
	}

	@Override
	public void optimizeXML(final File pFatFile, final File pOptimizedFile) {
		this.xmlService.optimizeXml(pFatFile, pOptimizedFile);
	}

	@Override
	public long calclateSum(final File pFile) {
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
