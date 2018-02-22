/**
 * 
 */
package mx.neoris.core.services;

import java.util.List;

import mx.neoris.core.model.NoticeModel;


/**
 * @author e-jecarrilloi
 * 
 */
public interface NeorisNoticesService
{
	public NoticeModel getNotice(String code);

	public NoticeModel addNotice(AddNoticeParameters parameters);

	public List<NoticeModel> getNoticesForCurrentStore();

	public boolean deleteNotice(String code);

}
