package com.liferay.gs.search.sample;

import com.liferay.gs.search.BaseModelSearcher;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;

/**
 * @author Shane Merriss
 */
public class OrganizationSearcher extends BaseModelSearcher<Organization> {

	public OrganizationSearcher(
		SearchContext searchContext, Query searchQuery) {

		super(searchContext, searchQuery);
	}

}