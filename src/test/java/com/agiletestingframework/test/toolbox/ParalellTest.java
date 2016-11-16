package com.agiletestingframework.test.toolbox;


    /* Uncomment to validate parallel Testing
public class ParalellTest {


    @BeforeMethod(alwaysRun = true)
    public void BeforeMethodSetup() {
        System.out.println("BM Thread id = " + Thread.currentThread().getId());
        System.out.println("BM Hashcode of webDriver instance = " + ATFHandler.getInstance().getWebAutomation().getWebDriver().hashCode());
    }

    @AfterMethod(alwaysRun=true)
    public void afterTeardown() {
        System.out.println("AM Thread id = " + Thread.currentThread().getId());
        System.out.println("AM teardown");
        ATFHandler.getInstance().getWebAutomation().teardown();
    }

    @Test
    public void testMethod1()
    {
        invokeBrowser("http://www.cnn.com");

    }

    @Test
    public void testMethod2() {
        invokeBrowser("https://www.facebook.com");

    }

    @Test
    public void testMethod3() {
        invokeBrowser("http://www.agiletestingframework.com");

    }


    private void invokeBrowser(String url) {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println("Hashcode of webDriver instance = " + WebDriverManager.getDriver().hashCode());
        WebDriverManager.getDriver().get(url);

        assertThat(WebDriverManager.getDriver().getCurrentUrl()).contains(url);


}
        }*/
