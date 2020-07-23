package framework;

import org.dyn4j.dynamics.Body;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Force;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class Robot extends SimulationFrame{
		
	private double leftSpeed = 0; 
	private double rightSpeed = 0;
	private SimulationBody robot;
	private SimulationBody wallLeft, wallRight, wallBottom, wallTop;
	
	
	public Robot() { 
		super("Robot", Constants.SCALE);

		leftSpeed = 0;
		rightSpeed = 0;
	}

	@Override
	protected void initializeWorld() {
		this.world.setGravity(World.ZERO_GRAVITY);

		robot = createRobot(1.0, 1.0, 10.0, Color.CYAN);
		
		SimulationBody wallLeft = new SimulationBody();
		wallLeft.addFixture(Geometry.createRectangle(1, 15));
		wallLeft.translate(-6.5, 0);
		wallLeft.setMass(MassType.INFINITE);
		
		SimulationBody wallRight = new SimulationBody();
		wallRight.addFixture(Geometry.createRectangle(1, 15));
		wallRight.translate(6.5, 0);
		wallRight.setMass(MassType.INFINITE);
		
		SimulationBody wallBottom = new SimulationBody();
		wallBottom.addFixture(Geometry.createRectangle(15, 1));
		wallBottom.translate(0, 5);
		wallBottom.setMass(MassType.INFINITE);
		
		SimulationBody wallTop = new SimulationBody();
		wallTop.addFixture(Geometry.createRectangle(15, 1));
		wallTop.translate(0, -5);
		wallTop.setMass(MassType.INFINITE);

		this.world.addBody(wallLeft);
		this.world.addBody(wallRight);
		this.world.addBody(wallBottom);
		this.world.addBody(wallTop);
		this.world.addBody(robot);
	}
	
	@Override
	protected void update(Graphics2D g, double elapsedTime) {
		super.update(g, elapsedTime);
		// goal border 
		g.setColor(Color.RED);
	    g.draw(new Line2D.Double(250,200,250,300));
	    g.draw(new Line2D.Double(250,200,400,200)); 	   	
	   	
	    updateRobot(elapsedTime);
	}
	   	
   	public void updateRobot(double elapsedTime) {
	   	final double force = 1000 * elapsedTime;
	   	
    	Vector2 worldCenter = robot.getWorldCenter();
	   	Vector2 direction = new Vector2(robot.getTransform().getRotationAngle() + Math.PI * 0.5);
    	Vector2 f = new Vector2();

   		switch((int) Math.signum(leftSpeed * rightSpeed)){
   			case 1:
   				double direct = Math.signum(leftSpeed);

   				if(rightSpeed > leftSpeed) {	
   					f = direction.product(direct*force*Math.abs(rightSpeed));
   				}
   				else {
   					f = direction.product(direct*force*Math.abs(leftSpeed));
   				}
   				robot.applyTorque(direct * Math.abs(leftSpeed-rightSpeed));
   				break;
   				
   			case 0:
   				
   				break;
   				
   			case -1:
   				
   				break;
   				
   			default: 
   				System.out.println("An error has occured");
   				break;
   		}
   		robot.applyForce(f);
   	}
	
	public void setRobotSpeed(double leftSpeed, double rightSpeed) {
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;

		if(leftSpeed > 1) {
			this.leftSpeed = 1;
		}
		if(leftSpeed < -1) {
			this.leftSpeed = -1;
		}
		if(rightSpeed > 1) {
			this.rightSpeed = 1;
		}
		if(rightSpeed < -1) {
			this.rightSpeed = -1;
		}
	}
	
	public static SimulationBody createRobot(double turnRadius, double wheelFriction, double robotMass, Color color) {
		
		SimulationBody robot = new SimulationBody(color);
		robot.addFixture(Geometry.createRectangle(1.0, 1.25), 0.5, 0.5, 0.3);
		BodyFixture bfRight0 = robot.addFixture(Geometry.createRectangle(0.1, 0.25));
		BodyFixture bfRight1 = robot.addFixture(Geometry.createRectangle(0.1, 0.25));
		bfRight0.getShape().translate(-0.3, 0.3);
		bfRight1.getShape().translate(-0.3, -0.3);
		BodyFixture bfLeft0 = robot.addFixture(Geometry.createRectangle(0.1, 0.25));
		BodyFixture bfLeft1 = robot.addFixture(Geometry.createRectangle(0.1, 0.25));
		bfLeft0.getShape().translate(0.3, 0.3);
		bfLeft1.getShape().translate(0.3, -0.3);
		robot.setMass(MassType.NORMAL);
		return robot;
	}	
	
	public Vector2 getRobotLocation() {
		return robot.getWorldCenter();
	}
	
	public double getGyro() {
		double angle = (robot.getTransform().getRotationAngle() + Math.PI);
		System.out.println("Angle:" + angle);
		return angle;
	}
	
	
}
