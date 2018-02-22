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
package mx.neoris.storefront.controllers.misc;

import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.acceleratorservices.urlencoder.UrlEncoderService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import mx.neoris.storefront.controllers.AbstractController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import mx.neoris.facades.NeorisFacade;


/**
 * Controller for store session.
 */
@Controller
@Scope("tenant")
@RequestMapping("/_s")
public class StoreSessionController extends AbstractController
{
	private static final String REDIRECT_PREFIX = "redirect:";

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "urlEncoderService")
	private UrlEncoderService urlEncoderService;
	
	@Resource(name = "modelService")
	private ModelService modelService;
	
	@Resource(name = "neorisFacade")
	protected NeorisFacade neorisFacade;


	@RequestMapping(value = "/language", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String selectLanguage(@RequestParam("code") final String isoCode, final HttpServletRequest request)
	{
		final String previousLanguage = storeSessionFacade.getCurrentLanguage().getIsocode();
		storeSessionFacade.setCurrentLanguage(isoCode);
		userFacade.syncSessionLanguage();
		
		B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
		if(currentCustomerModel != null)
		{
			currentCustomerModel.setCustomerCurrentLenguaje(isoCode);
			modelService.save(currentCustomerModel);
		}
		
		return (urlEncoderService.isLanguageEncodingEnabled())?
				getReturnRedirectUrlForUrlEncoding(request,previousLanguage,storeSessionFacade.getCurrentLanguage().getIsocode()):
				getReturnRedirectUrl(request);
	}

	@RequestMapping(value = "/currency", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String selectCurrency(@RequestParam("code") final String isoCode, final HttpServletRequest request)
	{
		final String previousCurrency = storeSessionFacade.getCurrentCurrency().getIsocode();
		storeSessionFacade.setCurrentCurrency(isoCode);
		userFacade.syncSessionCurrency();
		return (urlEncoderService.isCurrencyEncodingEnabled())?
				getReturnRedirectUrlForUrlEncoding(request,previousCurrency,storeSessionFacade.getCurrentCurrency().getIsocode()):
				getReturnRedirectUrl(request);
	}

	protected String getReturnRedirectUrl(final HttpServletRequest request)
	{
		final String referer = request.getHeader("Referer");
		if (referer != null && !referer.isEmpty())
		{
			return REDIRECT_PREFIX + referer;
		}
		return REDIRECT_PREFIX + '/';
	}

	protected String getReturnRedirectUrlForUrlEncoding(final HttpServletRequest request, final String old, final String current)
	{
		final String referer = request.getHeader("Referer");
		if (referer != null && !referer.isEmpty() && StringUtils.contains(referer, "/"+old))
		{
			return REDIRECT_PREFIX + StringUtils.replace(referer, "/"+old, "/"+current);
		}
		return REDIRECT_PREFIX + '/';
	}

	@ExceptionHandler(UnknownIdentifierException.class)
	public String handleUnknownIdentifierException(final UnknownIdentifierException exception, final HttpServletRequest request)
	{
		RequestContextUtils.getOutputFlashMap(request).put("message", exception.getMessage());
		return REDIRECT_PREFIX + "/404";
	}
}
