package models;

public class ConfigurableSettings {
	private int easySpeed;
	private int mediumSpeed;
	private int hardSpeed;
	
	private ConfigurableSettings() {
		defaultSettings();
	}
	
	private ConfigurableSettings(int easy, int medium, int hard) {
		easySpeed = easy;
		mediumSpeed = medium;
		hardSpeed = hard;
	}
	
	
	
	private void defaultSettings() {
		easySpeed = 150;
		mediumSpeed = 100;
		hardSpeed = 50;
	}

	public int getEasySpeed() {
		return easySpeed;
	}

	public int getMediumSpeed() {
		return mediumSpeed;
	}

	public int getHardSpeed() {
		return hardSpeed;
	}
	
	
	
}
