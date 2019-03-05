package com.lfrgs.tools.api.validator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrew Betts
 */
public class ValidStringRule implements ValidatorRule<String> {

	public ValidStringRule(String... invalidValues) {
		_invalidValues = invalidValues;
	}

	@Override
	public void onFailure(String input) throws PortalException {
		if (_log.isDebugEnabled()) {
			_log.debug(
				input + " found in: " + StringUtil.merge(_invalidValues));
		}
	}

	@Override
	public void onSuccess(String input) throws PortalException {
	}

	@Override
	public boolean test(String s) {
		if (ArrayUtil.isEmpty(_invalidValues)) {
			return true;
		}

		return !ArrayUtil.contains(_invalidValues, s);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ValidStringRule.class);

	private String[] _invalidValues;

}