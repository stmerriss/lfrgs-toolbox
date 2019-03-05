package com.lfrgs.tools.api.validator;

import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Andrew Betts
 */
@Component(
	immediate = true,
	property = {"inputClass=java.lang.List<java.lang.String>"},
	service = InputValidator.class)
public class StringListValidator implements InputValidator<List<String>> {
}