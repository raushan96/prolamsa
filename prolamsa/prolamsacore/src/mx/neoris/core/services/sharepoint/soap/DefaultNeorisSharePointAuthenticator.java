package mx.neoris.core.services.sharepoint.soap;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;


public class DefaultNeorisSharePointAuthenticator extends Authenticator
{
	private final Properties properties;

	private static final String PROP_USERNAME_AUTHENTICATION_KEY = "prolamsa.sharepoint.credentials.username";
	private static final String PROP_PASSWORD_AUTHENTICATION_KEY = "prolamsa.sharepoint.credentials.password";

	public DefaultNeorisSharePointAuthenticator(final Properties props)
	{
		properties = props;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(properties.getProperty(PROP_USERNAME_AUTHENTICATION_KEY), properties.getProperty(
				PROP_PASSWORD_AUTHENTICATION_KEY).toCharArray());
	}
}
