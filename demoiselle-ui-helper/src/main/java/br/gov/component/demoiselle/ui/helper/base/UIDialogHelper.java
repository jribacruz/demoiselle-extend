package br.gov.component.demoiselle.ui.helper.base;

import org.apache.commons.lang.StringUtils;

import br.gov.component.demoiselle.ui.helper.annotations.UIDialog;
import br.gov.component.demoiselle.ui.helper.core.UIHelper;

public class UIDialogHelper extends UIHelper {
	private static final long serialVersionUID = 1L;

	public void update() {
		super.update(getFormId(), getBodyId());
	}

	public void show() {
		super.execute(getWvar().concat(".show()"));
	}

	public void hide() {
		super.execute(getWvar().concat(".hide()"));
	}

	public String getFormId() {
		if (this.getClass().getAnnotation(UIDialog.class).formId() != null
				&& !StringUtils.isEmpty(this.getClass().getAnnotation(UIDialog.class).formId())) {
			return this.getClass().getAnnotation(UIDialog.class).formId();
		}
		return "";
	}

	public String getBodyId() {
		if (this.getClass().getAnnotation(UIDialog.class).bodyId() != null
				&& !StringUtils.isEmpty(this.getClass().getAnnotation(UIDialog.class).bodyId())) {
			return this.getClass().getAnnotation(UIDialog.class).bodyId();
		}
		return "";
	}

	public String getWvar() {
		if (this.getClass().getAnnotation(UIDialog.class).wvar() != null
				&& !StringUtils.isEmpty(this.getClass().getAnnotation(UIDialog.class).wvar())) {
			return this.getClass().getAnnotation(UIDialog.class).wvar();
		}
		return "";
	}
}
