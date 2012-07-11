package br.gov.component.demoiselle.jsf.criteria.template;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface IProjection<T> extends ICriteria<T> {
	public CompoundSelection<T> projection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);
}
