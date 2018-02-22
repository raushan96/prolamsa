/**
 * 
 */
package mx.neoris.core.uom;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.UnitModel;

import java.util.ArrayList;
import java.util.List;

import mx.neoris.core.product.ProductInventoryEntry;

import org.apache.log4j.Logger;


/**
 * @author fdeutsch
 * 
 */
public class DefaultNeorisUoMQuantityConverter implements NeorisUoMQuantityConverter
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.uom.NeorisUoMQuantityConverter#convertInputQuantityFrom(de.hybris.platform.core.model.product.UnitModel
	 * , de.hybris.platform.commercefacades.product.data.ProductData)
	 */
	private static final Logger LOG = Logger.getLogger(DefaultNeorisUoMQuantityConverter.class);

	@Override
	public Long convertInputQuantityFrom(final UnitModel uom, final double quantity, final ProductData productData)
	{
		if (productData.getIsAPI())
		{
			// Quantity for products API are represented in ft.
			LOG.info("API Product: " + productData.getBaseCode() + "... skipping quantity conversion.");
			return Math.round(quantity);
		}

		try
		{
			if (uom.getCode().contains("bun_kg"))
			{
				//return Math.round((quantity / productData.getBundleKgEquiv()));
				return Math.round(quantity);
			}
			else if (uom.getCode().contains("bun_lb"))
			{
				//return Math.round((quantity / productData.getBundleLbEquiv()));
				return Math.round(quantity);
			}
			else if (uom.getCode().contains("pc_kg"))
			{
				return Math.round((quantity / productData.getPiecesPerBundle()));
				//return Math.round(quantity);
			}
			else if (uom.getCode().contains("pc_lb"))
			{
				return Math.round((quantity / productData.getPiecesPerBundle()));
				//return Math.round(quantity);
			}
			else if (uom.getCode().contains("kg"))
			{
				return Math.round((quantity / productData.getKgEquiv()));
				//return Math.round(quantity);
			}
			else if (uom.getCode().contains("mt"))
			{
				//08Ene2015
				return Math.round((quantity / productData.getMtEquiv()));
				//return Math.round((quantity / (productData.getLength() * productData.getPiecesPerBundle())));
			}
			else if (uom.getCode().contains("lb"))
			{
				return Math.round((quantity / productData.getLbEquiv()));
				//return Math.round(quantity);
			}
			else if (uom.getCode().contains("ft"))
			{
				//08Ene2015
				return Math.round((quantity / productData.getFtEquiv()));
				//return Math.round((quantity / (productData.getFtEquiv() * productData.getPiecesPerBundle())));
			}
			else if (uom.getCode().contains("ton"))
			{
				return Math.round((quantity / productData.getTnEquiv()));
				//return Math.round(quantity);
			}
		}
		catch (final Exception e)
		{
			LOG.error("Error not found product:" + productData.getBaseCode() + " equivalent converted quantity for: "
					+ uom.getCode());
			return 0L;
		}

		return Math.round(quantity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.uom.NeorisUoMQuantityConverter#convertOutputQuantityFrom(de.hybris.platform.core.model.product.
	 * UnitModel, de.hybris.platform.commercefacades.product.data.ProductData)
	 */
	@Override
	public Double convertOutputQuantityFrom(final UnitModel uom, final Long quantity, final ProductData productData)
	{
		if (productData.getIsAPI())
		{
			// Quantity for products API are represented in ft.
			LOG.info("API Product: " + productData.getBaseCode() + "... skipping quantity conversion.");
			return quantity.doubleValue();
		}

		try
		{
			if (uom.getCode().contains("bun_kg"))
			{
				//return quantity * productData.getBundleKgEquiv();
				return quantity.doubleValue();
			}
			else if (uom.getCode().contains("bun_lb"))
			{
				//return quantity * productData.getBundleLbEquiv();
				return quantity.doubleValue();
			}
			else if (uom.getCode().contains("pc_kg"))
			{

				return quantity * productData.getPiecesPerBundle();

			}
			else if (uom.getCode().contains("pc_lb"))
			{

				return quantity * productData.getPiecesPerBundle();
			}
			else if (uom.getCode().contains("kg"))
			{
				//return quantity * productData.getKgEquiv();
				//return quantity.doubleValue();
				return quantity * productData.getBundleKgEquiv();
			}
			else if (uom.getCode().contains("mt"))
			{
				//return quantity.doubleValue();
				//return quantity * productData.getMtEquiv() * productData.getPiecesPerBundle();
				return quantity * productData.getMtEquiv();

			}
			else if (uom.getCode().contains("lb"))
			{
				//return quantity * productData.getLbEquiv();
				//return quantity.doubleValue();
				return quantity * productData.getBundleLbEquiv();
			}
			else if (uom.getCode().contains("ft"))
			{
				//return quantity * productData.getFtEquiv() * productData.getPiecesPerBundle();
				//return quantity.doubleValue();
				return quantity * productData.getFtEquiv();
			}
			else if (uom.getCode().contains("ton"))
			{
				//return quantity.doubleValue();
				return quantity * productData.getTnEquiv();
			}
		}
		catch (final Exception e)
		{
			LOG.error("Error not found product:" + productData.getBaseCode() + " equivalent converted quantity for: "
					+ uom.getCode());
			return (double) 0;
		}

		return (double) quantity;
	}

	@Override
	public Double convertOutputQuantityFrom(final UnitModel uom, final Long quantity, final ProductData productData,
			final Boolean isQuantityInPieces)
	{
		if (isQuantityInPieces == null || !isQuantityInPieces.booleanValue())
		{
			return convertOutputQuantityFrom(uom, quantity, productData);
		}

		// for incomplete packages represented in pieces, only for a4c
		final Double qtyBundles = quantity / productData.getPiecesPerBundle();

		try
		{
			if (uom.getCode().contains("bun_kg"))
			{
				return qtyBundles.doubleValue();
			}
			else if (uom.getCode().contains("bun_lb"))
			{
				return qtyBundles.doubleValue();
			}
			else if (uom.getCode().contains("pc_kg"))
			{
				return qtyBundles * productData.getPiecesPerBundle();
			}
			else if (uom.getCode().contains("pc_lb"))
			{
				return qtyBundles * productData.getPiecesPerBundle();
			}
			else if (uom.getCode().contains("kg"))
			{
				return qtyBundles * productData.getBundleKgEquiv();
			}
			else if (uom.getCode().contains("mt"))
			{
				return qtyBundles * productData.getMtEquiv();
			}
			else if (uom.getCode().contains("lb"))
			{
				return qtyBundles * productData.getBundleLbEquiv();
			}
			else if (uom.getCode().contains("ft"))
			{
				return qtyBundles * productData.getFtEquiv();
			}
			else if (uom.getCode().contains("ton"))
			{
				return qtyBundles * productData.getTnEquiv();
			}
		}
		catch (final Exception e)
		{
			LOG.error("Error not found product:" + productData.getBaseCode() + " equivalent converted quantity for: "
					+ uom.getCode());
			return (double) 0;
		}

		return (double) quantity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.uom.NeorisUoMQuantityConverter#convertOutputQuantiesFrom(de.hybris.platform.core.model.product.
	 * UnitModel, de.hybris.platform.commercefacades.product.data.ProductData, java.util.List)
	 */
	@Override
	public List<Double> convertOutputQuantiesFrom(final UnitModel uom, final Long availableQty, final ProductData productData)
	{
		final List<Double> result = new ArrayList<Double>();

		if (productData.getIsAPI())
		{
			// Quantity for products API are represented in ft and a free text input is used to capture quantities.
			LOG.info("API Product: " + productData.getBaseCode() + "... skipping quantity conversion.");
			return result;
		}

		for (long bundleIndex = 0; bundleIndex <= availableQty; bundleIndex++)
		{
			result.add(convertOutputQuantityFrom(uom, bundleIndex, productData));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.neoris.core.uom.NeorisUoMQuantityConverter#injectConvertedQuantitiesOn(de.hybris.platform.core.model.product
	 * .UnitModel, java.util.List)
	 */
	@Override
	public void injectInventoryConvertedQuantitiesOn(final UnitModel uom, final List<ProductData> col)
	{
		for (final ProductData eachProduct : col)
		{
			final ProductInventoryEntry productInventory = eachProduct.getInventoryEntry();

			Long availableAmount = new Long(productInventory.getAvailableStockBundles());
			List<Double> quantities = convertOutputQuantiesFrom(uom, availableAmount, eachProduct);
			productInventory.setAvailableStockBundlesCol(quantities);

			availableAmount = new Long(productInventory.getAvailableStockBundlesInternal());
			quantities = convertOutputQuantiesFrom(uom, availableAmount, eachProduct);
			productInventory.setAvailableStockBundlesColInternal(quantities);

			availableAmount = new Long(productInventory.getRollingScheduleBundles());
			quantities = convertOutputQuantiesFrom(uom, availableAmount, eachProduct);
			productInventory.setRollingScheduleBundlesCol(quantities);

			availableAmount = new Long(productInventory.getNoInventoyRoleBundles());
			quantities = convertOutputQuantiesFrom(uom, availableAmount, eachProduct);
			productInventory.setNoInventoryRoleBundlesCol(quantities);
		}
	}
}
