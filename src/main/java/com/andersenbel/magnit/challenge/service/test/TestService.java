package com.andersenbel.magnit.challenge.service.test;

import java.io.File;

public interface TestService {
	public void createNTests(final long pN);

	public long[] readAllTests();

	public void saveToXML(final File pTargetFile, final long[] pEntries);

	public void optimizeXML(final File pFatFile, final File pOptimizedFile);

	public long calclateSum(final File pFile);
}
