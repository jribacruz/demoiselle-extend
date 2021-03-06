package br.gov.frameworkdemoiselle.restriction.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.base.EPredicate;
import br.gov.frameworkdemoiselle.restriction.context.ModelContext;
import br.gov.frameworkdemoiselle.restriction.criterions.LikeCriterion;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.Sets;

public abstract class DefaultLazyModel<T> extends LazyDataModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	protected EntityManager em;

	protected int first;

	protected int pageSize;

	protected ModelContext<T> context;

	protected String query;

	protected String queryAttribute;

	protected EPredicate predicates;

	protected List<Order> orders;

	public DefaultLazyModel() {
		super();
		this.predicates = new EPredicate();
		this.orders = new ArrayList<Order>();
	}

	public int size() {
		return this.getRowCount();
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		this.first = first;
		this.pageSize = pageSize;
		context.setSortOrder(sortOrder, sortField);

		this.setRowCount(this.countAll());
		return this.findAll();
	}

	protected List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(this.getBeanClass());
		Root<T> p = cq.from(this.getBeanClass());

		this.processPredicates(cb, p);
		this.predicates.apply(cq);

		this.context.getOrders().apply(cb, cq, p);

		TypedQuery<T> query = this.em.createQuery(cq);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	protected List<T> findAll(int maxResults) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(this.getBeanClass());
		Root<T> p = cq.from(this.getBeanClass());

		this.processPredicates(cb, p);
		this.predicates.apply(cq);

		this.context.getOrders().apply(cb, cq, p);

		TypedQuery<T> query = this.em.createQuery(cq);
		// query.setFirstResult(first);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	protected int countAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(this.getBeanClass());

		cq.select(cb.count(p));
		this.processPredicates(cb, p);
		this.predicates.apply(cq);

		TypedQuery<Long> query = this.em.createQuery(cq);

		return query.getSingleResult().intValue();
	}

	@SuppressWarnings("unchecked")
	protected void processPredicates(CriteriaBuilder cb, Root<T> p) {
		this.predicates.clear();
		if (!Strings.isEmpty(query)) {
			LikeCriterion<T> c = Beans.getReference(LikeCriterion.class);
			c.setValue(query);
			c.setFields(this.resolveQueryAttributes());
			this.predicates.addAll(c.criterion(cb, p));
		}
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryAttribute() {
		return queryAttribute;
	}

	public void setQueryAttribute(String queryAttribute) {
		this.queryAttribute = queryAttribute;
	}

	protected Set<String> resolveQueryAttributes() {
		if (!Strings.isEmpty(this.queryAttribute)) {
			return Sets.newHashSet(this.queryAttribute);
		}
		return Sets.newHashSet(context.getQueryAttributes());
	}

	protected Class<T> getBeanClass() {
		return context.getBeanClass();
	}

	public List<T> complete(String query) {
		this.query = query;
		return this.findAll(context.getMaxResults());
	}

	public List<T> list() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(this.getBeanClass());
		Root<T> p = cq.from(this.getBeanClass());

		this.processPredicates(cb, p);
		this.predicates.apply(cq);

		this.context.getOrders().apply(cb, cq, p);

		TypedQuery<T> query = this.em.createQuery(cq);

		return query.getResultList();
	}

	public void clear() {
		this.query = new String();
	}
}
