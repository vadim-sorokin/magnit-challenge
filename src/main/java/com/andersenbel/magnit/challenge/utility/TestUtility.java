package com.andersenbel.magnit.challenge.utility;

public final class TestUtility {
	private TestUtility() {

	}

	public static String createJDBCUrl(final String pRdbms, final String pSocket, final String pSchema,
			final String pUser, final String pPassword, final String pJdbcOptions) {
		final StringBuilder result = new StringBuilder();
		result.append("jdbc:");
		result.append(pRdbms);
		result.append("://");
		result.append(pSocket);
		result.append("/");
		result.append(pSchema);
		result.append("?user=");
		result.append(pUser);
		result.append("&password=");
		result.append(pPassword);
		result.append("&");
		result.append(pJdbcOptions);
		return result.toString();
	}
}
