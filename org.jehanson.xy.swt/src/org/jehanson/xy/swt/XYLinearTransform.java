package org.jehanson.xy.swt;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.jehanson.xy.XYPoint;
import org.jehanson.xy.XYRect;

/**
 * A very simple transform from a 3-D Euclidean space to 2-D canvas pixels. The
 * canvas shows the space with the x-axis running horizontally (increasing to
 * the right) and the z-axis vertically (increasing upward).
 * 
 * @author jehanson
 * 
 */
public class XYLinearTransform implements XYTransform {

	// ================================
	// Variables
	// ================================

	private double xFactor;
	private double xOffset;
	private double yFactor;
	private double yOffset;

	// ================================
	// Creation
	// ================================

	public XYLinearTransform() {
		xFactor = 1;
		xOffset = 0;
		yFactor = 1;
		yOffset = 0;
	}

	// ================================
	// Operation
	// ================================

	@Override
	public void initialize(XYRect dataRect, Rectangle pixelRect) {

		int a0 = pixelRect.x;
		int a1 = pixelRect.x + pixelRect.width;
		int b0 = pixelRect.y;
		int b1 = pixelRect.y + pixelRect.height;

		double x0 = dataRect.getXMin();
		double x1 = dataRect.getXMax();
		double y0 = dataRect.getYMin();
		double y1 = dataRect.getYMax();

		xFactor = (x1 == x0) ? 1 : (a1 - a0) / (x1 - x0);
		xOffset = a0 - xFactor * x0;

		yFactor = (y1 == y0) ? 1 : (b1 - b0) / (y0 - y1);
		yOffset = b0 - yFactor * y1;
	}

	@Override
	public double pixelToDataX(int x) {
		return (x - xOffset) / xFactor;
	}

	@Override
	public double pixelToDataY(int y) {
		return (y - yOffset) / yFactor;
	}

	@Override
	public XYPoint pixelToData(int x, int y) {
		return new XYPoint((x - xOffset) / xFactor, (y - yOffset) / yFactor);
	}

	@Override
	public XYPoint pixelToData(Point p) {
		return new XYPoint((p.x - xOffset) / xFactor, (p.y - yOffset) / yFactor);
	}

	@Override
	public int dataToPixelX(double x) {
		return (int) (x * xFactor + xOffset);
	}

	@Override
	public int dataToPixelY(double y) {
		return (int) (y * yFactor + yOffset);
	}

	@Override
	public Point dataToPixel(double x, double y) {
		return new Point((int) (x * xFactor + xOffset),
				(int) (x * yFactor + yOffset));
	}

	@Override
	public Point dataToPixel(XYPoint p) {
		return new Point((int) (p.getX() * xFactor + xOffset), (int) (p.getY()
				* yFactor + yOffset));
	}

}
