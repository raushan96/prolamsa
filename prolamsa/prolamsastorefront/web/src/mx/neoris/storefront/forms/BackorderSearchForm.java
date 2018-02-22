package mx.neoris.storefront.forms;

import java.util.List;

public class BackorderSearchForm {
	
	public BackorderSearchForm()
	{
		super();
	}

	private String customer;
	private String baseStore;
	private List<String> listCustomer;
	private String companyName;
	private String description;
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

	/**
	 * @return the listCustomer
	 */
	public List<String> getListCustomer() {
		return listCustomer;
	}

	/**
	 * @param listCustomer the listCustomer to set
	 */
	public void setListCustomer(List<String> listCustomer) {
		this.listCustomer = listCustomer;
	}

	private String pcsOrder;
	private String kgsOrder;
	private String pendingKilos;
	private String readyKilos;
	private String loadingKilos;
	private String balanceKilos;
	private String sort;

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the description
	 */
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
	 * @return the pcsOrder
	 */
	public String getPcsOrder() {
		return pcsOrder;
	}

	/**
	 * @param pcsOrder the pcsOrder to set
	 */
	public void setPcsOrder(String pcsOrder) {
		this.pcsOrder = pcsOrder;
	}

	/**
	 * @return the kgsOrder
	 */
	public String getKgsOrder() {
		return kgsOrder;
	}

	/**
	 * @param kgsOrder the kgsOrder to set
	 */
	public void setKgsOrder(String kgsOrder) {
		this.kgsOrder = kgsOrder;
	}

	/**
	 * @return the pendingKilos
	 */
	public String getPendingKilos() {
		return pendingKilos;
	}

	/**
	 * @param pendingKilos the pendingKilos to set
	 */
	public void setPendingKilos(String pendingKilos) {
		this.pendingKilos = pendingKilos;
	}

	/**
	 * @return the readyKilos
	 */
	public String getReadyKilos() {
		return readyKilos;
	}

	/**
	 * @param readyKilos the readyKilos to set
	 */
	public void setReadyKilos(String readyKilos) {
		this.readyKilos = readyKilos;
	}

	/**
	 * @return the loadingKilos
	 */
	public String getLoadingKilos() {
		return loadingKilos;
	}

	/**
	 * @param loadingKilos the loadingKilos to set
	 */
	public void setLoadingKilos(String loadingKilos) {
		this.loadingKilos = loadingKilos;
	}

	/**
	 * @return the balanceKilos
	 */
	public String getBalanceKilos() {
		return balanceKilos;
	}

	/**
	 * @param balanceKilos the balanceKilos to set
	 */
	public void setBalanceKilos(String balanceKilos) {
		this.balanceKilos = balanceKilos;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	



}
