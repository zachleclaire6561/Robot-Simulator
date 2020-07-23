package framework;

public class Main {

	public static void main(String[] args) {
		Robot simulator = new Robot();
		simulator.run();
		simulator.setRobotSpeed(0.5, 0.1);
	}
}
