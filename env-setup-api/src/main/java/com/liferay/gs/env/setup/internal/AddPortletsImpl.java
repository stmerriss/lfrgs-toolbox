package com.liferay.gs.env.setup.internal;

import com.liferay.gs.env.setup.AddPortlets;
import com.liferay.gs.env.setup.config.LayoutConfig;
import com.liferay.gs.env.setup.config.PortletConfig;
import com.liferay.gs.env.setup.config.SiteConfig;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component
public class AddPortletsImpl implements AddPortlets {

	@Override
	public void addPortlets(
			long companyId, SiteConfig siteConfig,
			PortletConfig[] portletConfigs, LayoutConfig[] layoutConfigs)
		throws PortalException {

		Group companyGroup = groupLocalService.getCompanyGroup(companyId);

		Group group = groupLocalService.getFriendlyURLGroup(
			companyId, siteConfig.getGroupFriendlyURL());

		for (PortletConfig portletConfig : portletConfigs) {
			for (LayoutConfig layoutConfig : layoutConfigs) {
				Layout layout = layoutLocalService.getFriendlyURLLayout(
					group.getGroupId(), layoutConfig.getPrivateLayout(),
					layoutConfig.getFriendlyURL());

				LayoutType layoutType = layout.getLayoutType();

				if (!(layoutType instanceof LayoutTypePortlet)) {
					continue;
				}

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layoutType;

				String portletId = layoutTypePortlet.addPortletId(
					companyGroup.getCreatorUserId(),
					portletConfig.getPortletId(), false);

				String xml = portletConfig.getXml();

				if ((portletId != null) && (xml != null) && !xml.isEmpty()) {
					portletPreferencesLocalService.updatePreferences(
						portletConfig.getOwnerId(),
						portletConfig.getOwnerType(), layout.getPlid(),
						portletId, portletConfig.getXml());
				}
			}
		}
	}

	@Reference
	private GroupLocalService groupLocalService;

	@Reference
	private LayoutLocalService layoutLocalService;

	@Reference
	private PortletLocalService portletLocalService;

	@Reference
	private PortletPreferencesLocalService portletPreferencesLocalService;

}