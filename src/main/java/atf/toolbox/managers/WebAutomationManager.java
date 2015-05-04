package atf.toolbox.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriverException;
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

import atf.toolbox.util.TestConstant;

import com.opera.core.systems.OperaDriver;


public class WebAutomationManager {

	private static Logger log = LoggerFactory.getLogger(WebAutomationManager.class);

	private WebDriver webDriver;
    public WebDriver getWebDriver() { return webDriver; }
   
    public WebAutomationManager()
    {
    	log.info("Initializing the WebAutomationManager.");
        webDriver = webDriverSetup();
    }

	/**
	 * webDriverSetup load the configured webdriver and options
	 * @return WebDriver
	 */
    private WebDriver webDriverSetup(){
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
        	
    		FirefoxBinary binary = new FirefoxBinary();
            File binaryFile = new File(ConfigurationManager.getInstance().getWebBrowserDownloadPath());
            
            if (binaryFile.exists()) { 
            	binary = new FirefoxBinary(binaryFile); 
        	}

            FirefoxProfile profile = new FirefoxProfile();
            File profileFile = new File(ConfigurationManager.getInstance().getFirefoxProfileDirecotryAndFilename());
            
            if (profileFile.exists()) {
            	profile = new FirefoxProfile(profileFile);
            }
            
            profile.setPreference("webdriver_accept_untrusted_certs", ConfigurationManager.getInstance().getFirefoxAcceptUntrustedCerts());
            profile.setPreference("webdriver_assume_untrusted_issuer", ConfigurationManager.getInstance().getFirefoxAssumeUntrustedIssuer());
            profile.setPreference("webdriver.log.driver", ConfigurationManager.getInstance().getFirefoxLogDriverLevel());
            profile.setPreference("webdriver.log.file", ConfigurationManager.getInstance().getFirefoxLogFile());
            profile.setPreference("webdriver.load.strategy", ConfigurationManager.getInstance().getFirefoxLoadStrategy());
            profile.setPreference("webdriver_firefox_port", ConfigurationManager.getInstance().getFirefoxPort());
            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
            
            capabilities.setCapability("mode", ConfigurationManager.getInstance());
            capabilities.setCapability("captureNetworkTraffic", ConfigurationManager.getInstance());
            capabilities.setCapability("addCustomRequestHeaders", ConfigurationManager.getInstance());
            capabilities.setCapability("trustAllSSLCertificates", ConfigurationManager.getInstance());
            
            if (!ConfigurationManager.getInstance().getUseGrid()) {
            	driver = new FirefoxDriver(binary, profile, capabilities);
            }

        } else if (browserName.toLowerCase().contains("ie") || browserName.toLowerCase().contains("explorer")) {
        	capabilities = DesiredCapabilities.internetExplorer();
        	
        	capabilities = setCommonCapabilities(capabilities);
        	
            System.setProperty("webdriver.ie.driver", new File(ConfigurationManager.getInstance().getWebBrowserDownloadPath()).getAbsolutePath());
            
            capabilities.setCapability("ignoreProtectedModeSettings", ConfigurationManager.getInstance().getIEIgnoreProtectedModeSettings());
            capabilities.setCapability("ignoreZoomSetting", ConfigurationManager.getInstance().getIEIgnoreZoomSetting());
            capabilities.setCapability("initialBrowserUrl", ConfigurationManager.getInstance().getIEInitialBrowserURL());
            capabilities.setCapability("enablePersistentHover", ConfigurationManager.getInstance().getIEEnablePersistentHover());
            capabilities.setCapability("enableElementCacheCleanup", ConfigurationManager.getInstance().getIEEnableElementCacheCleanup());
            capabilities.setCapability("requireWindowFocus", ConfigurationManager.getInstance().getIERequireWindowFocus());
            capabilities.setCapability("browserAttachTimeout", ConfigurationManager.getInstance().getIEBrowserAttachTimeout());
            capabilities.setCapability("ie.forceCreateProcessApi", ConfigurationManager.getInstance().getIEForceCreateProcessAPI());
            capabilities.setCapability("ie.browserCommandLineSwitches", ConfigurationManager.getInstance().getIEBrowserCmdLineSwitches());
            capabilities.setCapability("ie.usePerProcessProxy", ConfigurationManager.getInstance().getIEUsePerProcessProxy());
            capabilities.setCapability("ie.ensureCleanSession", ConfigurationManager.getInstance().getIEEnsureCleanSession());
            capabilities.setCapability("logFile", ConfigurationManager.getInstance().getIELogFile());
            capabilities.setCapability("logLevel", ConfigurationManager.getInstance().getIELogLevel());
            capabilities.setCapability("host", ConfigurationManager.getInstance().getIEHost());
            capabilities.setCapability("extractPath", ConfigurationManager.getInstance().getIEExtractPath());
            capabilities.setCapability("silent", ConfigurationManager.getInstance().getIESilent());
            capabilities.setCapability("ie.setProxyByServer", ConfigurationManager.getInstance().getIESetProxyByServer());
            
            capabilities.setCapability("mode", ConfigurationManager.getInstance().getIERCMode());
            capabilities.setCapability("killProcessesByName", ConfigurationManager.getInstance().getIERCKillProcessByName());
            capabilities.setCapability("honorSystemProxy", ConfigurationManager.getInstance().getIERCHonorSystemProxy());
            capabilities.setCapability("ensureCleanSession", ConfigurationManager.getInstance().getIERCEnsureCleanSession());
            
            if (!ConfigurationManager.getInstance().getUseGrid()) {
            	driver = new InternetExplorerDriver(capabilities);
            }
            
        } else if (browserName.toLowerCase().contains("safari")) {
       	
        	if (ConfigurationManager.getInstance().getSafariUseOptions()) {
	        	SafariOptions options = new SafariOptions();
	        	options.setDataDir(new File(ConfigurationManager.getInstance().getSafariDataDirectory()));
	        	options.setDriverExtension(new File(ConfigurationManager.getInstance().getSafariDriverExtension()));
	        	options.setPort(ConfigurationManager.getInstance().getSafariPort());
	        	options.setSkipExtensionInstallation(ConfigurationManager.getInstance().getSafariSkipExtentionInstallation());
	        	options.setUseCleanSession(ConfigurationManager.getInstance().getSafariCleanSession());
	        	
	            if (!ConfigurationManager.getInstance().getUseGrid()) {
	            	driver = new SafariDriver(options);
	            }       
        	} else {
            	capabilities = DesiredCapabilities.safari();
            	
            	capabilities = setCommonCapabilities(capabilities);
            	
            	capabilities.setCapability("mode", ConfigurationManager.getInstance().getSafariRCMode());
            	capabilities.setCapability("honorSystemProxy", ConfigurationManager.getInstance().getSafariRCHonorSystemProxy());
            	capabilities.setCapability("ensureCleanSession", ConfigurationManager.getInstance().getSafariCleanSession());

	            if (!ConfigurationManager.getInstance().getUseGrid()) {
	            	driver = new SafariDriver(capabilities);
	            }
        	}
        } else if (browserName.toLowerCase().contains("opera")) {
        	capabilities = DesiredCapabilities.opera();
        	
        	capabilities = setCommonCapabilities(capabilities);
        	
        	capabilities.setCapability("opera.binary", ConfigurationManager.getInstance().getOperaBinary());
        	capabilities.setCapability("opera.guess_binary_path", ConfigurationManager.getInstance().getOperaGuessBinaryPath());
        	capabilities.setCapability("opera.no_restart", ConfigurationManager.getInstance().getOperaNoRestart());
        	capabilities.setCapability("opera.product", ConfigurationManager.getInstance().getOperaProduct());
        	capabilities.setCapability("opera.no_quit", ConfigurationManager.getInstance().getOperaNoQuit());
        	capabilities.setCapability("opera.autostart", ConfigurationManager.getInstance().getOperaAutoStart());
        	capabilities.setCapability("opera.display", ConfigurationManager.getInstance().getOperaDisplay());
        	capabilities.setCapability("opera.idle", ConfigurationManager.getInstance().getOperaIdle());
        	capabilities.setCapability("opera.profile", ConfigurationManager.getInstance().getOperaProfileDirectory());
        	capabilities.setCapability("opera.launcher", ConfigurationManager.getInstance().getOperaLauncher());
        	capabilities.setCapability("opera.port", ConfigurationManager.getInstance().getOperaPort());
        	capabilities.setCapability("opera.host", ConfigurationManager.getInstance().getOperaHost());
        	capabilities.setCapability("opera.arguments", ConfigurationManager.getInstance().getOperaArguments());
        	capabilities.setCapability("opera.logging.file", ConfigurationManager.getInstance().getOperaLoggingFile());
        	capabilities.setCapability("opera.logging.level", ConfigurationManager.getInstance().getOperaLoggingLevel());
        	
            if (!ConfigurationManager.getInstance().getUseGrid()) {
            	driver = new OperaDriver(capabilities);
            }
	
        } else if (browserName.toLowerCase().contains("htmlunitdriver")) {
        	capabilities = DesiredCapabilities.htmlUnitWithJs();
        	
        	capabilities = setCommonCapabilities(capabilities);
            
        	if (!ConfigurationManager.getInstance().getUseGrid()) {
            	driver = new HtmlUnitDriver(ConfigurationManager.getInstance().getJavascriptEnabled());
            }      	
        	
        } else if (browserName.toLowerCase().contains("chrome")) {
        	capabilities = DesiredCapabilities.chrome();
        	
        	capabilities = setCommonCapabilities(capabilities);
        	
        	ChromeOptions options = new ChromeOptions();
        	options.addArguments(ConfigurationManager.getInstance().getChromeArgs());
        	options.setBinary(new File(ConfigurationManager.getInstance().getChromeBinary()));
        	
        	Proxy proxy = new Proxy();
        	proxy.setHttpProxy(ConfigurationManager.getInstance().getChromeProxy());
        	capabilities.setCapability("proxy", proxy);
        	
        	List<File> fileExtensions = new ArrayList<File>();
        	for (String extension : ConfigurationManager.getInstance().getChromeExtensions()) {
        		if (extension != "") {
        			fileExtensions.add(new File(extension));
        		}
        	}
        	options.addExtensions(fileExtensions);
       	
        	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        	
  	
        	if (!ConfigurationManager.getInstance().getUseGrid()) {
            	driver = new ChromeDriver(capabilities);
            }
        	
        } else if (browserName.toLowerCase().contains("phantom")) {
        	capabilities = DesiredCapabilities.phantomjs();
        	
        	capabilities = setCommonCapabilities(capabilities);
        	
        	capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, ConfigurationManager.getInstance().getPhantomJSExecutablePath());
        	capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, ConfigurationManager.getInstance().getPhantomJSGhostdriverPath());
        	capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, ConfigurationManager.getInstance().getPhantomJSPageSettingsPrefix());
        	capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX, ConfigurationManager.getInstance().getPhantomJSPAgeCustomHeadersPrefix());
        	capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, ConfigurationManager.getInstance().getPhantomJSCLIArgs());
        	capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, ConfigurationManager.getInstance().getPhantomJSGhostdriverCLIArgs());
        	
            if (!ConfigurationManager.getInstance().getUseGrid()) {
            	driver = new PhantomJSDriver(capabilities);
            }
        }
        
        if (ConfigurationManager.getInstance().getUseGrid())
        {
        	driver = new RemoteWebDriver(capabilities);
        } 

        log.trace(String.format("Using browser='%s', with getCapabilities='%s'", driver.toString(), ((RemoteWebDriver) driver).getCapabilities().toString()));

        Timeouts timeouts = driver.manage().timeouts();
        timeouts.implicitlyWait(1, TimeUnit.SECONDS);
        timeouts.setScriptTimeout(TestConstant.WAIT_SECONDS_LONG, TimeUnit.SECONDS);
        timeouts.pageLoadTimeout(TestConstant.WAIT_SECONDS_LONG * 2, TimeUnit.SECONDS);

        driver.manage().window().maximize();

    	return driver;
    }
    
    private DesiredCapabilities setCommonCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability("browserVersion", ConfigurationManager.getInstance().getWebBrowserVersion());
        capabilities.setCapability("platform", ConfigurationManager.getInstance().getPlatform());
        capabilities.setCapability("takesScreenshot", ConfigurationManager.getInstance().getTakesScreenshot());
        capabilities.setCapability("handlesAlerts", ConfigurationManager.getInstance().getHandlesAlerts());
        capabilities.setCapability("cssSelectorsEnabled", ConfigurationManager.getInstance().getCSSSelectorsEnabled());
        capabilities.setCapability("javascriptEnabled", ConfigurationManager.getInstance().getJavascriptEnabled());
        capabilities.setCapability("databaseEnabled", ConfigurationManager.getInstance().getDatabaseEnabled());
        capabilities.setCapability("locationContextEnabled", ConfigurationManager.getInstance().getLocationContextEnabled());
        capabilities.setCapability("applicationCacheEnabled", ConfigurationManager.getInstance().getApplicationCacheEnabled());
        capabilities.setCapability("browserConnectionEnabled", ConfigurationManager.getInstance().getBrowserConnectionEnabled());
        capabilities.setCapability("webStorageEnabled", ConfigurationManager.getInstance().getWebStorageEnabled());
        capabilities.setCapability("acceptSslCerts", ConfigurationManager.getInstance().getAcceptSSLCerts());
        capabilities.setCapability("rotatable", ConfigurationManager.getInstance().getRotatable());
        capabilities.setCapability("nativeEvents", ConfigurationManager.getInstance().getNativeEvents());
        capabilities.setCapability("unexpectedAlertBehaviour", ConfigurationManager.getInstance().getUnexpectedAlertBehavior());
        capabilities.setCapability("elementScrollBehavior", ConfigurationManager.getInstance().getElementScrollBehavior());

        // JSON Proxy
        capabilities.setCapability("proxyType", ConfigurationManager.getInstance().getJSONProxyType());
        capabilities.setCapability("proxyAutoconfigUrl", ConfigurationManager.getInstance().getJSONProxyAutoConfigURL());
        capabilities.setCapability(ConfigurationManager.getInstance().getJSONProxy(), ConfigurationManager.getInstance().getJSONProxy());
        capabilities.setCapability("socksUsername", ConfigurationManager.getInstance().getJSONSocksUsername());
        capabilities.setCapability("socksPassword", ConfigurationManager.getInstance().getJSONSocksPassword());
        capabilities.setCapability("noProxy", ConfigurationManager.getInstance().getJSONNoProxy());
        capabilities.setCapability("component", ConfigurationManager.getInstance().getJSONLoggingComponent());

        capabilities.setCapability("webdriver.remote.quietExceptions", ConfigurationManager.getInstance().getRemoteWebDriverQuietExceptions());
        
        return capabilities;
    }
  
    public void teardown()
    {
        if (webDriver != null)
        {
        	try {
	            // Shut down the webdriver
	            log.info("Webdriver teardown started.");
	            webDriver.quit();
	            webDriver = null;
	            log.info("Webdriver teardown complete.");
        	}
            catch (WebDriverException wde) 
            {
                log.error("Error encountered during Webdriver teardown.", wde);
            }
        }
    }

}
