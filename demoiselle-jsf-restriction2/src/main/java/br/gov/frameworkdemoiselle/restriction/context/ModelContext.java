package br.gov.frameworkdemoiselle.restriction.context;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;
import br.gov.frameworkdemoiselle.util.Reflections;

@SessionScoped
public class ModelContext implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private DefaultLazyModel defaultLazyModel;

	public <T> Class<T> getBeanClass() {
		if (defaultLazyModel != null) {
			return Reflections.getGenericTypeArgument(this.getClass(), 0);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public DefaultLazyModel getDefaultLazyModel() {
		return defaultLazyModel;
	}

	@SuppressWarnings("rawtypes")
	public void setDefaultLazyModel(DefaultLazyModel defaultLazyModel) {
		this.defaultLazyModel = defaultLazyModel;
	}

}
