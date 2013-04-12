package br.gov.frameworkdemoiselle.restriction.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.orderer.DataTableOrderer;
import br.gov.frameworkdemoiselle.restriction.processor.FilterProcessor;
import br.gov.frameworkdemoiselle.restriction.processor.RestrictionProcessor;
import br.gov.frameworkdemoiselle.util.Reflections;

public class DefaultLazyCriteria<T> extends LazyDataModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	protected JPABuilder<T> builder;

	protected Class<T> beanClass;

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		this.setRowCount(builder.countAll(getBeanClass(), new FilterProcessor<T>(filters), new RestrictionProcessor<T>(this)));
		return builder.findAll(getBeanClass(), first, pageSize, new DataTableOrderer(sortField, sortOrder), new FilterProcessor<T>(filters),
				new RestrictionProcessor<T>(this));
	}

	protected Class<T> getBeanClass() {
		if (this.beanClass == null) {
			this.beanClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
		}
		return this.beanClass;
	}

	public Predicate defaultQuery(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

}
