package atf.toolbox.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class StringUtil {
    public static List<String> getAlphabetLowerCase() {
        int LOWER_A = 97;
        int LOWER_Z = 122;
        return getListOfChars(LOWER_A, LOWER_Z);
    }

    public static List<String> getAlphabetUpperCase() {
        int UPPER_A = 65;
        int UPPER_Z = 90;
        return getListOfChars(UPPER_A, UPPER_Z);
    }

    public static List<String> getListOfChars(int start, int end) {
        List<String> stringList = new ArrayList<String>();
        for (int i = start; i <= end; i++) {
            stringList.add(Character.toString((char) i));
        }
        return stringList;
    }

    public static String makeFileNameLegal(String fileName) {
        String answer = fileName;

        // http://stackoverflow.com/questions/1155107/is-there-a-cross-platform-java-method-to-remove-filename-special-chars
        // Explanation:
        // [a-zA-Z0-9\\._] matches a letter from a-z lower or uppercase,
        // numbers, dots and underscores
        // [^a-zA-Z0-9\\._] is the inverse. i.e. all characters which do not
        // match the first expression
        // [^a-zA-Z0-9\\._]+ is a sequence of characters which do not match the
        // first expression
        // So every sequence of characters which does not consist of characters
        // from a-z, 0-9 or . _ will be replaced.

        answer = StringUtils.trimToEmpty(answer);
        answer = answer.replaceAll("[^a-zA-Z0-9\\._]+", "_");
        if (StringUtils.isBlank(answer)) {
            answer = "make_legal" + DateUtil.timeStampAsString();
        }
        return answer;
    }

    public static String createIndexedMapKey(String valueToConvert, int index) {
        String value = StringUtils.trimToEmpty(valueToConvert);
        if (StringUtils.isBlank(value)) {
            value = StringUtils.EMPTY + index;
        } else {
            value = StringUtils.replace(value, "\n", " ");
            value = StringUtils.replace(value, " ", "-");
            value = StringUtils.lowerCase(value);
        }
        return value;
    }

    public static boolean isNotEmpty(List<?> list) {
        return list != null && list.size() > 0;
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && map.size() > 0;
    }

    public static String getMapStringValue(Map<String, String> map, String key) {
        return getMapStringValue(map, key, StringUtils.EMPTY);
    }

    public static String getMapStringValue(Map<String, String> map, String key, String defaultValue) {
        String answer = StringUtils.trimToEmpty(map.get(key));
        answer = StringUtils.isEmpty(answer) ? defaultValue : answer;
        return answer;
    }

    public static String generateRandomnumber(int firstNum, int restNum) {
        Random random = new Random(System.currentTimeMillis());
        String first = String.valueOf(random.nextInt(firstNum));
        String rest = String.valueOf(random.nextInt(restNum));
        return first + rest;
    }

    public static String generateUniqueString(String prefixString) {
        return generateUniqueString(prefixString, 0, 15);
    }

    public static String generateUniqueString(String prefixString, int start, int end) {
        return StringUtils.substring(prefixString + UUID.randomUUID().toString(), start, end);
    }



    public static String randomPhone() {
        Random random = new Random(System.currentTimeMillis());
        String firstNum = String.valueOf(random.nextInt(9));
        String restNum = String.valueOf(random.nextInt(999999999));
        return firstNum + restNum;
    }

    /**
     * <p>Generates a unique string not longer than a max length.</p>
     * @param prefixString Prepended to the generated unique string
     * @param maxLength Generated string is guaranteed not to be longer than this max length.
     * @return - unique string
     */
    public static String uniqueString(String prefixString, int maxLength) {
        return uniqueString(prefixString, 0, maxLength);
    }

    /**
     * <p>Generates a unique string.</p>
     * @param prefixString Prepended to the generated unique string
     * @return - unique string
     */
    public static String uniqueString(String prefixString) {
        return uniqueString(prefixString, 0, Integer.MAX_VALUE);
    }

    /**
     * <p>
     * Generates a unique string and appends to your prefix. Then substrings this
     * new string according to the int parameters.
     * </p>
     *
     * @param prefixString
     *            Prepended to the generated unique string
     * @param start
     *            See {@link StringUtils#substring(String, int, int)}
     * @param end
     *            See {@link StringUtils#substring(String, int, int)}
     * @return - unique string
     */
    public static String uniqueString(String prefixString, int start, int end) {
        return StringUtils.substring(prefixString + UUID.randomUUID().toString(), start, end);
    }

    /**
     * Creates a name looking something like "Tbxau Pdsaddwe".
     * @return - random name
     */
    public static String randomName() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.capitalize(randomAlphaWord(7)));
        sb.append(" ");
        sb.append(StringUtils.capitalize(randomAlphaWord(12)));
        return sb.toString();
    }

    /**
     * Creates a name looking something like "Tbxau_Pdsaddwe@example.com".
     * @return - random email
     */
    public static String randomEmail() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.capitalize(randomAlphaWord(5)));
        sb.append("_");
        sb.append(StringUtils.capitalize(randomAlphaWord(5)));
        sb.append("@example.com");
        return sb.toString();
    }

    /**
     * Creates an alpha word looking something like "Tbxau".
     * @param length - of generated string.
     * @return - random alpha word
     */
    public static String randomAlphaWord(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i += 1) {
            sb.append(randomCharLowercase());
        }
        return sb.toString();
    }

    public static char randomCharLowercase() {
        // a = 97, z = 122
        // for reference:
        // https://en.wikipedia.org/wiki/ASCII#ASCII_printable_code_chart
        return (char)randomInt(97, 122);
    }

    /**
     * Returns a random integer within (inclusive) the given range.
     * @param min inclusive
     * @param max inclusive
     * @return - random int
     */
    public static int randomInt(int min, int max) {
        return RandomUtils.nextInt((max - min) + 1) + min;
    }

}
