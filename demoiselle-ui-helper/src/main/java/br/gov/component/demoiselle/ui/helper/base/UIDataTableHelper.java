package br.gov.component.demoiselle.ui.helper.base;

import br.gov.component.demoiselle.ui.helper.annotations.UIDataTable;
import br.gov.component.demoiselle.ui.helper.core.UIHelper;

public class UIDataTableHelper extends UIHelper {
	private static final long serialVersionUID = 1L;

	public void update() {
		super.update(getFormId(), getId());
	}

	public String getFormId() {
		if (this.getClass().getAnnotation(UIDataTable.class).formId() != null
				&& !this.getClass().getAnnotation(UIDataTable.class).formId().isEmpty()) {
			return this.getClass().getAnnotation(UIDataTable.class).formId();
		}
		return "";
	}

	public String getId() {
		if (this.getClass().getAnnotation(UIDataTable.class).id() != null
				&& !this.getClass().getAnnotation(UIDataTable.class).id().isEmpty()) {
			return this.getClass().getAnnotation(UIDataTable.class).id();
		}
		return "";
	}
}
