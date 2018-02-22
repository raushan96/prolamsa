package mx.neoris.core.services;

import de.hybris.platform.store.BaseStoreModel;

import java.util.List;

import mx.neoris.core.incident.IncidentLine;

import org.springframework.web.multipart.MultipartFile;


/**
 * Pojo for Invoice Search form.
 */
public class AddIncidentParameters
{

	private String customer;
	private String customerName;
	private String shipTo;
	private String state;
	private String type;
	private String invoice;
	private String customerIncidentNumber;
	private String contactName;
	private String alternateContactName;
	private String phone;
	private String email;
	private String description;
	private String productLocation;
	private String country;
	private String incoterm;
	private BaseStoreModel baseStore;
	private String atach1Description;
	private List<IncidentLine> lines;
	private MultipartFile atach1;
	private String atach2Description;
	private MultipartFile atach2;
	private String atach3Description;
	private MultipartFile atach3;
	private String atach4Description;
	private MultipartFile atach4;
	private String atach5Description;
	private MultipartFile atach5;

	/**
	 * @return the atach1
	 */
	public MultipartFile getAtach1()
	{
		return atach1;
	}

	/**
	 * @return the atach1Description
	 */
	public String getAtach1Description()
	{
		return atach1Description;
	}

	/**
	 * @param atach1Description
	 *           the atach1Description to set
	 */
	public void setAtach1Description(final String atach1Description)
	{
		this.atach1Description = atach1Description;
	}

	/**
	 * @return the atach2Description
	 */
	public String getAtach2Description()
	{
		return atach2Description;
	}

	/**
	 * @param atach2Description
	 *           the atach2Description to set
	 */
	public void setAtach2Description(final String atach2Description)
	{
		this.atach2Description = atach2Description;
	}

	/**
	 * @return the atach3Description
	 */
	public String getAtach3Description()
	{
		return atach3Description;
	}

	/**
	 * @param atach3Description
	 *           the atach3Description to set
	 */
	public void setAtach3Description(final String atach3Description)
	{
		this.atach3Description = atach3Description;
	}

	/**
	 * @return the atach4Description
	 */
	public String getAtach4Description()
	{
		return atach4Description;
	}

	/**
	 * @param atach4Description
	 *           the atach4Description to set
	 */
	public void setAtach4Description(final String atach4Description)
	{
		this.atach4Description = atach4Description;
	}

	/**
	 * @param atach1
	 *           the atach1 to set
	 */
	public void setAtach1(final MultipartFile atach1)
	{
		this.atach1 = atach1;
	}

	/**
	 * @return the atach2
	 */
	public MultipartFile getAtach2()
	{
		return atach2;
	}

	/**
	 * @param atach2
	 *           the atach2 to set
	 */
	public void setAtach2(final MultipartFile atach2)
	{
		this.atach2 = atach2;
	}

	/**
	 * @return the atach3
	 */
	public MultipartFile getAtach3()
	{
		return atach3;
	}

	/**
	 * @param atach3
	 *           the atach3 to set
	 */
	public void setAtach3(final MultipartFile atach3)
	{
		this.atach3 = atach3;
	}

	/**
	 * @return the atach4
	 */
	public MultipartFile getAtach4()
	{
		return atach4;
	}

	/**
	 * @param atach4
	 *           the atach4 to set
	 */
	public void setAtach4(final MultipartFile atach4)
	{
		this.atach4 = atach4;
	}

	/**
	 * @return the atach5
	 */
	public MultipartFile getAtach5()
	{
		return atach5;
	}

	/**
	 * @param atach5
	 *           the atach5 to set
	 */
	public void setAtach5(final MultipartFile atach5)
	{
		this.atach5 = atach5;
	}

	/**
	 * @return the invoice
	 */
	public String getInvoice()
	{
		return invoice;
	}

	/**
	 * @param invoice
	 *           the invoice to set
	 */
	public void setInvoice(final String invoice)
	{
		this.invoice = invoice;
	}

	/**
	 * @return the customerIncidentNumber
	 */
	public String getCustomerIncidentNumber()
	{
		return customerIncidentNumber;
	}

	/**
	 * @param customerIncidentNumber
	 *           the customerIncidentNumber to set
	 */
	public void setCustomerIncidentNumber(final String customerIncidentNumber)
	{
		this.customerIncidentNumber = customerIncidentNumber;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName()
	{
		return contactName;
	}

	/**
	 * @param contactName
	 *           the contactName to set
	 */
	public void setContactName(final String contactName)
	{
		this.contactName = contactName;
	}

	/**
	 * @return the alternateContactName
	 */
	public String getAlternateContactName()
	{
		return alternateContactName;
	}

	/**
	 * @param alternateContactName
	 *           the alternateContactName to set
	 */
	public void setAlternateContactName(final String alternateContactName)
	{
		this.alternateContactName = alternateContactName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *           the phone to set
	 */
	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *           the description to set
	 */
	public void setDescription(final String description)
	{
		this.description = description;
	}




	/**
	 * @return the customer
	 */
	public String getCustomer()
	{
		return customer;
	}

	/**
	 * @param customer
	 *           the uidCustomer to set
	 */
	public void setCustomer(final String customer)
	{
		this.customer = customer;
	}

	public String getType()
	{
		return type;
	}

	public void setType(final String type)
	{
		this.type = type;
	}

	public String getState()
	{
		return state;
	}

	public void setState(final String state)
	{
		this.state = state;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(final String customerName)
	{
		this.customerName = customerName;
	}

	public String getShipTo()
	{
		return shipTo;
	}

	public void setShipTo(final String shipTo)
	{
		this.shipTo = shipTo;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public List<IncidentLine> getLines()
	{
		return lines;
	}

	public void setLines(final List<IncidentLine> lines)
	{
		this.lines = lines;
	}

	public String getAtach5Description()
	{
		return atach5Description;
	}

	public void setAtach5Description(final String atach5Description)
	{
		this.atach5Description = atach5Description;
	}

	public BaseStoreModel getBaseStore()
	{
		return baseStore;
	}

	public void setBaseStore(final BaseStoreModel baseStore)
	{
		this.baseStore = baseStore;
	}


	/**
	 * @return the productLocation
	 */
	public String getProductLocation()
	{
		return productLocation;
	}

	/**
	 * @param productLocation
	 *           the productLocation to set
	 */
	public void setProductLocation(final String productLocation)
	{
		this.productLocation = productLocation;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country
	 *           the country to set
	 */
	public void setCountry(final String country)
	{
		this.country = country;
	}

	/**
	 * @return the incoterm
	 */
	public String getIncoterm()
	{
		return incoterm;
	}

	/**
	 * @param incoterm
	 *           the incoterm to set
	 */
	public void setIncoterm(final String incoterm)
	{
		this.incoterm = incoterm;
	}


}
