package br.gov.frameworkdemoiselle.component.jsf.view.util;

import br.gov.frameworkdemoiselle.component.jsf.view.Element;

public class ElementUtil {
	public static String getContainerPath(Element element) {
		String path = ":" + element.getId();
		Element elementIterator = element.getContainer();
		while (elementIterator != null) {
			path = ":".concat(elementIterator.getId()).concat(path);
			elementIterator = elementIterator.getContainer();
		}
		return path;
	}
}
