package com.liferay.gs.search;

import java.util.List;

/**
 * @author Shane Merriss
 */
public interface Searcher<T> {

	public List<T> search();

	public long searchCount();
}
