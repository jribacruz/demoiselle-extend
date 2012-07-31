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

import br.gov.component.demoiselle.jsf.restriction.annotation.BC;
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
		Field field = (Field) ip.getMember();
		final AbstractListPageBean listPageBean = (AbstractListPageBean) Beans.getReference(ip.getMember()
				.getDeclaringClass());
		if (!field.isAnnotationPresent(BC.class)) {
			throw new AnnotationBCNotFoundException("Annotation @BC não encontrada no campo: " + field.getName());
		}
		final DelegateCrud bc = Beans.getReference(field.getAnnotation(BC.class).value());

		return new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
				Pagination pagination = listPageBean.getPagination();
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
