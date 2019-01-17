package com.liferay.gs.env.setup.sample;

import com.liferay.gs.env.setup.upgrade.CompanyAwareUpgradeProcess;
import com.liferay.gs.env.setup.EnvSetupStep;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author andrewbetts
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SampleEnvSetupStepRegistrar implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			_bundleSymbolicName, "0.0.0", "1",
			new CompanyAwareUpgradeProcess(_addSampleSite, _portal));
		registry.register(
			_bundleSymbolicName, "1", "2",
			new CompanyAwareUpgradeProcess(_addSampleRoles, _portal));
		registry.register(
			_bundleSymbolicName, "2", "3",
			new CompanyAwareUpgradeProcess(_addSampleUsers, _portal));
		registry.register(
			_bundleSymbolicName, "3", "4",
			new CompanyAwareUpgradeProcess(_addSampleUserGroups, _portal));
		registry.register(
			_bundleSymbolicName, "4", "5",
			new CompanyAwareUpgradeProcess(
				_configureSampleUserGroups, _portal));
		registry.register(
			_bundleSymbolicName, "5", "6",
			new CompanyAwareUpgradeProcess(_addSampleLayouts, _portal));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleSymbolicName = bundleContext.getBundle().getSymbolicName();
	}

	private String _bundleSymbolicName;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference(target = "(component.name=*AddSampleSite)")
	private EnvSetupStep _addSampleSite;

	@Reference(target = "(component.name=*AddSampleRoles)")
	private EnvSetupStep _addSampleRoles;

	@Reference(target = "(component.name=*AddSampleUsers)")
	private EnvSetupStep _addSampleUsers;

	@Reference(target = "(component.name=*AddSampleUserGroups)")
	private EnvSetupStep _addSampleUserGroups;

	@Reference(target = "(component.name=*ConfigureSampleUserGroups)")
	private EnvSetupStep _configureSampleUserGroups;

	@Reference(target = "(component.name=*AddSampleLayouts)")
	private EnvSetupStep _addSampleLayouts;

	@Reference
	private Portal _portal;

}