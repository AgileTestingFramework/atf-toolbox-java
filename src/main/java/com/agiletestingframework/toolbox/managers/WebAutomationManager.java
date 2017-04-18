package com.agiletestingframework.toolbox.managers;

import com.agiletestingframework.toolbox.util.TestConstant;
import com.opera.core.systems.OperaDriver;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebAutomationManager {

    private static Logger log = LoggerFactory.getLogger(WebAutomationManager.class);

    /**
     * getWebDriver
     *
     * @return the local instance of the webDriver. will create new instance if
     *         one doesn't already exist to return.
     */
    public WebDriver getWebDriver() {
        if (WebDriverManager.getDriver() == null) {
            WebDriverManager.setWebDriver(webDriverSetup());
        }

        return WebDriverManager.getDriver();
    }

    private TakesScreenshot takesScreenshot;

    public TakesScreenshot getTakesScreenshot() {
        return takesScreenshot;
    }

    public WebAutomationManager() {
        log.info("Initializing the WebAutomationManager.");
    }

    /**
     * webDriverSetup load the configured webdriver and options
     *
     * @return WebDriver
     */
    private WebDriver webDriverSetup() {
        WebDriver driver = null;

        // SETUP Common Capabilities
        // Configure Candidate Browser Information
        String browserName = ConfigurationManager.getInstance().getWebBrowserName();
        String platform = ConfigurationManager.getInstance().getPlatform();

        // Set defaults if not configured
        if (StringUtils.isBlank(browserName)) {
            browserName = BrowserType.FIREFOX;
        }

        if (StringUtils.isBlank(platform)) {
            platform = Platform.ANY.toString();
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browserName.toLowerCase().contains("firefox")) {
            capabilities = DesiredCapabilities.firefox();

            capabilities = setCommonCapabilities(capabilities);

            FirefoxBinary binary = null;
            File binaryFile = null;
            if (ConfigurationManager.getInstance().getWebBrowserDownloadPath().length() > 0) {
                binaryFile = new File(ConfigurationManager.getInstance().getWebBrowserDownloadPath());
            }

            if (binaryFile != null && binaryFile.exists()) {
                binary = new FirefoxBinary(binaryFile);
                System.setProperty("webdriver.firefox.bin", ConfigurationManager.getInstance().getWebBrowserDownloadPath());
                capabilities.setCapability("binary", ConfigurationManager.getInstance().getWebBrowserDownloadPath());
                capabilities.setCapability("firefox_binary", ConfigurationManager.getInstance().getWebBrowserDownloadPath());

            }

            FirefoxProfile profile = new FirefoxProfile();
            File profileFile = null;
            if (ConfigurationManager.getInstance().getFirefoxProfileDirecotryAndFilename().length() > 0)
                profileFile = new File(ConfigurationManager.getInstance().getFirefoxProfileDirecotryAndFilename());

            if (profileFile != null && profileFile.exists()) {
                profile = new FirefoxProfile(profileFile);
            }

            if (ConfigurationManager.getInstance().getFirefoxAcceptUntrustedCerts() != null)
                profile.setPreference("webdriver_accept_untrusted_certs", ConfigurationManager.getInstance().getFirefoxAcceptUntrustedCerts());
            if (ConfigurationManager.getInstance().getFirefoxAssumeUntrustedIssuer() != null)
                profile.setPreference("webdriver_assume_untrusted_issuer", ConfigurationManager.getInstance().getFirefoxAssumeUntrustedIssuer());
            if (ConfigurationManager.getInstance().getFirefoxLogDriverLevel() != null)
                profile.setPreference("webdriver.log.driver", ConfigurationManager.getInstance().getFirefoxLogDriverLevel());
            if (ConfigurationManager.getInstance().getFirefoxLogFile().length() > 0)
                profile.setPreference("webdriver.log.file", ConfigurationManager.getInstance().getFirefoxLogFile());
            if (ConfigurationManager.getInstance().getFirefoxLoadStrategy().length() > 0)
                profile.setPreference("webdriver.load.strategy", ConfigurationManager.getInstance().getFirefoxLoadStrategy());

            if (ConfigurationManager.getInstance().getFirefoxPort() != null)
                profile.setPreference("webdriver_firefox_port", ConfigurationManager.getInstance().getFirefoxPort());

            capabilities.setCapability(FirefoxDriver.PROFILE, profile);

            if (ConfigurationManager.getInstance().getFirefoxRCMode().length() > 0)
                capabilities.setCapability("mode", ConfigurationManager.getInstance().getFirefoxRCMode());
            if (ConfigurationManager.getInstance().getFirefoxRCCaptureNetworkTraffic() != null)
                capabilities.setCapability("captureNetworkTraffic", ConfigurationManager.getInstance().getFirefoxRCCaptureNetworkTraffic());
            if (ConfigurationManager.getInstance().getFirefoxRCAddCustomReqHeader() != null)
                capabilities.setCapability("addCustomRequestHeaders", ConfigurationManager.getInstance().getFirefoxRCAddCustomReqHeader());
            if (ConfigurationManager.getInstance().getFirefoxRCTrustAllSSLCerts() != null)
                capabilities.setCapability("trustAllSSLCertificates", ConfigurationManager.getInstance());

            if (ConfigurationManager.getInstance().getUseGrid() != null && !ConfigurationManager.getInstance().getUseGrid()) {
                driver = new FirefoxDriver(binary, profile, capabilities);
            }

        } else if (browserName.toLowerCase().contains("ie") || browserName.toLowerCase().contains("explorer")) {
            capabilities = DesiredCapabilities.internetExplorer();

            capabilities = setCommonCapabilities(capabilities);

            System.setProperty("webdriver.ie.driver", new File(ConfigurationManager.getInstance().getWebBrowserDownloadPath()).getAbsolutePath());

            if (ConfigurationManager.getInstance().getIEIgnoreProtectedModeSettings() != null)
                capabilities.setCapability("ignoreProtectedModeSettings", ConfigurationManager.getInstance().getIEIgnoreProtectedModeSettings());
            if (ConfigurationManager.getInstance().getIEIgnoreZoomSetting() != null)
                capabilities.setCapability("ignoreZoomSetting", ConfigurationManager.getInstance().getIEIgnoreZoomSetting());
            if (ConfigurationManager.getInstance().getIEInitialBrowserURL() != null)
                capabilities.setCapability("initialBrowserUrl", ConfigurationManager.getInstance().getIEInitialBrowserURL());
            if (ConfigurationManager.getInstance().getIEEnablePersistentHover() != null)
                capabilities.setCapability("enablePersistentHover", ConfigurationManager.getInstance().getIEEnablePersistentHover());
            if (ConfigurationManager.getInstance().getIEEnableElementCacheCleanup() != null)
                capabilities.setCapability("enableElementCacheCleanup", ConfigurationManager.getInstance().getIEEnableElementCacheCleanup());
            if (ConfigurationManager.getInstance().getIERequireWindowFocus() != null)
                capabilities.setCapability("requireWindowFocus", ConfigurationManager.getInstance().getIERequireWindowFocus());
            if (ConfigurationManager.getInstance().getIEBrowserAttachTimeout() != null)
                capabilities.setCapability("browserAttachTimeout", ConfigurationManager.getInstance().getIEBrowserAttachTimeout());
            if (ConfigurationManager.getInstance().getIEForceCreateProcessAPI() != null)
                capabilities.setCapability("ie.forceCreateProcessApi", ConfigurationManager.getInstance().getIEForceCreateProcessAPI());
            if (ConfigurationManager.getInstance().getIEBrowserCmdLineSwitches() != null)
                capabilities.setCapability("ie.browserCommandLineSwitches", ConfigurationManager.getInstance().getIEBrowserCmdLineSwitches());
            if (ConfigurationManager.getInstance().getIEUsePerProcessProxy() != null)
                capabilities.setCapability("ie.usePerProcessProxy", ConfigurationManager.getInstance().getIEUsePerProcessProxy());
            if (ConfigurationManager.getInstance().getIEEnsureCleanSession() != null)
                capabilities.setCapability("ie.ensureCleanSession", ConfigurationManager.getInstance().getIEEnsureCleanSession());
            if (ConfigurationManager.getInstance().getIELogFile().length() > 0)
                capabilities.setCapability("logFile", ConfigurationManager.getInstance().getIELogFile());
            if (ConfigurationManager.getInstance().getIELogLevel().length() > 0)
                capabilities.setCapability("logLevel", ConfigurationManager.getInstance().getIELogLevel());
            if (ConfigurationManager.getInstance().getIEHost().length() > 0)
                capabilities.setCapability("host", ConfigurationManager.getInstance().getIEHost());
            if (ConfigurationManager.getInstance().getIEExtractPath().length() > 0)
                capabilities.setCapability("extractPath", ConfigurationManager.getInstance().getIEExtractPath());
            if (ConfigurationManager.getInstance().getIESilent() != null)
                capabilities.setCapability("silent", ConfigurationManager.getInstance().getIESilent());
            if (ConfigurationManager.getInstance().getIESetProxyByServer() != null)
                capabilities.setCapability("ie.setProxyByServer", ConfigurationManager.getInstance().getIESetProxyByServer());

            if (ConfigurationManager.getInstance().getIERCMode().length() > 0)
                capabilities.setCapability("mode", ConfigurationManager.getInstance().getIERCMode());
            if (ConfigurationManager.getInstance().getIERCKillProcessByName() != null)
                capabilities.setCapability("killProcessesByName", ConfigurationManager.getInstance().getIERCKillProcessByName());
            if (ConfigurationManager.getInstance().getIERCHonorSystemProxy() != null)
                capabilities.setCapability("honorSystemProxy", ConfigurationManager.getInstance().getIERCHonorSystemProxy());
            if (ConfigurationManager.getInstance().getIERCEnsureCleanSession() != null)
                capabilities.setCapability("ensureCleanSession", ConfigurationManager.getInstance().getIERCEnsureCleanSession());

            if (ConfigurationManager.getInstance().getUseGrid() != null && !ConfigurationManager.getInstance().getUseGrid()) {
                driver = new InternetExplorerDriver(capabilities);
            }

        } else if (browserName.toLowerCase().contains("microsoftedge")) {
            capabilities = DesiredCapabilities.edge();

            String microsoftEdgeVersion = "13.10586";
            if (ConfigurationManager.getInstance().getMicrosoftEdgeVersion() != null) {
                microsoftEdgeVersion = ConfigurationManager.getInstance().getMicrosoftEdgeVersion();
            }

            capabilities.setCapability("version", microsoftEdgeVersion);
            capabilities.setCapability("platform", "Windows 10");
            capabilities = setCommonCapabilities(capabilities);

        } else if (browserName.toLowerCase().contains("safari")) {

            if (ConfigurationManager.getInstance().getSafariUseOptions() != null && ConfigurationManager.getInstance().getSafariUseOptions()) {
                SafariOptions options = new SafariOptions();
                if (ConfigurationManager.getInstance().getSafariPort() != null)
                    options.setPort(ConfigurationManager.getInstance().getSafariPort());
                if (ConfigurationManager.getInstance().getSafariCleanSession() != null)
                    options.setUseCleanSession(ConfigurationManager.getInstance().getSafariCleanSession());

                if (ConfigurationManager.getInstance().getUseGrid() != null && !ConfigurationManager.getInstance().getUseGrid()) {
                    driver = new SafariDriver(options);
                }
            } else {
                capabilities = DesiredCapabilities.safari();

                capabilities = setCommonCapabilities(capabilities);

                if (ConfigurationManager.getInstance().getSafariRCMode().length() > 0)
                    capabilities.setCapability("mode", ConfigurationManager.getInstance().getSafariRCMode());
                if (ConfigurationManager.getInstance().getSafariRCHonorSystemProxy() != null)
                    capabilities.setCapability("honorSystemProxy", ConfigurationManager.getInstance().getSafariRCHonorSystemProxy());
                if (ConfigurationManager.getInstance().getSafariCleanSession() != null)
                    capabilities.setCapability("ensureCleanSession", ConfigurationManager.getInstance().getSafariCleanSession());

                if (ConfigurationManager.getInstance().getUseGrid() && !ConfigurationManager.getInstance().getUseGrid()) {
                    driver = new SafariDriver(capabilities);
                }
            }
        } else if (browserName.toLowerCase().contains("opera")) {
            capabilities = DesiredCapabilities.opera();

            capabilities = setCommonCapabilities(capabilities);

            if (ConfigurationManager.getInstance().getOperaBinary().length() > 0)
                capabilities.setCapability("opera.binary", ConfigurationManager.getInstance().getOperaBinary());
            if (ConfigurationManager.getInstance().getOperaGuessBinaryPath() != null)
                capabilities.setCapability("opera.guess_binary_path", ConfigurationManager.getInstance().getOperaGuessBinaryPath());
            if (ConfigurationManager.getInstance().getOperaNoRestart() != null)
                capabilities.setCapability("opera.no_restart", ConfigurationManager.getInstance().getOperaNoRestart());
            if (ConfigurationManager.getInstance().getOperaProduct().length() > 0)
                capabilities.setCapability("opera.product", ConfigurationManager.getInstance().getOperaProduct());
            if (ConfigurationManager.getInstance().getOperaNoQuit() != null)
                capabilities.setCapability("opera.no_quit", ConfigurationManager.getInstance().getOperaNoQuit());
            if (ConfigurationManager.getInstance().getOperaAutoStart() != null)
                capabilities.setCapability("opera.autostart", ConfigurationManager.getInstance().getOperaAutoStart());
            if (ConfigurationManager.getInstance().getOperaDisplay() != null)
                capabilities.setCapability("opera.display", ConfigurationManager.getInstance().getOperaDisplay());
            if (ConfigurationManager.getInstance().getOperaIdle() != null)
                capabilities.setCapability("opera.idle", ConfigurationManager.getInstance().getOperaIdle());
            if (ConfigurationManager.getInstance().getOperaProfileDirectory().length() > 0)
                capabilities.setCapability("opera.profile", ConfigurationManager.getInstance().getOperaProfileDirectory());
            if (ConfigurationManager.getInstance().getOperaLauncher().length() > 0)
                capabilities.setCapability("opera.launcher", ConfigurationManager.getInstance().getOperaLauncher());
            if (ConfigurationManager.getInstance().getOperaPort() != null)
                capabilities.setCapability("opera.port", ConfigurationManager.getInstance().getOperaPort());
            if (ConfigurationManager.getInstance().getOperaHost().length() > 0)
                capabilities.setCapability("opera.host", ConfigurationManager.getInstance().getOperaHost());
            if (ConfigurationManager.getInstance().getOperaArguments().length() > 0)
                capabilities.setCapability("opera.arguments", ConfigurationManager.getInstance().getOperaArguments());
            if (ConfigurationManager.getInstance().getOperaLoggingFile().length() > 0)
                capabilities.setCapability("opera.logging.file", ConfigurationManager.getInstance().getOperaLoggingFile());
            if (ConfigurationManager.getInstance().getOperaLoggingLevel().length() > 0)
                capabilities.setCapability("opera.logging.level", ConfigurationManager.getInstance().getOperaLoggingLevel());

            if (ConfigurationManager.getInstance().getUseGrid() != null && !ConfigurationManager.getInstance().getUseGrid()) {
                driver = new OperaDriver(capabilities);
            }

        } else if (browserName.toLowerCase().contains("htmlunitdriver")) {
            capabilities = DesiredCapabilities.htmlUnitWithJs();

            capabilities = setCommonCapabilities(capabilities);

            if (ConfigurationManager.getInstance().getUseGrid() != null && !ConfigurationManager.getInstance().getUseGrid()) {
                driver = new HtmlUnitDriver(ConfigurationManager.getInstance().getJavascriptEnabled());
            }

        } else if (browserName.toLowerCase().contains("chrome")) {
            capabilities = DesiredCapabilities.chrome();
            capabilities = setCommonCapabilities(capabilities);
            System.setProperty("webdriver.chrome.driver", new File(ConfigurationManager.getInstance().getWebBrowserDownloadPath()).getAbsolutePath());

            ChromeOptions options = new ChromeOptions();
            if (ConfigurationManager.getInstance().getChromeArgs() != null && !ConfigurationManager.getInstance().getChromeArgs().isEmpty())
                options.addArguments(ConfigurationManager.getInstance().getChromeArgs());
            if (ConfigurationManager.getInstance().getChromeBinary().length() > 0)
                options.setBinary(new File(ConfigurationManager.getInstance().getChromeBinary()));
            if (ConfigurationManager.getInstance().getChromeExtensions() != null
                    && !ConfigurationManager.getInstance().getChromeExtensions().isEmpty()) {
                List<File> fileExtensions = new ArrayList<File>();
                for (String extension : ConfigurationManager.getInstance().getChromeExtensions()) {
                    if (extension != "") {
                        fileExtensions.add(new File(extension));
                    }
                }
                options.addExtensions(fileExtensions);
            }
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            if (ConfigurationManager.getInstance().getChromeProxy().length() > 0) {
                Proxy proxy = new Proxy();
                proxy.setHttpProxy(ConfigurationManager.getInstance().getChromeProxy());
                capabilities.setCapability("proxy", proxy);
            }

            if (ConfigurationManager.getInstance().getUseGrid() != null && !ConfigurationManager.getInstance().getUseGrid()) {
                driver = new ChromeDriver(capabilities);
            }

        } else if (browserName.toLowerCase().contains("phantom")) {
            capabilities = DesiredCapabilities.phantomjs();

            capabilities = setCommonCapabilities(capabilities);

            if (ConfigurationManager.getInstance().getPhantomJSExecutablePath().length() > 0)
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                        ConfigurationManager.getInstance().getPhantomJSExecutablePath());
            if (ConfigurationManager.getInstance().getPhantomJSGhostdriverPath().length() > 0)
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY,
                        ConfigurationManager.getInstance().getPhantomJSGhostdriverPath());
            if (ConfigurationManager.getInstance().getPhantomJSPageSettingsPrefix().length() > 0)
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX,
                        ConfigurationManager.getInstance().getPhantomJSPageSettingsPrefix());
            if (ConfigurationManager.getInstance().getPhantomJSPAgeCustomHeadersPrefix().length() > 0)
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX,
                        ConfigurationManager.getInstance().getPhantomJSPAgeCustomHeadersPrefix());
            if (ConfigurationManager.getInstance().getPhantomJSCLIArgs().length() > 0)
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, ConfigurationManager.getInstance().getPhantomJSCLIArgs());
            if (ConfigurationManager.getInstance().getPhantomJSGhostdriverCLIArgs().length() > 0)
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
                        ConfigurationManager.getInstance().getPhantomJSGhostdriverCLIArgs());

            if (ConfigurationManager.getInstance().getUseGrid() != null && !ConfigurationManager.getInstance().getUseGrid()) {
                driver = new PhantomJSDriver(capabilities);
            }
        }

        if (ConfigurationManager.getInstance().getUseGrid()) {
            URL gridUrl = null;

            // Code for sauce based on Jekins configuration of a multi-matrix
            // project with sauce on demand plugin
            try {
                String username = System.getenv("SAUCE_USERNAME");
                String accesskey = System.getenv("SAUCE_ACCESS_KEY");
                gridUrl = new URL("http://" + username + ":" + accesskey + "@ondemand.saucelabs.com:80/wd/hub");
            } catch (MalformedURLException e) {
                log.error("Unable to create grid URL to create remote web driver.");
            }
            capabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
            capabilities.setVersion(System.getenv("SELENIUM_VERSION"));
            capabilities.setCapability("platform", System.getenv("SELENIUM_PLATFORM"));

            if (ConfigurationManager.getInstance().getSauceSuiteName() != null) {
                capabilities.setCapability("name", ConfigurationManager.getInstance().getSauceSuiteName());
            } else {
                if (System.getenv("SAUCE_JOB_NAME") != null) {
                    capabilities.setCapability("name", System.getenv("SAUCE_JOB_NAME"));
                } else if (System.getProperty("SAUCE_JOB_NAME") != null) {
                    capabilities.setCapability("name", System.getProperty("SAUCE_JOB_NAME"));
                } else {
                    long currentTimeMillis = System.currentTimeMillis();
                    capabilities.setCapability("name", String.format("ATF Job %s", currentTimeMillis));
                }
            }

            driver = new RemoteWebDriver(gridUrl, capabilities);
        }

        log.trace(String.format("Using browser='%s', with getCapabilities='%s'", driver.toString(),
                ((RemoteWebDriver) driver).getCapabilities().toString()));

        Timeouts timeouts = driver.manage().timeouts();
        timeouts.implicitlyWait(1, TimeUnit.SECONDS);
        timeouts.setScriptTimeout(TestConstant.WAIT_SECONDS_LONG, TimeUnit.SECONDS);
        timeouts.pageLoadTimeout(TestConstant.WAIT_SECONDS_LONG * 2, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        return driver;
    }

    private DesiredCapabilities setCommonCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability("platform", Platform.ANY);

        if (ConfigurationManager.getInstance().getPlatform().length() > 0) {
            if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("windows")) {
                capabilities.setCapability("platform", Platform.WINDOWS);
            } else if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("win8")) {
                capabilities.setCapability("platform", Platform.WIN8);
            } else if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("win81")) {
                capabilities.setCapability("platform", Platform.WIN8_1);
            } else if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("linux")) {
                capabilities.setCapability("platform", Platform.LINUX);
            } else if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("mac")) {
                capabilities.setCapability("platform", Platform.MAC);
            } else if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("XP")) {
                capabilities.setCapability("platform", Platform.XP);
            } else if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("unix")) {
                capabilities.setCapability("platform", Platform.UNIX);
            } else if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("Vista")) {
                capabilities.setCapability("platform", Platform.VISTA);
            } else if (ConfigurationManager.getInstance().getPlatform().toLowerCase().contains("android")) {
                capabilities.setCapability("platform", Platform.ANDROID);
            }
        }

        if (ConfigurationManager.getInstance().getWebBrowserVersion().length() > 0)
            capabilities.setCapability("browserVersion", ConfigurationManager.getInstance().getWebBrowserVersion());
        if (ConfigurationManager.getInstance().getTakesScreenshot() != null)
            capabilities.setCapability("takesScreenshot", ConfigurationManager.getInstance().getTakesScreenshot());
        if (ConfigurationManager.getInstance().getHandlesAlerts() != null)
            capabilities.setCapability("handlesAlerts", ConfigurationManager.getInstance().getHandlesAlerts());
        if (ConfigurationManager.getInstance().getCSSSelectorsEnabled() != null)
            capabilities.setCapability("cssSelectorsEnabled", ConfigurationManager.getInstance().getCSSSelectorsEnabled());
        if (ConfigurationManager.getInstance().getJavascriptEnabled() != null)
            capabilities.setCapability("javascriptEnabled", ConfigurationManager.getInstance().getJavascriptEnabled());
        if (ConfigurationManager.getInstance().getDatabaseEnabled() != null)
            capabilities.setCapability("databaseEnabled", ConfigurationManager.getInstance().getDatabaseEnabled());
        if (ConfigurationManager.getInstance().getLocationContextEnabled() != null)
            capabilities.setCapability("locationContextEnabled", ConfigurationManager.getInstance().getLocationContextEnabled());
        if (ConfigurationManager.getInstance().getApplicationCacheEnabled() != null)
            capabilities.setCapability("applicationCacheEnabled", ConfigurationManager.getInstance().getApplicationCacheEnabled());
        if (ConfigurationManager.getInstance().getBrowserConnectionEnabled() != null)
            capabilities.setCapability("browserConnectionEnabled", ConfigurationManager.getInstance().getBrowserConnectionEnabled());
        if (ConfigurationManager.getInstance().getWebStorageEnabled() != null)
            capabilities.setCapability("webStorageEnabled", ConfigurationManager.getInstance().getWebStorageEnabled());
        if (ConfigurationManager.getInstance().getAcceptSSLCerts() != null)
            capabilities.setCapability("acceptSslCerts", ConfigurationManager.getInstance().getAcceptSSLCerts());
        if (ConfigurationManager.getInstance().getRotatable() != null)
            capabilities.setCapability("rotatable", ConfigurationManager.getInstance().getRotatable());
        if (ConfigurationManager.getInstance().getNativeEvents() != null)
            capabilities.setCapability("nativeEvents", ConfigurationManager.getInstance().getNativeEvents());
        if (ConfigurationManager.getInstance().getUnexpectedAlertBehavior() != null)
            capabilities.setCapability("unexpectedAlertBehaviour", ConfigurationManager.getInstance().getUnexpectedAlertBehavior());
        if (ConfigurationManager.getInstance().getElementScrollBehavior() != null)
            capabilities.setCapability("elementScrollBehavior", ConfigurationManager.getInstance().getElementScrollBehavior());

        // JSON Proxy
        if (ConfigurationManager.getInstance().getJSONProxyType().length() > 0)
            capabilities.setCapability("proxyType", ConfigurationManager.getInstance().getJSONProxyType());
        if (ConfigurationManager.getInstance().getJSONProxyAutoConfigURL().length() > 0)
            capabilities.setCapability("proxyAutoconfigUrl", ConfigurationManager.getInstance().getJSONProxyAutoConfigURL());
        if (ConfigurationManager.getInstance().getJSONProxy().length() > 0)
            capabilities.setCapability(ConfigurationManager.getInstance().getJSONProxy(), ConfigurationManager.getInstance().getJSONProxy());
        if (ConfigurationManager.getInstance().getJSONSocksUsername().length() > 0)
            capabilities.setCapability("socksUsername", ConfigurationManager.getInstance().getJSONSocksUsername());
        if (ConfigurationManager.getInstance().getJSONSocksPassword().length() > 0)
            capabilities.setCapability("socksPassword", ConfigurationManager.getInstance().getJSONSocksPassword());
        if (ConfigurationManager.getInstance().getJSONNoProxy().length() > 0)
            capabilities.setCapability("noProxy", ConfigurationManager.getInstance().getJSONNoProxy());
        if (ConfigurationManager.getInstance().getJSONLoggingComponent().length() > 0)
            capabilities.setCapability("component", ConfigurationManager.getInstance().getJSONLoggingComponent());
        if (ConfigurationManager.getInstance().getRemoteWebDriverQuietExceptions() != null)
            capabilities.setCapability("webdriver.remote.quietExceptions", ConfigurationManager.getInstance().getRemoteWebDriverQuietExceptions());

        return capabilities;
    }

    public void teardown() {
        if (getWebDriver() != null) {
            try {
                // Shut down the webdriver
                getWebDriver().quit();
                WebDriverManager.setWebDriver(null);
                log.info("Webdriver teardown complete.");
            } catch (WebDriverException wde) {
                log.error("Error encountered during Webdriver teardown.", wde);
            }
        }
    }

    /**
     * This method will close the current window in focus. It should be used to
     * close a windows that is opened and currently has focus.
     */
    public void closeCurrentWindow() {
        if (getWebDriver() != null) {
            try {
                // Closes the current window in focus
                log.info("Webdriver teardown started.");
                // closes the current window in focus
                getWebDriver().close();
                log.info("Webdriver window close complete.");
            } catch (WebDriverException wde) {
                log.error("Error encountered during Webdriver window close.", wde);
            }
        }
    }

}
