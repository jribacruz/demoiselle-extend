package br.gov.frameworkdemoiselle.restriction2.producer;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.internal.configuration.PaginationConfig;
import br.gov.frameworkdemoiselle.restriction2.annotations.CriteriaBy;
import br.gov.frameworkdemoiselle.restriction2.processor.JPAProcessor;
import br.gov.frameworkdemoiselle.restriction2.template.CriteriaBean;
import br.gov.frameworkdemoiselle.util.Reflections;

public class LazyDataModelProducer {

	@Inject
	private Logger logger;

	@Inject
	private PaginationConfig paginationConfig;

	@Produces
	@CriteriaBy(value = CriteriaBean.class)
	public <T> LazyDataModel<T> create(InjectionPoint ip, @New JPAProcessor<T> processor) {

		logger.info("Inicializando LazyDataModel para classe {} ", new Object[] { getCriteriaClass(ip.getMember()).getName() });

		return this.getLazyDataModel(getCriteriaClass(ip.getMember()), this.<T> getBeanClass(ip.getMember()), processor);
	}

	@SuppressWarnings("rawtypes")
	private Class<? extends CriteriaBean> getCriteriaClass(Member member) {
		return ((Field) member).getAnnotation(CriteriaBy.class).value();
	}

	private <T> Class<T> getBeanClass(Member member) {
		return Reflections.<T> getGenericTypeArgument(member, 0);
	}

	@SuppressWarnings("rawtypes")
	private <T> LazyDataModel<T> getLazyDataModel(final Class<? extends CriteriaBean> criteriaClass, final Class<T> beanClass,
			final JPAProcessor<T> processor) {
		return new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

				pageSize = pageSize == 0 ? paginationConfig.getPageSize() : pageSize;
				processor.setCriteriaBeanClass(criteriaClass);
				List<T> list = processor.findAll(beanClass, first, pageSize, filters);
				this.setRowCount(processor.getRowCount());

				return list;
			}
		};
	}

}
