package com.liferay.gs.search;

import com.liferay.portal.kernel.search.Document;

import java.util.List;
import java.util.function.Function;

public interface ModelSearcher<T> extends Searcher<Document> {

	public List<T> search(Function<Document, T> documentToModelFunction);

}