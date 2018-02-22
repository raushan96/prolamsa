/**
 * 
 */
package mx.neoris.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.event.ClusterAwareEvent;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;
import de.hybris.platform.store.BaseStoreModel;

import mx.neoris.core.model.IncidentModel;


/**
 * @author e-jecarrilloi
 * 
 */
public class IncidentAddedEvent extends AbstractEvent implements ClusterAwareEvent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IncidentModel incidentModel;
	private CustomerModel customerModel;
	private BaseStoreModel baseStoreModel;
	private BaseSiteModel baseSiteModel;
	private LanguageModel languageModel;




	public IncidentModel getIncidentModel()
	{
		return incidentModel;
	}

	public void setIncidentModel(final IncidentModel incidentModel)
	{
		this.incidentModel = incidentModel;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.servicelayer.event.ClusterAwareEvent#publish(int, int)
	 */
	@Override
	public boolean publish(final int sourceNodeId, final int targetNodeId)
	{
		// YTODO Auto-generated method stub
		return (sourceNodeId == targetNodeId);
	}

	public CustomerModel getCustomerModel()
	{
		return customerModel;
	}

	public void setCustomerModel(final CustomerModel customerModel)
	{
		this.customerModel = customerModel;
	}

	public BaseStoreModel getBaseStoreModel()
	{
		return baseStoreModel;
	}

	public void setBaseStoreModel(final BaseStoreModel baseStoreModel)
	{
		this.baseStoreModel = baseStoreModel;
	}

	public BaseSiteModel getBaseSiteModel()
	{
		return baseSiteModel;
	}

	public void setBaseSiteModel(final BaseSiteModel baseSiteModel)
	{
		this.baseSiteModel = baseSiteModel;
	}

	public LanguageModel getLanguageModel()
	{
		return languageModel;
	}

	public void setLanguageModel(LanguageModel languageModel)
	{
		this.languageModel = languageModel;
	}

}
