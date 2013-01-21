package br.gov.frameworkdemoiselle.restriction2.producer;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.restriction2.annotations.CriteriaBy;
import br.gov.frameworkdemoiselle.restriction2.template.CriteriaBean;

public class LazyDataModelProducer {

	@Inject
	private Logger logger;

	@Produces
	@CriteriaBy(value = CriteriaBean.class)
	public <T> LazyDataModel<T> create(InjectionPoint ip) {

		logger.info("Inicializando LazyDataModel para classe {} ", new Object[] { ip.getMember() });

		return getLazyDataModel();
	}

	private <T> LazyDataModel<T> getLazyDataModel() {
		return new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
				return null;
			}
		};
	}

}
