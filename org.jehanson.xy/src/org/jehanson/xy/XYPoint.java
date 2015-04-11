package org.jehanson.xy;

/**
 * 
 * @author jehanson
 */
public class XYPoint {

	public static final XYPoint ZERO = new XYPoint(0,0);
	public static final XYPoint ONE = new XYPoint(1,1);
	
	private final double x;
	private final double y;

	public XYPoint(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
