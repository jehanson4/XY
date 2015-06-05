package org.jehanson.xy;

/**
 * 
 * @author jehanson
 */
public class XYRect {

	private double xMin;
	private double yMin;
	private double xMax;
	private double yMax;

	public XYRect() {
		this(0, 0, 0, 0);
	}

	public XYRect(double x0, double y0) {
		this(x0, y0, x0, y0);
	}
	
	public XYRect(XYPoint p) {
		this(p, p);
	}

	public XYRect(XYPoint p1, XYPoint p2) {
		this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public XYRect(double x0, double y0, double x1, double y1) {
		if (x0 <= x1) {
			xMin = x0;
			xMax = x1;
		} else if (x0 > x1) {
			xMin = x1;
			xMax = x0;
		}

		if (y0 <= y1) {
			yMin = y0;
			yMax = y1;
		} else if (y0 > y1) {
			yMin = y1;
			yMax = y0;
		}
	}

	public XYRect(XYRect r) {
		this.xMin = r.xMin;
		this.xMax = r.xMax;
		this.yMin = r.yMin;
		this.yMax = r.yMax;
	}

	public double getXMin() {
		return this.xMin;
	}

	public double getYMin() {
		return this.yMin;
	}

	public double getXMax() {
		return this.xMax;
	}

	public double getYMax() {
		return this.yMax;
	}

	public double getWidth() {
		return xMax - xMin;
	}

	public double getHeight() {
		return yMax - yMin;
	}

	public void copyFrom(XYRect r) {
		this.xMin = r.xMin;
		this.xMax = r.xMax;
		this.yMin = r.yMin;
		this.yMax = r.yMax;
	}

	public void cover(XYPoint p) {
		double x = p.getX();
		if (x < xMin)
			xMin = x;
		else if (x > xMax)
			xMax = x;

		double y = p.getY();
		if (y < yMin)
			yMin = y;
		else if (y > yMax)
			yMax = y;
	}

	public void cover(XYRect r) {
		if (r.getXMin() < xMin)
			xMin = r.getXMin();
		if (r.getXMax() > xMax)
			xMax = r.getXMax();
		if (r.getYMin() < yMin)
			yMin = r.getYMin();
		if (r.getYMax() > yMax)
			yMax = r.getYMax();
	}

	/**
	 * @return The point (xMin, yMin)
	 */
	public XYPoint getLocation() {
		return new XYPoint(xMin, yMin);
	}

//	/**
//	 * @param p
//	 *            The new point (xMin, yMin)
//	 */
//	public void setLocation(XYPoint p) {
//		double w = getWidth();
//		double h = getHeight();
//		xMin = p.getX();
//		xMax = xMin + w;
//		yMin = p.getY();
//		yMax = yMin + h;
//	}

	@Override
	public String toString() {
		return "[" + XYPoint.makeString(xMin, yMin) + ", "
				+ XYPoint.makeString(xMax, yMax) + "]";
	}

}
