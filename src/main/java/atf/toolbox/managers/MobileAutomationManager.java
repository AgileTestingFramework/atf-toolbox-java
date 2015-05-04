package atf.toolbox.managers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileAutomationManager {

	private static Logger log = LoggerFactory.getLogger(MobileAutomationManager.class);

	private AppiumDriver mobileDriver;

	public AppiumDriver getMobileDriver() {
		return mobileDriver;
	}

	public MobileAutomationManager() {
		log.info("Initializing the MobileAutomationManager.");
		if (ConfigurationManager.getInstance().getMobileAppiumStartNodeServer()) {
			  startAppiumServer(); 
		  }
		  
		  mobileDriver = startMobileDriver();
	}

	private AppiumDriver startMobileDriver() {
		AppiumDriver localwebDriver = null;

		try {
			DesiredCapabilities caps;
			
			URL gridURL = new URL(ConfigurationManager.getInstance().getMobileGridURL());
			
			if (ConfigurationManager.getInstance().getMobileBrowserPlatform().toLowerCase().contains("ios")) {
				// IOS
				if (ConfigurationManager.getInstance().getMobileDeviceType().toLowerCase().contains("iphone"))
					caps = DesiredCapabilities.iphone();
				else caps = DesiredCapabilities.ipad();
				
				setCommonCapabilities(caps);
				setIOSCapabilities(caps);
				
				localwebDriver = new IOSDriver(gridURL, caps);
			}
			else {
				// ANDROID
				caps = DesiredCapabilities.android();
	
				setCommonCapabilities(caps);
				setAndroidCapabilities(caps);
				
				localwebDriver = new AndroidDriver(gridURL, caps);
			}
		} catch (MalformedURLException e) {
			log.error("Unable to start the appium driver.", e);
		}

		return localwebDriver;
	}

	private void setCommonCapabilities(DesiredCapabilities caps) {
		// COMMON CAPABILITIES
		caps.setCapability("appiumVersion", ConfigurationManager.getInstance().getMobileAppiumVersion());
		caps.setCapability("automationName", ConfigurationManager.getInstance().getMobileAutomationName());
		caps.setCapability("platformName", ConfigurationManager.getInstance().getMobileBrowserPlatform());
		caps.setCapability("platformVersion", ConfigurationManager.getInstance().getMobilePlatformVersion());
		caps.setCapability("deviceName", ConfigurationManager.getInstance().getMobileDeviceName());
		caps.setCapability("deviceType", ConfigurationManager.getInstance().getMobileDeviceType());
		caps.setCapability("app", ConfigurationManager.getInstance().getMobileApplication());
		caps.setCapability("browserName", ConfigurationManager.getInstance().getMobileBrowserName());
		caps.setCapability("newCommandTimeout", ConfigurationManager.getInstance().getMobileNewCommandTimeout());
		caps.setCapability("autoLaunch", ConfigurationManager.getInstance().getMobileAutoLaunch());
		caps.setCapability("language", ConfigurationManager.getInstance().getMobileLanguage());
		caps.setCapability("locale", ConfigurationManager.getInstance().getMobileLocale());
		caps.setCapability("udid", ConfigurationManager.getInstance().getMobileUniqueID());
		caps.setCapability("orientation", ConfigurationManager.getInstance().getMobileOrientation());
		caps.setCapability("autoWebview", ConfigurationManager.getInstance().getMobileAutoWebView());
		caps.setCapability("noReset", ConfigurationManager.getInstance().getMobileNoResetAppState());
		caps.setCapability("fullReset", ConfigurationManager.getInstance().getMobileFullReset());
	}

	private void setAndroidCapabilities(DesiredCapabilities caps){		
		caps.setCapability("appActivity", ConfigurationManager.getInstance().getMobileApplicationActivity());
		caps.setCapability("appPackage", ConfigurationManager.getInstance().getMobileApplicationPackage());
		caps.setCapability("appWaitActivity", ConfigurationManager.getInstance().getMobileApplicationWaitActivity());
		caps.setCapability("appWaitPackage", ConfigurationManager.getInstance().getMobileApplicationWaitPackage());
		caps.setCapability("deviceReadyTimeout", ConfigurationManager.getInstance().getMobileDeviceReadyTimeout());
		caps.setCapability("androidCoverage", ConfigurationManager.getInstance().getMobileAndroidCoverage());
		caps.setCapability("enablePerformanceLogging", ConfigurationManager.getInstance().getMobileEnablePerformanceLogging());
		caps.setCapability("androidDeviceReadyTimeout", ConfigurationManager.getInstance().getMobileAndroidDeviceReadyTimeout());
		caps.setCapability("androidDeviceSocket", ConfigurationManager.getInstance().getMobileAndroidDeviceSocket());
		caps.setCapability("avd", ConfigurationManager.getInstance().getMobileAVD());
		caps.setCapability("avdLaunchTimeout", ConfigurationManager.getInstance().getMobileAVDLaunchTimeout());
		caps.setCapability("avdReadyTimeout", ConfigurationManager.getInstance().getMobileAVDReadyTimeout());
		caps.setCapability("avdArgs", ConfigurationManager.getInstance().getMobileAVDArguments());
		caps.setCapability("useKeystore", ConfigurationManager.getInstance().getMobileUseKeystore());
		caps.setCapability("keystorePath", ConfigurationManager.getInstance().getMobileKeystorePath());
		caps.setCapability("keystorePassword", ConfigurationManager.getInstance().getMobileKeystorePassword());
		caps.setCapability("keyAlias", ConfigurationManager.getInstance().getMobileKeyAlias());
		caps.setCapability("keyPassword", ConfigurationManager.getInstance().getMobileKeyPassword());
		caps.setCapability("chromedriverExecutable", ConfigurationManager.getInstance().getMobileChromeDriverExecutable());
		caps.setCapability("autoWebviewTimeout", ConfigurationManager.getInstance().getMobileAutoWebviewTimeout());
		caps.setCapability("intentAction", ConfigurationManager.getInstance().getMobileIntentAction());
		caps.setCapability("intentCategory", ConfigurationManager.getInstance().getMobileIntentCategory());
		caps.setCapability("intentFlags", ConfigurationManager.getInstance().getMobileIntentFlags());
		caps.setCapability("optionalIntentArguments", ConfigurationManager.getInstance().getMobileOptionalIntentArguments());
		caps.setCapability("stopAppOnReset", ConfigurationManager.getInstance().getMobileStopApplicationOnReset());
		caps.setCapability("unicodeKeyboard", ConfigurationManager.getInstance().getMobileEnableUnicodeInput());
		caps.setCapability("resetKeyboard", ConfigurationManager.getInstance().getMobileResetKeyboard());
		caps.setCapability("noSign", ConfigurationManager.getInstance().getMobileNoSigning());
		caps.setCapability("ignoreUnimportantViews", ConfigurationManager.getInstance().getMobileIgnoreUnimportantViews());	
	}
	
	private void setIOSCapabilities(DesiredCapabilities caps) {
		caps.setCapability("calendarFormat", ConfigurationManager.getInstance().getMobileCalendarFormat());
		caps.setCapability("bundleId", ConfigurationManager.getInstance().getMobileBundleId());
		caps.setCapability("launchTimeout", ConfigurationManager.getInstance().getMobileLaunchTimeout());
		caps.setCapability("locationServicesEnabled", ConfigurationManager.getInstance().getMobileLocationServiceEnabled());
		caps.setCapability("locationServicesAuthorized", ConfigurationManager.getInstance().getMobileLocationServiceAuthorized());
		caps.setCapability("autoAcceptAlerts", ConfigurationManager.getInstance().getMobileAutoAcceptAlerts());
		caps.setCapability("autoDismissAlerts", ConfigurationManager.getInstance().getMobileAutoDismissAlerts());
		caps.setCapability("nativeInstrumentsLib", ConfigurationManager.getInstance().getMobileNativeInstrumentsLib());
		caps.setCapability("nativeWebTap", ConfigurationManager.getInstance().getMobileNativeWebTap());
		caps.setCapability("safariInitialUrl", ConfigurationManager.getInstance().getMobileSafariInitialURL());
		caps.setCapability("safariAllowPopups", ConfigurationManager.getInstance().getMobileSafariAllowPopups());
		caps.setCapability("safariIgnoreFraudWarning", ConfigurationManager.getInstance().getMobileSafariIgnoreFraudWarnings());
		caps.setCapability("safariOpenLinksInBackground", ConfigurationManager.getInstance().getMobileSafariOpenLinksInBackground());
		caps.setCapability("keepKeyChains", ConfigurationManager.getInstance().getMobileKeepKeyChains());
		caps.setCapability("localizableStringsDir", ConfigurationManager.getInstance().getMobileLocalizableStringsDirectory());
		caps.setCapability("processArguments", ConfigurationManager.getInstance().getMobileProcessArguments());
		caps.setCapability("interKeyDelay", ConfigurationManager.getInstance().getMobileInterKeyDelay());
		caps.setCapability("showIOSLog", ConfigurationManager.getInstance().getMobileShowIOSLog());
		caps.setCapability("sendKeyStrategy", ConfigurationManager.getInstance().getMobileSendKeyStragegy());
		caps.setCapability("screenshotWaitTimeout", ConfigurationManager.getInstance().getMobileScreentimeWaitTimeout());
		caps.setCapability("waitForAppScript", ConfigurationManager.getInstance().getMobileWaitForAppScript());		
	}
	
	private void startAppiumServer() {
		try {
			// Kill any node servers that may be running
			stopAppiumServer();
			
			log.info("START APPIUM");

			CommandLine command = new CommandLine(ConfigurationManager.getInstance().getMobileAppiumNodeServerCommandLine());
			
			for (String argument : ConfigurationManager.getInstance().getMobileAppiumNodeServerArguments())
			{
				command.addArgument(argument);
			}


			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);

			executor.execute(command, resultHandler);
			Thread.sleep(10000);

			log.info("APPIUM server started successfully.");

		} catch (IOException e) {
			log.error("Appium Server Failed to start.", e);
		} catch (InterruptedException ie) {
			log.error("Appium Server wait failed.", ie);
		}
	}

	private void stopAppiumServer() {
		log.info("Stopping appium server...");
		
		try {
			if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX
					|| SystemUtils.IS_OS_MAC_OSX) {
				String[] command = { "/usr/bin/killall", "-KILL", ConfigurationManager.getInstance().getMobileAppiumNodeServerProcessName() };
				Runtime.getRuntime().exec(command);

			} else {
				CommandLine command = new CommandLine("cmd");
				command.addArgument("/c");
				command.addArgument("Taskkill /F /IM "+ ConfigurationManager.getInstance().getMobileAppiumNodeServerProcessName() +" /T");

				DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
				DefaultExecutor executor = new DefaultExecutor();
				executor.setExitValue(1);
				executor.execute(command, resultHandler);		
				Thread.currentThread();
				Thread.sleep(5000);
			}

		} catch (Exception ex) {
			log.error("Error stopping appium server.", ex);
		}
		
		log.info("Appium server stopped.");
	}

	public void teardown() {
		mobileDriver.quit();
		if (ConfigurationManager.getInstance().getMobileAppiumStartNodeServer()) { stopAppiumServer(); }
	}

}
