package br.gov.frameworkdemoiselle.jsf.view;

import br.gov.frameworkdemoiselle.util.Reflections;

public class UIPageBean<T> {

	Class<T> pageClass;

	private Class<T> getPageClass() {
		if (pageClass == null) {
			this.pageClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
		}
		return this.pageClass;
	}

}
