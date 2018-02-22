/**
 * 
 */
package mx.neoris.facades.populators;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorservices.company.B2BCommerceB2BUserGroupService;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.b2bacceleratorservices.strategies.B2BUserGroupsLookUpStrategy;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;


/**
 * @author christian.loredo
 *
 */
public class NeorisUserPopulator implements Populator<CustomerData, B2BCustomerModel>
{
	private CompanyB2BCommerceService companyB2BCommerceService;
	private B2BCommerceB2BUserGroupService b2BCommerceB2BUserGroupService;
	private CustomerNameStrategy customerNameStrategy;
	private B2BUserGroupsLookUpStrategy b2BUserGroupsLookUpStrategy;
	private UserService userService;
	private B2BUnitService<B2BUnitModel, UserModel> b2bUnitService;
	
	@Override
	public void populate(CustomerData source, B2BCustomerModel target)
			throws ConversionException {
		
		target.setEmail(source.getEmail());
		target.setActive(source.isActive());
		
		target.setName(customerNameStrategy.getName(source.getFirstName(), source.getLastName())); 
				//customerNameStrategy.getName(source.getFirstName(), source.getLastName())); 
				//getCustomerNameStrategy().getName(source.getFirstName(), source.getLastName()));
		//target.setName(source.getFirstName());
		final B2BUnitModel defaultUnit = companyB2BCommerceService.getUnitForUid(source.getUnit().getUid()); //this.getCompanyB2BCommerceService().getUnitForUid(source.getUnit().getUid());
		final B2BUnitModel oldDefaultUnit = b2bUnitService.getParent(target); //this.getB2bUnitService().getParent(target);
		target.setDefaultB2BUnit(defaultUnit);
        //target.setB2BUnits(source.getB2bUnits());
		//getCompanyB2BCommerceService().
		final Set<PrincipalGroupModel> groups = new HashSet<PrincipalGroupModel>(target.getGroups());
		if (oldDefaultUnit != null && groups.contains(oldDefaultUnit))
		{
			groups.remove(oldDefaultUnit);
		}
		groups.add(defaultUnit);
		target.setGroups(groups);
		//this.getB2BCommerceB2BUserGroupService().updateUserGroups(getUserGroups(), source.getRoles(), target);
		//b2BCommerceB2BUserGroupService.updateUserGroups(getUserGroups(), source.getRoles(), target);
		b2BCommerceB2BUserGroupService.updateUserGroups(getUserGroups(), source.getRoles(), target);
		if (StringUtils.isNotBlank(source.getTitleCode()))
		{
			target.setTitle(userService.getTitleForCode(source.getTitleCode())); //getUserService().getTitleForCode(source.getTitleCode()));
		}
		else
		{
			target.setTitle(null);
		}
		setUid(source, target);
		
	}
	
	protected void setUid(final CustomerData source, final B2BCustomerModel target)
	{
		if (source.getDisplayUid() != null && !source.getDisplayUid().isEmpty())
		{
			target.setOriginalUid(source.getDisplayUid());
			target.setUid(source.getDisplayUid().toLowerCase());
		}
		else if (source.getEmail() != null)
		{
			target.setOriginalUid(source.getEmail());
			target.setUid(source.getEmail().toLowerCase());
		}
	}
	
	public List<String> getUserGroups()
	{
		return b2BUserGroupsLookUpStrategy.getUserGroups(); // getB2BUserGroupsLookUpStrategy().getUserGroups();
	}

	/**
	 * @return the companyB2BCommerceService
	 */
	public CompanyB2BCommerceService getCompanyB2BCommerceService() {
		return companyB2BCommerceService;
	}

	/**
	 * @param companyB2BCommerceService the companyB2BCommerceService to set
	 */
	public void setCompanyB2BCommerceService(
			CompanyB2BCommerceService companyB2BCommerceService) {
		this.companyB2BCommerceService = companyB2BCommerceService;
	}

	/**
	 * @return the b2BCommerceB2BUserGroupService
	 */
	public B2BCommerceB2BUserGroupService getB2BCommerceB2BUserGroupService() {
		return b2BCommerceB2BUserGroupService;
	}

	/**
	 * @param b2bCommerceB2BUserGroupService the b2BCommerceB2BUserGroupService to set
	 */
	public void setB2BCommerceB2BUserGroupService(
			B2BCommerceB2BUserGroupService b2bCommerceB2BUserGroupService) {
		b2BCommerceB2BUserGroupService = b2bCommerceB2BUserGroupService;
	}

	/**
	 * @return the customerNameStrategy
	 */
	public CustomerNameStrategy getCustomerNameStrategy() {
		return customerNameStrategy;
	}

	/**
	 * @param customerNameStrategy the customerNameStrategy to set
	 */
	public void setCustomerNameStrategy(CustomerNameStrategy customerNameStrategy) {
		this.customerNameStrategy = customerNameStrategy;
	}

	/**
	 * @return the b2BUserGroupsLookUpStrategy
	 */
	public B2BUserGroupsLookUpStrategy getB2BUserGroupsLookUpStrategy() {
		return b2BUserGroupsLookUpStrategy;
	}

	/**
	 * @param b2bUserGroupsLookUpStrategy the b2BUserGroupsLookUpStrategy to set
	 */
	public void setB2BUserGroupsLookUpStrategy(
			B2BUserGroupsLookUpStrategy b2bUserGroupsLookUpStrategy) {
		b2BUserGroupsLookUpStrategy = b2bUserGroupsLookUpStrategy;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the b2bUnitService
	 */
	public B2BUnitService<B2BUnitModel, UserModel> getB2bUnitService() {
		return b2bUnitService;
	}

	/**
	 * @param b2bUnitService the b2bUnitService to set
	 */
	public void setB2bUnitService(
			B2BUnitService<B2BUnitModel, UserModel> b2bUnitService) {
		this.b2bUnitService = b2bUnitService;
	}

	

}
