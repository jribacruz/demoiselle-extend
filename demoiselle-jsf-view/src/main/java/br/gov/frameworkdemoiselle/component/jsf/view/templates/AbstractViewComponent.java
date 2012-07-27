package br.gov.frameworkdemoiselle.component.jsf.view.templates;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.gov.frameworkdemoiselle.component.jsf.view.Element;
import br.gov.frameworkdemoiselle.component.jsf.view.util.ElementUtil;

public abstract class AbstractViewComponent<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	protected Element formDialog;

	@Inject
	protected Element formDatatable;

	@Inject
	protected Element dialog;

	@Inject
	protected Element dialogBody;

	@Inject
	protected Element datatable;

	protected void initContainer() {
		formDatatable.add(datatable);
		formDialog.add(dialog);
		formDialog.add(dialogBody);
	}

	protected String update(Element... elements) {
		initContainer();
		Set<String> updateList = new HashSet<String>();
		for (Element element : elements) {
			updateList.add(ElementUtil.getContainerPath(element));
		}
		return StringUtils.join(updateList, " ");
	}

	public Element getFormDialog() {
		return formDialog;
	}

	public void setFormDialog(Element formDialog) {
		this.formDialog = formDialog;
	}

	public Element getFormDatatable() {
		return formDatatable;
	}

	public void setFormDatatable(Element formDatatable) {
		this.formDatatable = formDatatable;
	}

	public Element getDialog() {
		return dialog;
	}

	public void setDialog(Element dialog) {
		this.dialog = dialog;
	}

	public Element getDialogBody() {
		return dialogBody;
	}

	public void setDialogBody(Element dialogBody) {
		this.dialogBody = dialogBody;
	}

}
