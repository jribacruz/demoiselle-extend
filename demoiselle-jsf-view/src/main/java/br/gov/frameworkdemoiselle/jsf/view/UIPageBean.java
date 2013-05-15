package br.gov.frameworkdemoiselle.jsf.view;

import java.io.Serializable;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.util.Reflections;

public class UIPageBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UIDataTable<T> dataTable;

	@Inject
	private UIDialog<T> dialog;

	Class<T> pageClass;

	private Class<T> getPageClass() {
		if (pageClass == null) {
			this.pageClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
		}
		return this.pageClass;
	}

	public UIDataTable<T> getDataTable() {
		return dataTable;
	}

	public UIDialog<T> getDialog() {
		return dialog;
	}

}
