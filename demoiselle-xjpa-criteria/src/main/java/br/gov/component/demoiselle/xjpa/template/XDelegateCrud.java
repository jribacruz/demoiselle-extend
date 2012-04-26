package br.gov.component.demoiselle.xjpa.template;

import java.util.List;

import br.gov.component.demoiselle.xjpa.Criterion;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

public class XDelegateCrud<T, I, C extends XJPACrud<T, I>> extends DelegateCrud<T, I, XJPACrud<T, I>> {
	private static final long serialVersionUID = 1L;
	
	public List<T> findAll(Class<? extends Criterion> criterion, int pageSize) {
		return this.getDelegate().findAll(criterion, pageSize);
	}
}
