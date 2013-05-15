package br.gov.frameworkdemoiselle.jsf.view.implementations;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.jsf.view.UIDialog;
import br.gov.frameworkdemoiselle.jsf.view.config.UIConfig;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Strings;

public class UIDialogImpl<T> implements UIDialog<T> {
	private static final long serialVersionUID = 1L;

	private UIConfig config;

	private RequestContext context;

	private String formId;

	private String dialogId;

	private String widgetVar;

	private String bodyId;

	private Class<T> dialogClass;

	public UIDialogImpl(String formId, String dialogId, String widgetVar, String bodyId, Class<T> dialogClass) {
		super();
		this.config = Beans.getReference(UIConfig.class);
		this.context = Beans.getReference(RequestContext.class);
		this.formId = formId;
		this.dialogId = dialogId;
		this.widgetVar = widgetVar;
		this.bodyId = bodyId;
		this.dialogClass = dialogClass;
	}

	public void update() {
		this.context.update(":".concat(getFormId()).concat(":").concat(getBodyId()));
	}

	public void show() {
		this.context.execute(getWidgetVar().concat(".show()"));
	}

	public void hide() {
		this.context.execute(getWidgetVar().concat(".hide()"));
	}

	public String getFormId() {
		if (Strings.isEmpty(this.formId)) {
			this.formId = getDialogClassName().concat(config.getFormDialogIdSuffix());
		}
		return this.formId;
	}

	public String getDialogId() {
		if (Strings.isEmpty(this.dialogId)) {
			this.dialogId = getDialogClassName().concat(config.getDialogIdSuffix());
		}
		return this.dialogId;
	}

	public String getBodyId() {
		if (Strings.isEmpty(this.bodyId)) {
			this.bodyId = getDialogClassName().concat(config.getDialogBodySuffix());
		}
		return this.bodyId;
	}

	public String getWidgetVar() {
		if (Strings.isEmpty(this.widgetVar)) {
			this.widgetVar = getDialogClassName().concat(config.getDialogWidgetVarSuffix());
		}
		return this.widgetVar;
	}

	private String getDialogClassName() {
		return StringUtils.uncapitalize(this.dialogClass.getSimpleName());
	}
}
