package br.gov.frameworkdemoiselle.ui.helper.datatable;

import java.io.Serializable;

import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.util.Strings;

public class UIDataTableHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private RequestContext context;

	private String formId;

	private String id;

	public UIDataTableHelper(RequestContext context, String formId, String id) {
		super();
		this.context = context;
		this.formId = formId;
		this.id = id;
	}

	public void update() {
		if (!Strings.isEmpty(this.formId) && !Strings.isEmpty(this.id)) {
			this.context.update(":".concat(this.formId).concat(":").concat(this.id));
		}
	}

}
