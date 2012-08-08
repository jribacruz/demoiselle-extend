package br.gov.component.demoiselle.jsf.restriction.template;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public interface ProjectionBean<T> extends Serializable {
	Selection<?> projection(CriteriaBuilder cb, Root<T> p);
}
