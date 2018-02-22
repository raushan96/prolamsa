/**
 * 
 */
package mx.neoris.core.solrfacetsearch.provider.impl;

import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;


/**
 * @author fdeutsch
 * 
 */
public class NeorisModelPropertyEnumeratorProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider
{
	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;
	private FieldNameProvider fieldNameProvider;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model == null)
		{
			throw new IllegalArgumentException("No model given");
		}

		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		Collection<String> rangeNameList = null;
		Collection<String> fieldNames;

		Object value;

		if (indexedProperty.isLocalized())
		{
			final Collection<LanguageModel> languages = indexConfig.getLanguages();
			for (final LanguageModel language : languages)
			{
				value = null;
				final Locale locale = this.i18nService.getCurrentLocale();

				try
				{
					final Locale tmpLocale = this.localeService.getLocaleByString(language.getIsocode());
					this.i18nService.setCurrentLocale(tmpLocale);

					value = getPropertyValue(model, indexedProperty);

					try
					{
						final HybrisEnumValue enumValue = (HybrisEnumValue) value;
						value = enumerationService.getEnumerationName(enumValue, tmpLocale);
					}
					catch (final Exception ex)
					{
						final Collection<HybrisEnumValue> enumCol = (Collection<HybrisEnumValue>) value;

						final List<String> stringList = new ArrayList<String>();

						for (final HybrisEnumValue eachEnum : enumCol)
						{
							stringList.add(enumerationService.getEnumerationName(eachEnum, tmpLocale));
						}

						value = stringList;
					}

					rangeNameList = getRangeNameList(indexedProperty, value);
				}
				finally
				{
					this.i18nService.setCurrentLocale(locale);
				}
				fieldNames = fieldNameProvider.getFieldNames(indexedProperty, language.getIsocode());
				for (final String fieldName : fieldNames)
				{
					if (rangeNameList.isEmpty())
					{
						fieldValues.add(new FieldValue(fieldName, value));
					}
					else
					{
						for (final String rangeName : rangeNameList)
						{
							fieldValues.add(new FieldValue(fieldName, (rangeName == null) ? value : rangeName));
						}
					}
				}
			}
		}
		else
		{
			value = getPropertyValue(model, indexedProperty);
			rangeNameList = getRangeNameList(indexedProperty, value);
			fieldNames = this.fieldNameProvider.getFieldNames(indexedProperty, null);
			for (final String fieldName : fieldNames)
			{
				if (rangeNameList.isEmpty())
				{
					fieldValues.add(new FieldValue(fieldName, value));
				}
				else
				{
					for (final String rangeName : rangeNameList)
					{
						fieldValues.add(new FieldValue(fieldName, (rangeName == null) ? value : rangeName));
					}
				}
			}
		}
		return (fieldValues);
	}

	private Object getPropertyValue(final Object model, final IndexedProperty indexedProperty)
	{
		String qualifier = indexedProperty.getValueProviderParameter();

		if ((qualifier == null) || (qualifier.trim().isEmpty()))
		{
			qualifier = indexedProperty.getName();
		}

		Object result = null;

		result = this.modelService.getAttributeValue(model, qualifier);

		if ((result == null) && (model instanceof VariantProductModel))
		{
			final ProductModel baseProduct = ((VariantProductModel) model).getBaseProduct();
			result = this.modelService.getAttributeValue(baseProduct, qualifier);
		}

		return result;
	}

	/**
	 * @return the enumerationService
	 */
	public EnumerationService getEnumerationService()
	{
		return enumerationService;
	}

	/**
	 * @param enumerationService
	 *           the enumerationService to set
	 */
	public void setEnumerationService(final EnumerationService enumerationService)
	{
		this.enumerationService = enumerationService;
	}

	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	/**
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}
}
