package br.gov.frameworkdemoiselle.restriction2.processor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction2.assembler.CriteriaAssembler;
import br.gov.frameworkdemoiselle.restriction2.template.CriteriaBean;

public class JPAProcessor<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Inject
	private CriteriaAssembler<T> assembler;

	private int rowCount;

	private String sortField;

	private SortOrder sortOrder;

	private Class<? extends CriteriaBean> criteriaBeanClass;

	public void orderData(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	public void setCriteriaBeanClass(Class<? extends CriteriaBean> criteriaBeanClass) {
		this.criteriaBeanClass = criteriaBeanClass;
	}

	public List<T> findAll(Class<T> beanClass, int first, int pageSize, Map<String, String> filters) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(beanClass);
		Root<T> p = cq.from(beanClass);

		assembler.assemblyPredicates(criteriaBeanClass, cb, cq, p, filters);

		TypedQuery<T> query = this.em.createQuery(cq);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);

		this.rowCount = this.countAll(beanClass, first, pageSize, filters).intValue();

		return query.getResultList();
	}

	protected Long countAll(Class<T> beanClass, int first, int pageSize, Map<String, String> filters) {

		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> p = cq.from(beanClass);
		cq.select(cb.count(p));

		assembler.assemblyPredicates(criteriaBeanClass, cb, cq, p, filters);

		TypedQuery<Long> query = this.em.createQuery(cq);

		return query.getSingleResult();
	}

	public int getRowCount() {
		return rowCount;
	}

	public String getSortField() {
		return sortField;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

}
