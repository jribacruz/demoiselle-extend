package br.gov.frameworkdemoiselle.restriction.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class EnumConverter implements Converter {

	private Class<? extends Enum> targetClass;

	public EnumConverter(Class<? extends Enum> targetClass) {
		super();
		this.targetClass = targetClass;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return null;
	}

}
