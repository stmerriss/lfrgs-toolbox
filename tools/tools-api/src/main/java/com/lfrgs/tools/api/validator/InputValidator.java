package com.lfrgs.tools.api.validator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Andrew Betts
 */
public interface InputValidator<I> {

	public default boolean validate(I input, List<ValidatorRule<I>> rules)
		throws PortalException {

		if (ListUtil.isEmpty(rules)) {
			return true;
		}

		for (ValidatorRule<I> rule : rules) {
			if (rule.test(input)) {
				rule.onSuccess(input);
			}
			else {
				rule.onFailure(input);

				return false;
			}
		}

		return true;
	}

}