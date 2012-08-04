package br.gov.component.demoiselle.jsf.restriction.template;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface ProjectionBean<T> extends Serializable {
	void projection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);
}
