package br.gov.component.demoiselle.jsf.template;

import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;


public abstract class XAbstractEditPageBean<T, I> extends AbstractEditPageBean<T, I> {
	private static final long serialVersionUID = 1L;

	public abstract String create();

	public abstract String edit(I id);
	
	public abstract String save();
}
