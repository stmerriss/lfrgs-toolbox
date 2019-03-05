package com.liferay.gs.util.operations;

import java.util.Objects;

/**
 * @author Andrew Betts
 */
public interface UnsafeConsumer<T, E extends Throwable> {

	void accept(T t) throws E;

	default UnsafeConsumer<T, E> andThen(
		UnsafeConsumer<? super T, E> after) throws E {

		Objects.requireNonNull(after);

		return (T t) -> { accept(t); after.accept(t); };
	}

}