package br.gov.component.demoiselle.jsf.criteria.context;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.criteria.template.IRestriction;

public interface CriteriaContext extends Serializable {
	/**
	 * 
	 * @param criteriaClass
	 */
	@SuppressWarnings("rawtypes")
	public void setRestrictionClass(Class<? extends IRestriction> criteriaClass);

	/**
	 * 
	 * @param criteriaClass
	 * @param pageSize
	 */
	@SuppressWarnings("rawtypes")
	public void setRestrictionClass(Class<? extends IRestriction> criteriaClass, int pageSize);

	/**
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	public <T, X> List<Predicate> getRestriction(CriteriaBuilder cb, Root<T> p);

	/**
	 * 
	 * @param cb
	 * @param cq
	 * @param p
	 * @return
	 */
	public <T> void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <T> Class<? extends IRestriction> getRestrictionClass();

	/**
	 * 
	 */
	public <T> IRestriction<T> getRestrictionReference();

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

	/**
	 * 
	 */
	public void clear();
}
