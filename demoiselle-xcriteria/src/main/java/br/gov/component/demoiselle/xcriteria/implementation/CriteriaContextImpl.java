package br.gov.component.demoiselle.xcriteria.implementation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import br.gov.component.demoiselle.xcriteria.ICriterion;
import br.gov.component.demoiselle.xcriteria.context.CriteriaContext;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext, Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> params;
	private boolean paginated;
	private boolean filtered;
	private int pageSize;

	@SuppressWarnings("rawtypes")
	private ICriterion criterion;

	private Object criterionBean;

	public CriteriaContextImpl() {
		super();
		this.params = new HashMap<String, Object>();
		this.paginated = true;
		this.pageSize = 0;
	}

	public void setPaginated(boolean flag) {
		this.paginated = flag;
	}

	public boolean isPaginated() {
		return this.paginated;
	}

	public <T> void setCriteria(Class<? extends ICriterion<T>> criterionClass, Object criterionBean) {
		this.pageSize = 0;
		this.criterion = Beans.getReference(criterionClass);
		this.criterionBean = criterionBean;
	}

	public <T> void setCriteria(Class<? extends ICriterion<T>> criterionClass, Object criterionBean, int pageSize) {
		this.pageSize = pageSize;
		this.criterion = Beans.getReference(criterionClass);
		this.criterionBean = criterionBean;
	}

	public <T> ICriterion<T> getCriterion() {
		return this.criterion;
	}

	public Object getCriterionBean() {
		return this.criterionBean;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public boolean isFiltered() {
		return this.filtered;
	}

	public void setFiltered(boolean flag) {
		this.filtered = flag;
	}

	public void clear() {
		this.criterionBean = null;
	}

}
