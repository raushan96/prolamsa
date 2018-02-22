/**
 * 
 */
package mx.neoris.core.services.sharepoint.soap;

import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.URL;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.microsoft.schemas.sharepoint.soap.Lists;
import com.microsoft.schemas.sharepoint.soap.ListsSoap;


/**
 * @author hector.castaneda
 * 
 */
public class NeorisSharePointListWebServiceManager
{
	private static final Logger LOG = Logger.getLogger(NeorisSharePointListWebServiceManager.class);

	private Properties authenticatorProperties;

	@Resource(name = "configurationService")
	public ConfigurationService configurationService;

	private static final String PROP_USERNAME_KEY = "prolamsa.sharepoint.credentials.username";
	private static final String PROP_PASSWORD_KEY = "prolamsa.sharepoint.credentials.password";

	public void init() throws Exception
	{
		authenticatorProperties = new Properties();
		authenticatorProperties.put(PROP_USERNAME_KEY, configurationService.getConfiguration().getString(PROP_USERNAME_KEY));
		authenticatorProperties.put(PROP_PASSWORD_KEY, configurationService.getConfiguration().getString(PROP_PASSWORD_KEY));

		final java.net.CookieManager cm = new java.net.CookieManager();
		java.net.CookieHandler.setDefault(cm);
		Authenticator.setDefault(new DefaultNeorisSharePointAuthenticator(authenticatorProperties));
	}

	public ListsSoap getListWebService(final String wsdlLocation, final String endpointAddress) throws Exception
	{
		final Lists service = new Lists(new URL(wsdlLocation), new QName("http://schemas.microsoft.com/sharepoint/soap/", "Lists"));

		final ListsSoap port = service.getListsSoap();
		final BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
				configurationService.getConfiguration().getString(PROP_USERNAME_KEY));
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				configurationService.getConfiguration().getString(PROP_PASSWORD_KEY));
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

		return port;
	}

	public byte[] readAll(final File file) throws IOException
	{
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try
		{
			final byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1)
			{
				ous.write(buffer, 0, read);
			}
		}
		finally
		{
			try
			{
				if (ous != null)
				{
					ous.close();
				}
			}
			finally
			{
				if (ios != null)
				{
					ios.close();
				}
			}
		}
		return ous.toByteArray();
	}

	public Node createSharePointCAMLNode(final String theXML) throws Exception
	{
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false);
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final Document document = documentBuilder.parse(new InputSource(new StringReader(theXML)));
		final Node node = document.getDocumentElement();
		return node;
	}
}
