package br.gov.frameworkdemoiselle.ui.helper;

import java.util.Set;

public interface UIHelper {
	void update(String... ids);

	void update(Set<String> ids);

	void execute(String command);

	void param(String key, Object value);
	
	void scrollTo(String id);
}
