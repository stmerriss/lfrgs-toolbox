package com.lfrgs.tools.api.validator;

import com.liferay.portal.kernel.json.JSONObject;
import org.osgi.service.component.annotations.Component;

/**
 * @author Andrew Betts
 */
@Component(
	immediate = true,
	property = {"inputClass=com.liferay.portal.kernel.json.JSONObject"},
	service = InputValidator.class
)
public class JSONInputValidator implements InputValidator<JSONObject> {
}
