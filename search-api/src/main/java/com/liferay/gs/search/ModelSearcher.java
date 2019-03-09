package com.liferay.gs.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Shane Merriss
 */
public abstract class ModelSearcher<T extends BaseModel<T>>
	implements Searcher<Document> {

	public ModelSearcher(
		SearchContext searchContext, BooleanQuery searchQuery) {

		_searchContext = searchContext;
		_searchQuery = searchQuery;
	}

	public List<Document> search() {
		Hits hits = null;

		try {
			hits = IndexSearcherHelperUtil.search(_searchContext, _searchQuery);
		}
		catch (SearchException se) {
			_log.error(se);
		}

		if (Objects.isNull(hits)) {
			return new ArrayList<>();
		}

		return hits.toList();
	}

	public long searchCount() {
		long count = 0;

		try {
			count = IndexSearcherHelperUtil.searchCount(
				_searchContext, _searchQuery);
		}
		catch (SearchException se) {
			_log.error(se);
		}

		return count;
	}

	protected SearchContext _searchContext;
	protected BooleanQuery _searchQuery;

	private static final Log _log = LogFactoryUtil.getLog(ModelSearcher.class);

}