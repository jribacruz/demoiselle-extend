package br.gov.component.demoiselle.xcriteria.context;

import br.gov.component.demoiselle.xcriteria.ICriterion;

public interface CriteriaContext {
	public void setPaginated(boolean flag);

	public boolean isPaginated();

	public void addParam(String key, Object value);

	public Object getParam(String key);

	public void clearParam();

	public <T> void setCriteria(Class<? extends ICriterion<T>> criterionClass, Object criterionBean);

	public <T> void setCriteria(Class<? extends ICriterion<T>> criterionClass, Object criterionBean, int pageSize);

	public <T> ICriterion<T> getCriterion();

	public Object getCriterionBean();

	public int getPageSize();

	public void clear();
}
