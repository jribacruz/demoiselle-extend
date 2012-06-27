package br.gov.component.demoiselle.jsf.template;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public interface IProjection<T> extends ICriteria<T> {
	public CompoundSelection<T> projection(CriteriaBuilder cb, Root<T> p);
}
