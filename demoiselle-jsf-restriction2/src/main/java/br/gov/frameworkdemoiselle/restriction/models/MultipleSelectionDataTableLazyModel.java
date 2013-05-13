package br.gov.frameworkdemoiselle.restriction.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.context.ModelContext;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;
import br.gov.frameworkdemoiselle.restriction.criterions.NotContainsCriterion;

public class MultipleSelectionDataTableLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;

	private NotContainsCriterion<T> availableList;

	private List<T> selectedList;

	public MultipleSelectionDataTableLazyModel(ModelContext<T> context, EntityManager em) {
		super();
		this.context = context;
		this.em = em;
		this.availableList = new NotContainsCriterion<T>();
		this.selectedList = new ArrayList<T>();
	}

	public NotContainsCriterion<T> getAvailableList() {
		return availableList;
	}

	public void setAvailableList(NotContainsCriterion<T> availableList) {
		this.availableList = availableList;
	}

	@Override
	protected void processPredicates(CriteriaBuilder cb, Root<T> p) {
		super.processPredicates(cb, p);
		if (this.availableList.getValue() != null && !availableList.getValue().isEmpty()) {
			this.predicates.addAll(this.availableList.criterion(cb, p));
		}
	}

	public List<T> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<T> selectedList) {
		this.selectedList = selectedList;
	}

	public String init() {
		this.availableList.setValue(this.selectedList);
		return null;
	}

	@Override
	public void clear() {
		super.clear();
		this.availableList.getValue().clear();
		this.selectedList.clear();
	}

	public String addItem(T item) {
		this.selectedList.add(item);
		return null;
	}

	public String removeItem(T item) {
		this.selectedList.remove(item);
		return null;
	}

}
