package com.liferay.gs.search.sample;

import com.liferay.gs.search.builders.BaseModelSearcherBuilder;
import com.liferay.gs.search.builders.SearchFilterBuilder;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;

import org.osgi.service.component.annotations.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * @author Shane Merriss
 * @author Andrew Betts
 */
@Component
public class OrganizationSearcherBuilder
	extends BaseModelSearcherBuilder<OrganizationSearcherBuilder, Organization,
			OrganizationSearcher> {

	public OrganizationSearcherBuilder() {
		_name = null;
		_organizationIds = new HashSet<>();
		_parentOrganizationId = new HashSet<>();
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
	public SearchFilterBuilder getSearchFilterBuilder() {
		return new SearchFilterBuilder()
			.addFilter(Field.NAME, _name, BooleanClauseOccur.SHOULD)
			.addMultipleValues(
				Field.ORGANIZATION_ID, _organizationIds)
			.addMultipleValues(
				"parentOrganizationId", _parentOrganizationId)
			.addMultipleFields(
				keywords,
				Arrays.asList(
					Field.NAME, Field.TYPE, Field.COMMENTS,
					Field.USER_NAME));
	}

	@Override
	public BiFunction<SearchContext, Query, OrganizationSearcher> modelSearcherGenerator() {
		return OrganizationSearcher::new;
	}

	@Override
	protected Class<Organization> getModelClass() {
		return Organization.class;
	}

	@Override
	protected OrganizationSearcherBuilder self() {
		return this;
	}

	private String _name;
	private Set<Long> _organizationIds;
	private Set<Long> _parentOrganizationId;

}