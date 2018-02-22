package mx.neoris.storefront.forms;

import javax.validation.constraints.Size;

public class BackorderDetailSearchForm {
	
	public BackorderDetailSearchForm()
	{
		super();
	}

	private String customer;
	private String order;
	private String customerPO;
	private String initialDate;
	private String finalDate;
	private String address;
	private String name;
	
	//RESULT
	//private String addressResult;
	//private String partNumber;
	//private String deliveryDate;
	//private String orderResult;
	//private String orderDate;
	//private String customerPOResult;
	//private String pieces;
	
	private String sort;

	@Size(min = 0, max = 255, message = "{invoice.number.invalid}")
	
	
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}
	/**
	 * @return the customerPO
	 */
	public String getCustomerPO() {
		return customerPO;
	}
	/**
	 * @param customerPO the customerPO to set
	 */
	public void setCustomerPO(String customerPO) {
		this.customerPO = customerPO;
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
	 * @return the customer
	 */
	public String getCustomer()
	{
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer)
	{
		this.customer = customer;
	}
}
