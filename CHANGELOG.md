# Change Log
**5.1.3**
- Added ZAP Webdriver classes to support security testing samples
-- Added configuring a ZAP proxy to WebDriver creation
- Added deploy profile to pom.xml, so 'mvn clean install' does not prompt for pgp key

**5.1.2**
- Added cucumber dependency

**5.1.0**
- Changed Selenium Version to 3.4.0
- Changed sqlsheet to version 6.7, this fixes the issue we with addTwoNumbersEXCEL
- Removed jbehave, was unused
- Added NgWebDriver and JSoup to project

**5.0.5**
- Added ability to set Sauce Screen Resolution
- Removed sqlsheet dependency while we wait for new version to be published. This will prevent you from using Excel spreadsheet as a data source. We are working on resolving the issue soon.
-- Ignoring addTwoNumbersEXCEL test
- Added setting "webdriver.gecko.driver" with value from "atf/desired-capabilities/firefox/gecko-driver-path" in environment xml
- Added setting tunnelIdentifier with value from "atf/desired-capabilities/firefox/gecko-driver-path" in environment xml
- Added .editorconfig and atf-eclipse-format.xml to help enforce style guidelines
- Fixed deprecation warning about opera
- Fixed warnings about unused imports

**Implemented enhancements:**
- Added WebAutomationManager.closeCurrentWindow functionality (Bill Stapleton)