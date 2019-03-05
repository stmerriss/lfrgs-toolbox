package com.lfrgs.search.impl;

import com.lfrgs.search.builders.SearchFilterBuilder;
import com.lfrgs.search.builders.SearchQueryBuilder;
import com.lfrgs.search.ModelSearcher;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import org.osgi.service.component.annotations.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Shane Merriss
 */
@Component(
	immediate = true,
	service = OrganizationSearcher.class
)
public class OrganizationSearcher extends ModelSearcher<Organization> {

	public enum OrganizationField {
		PARENT_ORGANIZATION_ID("parentOrganizationId");

		OrganizationField(String value) {
			_value = value;
		}

		public String getValue() {
			return this._value;
		}

		private final String _value;
	}

	public OrganizationSearcher(SearchContext searchContext, BooleanQuery searchQuery) {
		_searchContext = searchContext;
		_searchQuery = searchQuery;
	}

	public static class Builder extends ModelSearcher.Builder<Builder> {

		public Builder() {
			_name = null;
			_organizationIds = new HashSet<>();
			_parentOrganizationId = new HashSet<>();
		}

		public Builder addName(String name) {
			_name = name;

			return this;
		}

		public Builder addUserIds(Long... organizationIds) {
			_organizationIds.addAll(ListUtil.fromArray(organizationIds));

			return this;
		}

		public Builder addParentOrganizationIds(Long... parentOrganizationIds) {
			_parentOrganizationId.addAll(ListUtil.fromArray(parentOrganizationIds));

			return this;
		}

		public OrganizationSearcher build() {
			SearchContext searchContext = super.buildSearchContext();

			BooleanFilter searchFilter = new SearchFilterBuilder()
				.addFilter(Field.NAME, _name, BooleanClauseOccur.SHOULD)
				.addMultipleValues(Field.ORGANIZATION_ID, ArrayUtil.toLongArray(_organizationIds))
				.addMultipleValues(OrganizationField.PARENT_ORGANIZATION_ID.getValue(), ArrayUtil.toLongArray(_parentOrganizationId))
				.addMultipleFields(_keywords, Field.NAME, Field.TYPE, Field.COMMENTS, Field.USER_NAME)
				.build();

			BooleanQuery searchQuery = new SearchQueryBuilder(Organization.class)
				.addFilter(searchFilter)
				.build();

			return new OrganizationSearcher(searchContext, searchQuery);
		}

		@Override
		protected Builder self() {
			return this;
		}

		private String _name;
		private Set<Long> _organizationIds;
		private Set<Long> _parentOrganizationId;
	}
}
