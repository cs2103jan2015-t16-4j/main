package common;

public class Settings {
	private static String filepath;
	
	public static void setFilePath(String userText) {
		filepath = userText;
	}
	public static String getFilePath() {
		return filepath;
	}
}
