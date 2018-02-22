/**
 * 
 */
package mx.neoris.facades.impl;

import de.hybris.platform.commerceservices.util.AbstractComparator;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import mx.neoris.core.model.NoticeModel;
import mx.neoris.core.services.AddNoticeParameters;
import mx.neoris.core.services.NeorisNoticesService;
import mx.neoris.facades.NeorisNoticesFacade;
import mx.neoris.facades.document.data.NoticeData;
import mx.neoris.facades.impl.DefaultNeorisFacade.AddressDataByFormattedAddress;

/**
 * @author eduardo.carrillo
 *
 */
public class DefaultNeorisNoticesFacade implements NeorisNoticesFacade {

	@Resource(name="neorisNoticesService")
	NeorisNoticesService neorisNoticesService;
	
	@Resource(name = "baseStoreService")
	private BaseStoreService baseStoreService;
	
	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisNoticesFacade#getNotice(java.lang.String)
	 */
	@Override
	public NoticeModel getNotice(String code) {
		
		NoticeModel noticeModel = neorisNoticesService.getNotice(code);
		
		if(noticeModel == null)
			return null;
		
		return noticeModel;
		
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisNoticesFacade#addNotice(mx.neoris.core.services.AddNoticeParameters)
	 */
	@Override
	public NoticeData addNotice(AddNoticeParameters parameters) {
		NoticeModel noticeModel = neorisNoticesService.addNotice(parameters);
		
		if(noticeModel == null)
			return null;
		
		NoticeData noticeData = new NoticeData();
		
		noticeData.setCode(noticeModel.getCode());
		noticeData.setName(noticeModel.getName());
		noticeData.setPublishedDate(noticeModel.getCreationtime());
		noticeData.setType(noticeModel.getType().toString());
		
		return noticeData;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisNoticesFacade#getNoticesForCurrentStore()
	 */
	@Override
	public List<NoticeData> getNoticesForCurrentStore() {
		// YTODO Auto-generated method stub
		
		
		
		List<NoticeModel> noticeModels = neorisNoticesService.getNoticesForCurrentStore();
		
		if(noticeModels == null || noticeModels.size() == 0)
			return null;
		
		List<NoticeData> noticeDatas = new ArrayList<NoticeData>();
		
		for(NoticeModel eachNoticeModel : noticeModels)
		{
			NoticeData eachNoticeData = new NoticeData();
			eachNoticeData.setCode(eachNoticeModel.getCode());
			eachNoticeData.setName(eachNoticeModel.getName());
			eachNoticeData.setPublishedDate(eachNoticeModel.getCreationtime());
			eachNoticeData.setType(eachNoticeModel.getType().toString());
			
			noticeDatas.add(eachNoticeData);
		}
		Collections.sort(noticeDatas, new NoticesByDate());
		
		return noticeDatas;
	}

	/* (non-Javadoc)
	 * @see mx.neoris.facades.NeorisNoticesFacade#deleteNotice(java.lang.String)
	 */
	@Override
	public boolean deleteNotice(String code) {
		
		return neorisNoticesService.deleteNotice(code);
		
	}

	public class NoticesByDate extends AbstractComparator<NoticeData>
	{

		/* (non-Javadoc)
		 * @see de.hybris.platform.commerceservices.util.AbstractComparator#compareInstances(java.lang.Object, java.lang.Object)
		 */
		@Override
		protected int compareInstances(NoticeData arg0, NoticeData arg1) {
			// YTODO Auto-generated method stub
			return compareValues(arg1.getPublishedDate(), arg0.getPublishedDate());
		}
		
	}
	
}
