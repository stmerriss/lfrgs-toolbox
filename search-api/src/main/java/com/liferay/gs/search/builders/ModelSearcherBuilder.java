package com.liferay.gs.search.builders;

import com.liferay.gs.search.Builder;

import com.liferay.gs.search.ModelSearcher;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

import java.util.function.BiFunction;

/**
 * @author Andrew Betts
 *
 * @param <B> SearcherBuilder
 * @param <T> Model
 * @param <S> Searcher
 */
public abstract class ModelSearcherBuilder<B, T extends BaseModel<T>, S extends ModelSearcher<T>>
	implements Builder<S> {

	public S build() {
		SearchContext searchContext = getSearchContextBuilder().build();

		BooleanFilter searchFilter = getSearchFilterBuilder().build();

		BooleanQuery searchQuery = getSearchQueryBuilder()
			.addFilter(searchFilter)
			.build();

		return modelSearcherGenerator().apply(searchContext, searchQuery);
	}

	public B setAndSearch(boolean andSearch) {
		_andSearch = andSearch;

		return self();
	}

	public B setCompanyId(long companyId) {
		_companyId = companyId;

		return self();
	}

	public B setEnd(int end) {
		_end = end;

		return self();
	}

	public B setStart(int start) {
		_start = start;

		return self();
	}

	public B setKeywords(String keywords) {
		_keywords = keywords;

		return self();
	}

	protected abstract Class<T> getModelClass();

	protected abstract BiFunction<SearchContext, BooleanQuery, S> modelSearcherGenerator();

	protected abstract B self();

	protected SearchContextBuilder getSearchContextBuilder() {
		return new SearchContextBuilder()
			.setAndSearch(_andSearch)
			.setCompanyId(_companyId)
			.setStart(_start)
			.setEnd(_end);
	}

	protected SearchFilterBuilder getSearchFilterBuilder() {
		return new SearchFilterBuilder();
	}

	protected SearchQueryBuilder getSearchQueryBuilder() {
		return new SearchQueryBuilder(getModelClass());
	}

	protected boolean _andSearch;
	protected long _companyId;
	protected String _keywords;
	protected int _start;
	protected int _end;

}