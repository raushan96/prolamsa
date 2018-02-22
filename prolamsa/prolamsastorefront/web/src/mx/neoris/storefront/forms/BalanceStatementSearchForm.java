/**
 * @author e-lacantu
 * Pojo for Balance Statement Form
 *
 */

package mx.neoris.storefront.forms;

public class BalanceStatementSearchForm
{
	private String user;
	private String customer;
	private String baseStore;
	private String sort;

	public BalanceStatementSearchForm()
	{
		super();
	}

	public String getCustomer()
	{
		return customer;
	}

	public void setCustomer(String customer)
	{
		this.customer = customer;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * @return the baseStore
	 */
	public String getBaseStore() {
		return baseStore;
	}

	/**
	 * @param baseStore the baseStore to set
	 */
	public void setBaseStore(String baseStore) {
		this.baseStore = baseStore;
	}
	
	
}
