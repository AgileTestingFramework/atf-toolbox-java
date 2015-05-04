package atf.toolbox.util;

public class TestConstant {
	// Delay and Wait Constants
    public static final int WAIT_SECONDS = 10;
    public static final int WAIT_SECONDS_TINY = WAIT_SECONDS / 5;
    public static final int WAIT_SECONDS_SHORT = WAIT_SECONDS / 2;
    public static final int WAIT_SECONDS_LONG = WAIT_SECONDS * 3;

    public static final int SMALL_DELAY = 1000;
    public static final int MEDIUM_DELAY = SMALL_DELAY * 2;
    public static final int LARGE_DELAY = MEDIUM_DELAY * 2;
    public static final int TINY_DELAY = SMALL_DELAY / 2;

    // Date and Time Constants
    public static final String STD_BAD_DATE = "111";
    public static final String STD_YMD_DATE = "yyyyMMdd";
    public static final String GENERATED_DATE = "yyyyMMddkkmmssSSS";

    // Assert Constants
    public static final String TEST_PASS = "Pass";
    public static final String TEST_FAIL = "Fail";

    // Page Constants
    public static final String DOUBLE_QUOTE = "\"";
    public static final int FIRST_SELECT_ENTRY = 1;
    public static final int FIRST_LIST_ROW = 1;
    
    // Default Data Constants
    public static final String DEFAULT_FIRST_NAME = "First";
    public static final String DEFAULT_LAST_NAME = "Last";
    public static final String DEFAULT_ALTERNATE_TEXT = "ALTERNATE_TEXT";
    
    // Misc Constants
    public static final String PROP_CONFIG = "config";
    
    // Other
    public static final String NONE = "NONE";

}
