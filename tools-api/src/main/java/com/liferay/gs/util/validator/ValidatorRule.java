package com.liferay.gs.util.validator;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.function.Predicate;

/**
 * @author Andrew Betts
 */
public interface ValidatorRule<I> extends Predicate<I> {

	public void onFailure(I input) throws PortalException;

	public void onSuccess(I input) throws PortalException;

}