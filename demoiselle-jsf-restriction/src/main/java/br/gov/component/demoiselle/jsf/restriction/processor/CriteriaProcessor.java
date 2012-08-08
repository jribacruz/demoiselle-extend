package br.gov.component.demoiselle.jsf.restriction.processor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;

@SessionScoped
public class CriteriaProcessor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@Inject
	private CriteriaProcessorContext processorContext;

	@Inject
	private Instance<PaginationContext> paginationContext;

	private Pagination pagination;

	private List<Predicate> predicateList;

	@Inject
	private EntityManager em;

	public <T> List<T> getResultList(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);
		processProjection(cb, cq, p, false);
		processRestriction(cb, cq, p);
		processOrder(cb, cq, p);

		TypedQuery<T> query = em.createQuery(cq);
		preparePagination(beanClass, query);

		processorContext.clear();
		return query.getResultList();
	}

	public <T, I> T load(Class<T> beanClass, I id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);
		processProjection(cb, cq, p, false);
		processRestriction(beanClass, id, cb, cq, p);

		TypedQuery<T> query = em.createQuery(cq);
		processorContext.clear();
		return query.getSingleResult();
	}

	private <T> Long countAll(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(beanClass);
		//cq.select(cb.count(p));
		this.<T,Long>processProjection(cb, cq, p, true);
		processRestriction(cb, cq, p);
		cq.where(getPredicateList().toArray(new Predicate[] {}));

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
			context.setPageSize(0);
		}
	}

	protected <T> Pagination getPagination(Class<T> beanClass) {
		if (pagination == null) {
			PaginationContext context = paginationContext.get();
			pagination = context.getPagination(beanClass);
		}
		return pagination;
	}

	protected <T, X> void processRestriction(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		this.predicateList = processorContext.getPredicateList(cb, p);
		if (this.predicateList != null && !this.predicateList.isEmpty()) {
			cq.where(this.predicateList.toArray(new Predicate[] {}));
		}
	}

	protected <T, X, I> void processRestriction(Class<T> beanClass, I id, CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		List<Predicate> restrictions = processorContext.getPredicateList(cb, p);
		restrictions.add(prepareLoadRestriction(beanClass, id, cb, p));
		if (restrictions != null && !restrictions.isEmpty()) {
			cq.where(restrictions.toArray(new Predicate[] {}));
		}
	}

	protected <T, X> void processProjection(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p, boolean countAllMethod) {
		Selection<?> selection = processorContext.getProjection(cb, p);
		if(selection != null && !countAllMethod) {
			cq.multiselect(selection);
		} else if(selection != null && countAllMethod) {
			cq.multiselect(selection, cb.count(p));
		} else if(countAllMethod) {
			cq.multiselect(cb.count(p));
		}
	}

	protected <T> void processOrder(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		List<Order> orders = processorContext.getOrderList(cb, p);
		if (orders != null && !orders.isEmpty()) {
			cq.orderBy(orders);
		}

	}

	private <T, I> Predicate prepareLoadRestriction(Class<T> beanClass, I id, CriteriaBuilder cb, Root<T> p) {
		return cb.equal(p.get(getId(beanClass)), id);
	}

	private <T> String getId(Class<T> beanClass) {
		for (Field field : beanClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				return field.getName();
			}
		}
		return null;
	}

	public List<Predicate> getPredicateList() {
		if (predicateList == null) {
			return new ArrayList<Predicate>();
		}
		return predicateList;
	}

	public void setPredicateList(List<Predicate> predicateList) {
		this.predicateList = predicateList;
	}

}
