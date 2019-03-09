package com.liferay.gs.search.builders;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.function.BiFunction;

public interface ModelSearcherBuilder<S> extends SearcherBuilder<S> {

	public BiFunction<SearchContext, Query, S> modelSearcherGenerator();

}