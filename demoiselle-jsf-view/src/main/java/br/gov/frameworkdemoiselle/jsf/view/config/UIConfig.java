package br.gov.frameworkdemoiselle.jsf.view.config;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(prefix = "demoiselle.jsf.view.ui")
public class UIConfig {

	@Name("form.dialog.id.suffix")
	private String formDialogIdSuffix;

	@Name("dialog.body.id.suffix")
	private String dialogBodySuffix;

	@Name("dialog.id.suffix")
	private String dialogIdSuffix;

	@Name("dialog.widgetvar.suffix")
	private String dialogWidgetvarSuffix;

	@Name("form.datatable.id.suffix")
	private String formDataTableIdSuffix;

	@Name("datatable.id.suffix")
	private String dataTableIdSuffix;

	@Name("toolbar.datatable.id.suffix")
	private String toolbarDataTableIdSuffix;

	public String getFormDialogIdSuffix() {
		return formDialogIdSuffix;
	}

	public void setFormDialogIdSuffix(String formDialogIdSuffix) {
		this.formDialogIdSuffix = formDialogIdSuffix;
	}

	public String getDialogBodySuffix() {
		return dialogBodySuffix;
	}

	public void setDialogBodySuffix(String dialogBodySuffix) {
		this.dialogBodySuffix = dialogBodySuffix;
	}

	public String getDialogIdSuffix() {
		return dialogIdSuffix;
	}

	public void setDialogIdSuffix(String dialogIdSuffix) {
		this.dialogIdSuffix = dialogIdSuffix;
	}

	public String getDialogWidgetvarSuffix() {
		return dialogWidgetvarSuffix;
	}

	public void setDialogWidgetvarSuffix(String dialogWidgetvarSuffix) {
		this.dialogWidgetvarSuffix = dialogWidgetvarSuffix;
	}

	public String getFormDataTableIdSuffix() {
		return formDataTableIdSuffix;
	}

	public void setFormDataTableIdSuffix(String formDataTableIdSuffix) {
		this.formDataTableIdSuffix = formDataTableIdSuffix;
	}

	public String getDataTableIdSuffix() {
		return dataTableIdSuffix;
	}

	public void setDataTableIdSuffix(String dataTableIdSuffix) {
		this.dataTableIdSuffix = dataTableIdSuffix;
	}

	public String getToolbarDataTableIdSuffix() {
		return toolbarDataTableIdSuffix;
	}

	public void setToolbarDataTableIdSuffix(String toolbarDataTableIdSuffix) {
		this.toolbarDataTableIdSuffix = toolbarDataTableIdSuffix;
	}

}
