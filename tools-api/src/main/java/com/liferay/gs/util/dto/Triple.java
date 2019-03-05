package com.liferay.gs.util.dto;

import com.liferay.portal.kernel.util.HashUtil;

/**
 * @author Andrew Betts
 */
public class Triple<T> {

	public Triple() {
	}

	public Triple(T first, T second, T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Triple)) {
			return false;
		}

		Triple triple = (Triple)obj;

		if (!first.equals(triple.first) || !second.equals(triple.second) ||
			!third.equals(triple.third)) {

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

	public T getThird() {
		return third;
	}

	public int hashCode() {
		int hash = HashUtil.hash(0, first);

		hash = HashUtil.hash(hash, second);

		return HashUtil.hash(hash, third);
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public void setSecond(T second) {
		this.second = second;
	}

	public void setThird(T third) {
		this.third = third;
	}

	private T first;
	private T second;
	private T third;

}
