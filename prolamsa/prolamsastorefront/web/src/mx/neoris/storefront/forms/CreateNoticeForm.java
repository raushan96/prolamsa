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
public class CreateNoticeForm
{

	private String name;
	private String type;
	private MultipartFile media;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the media
	 */
	public MultipartFile getMedia() {
		return media;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(MultipartFile media) {
		this.media = media;
	}
	
	
	
	
	
	
	
}
