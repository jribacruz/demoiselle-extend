package br.gov.component.demoiselle.jsf.restriction.producer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import br.gov.component.demoiselle.jsf.restriction.annotation.Criteria;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.component.demoiselle.jsf.restriction.exception.AnnotationCriteriaNotFoundException;
import br.gov.component.demoiselle.jsf.restrictions.util.Utils;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.util.Beans;

public class LazyDataModelProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext criteriaContext;

	@Inject
	private CriteriaProcessorContext processorContext;

	@SuppressWarnings("rawtypes")
	@Produces
	@Default
	public <T> LazyDataModel getLazyDataModel(final InjectionPoint ip) {
		final Field field = (Field) ip.getMember();
		validateCriteriaPresence(field);
		final AbstractListPageBean listPageBean = getListMB(ip);
		final Class<? extends AbstractCriteriaBean> criteria = getCriteriaClass(field);
		return getLazyDataModelInstance(listPageBean, criteria);
	}

	private void validateCriteriaPresence(Field field) {
		if (!field.isAnnotationPresent(Criteria.class)) {
			throw new AnnotationCriteriaNotFoundException("Annotation @Criteria não encontrada no campo: " + field.getName());
		}
	}

	@SuppressWarnings("rawtypes")
	private AbstractListPageBean getListMB(InjectionPoint ip) {
		return (AbstractListPageBean) Beans.getReference(ip.getMember().getDeclaringClass());
	}

	@SuppressWarnings("rawtypes")
	private Class<? extends AbstractCriteriaBean> getCriteriaClass(Field field) {
		return field.isAnnotationPresent(Criteria.class) ? field.getAnnotation(Criteria.class).value() : null;
	}

	@SuppressWarnings("rawtypes")
	private <T> LazyDataModel<T> getLazyDataModelInstance(final AbstractListPageBean listMB,
			final Class<? extends AbstractCriteriaBean> criteriaBeanClass) {
		return new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
				criteriaContext.setFilters(filters);
				criteriaContext.setSortField(sortField);
				criteriaContext.setSortOrder(sortOrder);
				processorContext.setCriteriaControllerClass(criteriaBeanClass);

				Pagination pagination = listMB.getPagination();
				pagination.setFirstResult(first);
				pagination.setPageSize(pageSize);
				List<T> list = Utils.invokeMethodReturnCollection(listMB, "handleResultList");
				this.setRowCount(pagination.getTotalResults());
				this.setPageSize(pageSize);
				return list;
			}
		};
	}
}
