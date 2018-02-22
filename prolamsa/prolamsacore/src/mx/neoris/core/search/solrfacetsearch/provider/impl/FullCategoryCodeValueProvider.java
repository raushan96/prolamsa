/**
 * 
 */
package mx.neoris.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;


/**
 * @author fdeutsch
 * 
 */
public class FullCategoryCodeValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String categoriesQualifier = "supercategories";
	private FieldNameProvider fieldNameProvider;

	@SuppressWarnings("unchecked")
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		Collection<CategoryModel> categories = null;
		Collection<FieldValue> fieldValues = Collections.emptyList();

		if (model instanceof VariantProductModel)
		{
			final ProductModel baseProduct = ((VariantProductModel) model).getBaseProduct();
			categories = (Collection<CategoryModel>) this.modelService.getAttributeValue(baseProduct, this.categoriesQualifier);
		}
		else
		{
			//			if (this.modelService.getAttributeValue(model, "baseCode").equals("300225"))
			//			{
			//				final Integer one = 1;
			//			}
			categories = (Collection<CategoryModel>) this.modelService.getAttributeValue(model, this.categoriesQualifier);
		}
		if ((categories != null) && (!(categories.isEmpty())))
		{
			fieldValues = new ArrayList<FieldValue>();
			final Iterator<CategoryModel> localIterator1 = categories.iterator();

			while ((localIterator1.hasNext()))
			{
				final CategoryModel category = localIterator1.next();
				fieldValues.addAll(createFieldValue(category, indexedProperty));
				for (final CategoryModel superCategory : category.getAllSupercategories())
				{
					fieldValues.addAll(createFieldValue(superCategory, indexedProperty));
				}
			}
		}

		return fieldValues;
	}

	private List<FieldValue> createFieldValue(final CategoryModel category, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		final Object value = getPropertyValue(category, "code");
		final Collection<String> fieldNames = this.fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
		return fieldValues;
	}

	private Object getPropertyValue(final Object model, final String propertyName)
	{
		return this.modelService.getAttributeValue(model, propertyName);
	}

	@Required
	public void setCategoriesQualifier(final String categoriesQualifier)
	{
		this.categoriesQualifier = categoriesQualifier;
	}


	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}
}
