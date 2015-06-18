package test.atf.toolbox;


import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ATFHandlerTest {

	// Placeholder class to exercise the framework during development DO NOT REMOVE !!!
	/*
	 * @Test public void validateFirstNameAcceptsNoMoreThanMaxCharFAIL() {
	 * ATFHandler.getInstance().getWebAutomation().getWebDriver().navigate().to(
	 * "http://stackoverflow.com/questions/19011991/getting-error-when-trying-to-intialize-webdriver-for-firefox"
	 * ); }
	
	
	public VerifyEmailSoapService verifyEmailService;
	
	@BeforeClass(alwaysRun=true)
	public void BeforeGroupsSetup()
	{
		verifyEmailService = new VerifyEmailSoapService();
	}
	@AfterClass(alwaysRun=true)
	public void AfterGroupsTeardown()
	{
		verifyEmailService.teardown();
	}

    @Test(groups = { "soap" } )
    public void shouldEmailForLicenseBeNotFound() {
    	String emailToVerify = "test@test.com";
    	String licenseKey = "123";
    	
    	String expectedBody = "<VerifyEmailResult><ResponseText>User Not Found</ResponseText><ResponseCode>4</ResponseCode><LastMailServer>mx.spamexperts.com</LastMailServer><GoodEmail>false</GoodEmail></VerifyEmailResult>";
    	
    	String response = verifyEmailService.verifyEmail(emailToVerify, licenseKey);
    	
    	assertThat(response).isNotNull();
    	assertThat(response).containsIgnoringCase(expectedBody);
    }
     */
}
