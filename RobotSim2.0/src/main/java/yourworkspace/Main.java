package yourworkspace;

import framework.Robot;

public class Main {

	// DO NOT MESS WITH THIS CLASS UNLESS YOU KNOW WHAT YOU ARE DOING
	
	public static Robot robot;
	private static YourRobot yourRobo;
	
	
	public static void main(String[] args) {
		robot = new Robot();
		YourRobot yourRobo = new YourRobot();

		robot.run();
		while(true) {
			yourRobo.robotUpdates();
		
		}
	}
	/*
	class yourRobotThread extends Thread{
		public void run() {
			while(true ) {
				
			}
		}
	}
	*/
}
