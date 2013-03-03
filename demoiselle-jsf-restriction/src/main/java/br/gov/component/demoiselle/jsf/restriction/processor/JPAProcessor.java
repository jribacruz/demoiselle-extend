package br.gov.component.demoiselle.jsf.restriction.processor;

import java.io.Serializable;
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

import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.component.demoiselle.jsf.restrictions.util.Utils;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;

@SessionScoped
public class JPAProcessor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaProcessorContext processorContext;

	@Inject
	private Instance<PaginationContext> paginationContext;

	private Pagination pagination;

	@Inject
	private EntityManager em;

	private List<Predicate> predicateList;

	public <T> List<T> findAll(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);
		processProjection(cb, cq, p);
		processRestriction(cb, cq, p);
		processOrder(cb, cq, p);

		TypedQuery<T> query = em.createQuery(cq);
		preparePagination(beanClass, query);

		processorContext.clear();
		return query.getResultList();
	}

	protected <T> Long countAll(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(beanClass);
		cq.select(cb.count(p));
		// this.<T, Long> processProjection(cb, cq, p, true);
		processRestriction(cb, cq, p);
		// cq.where(getPredicateList().toArray(new Predicate[] {}));

		TypedQuery<Long> query = em.createQuery(cq);
		return query.getSingleResult();
	}

	protected <T> Pagination getPagination(Class<T> beanClass) {
		PaginationContext context = paginationContext.get();
		pagination = context.getPagination(beanClass);
		return pagination;
	}

	private <T> void preparePagination(Class<T> beanClass, TypedQuery<T> query) {
		final Pagination pagination = this.<T> getPagination(beanClass);
		if (pagination != null) {
			pagination.setTotalResults(this.<T> countAll(beanClass).intValue());
			query.setFirstResult(pagination.getFirstResult());
			query.setMaxResults(pagination.getPageSize());
		}
	}

	protected <T, X> void processRestriction(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		this.predicateList = processorContext.getPredicates(cb, p);
		if (this.predicateList != null && !this.predicateList.isEmpty()) {
			cq.where(this.predicateList.toArray(new Predicate[] {}));
		}
	}

	protected <T, X, I> void processRestriction(Class<T> beanClass, I id, CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		List<Predicate> restrictions = processorContext.getPredicates(cb, p);
		restrictions.add(prepareLoadRestriction(beanClass, id, cb, p));
		if (restrictions != null && !restrictions.isEmpty()) {
			cq.where(restrictions.toArray(new Predicate[] {}));
		}
	}

	protected <T, X> void processProjection(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		CompoundSelection<X> compoundSelection = processorContext.getProjection(cb, p);
		if (compoundSelection != null) {
			cq.select(compoundSelection);
		}
	}

	protected <T, X> void processOrder(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		List<Order> orders = processorContext.getOrders(cb, p);
		if (orders != null && !orders.isEmpty()) {
			cq.orderBy(orders);
		}

	}

	private <T, I> Predicate prepareLoadRestriction(Class<T> beanClass, I id, CriteriaBuilder cb, Root<T> p) {
		return cb.equal(p.get(Utils.getId(beanClass)), id);
	}

	public void setPredicateList(List<Predicate> predicateList) {
		this.predicateList = predicateList;
	}

	public boolean hasCriteria() {
		return processorContext.getCriteriaControllerClass() != null;
	}

}
