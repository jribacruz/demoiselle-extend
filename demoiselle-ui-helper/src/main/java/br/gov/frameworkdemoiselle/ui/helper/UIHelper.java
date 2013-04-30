package br.gov.frameworkdemoiselle.ui.helper;

import java.io.Serializable;

public interface UIHelper extends Serializable {
	void update(String... ids);

	void execute(String command);

	void param(String key, Object value);

	void scrollTo(String id);
}
