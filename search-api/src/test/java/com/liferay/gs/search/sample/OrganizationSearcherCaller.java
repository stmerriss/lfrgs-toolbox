package com.liferay.gs.search.sample;

public class OrganizationSearcherCaller {

	public void search() {
		OrganizationSearcher organizationSearcher =
			new OrganizationSearcherBuilder()
				.setName("orgName")
				.setOrganizationIds(0L, 1L, 2L)
				.setParentOrganizationIds(3L, 4L, 5L)
				.setCompanyId(20154)
				.setKeywords("test")
				.setStart(0)
				.setEnd(5)
				.setAndSearch(true)
				.build();

		organizationSearcher.searchCount();
		organizationSearcher.search();
	}

}