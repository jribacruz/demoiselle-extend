package br.gov.frameworkdemoiselle.restriction.core;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;

import br.gov.frameworkdemoiselle.util.Reflections;

public abstract class DefaultLazyModel<T> extends LazyDataModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private Class<T> beanClass;

	protected int first;

	protected int pageSize;

	public int size() {
		return this.getRowCount();
	}

	protected List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getBeanClass());
		Root<T> p = cq.from(getBeanClass());

		cq.where(this.getPredicates(cb, p));
		cq.orderBy(this.getOrders(cb, p));

		TypedQuery<T> query = this.em.createQuery(cq);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	protected int countAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(getBeanClass());

		cq.select(cb.count(p));
		cq.where(this.getPredicates(cb, p));

		TypedQuery<Long> query = this.em.createQuery(cq);

		return query.getSingleResult().intValue();
	}

	protected Class<T> getBeanClass() {
		if (this.beanClass == null) {
			this.beanClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
		}
		return this.beanClass;
	}

	protected abstract Predicate[] getPredicates(CriteriaBuilder cb, Root<T> p);

	protected abstract List<Order> getOrders(CriteriaBuilder cb, Root<T> p);

}
