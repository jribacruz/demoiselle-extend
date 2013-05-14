package br.gov.frameworkdemoiselle.jsf.view.types;

import org.apache.commons.lang.StringUtils;

import br.gov.frameworkdemoiselle.jsf.view.utils.UIUtils;
import br.gov.frameworkdemoiselle.util.Strings;

public class UIAction {

	private Class<?> beanClass;

	private String fieldName;

	private String actionName;

	private Object[] params;

	public UIAction(Class<?> beanClass, String fieldName, String actionName, Object... params) {
		super();
		this.beanClass = beanClass;
		this.fieldName = fieldName;
		this.actionName = actionName;
		this.params = params;
	}

	public UIAction(Class<?> beanClass, String actionName, Object... params) {
		super();
		this.beanClass = beanClass;
		this.actionName = actionName;
		this.params = params;
	}

	public UIAction(Class<?> beanClass, String actionName) {
		super();
		this.beanClass = beanClass;
		this.actionName = actionName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("#{");
		builder.append(StringUtils.uncapitalize(this.beanClass.getSimpleName()));
		builder.append(".");
		if (!Strings.isEmpty(fieldName)) {
			builder.append(this.fieldName);
			builder.append(".");
		}
		builder.append(this.actionName);
		builder.append(UIUtils.itemsToActionParam(params));
		builder.append("}");
		return builder.toString();
	}

}
