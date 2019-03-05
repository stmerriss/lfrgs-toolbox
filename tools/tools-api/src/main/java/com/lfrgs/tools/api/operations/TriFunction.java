package com.lfrgs.tools.api.operations;

/**
 * @author Andrew Betts
 */
public interface TriFunction<R, A, B, C> {

	public R get(A a, B b, C c);

}