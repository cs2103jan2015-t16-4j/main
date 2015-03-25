package common;

public class Settings {
	private static String filepath;

	public static String getFilePath() {
		return filepath;
	}

	public static void setFilePath(String userText) {
		filepath = userText;
	}
}
