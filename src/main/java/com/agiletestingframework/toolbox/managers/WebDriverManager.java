package com.agiletestingframework.toolbox.managers;

import org.openqa.selenium.WebDriver;

/**
 * Manage webdriver, one per thread to support parallel testing
 */
public class WebDriverManager {

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
}
