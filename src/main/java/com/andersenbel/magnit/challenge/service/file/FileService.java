package com.andersenbel.magnit.challenge.service.file;

import java.io.File;
import java.io.IOException;

public interface FileService {
	public void writeToFile(final File pFile, byte[] pBytes) throws IOException;

	public byte[] readFile(final File pFile) throws IOException;
}
