package com.liferay.gs.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Shane Merriss
 */
public abstract class BaseModelSearcher<T extends BaseModel<T>>
	implements ModelSearcher<T> {

	public BaseModelSearcher(
		SearchContext searchContext, Query searchQuery) {

		_searchContext = searchContext;
		_searchQuery = searchQuery;
	}

	public List<T> search(Function<Document, T> documentToModelFunction) {
		List<T> models = new ArrayList<>();

		search().forEach(
			document -> models.add(documentToModelFunction.apply(document)));

		return models;
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
	protected Query _searchQuery;

	private static final Log _log = LogFactoryUtil.getLog(BaseModelSearcher.class);

}