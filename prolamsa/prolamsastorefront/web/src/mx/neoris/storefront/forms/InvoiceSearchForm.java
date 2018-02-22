package mx.neoris.storefront.forms;

import javax.validation.constraints.Size;


/**
 * Pojo for Invoice Search form.
 */
public class InvoiceSearchForm
{

	public InvoiceSearchForm()
	{
		super();
	}

	private String customer;
	private String baseStore;
	private String number;
	private String initialDate;
	private String finalDate;

	private String sort;

	@Size(min = 0, max = 255, message = "{invoice.number.invalid}")
	public String getNumber()
	{
		return number;
	}

	public void setNumber(final String number)
	{
		this.number = number;
	}

	public String getInitialDate()
	{
		return initialDate;
	}

	public void setInitialDate(final String initialDate)
	{
		this.initialDate = initialDate;
	}

	public String getFinalDate()
	{
		return finalDate;
	}

	public void setFinalDate(final String finalDate)
	{
		this.finalDate = finalDate;
	}

	/**
	 * @return the sort
	 */
	public String getSort()
	{
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort)
	{
		this.sort = sort;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the uidCustomer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the baseStore
	 */
	public String getBaseStore() {
		return baseStore;
	}

	/**
	 * @param baseStore the baseStore to set
	 */
	public void setBaseStore(String baseStore) {
		this.baseStore = baseStore;
	}
	
	
	
	
}
