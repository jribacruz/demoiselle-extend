package br.gov.frameworkdemoiselle.restriction.core;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class Criteria<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public Predicate predicate(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

}
