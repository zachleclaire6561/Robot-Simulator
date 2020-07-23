package yourworkspace;

public class YourRobot {

	public void robotUpdates() {
		if(Main.robot.getRobotLocation().y > 10) {
			Main.robot.setRobotSpeed(-1.0, 0);
		}
		else {
			Main.robot.setRobotSpeed(-1.0, 0);
		}
		System.out.println("Position " + Main.robot.getRobotLocation().x + ", " + Main.robot.getRobotLocation().y);
	}
}
