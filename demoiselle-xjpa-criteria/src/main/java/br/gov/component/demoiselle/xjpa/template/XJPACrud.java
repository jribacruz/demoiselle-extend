package br.gov.component.demoiselle.xjpa.template;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.xjpa.CriteriaOrder;
import br.gov.component.demoiselle.xjpa.Criterion;
import br.gov.component.demoiselle.xjpa.internal.context.CriteriaContext;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.util.Beans;

public class XJPACrud<T, I> extends JPACrud<T, I> {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@Override
	public List<T> findAll() {
		TypedQuery<T> query = context.getQuery(getBeanClass());

		final Pagination pagination = getPagination();
		if (pagination != null && context.isPaginated()) {
			pagination.setTotalResults(this.countAll().intValue());
			query.setFirstResult(pagination.getFirstResult());
			query.setMaxResults(pagination.getPageSize());
		}

		return query.getResultList();
	}


	public List<T> findAll(Class<? extends Criterion<T>> criterion, int pageSize) {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getBeanClass());
		Root<T> p = cq.from(getBeanClass());

		processCriteria(criterion, cb, cq, p);
		TypedQuery<T> query = this.getEntityManager().createQuery(cq);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	public List<T> findAll(String sortField, CriteriaOrder order, Map<String, String> filters) {
		System.out.println("Filters: "+filters);
		context.criteria(getBeanClass(), order, sortField, filters);
		return this.findAll();
	}

	private Long countAll() {
		TypedQuery<Long> query = context.getQueryCount(getBeanClass());
		return query.getSingleResult();
	}

	private void processCriteria(Class<? extends Criterion<T>> criterion, CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		Criterion<T> c = Beans.getReference(criterion);
		if (c != null) {
			processCriteriaPredicate(c, cb, cq, p);
			processCriteriaProjection(c, cb, cq, p);
			processCriteriaOrder(c, cb, cq, p);
		}
	}

	private <X> void processCriteriaPredicate(Criterion<T> c, CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		Predicate predicate = c.restriction(cb, p);
		if (predicate != null) {
			cq.where(predicate);
		}
	}

	private void processCriteriaProjection(Criterion<T> c, CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		CompoundSelection<T> compoundSelection = c.projection(cb, p);
		if (compoundSelection != null) {
			cq.select(compoundSelection);
		}
	}

	private <X> void processCriteriaOrder(Criterion<T> c, CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		Order order = c.order(cb, p);
		if (order != null) {
			cq.orderBy(order);
		}
	}

}
