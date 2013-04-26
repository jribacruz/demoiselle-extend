package br.gov.frameworkdemoiselle.restriction;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class CriteriaBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Predicate restriction(CriteriaBuilder cb, Root<T> p) {
		return null;
	}
}
