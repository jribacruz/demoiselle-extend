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
import br.gov.component.demoiselle.jsf.restriction.annotation.BC;
import br.gov.component.demoiselle.jsf.restriction.annotation.Criteria;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.exception.AnnotationBCNotFoundException;
import br.gov.component.demoiselle.jsf.restriction.exception.AnnotationCriteriaNotFoundException;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.util.Beans;

public class LazyDataModelProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext criteriaContext;

	@SuppressWarnings("rawtypes")
	@Produces
	@Default
	public <T> LazyDataModel getLazyDataModel(final InjectionPoint ip) {
		final Field field = (Field) ip.getMember();
		validateBCPresence(field);
		validateCriteriaPresence(field);
		final AbstractListPageBean listPageBean = getListMB(ip);
		final DelegateCrud bc = getBC(field);
		final Class<? extends AbstractCriteriaBean> criteria = getCriteriaClass(field);
		System.out.println("Produzindo a listagem....");
		return getLazyDataModelInstance(bc, listPageBean, criteria);
	}

	private void validateBCPresence(Field field) {
		if (!field.isAnnotationPresent(BC.class)) {
			throw new AnnotationBCNotFoundException("Annotation @BC não encontrada no campo: " + field.getName());
		}
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
	private DelegateCrud getBC(Field field) {
		return Beans.getReference(field.getAnnotation(BC.class).value());
	}

	@SuppressWarnings("rawtypes")
	private Class<? extends AbstractCriteriaBean> getCriteriaClass(Field field) {
		return field.isAnnotationPresent(Criteria.class) ? field.getAnnotation(Criteria.class).value() : null;
	}

	@SuppressWarnings("rawtypes")
	private <T> LazyDataModel<T> getLazyDataModelInstance(final DelegateCrud bc, final AbstractListPageBean listMB,
			final Class<? extends AbstractCriteriaBean> criteriaBeanClass) {
		return new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

				criteriaContext.setFilters(filters);
				criteriaContext.setSortField(sortField);
				criteriaContext.setSortOrder(sortOrder);
				criteriaContext.setCriteriaControllerClass(criteriaBeanClass);
				System.out.println("Setando bean class: " + criteriaBeanClass.getCanonicalName());

				Pagination pagination = listMB.getPagination();
				pagination.setFirstResult(first);
				pagination.setPageSize(pageSize);
				List<T> lists = bc.findAll();
				this.setRowCount(pagination.getTotalResults());
				this.setPageSize(pageSize);
				return lists;
			}
		};
	}
}
