package br.gov.component.demoiselle.jsf.criteria.context;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.criteria.template.ICriteria;

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
	 * @param cb
	 * @param p
	 * @return
	 */
	public <T> Predicate[] getRestriction(CriteriaBuilder cb, Root<T> p);

	/**
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	public <T> List<Order> getOrder(CriteriaBuilder cb, Root<T> p);

	/**
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	public <T> CompoundSelection<T> getProjection(CriteriaBuilder cb, Root<T> p);

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

	/**
	 * 
	 * @param query
	 */
	public void setQuery(String query);

	/**
	 * 
	 * @return
	 */
	public String getQuery();
}
