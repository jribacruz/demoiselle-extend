package br.gov.frameworkdemoiselle.restriction.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.Orderer;
import br.gov.frameworkdemoiselle.restriction.context.ModelContext;

public class JPABuilder<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Inject
	private ModelContext context;

	public List<T> findAll(int first, int pageSize, Orderer orderer) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(context.<T> getBeanClass());
		Root<T> p = cq.from(context.<T> getBeanClass());

		List<Predicate> predicates = new ArrayList<Predicate>();

		// restrictionProcessor.apply(cb, p, predicates);
		cq.orderBy(orderer.apply(cb, p));

		if (!predicates.isEmpty()) {
			cq.where(predicates.toArray(new Predicate[] {}));
		}

		TypedQuery<T> query = em.createQuery(cq);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	public int countAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(context.<T> getBeanClass());

		List<Predicate> predicates = new ArrayList<Predicate>();

		// restrictionProcessor.apply(cb, p, predicates);

		cq.where(predicates.toArray(new Predicate[] {}));

		cq.select(cb.count(p));

		TypedQuery<Long> query = em.createQuery(cq);

		return query.getSingleResult().intValue();
	}

}
