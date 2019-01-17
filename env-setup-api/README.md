# lfrgs-env-setup

the Env Setup Module is designed to make creating developer environments fast and easy.

this is designed with Liferay's UpgradeProcess in mind to keep track of the
various revisions that may be used in setting up developer environments. The 
primary target is for top level entities such as Users, Groups, and Roles.
 
Layouts, Organizations, and UserGroups are more context driven, but
are fairly safe to use within an environment setup implementation.

Portlets are best added after the portlet has been initialized, but within the 
Liferay OSGI implementation, this is difficult to achieve. as of 7.0, there is
a portlet service tracker that always runs last in module deployment
that wires portlets and their permissions together. Thus, if one is attempting 
to assign portlet permissions programmatically, it may not take effect. Portlets
are also best added to Layouts through their own UpgradeProcess or 
OSGI Component within the module containing the portlet.

for exammple an OSGI Component may add portlets to some pages with:

    @Activate
    protected void activate() {
        PortletConfig[] portletConfigs = new PortletConfig[]{ 
            ... group of portlets to put on the same layouts ... 
        };
        
        LayoutConfig[] layoutConfigs = new LayoutConfig[] {
            ... layouts to use ...    
        };
        
        SiteConfig siteConfig = new SiteConfig("my-site-friendly-url");
        
        addPortlets.addPortlets(
            PortalUtil.getDefaultCompnayId(), siteConfig, portletConfigs, layoutConfigs)
    }
    
    ...
    
    @Reference
    private AddPortlets addPortlets;

The test package contains a sample upgrade step registrar and module versioning 
scheme.

mvn repository under http://raw.github.com/arbetts/lfrgs-env-setup/repository

releases: 
- 1.0.0 initial api
- 1.1.0 added portlet api