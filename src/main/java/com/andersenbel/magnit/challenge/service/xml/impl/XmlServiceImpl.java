package com.andersenbel.magnit.challenge.service.xml.impl;

import java.io.File;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.andersenbel.magnit.challenge.dao.test.impl.TestDaoImpl;
import com.andersenbel.magnit.challenge.service.xml.XmlService;

/**
 * Bean for working with XML.
 * 
 * @author Vadim
 *
 */
public class XmlServiceImpl implements XmlService, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(TestDaoImpl.class.getName());

	/**
	 * File in XSTL format which contains rules for optimizing XML data.
	 */
	private File xsltFile;

	/**
	 * Write entries to XML file.
	 * 
	 * @param pFile    - target file.
	 * @param pEntries - entries for writing.
	 */
	@Override
	public void writetToXml(final File pFile, final long[] pEntries) {
		if (pEntries != null && pEntries.length > 0) {
			try {
				final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				final Document doc = docBuilder.newDocument();
				final Element rootElement = doc.createElement("entries");
				doc.appendChild(rootElement);

				for (long entry : pEntries) {
					final Element entryElement = doc.createElement("entry");
					final Element childElement = doc.createElement("field");
					childElement.appendChild(doc.createTextNode(String.valueOf(entry)));
					entryElement.appendChild(childElement);
					rootElement.appendChild(entryElement);
				}

				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
				final Transformer transformer = transformerFactory.newTransformer();
				final DOMSource source = new DOMSource(doc);
				final StreamResult result = new StreamResult(pFile);
				transformer.transform(source, result);
				log.info(pEntries.length + " entries were written to " + pFile.getName() + " file.");
			} catch (Exception e) {
				log.severe(e.getMessage());
			}
		}
	}

	/**
	 * Optimize first XML data from a file and save it in separated file.
	 * 
	 * @param pFrom - file for getting not optimized XML data.
	 * @param pTo   - file for saving optimized XML data.
	 */
	@Override
	public void optimizeXml(final File pFrom, final File pTo) {
		try {
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Source xsltStreamSource = new StreamSource(this.xsltFile);
			final Transformer transformer = transformerFactory.newTransformer(xsltStreamSource);
			final Source text = new StreamSource(pFrom);
			transformer.transform(text, new StreamResult(pTo));
			log.info("Entries from " + pFrom.getName() + " file were optimized and written to " + pTo.getName()
					+ " file.");
		} catch (Exception err) {
			log.severe(err.getMessage());
		}
	}

	/**
	 * Read XML data from a file.
	 * 
	 * @param pFile - file for parsing.
	 */
	@Override
	public Document readXml(final File pFile) {
		Document document = null;
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(pFile);
		} catch (Exception err) {
			err.printStackTrace();
		}
		return document;
	}

	public File getXsltFile() {
		return xsltFile;
	}

	public void setXsltFile(File xsltFile) {
		this.xsltFile = xsltFile;
	}
}
