package br.gov.frameworkdemoiselle.restriction.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.orderer.DataTableOrder;
import br.gov.frameworkdemoiselle.restriction.processor.RestrictionProcessor;
import br.gov.frameworkdemoiselle.util.Reflections;

public class DefaultLazyModel<T> extends LazyDataModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	protected JPABuilder<T> builder;

	protected Class<T> beanClass;

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		this.setRowCount(builder.countAll(getBeanClass(), new RestrictionProcessor<T>(this)));
		return builder
				.findAll(getBeanClass(), first, pageSize, new DataTableOrder(sortField, sortOrder), new RestrictionProcessor<T>(this));
	}

	protected Class<T> getBeanClass() {
		if (this.beanClass == null) {
			this.beanClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
		}
		return this.beanClass;
	}

}
