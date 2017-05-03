package com.agiletestingframework.test.toolbox;

import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.agiletestingframework.toolbox.data.CSVDataDriver;
import com.agiletestingframework.toolbox.data.ExcelDataDriver;
import com.agiletestingframework.toolbox.data.ScenarioData;
import com.agiletestingframework.toolbox.data.TestDataProvider;

public class DataDriverTest {
    private static Logger log = LoggerFactory.getLogger(DataDriverTest.class);
    private static TestDataProvider atfTestDataProvider;

    @DataProvider(name = "AddTwoNumbersEXCEL")
    public static Object[][] additionDataEXCEL() {
        String testCaseName = "Add2Numbers";
        return atfTestDataProvider.initialize(new ExcelDataDriver("./testData/datainjection-calculator-test-data.xlsx", testCaseName));
    }

    @DataProvider(name = "AddTwoNumbersCSV")
    public static Object[][] additionDataCSV() {
        return atfTestDataProvider.initialize(new CSVDataDriver("./testData", "datainjection-calculator-test-data"));
    }

    @BeforeClass(alwaysRun = true)
    public static void beforeClassSetup() {
        log.info("Creating TestDataProvider");
        atfTestDataProvider = new TestDataProvider();
    }

    @Test(enabled=false, dataProvider = "AddTwoNumbersEXCEL", groups = { "datainjection" } )
    public void addTwoNumbersEXCEL(ScenarioData scenario) {

        int num1 = scenario.getIntParameterData("firstNumber");
        int num2 = scenario.getIntParameterData("secondNumber");
        int expectedResult = scenario.getIntParameterData("expectedSum");
        log.info("Adding two numbers from EXCEL ({} + {} = {})", num1, num2, expectedResult);

        assertThat((num1 + num2)).isEqualTo(expectedResult);
    }

    @Test(dataProvider = "AddTwoNumbersCSV", groups = { "datainjection" })
    public void addTwoNumbersCSV(ScenarioData scenario) {
        int num1 = scenario.getIntParameterData("firstNumber");
        int num2 = scenario.getIntParameterData("secondNumber");
        int expectedResult = scenario.getIntParameterData("expectedSum");
        log.info("Adding two numbers from CSV ({} + {} = {})", num1, num2, expectedResult);

        assertThat((num1 + num2)).isEqualTo(expectedResult);
    }

}
