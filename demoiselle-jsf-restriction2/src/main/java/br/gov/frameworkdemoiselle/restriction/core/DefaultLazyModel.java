package br.gov.frameworkdemoiselle.restriction.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.context.ModelContext;
import br.gov.frameworkdemoiselle.restriction.orderer.DataTableOrder;

public class DefaultLazyModel<T> extends LazyDataModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	protected JPABuilder<T> builder;

	@Inject
	private ModelContext context;

	protected Criteria<T> criteria;

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		context.setDefaultLazyModel(this);
		this.setRowCount(builder.countAll());
		return builder.findAll(first, pageSize, new DataTableOrder(sortField, sortOrder));
	}

	public Criteria<T> getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria<T> criteria) {
		this.criteria = criteria;
	}

}
