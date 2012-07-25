package br.gov.frameworkdemoiselle.component.jsf.view.templates;

import java.io.Serializable;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.component.jsf.view.Element;

public abstract class AbstractViewComponent<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Cria os elementos do formulario do dialog de inclusao e edicao
	 */
	@Inject
	private Element formDialog;

	@Inject
	private Element formDatatable;

	@Inject
	private Element formToolbar;

	@Inject
	private Element toolbar;

	@Inject
	private Element dialog;

	@Inject
	private Element dialogBody;

	@Inject
	private Element dialogHeader;

	@Inject
	private Element dialogFooter;

	@Inject
	private Element close;

	@Inject
	private Element save;

	@Inject
	private Element create;

	@Inject
	private Element edit;

	@Inject
	private Element refresh;

	@Inject
	private Element delete;

	@Inject
	private Element datatable;

	public Element getFormDialog() {
		return formDialog;
	}

	public void setFormDialog(Element formDialog) {
		this.formDialog = formDialog;
	}

	public Element getToolbar() {
		return toolbar;
	}

	public void setToolbar(Element toolbar) {
		this.toolbar = toolbar;
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

	public Element getClose() {
		return close;
	}

	public void setClose(Element close) {
		this.close = close;
	}

	public Element getDatatable() {
		return datatable;
	}

	public void setDatatable(Element datatable) {
		this.datatable = datatable;
	}

	public Element getFormToolbar() {
		return formToolbar;
	}

	public void setFormToolbar(Element formToolbar) {
		this.formToolbar = formToolbar;
	}

	public Element getDialogHeader() {
		return dialogHeader;
	}

	public void setDialogHeader(Element dialogHeader) {
		this.dialogHeader = dialogHeader;
	}

	public Element getDialogFooter() {
		return dialogFooter;
	}

	public void setDialogFooter(Element dialogFooter) {
		this.dialogFooter = dialogFooter;
	}

	public Element getSave() {
		return save;
	}

	public void setSave(Element save) {
		this.save = save;
	}

	public Element getCreate() {
		return create;
	}

	public void setCreate(Element create) {
		this.create = create;
	}

	public Element getEdit() {
		return edit;
	}

	public void setEdit(Element edit) {
		this.edit = edit;
	}

	public Element getRefresh() {
		return refresh;
	}

	public void setRefresh(Element refresh) {
		this.refresh = refresh;
	}

	public Element getDelete() {
		return delete;
	}

	public void setDelete(Element delete) {
		this.delete = delete;
	}

}
