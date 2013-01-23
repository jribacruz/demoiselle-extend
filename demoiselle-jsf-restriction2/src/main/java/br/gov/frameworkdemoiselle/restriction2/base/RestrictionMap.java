package br.gov.frameworkdemoiselle.restriction2.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import br.gov.frameworkdemoiselle.restriction2.annotations.Attribute;
import br.gov.frameworkdemoiselle.restriction2.annotations.Converter;
import br.gov.frameworkdemoiselle.restriction2.annotations.Required;
import br.gov.frameworkdemoiselle.restriction2.annotations.SelectionMode;
import br.gov.frameworkdemoiselle.restriction2.annotations.Value;
import br.gov.frameworkdemoiselle.restriction2.converters.EnumConverter;
import br.gov.frameworkdemoiselle.restriction2.template.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;
import br.gov.frameworkdemoiselle.restriction2.utils.ReflectionUtils;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public class RestrictionMap {

	private Field field;

	@SuppressWarnings("rawtypes")
	private Class<? extends CriteriaBean> criteriaClass;

	@SuppressWarnings("rawtypes")
	private CriteriaBean criteriaBean;

	private String[] attributes;

	private boolean selectionMode;

	private boolean dataTableColumn;

	private boolean required;

	private boolean hasConverter;

	private boolean hasValue;

	private boolean hasAttribute;

	private Map<String, String> filters;

	@SuppressWarnings("rawtypes")
	public RestrictionMap(Class<? extends CriteriaBean> criteriaClass, Field field, Map<String, String> filters) {
		super();
		this.criteriaClass = criteriaClass;
		this.field = field;
		this.filters = filters;
		init();
	}

	private void init() {
		this.selectionMode = this.field.isAnnotationPresent(SelectionMode.class);
		this.dataTableColumn = this.field.isAnnotationPresent(Attribute.class) ? this.field.getAnnotation(Attribute.class)
				.dataTableColumn() : false;
		this.required = this.field.isAnnotationPresent(Required.class);
		this.hasConverter = this.field.isAnnotationPresent(Converter.class);
		this.hasValue = this.field.isAnnotationPresent(Value.class);
		this.hasAttribute = this.field.isAnnotationPresent(Attribute.class);
		this.attributes = hasAttribute ? this.field.getAnnotation(Attribute.class).value() : null;
		this.criteriaBean = Beans.getReference(this.criteriaClass);
	}

	private javax.faces.convert.Converter getConverter() {
		if (hasConverter) {
			Class<? extends javax.faces.convert.Converter> converter = this.field.getAnnotation(Converter.class).value();
			if (converter == EnumConverter.class) {
				Class<? extends Enum> targetClass = this.field.getAnnotation(Converter.class).targetClass();
				return new EnumConverter(targetClass);
			}
			return Reflections.instantiate(converter);
		}
		return null;
	}

	private Object getValue() {
		if (hasValue) {
			String value = this.field.getAnnotation(Value.class).value();
			if (hasConverter) {
				javax.faces.convert.Converter converter = getConverter();
				return converter.getAsObject(null, null, value);
			}
			return value;
		}
		return null;
	}

	private String getDataTableColumnValue(String attribute) {
		return this.filters.get(attribute);
	}

	@SuppressWarnings("unchecked")
	public <T, X> RestrictionBean<T, X> getRestrictionBean() {

		Method getter = ReflectionUtils.getGetter(criteriaClass, this.field);
		if (getter != null) {
			RestrictionBean<T, X> restrictionBean = (RestrictionBean<T, X>) ReflectionUtils.invoke(getter, this.criteriaBean);
			if (hasAttribute && this.attributes.length == 1) {
				if (isDataTableColumn()) {
					String attribute = this.attributes[0];
					restrictionBean.setValue((X) getDataTableColumnValue(attribute));
				} else {
					if (hasValue) {
						restrictionBean.setValue((X) getValue());
					}
				}
			}
			return restrictionBean;
		}

		return null;
	}

	public boolean isSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		this.selectionMode = selectionMode;
	}

	public boolean isDataTableColumn() {
		return dataTableColumn;
	}

	public void setDataTableColumn(boolean dataTableColumn) {
		this.dataTableColumn = dataTableColumn;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "RestrictionMap [criteriaClass=" + criteriaClass + ", attributes=" + Arrays.toString(attributes) + ", selectionMode="
				+ selectionMode + ", dataTableColumn=" + dataTableColumn + ", required=" + required + ", hasConverter=" + hasConverter
				+ ", hasValue=" + hasValue + ", filters=" + filters + "]";
	}

}
