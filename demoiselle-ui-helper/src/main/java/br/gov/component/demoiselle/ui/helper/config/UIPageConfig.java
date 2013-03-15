package br.gov.component.demoiselle.ui.helper.config;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(resource = "demoiselle", prefix = "app.ui")
public class UIPageConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Name(value = "form.dialog.id.suffix")
	private String formDialogIdSuffix;

	@Name(value = "dialog.id.suffix")
	private String dialogIdSuffix;

	@Name(value = "form.datatable.id.suffix")
	private String formDatatableIdSuffix;

	@Name(value = "datatable.id.suffix")
	private String dataTableIdSuffix;

	@Name(value = "dialog.wvar.suffix")
	private String dialogWVarSuffix;

	@Name(value = "dialog.body.id.suffix")
	private String dialogBodyIdSuffix;

	@Name(value = "form.toolbar.id.suffix")
	private String formToolbarIdSuffix;

	@Name(value = "toolbar.id.suffix")
	private String toolbarIdSuffix;

	@Name(value = "form.toolbar.top.id")
	private String formToolbarTopId;

	@Name(value = "toolbar.top.id")
	private String toolbarTopId;

	@Name(value = "growl.id")
	private String growlId;

	@Name(value = "global.messages.id")
	private String globalMessagesId;

	@Name(value = "global.messages.wvar")
	private String globalMessagesWVar;

	public String getFormDialogIdSuffix() {
		return formDialogIdSuffix;
	}

	public void setFormDialogIdSuffix(String formDialogIdSuffix) {
		this.formDialogIdSuffix = formDialogIdSuffix;
	}

	public String getDialogIdSuffix() {
		return dialogIdSuffix;
	}

	public void setDialogIdSuffix(String dialogIdSuffix) {
		this.dialogIdSuffix = dialogIdSuffix;
	}

	public String getFormDatatableIdSuffix() {
		return formDatatableIdSuffix;
	}

	public void setFormDatatableIdSuffix(String formDatatableIdSuffix) {
		this.formDatatableIdSuffix = formDatatableIdSuffix;
	}

	public String getDataTableIdSuffix() {
		return dataTableIdSuffix;
	}

	public void setDataTableIdSuffix(String dataTableIdSuffix) {
		this.dataTableIdSuffix = dataTableIdSuffix;
	}

	public String getDialogWVarSuffix() {
		return dialogWVarSuffix;
	}

	public void setDialogWVarSuffix(String dialogWVarSuffix) {
		this.dialogWVarSuffix = dialogWVarSuffix;
	}

	public String getDialogBodyIdSuffix() {
		return dialogBodyIdSuffix;
	}

	public void setDialogBodyIdSuffix(String dialogBodyIdSuffix) {
		this.dialogBodyIdSuffix = dialogBodyIdSuffix;
	}

	public String getFormToolbarTopId() {
		return formToolbarTopId;
	}

	public void setFormToolbarTopId(String formToolbarTopId) {
		this.formToolbarTopId = formToolbarTopId;
	}

	public String getToolbarTopId() {
		return toolbarTopId;
	}

	public void setToolbarTopId(String toolbarTopId) {
		this.toolbarTopId = toolbarTopId;
	}

	public String getGrowlId() {
		return growlId;
	}

	public void setGrowlId(String growlId) {
		this.growlId = growlId;
	}

	public String getGlobalMessagesId() {
		return globalMessagesId;
	}

	public void setGlobalMessagesId(String globalMessagesId) {
		this.globalMessagesId = globalMessagesId;
	}

	public String getGlobalMessagesWVar() {
		return globalMessagesWVar;
	}

	public void setGlobalMessagesWVar(String globalMessagesWVar) {
		this.globalMessagesWVar = globalMessagesWVar;
	}

	public String getFormToolbarIdSuffix() {
		return formToolbarIdSuffix;
	}

	public void setFormToolbarIdSuffix(String formToolbarIdSuffix) {
		this.formToolbarIdSuffix = formToolbarIdSuffix;
	}

	public String getToolbarIdSuffix() {
		return toolbarIdSuffix;
	}

	public void setToolbarIdSuffix(String toolbarIdSuffix) {
		this.toolbarIdSuffix = toolbarIdSuffix;
	}

	@Override
	public String toString() {
		return "UIPageConfig [formDialogIdSuffix=" + formDialogIdSuffix + ", dialogIdSuffix=" + dialogIdSuffix + ", formDatatableIdSuffix="
				+ formDatatableIdSuffix + ", dataTableIdSuffix=" + dataTableIdSuffix + ", dialogWVarSuffix=" + dialogWVarSuffix
				+ ", dialogBodyIdSuffix=" + dialogBodyIdSuffix + ", formToolbarIdSuffix=" + formToolbarIdSuffix + ", toolbarIdSuffix="
				+ toolbarIdSuffix + ", formToolbarTopId=" + formToolbarTopId + ", toolbarTopId=" + toolbarTopId + ", growlId=" + growlId
				+ ", globalMessagesId=" + globalMessagesId + ", globalMessagesWVar=" + globalMessagesWVar + "]";
	}

}
