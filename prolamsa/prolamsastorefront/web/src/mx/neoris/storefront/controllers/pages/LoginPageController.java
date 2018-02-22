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
package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.acceleratorfacades.device.impl.DefaultDeviceDetectionFacade;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;

import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.util.GlobalMessage;
import mx.neoris.storefront.controllers.util.GlobalMessages;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.batik.bridge.UserAgent;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.bitwalker.useragentutils.Version;


/**
 * Login Controller. Handles login and register for the account flow.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/login")
public class LoginPageController extends AbstractLoginPageController
{
	private static final Logger LOG = Logger.getLogger(LoginPageController.class.getName());
	
	@Resource(name = "httpSessionRequestCache")
	private HttpSessionRequestCache httpSessionRequestCache;

	public void setHttpSessionRequestCache(final HttpSessionRequestCache accHttpSessionRequestCache)
	{
		this.httpSessionRequestCache = accHttpSessionRequestCache;
	}


	@RequestMapping(method = RequestMethod.GET)
	public String doLogin(@RequestHeader(value = "referer", required = false) final String referer,
			@RequestParam(value = "error", defaultValue = "false") final boolean loginError, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final HttpSession session)
			throws CMSItemNotFoundException
	{
		if (!loginError)
		{
			storeReferer(referer, request, response);
		}
		
		boolean isBrowserCompatible = isBrowserCompatible(request);
		LOG.info("Browser compatible: " + isBrowserCompatible);
		
		if(!isBrowserCompatible)
		{
			GlobalMessages.addErrorMessage(model, "login.message.browser.notcompatible");
		}
		
		model.addAttribute("isBrowserCompatible", isBrowserCompatible);

		return getDefaultLoginPage(loginError, session, model);
	}
	
	protected boolean isBrowserCompatible(final HttpServletRequest request)
	{
		boolean isCompatible = true;
		
		String userAgentString = request.getHeader("user-agent");
		eu.bitwalker.useragentutils.UserAgent userAgent = eu.bitwalker.useragentutils.UserAgent.parseUserAgentString(userAgentString);
		
		eu.bitwalker.useragentutils.Version browserVersion = userAgent.getBrowserVersion();
		String browserName = userAgent.getBrowser().toString();
		
		LOG.info("Browser Name:" + browserName);
		LOG.info("Browser Version:" + browserVersion);
		
		eu.bitwalker.useragentutils.Version limitVersion = new Version("9.0", null, null);
		
		if(StringUtils.isEmpty(browserName)|| browserVersion == null || (browserName.contains("IE") && 
				browserVersion.compareTo(limitVersion) < 0))
		{
			isCompatible = false;
		}
		
		return isCompatible;
	}

	@Override
	protected String getLoginView()
	{
	    //NEORIS CHANGE  1-APRIL-14
		//return ControllerConstants.Views.Pages.Account.AccountLoginPage;
		return ControllerConstants.Views.Pages.Misc.LoginPageProlamsa;
	}

	@Override
	protected String getSuccessRedirect(final HttpServletRequest request, final HttpServletResponse response)
	{
		if (httpSessionRequestCache.getRequest(request, response) != null)
		{
			return httpSessionRequestCache.getRequest(request, response).getRedirectUrl();
		}

		return "/my-account";
	}

	@Override
	protected AbstractPageModel getLoginCmsPage() throws CMSItemNotFoundException
	{
	    //NEORIS CHANAGE  4-APRIL-14
		//return getContentPageForLabelOrId("loginProlamsa");
		return getContentPageForLabelOrId("login");
		
	}

	protected void storeReferer(final String referer, final HttpServletRequest request, final HttpServletResponse response)
	{
		if (StringUtils.isNotBlank(referer))
		{
			httpSessionRequestCache.saveRequest(request, response);
		}
	}
}
