package atf.toolbox.data;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ScenarioData
 * This class holds a list of parameters (key\value pairs) for a test case scenario
 * ie. Login Test Case may consist of user\sa, pwd\password, language\english
 */
public class ScenarioData {

	private String scenarioFullName;
	private String testCaseFullName;
	private Map<String, Object> scenarioParameters;
	
	public ScenarioData() {
		this("unNamed Scenario");
	}
	public ScenarioData(String scenarioName) {
		this(scenarioName, "");
	}
	public ScenarioData(String scenarioName, String testCaseName) {
		scenarioFullName = scenarioName;
		testCaseFullName = testCaseName;
		scenarioParameters = new HashMap<String, Object>();
	}	
	/**
	 * putScenarioData
	 * Will put the key and overwrite if an existing data value was already present
	 */
	public void putScenarioData(String parameterKey, Object parameterData)
	{
		this.putScenarioData(parameterKey, parameterData, true);
	}
	/**
	 * putScenarioData
	 * @param overwriteIfKeyFound if TRUE, will overwrite an existing value
	 * if FALSE, will not overwrite an existing data value
	 * if no matching key is found, will add the new key and value
	 */
	public void putScenarioData(String parameterKey, Object parameterData, boolean overwriteIfKeyFound) {
		if (ParameterDataTypeIsSupported(parameterData.getClass())) {
			if (overwriteIfKeyFound) {
				scenarioParameters.put(parameterKey, parameterData);
			}
			else {
				if (!scenarioParameters.containsKey(parameterKey)) {
					scenarioParameters.put(parameterKey, parameterData);
				}
			}
		}
	}
	
	/**
	 * getScenarioName
	 * @return the name of the Scenario
	 */
	public String getScenarioName() {
		return scenarioFullName;
	}
	/**
	 * getTestCaseName
	 * @return the name of the TestCase this Scenario belongs to
	 */
	public String getTestCaseName() {
		return testCaseFullName;
	}
	/**
	 * setTestCaseName
	 * @param testCaseName the name of the TestCase this Scenario belongs to
	 */
	public void setTestCaseName(String testCaseName) {
		testCaseFullName = testCaseName;
	}
	
	public int getIntParameterData(String parameterKey) {
		return Integer.parseInt(scenarioParameters.get(parameterKey).toString());
	}
	public char getCharParameterData(String parameterKey) {
		return ((Character)scenarioParameters.get(parameterKey)).charValue();
	}
	public boolean getBooleanParameterData(String parameterKey) {
		return ((Boolean)scenarioParameters.get(parameterKey)).booleanValue();
	}
	public byte getByteParameterData(String parameterKey) {
		return ((Byte)scenarioParameters.get(parameterKey)).byteValue();
	}
	public long getShortParameterData(String parameterKey) {
		return ((Long)scenarioParameters.get(parameterKey)).longValue();
	}
	public float getFloatParameterData(String parameterKey) {
		return ((Float)scenarioParameters.get(parameterKey)).floatValue();
	}
	public double getDoubleParameterData(String parameterKey) {
		return ((Double)scenarioParameters.get(parameterKey)).doubleValue();
	}
	public Date getDateParameterData(String parameterKey) {
		return (Date)scenarioParameters.get(parameterKey);
	}
	public String getStringParameterData(String parameterKey) {
		return scenarioParameters.get(parameterKey).toString();
	}
	
    private boolean ParameterDataTypeIsSupported(Class<?> clazz)
    {
        return SUPPORTED_TYPES.contains(clazz);
    }

    private static final Set<Class<?>> SUPPORTED_TYPES = getSupportedDataTypes();
    private static Set<Class<?>> getSupportedDataTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(String.class);
        ret.add(Date.class);
        return ret;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Scenario [name=").append(scenarioFullName).append("]");
        return builder.toString();
    }
    
    public String getParameters()
    {
    	String[] list = scenarioParameters.values().toArray(new String[scenarioParameters.size()]);
    	return Arrays.toString(list);
    }
}
