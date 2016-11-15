package com.agiletestingframework.toolbox.managers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileAutomationManager
{

	private static Logger log = LoggerFactory.getLogger(MobileAutomationManager.class);

	/**
	 * getWebDriver
	 *
	 * @return the local instance of the webDriver. will create new instance if
	 *         one doesn't already exist to return.
	 */
	public AppiumDriver getMobileDriver() {
		if (WebDriverManager.getDriver() == null) {
			WebDriverManager.setWebDriver(startMobileDriver());
		}

		return ((AppiumDriver)WebDriverManager.getDriver());
	}

	public MobileAutomationManager()
	{
		log.info("Initializing the MobileAutomationManager.");
		if (ConfigurationManager.getInstance().getMobileAppiumStartNodeServer())
		{
			startAppiumServer();
		}
	}

	private AppiumDriver startMobileDriver()
	{
		AppiumDriver localwebDriver = null;

		try
		{
			DesiredCapabilities caps;

			URL gridURL = new URL(ConfigurationManager.getInstance().getMobileGridURL());

			if (ConfigurationManager.getInstance().getMobileBrowserPlatform().toLowerCase().contains("ios"))
			{
				// IOS
				if (ConfigurationManager.getInstance().getMobileDeviceType().toLowerCase().contains("iphone"))
					caps = DesiredCapabilities.iphone();
				else caps = DesiredCapabilities.ipad();

				setCommonCapabilities(caps);
				setIOSCapabilities(caps);

				localwebDriver = new IOSDriver(gridURL, caps);
			}
			else
			{
				// ANDROID
				caps = DesiredCapabilities.android();

				setCommonCapabilities(caps);
				setAndroidCapabilities(caps);

				localwebDriver = new AndroidDriver(gridURL, caps);
			}
		}
		catch (MalformedURLException e)
		{
			log.error("Unable to start the appium driver.", e);
		}

		return localwebDriver;
	}

	private void setCommonCapabilities(DesiredCapabilities caps)
	{
		// COMMON CAPABILITIES
		if (ConfigurationManager.getInstance().getMobileAppiumVersion().length() != 0) caps.setCapability("appiumVersion", ConfigurationManager.getInstance().getMobileAppiumVersion());
		if (ConfigurationManager.getInstance().getMobileAutomationName().length() != 0) caps.setCapability("automationName", ConfigurationManager.getInstance().getMobileAutomationName());
		if (ConfigurationManager.getInstance().getMobileBrowserPlatform().length() != 0) caps.setCapability("platformName", ConfigurationManager.getInstance().getMobileBrowserPlatform());
		if (ConfigurationManager.getInstance().getMobilePlatformVersion().length() != 0) caps.setCapability("platformVersion", ConfigurationManager.getInstance().getMobilePlatformVersion());
		if (ConfigurationManager.getInstance().getMobileDeviceName().length() != 0) caps.setCapability("deviceName", ConfigurationManager.getInstance().getMobileDeviceName());
		if (ConfigurationManager.getInstance().getMobileDeviceType().length() != 0) caps.setCapability("deviceType", ConfigurationManager.getInstance().getMobileDeviceType());
		if (ConfigurationManager.getInstance().getMobileApplication().length() != 0) caps.setCapability("app", ConfigurationManager.getInstance().getMobileApplication());
		if (ConfigurationManager.getInstance().getMobileBrowserName().length() != 0) caps.setCapability("browserName", ConfigurationManager.getInstance().getMobileBrowserName());
		if (ConfigurationManager.getInstance().getMobileNewCommandTimeout() != null) caps.setCapability("newCommandTimeout", ConfigurationManager.getInstance().getMobileNewCommandTimeout());
		if (ConfigurationManager.getInstance().getMobileLanguage().length() != 0) caps.setCapability("language", ConfigurationManager.getInstance().getMobileLanguage());
		if (ConfigurationManager.getInstance().getMobileLocale().length() != 0) caps.setCapability("locale", ConfigurationManager.getInstance().getMobileLocale());
		if (ConfigurationManager.getInstance().getMobileUDID().length() != 0) caps.setCapability("udid", ConfigurationManager.getInstance().getMobileUDID());
		if (ConfigurationManager.getInstance().getMobileOrientation().length() != 0) caps.setCapability("orientation", ConfigurationManager.getInstance().getMobileOrientation());
		if (ConfigurationManager.getInstance().getMobileAutoLaunch() != null) caps.setCapability("autoLaunch", ConfigurationManager.getInstance().getMobileAutoLaunch());
		if (ConfigurationManager.getInstance().getMobileAutoWebView() != null) caps.setCapability("autoWebview", ConfigurationManager.getInstance().getMobileAutoWebView());
		if (ConfigurationManager.getInstance().getMobileNoResetAppState() != null) caps.setCapability("noReset", ConfigurationManager.getInstance().getMobileNoResetAppState());
		if (ConfigurationManager.getInstance().getMobileFullReset() != null) caps.setCapability("fullReset", ConfigurationManager.getInstance().getMobileFullReset());
	}

	private void setAndroidCapabilities(DesiredCapabilities caps)
	{
		if (ConfigurationManager.getInstance().getMobileApplicationActivity().length() != 0) caps.setCapability("appActivity", ConfigurationManager.getInstance().getMobileApplicationActivity());
		if (ConfigurationManager.getInstance().getMobileApplicationPackage().length() != 0) caps.setCapability("appPackage", ConfigurationManager.getInstance().getMobileApplicationPackage());
		if (ConfigurationManager.getInstance().getMobileApplicationWaitActivity().length() != 0) caps.setCapability("appWaitActivity", ConfigurationManager.getInstance().getMobileApplicationWaitActivity());
		if (ConfigurationManager.getInstance().getMobileApplicationWaitPackage().length() != 0) caps.setCapability("appWaitPackage", ConfigurationManager.getInstance().getMobileApplicationWaitPackage());
		if (ConfigurationManager.getInstance().getMobileDeviceReadyTimeout() != null) caps.setCapability("deviceReadyTimeout", ConfigurationManager.getInstance().getMobileDeviceReadyTimeout());
		if (ConfigurationManager.getInstance().getMobileAndroidCoverage() != null) caps.setCapability("androidCoverage", ConfigurationManager.getInstance().getMobileAndroidCoverage());
		if (ConfigurationManager.getInstance().getMobileEnablePerformanceLogging() != null) caps.setCapability("enablePerformanceLogging", ConfigurationManager.getInstance().getMobileEnablePerformanceLogging());
		if (ConfigurationManager.getInstance().getMobileAndroidDeviceReadyTimeout() != null) caps.setCapability("androidDeviceReadyTimeout", ConfigurationManager.getInstance().getMobileAndroidDeviceReadyTimeout());
		if (ConfigurationManager.getInstance().getMobileAndroidDeviceSocket() != null) caps.setCapability("androidDeviceSocket", ConfigurationManager.getInstance().getMobileAndroidDeviceSocket());
		if (ConfigurationManager.getInstance().getMobileAVD().length() != 0) caps.setCapability("avd", ConfigurationManager.getInstance().getMobileAVD());
		if (ConfigurationManager.getInstance().getMobileAVDLaunchTimeout() != null) caps.setCapability("avdLaunchTimeout", ConfigurationManager.getInstance().getMobileAVDLaunchTimeout());
		if (ConfigurationManager.getInstance().getMobileAVDReadyTimeout() != null) caps.setCapability("avdReadyTimeout", ConfigurationManager.getInstance().getMobileAVDReadyTimeout());
		if (ConfigurationManager.getInstance().getMobileAVDArguments().length() != 0) caps.setCapability("avdArgs", ConfigurationManager.getInstance().getMobileAVDArguments());

		if (ConfigurationManager.getInstance().getMobileUseKeystore() != null && ConfigurationManager.getInstance().getMobileUseKeystore())
		{
			caps.setCapability("useKeystore", ConfigurationManager.getInstance().getMobileUseKeystore());
			if (ConfigurationManager.getInstance().getMobileKeystorePath().length() != 0) caps.setCapability("keystorePath", ConfigurationManager.getInstance().getMobileKeystorePath());
			if (ConfigurationManager.getInstance().getMobileKeystorePassword().length() != 0) caps.setCapability("keystorePassword", ConfigurationManager.getInstance().getMobileKeystorePassword());
		}

		if (ConfigurationManager.getInstance().getMobileKeyAlias().length() != 0) caps.setCapability("keyAlias", ConfigurationManager.getInstance().getMobileKeyAlias());
		if (ConfigurationManager.getInstance().getMobileKeyPassword().length() != 0) caps.setCapability("keyPassword", ConfigurationManager.getInstance().getMobileKeyPassword());
		if (ConfigurationManager.getInstance().getMobileChromeDriverExecutable().length() != 0) caps.setCapability("chromedriverExecutable", ConfigurationManager.getInstance().getMobileChromeDriverExecutable());
		if (ConfigurationManager.getInstance().getMobileAutoWebviewTimeout() != null) caps.setCapability("autoWebviewTimeout", ConfigurationManager.getInstance().getMobileAutoWebviewTimeout());
		if (ConfigurationManager.getInstance().getMobileIntentAction().length() != 0) caps.setCapability("intentAction", ConfigurationManager.getInstance().getMobileIntentAction());
		if (ConfigurationManager.getInstance().getMobileIntentCategory().length() != 0) caps.setCapability("intentCategory", ConfigurationManager.getInstance().getMobileIntentCategory());
		if (ConfigurationManager.getInstance().getMobileIntentFlags().length() != 0) caps.setCapability("intentFlags", ConfigurationManager.getInstance().getMobileIntentFlags());
		if (ConfigurationManager.getInstance().getMobileOptionalIntentArguments().length() != 0) caps.setCapability("optionalIntentArguments", ConfigurationManager.getInstance().getMobileOptionalIntentArguments());
		if (ConfigurationManager.getInstance().getMobileStopApplicationOnReset() != null) caps.setCapability("stopAppOnReset", ConfigurationManager.getInstance().getMobileStopApplicationOnReset());
		if (ConfigurationManager.getInstance().getMobileEnableUnicodeInput() != null) caps.setCapability("unicodeKeyboard", ConfigurationManager.getInstance().getMobileEnableUnicodeInput());
		if (ConfigurationManager.getInstance().getMobileResetKeyboard() != null) caps.setCapability("resetKeyboard", ConfigurationManager.getInstance().getMobileResetKeyboard());
		if (ConfigurationManager.getInstance().getMobileNoSigning() != null) caps.setCapability("noSign", ConfigurationManager.getInstance().getMobileNoSigning());
		if (ConfigurationManager.getInstance().getMobileIgnoreUnimportantViews() != null) caps.setCapability("ignoreUnimportantViews", ConfigurationManager.getInstance().getMobileIgnoreUnimportantViews());
	}

	private void setIOSCapabilities(DesiredCapabilities caps)
	{
		if (ConfigurationManager.getInstance().getMobileCalendarFormat().length() != 0) caps.setCapability("calendarFormat", ConfigurationManager.getInstance().getMobileCalendarFormat());
		if (ConfigurationManager.getInstance().getMobileBundleId().length() != 0) caps.setCapability("bundleId", ConfigurationManager.getInstance().getMobileBundleId());
		if (ConfigurationManager.getInstance().getMobileLaunchTimeout() != null) caps.setCapability("launchTimeout", ConfigurationManager.getInstance().getMobileLaunchTimeout());
		if (ConfigurationManager.getInstance().getMobileLocationServiceEnabled() != null) caps.setCapability("locationServicesEnabled", ConfigurationManager.getInstance().getMobileLocationServiceEnabled());
		if (ConfigurationManager.getInstance().getMobileLocationServiceAuthorized() != null) caps.setCapability("locationServicesAuthorized", ConfigurationManager.getInstance().getMobileLocationServiceAuthorized());
		if (ConfigurationManager.getInstance().getMobileAutoAcceptAlerts() != null) caps.setCapability("autoAcceptAlerts", ConfigurationManager.getInstance().getMobileAutoAcceptAlerts());
		if (ConfigurationManager.getInstance().getMobileAutoDismissAlerts() != null) caps.setCapability("autoDismissAlerts", ConfigurationManager.getInstance().getMobileAutoDismissAlerts());
		if (ConfigurationManager.getInstance().getMobileNativeInstrumentsLib() != null) caps.setCapability("nativeInstrumentsLib", ConfigurationManager.getInstance().getMobileNativeInstrumentsLib());
		if (ConfigurationManager.getInstance().getMobileNativeWebTap() != null) caps.setCapability("nativeWebTap", ConfigurationManager.getInstance().getMobileNativeWebTap());
		if (ConfigurationManager.getInstance().getMobileSafariInitialURL().length() != 0) caps.setCapability("safariInitialUrl", ConfigurationManager.getInstance().getMobileSafariInitialURL());
		if (ConfigurationManager.getInstance().getMobileSafariAllowPopups() != null) caps.setCapability("safariAllowPopups", ConfigurationManager.getInstance().getMobileSafariAllowPopups());
		if (ConfigurationManager.getInstance().getMobileSafariIgnoreFraudWarnings() != null) caps.setCapability("safariIgnoreFraudWarning", ConfigurationManager.getInstance().getMobileSafariIgnoreFraudWarnings());
		if (ConfigurationManager.getInstance().getMobileSafariOpenLinksInBackground() != null) caps.setCapability("safariOpenLinksInBackground", ConfigurationManager.getInstance().getMobileSafariOpenLinksInBackground());
		if (ConfigurationManager.getInstance().getMobileKeepKeyChains() != null) caps.setCapability("keepKeyChains", ConfigurationManager.getInstance().getMobileKeepKeyChains());
		if (ConfigurationManager.getInstance().getMobileLocalizableStringsDirectory().length() != 0) caps.setCapability("localizableStringsDir", ConfigurationManager.getInstance().getMobileLocalizableStringsDirectory());
		if (ConfigurationManager.getInstance().getMobileProcessArguments().length() != 0) caps.setCapability("processArguments", ConfigurationManager.getInstance().getMobileProcessArguments());
		if (ConfigurationManager.getInstance().getMobileInterKeyDelay() != null) caps.setCapability("interKeyDelay", ConfigurationManager.getInstance().getMobileInterKeyDelay());
		if (ConfigurationManager.getInstance().getMobileShowIOSLog() != null) caps.setCapability("showIOSLog", ConfigurationManager.getInstance().getMobileShowIOSLog());
		if (ConfigurationManager.getInstance().getMobileSendKeyStragegy() != null) caps.setCapability("sendKeyStrategy", ConfigurationManager.getInstance().getMobileSendKeyStragegy());
		if (ConfigurationManager.getInstance().getMobileScreentimeWaitTimeout() != null) caps.setCapability("screenshotWaitTimeout", ConfigurationManager.getInstance().getMobileScreentimeWaitTimeout());
		if (ConfigurationManager.getInstance().getMobileWaitForAppScript() != null) caps.setCapability("waitForAppScript", ConfigurationManager.getInstance().getMobileWaitForAppScript());
	}

	private void startAppiumServer()
	{
		try
		{
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

		}
		catch (IOException e)
		{
			log.error("Appium Server Failed to start.", e);
		}
		catch (InterruptedException ie)
		{
			log.error("Appium Server wait failed.", ie);
		}
	}

	private void stopAppiumServer()
	{
		log.info("Stopping appium server...");

		try
		{
			if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC_OSX)
			{
				String[] command = { "/usr/bin/killall", "-KILL", ConfigurationManager.getInstance().getMobileAppiumNodeServerProcessName() };
				Runtime.getRuntime().exec(command);

			}
			else
			{
				CommandLine command = new CommandLine("cmd");
				command.addArgument("/c");
				command.addArgument("Taskkill /F /IM " + ConfigurationManager.getInstance().getMobileAppiumNodeServerProcessName() + " /T");

				DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
				DefaultExecutor executor = new DefaultExecutor();
				executor.setExitValue(1);
				executor.execute(command, resultHandler);
				Thread.currentThread();
				Thread.sleep(5000);
			}

		}
		catch (Exception ex)
		{
			log.error("Error stopping appium server.", ex);
		}

		log.info("Appium server stopped.");
	}

	public void teardown()
	{
		getMobileDriver().quit();
		if (ConfigurationManager.getInstance().getMobileAppiumStartNodeServer())
		{
			stopAppiumServer();
		}
	}

}
