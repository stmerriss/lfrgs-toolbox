package com.liferay.gs.search.builders;

import com.liferay.gs.search.Builder;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

/**
 * @author Shane Merriss
 */
public class SearchFilterBuilder implements Builder<BooleanFilter> {

	public SearchFilterBuilder() {
		_searchFilter = new BooleanFilter();
	}

	public SearchFilterBuilder addFilter(
		String field, String keyword, BooleanClauseOccur booleanClauseOccur) {

		_searchFilter.addTerm(field, keyword, booleanClauseOccur);

		return this;
	}

	public SearchFilterBuilder addFilter(
		BooleanFilter booleanFilter, BooleanClauseOccur booleanClauseOccur) {

		_searchFilter.add(booleanFilter, booleanClauseOccur);

		return this;
	}

	public <V> SearchFilterBuilder addMultipleFields(
		V value, Iterable<String> fields) {

		BooleanFilter filter = new BooleanFilter();

		for (String field : fields) {
			filter.addTerm(
				field, String.valueOf(value), BooleanClauseOccur.SHOULD);
		}

		_searchFilter.add(filter, BooleanClauseOccur.MUST);

		return this;
	}

	public <V> SearchFilterBuilder addMultipleValues(
		String field, Iterable<V> values) {

		BooleanFilter filter = new BooleanFilter();

		for (V value : values) {
			filter.addTerm(
				field, String.valueOf(value), BooleanClauseOccur.SHOULD);
		}

		_searchFilter.add(filter, BooleanClauseOccur.MUST);

		return this;
	}

	public BooleanFilter build() {
		return _searchFilter;
	}

	private BooleanFilter _searchFilter;
}
