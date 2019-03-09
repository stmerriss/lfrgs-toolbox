package com.liferay.gs.search;

import com.liferay.gs.search.builders.ModelSearcherBuilder;
import com.liferay.gs.search.builders.SearchFilterBuilder;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class OrganizationSearcherBuilder
	extends ModelSearcherBuilder<OrganizationSearcherBuilder, Organization,
		OrganizationSearcher> {

	protected OrganizationSearcherBuilder() {
		_name = null;
		_organizationIds = new HashSet<>();
		_parentOrganizationId = new HashSet<>();
	}

	@Override
	protected Class<Organization> getModelClass() {
		return Organization.class;
	}

	public OrganizationSearcherBuilder setName(String name) {
		_name = name;

		return this;
	}

	public OrganizationSearcherBuilder setOrganizationIds(
		Long... organizationIds) {

		Collections.addAll(_organizationIds, organizationIds);

		return this;
	}

	public OrganizationSearcherBuilder setParentOrganizationIds(
		Long... parentOrganizationIds) {

		Collections.addAll(_parentOrganizationId, parentOrganizationIds);

		return this;
	}

	@Override
	protected SearchFilterBuilder getSearchFilterBuilder() {
		return new SearchFilterBuilder()
			.addFilter(Field.NAME, _name, BooleanClauseOccur.SHOULD)
			.addMultipleValues(
				Field.ORGANIZATION_ID, _organizationIds)
			.addMultipleValues(
				"parentOrganizationId", _parentOrganizationId)
			.addMultipleFields(
				_keywords,
				Arrays.asList(
					Field.NAME, Field.TYPE, Field.COMMENTS,
					Field.USER_NAME));
	}

	@Override
	protected OrganizationSearcherBuilder self() {
		return this;
	}

	@Override
	protected BiFunction<SearchContext, BooleanQuery, OrganizationSearcher> modelSearcherGenerator() {
		return OrganizationSearcher::new;
	}

	private String _name;
	private Set<Long> _organizationIds;
	private Set<Long> _parentOrganizationId;
}