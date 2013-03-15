package br.gov.component.demoiselle.ui.helper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface UICrudHelper {
	UICrudComponents[] components() default {};

	UICrudMessages[] messages() default {};
}
