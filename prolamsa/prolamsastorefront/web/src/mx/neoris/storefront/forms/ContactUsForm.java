/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package mx.neoris.storefront.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


/**
 * Pojo for 'Contact Us' form.
 * @author e-lacantu
 */
public class ContactUsForm
{
	@NotNull(message = "{general.required}")
	@Size(min = 1, max = 255, message = "{general.required}")
	private String name;
	
	@NotNull(message = "{general.required}")
	@Size(min = 1, max = 255, message = "{general.required}")
	private String lastName;
	
	@Email
	@NotNull(message = "{general.required}")
	private String email;
	
	@NotNull(message = "{general.required}")
	private String phone;
	
	private String altPhone;
	
	@NotNull(message = "{general.required}")
	private String contactArea;
	
	@NotNull(message = "{general.required}")
	private String comment;
	
	
	public ContactUsForm(){
		super();
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
	 * @return the lastName
	 */
	
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the email
	 */	
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
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
	 * @return the altPhone
	 */
	public String getAltPhone() {
		return altPhone;
	}
	/**
	 * @param altPhone the altPhone to set
	 */
	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
	}
	/**
	 * @return the contactArea
	 */
	public String getContactArea() {
		return contactArea;
	}
	/**
	 * @param contactArea the contactArea to set
	 */
	public void setContactArea(String contactArea) {
		this.contactArea = contactArea;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	

	
	
}
