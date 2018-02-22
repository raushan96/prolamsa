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
public class B2BCustomerSettingForm
{

	private String address;
	private String uom;
	private String transportationMode;
	private String location;
	private String b2BUnit;
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}
	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}
	/**
	 * @return the transportationMode
	 */
	public String getTransportationMode() {
		return transportationMode;
	}
	/**
	 * @param transportationMode the transportationMode to set
	 */
	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	public String getB2BUnit() {
		return b2BUnit;
	}
	public void setB2BUnit(String b2bUnit) {
		b2BUnit = b2bUnit;
	}
	
	
}
