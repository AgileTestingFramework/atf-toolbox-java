package test.atf.toolbox;

import static org.fest.assertions.api.Assertions.assertThat;

import org.apache.commons.configuration.Configuration;
import org.testng.annotations.Test;

import atf.toolbox.managers.ConfigurationManager;

public class ConfigurationManagerTest {

	  @Test
	  public void LoadConfiguration()
	  {
		  Configuration config = ConfigurationManager.getInstance().AllConfiguration;
		  
		  assertThat(config).isNotNull();
	  }
}
