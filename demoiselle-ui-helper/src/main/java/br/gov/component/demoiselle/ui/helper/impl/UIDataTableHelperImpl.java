package br.gov.component.demoiselle.ui.helper.impl;

import java.lang.reflect.Field;

import br.gov.component.demoiselle.ui.helper.annotations.UIDataTable;
import br.gov.component.demoiselle.ui.helper.core.UIHelper;
import br.gov.component.demoiselle.ui.helper.types.UIDataTableHelper;

public class UIDataTableHelperImpl implements UIDataTableHelper {
	private UIHelper helper;

	private Field field;

	public UIDataTableHelperImpl(Field field, UIHelper helper) {
		super();
		this.field = field;
		this.helper = helper;
	}

	public void update() {
		helper.update(getFormId(), getId());
	}

	public String getFormId() {
		if (field.getAnnotation(UIDataTable.class).formId() != null && !field.getAnnotation(UIDataTable.class).formId().isEmpty()) {
			return field.getAnnotation(UIDataTable.class).formId();
		}
		return "";
	}

	public String getId() {
		if (field.getAnnotation(UIDataTable.class).id() != null && !field.getAnnotation(UIDataTable.class).id().isEmpty()) {
			return field.getAnnotation(UIDataTable.class).id();
		}
		return "";
	}
}
