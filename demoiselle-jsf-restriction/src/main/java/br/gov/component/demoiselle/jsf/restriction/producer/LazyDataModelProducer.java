package br.gov.component.demoiselle.jsf.restriction.producer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import br.gov.component.demoiselle.jsf.restriction.qualifier.CriteriaBy;

public class LazyDataModelProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Produces
	@Default
	@CriteriaBy(value = AbstractCriteriaBean.class)
	public <T> LazyDataModel<T> getLazyDataModel(final InjectionPoint ip) {
		final Field field = (Field) ip.getMember();
		final Class<? extends AbstractCriteriaBean> criteria = field.getAnnotation(CriteriaBy.class).value();
		return getLazyDataModelInstance(criteria);
	}

	@SuppressWarnings("rawtypes")
	private <T> LazyDataModel<T> getLazyDataModelInstance(final Class<? extends AbstractCriteriaBean> criteriaBeanClass) {
		return new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

				return null;
			}
		};
	}
}
