package mx.neoris.storefront.forms;

import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;


/**
 * Pojo for Invoice Search form.
 */
public class IncidentSearchForm
{

	private String customer;
	private String number;
	private String initialDate;
	private String finalDate;
	private String state;
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	
	
	
}
