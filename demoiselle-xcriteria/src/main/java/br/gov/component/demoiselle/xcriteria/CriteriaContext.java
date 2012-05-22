package br.gov.component.demoiselle.xcriteria;

public interface CriteriaContext {
	public void setPaginated(boolean flag);

	public boolean isPaginated();

	public void addParam(String key, Object value);

	public Object getParam(String key);

	public void clearParam();

	public <X> void setCriterion(Class<? extends Criterion<X>> criterion);

	public <X> Criterion<X> getCriterion();

	public <X> void setCriterionBean(X criterionBean);

	public <X> X getCriterionBean();
}
