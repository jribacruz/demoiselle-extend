package br.gov.frameworkdemoiselle.restriction.producer;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.restriction.Parameter;
import br.gov.frameworkdemoiselle.restriction.context.CriteriaContext;
import br.gov.frameworkdemoiselle.restriction.model.DataModel;
import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Reflections;

public class DataModelProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@Inject
	private Parameter parameter;

	public <T, C extends CriteriaBean<T>> DataModel<T, C> create(InjectionPoint ip) {
		return getDataModel(getListMB(ip), this.<T, C> getCriteriaClass(ip.getMember()));
	}

	@SuppressWarnings("rawtypes")
	private AbstractListPageBean getListMB(InjectionPoint ip) {
		return (AbstractListPageBean) Beans.getReference(ip.getMember().getDeclaringClass());
	}

	/**
	 * Retorna a classe de criteria do DataModel
	 * 
	 * @param member
	 * @return
	 */
	private <T, C extends CriteriaBean<T>> Class<C> getCriteriaClass(Member member) {
		return Reflections.<C> getGenericTypeArgument(member, 1);
	}

	@SuppressWarnings("rawtypes")
	private <T, C extends CriteriaBean<T>> DataModel<T, C> getDataModel(final AbstractListPageBean listBean, final Class<C> criteriaClass) {
		return new DataModel<T, C>() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

				context.registerCriteriaClass(criteriaClass);

				parameter.setField(sortField);
				parameter.setOrder(sortOrder);
				parameter.setFilters(filters);

				Pagination pagination = listBean.getPagination();
				pagination.setFirstResult(first);
				pagination.setPageSize(pageSize);

				listBean.clear();
				List<T> list = listBean.getResultList();
				this.setRowCount(pagination.getTotalResults());
				this.setPageSize(pageSize);
				return list;
			}

		};
	}

}
