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

import org.primefaces.model.SortOrder;

import com.google.common.collect.ArrayListMultimap;

public abstract class AbstractCriteria<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private Class<T> beanClass;

	protected ArrayListMultimap<SortOrder, String> orderMap = ArrayListMultimap.create();

	protected abstract List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> p);

	protected abstract List<Order> getOrders(CriteriaBuilder cb, Root<T> p);

	public void addOrder(SortOrder order, String value) {
		this.orderMap.put(order, value);
	}

	public List<T> findAll(int first, int pageSize) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getBeanClass());
		Root<T> p = cq.from(getBeanClass());

		List<Predicate> predicates = getPredicates(cb, p);
		if (predicates != null && !predicates.isEmpty()) {
			cq.where(predicates.toArray(new Predicate[] {}));
		}

		List<Order> orders = getOrders(cb, p);
		if (orders != null && !orders.isEmpty()) {
			cq.orderBy(orders);
		}

		TypedQuery<T> query = em.createQuery(cq);

		return query.getResultList();
	}

	public int countAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(getBeanClass());

		cq.select(cb.count(p));

		List<Predicate> predicates = getPredicates(cb, p);
		if (predicates != null && !predicates.isEmpty()) {
			cq.where(predicates.toArray(new Predicate[] {}));
		}

		TypedQuery<Long> query = em.createQuery(cq);

		return query.getSingleResult().intValue();
	}

	public void setBeanClass(Class<T> beanClass) {
		this.beanClass = beanClass;
	}

	protected Class<T> getBeanClass() {
		return beanClass;
	}

}
