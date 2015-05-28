package atf.toolbox.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.lang3.StringUtils;
import org.fest.assertions.api.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;

public class ConfigurationManager {
	private static Logger log = LoggerFactory.getLogger(ConfigurationManager.class);
	private String defaultConfigurationFileName = "atf-config.xml";

	
	private static ConfigurationManager atfConfigurationInstance;
	private ConfigurationManager() { loadConfiguration(); }

	/**
	 * ConfigurationManager.getInstance
	 * 
	 * @return the instance of the ConfigurationManager
	 */
	public static ConfigurationManager getInstance() {
		if (atfConfigurationInstance == null) {
			synchronized (ConfigurationManager.class) {
				ConfigurationManager inst = atfConfigurationInstance;
				if (inst == null) {
					synchronized (ConfigurationManager.class) {
						atfConfigurationInstance = new ConfigurationManager();
						log.info("Created new instance of the ATFConfigurationManager.");
					}
				}
			}
		}
		return atfConfigurationInstance;
	}
	
	/**
	 * AllConfiguration
	 * Contains all configuration entries
	 */
	public Configuration AllConfiguration;

	private void loadConfiguration()
	{
		Properties sysProps = System.getProperties();
        String configFileName = sysProps.getProperty("test.config.filename");
        
        if (StringUtils.isEmpty(configFileName)) {
        	configFileName = defaultConfigurationFileName;
        }
        
        try {
        	AllConfiguration = new DefaultConfigurationBuilder(configFileName).getConfiguration();
            ((HierarchicalConfiguration) AllConfiguration).setExpressionEngine(new XPathExpressionEngine());
        } catch (Exception e) {
            Fail.fail("failed to read config file", e);
            log.error("Failed to read config file", e);
        }		
	}
	
    /**
     * RefreshConfiguration
     * Read the configuration and refresh
     */
    public void RefreshConfiguration()
    {
    	AllConfiguration = null;
    	loadConfiguration();
    }
    
    /**
     * getBooleanConfigEntry
     * @param xPathKey - Key in XPath format to locate in loaded config file
     * @return - Boolean entry from config file. Will return false if not found.
     */
    public Boolean getBooleanConfigEntry(String xPathKey)
    {
        	if (AllConfiguration.containsKey(xPathKey) && AllConfiguration.getString(xPathKey).length() != 0)
    		{
        		try
        		{
        			return Boolean.parseBoolean(AllConfiguration.getString(xPathKey));
        		}
        		catch (Exception ex)
        		{
        			return null;
        		}
    		}
        	else return null;
    }
    
    /**
     * getListEntry
     * @param xPathKey
     * @return List of strings. Will return empty List<string> if not found.
     */
    public List<String> getListEntry(String xPathKey)
    {
    	List<String> listOfConfig = new ArrayList<String>();
    	
    	if (AllConfiguration.containsKey(xPathKey)) {
    		listOfConfig = Lists.transform(AllConfiguration.getList(xPathKey), Functions.toStringFunction());
    	}
    	
    	return listOfConfig;
    }
    
    /**
     * getStringEntry
     * @param xPathKey - Key in XPath format to locate in loaded config file
     * @return - String entry from config file. Will return empty string if not found.
     */
    public String getStringEntry(String xPathKey)
    {
     	String returnValue = "";
    	try {
    		if (AllConfiguration.containsKey(xPathKey)) return AllConfiguration.getString(xPathKey);
    		else return returnValue;
    	}
    	catch (Exception ex) {
    		return returnValue;
    	}
    }
    
    /**
     * getIntEntry
     * @param xPathKey - Key in XPath format to locate in loaded config file
     * @return - integer entry from config file. Will return 0 if not found.
     */
    public Integer getIntEntry(String xPathKey)
    {
    	try {
        	if (AllConfiguration.containsKey(xPathKey)) return Integer.parseInt(AllConfiguration.getString(xPathKey));
        	else return null;
    	}
    	catch (Exception e)
    	{
    		return null;
    	}
    }
    
