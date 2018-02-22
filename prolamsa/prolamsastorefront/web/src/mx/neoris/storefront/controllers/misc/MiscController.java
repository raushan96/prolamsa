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

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import mx.neoris.core.enums.ProductLocation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;

import mx.neoris.core.enums.Wishlist2Type;
import mx.neoris.core.model.NeorisMediaModel;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.util.NeorisURLFileDownloader;
import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.ControllerConstants;
import mx.neoris.storefront.controllers.pages.AbstractPageController;
import mx.neoris.storefront.controllers.util.GlobalMessages;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greycon.reports.ArrayOfReportParameter;
import com.greycon.reports.AuthenticationContract;
import com.greycon.reports.IReportProvider;
import com.greycon.reports.ReportProvider;
import com.sap.conn.jco.JCoAttributes;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;

import de.hybris.platform.servicelayer.model.ModelService;

@Controller
@Scope("tenant")
@RequestMapping(value = "/misc")
public class MiscController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(MiscController.class);
	
	private static final String WS_ORDER_REPORT_CMS_PAGE = "WsOrderReportPage";
	private static final String WS_ORDER_REPORT_V2_CMS_PAGE = "WsOrderReportV2Page";
	private static final String WS_ORDER_REPORT_RISK_CMS_PAGE = "WsRiskProductionScheduleReportPage";
	private static final String WS_ROLLING_SCHEDULE_CMS_PAGE = "WsRollingScheduleGeneralPage";
	
	private static final String PROLAMSA_ACCESS_ROLLING_SCHEDULE_REPORT_GRID_GROUP = "access_rolling_schedule_report_grid";

	static final String SYSTEM_UNIT_SLOT = "systemUnit";
	static final String LOCATION_SLOT = "locationB2BUnit";

	@Resource(name = "SAPConnectionManager")
	SAPConnectionManager sapConnection;
	
	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder breadcrumbBuilder;
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	
	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	
	@Resource(name = "modelService")
	private ModelService modelService;

	@RequestMapping(value = "/testSap", method = RequestMethod.GET)
	@RequireHardLogIn
	public String testSAP(Model model)
	{
		model.addAttribute("pageTitle", "Test SAP call");

		String message = "";
		String code = "";

		try
		{
			JCoDestination destination = sapConnection.getDestination();

			JCoAttributes attribs = destination.getAttributes();
			code = attribs.toString();

			JCoFunction function = sapConnection.createFunction("BAPI_USER_GET_DETAIL", destination);

			function.getImportParameterList().setValue("USERNAME", "DHERNANDEZ");

			sapConnection.executeFunction(function, destination);

			StringBuilder builder = new StringBuilder();

			builder.append("BAPI_USER_GET_DETAIL");
			builder.append("\nADDRESS:");
			builder.append(function.getExportParameterList().getString("ADDRESS"));

			LOG.info("BAPI_USER_GET_DETAIL finished:");
			LOG.info(" ADDRESS: " + function.getExportParameterList().getString("ADDRESS"));

			final JCoStructure exportStructure = function.getExportParameterList().getStructure("ADDRESS");

			builder.append("\nSystem info for:");
			builder.append(destination.getAttributes().getSystemID());

			LOG.info("System info for " + destination.getAttributes().getSystemID() + ":\n");

			// The structure contains some fields. The loop just prints out each
			// field with its name.
			for (int i = 0; i < exportStructure.getMetaData().getFieldCount(); i++)
			{
				builder.append("\nSystem info for:");
				builder.append(exportStructure.getMetaData().getName(i));
				builder.append("\t");
				builder.append(exportStructure.getString(i));

				LOG.info(exportStructure.getMetaData().getName(i) + ":\t" + exportStructure.getString(i));
			}

			message = "SAP call successfull";
			code = builder.toString();
		}
		catch (Exception ex)
		{
			LOG.error("error when executing SAP function", ex);

			message = "SAP call failed";
			code = ExceptionUtils.getStackTrace(ex);
		}

		model.addAttribute("message", message);
		model.addAttribute("code", code);

		return "pages/misc/testPage";
	}

	@RequestMapping(value = "/testWebService1", method = RequestMethod.GET)
	@RequireHardLogIn
	public String testWebService1(Model model)
	{
		return "pages/misc/testPageWebService1";
	}

	// NEORIS_CHANGE #48
	@RequestMapping(value = "/setRootUnit.json", method = RequestMethod.POST)
	@RequireHardLogIn
	@ResponseBody
	public Object setRootUnit(@RequestParam(value = "rootUnit")
	final String rootUnit)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		// assign the new root unit
		final String result = neorisFacade.setRootUnitWithId(rootUnit);

		node.put("result", result);
		node.put("status", AJAX_STATUS_OK);

		// empty any existing cart information
		CartModel currentCart = (CartModel) sessionService.getAttribute("cart");

		if (currentCart != null)
		{
			try
			{
				neorisFacade.emptyCartFromSession(currentCart);
			}
			catch (Exception ex)
			{
				LOG.error("error while empty cart", ex);

				node.put("status", AJAX_STATUS_ERROR);
				node.put("message", ex.getMessage());
			}
		}

		return node;
	}

	// NEORIS_CHANGE #48
	@RequestMapping(value = "/setUnit.json", method = RequestMethod.POST)
	@RequireHardLogIn
	@ResponseBody
	public Object setUnit(@RequestParam(value = "unit")
	final String unit)
	{
		B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
		if(currentCustomerModel != null)
		{
			currentCustomerModel.setCustomerCurrentUMedida(unit);
			modelService.save(currentCustomerModel);
		}
		
		final String result = neorisFacade.setUnitWithId(unit);

		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		CartModel cart = neorisFacade.getCurrentCart();

		node.put("hasCart", (cart != null && cart.getEntries().size() > 0) ? "1" : "0");
		node.put("result", result);
		node.put("status", AJAX_STATUS_OK);

		return node;
	}

	
	@RequestMapping(value = "/setUnitBySystem.json", method = RequestMethod.POST, produces = "application/json")
	@RequireHardLogIn
	@ResponseBody
	public Object setUnitBySystem(@RequestParam(value = "unit")
	final String unit)
	{
		HybrisEnumValue enumValue = enumerationService.getEnumerationValue("MeasurementSystemType", unit);
		
		B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
		if(currentCustomerModel != null)
		{
			currentCustomerModel.setCustomerCurrentSMetrico(unit);
			modelService.save(currentCustomerModel);
		}
		
		sessionService.setAttribute(SYSTEM_UNIT_SLOT, enumValue);				

		List<UnitModel> listUnit = neorisFacade.getUnitBySystem(unit);
		
		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
				
        for (UnitModel unitLs : listUnit) {        	           
            ObjectNode system = JsonNodeFactory.instance.objectNode();
            system.put("name", unitLs.getName());
            system.put("code", unitLs.getCode());
			result.add(system);                                    
        }
       				
		node.put("status", AJAX_STATUS_OK);       
        node.put("result", result);

		return node;
	}

	
	// NEORIS_CHANGE #54
	@RequestMapping(value = "/emptyUsedCart", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object emptyUsedCart(final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", "Cart Empty");
		node.put("status", AJAX_STATUS_OK);

		try
		{
			CartModel currentCart = (CartModel) sessionService.getAttribute("cart");

			if (currentCart != null)
				neorisFacade.emptyCartFromSession(currentCart);
		}
		catch (Exception ex)
		{
			LOG.error("error while empty cart", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;

	}

	// //////////////////////////////////////////////

	@RequestMapping(value = "/ifEmptyCart", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Object ifEmptyCart(final HttpServletRequest request)
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();

		node.put("message", "Cart Empty");
		node.put("status", AJAX_STATUS_OK);

		try
		{
			CartModel cart = (CartModel) sessionService.getAttribute("cart");

			if (cart == null || cart.getEntries() == null || cart.getEntries().size() == 0)
			{
				throw new Exception(getMessageWithDefaultContext("draft-template.text.cart.empty"));
			}
		}
		catch (Exception ex)
		{
			LOG.error("error while empty cart", ex);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", ex.getMessage());
		}

		return node;

	}
	
	@RequestMapping(value = "/setLocation.json", method = RequestMethod.POST)
	//@RequestMapping(value = "/setLocation.json", method ={ RequestMethod.POST , RequestMethod.GET})
	@RequireHardLogIn
	@ResponseBody
	public Object setLocation(@RequestParam(value = "unit")	final String unit, final HttpServletRequest request,
			final HttpServletResponse response)
	{
		//
		List<HybrisEnumValue> listLocations = neorisFacade.getListLocation(unit);
		
		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		ArrayNode result = new ArrayNode(JsonNodeFactory.instance);
		
		if(listLocations.isEmpty())
		{
			String msg = getMessageWithDefaultContext("validate.visibilityClient") + ": " + unit; 
			LOG.error(msg);

			node.put("status", AJAX_STATUS_ERROR);
			node.put("message", msg);
			
			return node;
		}
		
		//Se obtiene el usuario
		B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
		
		//Se obtiene ka sessionId
		String sessionActual = sessionService.getCurrentSession().getSessionId();
		System.out.println("sessionActual: " +sessionActual);
		
		//Se declara Array String
		String[] splitter = new String[2];
		
		
		if(currentCustomerModel != null)
		{
		System.out.println("currentCustomerModel.getCurrentLocation(): " +currentCustomerModel.getCurrentLocation());
		//splitter = currentCustomerModel.getCurrentLocation().toString().split("-");
		}
		
		if(currentCustomerModel.getCurrentLocation() == null)
		{
			sessionService.setAttribute(LOCATION_SLOT, null);
		}else //if(sessionActual.equalsIgnoreCase(splitter[1].toString()))
		{
			splitter = currentCustomerModel.getCurrentLocation().toString().split("-");
			//if(splitter[1].equalsIgnoreCase(sessionActual))
			//{
				//sessionService.setAttribute(LOCATION_SLOT, enumerationService.getEnumerationValue("ProductLocation", currentCustomerModel.getCurrentLocation().toString()));
				sessionService.setAttribute(LOCATION_SLOT, enumerationService.getEnumerationValue("ProductLocation", splitter[0].toString()));
			//}else
			//{
			//	sessionService.setAttribute(LOCATION_SLOT, null);
			//}
		}
		
		if(!listLocations.contains(sessionService.getAttribute(LOCATION_SLOT)))
		{
			sessionService.setAttribute(LOCATION_SLOT, null);
		}	
		
		//HttpSession httpSession = request.getSession(); 
		//System.out.println("XXXXXXXXXXXXXXXXXXXX1 " + httpSession.getAttribute("locationCurrent"));
		
		//System.out.println("XXXXXXXXXXXXXXXXXXXX2 " + sessionService.getCurrentSession().getAttribute(LOCATION_SLOT));
		
		//System.out.println("XXXXXXXXXXXXXXXXXXXX3 " + request.getParameter("locationCurrentX"));
		
		/*
		if(!newlocation.equalsIgnoreCase("0"))
		{
			sessionService.setAttribute(LOCATION_SLOT, newlocation);
			//if(!listLocations.contains(newlocation))
			//enumerationService.getEnumerationValue("ProductLocation", newlocation.toString())
			if(!listLocations.contains(enumerationService.getEnumerationValue("ProductLocation", newlocation.toString())))
			{
				sessionService.setAttribute(LOCATION_SLOT, null);
			}
		}else
		{
			if(!listLocations.contains(sessionService.getAttribute(LOCATION_SLOT)))
			{
				sessionService.setAttribute(LOCATION_SLOT, null);
			}	
		}
		*/
		
		
		
		int i;
		for (i = 0 ; i< listLocations.size(); i++)
		{
						
			ObjectNode location = JsonNodeFactory.instance.objectNode();
			
			location.put("code", listLocations.get(i).getCode());
			location.put("name", getMessageWithDefaultContext("short.location."+(listLocations.get(i).getCode()).toString()));
			
			if(sessionService.getAttribute(LOCATION_SLOT) != null)
			{
				HybrisEnumValue locacionActual = enumerationService.getEnumerationValue("ProductLocation", sessionService.getAttribute(LOCATION_SLOT).toString());
				String locacionActualString = locacionActual.toString();
				location.put("selectedLocation", locacionActualString);
				//model.addAttribute("locationCombo", locacionActualString);
			}else
			{
				location.put("selectedLocation", listLocations.get(0).getCode());
			}	
			result.add(location);
		}
		
		if(sessionService.getAttribute(LOCATION_SLOT) == null)
		{
			sessionService.setAttribute(LOCATION_SLOT, listLocations.get(0).getCode());
			
			currentCustomerModel.setCurrentLocation(listLocations.get(0).getCode()+ "-" +sessionActual);
			modelService.save(currentCustomerModel);
			
			//model.addAttribute("locationCombo", listLocations.get(0).getCode());
		}else
		{
			currentCustomerModel.setCurrentLocation(sessionService.getAttribute(LOCATION_SLOT).toString()+ "-" +sessionActual);
			modelService.save(currentCustomerModel);
		}
		//System.out.println(sessionService.getAttribute(LOCATION_SLOT));
		
		node.put("status", AJAX_STATUS_OK);
		node.put("result", result);
		
		return node;
	}

	@RequestMapping(value = "/setNewLocation.json", method =
	{ RequestMethod.POST })
	@RequireHardLogIn
	@ResponseBody
	public Object setNewLocation(final String newlocation, final HttpServletRequest request,
			final HttpServletResponse response) 
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		
		HybrisEnumValue enumValue = enumerationService.getEnumerationValue("ProductLocation", newlocation.toString());
		sessionService.getCurrentSession().setAttribute(LOCATION_SLOT, enumValue);
		//System.out.println(enumValue);
		
		//HttpSession httpSession = request.getSession(); 
		//httpSession.setAttribute("locationCurrent",enumValue);
		
		//request.setAttribute("locationCurrentX",enumValue);
		String sessionActual = sessionService.getCurrentSession().getSessionId();
		System.out.println("sessionActual-setNewLocation: " +sessionActual);
		
		B2BCustomerModel currentCustomerModel = neorisFacade.getCurrentCustomer();
		if(currentCustomerModel != null)
		{
			currentCustomerModel.setCurrentLocation(enumValue.toString()+ "-" +sessionActual);
			modelService.save(currentCustomerModel);
		}
		
		node.put("status", AJAX_STATUS_OK);
		//node.put("newLocationSelected",enumValue.toString());
		//model.addAttribute("locationCombo", newlocation.toString());
		
		return node;
	}
}
