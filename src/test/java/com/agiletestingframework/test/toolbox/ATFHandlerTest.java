package com.agiletestingframework.test.toolbox;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.agiletestingframework.toolbox.data.CSVDataDriver;
import com.agiletestingframework.toolbox.data.ExcelDataDriver;
import com.agiletestingframework.toolbox.data.ScenarioData;
import com.agiletestingframework.toolbox.data.TestDataProvider;
@SuppressWarnings("unused")
public class ATFHandlerTest {
    // Placeholder class to exercise the framework during development DO NOT
    // REMOVE !!!
    /*
     * private static TestDataProvider atfTestDataProvider;
     *
     * @DataProvider(name = "AddTwoNumbersEXCEL") public static Object[][]
     * additionDataEXCEL() { String testCaseName = "Add2Numbers"; return
     * atfTestDataProvider.initialize(new
     * ExcelDataDriver("./testData/datainjection-calculator-test-data.xlsx",
     * testCaseName)); }
     *
     * @DataProvider(name = "AddTwoNumbersCSV") public static Object[][]
     * additionDataCSV() { return atfTestDataProvider.initialize(new
     * CSVDataDriver("./testData", "datainjection-calculator-test-data")); }
     *
     * @BeforeClass(alwaysRun = true) public static void beforeClassSetup() {
     * atfTestDataProvider = new TestDataProvider(); }
     *
     * @Test(dataProvider = "AddTwoNumbersEXCEL", groups = { "datainjection" })
     * public void addTwoNumbersEXCEL(ScenarioData scenario) { int num1 =
     * scenario.getIntParameterData("firstNumber"); int num2 =
     * scenario.getIntParameterData("secondNumber"); int expectedResult =
     * scenario.getIntParameterData("expectedSum");
     *
     * assertThat((num1 + num2)).isEqualTo(expectedResult); }
     *
     * @Test(dataProvider = "AddTwoNumbersCSV", groups = { "datainjection" })
     * public void addTwoNumbersCSV(ScenarioData scenario) { int num1 =
     * scenario.getIntParameterData("firstNumber"); int num2 =
     * scenario.getIntParameterData("secondNumber"); int expectedResult =
     * scenario.getIntParameterData("expectedSum");
     *
     * assertThat((num1 + num2)).isEqualTo(expectedResult); }
     *
     *
     * @Test public void validateFirstNameAcceptsNoMoreThanMaxCharFAIL() {
     * ATFHandler.getInstance().getWebAutomation().getWebDriver().navigate().to(
     * "http://stackoverflow.com/questions/19011991/getting-error-when-trying-to-intialize-webdriver-for-firefox"
     * ); }
     *
     *
     * public VerifyEmailSoapService verifyEmailService;
     *
     * @BeforeClass(alwaysRun=true) public void BeforeGroupsSetup() {
     * verifyEmailService = new VerifyEmailSoapService(); }
     *
     * @AfterClass(alwaysRun=true) public void AfterGroupsTeardown() {
     * verifyEmailService.teardown(); }
     *
     * @Test(groups = { "soap" } ) public void shouldEmailForLicenseBeNotFound()
     * { String emailToVerify = "test@test.com"; String licenseKey = "123";
     *
     * String expectedBody =
     * "<VerifyEmailResult><ResponseText>User Not Found</ResponseText><ResponseCode>4</ResponseCode><LastMailServer>mx.spamexperts.com</LastMailServer><GoodEmail>false</GoodEmail></VerifyEmailResult>"
     * ;
     *
     * String response = verifyEmailService.verifyEmail(emailToVerify,
     * licenseKey);
     *
     * assertThat(response).isNotNull();
     * assertThat(response).containsIgnoringCase(expectedBody); }
     */
}
