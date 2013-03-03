package br.gov.component.demoiselle.jsf.restriction.processor;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JPAProcessor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public <T> List<T> findAll(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);

		TypedQuery<T> query = em.createQuery(cq);

		return query.getResultList();
	}

	protected <T> Long countAll(Class<T> beanClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(beanClass);
		cq.select(cb.count(p));

		TypedQuery<Long> query = em.createQuery(cq);
		return query.getSingleResult();
	}

}
