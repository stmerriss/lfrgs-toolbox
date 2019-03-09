package com.liferay.gs.search.builders;

import com.liferay.gs.search.Builder;

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
		_className = klass.getName();

		_filter = new BooleanFilter();
	}

	public SearchQueryBuilder(String className) {
		_className = className;

		_filter = new BooleanFilter();
	}

	public SearchQueryBuilder addFilter(BooleanFilter filter) {
		_filter.add(filter, BooleanClauseOccur.MUST);

		return this;
	}

	public BooleanQuery build(){
		BooleanQuery searchQuery = new BooleanQueryImpl();

		BooleanFilter typeFilter = new BooleanFilter();

		typeFilter.addRequiredTerm(Field.ENTRY_CLASS_NAME, _className);

		searchQuery.setPreBooleanFilter(typeFilter);

		searchQuery.setPostFilter(_filter);

		return searchQuery;
	}

	private BooleanFilter _filter;
	private String _className;

}