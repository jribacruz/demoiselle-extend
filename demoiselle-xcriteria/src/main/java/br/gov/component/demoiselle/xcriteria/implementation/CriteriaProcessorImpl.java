package br.gov.component.demoiselle.xcriteria.implementation;

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

import br.gov.component.demoiselle.xcriteria.CriteriaContext;
import br.gov.component.demoiselle.xcriteria.CriteriaProcessor;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;

@SessionScoped
public class CriteriaProcessorImpl implements CriteriaProcessor {

	@Inject
	private CriteriaContext context;

	@Inject
	private EntityManager em;

	@Inject
	private Instance<PaginationContext> paginationContext;

	private Pagination pagination;

	/**
	 * 
	 */
	public <T> List<T> findAll(Class<T> beanClass) {
		TypedQuery<T> query = this.getQuery(beanClass);

		final Pagination pagination = getPagination(beanClass);
		if (pagination != null && context.isPaginated()) {
			pagination.setTotalResults(this.countAll(beanClass).intValue());
			query.setFirstResult(pagination.getFirstResult());
			query.setMaxResults(pagination.getPageSize());
		}

		return query.getResultList();
	}

	/**
	 * 
	 */
	public <T> TypedQuery<T> getQuery(Class<T> bean) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(bean);
		Root<T> p = cq.from(bean);

		processCriterion(cb, cq, p);
		return this.em.createQuery(cq);
	}

	/**
	 * 
	 */
	public <T> TypedQuery<Long> getQueryCount(Class<T> bean) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(bean);
		cq.select(cb.count(p));

		processCriterionPredicate(cb, cq, p);
		return this.em.createQuery(cq);
	}

	private <T> Long countAll(Class<T> beanClass) {
		TypedQuery<Long> query = this.getQueryCount(beanClass);
		return query.getSingleResult();
	}

	/**
	 * Retorna o predicado do criterion do contexto
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	private <T> Predicate getPredicate(CriteriaBuilder cb, Root<T> p) {
		if (context.getCriterion() != null) {
			return context.<T> getCriterion().restriction(cb, p);
		}
		return null;
	}

	/**
	 * Retorna a projecao do criterion do contexto
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	private <T> CompoundSelection<T> getProjection(CriteriaBuilder cb, Root<T> p) {
		if (context.getCriterion() != null) {
			return context.<T> getCriterion().projection(cb, p);
		}
		return null;
	}

	/**
	 * Retorna
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	private <T> Order getOrder(CriteriaBuilder cb, Root<T> p) {
		if (context.getCriterion() == null) {
			return null;
		}
		return context.<T> getCriterion().order(cb, p);
	}

	/**
	 * 
	 * 
	 * @param cb
	 * @param cq
	 * @param p
	 */
	private <T> void processCriterion(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		processCriterionPredicate(cb, cq, p);
		processCriterionProjection(cb, cq, p);
		processCriterionOrder(cb, cq, p);
	}

	/**
	 * Processa os dados do predicado (Clausula where)
	 * 
	 * @param cb
	 *            CriteriaBuilder
	 * @param cq
	 *            CriteriaQuery
	 * @param p
	 *            Root
	 */
	private <T, X> void processCriterionPredicate(CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p) {
		Predicate predicate = getPredicate(cb, p);
		if (predicate != null) {
			cq.where(predicate);
		}
	}

	/**
	 * Processa os dados da projecao (Clausula select)
	 * 
	 * @param cb
	 *            CriteriaBuilder
	 * @param cq
	 *            CriteriaQuery
	 * @param p
	 *            Root
	 */
	private <T> void processCriterionProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		CompoundSelection<T> compoundSelection = getProjection(cb, p);
		if (compoundSelection != null) {
			cq.select(compoundSelection);
		}
	}

	/**
	 * Processa os dados de ordenação (Clausula order by)
	 * 
	 * @param cb
	 *            CriteriaBuilder
	 * @param cq
	 *            CriteriaQuery
	 * @param p
	 *            Root
	 */
	private <T> void processCriterionOrder(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		Order order = getOrder(cb, p);
		if (order != null) {
			cq.orderBy(order);
		}
	}

	/**
	 * Retorna os dados do paginador
	 * 
	 * @param beanClass
	 * @return Retorna o paginador
	 */
	protected <T> Pagination getPagination(Class<T> beanClass) {
		if (pagination == null) {
			PaginationContext context = paginationContext.get();
			pagination = context.getPagination(beanClass);
		}
		return pagination;
	}

}
