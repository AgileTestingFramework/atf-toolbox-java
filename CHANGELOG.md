# Change Log

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