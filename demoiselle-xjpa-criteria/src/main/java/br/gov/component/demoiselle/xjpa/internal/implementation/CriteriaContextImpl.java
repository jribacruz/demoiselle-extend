package br.gov.component.demoiselle.xjpa.internal.implementation;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
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

import br.gov.component.demoiselle.xjpa.Criterion;
import br.gov.component.demoiselle.xjpa.internal.context.CriteriaContext;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
@Alternative
public class CriteriaContextImpl implements CriteriaContext {
	private static final long serialVersionUID = 1L;

	private boolean paginate = true;

	private Criterion criteria;

	private Map<String, Object> params = new HashMap<String, Object>();

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	public void criteria(Class<? extends Criterion> criteria) {
		this.criteria = Beans.getReference(criteria);
		logger.info("Active Criterion: [" + criteria + "]");
	}

	public void paginate(boolean flag) {
		this.paginate = flag;
	}

	public boolean isPaginated() {
		return this.paginate;
	}

	public void clearAll() {
		this.criteria = null;
		this.paginate = true;
	}

	public <T> Predicate getPredicate(CriteriaBuilder cb, Root<T> p) {
		if (criteria != null) {
			return criteria.restriction(cb, p);
		}
		return null;
	}

	protected <T> CompoundSelection<T> getProjection(CriteriaBuilder cb, Root<T> p) {
		if (criteria != null) {
			return criteria.projection(cb, p);
		}
		return null;
	}

	protected <T> Order getOrder(CriteriaBuilder cb, Root<T> p) {
		if (criteria != null) {
			return criteria.order(cb, p);
		}
		return null;
	}

	public <T> TypedQuery<T> getQuery(Class<T> bean) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(bean);
		Root<T> p = cq.from(bean);

		Predicate predicate1 = getPredicate(cb, p);
		if (predicate1 != null) {
			cq.where(predicate1);
		}

		CompoundSelection<T> compoundSelection = getProjection(cb, p);
		if (compoundSelection != null) {
			cq.select(compoundSelection);
		}

		Order order = getOrder(cb, p);

		if (order != null) {
			cq.orderBy(order);
		}

		return this.em.createQuery(cq);
	}

	public <T> TypedQuery<Long> getQueryCount(Class<T> bean) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(bean);
		cq.select(cb.count(p));

		Predicate predicate1 = getPredicate(cb, p);
		if (predicate1 != null) {
			cq.where(predicate1);
		}

		return this.em.createQuery(cq);
	}

	public void addCriteriaParam(String key, Object value) {
		this.params.put(key, value);
	}

	public Object getCriteriaParam(String key) {
		return this.params.get(key);
	}

	public void clearParams() {
		this.params.clear();
	}

}
