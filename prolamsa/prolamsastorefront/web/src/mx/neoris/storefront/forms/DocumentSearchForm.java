package mx.neoris.storefront.forms;

import javax.validation.constraints.Size;

public class DocumentSearchForm
{
	public DocumentSearchForm()
	{
		super();
	}
	
	private String customer;
	private String baseStore;
	private String documentType;
	private String reference;

	private String initialDate;
	private String finalDate;

	private String sort;
	

	@Size(min = 0, max = 255, message = "{invoice.number.invalid}")

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
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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
	 * @return the initialDate
	 */
	public String getInitialDate() {
		return initialDate;
	}

	/**
	 * @param initialDate the initialDate to set
	 */
	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}

	/**
	 * @return the finalDate
	 */
	public String getFinalDate() {
		return finalDate;
	}

	/**
	 * @param finalDate the finalDate to set
	 */
	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
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
