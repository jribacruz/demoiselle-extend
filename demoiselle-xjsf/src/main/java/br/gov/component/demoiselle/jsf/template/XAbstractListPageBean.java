package br.gov.component.demoiselle.jsf.template;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;

public abstract class XAbstractListPageBean<T, I> extends AbstractListPageBean<T, I> {
	private static final long serialVersionUID = 1L;
	private LazyDataModel<T> lazyDataModel;

	@SuppressWarnings("unused")
	private int countSelected;

	protected void initLazyDataModel() {
		this.lazyDataModel = new LazyDataModel<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
				Pagination pagination = getPagination();
				pagination.setFirstResult(first);
				pagination.setPageSize(pageSize);

				List<T> list = handleResultList(sortField, sortOrder, filters);
				this.setRowCount(pagination.getTotalResults());
				this.setPageSize(pageSize);

				return list;
			}

		};
	}

	public abstract String deleteSelection();

	protected abstract List<T> handleResultList(String sortField, SortOrder sortOrder, Map<String, String> filters);

	public LazyDataModel<T> getLazyDataModel() {
		if (lazyDataModel == null) {
			initLazyDataModel();
		}
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<T> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public int getCountSelected() {
		return countSelected;
	}

	public void setCountSelected(int countSelected) {
		this.countSelected = countSelected;
	}

}
