package br.gov.component.demoiselle.jsf.restriction.base;

import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;

public class RMap {
	private String[] attributes;
	private boolean selectionMode;
	private boolean dataTableColumn;
	private boolean required;
	@SuppressWarnings("rawtypes")
	private RestrictionBean restrictionBean;

	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	public boolean isSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		this.selectionMode = selectionMode;
	}

	public boolean isDataTableColumn() {
		return dataTableColumn;
	}

	public void setDataTableColumn(boolean dataTableColumn) {
		this.dataTableColumn = dataTableColumn;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@SuppressWarnings("rawtypes")
	public RestrictionBean getRestrictionBean() {
		return restrictionBean;
	}

	@SuppressWarnings("rawtypes")
	public void setRestrictionBean(RestrictionBean restrictionBean) {
		this.restrictionBean = restrictionBean;
	}

}
