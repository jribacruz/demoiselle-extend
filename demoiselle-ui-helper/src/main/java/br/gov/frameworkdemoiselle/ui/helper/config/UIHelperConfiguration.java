package br.gov.frameworkdemoiselle.ui.helper.config;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(prefix = "demoiselle.ui.helper")
public class UIHelperConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Name("growl.id")
	private String growlId;

	public String getGrowlId() {
		return growlId;
	}

	public void setGrowlId(String growlId) {
		this.growlId = growlId;
	}

}
