package br.gov.component.demoiselle.xjpa.template;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

	private Long countAll() {
		TypedQuery<Long> query = context.getQueryCount(getBeanClass());
		return query.getSingleResult();
	}

	public List<T> findAll(Class<? extends Criterion> criterion, int pageSize) {

		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getBeanClass());
		Root<T> p = cq.from(getBeanClass());

		Criterion c = Beans.getReference(criterion);
		Predicate predicate = null;
		if (c != null) {
			predicate = c.restriction(cb, p);
			CompoundSelection<T> compoundSelection = c.projection(cb, p);
			Order order = c.order(cb, p);
			if (predicate != null) {
				cq.where(predicate);
			}
			if (compoundSelection != null) {
				cq.select(compoundSelection);
			}
			if (order != null) {
				cq.orderBy(order);
			}
		}

		TypedQuery<T> query = this.getEntityManager().createQuery(cq);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

}
