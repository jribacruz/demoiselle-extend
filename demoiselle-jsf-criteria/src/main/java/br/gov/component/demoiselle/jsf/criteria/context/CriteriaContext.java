package br.gov.component.demoiselle.jsf.criteria.context;

import java.io.Serializable;

import br.gov.component.demoiselle.jsf.template.ICriteria;

public interface CriteriaContext extends Serializable {
	/**
	 * 
	 * @param criteriaClass
	 */
	public void setCriteria(Class<?> criteriaClass);

	/**
	 * 
	 * @param criteriaClass
	 * @param pageSize
	 */
	public void setCriteria(Class<?> criteriaClass, int pageSize);

	/**
	 * 
	 * @return
	 */
	public <T> ICriteria<T> getCriteria();

	/**
	 * 
	 * @return
	 */
	public int getPageSize();

	/**
	 * 
	 * @param size
	 */
	public void setPageSize(int size);
}
