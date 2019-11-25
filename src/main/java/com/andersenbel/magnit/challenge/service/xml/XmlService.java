package com.andersenbel.magnit.challenge.service.xml;

import java.io.File;

import org.w3c.dom.Document;

public interface XmlService {
	public void writetToXml(final File pFile, final long[] pEntries);

	public void optimizeXml(final File pFatFile, final File pOptimizedFile);

	public Document readXml(final File pFile);
}
