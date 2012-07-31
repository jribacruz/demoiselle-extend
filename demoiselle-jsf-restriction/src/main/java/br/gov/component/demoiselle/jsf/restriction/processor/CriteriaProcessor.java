package br.gov.component.demoiselle.jsf.restriction.processor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;

@SessionScoped
public class CriteriaProcessor implements Serializable {
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
		processProjection(cb, cq, p);
		processRestriction(cb, cq, p);

		TypedQuery<T> query = em.createQuery(cq);
		preparePagination(beanClass, query);

		context.clear();
		return query.getResultList();
	}

	public <T, I> T load(Class<T> beanClass, I id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);
		processProjection(cb, cq, p);
		processRestriction(beanClass, id, cb, cq, p);

		TypedQuery<T> query = em.createQuery(cq);
		context.clear();
		return query.getSingleResult();
	}

	private <T> Long countAll(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(beanClass);
		cq.select(cb.count(p));
		processRestriction(cb, cq, p);

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

	protected <T, X> void processRestriction(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		List<Predicate> restrictions = context.getPredicateList(cb, p);
		if (restrictions != null && !restrictions.isEmpty()) {
			cq.where(restrictions.toArray(new Predicate[] {}));
		}
	}

	protected <T, X, I> void processRestriction(Class<T> beanClass, I id, CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		List<Predicate> restrictions = context.getPredicateList(cb, p);
		restrictions.add(prepareLoadRestriction(beanClass, id, cb, p));
		if (restrictions != null && !restrictions.isEmpty()) {
			cq.where(restrictions.toArray(new Predicate[] {}));
		}
	}

	protected <T> void processProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		context.getProjection(cb, cq, p);
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

}
