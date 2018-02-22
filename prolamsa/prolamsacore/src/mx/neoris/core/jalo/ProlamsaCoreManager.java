/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import mx.neoris.core.constants.ProlamsaCoreConstants;
import mx.neoris.core.setup.CoreSystemSetup;

import org.apache.log4j.Logger;


/**
 * Don't use. User {@link CoreSystemSetup} instead.
 */
@SuppressWarnings("PMD")
public class ProlamsaCoreManager extends GeneratedProlamsaCoreManager
{
	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ProlamsaCoreManager.class.getName());

	public static final ProlamsaCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (ProlamsaCoreManager) em.getExtension(ProlamsaCoreConstants.EXTENSIONNAME);
	}
}
