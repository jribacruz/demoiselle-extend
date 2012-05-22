package br.gov.component.demoiselle.xcriteria.implementation;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import br.gov.component.demoiselle.xcriteria.CriteriaContext;
import br.gov.component.demoiselle.xcriteria.Criterion;
import br.gov.frameworkdemoiselle.util.Beans;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {

	private Map<String, Object> params;
	private boolean paginated;

	@SuppressWarnings("rawtypes")
	private Criterion criterion;

	private Object criterionBean;

	public CriteriaContextImpl() {
		super();
		this.params = new HashMap<String, Object>();
		this.paginated = true;
	}

	public void setPaginated(boolean flag) {
		this.paginated = flag;
	}

	public boolean isPaginated() {
		return this.paginated;
	}

	public void addParam(String key, Object value) {
		this.params.put(key, value);
	}

	public Object getParam(String key) {
		return this.params.get(key);
	}

	public void clearParam() {
		this.params.clear();
	}

	public <X> void setCriterion(Class<? extends Criterion<X>> criterion) {
		this.criterion = Beans.getReference(criterion);
	}

	@SuppressWarnings("unchecked")
	public <X> Criterion<X> getCriterion() {
		return this.criterion;
	}

	public <X> void setCriterionBean(X criterionBean) {
		this.criterionBean = criterionBean;
	}

	@SuppressWarnings("unchecked")
	public <X> X getCriterionBean() {
		return (X) this.criterionBean;
	}

}
