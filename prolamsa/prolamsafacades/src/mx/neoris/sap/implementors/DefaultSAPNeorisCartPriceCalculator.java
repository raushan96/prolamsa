package mx.neoris.sap.implementors;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource; 

import mx.neoris.core.cart.NeorisCartPriceCalculator;
import mx.neoris.core.constants.NeorisServiceConstants;
import mx.neoris.core.constants.SAPConstants;
import mx.neoris.core.sap.SAPConnectionManager;
import mx.neoris.core.updaters.impl.NeorisEmailNotifierSAP;
import mx.neoris.core.util.SAPUtils;
import mx.neoris.facades.NeorisFacade;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoCustomDestination.UserData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


/**
 * @author fdeutsch, lacantu
 *
 */
public class DefaultSAPNeorisCartPriceCalculator implements NeorisCartPriceCalculator
{
	@Resource(name="SAPConnectionManager")
	SAPConnectionManager sapConnection;

	@Resource(name = "neorisFacade")
	private NeorisFacade neorisFacade;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;
	
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	
	@Resource(name = "sessionService")
	protected SessionService sessionService;
	
	@Resource(name = "neorisEmailNotifierSAP")
	private NeorisEmailNotifierSAP neorisEmailNotifierSAP;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;

	
	private static final Logger LOG = Logger.getLogger(DefaultSAPNeorisCartPriceCalculator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.facades.impl.NeorisCartPriceCalculator#calculateCart(de.hybris.platform.core.model.order.AbstractOrderModel
	 * )
	 */
	@Override
	public void calculatePrices(final AbstractOrderData orderData) throws Exception 
	{		
		UserModel currentUser = (UserModel) sessionService.getAttribute("user");
		String salesOrganization = neorisFacade.getCurrentBaseStore().getUid();
		String buildTo = neorisFacade.getRootUnit().getUid();
		String deliveryAddressCode = orderData.getDeliveryAddress().getCode();
		String transportationModeCode = orderData.getTransportationMode().getCode();
		String uom = neorisFacade.getCurrentUnit().getCode();
		StringBuilder emailDesc= new StringBuilder();
		String eol = "<br>";
		Double quantity = 0.00d;
		Integer daysAddedDeliveryDate = neorisFacade.getCurrentBaseStore().getDaysAddedDeliveryDate();
		
		String distributionChannel = configurationService.getConfiguration().getString("sap.parameter.distribution-channel");
		
		String incoterm = orderData.getTransportationMode().getIncotermCode();
			
		try
		{
			LOG.info("SAP CONNECTION: Initialize");
			
			JCoDestination sapDest = sapConnection.getDestination();
			
			//Se customiza el lenguaje del Destination cuando el sitio es MX 
			final JCoCustomDestination custDest = sapDest.createCustomDestination();
						
			if (baseStoreService.getCurrentBaseStore().getUid().equals("1000") || baseStoreService.getCurrentBaseStore().getUid().equals("5000"))
			{
				final UserData data = custDest.getUserLogonData();
				data.setLanguage("es");
			}
			
			JCoFunction sapFunc = sapConnection.createFunction(SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION, custDest);
			
			LOG.info("SAP CONNECTION: Successfull");
			
			// input header structure		
			JCoStructure orderHeader = sapFunc.getImportParameterList().getStructure(SAPConstants.RFC.PRICE_CALCULATE.INPUT_STRUCTURE);						
			
			LOG.info("RFC :" + SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION);
			LOG.info("--INPUT PARAMETERS--" );
			//emailDesc.append("RFC:" + SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION + eol);
			
			orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.BASE_STORE,salesOrganization);
			LOG.info(SAPConstants.RFC.PRICE_CALCULATE.BASE_STORE +": " + salesOrganization);
			emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.BASE_STORE +":</b> " + salesOrganization + eol);
					
			orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.DIST_CHANNEL,distributionChannel);
			LOG.info(SAPConstants.RFC.PRICE_CALCULATE.DIST_CHANNEL + ": " + distributionChannel);
			emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.DIST_CHANNEL + ":</b>"  + distributionChannel + eol);
					
			orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.CUSTOMER1,buildTo);
			LOG.info(SAPConstants.RFC.PRICE_CALCULATE.CUSTOMER1 + ": " + buildTo);
			emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.CUSTOMER1 + ":</b> " + buildTo +eol);
					
			orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.CUSTOMER2,deliveryAddressCode);
			LOG.info(SAPConstants.RFC.PRICE_CALCULATE.CUSTOMER2 + ": " + deliveryAddressCode);
			emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.CUSTOMER2 + ":</b> " + deliveryAddressCode +eol);
					
			//orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.REQUESTED_DATE,"UPDATEFLAG");
			//orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.REQUESTED_DATE,SAPUtils.convertSAPDate("2014/08/13")); 
			
			orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.TRANSPORTATION_MODE,transportationModeCode);
			LOG.info(SAPConstants.RFC.PRICE_CALCULATE.TRANSPORTATION_MODE + ": " + transportationModeCode);
			emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.TRANSPORTATION_MODE + ":</b> " + transportationModeCode + eol);
		
			orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.UOM,"PC");
			LOG.info(SAPConstants.RFC.PRICE_CALCULATE.UOM + ": PC");
			emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.UOM + ":</b> PC " + eol);
			
			orderHeader.setValue(SAPConstants.RFC.PRICE_CALCULATE.INTERCOM,incoterm); 
			LOG.info(SAPConstants.RFC.PRICE_CALCULATE.INTERCOM + ": " + incoterm);
			emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.INTERCOM + ":</b> " + incoterm + eol);
			

			// input entry table
			final JCoTable inputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.PRICE_CALCULATE.INPUT_TABLE2);
						
			for (OrderEntryData entry : orderData.getEntries())
			{
								
				inputTable.appendRow();
				
				emailDesc.append(eol + "<b>PRODUCT DETAIL:</b> " + eol);
							
				inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.PRODUCT_ID, entry.getProduct().getBaseCode());	
				LOG.info(SAPConstants.RFC.PRICE_CALCULATE.PRODUCT_ID + ": " + entry.getProduct().getBaseCode());
				emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.PRODUCT_ID + ":</b> " + entry.getProduct().getBaseCode() + eol);
								
				
				if(orderData.getHasAPIProducts())
				{
					inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.QUANTITY,entry.getQuantity());
					LOG.info(SAPConstants.RFC.PRICE_CALCULATE.QUANTITY + ": " +  entry.getQuantity());
					emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.QUANTITY + ":</b> " + entry.getQuantity() + eol);
					
					inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.UNIT_BASE, "FT");
					LOG.info(SAPConstants.RFC.PRICE_CALCULATE.UNIT_BASE + ": FT");
					emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.UNIT_BASE + ":</b> FT " + eol);
				}else
				{
					inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.QUANTITY, entry.getProduct().getPiecesPerBundle() * entry.getQuantity());
					LOG.info(SAPConstants.RFC.PRICE_CALCULATE.QUANTITY + ": " +  entry.getProduct().getPiecesPerBundle() * entry.getQuantity());
					emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.QUANTITY + ":</b> " + entry.getProduct().getPiecesPerBundle() * entry.getQuantity() + eol);
					
					inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.UNIT_BASE, "PC");
					LOG.info(SAPConstants.RFC.PRICE_CALCULATE.UNIT_BASE + ": PC");
					emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.UNIT_BASE + ":</b> PC " + eol);
				}
				
											
			
				
				inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.LOCATION, entry.getProduct().getLocation().getCode().substring(1));	
				LOG.info(SAPConstants.RFC.PRICE_CALCULATE.LOCATION + ": " + entry.getProduct().getLocation().getCode().substring(1));
				emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.LOCATION + ":</b> " + entry.getProduct().getLocation().getCode().substring(1) +eol);
								
				//validate date format
				//inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.ROLLING_SCHEDULE, entry.getRollingScheduleWeek());
				//inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.ROLLING_SCHEDULE, SAPUtils.convertMM_DD_YYYY("08/13/2014")); 
				//validate line item
				inputTable.setValue(SAPConstants.RFC.PRICE_CALCULATE.LINE_ITEM, (entry.getEntryNumber() + 1)*10 );	
				LOG.info(SAPConstants.RFC.PRICE_CALCULATE.LINE_ITEM + ": " + (entry.getEntryNumber() + 1)*10);
				emailDesc.append("<b>" + SAPConstants.RFC.PRICE_CALCULATE.LINE_ITEM + ":</b> " + (entry.getEntryNumber() + 1)*10);
							
			}
			

			final String sessionActual = sessionService.getCurrentSession().getSessionId();
			
			//execute
			LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION, currentUser.getUid(),
					true, sessionActual));
	   	    sapConnection.executeFunction(sapFunc, custDest);
	   	    LOG.info(SAPUtils.getTimeStamExecutionRFC("SAPPERFLOG", SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION, currentUser.getUid(),
					false, sessionActual));
	   	    
			// output table
			final JCoTable outputTable = sapFunc.getTableParameterList().getTable(SAPConstants.RFC.PRICE_CALCULATE.OUTPUT_TABLE);
			
			//Enviar la tabla de salida vacia o enviar el campo XXX con valor definido para error
			//El valor de esta variable debe de llegar de SAP
			//String getPriceErrorVariable = "E";
			
			//if(getPriceErrorVariable.equalsIgnoreCase("E"))
			//{
			//	throw new Exception("XXR");
			//}
			
			if (outputTable.getNumRows() > 0)
			{
				//initialize totals price
				Double coldRolledTotalPrice = 0.00d;
				Double hotRolledTotalPrice = 0.00d;
				Double galvanizedTotalPrice = 0.00d;
				Double aluminizedTotalPrice = 0.00d;
				Double galvametalTotalPrice = 0.00d;
								
				//initialize totals Weight
				Double coldRolledTotalWeight = 0.00d;
				Double hotRolledTotalWeight = 0.00d;
				Double galvanizedTotalWeight = 0.00d;
				Double aluminizedTotalWeight = 0.00d;
				Double galvametalTotalWeight = 0.00d;
				
				Double totalWeightAll = 0.00d;
				Double totalPriceAll = 0.00d;
				Double subtotalPriceAll = 0.00d;
				
				Double totalAssurance= 0.00d;
				Double totalTaxas=0.00d;
				Double totalDeliveryCost=0.00d;
								
				for (int i = 0; i < outputTable.getNumRows();i++) 
				{
					LOG.info("Order entry #" + i);
					
					outputTable.setRow(i);
					OrderEntryData orderEntry = orderData.getEntries().get(i);	
					
					//30032016 Christian Loredo
					//Validación para que el precio no sea CERO
					Double precioOutput = 0.00d;
					Double totalOutput = 0.00d;
					Double pricePerTon = 0.00d;
					Double pricePerPiece = 0.00d;
					
					precioOutput = Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.PRICE_PER_FEET));
					totalOutput = Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.TOTAL_NET_PRICE));
					
					LOG.info("PRICE_PER_FEET " + precioOutput);
					LOG.info("TOTAL_NET_PRICE " + totalOutput);
					
					if(precioOutput == 0.00d || totalOutput == 0.00d)
					{
						String errorMsg = "Error al obtener los precios, SAP no asigno precio para el producto " + orderEntry.getProduct().getBaseCode();
						LOG.error(errorMsg);
						throw new Exception(errorMsg);
					}
					
					String pricePerTonString = outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.PRICE_PER_TON);
					
					if(pricePerTonString != null)
					{
						pricePerTon = Double.parseDouble(pricePerTonString);
						LOG.info("PRICE_PER_TON " + pricePerTon);
					}
					
					String pricePerPieceString = outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.PRICE_PER_PIECE);
					
					if(pricePerPieceString != null)
					{
						pricePerPiece = Double.parseDouble(pricePerPieceString);
						LOG.info("PRICE_PER_PIECE " + pricePerPiece);
					}
					
					//set the SAP price
					orderEntry.setPricePerFeet(precioOutput);
					orderEntry.setNetPriceWOTaxes(totalOutput);
					orderEntry.setPricePerTon(pricePerTon);					
					orderEntry.setPricePerPc(pricePerPiece);
					
					//Change to remove gruopind data and keep the order en in the entries
					//remove this in case the grouping item by transportation.
					orderEntry.setSapEntryNumber(Integer.parseInt(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.LINE_ITEM)));

                    //calculate ready to ship date
					Date readyToShipDate = new Date();

					if (orderEntry.getRollingScheduleWeek() != null)
						orderEntry.setReadyToShip(orderEntry.getRollingScheduleWeek());
					else
					{
						final Calendar c = Calendar.getInstance();
						c.setTime(readyToShipDate);
						c.add(Calendar.DATE, daysAddedDeliveryDate);
						readyToShipDate = c.getTime();
						
						orderEntry.setReadyToShip(readyToShipDate);
					}
					//<formDate:formFormatDate
					final BaseStoreModel baseStoreModel = baseStoreService.getCurrentBaseStore();
					if (baseStoreModel.getUid().equalsIgnoreCase("1000") || baseStoreModel.getUid().equalsIgnoreCase("5000"))
					{
						orderEntry.setFormattedReadyToShip(new SimpleDateFormat("dd/MMM/yyyy").format(readyToShipDate));
					}
					else
					{
						orderEntry.setFormattedReadyToShip(new SimpleDateFormat("MMM/dd/yyyy").format(readyToShipDate));
					}
					//orderEntry.setFormattedReadyToShip(new SimpleDateFormat("MM/dd/yyyy").format(readyToShipDate));

					final HybrisEnumValue enumValue = orderEntry.getProduct().getLocation();
					final String formattedLocation = enumerationService.getEnumerationName(enumValue, i18nService.getCurrentLocale());

					orderEntry.setFormattedLocation(formattedLocation);
					
					// get steel category
					String varCondition = outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.VARIANT_CONDITION);
					orderEntry.setSteelCategory(varCondition);
					
					// get taxes
					Double entryTax= Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.TAX));
					orderEntry.setTotalTaxas(entryTax);
					
					// get assurance
					Double entryAssurance = Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.ASSURANCE));
					orderEntry.setTotalAssurance(entryAssurance);
					
					// get delivery cost
					Double entryDeliveryCost = Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.DELIVERY_COST));
					orderEntry.setTotalDeliveryCost(entryDeliveryCost);
					
					// get weight
					//Double totalWeight = Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.TOTAL_WEIGHT));
					//orderEntry.setWeight(totalWeight);
					
					//Fase 9 CILS 07Dic2016
					Double totalWeight = 0.00d;
					if (baseStoreModel.getUid().equalsIgnoreCase("6000"))
					{
						totalWeight = Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.TOTAL_WEIGHT_TON));
						System.out.println("totalWeightTon " + totalWeight);
					}else
					{
						totalWeight = Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.TOTAL_WEIGHT));
						System.out.println("totalWeight " + totalWeight);
					}
					orderEntry.setWeight(totalWeight);
					
					// accumulate assurance, taxes and delivery cost for order data
					totalAssurance+=entryAssurance;
					totalTaxas+=entryTax;
					totalDeliveryCost+=entryDeliveryCost;
					
					Double totalNetPrice = Double.parseDouble(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.TOTAL_NET_PRICE));
															
					if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.COLD_ROLLED)){
						coldRolledTotalPrice += totalNetPrice;
						coldRolledTotalWeight += totalWeight;
					} else if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.HOT_ROLLED)){
						hotRolledTotalPrice += totalNetPrice;
						hotRolledTotalWeight += totalWeight;
					} else if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.ALUMINIZED)){
						aluminizedTotalPrice += totalNetPrice;
						aluminizedTotalWeight += totalWeight;
					} else if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.GALVANIZED)){
						galvanizedTotalPrice += totalNetPrice;
						galvanizedTotalWeight += totalWeight;
					} else if (varCondition.equalsIgnoreCase(NeorisServiceConstants.SteelType.GALVAMETAL)){
						galvametalTotalPrice += totalNetPrice;
						galvametalTotalWeight += totalWeight;
					}
					
					totalWeightAll = coldRolledTotalWeight + hotRolledTotalWeight + aluminizedTotalWeight +galvanizedTotalWeight+galvametalTotalWeight;
					totalPriceAll = coldRolledTotalPrice + hotRolledTotalPrice + aluminizedTotalPrice +galvanizedTotalPrice+galvametalTotalPrice+totalAssurance+totalDeliveryCost+totalTaxas;
					subtotalPriceAll =  coldRolledTotalPrice + hotRolledTotalPrice + aluminizedTotalPrice +galvanizedTotalPrice+galvametalTotalPrice+totalAssurance+totalDeliveryCost;
					
					BaseStoreModel currentBaseStore = neorisFacade.getCurrentBaseStore();
					if (currentBaseStore.getPriceNegotiationEnabled() != null
							&& currentBaseStore.getPriceNegotiationEnabled().booleanValue())
					{
						final String isPriceNegotiable = outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.IS_AVAILABLE_TO_NEGOTIATE_PRICE);
						LOG.info(SAPConstants.RFC.PRICE_CALCULATE.IS_AVAILABLE_TO_NEGOTIATE_PRICE + ": " + isPriceNegotiable);

						if (isPriceNegotiable != null && isPriceNegotiable.equals("1"))
						{
							orderEntry.setIsAvailableToNegotiatePrice(Boolean.TRUE);
							LOG.info("Entry #" + i + " is negotiable");
						}
						else if (isPriceNegotiable != null && isPriceNegotiable.equals("0"))
						{
							orderEntry.setIsAvailableToNegotiatePrice(Boolean.FALSE);	
							LOG.info("Entry #" + i + " is not negotiable");
						}
					}
				}
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////
				//set weights categories
				orderData.setHotRolledWeight(hotRolledTotalWeight); 
				orderData.setColdRolledWeight(coldRolledTotalWeight);
				orderData.setGalvanizedWeight(galvanizedTotalWeight);
				orderData.setAluminizedWeight(aluminizedTotalWeight);
				orderData.setGalvametalWeight(galvametalTotalWeight);
				orderData.setTotalWeight(totalWeightAll);

				//set prices categories
				orderData.setHotRolledPrice(hotRolledTotalPrice);
				orderData.setColdRolledPrice(coldRolledTotalPrice);
				orderData.setGalvanizedPrice(galvanizedTotalPrice);
				orderData.setGalvametalPrice(galvametalTotalPrice);
				orderData.setAluminizedPrice(aluminizedTotalPrice);
				orderData.setSapTotalPrice(totalPriceAll);
				orderData.setSapSubtotalPrice(subtotalPriceAll);
				
				//set taxas, delivery cost and assurance totals
				orderData.setTotalAssurance(totalAssurance);
				orderData.setTotalDeliveryCost(totalDeliveryCost);
				orderData.setTotalTaxas(totalTaxas);
				
				
				//set the SAP Weight Unit and Currency
				orderData.setSapLabelWeightUnit(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.LABEL_UNIT));
				orderData.setSapWeightUnit(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.WEIGHT_UNIT));
				orderData.setSapCurrency(outputTable.getString(SAPConstants.RFC.PRICE_CALCULATE.CURRENCY));
				/////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				sessionService.getCurrentSession().setAttribute("sapLabelWeightUnit", orderData.getSapLabelWeightUnit());
				sessionService.getCurrentSession().setAttribute("sapWeightUnit", orderData.getSapWeightUnit());
				sessionService.getCurrentSession().setAttribute("sapCurrency", orderData.getSapCurrency());
			}
			else
			{		  
			   LOG.error("Not found result data for Price Calculation in RFC: ZHSD_PRICE_CALCULATION");
    			    			   								
				final StringBuilder subjectData = new StringBuilder();
				final StringBuilder bodyData = new StringBuilder();

				//set the subject
				subjectData.append(neorisFacade.getCurrentBaseStore().getName() + " | ");
				subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CALCULATE_PRICE.SUBJECT + " ");
				subjectData.append(SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION + " | ");
				subjectData.append( neorisFacade.getCurrentUser().getName() + " | ");
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				
				subjectData.append( dateFormat.format(date) );
								
				//set the body
				bodyData.append("<b>Store name:</b> " + neorisFacade.getCurrentBaseStore().getName() + eol + eol);
				bodyData.append("<b>User:</b> " + neorisFacade.getCurrentUser().getName() + eol + eol);
				bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
				bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION + eol + eol);
				bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
				bodyData.append("<b>Description:</b> RFC Cart Calculation not found product prices" + eol + eol);
							
			   neorisEmailNotifierSAP.sendEmailMessageWith( 
					   subjectData.toString(), bodyData.toString());
		   }
		}
		catch (final Exception ex)
		{
			LOG.error("Error while executing RFC: ZHSD_PRICE_CALCULATION", ex); 
			final StringBuilder subjectData = new StringBuilder();
			final StringBuilder bodyData = new StringBuilder();

			//set the subject
			subjectData.append(neorisFacade.getCurrentBaseStore().getName() + " | ");
			subjectData.append(SAPConstants.RFC.NOTIFICATIONS.CALCULATE_PRICE.SUBJECT + " | ");
			subjectData.append("RFC error: " + SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION + " | ");
			subjectData.append( neorisFacade.getCurrentUser().getName() + " | ");
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			
			subjectData.append( dateFormat.format(date) );
							
			//set the body
			bodyData.append("<b>Store name:</b> " + neorisFacade.getCurrentBaseStore().getName() + eol + eol);
			bodyData.append("<b>User:</b> " + neorisFacade.getCurrentUser().getName() + eol + eol);
			bodyData.append("<b>Time:</b> " + dateFormat.format(date) + eol + eol);
			bodyData.append("<b>RFC:</b> " + SAPConstants.RFC.PRICE_CALCULATE.PRICE_CALCULATE_FUNCTION + eol + eol);
			bodyData.append("<b>Input parameters:</b> " + eol + emailDesc + eol + eol);
			bodyData.append("<b>Description:</b> RFC Error Price Calculation" + eol);
			bodyData.append(eol + ex.getMessage());
			
			 neorisEmailNotifierSAP.sendEmailMessageWith( 
					   subjectData.toString(), bodyData.toString());
		}
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public String getMessageWithDefaultContext(final String resourceKey)
	{
		return getMessageWithDefaultContext(resourceKey, null);
	}

	public String getMessageWithDefaultContext(final String resourceKey, final Object[] params)
	{
		String message = resourceKey;

		try
		{
			message = messageSource.getMessage(resourceKey, params, getI18nService().getCurrentLocale());
		}
		catch (final Exception ex)
		{
			// do not raise any error on messages not found
		}

		return message;
	}

	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService() {
		return i18nService;
	}

	/**
	 * @param i18nService the i18nService to set
	 */
	public void setI18nService(I18NService i18nService) {
		this.i18nService = i18nService;
	}
	
	

}
