package com.liferay.gs.search.builders;

import com.liferay.gs.search.BaseModelSearcher;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

/**
 * @author Shane Merriss
 * @author Andrew Betts
 *
 * @param <B> SearcherBuilder
 * @param <T> Model
 * @param <S> Searcher
 */
public abstract class BaseModelSearcherBuilder<B, T extends BaseModel<T>, S extends BaseModelSearcher<T>>
	implements ModelSearcherBuilder<S> {

	public S build() {
		SearchContext searchContext = getSearchContextBuilder().build();

		BooleanFilter searchFilter = getSearchFilterBuilder().build();

		Query searchQuery = getSearchQueryBuilder()
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
		this.keywords = keywords;

		return self();
	}

	protected abstract Class<T> getModelClass();

	protected abstract B self();

	public SearchContextBuilder getSearchContextBuilder() {
		return new SearchContextBuilder()
			.setAndSearch(_andSearch)
			.setCompanyId(_companyId)
			.setStart(_start)
			.setEnd(_end);
	}

	public SearchFilterBuilder getSearchFilterBuilder() {
		return new SearchFilterBuilder();
	}

	public SearchQueryBuilder getSearchQueryBuilder() {
		return new SearchQueryBuilder(getModelClass());
	}

	protected String keywords;

	private boolean _andSearch;
	private long _companyId;
	private int _start;
	private int _end;

}