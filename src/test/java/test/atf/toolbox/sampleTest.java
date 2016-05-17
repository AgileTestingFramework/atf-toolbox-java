package test.atf.toolbox;
import atf.toolbox.ATFHandler;
import org.testng.annotations.Test;

/**
 * Created by kvarona on 5/17/2016.
 */
public class sampleTest {


    @Test
    public void someTest() {
        ATFHandler.getInstance().getWebAutomation().getWebDriver().navigate().to("https://wiki.saucelabs.com/display/DOCS/Using+Sauce+Labs+with+Continuous+Integration+Platforms");
        ATFHandler.getInstance().getWebAutomation().getWebDriver().close();
    }
}
