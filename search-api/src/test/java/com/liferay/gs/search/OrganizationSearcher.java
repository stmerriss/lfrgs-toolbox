package com.liferay.gs.search;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;

/**
 * @author Shane Merriss
 */
public class OrganizationSearcher extends ModelSearcher<Organization> {

	public OrganizationSearcher(
		SearchContext searchContext, BooleanQuery searchQuery) {

		super(searchContext, searchQuery);
	}

}