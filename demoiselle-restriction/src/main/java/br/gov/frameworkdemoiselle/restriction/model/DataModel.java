package br.gov.frameworkdemoiselle.restriction.model;

import org.primefaces.model.LazyDataModel;

import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;

public abstract class DataModel<T, C extends CriteriaBean<T>> extends LazyDataModel<T> {
	private static final long serialVersionUID = 1L;
}
