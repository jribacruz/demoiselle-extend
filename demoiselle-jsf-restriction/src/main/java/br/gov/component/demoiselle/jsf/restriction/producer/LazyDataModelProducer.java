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
import br.gov.component.demoiselle.jsf.restriction.annotation.BC;
import br.gov.component.demoiselle.jsf.restriction.annotation.Criteria;
import br.gov.component.demoiselle.jsf.restriction.exception.AnnotationBCNotFoundException;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.util.Beans;

public class LazyDataModelProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Produces
	@Default
	public <T> LazyDataModel getLazyDataModel(final InjectionPoint ip) {
		final Field field = (Field) ip.getMember();
		final AbstractListPageBean listPageBean = getListMB(ip);
		validateBCpresence(field);
		final DelegateCrud bc = getBC(field);
		final AbstractCriteriaBean criteria = getCriteria(field);
		System.out.println("Produzindo a listagem....");
		return getLazyDataModelInstance(bc, listPageBean, criteria);
	}

	private void validateBCpresence(Field field) {
		if (!field.isAnnotationPresent(BC.class)) {
			throw new AnnotationBCNotFoundException("Annotation @BC não encontrada no campo: " + field.getName());
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
	private AbstractCriteriaBean getCriteria(Field field) {
		if (field.isAnnotationPresent(Criteria.class)) {
			return Beans.getReference(field.getAnnotation(Criteria.class).value());
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private <T> LazyDataModel<T> getLazyDataModelInstance(final DelegateCrud bc, final AbstractListPageBean listMB,
			final AbstractCriteriaBean criteria) {
		return new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
				if (criteria != null) {
					criteria.setFilter(filters);
					criteria.setSortField(sortField);
					criteria.setSortOrder(sortOrder);
					System.out.println("Setando os criterios: ");
				}
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
