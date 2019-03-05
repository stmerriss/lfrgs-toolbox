package com.lfrgs.search.builders;

import com.lfrgs.search.Builder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Shane Merriss
 */
public class SearchContextBuilder implements Builder<SearchContext> {

	public SearchContextBuilder() {
		_andSearch = true;
		_companyId = PortalUtil.getDefaultCompanyId();
		_end = QueryUtil.ALL_POS;
		_start = QueryUtil.ALL_POS;
	}

	public SearchContextBuilder setAndSearch(boolean andSearch) {
		_andSearch = andSearch;
		return this;
	}

	public SearchContextBuilder setCompanyId(long companyId) {
		_companyId = companyId;
		return this;
	}

	public SearchContextBuilder setEnd(int end) {
		_end = end;
		return this;
	}

	public SearchContextBuilder setStart(int start) {
		_start = start;
		return this;
	}

	public SearchContext build() {
		SearchContext searchContext = new SearchContext();

		searchContext.setStart(_start);
		searchContext.setEnd(_end);
		searchContext.setAndSearch(_andSearch);
		searchContext.setCompanyId(_companyId);

		return searchContext;
	}

	private long _companyId;
	private int _start;
	private int _end;
	private boolean _andSearch;
}
