package atf.toolbox.data;

import java.util.ArrayList;
import java.util.List;

/**
 * TestCaseData
 * This class holds a list of scenarios for a test case
 * A test case may have multiple data runs
 * ie. Login Test Case may consist of:
 * user\sa, pwd\password, language\english
 * user\sa, pwd\password, language\french
 * user\sa2, pwd\password, language\english
 * user\sa2, pwd\password, language\french
 */
public class TestCaseData {
	private String testCaseName;
	private List<ScenarioData> scenarios;
	
	/**
	 * Default Constructor
	 * Test Case Name will default to : unNamed Test Case
	 */
	public TestCaseData() {
		this("unNamed Test Case");
	}
	/**
	 * Constructor
	 * @param name Test Case Name
	 */
	public TestCaseData(String name) {
		this(name, new ArrayList<ScenarioData>());
	}
	/**
	 * Constructor
	 * @param name Test Case Name
	 * @param data list of test case scenario data
	 */
	public TestCaseData(String name, List<ScenarioData> data) {
		testCaseName = name;
		scenarios = data;
	}	
	/**
	 * getTestCaseName
	 * @return name of the test case
	 */
	public String getTestCaseName() {
		return testCaseName;
	}
	/**
	 * setListScenarioData
	 * @param data set the list of sceanrio data
	 */
	public void setListScenarioData(List<ScenarioData> data) {
		scenarios = data;
	}
	/**
	 * addScenarioData
	 * @param scenario a scenario to add to the test case
	 */
	public void addScenarioData(ScenarioData scenario) {
		scenarios.add(scenario);
	}
	/**
	 * getScenarioData
	 * @return list of sceanrio data
	 */
	public List<ScenarioData> getScenarioData() {
		return scenarios;
	}
	/**
	 * getNumberOfScenarios
	 * @return number of scenarios in this test case
	 */
	public int getNumberOfScenarios() {
		return scenarios.size();
	}
}
