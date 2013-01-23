package br.gov.frameworkdemoiselle.restriction2.producer;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.internal.configuration.PaginationConfig;
import br.gov.frameworkdemoiselle.restriction2.annotations.CriteriaBy;
import br.gov.frameworkdemoiselle.restriction2.base.RestrictionMap;
import br.gov.frameworkdemoiselle.restriction2.processor.JPAProcessor;
import br.gov.frameworkdemoiselle.restriction2.template.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;

public class LazyDataModelProducer {

	@Inject
	private Logger logger;

	@Inject
	private JPAProcessor processor;

	@Inject
	private PaginationConfig paginationConfig;

	@Produces
	@CriteriaBy(value = CriteriaBean.class)
	public <T> LazyDataModel<T> create(InjectionPoint ip) {

		logger.info("Inicializando LazyDataModel para classe {} ", new Object[] { getCriteriaClass(ip.getMember()).getName() });

		// for (Field field :
		// getCriteriaClass(ip.getMember()).getDeclaredFields()) {
		// if (RestrictionBean.class.isAssignableFrom(field.getType())) {
		// RestrictionMap map = new
		// RestrictionMap(getCriteriaClass(ip.getMember()), field, null);
		// System.out.println(map);
		// System.out.println(map.getRestrictionBean());
		// }
		// }

		return this.getLazyDataModel(getCriteriaClass(ip.getMember()), this.<T> getBeanClass(ip.getMember()));
	}

	@SuppressWarnings("rawtypes")
	private Class<? extends CriteriaBean> getCriteriaClass(Member member) {
		return ((Field) member).getAnnotation(CriteriaBy.class).value();
	}

	private <T> Class<T> getBeanClass(Member member) {
		return Reflections.<T> getGenericTypeArgument(member, 0);
	}

	@SuppressWarnings("rawtypes")
	private <T> LazyDataModel<T> getLazyDataModel(final Class<? extends CriteriaBean> criteriaClass, final Class<T> beanClass) {
		return new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

				pageSize = pageSize == 0 ? paginationConfig.getPageSize() : pageSize;
				
				List<T> list = processor.findAll(beanClass, first, pageSize);
				this.setRowCount(processor.getRowCount());

				return list;
			}
		};
	}

}
