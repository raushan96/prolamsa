package mx.neoris.core.services;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;


/**
 * Pojo for Invoice Search form.
 */
public class AddNoticeParameters
{

	private MultipartFile media;
	private String name;
	private Date publicationDate;
	private String type;


	public MultipartFile getMedia()
	{
		return media;
	}

	public void setMedia(final MultipartFile media)
	{
		this.media = media;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public Date getPublicationDate()
	{
		return publicationDate;
	}

	public void setPublicationDate(final Date publicationDate)
	{
		this.publicationDate = publicationDate;
	}

	public String getType()
	{
		return type;
	}

	public void setType(final String type)
	{
		this.type = type;
	}

}
