package br.gov.frameworkdemoiselle.jsf.view.utils;

import java.util.ArrayList;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.faces.event.MethodExpressionActionListener;

import org.apache.commons.lang.StringUtils;

public class UIUtils {
	public static MethodExpression createAction(FacesContext context, String action) {
		return context.getApplication().getExpressionFactory()
				.createMethodExpression(context.getELContext(), action, String.class, new Class[] {});
	}

	public static ActionListener createActionListener(FacesContext context, String actionListener) {
		MethodExpression expression = context.getApplication().getExpressionFactory()
				.createMethodExpression(context.getELContext(), actionListener, null, new Class[] { String.class });
		return new MethodExpressionActionListener(expression);
	}

	public static String itemsToActionParam(Object[] params) {
		StringBuilder builder = new StringBuilder();
		List<String> paramList = new ArrayList<String>();

		if (params.length > 0) {
			builder.append("(");
		}

		for (Object param : params) {
			if (param.getClass() == String.class) {
				paramList.add("'" + param + "'");
			} else {
				paramList.add(String.valueOf(param));
			}
		}

		if (params.length > 0) {
			builder.append(StringUtils.join(paramList, ","));
			builder.append(")");
		}
		return builder.toString();
	}

}
