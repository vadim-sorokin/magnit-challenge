package com.andersenbel.magnit.challenge.service.xml.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.andersenbel.magnit.challenge.dao.test.impl.TestDaoImpl;
import com.andersenbel.magnit.challenge.service.xml.XmlService;

public class XmlServiceImpl implements XmlService, Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(TestDaoImpl.class.getName());

	private File xsltFile;

	@Override
	public void writetToXml(final File pFile, final long[] pEntries) {
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
		} catch (ParserConfigurationException e) {
			log.severe(e.getMessage());
		} catch (TransformerException e) {
			log.severe(e.getMessage());
		}
	}

	@Override
	public void optimizeXml(final File pFatFile, final File pOptimizedFile) {
		try {
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Source xsltStreamSource = new StreamSource(xsltFile);
			final Transformer transformer = transformerFactory.newTransformer(xsltStreamSource);
			final Source text = new StreamSource(pFatFile);
			transformer.transform(text, new StreamResult(pOptimizedFile));
		} catch (TransformerException err) {
			log.severe(err.getMessage());
		}
	}

	@Override
	public Document readXml(final File pFile) {
		Document document = null;
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(pFile);
		} catch (ParserConfigurationException err) {
			err.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
