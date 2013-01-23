package br.gov.frameworkdemoiselle.restriction2.processor;

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

	private int rowCount;

	public <T> List<T> findAll(Class<T> beanClass, int first, int pageSize) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);

		TypedQuery<T> query = this.em.createQuery(cq);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);

		this.rowCount = this.<T> countAll(beanClass, first, pageSize).intValue();

		return query.getResultList();
	}

	protected <T> Long countAll(Class<T> beanClass, int first, int pageSize) {

		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(beanClass);
		cq.select(cb.count(p));

		TypedQuery<Long> query = this.em.createQuery(cq);

		return query.getSingleResult();
	}

	public int getRowCount() {
		return rowCount;
	}

}
