package br.gov.frameworkdemoiselle.ui.helper.dialog;

import java.io.Serializable;

import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.util.Strings;

public class UIDialogHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private RequestContext context;

	protected String formId;

	protected String id;

	protected String bodyId;

	protected String widgetVar;

	public UIDialogHelper(RequestContext context, String formId, String id, String bodyId, String widgetVar) {
		super();
		this.context = context;
		this.formId = formId;
		this.id = id;
		this.bodyId = bodyId;
		this.widgetVar = widgetVar;
	}

	public void update() {
		if (!Strings.isEmpty(this.formId) && !Strings.isEmpty(this.bodyId)) {
			this.context.update(":".concat(this.formId).concat(":").concat(this.bodyId));
		}
	}

	public void show() {
		if (!Strings.isEmpty(this.widgetVar)) {
			this.context.execute(this.widgetVar.concat(".show()"));
		}
	}

	public void hide() {
		if (!Strings.isEmpty(this.widgetVar)) {
			this.context.execute(this.widgetVar.concat(".hide()"));
		}
	}

}
