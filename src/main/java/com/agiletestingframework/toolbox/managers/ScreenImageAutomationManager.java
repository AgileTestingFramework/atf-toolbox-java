package atf.toolbox.managers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenImageAutomationManager
{

	private static Logger log = LoggerFactory.getLogger(ScreenImageAutomationManager.class);

	private ScreenRegion fullScreen;

	public ScreenRegion getFullScreen()
	{
		return fullScreen;
	}

	public ScreenImageAutomationManager()
	{
		log.info("Initializing the ScreenImageAutomationManager.");
		fullScreen = new DesktopScreenRegion();
	}

	/**
	 * saveBufferedImage Buffered image will be saved to the configured buffered
	 * image save location + fileName
	 *
	 * @param fileName
	 *            file name to save image as
	 * @param imageToSave
	 *            BufferedImage to save to file
	 */
	public void saveBufferedImage(String fileName, BufferedImage imageToSave)
	{
		String filePath = ConfigurationManager.getInstance().getBufferedImageSaveLocation() + fileName;
		File imgFile = new File(filePath);

		if (!imgFile.exists())
		{
			imgFile.mkdirs();
		}

		try
		{
			ImageIO.write(imageToSave, ConfigurationManager.getInstance().getBufferedImageSaveFormat(), imgFile);
		}
		catch (IOException e)
		{
			log.error("Unable to save buffered image to filename: " + fileName, e);
		}
	}

	/**
	 * isImageDisplayed
	 *
	 * @param screenRegionToSearch
	 *            the screen region to attempt to locate the image pattern
	 * @param imagePattern
	 *            image pattern to match and wait (Will look in resources)
	 * @return TRUE if image is found, FALSE if image is not found
	 */
	public boolean isImageDisplayed(ScreenRegion screenRegionToSearch, String imagePattern)
	{
		return isImageDisplayed(screenRegionToSearch, imagePattern, 0);
	}

	/**
	 * isImageDisplayed
	 *
	 * @param screenRegionToSearch
	 *            the screen region to attempt to locate the image pattern
	 * @param imagePattern
	 *            image pattern to match (Will look in resources)
	 * @param implicitWaitTime
	 *            wait time for image to match in milliseconds
	 * @return TRUE if image if found, FALSE if image is not found within the
	 *         time allocated
	 */
	public boolean isImageDisplayed(ScreenRegion screenRegionToSearch, String imagePattern, int implicitWaitTime)
	{
		ClassLoader classLoader = getClass().getClassLoader();
		File imagePatternFile = new File(classLoader.getResource(imagePattern).getFile());

		return isImageDisplayed(screenRegionToSearch, imagePatternFile, implicitWaitTime);
	}

	/**
	 * isImageDisplayed
	 *
	 * @param screenRegionToSearch
	 *            screenRegionToSearch the screen region to attempt to locate
	 *            the image pattern
	 * @param imagePatternFile
	 *            image pattern file to use to match
	 * @return TRUE if image if found, FALSE if image is not found within the
	 *         time allocated
	 */
	public boolean isImageDisplayed(ScreenRegion screenRegionToSearch, File imagePatternFile)
	{
		return isImageDisplayed(screenRegionToSearch, imagePatternFile, 0);
	}

	/**
	 * isImageDisplayed
	 *
	 * @param screenRegionToSearch
	 *            screenRegionToSearch the screen region to attempt to locate
	 *            the image pattern
	 * @param imagePatternFile
	 *            image pattern file to use to match
	 * @param implicitWaitTime
	 *            wait time for image to match in milliseconds
	 * @return TRUE if image if found, FALSE if image is not found within the
	 *         time allocated
	 */
	public boolean isImageDisplayed(ScreenRegion screenRegionToSearch, File imagePatternFile, int implicitWaitTime)
	{
		Target imageTarget = new ImageTarget(imagePatternFile);

		ScreenRegion foundRegion = screenRegionToSearch.wait(imageTarget, implicitWaitTime);

		if (foundRegion != null)
			return true;
		else return false;
	}

	public void teardown()
	{

	}
}
