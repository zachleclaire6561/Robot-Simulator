package framework;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class Robot extends SimulationFrame{
		
	private double forward = 0; 
	private double turn = 0;
	private SimulationBody robot;
	private SimulationBody wallLeft, wallRight, wallBottom, wallTop;
	
	
	public Robot() { 
		super("Robot", Constants.SCALE);

	}

	@Override
	protected void initializeWorld() {
		this.world.setGravity(World.ZERO_GRAVITY);

		robot = createRobot(1.0, Color.CYAN);
		
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
	   	final double forceForward = Constants.forceFactor * forward * elapsedTime;
	   	final double forceTurn = Constants.forceFactor * turn * elapsedTime;
	   	
    	Vector2 worldCenter = robot.getWorldCenter();
	   	Vector2 direction = new Vector2(robot.getTransform().getRotationAngle() + Math.PI * 0.5);
    	
    	Vector2 f1 = direction.product(-forward);
    	Vector2 f2;
    	if(forward > 0) {
    		f2 = direction.product(forceTurn * 0.15).right();
    	}
    	else {
    		f2 = direction.product(forceTurn * 0.15).left();
    	}
    	Vector2 p2 = worldCenter.sum(direction.product(0.9));
   		//applying friction
   		
   		robot.applyForce(f1);
   		robot.applyForce(f2, p2);
	    Vector2 robotVector = robot.getLinearVelocity();
	    robot.applyForce(robotVector.getNormalized().product(-1*Constants.coefFrictionMove));
	    robot.applyTorque(-1 * robot.getAngularVelocity() * Constants.coefFrictionTurn);
   	}
	
	public void setRobotSpeed(double forward, double turn) {
		if(forward > 1) {
			forward = 1;
		}
		if(forward < -1) {
			forward = -1;
		}
		if(turn > 1) {
			turn = 1;
		}
		if(turn < -1) {
			turn = -1;
		}
		this.forward = forward;
		this.turn = turn;
		
	}
	
	public static SimulationBody createRobot(double wheelFriction, Color color) {
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
		return robot.getWorldVector(new Vector2(0,0));
	}
	
	public double getGyro() {
		double angle = (robot.getTransform().getRotationAngle() + Math.PI);
		System.out.println("Angle:" + angle);
		return angle;
	}
	
	public double getUltraSonicDistance() {
		return 0;
	}
	
}
