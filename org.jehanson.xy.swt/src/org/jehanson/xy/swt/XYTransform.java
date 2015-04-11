package org.jehanson.xy.swt;


import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.jehanson.xy.XYPoint;
import org.jehanson.xy.XYRect;

/**
 * 
 * @author jehanson
 */
public interface XYTransform {

	public double pixelToDataX(int x);
	public double pixelToDataY(int y);
	public XYPoint pixelToData(Point p);
	
	public int dataToPixelX(double x);
	public int dataToPixelY(double y);
	public Point dataToPixel(XYPoint p);
	
	public void initialize(XYRect dataBounds, Rectangle pixelBounds);
}
