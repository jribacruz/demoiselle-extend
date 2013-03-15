package br.gov.component.demoiselle.ui.helper.impl;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import br.gov.component.demoiselle.ui.helper.annotations.UIDialog;
import br.gov.component.demoiselle.ui.helper.core.UIHelper;
import br.gov.component.demoiselle.ui.helper.types.UIDialogHelper;

public class UIDialogHelperImpl implements UIDialogHelper {

	private Field field;

	private UIHelper helper;

	public UIDialogHelperImpl(Field field, UIHelper helper) {
		super();
		this.field = field;
		this.helper = helper;
	}

	public void update() {
		helper.update(getFormId(), getBodyId());
	}

	public void show() {
		helper.execute(getWvar().concat(".show()"));
	}

	public void hide() {
		helper.execute(getWvar().concat(".hide()"));
	}

	public String getFormId() {
		if (field.getAnnotation(UIDialog.class).formId() != null && !StringUtils.isEmpty(field.getAnnotation(UIDialog.class).formId())) {
			return field.getAnnotation(UIDialog.class).formId();
		}
		return "";
	}

	public String getBodyId() {
		if (field.getAnnotation(UIDialog.class).bodyId() != null && !StringUtils.isEmpty(field.getAnnotation(UIDialog.class).bodyId())) {
			return field.getAnnotation(UIDialog.class).bodyId();
		}
		return "";
	}

	public String getWvar() {
		if (field.getAnnotation(UIDialog.class).wvar() != null && !StringUtils.isEmpty(field.getAnnotation(UIDialog.class).wvar())) {
			return field.getAnnotation(UIDialog.class).wvar();
		}
		return "";
	}
}
