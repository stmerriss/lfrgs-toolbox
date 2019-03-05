package com.liferay.gs.util.dto;

import com.liferay.portal.kernel.util.HashUtil;

/**
 * @author Andrew Betts
 */
public class Pair<T> {

	public Pair() {
	}

	public Pair(T first, T second) {
		this.first = first;
		this.second = second;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Pair)) {
			return false;
		}

		Pair pair = (Pair)obj;

		if (!first.equals(pair.first) || !second.equals(pair.second)) {
			return false;
		}

		return true;
	}

	public T getFirst() {
		return first;
	}

	public T getSecond() {
		return second;
	}

	public int hashCode() {
		int hash = HashUtil.hash(0, first);

		return HashUtil.hash(hash, second);
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public void setSecond(T second) {
		this.second = second;
	}

	private T first;
	private T second;

}