# lfrgs-toolbox

this toolbox is intended to be a repository of api/tools that are helpful for easing the development process

## requirements for contributing a module
1) module root that is pluggable into a liferay gradle workspace
2) sample/test code resides in test package only
3) README.md file in module root that describes usage and purpose
4) semver compatible api for module versions and maven artifact releases

every module should be intended to be consumable as a maven artifact to provide some capability/functionality that can be included. 
Liferay dependencies are not considered 3rd party
everything else is considered 3rd party and must be satisfied within the module by the gradle dependnecies/build.

ultimate goal is that a developer can reference your module via their build.gradle and use the tool you provide without having to copy/paste your module into their workspace.
