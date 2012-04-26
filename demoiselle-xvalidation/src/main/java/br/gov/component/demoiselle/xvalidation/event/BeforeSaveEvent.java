package br.gov.component.demoiselle.xvalidation.event;

public class BeforeSaveEvent {
	Object obj;

	public BeforeSaveEvent(Object obj) {
		super();
		this.obj = obj;
	}

	public BeforeSaveEvent() {
		super();
	}

	public Object getBean() {
		return obj;
	}
}
