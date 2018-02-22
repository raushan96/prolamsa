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
package mx.neoris.storefront.security;

import de.hybris.platform.acceleratorservices.enums.UiExperienceLevel;
import de.hybris.platform.acceleratorservices.uiexperience.UiExperienceService;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.product.UnitService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;
import mx.neoris.core.model.ProlamsaProductModel;
import mx.neoris.core.product.ProductInventoryEntry;
import mx.neoris.core.uom.NeorisUoMQuantityConverter;
import mx.neoris.facades.NeorisFacade;
import mx.neoris.facades.NeorisProductFacade;
import mx.neoris.storefront.constants.WebConstants;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.google.common.collect.Lists;

/**
 * Success handler initializing user settings and ensuring the cart is handled correctly
 */
public class StorefrontAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
	private static final Logger LOG = Logger.getLogger(StorefrontAuthenticationSuccessHandler.class);
	
	private CustomerFacade customerFacade;
	private UiExperienceService uiExperienceService;
	private CartFacade cartFacade;
	private SessionService sessionService;
    private BruteForceAttackCounter bruteForceAttackCounter;
    
	@Resource(name = "modelService")
	private ModelService modelService;
    
    @Resource(name="baseStoreService")
    private BaseStoreService baseStoreService; 
    
    @Resource(name="neorisFacade")
    private NeorisFacade neorisFacade; 
    
    @Resource(name = "unitService")
	private UnitService unitService;

	@Resource(name="neorisUoMQuantityConverter")
	private NeorisUoMQuantityConverter neorisUoMQuantityConverter;
	
	@Resource(name = "neorisProductConverter")
	private AbstractConverter<ProductModel, ProductData> neorisProductConverter;
	
	@Resource(name = "neorisProductFacade")
	private NeorisProductFacade neorisProductFacade;

	private static final String PROLAMSA_INTERNAL_GROUP = "prolamsa_internal";
	
	private Map<UiExperienceLevel, Boolean> forceDefaultTargetForUiExperienceLevel;

	public UiExperienceService getUiExperienceService()
	{
		return uiExperienceService;
	}

	@Required
	public void setUiExperienceService(final UiExperienceService uiExperienceService)
	{
		this.uiExperienceService = uiExperienceService;
	}

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException
	{
		getCustomerFacade().loginSuccess();

		if (!getCartFacade().hasSessionCart() || getCartFacade().getSessionCart().getEntries().isEmpty())
		{
			try
			{
				getSessionService().setAttribute(WebConstants.CART_RESTORATION, getCartFacade().restoreSavedCart(null));
			}
			catch (final CommerceCartRestorationException e)
			{
				getSessionService().setAttribute(WebConstants.CART_RESTORATION, "basket.restoration.errorMsg");
			}
		}
		
		CartModel currentCartModel = neorisFacade.getCurrentCart();
		String uidRootUnit = null;
		boolean isb2bUnitValid = false;
		
		if (currentCartModel != null && currentCartModel.getUnitOwner() != null) 
		{
			try 
			{
			// Resolve b2b owner of the cart
			uidRootUnit = currentCartModel.getUnitOwner().getUid();
			isb2bUnitValid = neorisFacade.isValidB2bUnitUIDInStore(
					baseStoreService.getCurrentBaseStore(), uidRootUnit);
			}
			catch (Exception e) 
			{
				LOG.error("Error get uidRootUnit: " + e.getMessage());
			}
			
			if (!isb2bUnitValid)
			{
				uidRootUnit = null;
				// since b2bunit is not valid for cart... cleaning up cart
				try 
				{
					neorisFacade.emptyCartFromSession(currentCartModel);
				} 
				catch (Exception e) 
				{
					LOG.error("Erro while cleaning up cart: " + e.getMessage());
				}
			}
		}
		
		if (uidRootUnit == null) 
		{
			try
			{
			// Since cart doesn't have b2b owner... setting b2b unit default
			B2BUnitData unitData = neorisFacade.getB2BUnitsForStore(
					baseStoreService.getCurrentBaseStore()).get(0);
			uidRootUnit = unitData.getUid();
			}
			catch (Exception e) 
			{
				LOG.error("Error while searching Unit: " + e.getMessage());
			}
		}
		
		neorisFacade.setRootUnitWithId(uidRootUnit);
		
		
		if(neorisFacade.getCurrentCustomerType().equalsIgnoreCase("prolamsa_no_inventory"))
			validateRollingSchedules(currentCartModel);
		//else
		//	validateCartEntries(currentCartModel);

        getBruteForceAttackCounter().resetUserCounter(getCustomerFacade().getCurrentCustomer().getUid());
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	private void validateRollingSchedules(CartModel cartModel) {
		UnitModel currentUnit = neorisFacade.getCurrentUnit();
		
		// add wishlist entries to current cart
		if(cartModel.getEntries()!= null && cartModel.getEntries().size()>0)
		{
			for (AbstractOrderEntryModel eachCartEntry : cartModel.getEntries())
			{
				ProductModel product = eachCartEntry.getProduct();
	
				
				
	
				if (product instanceof ProlamsaProductModel)
				{
					
					Date rollingScheduleDate = eachCartEntry.getRollingScheduleWeek();
					
					if (rollingScheduleDate != null)
					{
						eachCartEntry.setRollingScheduleWeek(null);
					}
					
					modelService.save(eachCartEntry);
				}
				
			}
		}
	}

	protected void validateCartEntries(CartModel cartModel)
	{
		UnitModel currentUnit = neorisFacade.getCurrentUnit();
		
		// add wishlist entries to current cart
		if(cartModel.getEntries()!= null && cartModel.getEntries().size()>0)
		{
			try 
			{
			for (AbstractOrderEntryModel eachCartEntry : cartModel.getEntries())
			{
				ProductModel product = eachCartEntry.getProduct();
	
				
				// Bundles o pieces quntity
				Long quantity = Long.valueOf(eachCartEntry.getQuantity());
				
	
				if (product instanceof ProlamsaProductModel)
				{
					ProlamsaProductModel prolamsaProduct = (ProlamsaProductModel) product;
	
					ProductLocation productLocation = prolamsaProduct.getLocation();
					Date rollingScheduleDate = eachCartEntry.getRollingScheduleWeek();
					ProlamsaAPIProductConfigurationModel config = eachCartEntry.getApiProductConfiguration();
					
					// get product data
					ProductData productData = neorisProductConverter.convert(prolamsaProduct);
					
					Double convertedQuantity = neorisUoMQuantityConverter.convertOutputQuantityFrom(currentUnit, quantity, productData);
					neorisProductFacade.injectProductInventoryEntriesOn(Lists.newArrayList(productData), neorisFacade.getCurrentCustomerType());
					
					neorisUoMQuantityConverter.injectInventoryConvertedQuantitiesOn(currentUnit, Lists.newArrayList(productData));
					
					// if rolling schedule defined, only add the product to the cart
					// if rolling schedule date exists on the actual inventory
					// information
					
					ProductInventoryEntry eachInventoryEntry = productData.getInventoryEntry();
					
					if (rollingScheduleDate != null)
					{
						// if wishlist entry location matches
						if (productLocation.equals(eachInventoryEntry.getLocation()))
						{
							if(eachInventoryEntry.getRollingScheduleDates() == null 
									|| eachInventoryEntry.getRollingScheduleDates().isEmpty())
							{
								cartFacade.updateCartEntry(eachCartEntry.getEntryNumber(), 0);continue;
							}else if (!eachInventoryEntry.getRollingScheduleDates().contains(rollingScheduleDate))
							{
								
								eachCartEntry.setRollingScheduleWeek(eachInventoryEntry.getRollingScheduleDates().get(0));
							}
							
							List<Double> quantities = eachInventoryEntry.getRollingScheduleBundlesCol();
							if(!hasValidInventory(quantities))// if there is no valid quantiues ignore it
							{
								cartFacade.updateCartEntry(eachCartEntry.getEntryNumber(), 0);continue;
							}else if(!quantities.contains(convertedQuantity))
							{
								eachCartEntry.setQuantity(neorisUoMQuantityConverter.convertInputQuantityFrom(currentUnit, quantities.get(quantities.size()-1), productData));
							}
							
						}
					}
					else
					{
						
						if (productLocation.equals(eachInventoryEntry.getLocation()))
						{
							List <Double> quantities;
							if (PROLAMSA_INTERNAL_GROUP.equalsIgnoreCase(neorisFacade.getCurrentCustomerType()))
								quantities= eachInventoryEntry.getAvailableStockBundlesColInternal();
							else
								quantities= eachInventoryEntry.getAvailableStockBundlesCol();
							
							if(hasValidInventory(quantities))// there is valid inventory
							{
								if(!quantities.contains(convertedQuantity))// if there is not enough inventory adjust it to the max!
								{
									eachCartEntry.setQuantity(neorisUoMQuantityConverter.convertInputQuantityFrom(currentUnit, quantities.get(quantities.size()-1), productData));
								}
								
							}else // there is no stock then create rolling schedule
							{
								if(eachInventoryEntry.getRollingScheduleDates() == null 
										|| eachInventoryEntry.getRollingScheduleDates().isEmpty())// if there is not rolling ignore the entry
								{
									cartFacade.updateCartEntry(eachCartEntry.getEntryNumber(), 0);continue;
								}else
								{
									eachCartEntry.setRollingScheduleWeek(eachInventoryEntry.getRollingScheduleDates().get(0));
									quantities = eachInventoryEntry.getRollingScheduleBundlesCol();
									if(hasValidInventory(quantities))
									{
										if(!quantities.contains(convertedQuantity))
										{
											eachCartEntry.setQuantity(neorisUoMQuantityConverter.convertInputQuantityFrom(currentUnit, quantities.get(quantities.size()-1), productData));
										}
									}
									else
									{
										cartFacade.updateCartEntry(eachCartEntry.getEntryNumber(), 0);continue;
									}
								}
							}
							
						}
						
					}
					if(eachCartEntry.getQuantity()!=0)
						eachCartEntry.setConvertedQuantity(neorisUoMQuantityConverter.convertOutputQuantityFrom(currentUnit, eachCartEntry.getQuantity(), productData));
					
					modelService.save(eachCartEntry);
				}
				
			}
			
				cartFacade.validateCartData();
			} catch (CommerceCartModificationException e) 
			{
				// YTODO Auto-generated catch block
				LOG.error("There was an error when validating restorated cart on success login");
				cartFacade.cleanSavedCart();
			}
		}
	}
	
	protected boolean hasValidInventory(List<Double> inventory)
	{
		if(inventory == null)
			return false;
		
		if(inventory.size()==0)
			return false;
		
		if(inventory.size() == 1 && inventory.get(0)==0.0)
			return false;
		
		return true;
	}

	protected CartFacade getCartFacade()
	{
		return cartFacade;
	}

	@Required
	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

	protected SessionService getSessionService()
	{
		return sessionService;
	}

	@Required
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	protected CustomerFacade getCustomerFacade()
	{
		return customerFacade;
	}

	@Required
	public void setCustomerFacade(final CustomerFacade customerFacade)
	{
		this.customerFacade = customerFacade;
	}

	/*
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler#
	 * isAlwaysUseDefaultTargetUrl()
	 */
	@Override
	protected boolean isAlwaysUseDefaultTargetUrl()
	{
		final UiExperienceLevel uiExperienceLevel = getUiExperienceService().getUiExperienceLevel();
		if (getForceDefaultTargetForUiExperienceLevel().containsKey(uiExperienceLevel))
		{
			return Boolean.TRUE.equals(getForceDefaultTargetForUiExperienceLevel().get(uiExperienceLevel));
		}
		else
		{
			return false;
		}
	}

	protected Map<UiExperienceLevel, Boolean> getForceDefaultTargetForUiExperienceLevel()
	{
		return forceDefaultTargetForUiExperienceLevel;
	}

	@Required
	public void setForceDefaultTargetForUiExperienceLevel(
			final Map<UiExperienceLevel, Boolean> forceDefaultTargetForUiExperienceLevel)
	{
		this.forceDefaultTargetForUiExperienceLevel = forceDefaultTargetForUiExperienceLevel;
	}


    protected BruteForceAttackCounter getBruteForceAttackCounter()
    {
        return bruteForceAttackCounter;
    }
    @Required
    public void setBruteForceAttackCounter(BruteForceAttackCounter bruteForceAttackCounter)
    {
        this.bruteForceAttackCounter = bruteForceAttackCounter;
    }
}
