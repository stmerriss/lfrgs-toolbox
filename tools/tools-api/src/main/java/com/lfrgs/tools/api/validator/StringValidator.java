package com.lfrgs.tools.api.validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrew Betts
 */
@Component(
	immediate = true,
	property = {"inputClass=java.lang.String"},
	service = InputValidator.class
)
public class StringValidator implements InputValidator<String> {
}
