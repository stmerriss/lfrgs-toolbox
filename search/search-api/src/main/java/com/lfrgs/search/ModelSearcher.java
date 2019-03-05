package com.lfrgs.search;

import com.lfrgs.search.builders.SearchContextBuilder;
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
public abstract class ModelSearcher<T extends BaseModel> implements Searcher<T> {

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
			count = IndexSearcherHelperUtil.searchCount(_searchContext, _searchQuery);
		}
		catch (SearchException se) {
			_log.error(se);
		}

		return count;
	}

	public static abstract class Builder<E extends Builder<E>> {

		public abstract Searcher build();

		protected abstract E self();

		public Builder setCompanyId(long companyId) {
			_companyId = companyId;

			return this;
		}

		public Builder setKeywords(String keywords) {
			_keywords = keywords;

			return this;
		}

		public Builder setStart(int start) {
			_start = start;

			return this;
		}

		public Builder setEnd(int end) {
			_end = end;

			return this;
		}

		protected SearchContext buildSearchContext() {
			return new SearchContextBuilder()
				.setCompanyId(_companyId)
				.setStart(_start)
				.setEnd(_end)
				.build();
		}

		protected long _companyId;
		protected String _keywords;
		private int _start;
		private int _end;
	}

	protected SearchContext _searchContext;
	protected BooleanQuery _searchQuery;

	private Log _log = LogFactoryUtil.getLog(ModelSearcher.class);
}
