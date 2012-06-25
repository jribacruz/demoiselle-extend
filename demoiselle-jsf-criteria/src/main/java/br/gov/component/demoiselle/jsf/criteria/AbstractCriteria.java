package br.gov.component.demoiselle.jsf.criteria;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class AbstractCriteria<T, X> {
	protected CriteriaBuilder cb;
	protected Root<T> p;
	protected X criterion;

	/**
	 * Retorna a restricao da query
	 * 
	 * @return
	 */
	public abstract Predicate restriction();

	/**
	 * Retorna a ordem da query
	 * 
	 * @return
	 */
	public abstract Order order();

	/**
	 * Retorna a projecao da query
	 * 
	 * @return
	 */
	public abstract CompoundSelection<T> projection();

	/**
	 * Retorna a restricao da query apenas se o mapper do criterion não for nulo
	 * ou branco
	 * 
	 * @param q
	 * @return
	 */
	public abstract Predicate restriction(String q);

	public X getCriterion() {
		return this.criterion;
	}

	public Predicate criterionRestriction() {
		return null;
	}

	public void setCriteriaBuilder(CriteriaBuilder cb) {
		this.cb = cb;
	}

	public void setRoot(Root<T> p) {
		this.p = p;
	}

	public void filter() {

	}

	public void filter(int pageSize) {

	}

}
