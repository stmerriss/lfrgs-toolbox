package com.liferay.gs.util.accessor;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Andrew Betts
 */
public class KeyValuePairAccessor implements Accessor<KeyValuePair, String> {

	public KeyValuePairAccessor() {
		this(true);
	}

	public KeyValuePairAccessor(boolean accessValue) {
		_accessValue = accessValue;
	}

	@Override
	public String get(KeyValuePair keyValuePair) {
		if (_accessValue) {
			return keyValuePair.getValue();
		}

		return keyValuePair.getKey();
	}

	@Override
	public Class<String> getAttributeClass() {
		return String.class;
	}

	@Override
	public Class<KeyValuePair> getTypeClass() {
		return KeyValuePair.class;
	}

	private boolean _accessValue;

}