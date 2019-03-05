package com.lfrgs.tools.api.validator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Andrew Betts
 */
public class NonEmptyStringRule implements ValidatorRule<String> {

	@Override
	public void onFailure(String price) throws PortalException {
		if (_log.isDebugEnabled()) {
			_log.debug("input is empty");
		}
	}

	@Override
	public void onSuccess(String price) throws PortalException {
	}

	@Override
	public boolean test(String price) {
		return Validator.isNotNull(price);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NonEmptyStringRule.class);

}