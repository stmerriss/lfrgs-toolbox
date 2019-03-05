package com.lfrgs.search.builders;

import com.lfrgs.search.Builder;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;

/**
 * @author Shane Merriss
 */
public class SearchQueryBuilder implements Builder<BooleanQuery> {

	public SearchQueryBuilder(Class klass) {
		_klass = klass;

		_filter = new BooleanFilter();
	}

	public SearchQueryBuilder addFilter(BooleanFilter filter) {
		_filter.add(filter, BooleanClauseOccur.MUST);

		return this;
	}

	public BooleanQuery build(){
		BooleanQuery searchQuery = new BooleanQueryImpl();

		BooleanFilter typeFilter = new BooleanFilter();

		typeFilter.addRequiredTerm(Field.ENTRY_CLASS_NAME, _klass.getName());

		searchQuery.setPreBooleanFilter(typeFilter);

		searchQuery.setPostFilter(_filter);

		return searchQuery;
	}

	private BooleanFilter _filter;
	private Class _klass;
}
