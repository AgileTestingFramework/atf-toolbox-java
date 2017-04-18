package com.agiletestingframework.toolbox.data;

import com.agiletestingframework.toolbox.interfaces.DataDriver;

public class TestDataProvider {

    /**
     * Initialize
     * 
     * @param driver
     *            the data driver to use to initialize the test data for the
     *            testcase
     * @return the scenarios of data for the test case
     */
    public Object[][] initialize(DataDriver driver) {
        TestCaseData testCase = driver.load();

        return processScenarios(testCase);
    }

    private Object[][] processScenarios(TestCaseData data) {

        Object[][] processedData = new Object[data.getNumberOfScenarios()][1];

        int scenarioCount = 0;

        for (ScenarioData scenario : data.getScenarioData()) {

            if (scenario.getTestCaseName() == null || scenario.getTestCaseName() == "")
                scenario.setTestCaseName(data.getTestCaseName());

            processedData[scenarioCount][0] = scenario;
            scenarioCount++;

        }

        return processedData;
    }
}
