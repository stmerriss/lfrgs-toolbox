package com.liferay.gs.search.builders;

import com.liferay.gs.search.Builder;

public interface SearcherBuilder<S> extends Builder<S> {

	public SearchContextBuilder getSearchContextBuilder();

	public SearchFilterBuilder getSearchFilterBuilder();

	public SearchQueryBuilder getSearchQueryBuilder();

}