package com.agiletestingframework.toolbox;

import java.util.List;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletestingframework.toolbox.managers.ConfigurationManager;
import com.agiletestingframework.toolbox.managers.DatabaseAutomationManager;
import com.agiletestingframework.toolbox.managers.MobileAutomationManager;
import com.agiletestingframework.toolbox.managers.ScreenImageAutomationManager;
import com.agiletestingframework.toolbox.managers.WebAutomationManager;
import com.agiletestingframework.toolbox.managers.WebServiceAutomationManager;

import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

public class ATFHandler {

    private static Logger log = LoggerFactory.getLogger(ATFHandler.class);

    private static ATFHandler atfHandlerInstance;

    private ATFHandler() {
    }

    /**
     * ATFHandler.getInstance
     *
     * @return the instance of the AFTHandler
     */
    public static ATFHandler getInstance() {
        if (atfHandlerInstance == null) {
            synchronized (ATFHandler.class) {
                ATFHandler inst = atfHandlerInstance;
                if (inst == null) {
                    synchronized (ATFHandler.class) {
                        atfHandlerInstance = new ATFHandler();
                        log.info("Created new instance of the ATFHandler.");
                    }
                }
            }
        }
        return atfHandlerInstance;
    }

    private volatile WebAutomationManager webAutomationInstance;

    /**
     * getWebAutomation Used to perform web automation tasks
     *
     * @return the instance of the WebAutomationManager
     */
    public WebAutomationManager getWebAutomation() {
        if (webAutomationInstance == null) {
            synchronized (WebAutomationManager.class) {
                WebAutomationManager inst = webAutomationInstance;
                if (inst == null) {
                    synchronized (WebAutomationManager.class) {
                        webAutomationInstance = new WebAutomationManager();
                        log.info("Created new instance of the Web Automation Manager.");
                    }
                }
            }
        }
        return webAutomationInstance;
    }

    private volatile DatabaseAutomationManager databaseAutomationInstance;

    /**
     * getDatabaseAutomation Used to perform database automation tasks
     *
     * @return the instance of the DatabaseAutomationManager
     */
    public DatabaseAutomationManager getDatabaseAutomation() {
        if (databaseAutomationInstance == null) {
            synchronized (DatabaseAutomationManager.class) {
                DatabaseAutomationManager inst = databaseAutomationInstance;
                if (inst == null) {
                    synchronized (DatabaseAutomationManager.class) {
                        databaseAutomationInstance = new DatabaseAutomationManager();
                        log.info("Created new instance of the Database Automation Manager.");
                    }
                }
            }
        }
        return databaseAutomationInstance;
    }

    private volatile WebServiceAutomationManager webServiceAutomationInstance;

    /**
     * getWebServiceAutomation Used to perform web service automation tasks
     *
     * @return the instance of the WebServiceAutomationManager
     */
    public WebServiceAutomationManager getWebServiceAutomation() {
        if (webServiceAutomationInstance == null) {
            synchronized (WebServiceAutomationManager.class) {
                WebServiceAutomationManager inst = webServiceAutomationInstance;
                if (inst == null) {
                    synchronized (WebServiceAutomationManager.class) {
                        webServiceAutomationInstance = new WebServiceAutomationManager();
                        log.info("Created new instance of the Web Service Automation Manager.");
                    }
                }
            }
        }
        return webServiceAutomationInstance;
    }

    private volatile MobileAutomationManager mobileAutomationInstance;

    /**
     * getMobileAutomation Used to perform mobile automation tasks
     *
     * @return the instance of the MobileAutomationManager
     */
    public MobileAutomationManager getMobileAutomation() {
        if (mobileAutomationInstance == null) {
            synchronized (MobileAutomationManager.class) {
                MobileAutomationManager inst = mobileAutomationInstance;
                if (inst == null) {
                    synchronized (MobileAutomationManager.class) {
                        mobileAutomationInstance = new MobileAutomationManager();
                        log.info("Created new instance of the Mobile Automation Manager.");
                    }
                }
            }
        }
        return mobileAutomationInstance;
    }

    private volatile ScreenImageAutomationManager screenImageAutomationInstance;

    /**
     * getScreenImageAutomation Used to perform screen and image automation
     * tasks
     *
     * @return the instance of the ScreenImageAutomationManager
     */
    public ScreenImageAutomationManager getScreenImageAutomation() {
        if (screenImageAutomationInstance == null) {
            synchronized (ScreenImageAutomationManager.class) {
                ScreenImageAutomationManager inst = screenImageAutomationInstance;
                if (inst == null) {
                    synchronized (ScreenImageAutomationManager.class) {
                        screenImageAutomationInstance = new ScreenImageAutomationManager();
                        log.info("Created new instance of the Screen Image Automation Manager.");
                    }
                }
            }
        }
        return screenImageAutomationInstance;
    }

    /**
     * teardown Will cleanup any resources used within the instance of this
     * ATFHandler
     */
    public void teardown() {
        if (webAutomationInstance != null) {
            if (webAutomationInstance != null) {
                // Log the js error collected if this reporting is on and we
                // were using a firefox driver
                if (webAutomationInstance.getWebDriver() instanceof FirefoxDriver
                        && ConfigurationManager.getInstance().getWebUseJSErrorCollectorWithFirefox()) {
                    List<JavaScriptError> jsErrors = JavaScriptError.readErrors(webAutomationInstance.getWebDriver());
                    for (JavaScriptError jsErr : jsErrors) {
                        log.error("JSErrorCollected: Line: " + jsErr.getLineNumber() + " Source: " + jsErr.getSourceName() + " Error: "
                                + jsErr.getErrorMessage());
                    }
                }
            }

            webAutomationInstance.teardown();

        }

        if (webServiceAutomationInstance != null) {
            webServiceAutomationInstance.teardown();
            webServiceAutomationInstance = null;
        }

        if (databaseAutomationInstance != null) {
            databaseAutomationInstance.teardown();
            databaseAutomationInstance = null;
        }

        if (mobileAutomationInstance != null) {
            mobileAutomationInstance.teardown();
            mobileAutomationInstance = null;
        }

        if (screenImageAutomationInstance != null) {
            screenImageAutomationInstance.teardown();
            screenImageAutomationInstance = null;
        }
    }
}
