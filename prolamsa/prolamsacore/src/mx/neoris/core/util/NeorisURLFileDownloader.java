/**
 * 
 */
package mx.neoris.core.util;

import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import mx.neoris.core.model.NeorisMediaModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


/**
 * @author fdeutsch
 * 
 */
public class NeorisURLFileDownloader
{
	protected static final Logger LOG = Logger.getLogger(NeorisURLFileDownloader.class);

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource
	private MediaService mediaService;

	@Resource(name = "b2bOrderService")
	private B2BOrderService b2bOrderService;

	private static final int BUFFER = 2048;
	private static final String DOCUMENT_ORDER_DOWNLOAD_ALL = "ORDER-ALL";

	public File downloadFileFromPath(final String filePath) throws Exception
	{
		final File tempFile = File.createTempFile("download", ".tmp");

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try
		{

			final String extractPath = configurationService.getConfiguration().getString("sap.invoices.temporaryDirectory.extract");
			final String newPath = extractPath + filePath.substring(filePath.lastIndexOf("\\") + 1);
			final File file = new File(newPath);


			inputStream = new FileInputStream(file);
			outputStream = new FileOutputStream(tempFile);

			IOUtils.copy(inputStream, outputStream);
		}
		finally
		{
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}

		return tempFile;
	}

	public File downloadAndZipFilesFromPath(final List<String> filePaths) throws Exception
	{
		final File zipTempFile = File.createTempFile("download", ".tmp");
		ZipOutputStream zipFileOut = null;

		try
		{
			zipFileOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipTempFile)));
			final byte data[] = new byte[BUFFER];
			BufferedInputStream origin = null;

			for (final String path : filePaths)
			{
				String fileName = null;
				File fileDownloaded = null;

				if (path.contains(DOCUMENT_ORDER_DOWNLOAD_ALL))
				{
					// for files attached to the order
					final String[] docInformation = path.split("_");

					final String orderCode = docInformation[0];

					final OrderModel order = b2bOrderService.getOrderForCode(orderCode);
					final List<NeorisMediaModel> attachments = order.getAttachmentsPO();

					if (CollectionUtils.isEmpty(attachments))
					{
						continue;
					}

					try
					{
						fileDownloaded = packageZipFromMediaList(attachments);
						fileName = orderCode + "-attachments.zip";
					}
					catch (final Exception e)
					{
						LOG.error("Error while downloading order attachments: " + orderCode);
						continue;
					}
				}
				else
				{
					try
					{
						// for files from SAP
						fileName = path.substring(path.lastIndexOf("\\") + 1);
						fileDownloaded = downloadFileFromPath(path);
					}
					catch (final Exception e)
					{
						LOG.error("Error while downloading document: " + fileName);
						continue;
					}
				}

				final FileInputStream fi = new FileInputStream(fileDownloaded);
				origin = new BufferedInputStream(fi, BUFFER);
				final ZipEntry zipEntry = new ZipEntry(fileName);
				zipFileOut.putNextEntry(zipEntry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1)
				{
					zipFileOut.write(data, 0, count);
				}
				origin.close();
			}
			zipFileOut.close();

		}
		finally
		{
			IOUtils.closeQuietly(zipFileOut);
		}

		return zipTempFile;
	}

	public File packageZipFromMediaList(final List<NeorisMediaModel> mediasToZip) throws Exception
	{
		final File zipTempFile = File.createTempFile("download", ".tmp");
		ZipOutputStream zipFileOut = null;

		try
		{
			zipFileOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipTempFile)));
			final byte data[] = new byte[BUFFER];
			BufferedInputStream origin = null;

			for (final NeorisMediaModel media : mediasToZip)
			{
				final InputStream inputStream = mediaService.getStreamFromMedia(media);
				origin = new BufferedInputStream(inputStream, BUFFER);
				final ZipEntry zipEntry = new ZipEntry(media.getRealFileName());
				zipFileOut.putNextEntry(zipEntry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1)
				{
					zipFileOut.write(data, 0, count);
				}
				inputStream.close();
				origin.close();
			}
			zipFileOut.close();
		}
		finally
		{
			IOUtils.closeQuietly(zipFileOut);
		}

		return zipTempFile;
	}
}
