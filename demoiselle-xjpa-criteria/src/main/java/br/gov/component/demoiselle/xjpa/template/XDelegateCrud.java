package br.gov.component.demoiselle.xjpa.template;

import java.util.List;
import java.util.Map;

import br.gov.component.demoiselle.xjpa.CriteriaOrder;
import br.gov.component.demoiselle.xjpa.Criterion;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

public class XDelegateCrud<T, I, C extends XJPACrud<T, I>> extends DelegateCrud<T, I, C> {
	private static final long serialVersionUID = 1L;

	public List<T> findAll(Class<? extends Criterion<T>> criterion, int pageSize) {
		return this.getDelegate().findAll(criterion, pageSize);
	}

	public List<T> findAll(String sortField, CriteriaOrder order, Map<String, String> filters) {
		return this.getDelegate().findAll(sortField, order, filters);
	}

}
