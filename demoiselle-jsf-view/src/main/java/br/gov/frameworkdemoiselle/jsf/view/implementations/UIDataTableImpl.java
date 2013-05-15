package br.gov.frameworkdemoiselle.jsf.view.implementations;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.jsf.view.UIDataTable;
import br.gov.frameworkdemoiselle.jsf.view.config.UIConfig;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Strings;

public class UIDataTableImpl<T> implements UIDataTable<T> {
	private static final long serialVersionUID = 1L;

	private UIConfig config;

	private RequestContext context;

	private String formId;

	private String dataTableId;

	private Class<T> dataTableClass;

	public UIDataTableImpl(String formId, String dataTableId, Class<T> dataTableClass) {
		super();
		this.config = Beans.getReference(UIConfig.class);
		this.context = Beans.getReference(RequestContext.class);
		this.formId = formId;
		this.dataTableId = dataTableId;
		this.dataTableClass = dataTableClass;
	}

	public void update() {
		this.context.update(":".concat(getFormId()).concat(":").concat(getDataTableId()));
	}

	public String getFormId() {
		if (Strings.isEmpty(formId)) {
			this.formId = getDataTableClassName().concat(config.getFormDataTableIdSuffix());
		}
		return this.formId;
	}

	public String getDataTableId() {
		if (Strings.isEmpty(dataTableId)) {
			this.dataTableId = getDataTableClassName().concat(config.getDataTableIdSuffix());
		}
		return this.dataTableId;
	}

	private String getDataTableClassName() {
		return StringUtils.uncapitalize(this.dataTableClass.getSimpleName());
	}

}
