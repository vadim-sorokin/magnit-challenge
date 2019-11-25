package com.andersenbel.magnit.challenge.service.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.andersenbel.magnit.challenge.service.file.FileService;

public class FileServiceImpl implements FileService, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void writeToFile(final File pFile, byte[] pBytes) throws IOException {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(pFile);
			outputStream.write(pBytes);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	@Override
	public byte[] readFile(final File pFile) throws IOException {
		byte[] result = null;

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(pFile);
			result = inputStream.readAllBytes();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return result;
	}
}
