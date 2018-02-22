/**
 * 
 */
package com.jenkov.io;

import java.util.HashMap;
import java.util.Map;


/**
 * @author fdeutsch
 * 
 */
public class MapTokenResolver implements ITokenResolver
{
	protected Map<String, String> tokenMap = new HashMap<String, String>();

	public MapTokenResolver(final Map<String, String> tokenMap)
	{
		this.tokenMap = tokenMap;
	}

	public String resolveToken(final String tokenName)
	{
		return this.tokenMap.get(tokenName);
	}
}
