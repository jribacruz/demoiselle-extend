package br.gov.frameworkdemoiselle.restriction.template;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class CriteriaBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public CompoundSelection<T> projection(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	public List<Order> orderBy(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

}
