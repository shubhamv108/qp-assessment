package code.shubham.commons.utils;

public class StringUtils {

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}

	public static String rightPadSpaces(String value, int length) {
		return String.format("%1$-" + length + "s", value);
	}

	public static boolean isNotEmpty(final String input) {
		return !StringUtils.isEmpty(input);
	}

	public static boolean isEmpty(String input) {
		return input == null || "".equals(input) || "null".equals(input);
	}

}
