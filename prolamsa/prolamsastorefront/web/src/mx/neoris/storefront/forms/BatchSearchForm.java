package mx.neoris.storefront.forms;

public class BatchSearchForm
{
	public BatchSearchForm()
	{
		super();
	}
	
	private String customer;
	private String baseStore;
	private String batchType;
	private String reference;

	private String sort;

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
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

	/**
	 * @return the batchType
	 */
	public String getBatchType() {
		return batchType;
	}

	/**
	 * @param batchType the batchType to set
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
	
	
}
