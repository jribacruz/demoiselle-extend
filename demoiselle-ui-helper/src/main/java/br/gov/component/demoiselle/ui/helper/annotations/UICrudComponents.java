package br.gov.component.demoiselle.ui.helper.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE })
public @interface UICrudComponents {
	String formDataTableId() default "";

	String formDialogId() default "";
	
	String formToolbarId() default "";
	
	String toolbarId() default "";

	String dataTableId() default "";

	String dialogId() default "";

	String dialogBodyId() default "";

	String dialogWVar() default "";
}
