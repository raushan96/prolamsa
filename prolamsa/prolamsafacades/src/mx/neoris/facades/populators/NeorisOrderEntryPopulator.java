/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.data.ProlamsaAPIProductConfigurationData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.converters.impl.AbstractConverter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.type.TypeService;

import javax.annotation.Resource;

import mx.neoris.core.model.ProlamsaAPIProductConfigurationModel;

import org.apache.commons.lang.StringUtils;




public class NeorisOrderEntryPopulator implements Populator<AbstractOrderEntryModel, OrderEntryData>
{
	public final static String SEPARATOR = " - ";
	
	@Resource(name = "productConverter")
	private AbstractConverter<ProductModel, ProductData> productConverter;
	
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	
	private TypeService typeService;
	
	
	
	/**
	 * @return the typeService
	 */
	public TypeService getTypeService() {
		return typeService;
	}



	/**
	 * @param typeService the typeService to set
	 */
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}



	@Override
	public void populate(final AbstractOrderEntryModel source, final OrderEntryData target) throws ConversionException
	{
		target.setRollingScheduleWeek(source.getRollingScheduleWeek());
		
		// NEORIS_CHANGE #74
		target.setPricePerFeet(source.getPricePerFeet());
		target.setPricePerTon(source.getPricePerTon());
		target.setPricePerPc(source.getPricePerPc());
		target.setNetPriceWOTaxes(source.getNetPriceWOTaxes());
		target.setReadyToShip(source.getReadyToShip());
		target.setIsLastOnTraspotation(source.getIsLastOnTraspotation());
		target.setIsTransportationGroupFull(source.getIsTransportationGroupFull());
		target.setGroupNumber(source.getGroupNumber());
		target.setSapEntryNumber(source.getSapEntryNumber());
		target.setConvertedQuantity(source.getConvertedQuantity());
		
		
		if  (source.getStatus() != null){
			//target.setStatus(enumerationService.getEnumerationName(source.getStatus()));
			target.setStatus(source.getStatus().getCode());
		}  else {
			target.setStatus("-");
		}
		
		
		if (source.getProduct() !=  null)
		{
			target.setProduct(productConverter.convert(source.getProduct()));
		}
		if (source.getApiProductConfiguration()!= null)
		{
			ProlamsaAPIProductConfigurationModel configModel = source.getApiProductConfiguration();

			ProlamsaAPIProductConfigurationData configData = new ProlamsaAPIProductConfigurationData();
			configData.setDuration(configModel.getDuration());
			configData.setLocationOfTest(configModel.getLocationOfTest());
			configData.setPressure(configModel.getPressure());
			configData.setSampleDirection(configModel.getSampleDirection());
			configData.setSampleSize(configModel.getSampleSize());
			configData.setSpecialDrifter(configModel.getSpecialDrifter());
			configData.setSpecificLength(configModel.getSpecificLength());
			configData.setSpecificStencil(configModel.getSpecificStencil());
			configData.setTestTemp(configModel.getTestTemp());
			
			target.setApiProductConfiguration(configData);

			StringBuilder productDescription = new StringBuilder();
			
			productDescription.append(source.getProduct().getName());
			productDescription.append(SEPARATOR);
			
			if (configModel.getPressure() != null)
			{
				productDescription.append(configModel.getPressure());
				productDescription.append(SEPARATOR);
			}
			
			if (configModel.getDuration() != null)
			{
				productDescription.append(configModel.getDuration());
				productDescription.append(SEPARATOR);
			}
			
			if (StringUtils.isNotBlank(configModel.getSpecificStencil()))
			{
				productDescription.append(configModel.getSpecificStencil());
				productDescription.append(SEPARATOR);
			}
			
			if (configModel.getSpecialDrifter() != null)
			{
				productDescription.append(configModel.getSpecialDrifter());
				productDescription.append(SEPARATOR);
			}
			
			if (configModel.getSpecificLength() != null)
			{
				productDescription.append(configModel.getSpecificLength());
				productDescription.append(SEPARATOR);
			}
			
			if (StringUtils.isNotBlank(configModel.getLocationOfTest()))
			{
				productDescription.append(configModel.getLocationOfTest());
				productDescription.append(SEPARATOR);
			}
			
			if (StringUtils.isNotBlank(configModel.getSampleDirection()))
			{
				productDescription.append(configModel.getSampleDirection());
				productDescription.append(SEPARATOR);
			}
			
			if (configModel.getTestTemp()!= null)
			{
				productDescription.append(configModel.getTestTemp());
				productDescription.append(SEPARATOR);
			}
			
			if (StringUtils.isNotBlank(configModel.getSampleSize()))
			{
				productDescription.append(configModel.getSampleSize());
				productDescription.append(SEPARATOR);
			}
			
			target.setProductDescription(productDescription.toString());
		}
		
		target.setIsAvailableToNegotiatePrice(source.getIsAvailableToNegotiatePrice());
		target.setNegotiablePrice(source.getNegotiablePrice());
		target.setIsQuantityInPieces(source.getIsQuantityInPieces());
	}
}

