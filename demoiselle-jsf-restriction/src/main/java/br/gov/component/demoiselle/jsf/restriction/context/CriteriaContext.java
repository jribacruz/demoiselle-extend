package br.gov.component.demoiselle.jsf.restriction.context;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;

public interface CriteriaContext extends Serializable {
	void setCriteriaControllerClass(Class<? extends AbstractCriteriaBean> criteriaBeanClass);

	Class<? extends AbstractCriteriaBean> getCriteriaControllerClass();

	<T> List<Predicate> getPredicateList(CriteriaBuilder cb, Root<T> p);

	<T> void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);

	public int getPageSize();

	public void setPageSize(int size);

	void clear();

}
