package br.gov.frameworkdemoiselle.ui.helper.core;

import java.util.Arrays;

import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.ui.helper.UIHelper;
import br.gov.frameworkdemoiselle.ui.helper.annotations.UIDataTable;
import br.gov.frameworkdemoiselle.ui.helper.annotations.UIDialog;

public class UIPageHelper implements UIHelper {
	private static final long serialVersionUID = 1L;

	@Inject
	private RequestContext context;

	@Override
	public void update(String... ids) {
		this.context.update(Arrays.asList(ids));
	}

	@Override
	public void execute(String command) {
		this.context.execute(command);
	}

	@Override
	public void param(String key, Object value) {
		this.context.addCallbackParam(key, value);
	}

	@Override
	public void scrollTo(String id) {
		this.context.scrollTo(id);
	}

	public void updateDialog() {
		if (this.getClass().isAnnotationPresent(UIDialog.class)) {
			this.beforeUpdateDialogCallback();
			String formId = this.getClass().getAnnotation(UIDialog.class).formId();
			String bodyId = this.getClass().getAnnotation(UIDialog.class).bodyId();
			this.update(":".concat(formId).concat(":").concat(bodyId));
			this.afterUpdateDialogCallback();
		}
	}

	public void show() {
		if (this.getClass().isAnnotationPresent(UIDialog.class)) {
			this.beforeShowCallback();
			String wvar = this.getClass().getAnnotation(UIDialog.class).widgetVar();
			this.execute(wvar.concat(".show()"));
			this.afterShowCallback();
		}

	}

	public void hide() {
		if (this.getClass().isAnnotationPresent(UIDialog.class)) {
			this.beforeHideCallback();
			String wvar = this.getClass().getAnnotation(UIDialog.class).widgetVar();
			this.execute(wvar.concat(".hide()"));
			this.afterHideCallback();
		}
	}

	public void updateDataTable() {
		if (this.getClass().isAnnotationPresent(UIDataTable.class)) {
			this.beforeUpdateDataTableCallback();
			String formId = this.getClass().getAnnotation(UIDataTable.class).formId();
			String id = this.getClass().getAnnotation(UIDataTable.class).id();
			this.update(":".concat(formId).concat(":").concat(id));
			this.afterUpdateDataTableCallback();
		}
	}

	protected void beforeShowCallback() {
	}

	protected void afterShowCallback() {
	}

	protected void beforeHideCallback() {
	}

	protected void afterHideCallback() {
	}

	protected void beforeUpdateDataTableCallback() {
	}

	protected void afterUpdateDataTableCallback() {

	}

	protected void beforeUpdateDialogCallback() {
	}

	protected void afterUpdateDialogCallback() {

	}

}
