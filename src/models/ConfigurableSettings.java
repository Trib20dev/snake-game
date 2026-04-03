package models;

//import exceptions.StupidSpeedConfException;

public class ConfigurableSettings {
	private int easySpeed;
	private int mediumSpeed;
	private int hardSpeed;
	
	public ConfigurableSettings() {
		defaultSettings();
	}
//	/**
//	 * El objetivo es por aca hacer algo que sea adaptable a leer por fichero despues
//	 * @param speeds
//	 * @throws StupidSpeedConfException
//	 */
//	private ConfigurableSettings(int[] speeds) throws StupidSpeedConfException{
//		for(int speed: speeds )
//			if(speed < 1 || speed > 500)
//				throw new StupidSpeedConfException();
//		easySpeed = speeds[0];
//		mediumSpeed = speeds[1];
//		hardSpeed = speeds[2];
//	}
	
	
	
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
