package mx.neoris.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.servicelayer.config.ConfigurationService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mx.neoris.storefront.annotations.RequireHardLogIn;
import mx.neoris.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import mx.neoris.storefront.controllers.pages.AbstractPageController;
import mx.neoris.storefront.controllers.util.GlobalMessages;
import mx.neoris.storefront.forms.B2BUnitSearchForm;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
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

import de.hybris.platform.store.BaseStoreModel;

import com.greycon.reports.ArrayOfReportParameter;
import com.greycon.reports.AuthenticationContract;
import com.greycon.reports.ReportParameter;

@Controller
@Scope("tenant")
@RequestMapping(value = "/reports")
public class OptStudioReportsPageController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(OptStudioReportsPageController.class);
	
	private static final String WS_ORDER_REPORT_CMS_PAGE = "WsOrderReportPage";
	private static final String WS_ORDER_REPORT_V2_CMS_PAGE = "WsOrderReportV2Page";
	private static final String WS_ORDER_REPORT_RISK_CMS_PAGE = "WsRiskProductionScheduleReportPage";
	private static final String WS_ROLLING_SCHEDULE_CMS_PAGE = "WsRollingScheduleGeneralPage";
	
	private static final String WS_REPORTE_PEDIDOS_CMS_PAGE = "WsReportePedidosPage";
	private static final String WS_REPORTE_CALIBRES_CMS_PAGE = "WsReporteCalibresPage";
	private static final String WS_REPORTE_BLOCKS_CMS_PAGE = "WsReporteBlocksPage";
	
	private static final String PROLAMSA_ACCESS_ROLLING_SCHEDULE_REPORT_GRID_GROUP = "access_rolling_schedule_report_grid";
	
	private static final String GREYCON_OPTSTUDIO_AUTHENTICATION_USERNAME_PROP = "greycon.optstudio.authentication.userName";
	private static final String GREYCON_OPTSTUDIO_AUTHENTICATION_DOMAIN_PROP = "greycon.optstudio.authentication.domain";
	private static final String GREYCON_OPTSTUDIO_AUTHENTICATION_SYSTEMID_PROP = "greycon.optstudio.authentication.systemId";
	private static final String GREYCON_OPTSTUDIO_AUTHENTICATION_WORKAREAID_PROP = "greycon.optstudio.authentication.workareaId";
	private static final String GREYCON_OPTSTUDIO_AUTHENTICATION_CONNECTION_PROP = "greycon.optstudio.authentication.connection";
	
	private static final String GREYCON_OPTSTUDIO_ORDERSREPORT_NAME_PROP = "greycon.optstudio.ordersReport.name";
	private static final String GREYCON_OPTSTUDIO_PRODUCTSREPORT_NAME_PROP = "greycon.optstudio.productsReport.name";
	private static final String GREYCON_OPTSTUDIO_MACHINEBLOCKSREPORT_NAME_PROP = "greycon.optstudio.machineBlocksReport.name";
	private static final String GREYCON_OPTSTUDIO_MACHINERUNREPORT_NAME_PROP = "greycon.optstudio.machineRunReport.name";
	
	private static final String GREYCON_OPTSTUDIO_REPORTEPEDIDOS_NAME_PROP = "greycon.optstudio.reportePedidos.name";
	private static final String GREYCON_OPTSTUDIO_REPORTECALIBRES_NAME_PROP = "greycon.optstudio.reporteCalibres.name";	
	private static final String GREYCON_OPTSTUDIO_REPORTEBLOCKS_NAME_PROP = "greycon.optstudio.reporteBlocks.name";
	
	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder breadcrumbBuilder;
	
	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	
	@RequestMapping(value = "/wsOrderReport", method = RequestMethod.GET)
	@RequireHardLogIn
	public String wsOrderReport(Model model) throws CMSItemNotFoundException
	{
		String reportCode = "";
		
		
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		
		ArrayOfReportParameter params = null;
		AuthenticationContract authentication = getGreyconAuthenticationContract();
		String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_ORDERSREPORT_NAME_PROP+"."+baseStore.getUid());
		
		try
		{
			reportCode = getOptStudioReportWith(authentication, reportName, params);
		}
		catch (Exception ex)
		{
			LOG.error("error when executing WebService call: wsOrderReport", ex);
			GlobalMessages.addErrorMessage(model, "text.account.orderReport.error");
		}
		
		String reportResult = StringEscapeUtils.escapeXml(reportCode);
				
		model.addAttribute("code", reportResult);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(WS_ORDER_REPORT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WS_ORDER_REPORT_CMS_PAGE));
		
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.orderReport"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		prepareModelForGridReports(model);
		
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/wsOrderReportV2", method = RequestMethod.GET)
	@RequireHardLogIn
	public String wsOrderReportV2(Model model) throws CMSItemNotFoundException
	{
		String reportCode = "";
		
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		
		ArrayOfReportParameter params = null;
		AuthenticationContract authentication = getGreyconAuthenticationContract();
		String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_ORDERSREPORT_NAME_PROP+"."+baseStore.getUid());
		
		try
		{
			reportCode = getOptStudioReportWith(authentication, reportName, params);
		}
		catch (Exception ex)
		{
			LOG.error("error when executing WebService call: wsOrderReport", ex);
			GlobalMessages.addErrorMessage(model, "text.account.orderReportV2.error");
		}
		
		String reportResult = StringEscapeUtils.escapeXml(reportCode);
		
		model.addAttribute("code", reportResult);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(WS_ORDER_REPORT_V2_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WS_ORDER_REPORT_V2_CMS_PAGE));
		
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.orderReportV2"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		prepareModelForGridReports(model);
		
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/wsRiskProductionScheduleReport", method = RequestMethod.GET)
	@RequireHardLogIn
	public String wsRiskProductionScheduleReport(Model model) throws CMSItemNotFoundException
	{
		String reportCode = "";
		
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		
		ArrayOfReportParameter params = null;
		AuthenticationContract authentication = getGreyconAuthenticationContract();
		String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_ORDERSREPORT_NAME_PROP+"."+baseStore.getUid());
		
		try
		{
			reportCode = getOptStudioReportWith(authentication, reportName, params);
		}
		catch (Exception ex)
		{
			LOG.error("error when executing WebService call: wsRiskProductionScheduleReport", ex);
			GlobalMessages.addErrorMessage(model, "text.account.orderReportRisk.error");
		}
		
		String reportResult = StringEscapeUtils.escapeXml(reportCode);
		
		model.addAttribute("code", reportResult);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(WS_ORDER_REPORT_RISK_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WS_ORDER_REPORT_RISK_CMS_PAGE));
		
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.orderReportRisk"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		prepareModelForGridReports(model);
		
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/wsRollingScheduleGeneral", method = RequestMethod.GET)
	@RequireHardLogIn
	public String wsRollingScheduleGeneral(Model model) throws CMSItemNotFoundException
	{
		String reportCode = "";
		
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		
		ArrayOfReportParameter params = null;
		AuthenticationContract authentication = getGreyconAuthenticationContract();
		String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_MACHINEBLOCKSREPORT_NAME_PROP+"."+baseStore.getUid());
		
		try
		{
			reportCode = getOptStudioReportWith(authentication, reportName, params);
		}
		catch (Exception ex)
		{
			LOG.error("error when executing WebService call: wsRollingScheduleGeneral", ex);
			GlobalMessages.addErrorMessage(model, "text.account.rollingScheduleReport.error");
		}
		
		String reportResult = StringEscapeUtils.escapeXml(reportCode);
		
		model.addAttribute("code", reportResult);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(WS_ROLLING_SCHEDULE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WS_ROLLING_SCHEDULE_CMS_PAGE));
		
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.rollingScheduleReport"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		prepareModelForGridReports(model);
		
		return getViewForPage(model);
	}
	
	@RequestMapping(value = "/wsOrderReportWithParams.json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object getWsOrderReportWithParams(final String blockCode, final HttpServletRequest request,
			final HttpServletResponse response) 
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		String reportCode = "";
		
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		ArrayOfReportParameter params = new ArrayOfReportParameter();
		
		if (blockCode != null)
		{
			ReportParameter param = new ReportParameter();
			param.setParameter("BlockCodeParam");
			param.setValue(blockCode);
			params.getReportParameter().add(param);			
		}
		
		AuthenticationContract authentication = getGreyconAuthenticationContract();
		String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_ORDERSREPORT_NAME_PROP+"."+baseStore.getUid());
		
		try
		{
			reportCode = getOptStudioReportWith(authentication, reportName, params);
		}
		catch (Exception ex)
		{
			LOG.error("error when executing WebService call: wsOrderReportWithParams", ex);
			
			node.put("message", getMessageSource().getMessage("text.account.rollingScheduleReport.order.error", null, getI18nService().getCurrentLocale()));
			node.put("status", AJAX_STATUS_ERROR);
			
			return node;
		}
			
		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);
		node.put("result", reportCode);
		
		return node;
	}
	
	@RequestMapping(value = "/wsProductReportWithParams.json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object getWsProductReportWithParams(final String odIn,
			final HttpServletRequest request, final HttpServletResponse response)	
	{
		final ObjectNode node = JsonNodeFactory.instance.objectNode();
		String reportCode = "";
		
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		ArrayOfReportParameter params = new ArrayOfReportParameter();
		
		if (odIn != null) 
		{
			ReportParameter param = new ReportParameter();
			param.setParameter("ODInParam");
			param.setValue(odIn);
			params.getReportParameter().add(param);			
		}
		
		AuthenticationContract authentication = getGreyconAuthenticationContract();
		String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_PRODUCTSREPORT_NAME_PROP+"."+baseStore.getUid());
		
		try
		{
			reportCode = getOptStudioReportWith(authentication, reportName, params);
		}
		catch (Exception ex)
		{
			LOG.error("error when executing WebService call: wsProductReportWithParams", ex);
			
			node.put("message", getMessageSource().getMessage("text.account.rollingScheduleReport.product.error", null, getI18nService().getCurrentLocale()));
			node.put("status", AJAX_STATUS_ERROR);
			
			return node;
		}
		
		node.put("message", "OK");
		node.put("status", AJAX_STATUS_OK);
		node.put("result", reportCode);
		
		return node;
	}
	
	//Reporte de Pedidos
	@RequestMapping(value = "/wsReportePedidos", method = RequestMethod.GET)
	@RequireHardLogIn
	public String wsReportePedidos(Model model) throws CMSItemNotFoundException
	{
		String reportCode = "";
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		
		ArrayOfReportParameter params = null;
		AuthenticationContract authentication = getGreyconAuthenticationContractWitParam("1000");
		String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_REPORTEPEDIDOS_NAME_PROP+"."+baseStore.getUid());
		
		try
		{
			reportCode = getOptStudioReportWith(authentication, reportName, params);
		}
		catch (Exception ex)
		{
			LOG.error("error when executing WebService call: wsReportePedidos", ex);
			GlobalMessages.addErrorMessage(model, "text.account.ReportePedidos.error");
		}
		
		String reportResult = StringEscapeUtils.escapeXml(reportCode);
				
		model.addAttribute("code", reportResult);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(WS_REPORTE_PEDIDOS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WS_REPORTE_PEDIDOS_CMS_PAGE));
		
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.ReportePedidos"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		prepareModelForGridReports(model);
		
		return getViewForPage(model);
	}
	
	//Reporte de Calibres
	@RequestMapping(value = "/wsReporteCalibres", method = RequestMethod.GET)
	@RequireHardLogIn
	public String wsReporteCalibres(Model model) throws CMSItemNotFoundException
	{
		String reportCode = "";
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		
		ArrayOfReportParameter params = null;
		AuthenticationContract authentication = getGreyconAuthenticationContractWitParam("1000");
		String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_REPORTECALIBRES_NAME_PROP+"."+baseStore.getUid());
		
		try
		{
			reportCode = getOptStudioReportWith(authentication, reportName, params);
		}
		catch (Exception ex)
		{
			LOG.error("error when executing WebService call: wsReporteCalibres", ex);
			GlobalMessages.addErrorMessage(model, "text.account.ReporteCalibres.error");
		}
		
		String reportResult = StringEscapeUtils.escapeXml(reportCode);
				
		model.addAttribute("code", reportResult);
		
		storeCmsPageInModel(model, getContentPageForLabelOrId(WS_REPORTE_CALIBRES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WS_REPORTE_CALIBRES_CMS_PAGE));
		
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.ReporteCalibres"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		
		prepareModelForGridReports(model);
		
		return getViewForPage(model);
	}
	
	//Reporte de Blocks
		@RequestMapping(value = "/wsReporteBlocks", method = RequestMethod.GET)
		@RequireHardLogIn
		public String wsReporteBlocks(Model model) throws CMSItemNotFoundException
		{
			String reportCode = "";
			BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
			
			ArrayOfReportParameter params = null;
			AuthenticationContract authentication = getGreyconAuthenticationContractWitParam("1000");
			String reportName = configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_REPORTEBLOCKS_NAME_PROP+"."+baseStore.getUid());
			
			try
			{
				reportCode = getOptStudioReportWith(authentication, reportName, params);
			}
			catch (Exception ex)
			{
				LOG.error("error when executing WebService call: wsReporteBlocks", ex);
				GlobalMessages.addErrorMessage(model, "text.account.ReporteBlocks.error");
			}
			
			String reportResult = StringEscapeUtils.escapeXml(reportCode);
					
			model.addAttribute("code", reportResult);
			
			storeCmsPageInModel(model, getContentPageForLabelOrId(WS_REPORTE_BLOCKS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WS_REPORTE_BLOCKS_CMS_PAGE));
			
			model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.ReporteBlocks"));
			model.addAttribute("metaRobots", "no-index,no-follow");
			
			prepareModelForGridReports(model);
			
			return getViewForPage(model);
		}
	
	
	private String getOptStudioReportWith(
			final AuthenticationContract authentication,
			final String reportName, final ArrayOfReportParameter params)
			throws Exception	
	{
		String reportCode = "";
		reportCode = neorisFacade.getOptStudioReportInformation(authentication, reportName, params);
		String reportResult = removeUTF8BOM(reportCode);
		
		return reportResult;		
	}
	
	private AuthenticationContract getGreyconAuthenticationContract()
	{
		AuthenticationContract authentication = new AuthenticationContract();
		
		BaseStoreModel baseStore = neorisFacade.getCurrentBaseStore();
		
		authentication.setUserName(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_USERNAME_PROP+"."+baseStore.getUid()));
		authentication.setDomain(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_DOMAIN_PROP+"."+baseStore.getUid()));
		authentication.setSystemId(Integer.parseInt(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_SYSTEMID_PROP+"."+baseStore.getUid())));
		authentication.setWorkareaId(Integer.parseInt(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_WORKAREAID_PROP+"."+baseStore.getUid())));
		authentication.setConnection(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_CONNECTION_PROP+"."+baseStore.getUid()));
		
		return authentication;
	}
	
	private AuthenticationContract getGreyconAuthenticationContractWitParam(String id)
	{
		AuthenticationContract authentication = new AuthenticationContract();
		
		authentication.setUserName(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_USERNAME_PROP+"."+id));
		authentication.setDomain(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_DOMAIN_PROP+"."+id));
		authentication.setSystemId(Integer.parseInt(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_SYSTEMID_PROP+"."+id)));
		authentication.setWorkareaId(Integer.parseInt(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_WORKAREAID_PROP+"."+id)));
		authentication.setConnection(configurationService.getConfiguration().getString(GREYCON_OPTSTUDIO_AUTHENTICATION_CONNECTION_PROP+"."+id));
		
		return authentication;
	}
	
	private void prepareModelForGridReports(final Model model)
	{
		model.addAttribute("activateJQGridFronEndLibraries", true);
	}
	
	private String removeUTF8BOM(String s) 
	{
		if (s.startsWith("\uFEFF")) 
		{
			s = s.substring(1);
		}
		return s;
	}
}
