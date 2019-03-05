package com.liferay.gs.util.accessor;

import com.liferay.gs.util.dto.Pair;

import com.liferay.portal.kernel.util.Accessor;

/**
 * @author Andrew Betts
 */
public class StringPairAccessor
	implements Accessor<Pair<String>, String> {

	public StringPairAccessor() {
		_second = false;
	}

	public StringPairAccessor(boolean second) {
		_second = second;
	}

	@Override
	public String get(Pair<String> pair) {
		if (_second) {
			return pair.getSecond();
		}

		return pair.getFirst();
	}

	@Override
	public Class<String> getAttributeClass() {
		return String.class;
	}

	@Override
	public Class<Pair<String>> getTypeClass() {
		return (Class<Pair<String>>)_pair.getClass();
	}

	private static final Pair<String> _pair = new Pair<>("","");

	private boolean _second;

}
