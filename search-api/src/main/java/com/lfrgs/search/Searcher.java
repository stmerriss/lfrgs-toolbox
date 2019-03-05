package com.lfrgs.search;

import com.liferay.portal.kernel.search.Document;

import java.util.List;

/**
 * @author Shane Merriss
 */
public interface Searcher<T> {

	public List<Document> search();

	public long searchCount();
}
