package br.gov.component.demoiselle.jsf.criteria.implementation;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.criteria.processor.CriteriaProcessor;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;

@SessionScoped
public class CriteriaProcessorImpl implements CriteriaProcessor {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@Inject
	private Instance<PaginationContext> paginationContext;

	private Pagination pagination;

	@Inject
	private EntityManager em;

	public <T> List<T> getResultList(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);
		processCriteria(cb, cq, p);

		TypedQuery<T> query = em.createQuery(cq);
		preparePagination(beanClass, query);

		return query.getResultList();
	}

	private <T> Long countAll(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(beanClass);
		cq.select(cb.count(p));
		processPredicate(cb, cq, p);

		TypedQuery<Long> query = em.createQuery(cq);
		return query.getSingleResult();
	}

	private <T> void preparePagination(Class<T> beanClass, TypedQuery<T> query) {
		if (context.getPageSize() == 0) {
			final Pagination pagination = getPagination(beanClass);
			if (pagination != null) {
				pagination.setTotalResults(this.countAll(beanClass).intValue());
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(pagination.getPageSize());
			}
		} else {
			query.setMaxResults(context.getPageSize());
		}
	}

	protected <T> Pagination getPagination(Class<T> beanClass) {
		if (pagination == null) {
			PaginationContext context = paginationContext.get();
			pagination = context.getPagination(beanClass);
		}
		return pagination;
	}

	protected <T, X> void processPredicate(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		Predicate[] restrictions = context.getRestriction(cb, p);
		if (restrictions != null && restrictions.length > 0) {
			cq.where(restrictions);
		}
	}

	protected <T> void processCriteria(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		processPredicate(cb, cq, p);

		List<Order> order = context.getOrder(cb, p);
		if (order != null && !order.isEmpty()) {
			cq.orderBy(order);
		}

		CompoundSelection<T> selection = context.getProjection(cb, cq, p);
		if (selection != null) {
			cq.select(selection);
		}
	}

}