    public void Teardown()
    {
    	// TODO: any specific teardown required for the Configuration Manager
    }
    
	/********************************************************************************/
	/************* CONFIGURATION GET METHODS BELOW **********************************/
	/********************************************************************************/    
    
	/********************************************************************************/
	/************* WEB **************************************************************/
	/********************************************************************************/
    public String getWebBaseUrl()
    {
    	String key = "atf/web/base-url";
    	return getStringEntry(key);
    }
    
	/********************************************************************************/
	/************* DESIRED CAPABILITIES**********************************************/
	/********************************************************************************/
    public String getWebBrowserName()
    {
    	String key = "atf/desired-capabilities/browser-name";
    	return getStringEntry(key);
    }
    public String getWebBrowserVersion()
    {
    	String key = "atf/desired-capabilities/browser-version";
    	return getStringEntry(key);
    }
    public String getPlatform()
    {
    	String key = "atf/desired-capabilities/platform";
    	return getStringEntry(key);
    }
    public String getWebBrowserDownloadPath()
    {
    	String key = "atf/desired-capabilities/browser-download-path";
    	return getStringEntry(key);
    }
    public Boolean getTakesScreenshot() {
    	String key = "atf/desired-capabilities/takes-screenshot";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getHandlesAlerts() {
    	String key = "atf/desired-capabilities/handles-alerts";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getCSSSelectorsEnabled() {
    	String key = "atf/desired-capabilities/css-selectors-enabled";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getJavascriptEnabled() {
    	String key = "atf/desired-capabilities/javascript-enabled";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getDatabaseEnabled() {
    	String key = "atf/desired-capabilities/database-enabled";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getLocationContextEnabled() {
    	String key = "atf/desired-capabilities/location-context-enabled";
    	return getBooleanConfigEntry(key);
    }     
    public Boolean getApplicationCacheEnabled() {
    	String key = "atf/desired-capabilities/application-cache-enabled";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getBrowserConnectionEnabled() {
    	String key = "atf/desired-capabilities/browser-connection-enabled";
    	return getBooleanConfigEntry(key);
    }      
    public Boolean getWebStorageEnabled() {
    	String key = "atf/desired-capabilities/web-storage-enabled";
    	return getBooleanConfigEntry(key);
    }     
    public Boolean getAcceptSSLCerts() {
    	String key = "atf/desired-capabilities/accept-ssl-certs";
    	return getBooleanConfigEntry(key);
    }      
    public Boolean getRotatable() {
    	String key = "atf/desired-capabilities/rotatable";
    	return getBooleanConfigEntry(key);
    }      
    public Boolean getNativeEvents() {
    	String key = "atf/desired-capabilities/native-events";
    	return getBooleanConfigEntry(key);
    }
    public String getUnexpectedAlertBehavior() {
    	String key = "atf/desired-capabilities/unexpected-alert-behavior";
    	return getStringEntry(key);
    }    
    public Integer getElementScrollBehavior() {
    	String key = "atf/desired-capabilities/element-scroll-behavior";
    	return getIntEntry(key);
    }     
    public String getJSONProxyType() {
    	String key = "atf/desired-capabilities/json-proxy-type";
    	return getStringEntry(key);
    }      
    public String getJSONProxyAutoConfigURL() {
    	String key = "atf/desired-capabilities/json-proxy-auto-config-url";
    	return getStringEntry(key);
    }    
    public String getJSONProxy() {
    	String key = "atf/desired-capabilities/json-proxy";
    	return getStringEntry(key);
    }    
    public String getJSONSocksUsername() {
    	String key = "atf/desired-capabilities/json-socks-username";
    	return getStringEntry(key);
    }    
    public String getJSONSocksPassword() {
    	String key = "atf/desired-capabilities/json-socks-password";
    	return getStringEntry(key);
    }    
    public String getJSONNoProxy() {
    	String key = "atf/desired-capabilities/json-no-proxy";
    	return getStringEntry(key);
    }    
    public String getJSONLoggingComponent() {
    	String key = "atf/desired-capabilities/json-logging-component";
    	return getStringEntry(key);
    }
    public Boolean getRemoteWebDriverQuietExceptions() {
    	String key = "atf/desired-capabilities/remote-webdriver-quiet-exceptions";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getUseGrid() {
    	String key = "atf/desired-capabilities/grid-use";
    	return getBooleanConfigEntry(key);
    }
    public String getGridUrl() {
    	String key = "atf/desired-capabilities/grid-url";
    	return getStringEntry(key);
    }   
    public String getGridSeleniumProtocol() {
    	String key = "atf/desired-capabilities/grid-selenium-protocol";
    	return getStringEntry(key);
    }     
    public Integer getGridMaxInstances() {
    	String key = "atf/desired-capabilities/grid-max-instances";
    	return getIntEntry(key);
    }    
  
    
    // OPERA
    public String getOperaBinary() {
    	String key = "atf/desired-capabilities/opera/binary";
    	return getStringEntry(key);
    }     
    public Boolean getOperaGuessBinaryPath() {
    	String key = "atf/desired-capabilities/opera/guess-binary-path";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getOperaNoRestart() {
    	String key = "atf/desired-capabilities/opera/no-restart";
    	return getBooleanConfigEntry(key);
    }    
    public String getOperaProduct() {
    	String key = "atf/desired-capabilities/opera/product";
    	return getStringEntry(key);
    }     
    public Boolean getOperaNoQuit() {
    	String key = "atf/desired-capabilities/opera/no-quit";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getOperaAutoStart() {
    	String key = "atf/desired-capabilities/opera/autostart";
    	return getBooleanConfigEntry(key);
    }    
    public Integer getOperaDisplay() {
    	String key = "atf/desired-capabilities/opera/display";
    	return getIntEntry(key);
    }    
    public Boolean getOperaIdle() {
    	String key = "atf/desired-capabilities/opera/idle";
    	return getBooleanConfigEntry(key);
    }    
    public String getOperaProfileDirectory() {
    	String key = "atf/desired-capabilities/opera/profile-directory";
    	return getStringEntry(key);
    }    
    public String getOperaLauncher() {
    	String key = "atf/desired-capabilities/opera/launcher";
    	return getStringEntry(key);
    }    
    public Integer getOperaPort() {
    	String key = "atf/desired-capabilities/opera/port";
    	return getIntEntry(key);
    }    
    public String getOperaHost() {
    	String key = "atf/desired-capabilities/opera/host";
    	return getStringEntry(key);
    } 
    public String getOperaArguments() {
    	String key = "atf/desired-capabilities/opera/arguments";
    	return getStringEntry(key);
    }
    public String getOperaLoggingFile() {
    	String key = "atf/desired-capabilities/opera/logging-file";
    	return getStringEntry(key);
    }
    public String getOperaLoggingLevel() {
    	String key = "atf/desired-capabilities/opera/logging-level";
    	return getStringEntry(key);
    } 
    
    // CHROME
    public List<String> getChromeArgs() {
    	String key = "atf/desired-capabilities/chrome/cmndline-args";
    	return getListEntry(key);
    }
    public String getChromeBinary() {
    	String key = "atf/desired-capabilities/chrome/binary";
    	return getStringEntry(key);
    }
    public List<String> getChromeExtensions() {
    	String key = "atf/desired-capabilities/chrome/extentions";
    	return getListEntry(key);
    }
    public String getChromeProxy() {
    	String key = "atf/desired-capabilities/chrome/proxy";
    	return getStringEntry(key);
    }    
    
    // FIREFOX
    public String getFirefoxProfileDirecotryAndFilename() {
    	String key = "atf/desired-capabilities/firefox/profile-dir-and-filename";
    	return getStringEntry(key);
    }   
    public String getFirefoxBinary() {
    	String key = "atf/desired-capabilities/firefox/binary";
    	return getStringEntry(key);
    }    
    public String getFirefoxRCMode() {
    	String key = "atf/desired-capabilities/firefox/rc-mode";
    	return getStringEntry(key);
    }    
    public Boolean getFirefoxRCCaptureNetworkTraffic() {
    	String key = "atf/desired-capabilities/firefox/rc-capture-network-traffic";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getFirefoxRCAddCustomReqHeader() {
    	String key = "atf/desired-capabilities/firefox/rc-add-custom-request-header";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getFirefoxRCTrustAllSSLCerts() {
    	String key = "atf/desired-capabilities/firefox/rc-trust-all-ssl-cert";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getFirefoxAcceptUntrustedCerts() {
    	String key = "atf/desired-capabilities/firefox/webdriver-accept-untrusted-certs";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getFirefoxAssumeUntrustedIssuer() {
    	String key = "atf/desired-capabilities/firefox/webdriver-assume-untrusted-issuer";
    	return getBooleanConfigEntry(key);
    }   
    public String getFirefoxLogDriverLevel() {
    	String key = "atf/desired-capabilities/firefox/webdriver-log-driver-level";
    	return getStringEntry(key);
    }
    public String getFirefoxLogFile() {
    	String key = "atf/desired-capabilities/firefox/webdriver-log-file";
    	return getStringEntry(key);
    }
    public String getFirefoxLoadStrategy() {
    	String key = "atf/desired-capabilities/firefox/webdriver-load-strategy";
    	return getStringEntry(key);
    }
    public Integer getFirefoxPort() {
    	String key = "atf/desired-capabilities/firefox/webdriver-port";
    	return getIntEntry(key);
    }
    
    // INTERNET EXPLORER
    public Boolean getIEIgnoreProtectedModeSettings() {
    	String key = "atf/desired-capabilities/ie/ignore-protected-mode-settings";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getIEIgnoreZoomSetting() {
    	String key = "atf/desired-capabilities/ie/ignore-zoom-setting";
    	return getBooleanConfigEntry(key);
    }    
    public String getIEInitialBrowserURL() {
    	String key = "atf/desired-capabilities/ie/initial-browser-url";
    	return getStringEntry(key);
    }    
    public Boolean getIEEnablePersistentHover() {
    	String key = "atf/desired-capabilities/ie/enable-persistent-hover";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getIEEnableElementCacheCleanup() {
    	String key = "atf/desired-capabilities/ie/enable-element-cache-cleanup";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getIERequireWindowFocus() {
    	String key = "atf/desired-capabilities/ie/require-window-focus";
    	return getBooleanConfigEntry(key);
    }     
    public Integer getIEBrowserAttachTimeout() {
    	String key = "atf/desired-capabilities/ie/browser-attach-timeout";
    	return getIntEntry(key);
    }    
    public Boolean getIEForceCreateProcessAPI() {
    	String key = "atf/desired-capabilities/ie/force-create-process-api";
    	return getBooleanConfigEntry(key);
    }    
    public String getIEBrowserCmdLineSwitches() {
    	String key = "atf/desired-capabilities/ie/browser-cmd-line-switches";
    	return getStringEntry(key);
    }    
    public Boolean getIEUsePerProcessProxy() {
    	String key = "atf/desired-capabilities/ie/use-per-process-proxy";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getIEEnsureCleanSession() {
    	String key = "atf/desired-capabilities/ie/ensure-clean-session";
    	return getBooleanConfigEntry(key);
    }    
    public String getIELogFile() {
    	String key = "atf/desired-capabilities/ie/log-file";
    	return getStringEntry(key);
    } 
    public String getIELogLevel() {
    	String key = "atf/desired-capabilities/ie/log-level";
    	return getStringEntry(key);
    } 
    public String getIEHost() {
    	String key = "atf/desired-capabilities/ie/host";
    	return getStringEntry(key);
    } 
    public String getIEExtractPath() {
    	String key = "atf/desired-capabilities/ie/extract-path";
    	return getStringEntry(key);
    } 
    public Boolean getIESilent() {
    	String key = "atf/desired-capabilities/ie/silent";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getIESetProxyByServer() {
    	String key = "atf/desired-capabilities/ie/set-proxy-by-server";
    	return getBooleanConfigEntry(key);
    }   
    public String getIERCMode() {
    	String key = "atf/desired-capabilities/ie/rc-mode";
    	return getStringEntry(key);
    } 
    public Boolean getIERCKillProcessByName() {
    	String key = "atf/desired-capabilities/ie/rc-kill-processes-by-name";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getIERCHonorSystemProxy() {
    	String key = "atf/desired-capabilities/ie/rc-honor-system-proxy";
    	return getBooleanConfigEntry(key);
    }    
    public Boolean getIERCEnsureCleanSession() {
    	String key = "atf/desired-capabilities/ie/rc-ensure-clean-session";
    	return getBooleanConfigEntry(key);
    }    
    
    // SAFARI
    public Boolean getSafariUseOptions() {
    	String key = "atf/desired-capabilities/safari/use-options";
    	return getBooleanConfigEntry(key);
    }
    public String getSafariDataDirectory() {
    	String key = "atf/desired-capabilities/safari/data-dir";
    	return getStringEntry(key);
    }
    public String getSafariDriverExtension() {
    	String key = "atf/desired-capabilities/safari/driver-extension";
    	return getStringEntry(key);
    }
    public Boolean getSafariCleanSession() {
    	String key = "atf/desired-capabilities/safari/clean-session";
    	return getBooleanConfigEntry(key);
    }
    public Boolean getSafariSkipExtentionInstallation() {
    	String key = "atf/desired-capabilities/safari/skip-extension-installation";
    	return getBooleanConfigEntry(key);
    }   
    public Integer getSafariPort() {
    	String key = "atf/desired-capabilities/safari/port";
    	return getIntEntry(key);
    }
    public String getSafariRCMode() {
    	String key = "atf/desired-capabilities/safari/rc-mode";
    	return getStringEntry(key);
    } 
    public Boolean getSafariRCHonorSystemProxy() {
    	String key = "atf/desired-capabilities/safari/rc-honor-system-proxy";
    	return getBooleanConfigEntry(key);
    }    
    
    // PHANTOMJS
    public String getPhantomJSExecutablePath() {
    	String key = "atf/desired-capabilities/phantomjs/executable-path-property";
    	return getStringEntry(key);
    }
    public String getPhantomJSPageSettingsPrefix() {
    	String key = "atf/desired-capabilities/phantomjs/page-settings-prefix";
    	return getStringEntry(key);
    }
    public String getPhantomJSPAgeCustomHeadersPrefix() {
    	String key = "atf/desired-capabilities/phantomjs/page-customheaders-prefix";
    	return getStringEntry(key);
    }
    public String getPhantomJSGhostdriverPath() {
    	String key = "atf/desired-capabilities/phantomjs/ghostdriver-path-property";
    	return getStringEntry(key);
    }
    public String getPhantomJSGhostdriverCLIArgs() {
    	String key = "atf/desired-capabilities/phantomjs/ghostdriver-cli-args";
    	return getStringEntry(key);
    }
    public String getPhantomJSCLIArgs() {
    	String key = "atf/desired-capabilities/phantomjs/cli-args";
    	return getStringEntry(key);
    } 
    
    
	/********************************************************************************/
	/************* REPORTING  *******************************************************/
	/********************************************************************************/
    public Boolean getWebUseJSErrorCollectorWithFirefox() {
    	String key = "atf/reporting/use-jserrorcollector-with-firefox";
    	return getBooleanConfigEntry(key);
    }      
    public String getBufferedImageSaveLocation() {
    	String key = "atf/reporting/buffered-image-save-location";
    	return getStringEntry(key);   	
    }
    public String getBufferedImageSaveFormat() {
    	String key = "atf/reporting/buffered-image-save-format";
    	return getStringEntry(key);   	
    }
	/********************************************************************************/
	/************* LOCALIZATION  ****************************************************/
	/********************************************************************************/
    public String getLocalizationLocale()
    {
    	String key = "atf/localization/locale";
    	return getStringEntry(key);   	
    }
    public String getLocalizationResourcePath()
    {
    	String key = "atf/localization/resource-path";
    	return getStringEntry(key);   	
    } 
        
	/********************************************************************************/
	/************* DATABASE *********************************************************/
	/********************************************************************************/
    public String getDatabaseDriver()
    {
    	String key = "atf/database/db-driver";
    	return getStringEntry(key);   	
    } 
    public String getDatabaseUser()
    {
    	String key = "atf/database/db-user";
    	return getStringEntry(key);   	
    }
    public String getDatabasePassword()
    {
    	String key = "atf/database/db-password";
    	return getStringEntry(key);   	
    }
    public String getDatabaseUrl()
    {
    	String key = "atf/database/db-url";
    	return getStringEntry(key);   	
    }
    
	/********************************************************************************/
	/************* MOBILE - COMMON **************************************************/
	/********************************************************************************/
	public boolean getMobileAppiumStartNodeServer() {
		String key = "atf/mobile/common/appiumStartNodeServer";
		return getBooleanConfigEntry(key);
	}
	public String getMobileAppiumNodeServerProcessName() {
		String key = "atf/mobile/common/appiumNodeServerProcessName";
		return getStringEntry(key);
	}
	public String getMobileAppiumNodeServerCommandLine() {
		String key = "atf/mobile/common/appiumNodeServerCommandLine";
		return getStringEntry(key);
	}
	public List<String> getMobileAppiumNodeServerArguments() {
		String key = "atf/mobile/common/appiumNodeServerArguments";
		return getListEntry(key);
	}
	public String getMobileGridURL() {
		String key = "atf/mobile/common/gridUrl";
		return getStringEntry(key);
	}
	public String getMobileAppiumVersion() {
		String key = "atf/mobile/common/appiumVersion";
		return getStringEntry(key);
	}
	public String getMobileAutomationName() {
		String key = "atf/mobile/common/automationName";
		return getStringEntry(key);
	}
	public String getMobilePlatformVersion() {
		String key = "atf/mobile/common/platformVersion";
		return getStringEntry(key);
	}
	public String getMobileDeviceName()
	{
		String key = "atf/mobile/common/deviceName";
    	return getStringEntry(key);
	}   
	public String getMobileDeviceType() {
		String key = "atf/mobile/common/deviceType";
		return getStringEntry(key);
	}
	public String getMobileApplication() {
		String key = "atf/mobile/common/app";
		return getStringEntry(key);
	}
	public String getMobileBrowserName() {
		String key = "atf/mobile/common/browserName";
    	return getStringEntry(key);
	}
	public Integer getMobileNewCommandTimeout() {
		String key = "atf/mobile/common/newCommandTimeout";
    	return getIntEntry(key);
	}	
	public String getMobileBrowserPlatform()
	{
    	String key = "atf/mobile/common/platformName";
    	return getStringEntry(key);		
	}
	public Boolean getMobileAutoLaunch() {
		String key = "atf/mobile/common/autoLaunch";
		return getBooleanConfigEntry(key);
	}
	public String getMobileLanguage() {
		String key = "atf/mobile/common/language";
		return getStringEntry(key);
	}
	public String getMobileLocale() {
		String key = "atf/mobile/common/locale";
		return getStringEntry(key);
	}
	public String getMobileUDID() {
		String key = "atf/mobile/common/udid";
		return getStringEntry(key);
	}
	public String getMobileOrientation() {
		String key = "atf/mobile/common/orientation";
		return getStringEntry(key);
	}
	public Boolean getMobileAutoWebView() {
		String key = "atf/mobile/common/autoWebview";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileNoResetAppState() {
		String key = "atf/mobile/common/noReset";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileFullReset() {
		String key = "atf/mobile/common/fullReset";
		return getBooleanConfigEntry(key);
	}

	/********************************************************************************/
	/************* MOBILE - ANDROID *************************************************/
	/********************************************************************************/
	public String getMobileApplicationActivity() {
		String key = "atf/mobile/android/appActivity";
		return getStringEntry(key);
	}
	public String getMobileApplicationPackage() {
		String key = "atf/mobile/android/appPackage";
		return getStringEntry(key);
	}
	public String getMobileApplicationWaitActivity() {
		String key = "atf/mobile/android/appWaitActivity";
		return getStringEntry(key);
	}
	public String getMobileApplicationWaitPackage() {
		String key = "atf/mobile/android/appWaitPackage";
		return getStringEntry(key);
	}
	public Integer getMobileDeviceReadyTimeout() {	
		String key = "atf/mobile/android/deviceReadyTimeout";
		return getIntEntry(key);
	}
	public Boolean getMobileAndroidCoverage() {
		String key = "atf/mobile/android/androidCoverage";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileEnablePerformanceLogging() {
		String key = "atf/mobile/android/enablePerformanceLogging";
		return getBooleanConfigEntry(key);
	}
	public Integer getMobileAndroidDeviceReadyTimeout() {
		String key = "atf/mobile/android/androidDeviceReadyTimeout";
		return getIntEntry(key);
	}	
	public Integer getMobileAndroidDeviceSocket() {
		String key = "atf/mobile/android/androidDeviceSocket";
		return getIntEntry(key);
	}	
	public String getMobileAVD() {
		String key = "atf/mobile/android/avd";
		return getStringEntry(key);
	}
	public Integer getMobileAVDLaunchTimeout() {
		String key = "atf/mobile/android/avdLaunchTimeout";
		return getIntEntry(key);
	}
	public Integer getMobileAVDReadyTimeout() {
		String key = "atf/mobile/android/avdReadyTimeout";
		return getIntEntry(key);
	}
	public String getMobileAVDArguments() {
		String key = "atf/mobile/android/avdArgs";
		return getStringEntry(key);
	}	
	public Boolean getMobileUseKeystore() {
		String key = "atf/mobile/android/useKeystore";
		return getBooleanConfigEntry(key);
	}
	public String getMobileKeystorePath() {
		String key = "atf/mobile/android/keystorePath";
		return getStringEntry(key);
	}
	public String getMobileKeystorePassword() {
		String key = "atf/mobile/android/keystorePassword";
		return getStringEntry(key);
	}
	public String getMobileKeyAlias() {
		String key = "atf/mobile/android/keyAlias";
		return getStringEntry(key);
	}
	public String getMobileKeyPassword() {
		String key = "atf/mobile/android/keyPassword";
		return getStringEntry(key);
	}	
	public String getMobileChromeDriverExecutable() {
		String key = "atf/mobile/android/chromedriverExecutable";
		return getStringEntry(key);
	}	
	public Integer getMobileAutoWebviewTimeout() {
		String key = "atf/mobile/android/autoWebviewTimeout";
		return getIntEntry(key);
	}
	public String getMobileIntentAction() {
		String key = "atf/mobile/android/intentAction";
		return getStringEntry(key);
	}
	public String getMobileIntentCategory() {
		String key = "atf/mobile/android/intentCategory";
		return getStringEntry(key);
	}
	public String getMobileIntentFlags() {
		String key = "atf/mobile/android/intentFlags";
		return getStringEntry(key);
	}
	public String getMobileOptionalIntentArguments() {
		String key = "atf/mobile/android/optionalIntentArguments";
		return getStringEntry(key);
	}	
	public Boolean getMobileStopApplicationOnReset() {
		String key = "atf/mobile/android/stopAppOnReset";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileEnableUnicodeInput() {
		String key = "atf/mobile/android/unicodeKeyboard";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileResetKeyboard() {
		String key = "atf/mobile/android/resetKeyboard";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileNoSigning() {
		String key = "atf/mobile/android/noSign";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileIgnoreUnimportantViews() {
		String key = "atf/mobile/android/ignoreUnimportantViews";
		return getBooleanConfigEntry(key);
	}

	
	/********************************************************************************/
	/************* MOBILE - IOS *****************************************************/
	/********************************************************************************/
	public String getMobileCalendarFormat() {
		String key = "atf/mobile/ios/calendarFormat";
		return getStringEntry(key);
	}
	public String getMobileBundleId() {
		String key = "atf/mobile/ios/bundleId";
		return getStringEntry(key);
	}
	public Integer getMobileLaunchTimeout() {	
		String key = "atf/mobile/ios/launchTimeout";
		return getIntEntry(key);
	}
	public Boolean getMobileLocationServiceEnabled() {
		String key = "atf/mobile/ios/locationServicesEnabled";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileLocationServiceAuthorized() {
		String key = "atf/mobile/ios/locationServicesAuthorized";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileAutoAcceptAlerts() {
		String key = "atf/mobile/ios/autoAcceptAlerts";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileAutoDismissAlerts() {
		String key = "atf/mobile/ios/autoDismissAlerts";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileNativeInstrumentsLib() {
		String key = "atf/mobile/ios/nativeInstrumentsLib";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileNativeWebTap() {
		String key = "atf/mobile/ios/nativeWebTap";
		return getBooleanConfigEntry(key);
	}
	public String getMobileSafariInitialURL() {
		String key = "atf/mobile/ios/safariInitialUrl";
		return getStringEntry(key);
	}
	public Boolean getMobileSafariAllowPopups() {
		String key = "atf/mobile/ios/safariAllowPopups";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileSafariIgnoreFraudWarnings() {
		String key = "atf/mobile/ios/safariIgnoreFraudWarning";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileSafariOpenLinksInBackground() {
		String key = "atf/mobile/ios/safariOpenLinksInBackground";
		return getBooleanConfigEntry(key);
	}
	public Boolean getMobileKeepKeyChains() {
		String key = "atf/mobile/ios/keepKeyChains";
		return getBooleanConfigEntry(key);
	}
	public String getMobileLocalizableStringsDirectory() {
		String key = "atf/mobile/ios/localizableStringsDir";
		return getStringEntry(key);
	}
	public String getMobileProcessArguments() {
		String key = "atf/mobile/ios/processArguments";
		return getStringEntry(key);
	}
	public Integer getMobileInterKeyDelay() {	
		String key = "atf/mobile/ios/interKeyDelay";
		return getIntEntry(key);
	}
	public Boolean getMobileShowIOSLog() {
		String key = "atf/mobile/ios/showIOSLog";
		return getBooleanConfigEntry(key);
	}
	public String getMobileSendKeyStragegy() {
		String key = "atf/mobile/ios/sendKeyStrategy";
		return getStringEntry(key);
	}
	public Integer getMobileScreentimeWaitTimeout() {	
		String key = "atf/mobile/ios/screenshotWaitTimeout";
		return getIntEntry(key);
	}
	public Boolean getMobileWaitForAppScript() {
		String key = "atf/mobile/ios/waitForAppScript";
		return getBooleanConfigEntry(key);
	}

}
