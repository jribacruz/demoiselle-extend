package br.gov.component.demoiselle.xjpa.implementation;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import br.gov.component.demoiselle.xjpa.CriteriaOrder;
import br.gov.component.demoiselle.xjpa.Criterion;
import br.gov.component.demoiselle.xjpa.common.CommonDataTableCriteria;
import br.gov.component.demoiselle.xjpa.context.CriteriaContext;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {
	private static final long serialVersionUID = 1L;

	private boolean paginate = true;

	@SuppressWarnings("rawtypes")
	private Criterion criteria;

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	private String sortField;

	private CriteriaOrder order;

	private Map<String, Object> params = new HashMap<String, Object>();

	@Override
	@SuppressWarnings("rawtypes")
	public void criteria(Class<? extends Criterion> criteria) {
		this.criteria = Beans.getReference(criteria);
		logger.debug("Criterion ativo {}", this.criteria);
	}

	@Override
	public <E> void criteria(Class<E> beanClass, CriteriaOrder criteriaOrder, String sortField, Map<String, String> filters) {
		this.criteria = new CommonDataTableCriteria<E>(beanClass, criteriaOrder, sortField, filters);
	}

	@Override
	public void clearAll() {
		this.criteria = null;
		this.paginate = true;
		this.sortField = null;
		this.params.clear();
	}

	@SuppressWarnings("unchecked")
	public <T> Predicate getPredicate(CriteriaBuilder cb, Root<T> p) {
		if (criteria != null) {
			return criteria.restriction(cb, p);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected <T> CompoundSelection<T> getProjection(CriteriaBuilder cb, Root<T> p) {
		if (criteria != null) {
			return criteria.projection(cb, p);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected <T> Order getOrder(CriteriaBuilder cb, Root<T> p) {
		if (criteria == null) {
			return null;
		}
		return criteria.order(cb, p);
	}

	@Override
	public <T> TypedQuery<T> getQuery(Class<T> bean) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(bean);
		Root<T> p = cq.from(bean);

		processCriteria(cb, cq, p);
		return this.em.createQuery(cq);
	}

	@Override
	public <T> TypedQuery<Long> getQueryCount(Class<T> bean) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(bean);
		cq.select(cb.count(p));

		processCriteriaPredicate(cb, cq, p);
		return this.em.createQuery(cq);
	}

	private <T> void processCriteria(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		processCriteriaPredicate(cb, cq, p);
		processCriteriaProjection(cb, cq, p);
		processCriteriaOrder(cb, cq, p);
	}

	private <T, X> void processCriteriaPredicate(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		Predicate predicate = getPredicate(cb, p);
		if (predicate != null) {
			cq.where(predicate);
		}
	}

	private <T> void processCriteriaProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		CompoundSelection<T> compoundSelection = getProjection(cb, p);
		if (compoundSelection != null) {
			cq.select(compoundSelection);
		}
	}

	private <T> void processCriteriaOrder(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		Order order = getOrder(cb, p);
		if (order != null) {
			cq.orderBy(order);
		}
	}

	@Override
	public void addParam(String key, Object value) {
		this.params.put(key, value);
	}

	@Override
	public Object getParam(String key) {
		return this.params.get(key);
	}

	@Override
	public void clearParams() {
		this.params.clear();
	}

	public void orderBy(String sortField, CriteriaOrder order) {
		this.sortField = sortField;
		this.order = order;
	}

	public String getSortField() {
		return this.sortField;
	}

	@Override
	public CriteriaOrder getOrder() {
		return this.order;
	}

	@Override
	public void paginate(boolean flag) {
		this.paginate = flag;
	}

	@Override
	public boolean isPaginated() {
		return this.paginate;
	}

}
