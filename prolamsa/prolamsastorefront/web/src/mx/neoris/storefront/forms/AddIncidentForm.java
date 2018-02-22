package mx.neoris.storefront.forms;

import java.io.File;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;


/**
 * Pojo for Invoice Search form.
 */
public class AddIncidentForm
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
	private String productLocation;
	private String country;
	private String incoterm;
	private String description;
	private MultipartFile atach1;
	private MultipartFile atach2;
	private MultipartFile atach3;
	private MultipartFile atach4;
	private MultipartFile atach5;
	private String atach1Description;
	private String atach2Description;
	private String atach3Description;
	private String atach4Description;
	private String atach5Description;
	private String incidentLines;
	
	
	
	
	/**
	 * @return the atach1Description
	 */
	public String getAtach1Description() {
		return atach1Description;
	}

	/**
	 * @param atach1Description the atach1Description to set
	 */
	public void setAtach1Description(String atach1Description) {
		this.atach1Description = atach1Description;
	}

	/**
	 * @return the atach2Description
	 */
	public String getAtach2Description() {
		return atach2Description;
	}

	/**
	 * @param atach2Description the atach2Description to set
	 */
	public void setAtach2Description(String atach2Description) {
		this.atach2Description = atach2Description;
	}

	/**
	 * @return the atach3Description
	 */
	public String getAtach3Description() {
		return atach3Description;
	}

	/**
	 * @param atach3Description the atach3Description to set
	 */
	public void setAtach3Description(String atach3Description) {
		this.atach3Description = atach3Description;
	}

	/**
	 * @return the atach4Description
	 */
	public String getAtach4Description() {
		return atach4Description;
	}

	/**
	 * @param atach4Description the atach4Description to set
	 */
	public void setAtach4Description(String atach4Description) {
		this.atach4Description = atach4Description;
	}

	/**
	 * @return the atach5Description
	 */
	public String getAtach5Description() {
		return atach5Description;
	}

	/**
	 * @param atach5Description the atach5Description to set
	 */
	public void setAtach5Description(String atach5Description) {
		this.atach5Description = atach5Description;
	}

	
	/**
	 * @return the atach1
	 */
	public MultipartFile getAtach1() {
		return atach1;
	}

	/**
	 * @param atach1 the atach1 to set
	 */
	public void setAtach1(MultipartFile atach1) {
		this.atach1 = atach1;
	}

	/**
	 * @return the atach2
	 */
	public MultipartFile getAtach2() {
		return atach2;
	}

	/**
	 * @param atach2 the atach2 to set
	 */
	public void setAtach2(MultipartFile atach2) {
		this.atach2 = atach2;
	}

	/**
	 * @return the atach3
	 */
	public MultipartFile getAtach3() {
		return atach3;
	}

	/**
	 * @param atach3 the atach3 to set
	 */
	public void setAtach3(MultipartFile atach3) {
		this.atach3 = atach3;
	}

	/**
	 * @return the atach4
	 */
	public MultipartFile getAtach4() {
		return atach4;
	}

	/**
	 * @param atach4 the atach4 to set
	 */
	public void setAtach4(MultipartFile atach4) {
		this.atach4 = atach4;
	}

	/**
	 * @return the atach5
	 */
	public MultipartFile getAtach5() {
		return atach5;
	}

	/**
	 * @param atach5 the atach5 to set
	 */
	public void setAtach5(MultipartFile atach5) {
		this.atach5 = atach5;
	}

	/**
	 * @return the invoice
	 */
	@NotNull(message = "incident.add.invoice.invalid")
	@Size(min = 1, max = 255, message = "incident.add.invoice.invalid")
	public String getInvoice() {
		return invoice;
	}

	/**
	 * @param invoice the invoice to set
	 */
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	/**
	 * @return the customerIncidentNumber
	 */
	public String getCustomerIncidentNumber() {
		return customerIncidentNumber;
	}

	/**
	 * @param customerIncidentNumber the customerIncidentNumber to set
	 */
	public void setCustomerIncidentNumber(String customerIncidentNumber) {
		this.customerIncidentNumber = customerIncidentNumber;
	}

	/**
	 * @return the contactName
	 */
	@NotNull(message = "incident.add.contactName.invalid")
	@Size(min = 1, max = 255, message = "incident.add.contactName.invalid")
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the alternateContactName
	 */
	public String getAlternateContactName() {
		return alternateContactName;
	}

	/**
	 * @param alternateContactName the alternateContactName to set
	 */
	public void setAlternateContactName(String alternateContactName) {
		this.alternateContactName = alternateContactName;
	}

	/**
	 * @return the phone
	 */
	@NotNull(message = "incident.add.phone.invalid")
	@Size(min = 1, max = 255, message = "incident.add.phone.invalid")
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the description
	 */
	@NotNull(message = "incident.add.description.invalid")
	@Size(min = 1, max = 4000, message = "incident.add.description.invalid")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	/**
	 * @return the customer
	 */
	@NotNull(message = "incident.add.customer.invalid")
	@Size(min = 1, max = 255, message = "incident.add.customer.invalid")
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the uidCustomer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@NotNull(message = "incident.add.type.invalid")
	@Size(min = 1, max = 255, message = "incident.add.type.invalid")
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}
	@NotNull(message = "incident.add.email.invalid")
	@Email( message = "incident.add.email.invalid")
	@Size(min = 1, max = 70, message = "incident.add.email.invalid")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIncidentLines() {
		return incidentLines;
	}

	public void setIncidentLines(String incidentLines) {
		this.incidentLines = incidentLines;
	}

	public String getProductLocation() {
		return productLocation;
	}

	public void setProductLocation(String productLocation) {
		this.productLocation = productLocation;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIncoterm() {
		return incoterm;
	}

	public void setIncoterm(String incoterm) {
		this.incoterm = incoterm;
	}


	
	
	
}
