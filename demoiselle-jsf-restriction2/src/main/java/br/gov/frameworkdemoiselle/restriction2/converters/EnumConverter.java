package br.gov.frameworkdemoiselle.restriction2.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang.StringUtils;

public class EnumConverter implements Converter {

	@SuppressWarnings("rawtypes")
	private Class<? extends Enum> targetClass;

	@SuppressWarnings("rawtypes")
	public EnumConverter(Class<? extends Enum> targetClass) {
		super();
		this.targetClass = targetClass;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (targetClass != null) {
			if (!StringUtils.isEmpty(value)) {
				return Enum.valueOf(targetClass, value);
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return null;
	}

}
