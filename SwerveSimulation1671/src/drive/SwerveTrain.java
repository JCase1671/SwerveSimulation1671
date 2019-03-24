package drive;

import math.LinearAlgebraLib;
import math.Vector2D;

public class SwerveTrain {
	
	private SwerveWheel lF;
	private SwerveWheel lB;
	private SwerveWheel rF;
	private SwerveWheel rB;
	private Vector2D gyro;
	private double gyroPosX = 0;
	private double gyroPosY = 0;
	
	private final double length = 30.0;
	private final double width = 28.0;
	private final double diagonal = 41.04;
	
	
	public SwerveTrain() {
		lF = new SwerveWheel(1, 5, -0.14, 0.15);
		lB = new SwerveWheel(2, 6, -0.14, -0.15);
		rF = new SwerveWheel(3, 7, 0.14, 0.15);
		rB = new SwerveWheel(4, 8, 0.14, -0.15);
		gyro = new Vector2D(0, 0);
	}
	
	public void drive(double axisTX, double axisTY, double axisR) {
		double tX = deadZone(axisTX, 0.001);
		double tY = deadZone(axisTY, 0.001);
		double r = deadZone(axisR, 0.001);
		
		double gX = rF.getPosition().getX() - lF.getPosition().getX();
		double gY = rF.getPosition().getY() - lF.getPosition().getY();
		gyro.set(gX, gY);
		gyroPosX += tX;
		gyroPosY += tY;
		
		System.out.println((int)(lF.getVector().getAngle() * 180.0/Math.PI) + "     " + (int)(rF.getVector().getAngle() * 180.0/Math.PI) + "\n\n\n");
		System.out.println((int)(lB.getVector().getAngle() * 180.0/Math.PI) + "     " + (int)(rB.getVector().getAngle() * 180.0/Math.PI) + "\n\n\n\n\n\n\n\n\n");
		
		lF.set(tX + r * getRotation(lF, 1.0, 1.0).getX(), tY + r * getRotation(lF, 1.0, 1.0).getY());
		lB.set(tX + r * getRotation(lB, -1.0, 1.0).getX(), tY + r * getRotation(lB, -1.0, 1.0).getY());
		rF.set(tX + r * getRotation(rF, 1.0, -1.0).getX(), tY + r * getRotation(rF, 1.0, -1.0).getY());
		rB.set(tX + r * getRotation(rB, -1.0, -1.0).getX(), tY + r * getRotation(rB, -1.0, -1.0).getY());
		System.out.println(gyro.getAngle());
	}
	
	public Vector2D getRotation(SwerveWheel wheel, double signX, double signY) {
		double diagonal = 0.2052;
		double x = Math.cos(Math.acos(2.0 * signX * (wheel.getPosition().getY() - gyroPosY) / this.diagonal) + gyro.getAngle());
		double y = Math.sin(Math.asin(2.0 * signY * (wheel.getPosition().getX() - gyroPosX) / this.diagonal) + gyro.getAngle());
		return new Vector2D(x, y);
	}
	
	public double deadZone(double val, double deadZone) {
		if(Math.abs(val) > deadZone) {
			return val;
		}
		return 0.0;
	}
	
	public SwerveWheel getWheel(String wheel) {
		switch(wheel) {
		case "lF":
			return lF;
		case "lB":
			return lB;
		case "rF":
			return rF;
		case "rB":
			return rB;
		default:
			return null;
		}
	}
	
	public double getHeading() {
		return gyro.getAngle();
	}
	
	public double getX() {
		return gyro.getX();
	}
	
	public double getY() {
		return gyro.getY();
	}
	
}
