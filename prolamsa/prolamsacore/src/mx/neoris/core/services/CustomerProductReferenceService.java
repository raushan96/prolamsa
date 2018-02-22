/**
 * Retrieve custom product reference related data for {@link CustomerProductReferenceService}. 
 */
package mx.neoris.core.services;


import de.hybris.platform.b2b.model.B2BUnitModel;

import java.util.List;
import java.util.Map;

import mx.neoris.core.enums.ProductLocation;
import mx.neoris.core.model.CustomerProductReferenceModel;
import mx.neoris.facades.backorder.data.BackorderDetailData;


/**
 * @author e-lacantu
 * 
 */
public interface CustomerProductReferenceService
{

	CustomerProductReferenceModel getWithProductCodeAndB2BUnit(String productCode, B2BUnitModel unit, String catalogId);

	Map<String, CustomerProductReferenceModel> getWithUnitCodesAndB2BUnit(List<String> codes, B2BUnitModel unit, String catalogId);

	List<String> getProlamsaSkuFromSku(List<String> codes, B2BUnitModel unit, String catalogId);

	Map<String, String> getClientUnitCodesAndB2BUnit(List<String> codes, B2BUnitModel unit, String catalogId);

	Map<String, CustomerProductReferenceModel> getCustomerProductReferenceFor(List<String> prolamsaProductCodes,
			B2BUnitModel unit, String catalogId, List<ProductLocation> location);

	String getProductCodeFromClientCode(String code, B2BUnitModel unit, String catalogId);

	Map<String, CustomerProductReferenceModel> getCustomerProductReferenceFor(B2BUnitModel unit, String catalogId);

	void injectCustomerProductReferencesOn(List<BackorderDetailData> backorderDetailData);

	CustomerProductReferenceModel getWithProductCodeLocationAndB2BUnit(String productCode, B2BUnitModel unit, String catalogId,
			String location);

	CustomerProductReferenceModel getCustomerProductReference(String productCode, B2BUnitModel unit, String catalogId,
			String location);

	CustomerProductReferenceModel getWithProductCodeLocationAndB2BUnitExcel(String productCode, B2BUnitModel unit,
			String catalogId, String location);



}
