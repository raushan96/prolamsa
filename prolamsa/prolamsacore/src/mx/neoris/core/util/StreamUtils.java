package mx.neoris.core.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Blob;

import org.apache.commons.codec.binary.Base64;


/**
 * Helper class with many useful simplified Stream operations
 * 
 */
public class StreamUtils
{
	/**
	 * Field BUFFER_SIZE.
	 */
	public static final Integer BUFFER_SIZE = 8192;

	/**
	 * Method createTemporaryFileFromInputStream.
	 * 
	 * Creates a temporary file with the contents from the InputStream
	 * 
	 * @param prefixName
	 *           String
	 * @param sufixName
	 *           String
	 * @param inputStream
	 *           InputStream
	 * @return File
	 * @throws Exception
	 */
	public static final File createTemporaryFileFromInputStream(final String prefixName, final String sufixName,
			final InputStream inputStream) throws Exception
	{
		final File tempFile = File.createTempFile(prefixName, sufixName);

		final FileOutputStream outStream = new FileOutputStream(tempFile);

		try
		{
			copyStream(inputStream, outStream, BUFFER_SIZE);
		}
		finally
		{
			if (outStream != null)
			{
				outStream.close();
			}
		}

		return tempFile;
	}

	/**
	 * Method copyStream.
	 * 
	 * Copies the contents from the InputStream to the OutputStream
	 * 
	 * @param in
	 *           InputStream
	 * @param out
	 *           OutputStream
	 * @param bufferSize
	 *           Integer
	 * @throws Exception
	 */
	public static final void copyStream(final InputStream in, final OutputStream out, final Integer bufferSize) throws Exception
	{
		int length = 0;
		final byte[] buffer = new byte[bufferSize == null ? BUFFER_SIZE : bufferSize];

		while ((length = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, length);
		}
	}

	/**
	 * Method inputStreamFromString.
	 * 
	 * Creates an InputStream from the contents of the String
	 * 
	 * @param string
	 *           String
	 * @return InputStream
	 * @throws Exception
	 */
	public static final InputStream inputStreamFromString(final String string) throws Exception
	{
		return new ByteArrayInputStream(string.getBytes());
	}

	/**
	 * Method copyStringToStream.
	 * 
	 * Copies the String to the OutputStream
	 * 
	 * @param string
	 *           String
	 * @param out
	 *           OutputStream
	 * @param bufferSize
	 *           Integer
	 * @throws Exception
	 */
	public static final void copyStringToStream(final String string, final OutputStream out, final Integer bufferSize)
			throws Exception
	{
		copyStream(inputStreamFromString(string), out, bufferSize);
	}

	/**
	 * Method getStringFromStream.
	 * 
	 * Returns a String from the contents of the InputStream
	 * 
	 * @param stream
	 *           InputStream
	 * @return String
	 * @throws Exception
	 */
	public static final String getStringFromStream(final InputStream stream) throws Exception
	{
		final char[] buffer = new char[8192];

		final StringBuilder out = new StringBuilder();

		final Reader in = new InputStreamReader(stream, "UTF-8");

		for (;;)
		{
			final int rsz = in.read(buffer, 0, buffer.length);
			if (rsz < 0)
			{
				break;
			}
			out.append(buffer, 0, rsz);
		}

		in.close();

		return out.toString();
	}

	/**
	 * Method getStringBase64FromBlob.
	 * 
	 * Returns a Base64 String from a Java SQL Blob object
	 * 
	 * @param blobImage
	 *           Blob
	 * @return String
	 * @throws Exception
	 */
	public static final String getStringBase64FromBlob(final Blob blobImage) throws Exception
	{
		final byte[] encoded = Base64.encodeBase64(blobImage.getBytes(1, (int) blobImage.length()));

		return new String(encoded);
	}
}
