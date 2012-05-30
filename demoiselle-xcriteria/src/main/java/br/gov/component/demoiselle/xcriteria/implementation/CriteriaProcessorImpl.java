package br.gov.component.demoiselle.xcriteria.implementation;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.gov.component.demoiselle.xcriteria.ICriterion;
import br.gov.component.demoiselle.xcriteria.context.CriteriaContext;
import br.gov.component.demoiselle.xcriteria.context.CriteriaProcessor;
import br.gov.component.demoiselle.xcriteria.processor.CriterionBeanProcessor;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;
import br.gov.frameworkdemoiselle.util.Strings;

@SessionScoped
public class CriteriaProcessorImpl implements CriteriaProcessor, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	private CriteriaBuilder cb;

	@Inject
	private EntityManager em;

	@Inject
	private Instance<PaginationContext> paginationContext;

	private Pagination pagination;

	private CriterionBeanProcessor criterionBeanProcessor;

	@SuppressWarnings("rawtypes")
	private Class beanClass;

	/**
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	private <T> Predicate[] getPredicate(ICriterion<T> criterion, Root<T> p) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (criterion.restriction(cb, p) != null) {
			predicates.add(criterion.restriction(cb, p));
		}

		if (getPredicateFromCriterionMapper(criterion, p) != null) {
			System.out.println("Aqui.......Mapper");
			predicates.add(getPredicateFromCriterionMapper(criterion, p));
		}

		if (getPredicateFromCriterionBean(p) != null && !getPredicateFromCriterionBean(p).isEmpty()) {
			System.out.println("Aqui.......Cb");
			predicates.addAll(getPredicateFromCriterionBean(p));
		}

		return predicates.toArray(new Predicate[] {});
	}

	private <T> Predicate getPredicateFromCriterionMapper(ICriterion<T> criterion, Root<T> p) {
		String query = this.criterionBeanProcessor.getMapper();
		if (!Strings.isEmpty(query)) {
			return criterion.mapper(cb, p, query);
		}
		return null;
	}

	private <T> List<Predicate> getPredicateFromCriterionBean(Root<T> p) {
		return criterionBeanProcessor.getRestricition(cb, p);
	}

	/**
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	private <T> CompoundSelection<T> getProjection(ICriterion<T> criterion, Root<T> p) {
		return criterion.projection(this.cb, p);
	}

	/**
	 * Retorna
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	private <T> List<Order> getOrder(ICriterion<T> criterion, Root<T> p) {
		List<Order> orders = new ArrayList<Order>();

		if (criterion.order(cb, p) != null) {
			orders.add(criterion.order(cb, p));
		}

		if (getOrderFromCriterionBean(p) != null && !getOrderFromCriterionBean(p).isEmpty()) {
			orders.addAll(getOrderFromCriterionBean(p));
		}

		return orders;
	}

	private <T> List<Order> getOrderFromCriterionBean(Root<T> p) {
		List<Order> orders = new ArrayList<Order>();
		if (criterionBeanProcessor.getOrder(cb, p) != null && !criterionBeanProcessor.getOrder(cb, p).isEmpty()) {
			orders.addAll(criterionBeanProcessor.getOrder(cb, p));
		}
		return orders;
	}

	/**
	 * 
	 * 
	 * @param cb
	 * @param cq
	 * @param p
	 */
	private <T> void processCriteria(CriteriaQuery<T> cq, Root<T> p) {
		ICriterion<T> criterion = context.getCriterion();
		if (criterion != null) {
			processPredicate(criterion, cq, p);
			processProjection(criterion, cq, p);
			processOrder(criterion, cq, p);
		}
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
	private <T, X> void processPredicate(ICriterion<T> criterion, CriteriaQuery<X> cq, Root<T> p) {
		Predicate[] predicates = getPredicate(criterion, p);
		if (predicates != null && predicates.length > 0) {
			cq.where(predicates);
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
	private <T> void processProjection(ICriterion<T> criterion, CriteriaQuery<T> cq, Root<T> p) {
		CompoundSelection<T> compoundSelection = getProjection(criterion, p);
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
	private <T> void processOrder(ICriterion<T> criterion, CriteriaQuery<T> cq, Root<T> p) {
		List<Order> orders = getOrder(criterion, p);
		if (orders != null && !orders.isEmpty()) {
			cq.orderBy(orders);
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

	@SuppressWarnings("unchecked")
	private <T> void preparePagination(TypedQuery<T> query) {
		if (context.isPaginated() && context.getPageSize() == 0) {
			final Pagination pagination = getPagination(beanClass);
			if (pagination != null) {
				pagination.setTotalResults(this.countAll().intValue());
				query.setFirstResult(pagination.getFirstResult());
				query.setMaxResults(pagination.getPageSize());
			}
		} else if (context.getPageSize() > 0) {
			query.setMaxResults(context.getPageSize());
		}
	}

	public <T> List<T> getResultList(Class<T> beanClass) {
		this.beanClass = beanClass;
		this.criterionBeanProcessor = new CriterionBeanProcessor(context.getCriterionBean());

		this.cb = this.em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);

		processCriteria(cq, p);

		TypedQuery<T> query = this.em.createQuery(cq);
		preparePagination(query);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	private <T> Long countAll() {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(beanClass);
		cq.select(cb.count(p));

		ICriterion<T> criterion = context.getCriterion();
		if (criterion != null) {
			processPredicate(criterion, cq, p);
		}

		TypedQuery<Long> query = this.em.createQuery(cq);
		return query.getSingleResult();
	}

}
