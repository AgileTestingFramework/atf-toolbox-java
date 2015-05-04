package test.atf.toolbox;

import static org.fest.assertions.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import atf.toolbox.ATFHandler;

import com.truemesh.squiggle.Order;
import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.criteria.MatchCriteria;

public class ATFHandlerTest {

	/*@Test
	public void SelectDataTest() throws SQLException
	{
		Table tblCandy = new Table("CANDY");
		SelectQuery selectStmnt = new SelectQuery();
		selectStmnt.addToSelection(tblCandy.getWildcard());
		selectStmnt.addCriteria(new MatchCriteria(tblCandy, "CURRENT_QUANTITY", MatchCriteria.GREATER, 700) );
		selectStmnt.addOrder(tblCandy, "CANDY_NAME", Order.ASCENDING);
		
		List<Map<String, Object>> results = ATFHandler.getInstance().getDatabaseAutomation().selectData(selectStmnt);
		
		assertThat(results.size()).isEqualTo(3);
		
		// Assert the first row result is Mars only
		for (Map<String, Object> row : results) {
			assertThat(row).contains(entry("CANDY_NAME", "Mars"));
			
			// Loop on specifc column within the row
		    for (Map.Entry<String, Object> column : row.entrySet()) {
		    	// Assert something here
		    }
		    
		    // look at a specific column within the row
		    assertThat(Double.parseDouble(row.get("CANDY_PRICE").toString())).isEqualTo(.65);
		    break;
	    }
	}*/
	
	/*
	 * private static String testDataFilename = "sample-xml-test-data.xml";
	 * 
	 * @DataProvider(name = "AddTwoNumbers") public static Object[][]
	 * AdditionData() { String id = "Test Case: Add 2 Numbers"; TestDataProvider
	 * tdp = new TestDataProvider();
	 * 
	 * return tdp.Initialize(new XMLDataDriver(testDataFilename, id)); }
	 * 
	 * @Test(dataProvider = "AddTwoNumbers") public void
	 * AddTwoNumbers(ScenarioData scenario) { int num1 =
	 * scenario.getIntParameterData("firstNumber"); int num2 =
	 * scenario.getIntParameterData("secondNumber"); int expectedResult =
	 * scenario.getIntParameterData("expectedSum");
	 * 
	 * assertThat((num1+num2)).isEqualTo(expectedResult); }
	 */

/*	@Test
	public void DBTest() {
		List<String> tables = ATFHandler.getInstance().getDatabaseAutomation().getTables();
		assertThat(tables).hasSize(4)
			.containsOnly("CANDY", "CANDY_ORDER", "CANDY_ORDER_LINE", "CONTACT")
			.doesNotContain("Z_CANDY");
		
		Map<String, Integer> columnAndTypes = ATFHandler.getInstance().getDatabaseAutomation().getColumnsAndTypes("CANDY");
		assertThat(columnAndTypes).hasSize(7)
			.contains(entry("ID", Types.BIGINT));
	}*/

	/*
	 * @Test
	 * 
	 * @Ignore public void ATFHandlerIsSingleton() { ATFHandler handler1 =
	 * ATFHandler.getInstance(); ATFHandler handler2 = ATFHandler.getInstance();
	 * 
	 * assertThat(handler1).isSameAs(handler2); }
	 */

	/*
	 * @Test
	 * 
	 * @Ignore public void WebAutomationIsSingleton() { WebAutomationManager
	 * webAutomation1 = ATFHandler.getInstance() .getWebAutomation();
	 * WebAutomationManager webAutomation2 = ATFHandler.getInstance()
	 * .getWebAutomation();
	 * 
	 * assertThat(webAutomation1).isSameAs(webAutomation2); }
	 */

	/*
	 * @Test public void ShouldAppiumServerStart() {
	 * ATFHandler.getInstance().getMobileAutomation();
	 * ATFHandler.getInstance().teardown(); }
	 */

	/*
	 * @Test public void runMobileTest() { String tooLongName =
	 * "AbcdefgHijklmnoPqrstuvWxyz"; String expectedFirstName =
	 * "AbcdefgHijklmnoPqrstuvWxy";
	 * 
	 * ATFHandler.getInstance().getMobileAutomation();
	 * 
	 * AppiumDriver appiumDriver =
	 * ATFHandler.getInstance().getMobileAutomation().getMobileDriver();
	 * 
	 * appiumDriver.navigate().to(
	 * "http://store.agiletestingframework.com/candystore/contact");
	 * 
	 * WebElement element = appiumDriver.findElement(By.id("txtFirstName"));
	 * element.sendKeys(tooLongName); element =
	 * appiumDriver.findElement(By.id("txtFirstName"));
	 * 
	 * String actualFirstName = element.getAttribute("value");
	 * 
	 * assertThat(actualFirstName.length()).isEqualTo(25);
	 * assertThat(actualFirstName).isEqualTo(expectedFirstName);
	 * 
	 * ATFHandler.getInstance().teardown(); }
	 */
}
