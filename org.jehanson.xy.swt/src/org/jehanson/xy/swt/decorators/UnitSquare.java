package org.jehanson.xy.swt.decorators;

import org.eclipse.swt.graphics.GC;
import org.jehanson.xy.swt.XYDrawing;
import org.jehanson.xy.swt.XYTransform;
import org.jehanson.xy.swt.XYViewer;

public class UnitSquare implements XYDrawing {

	@Override
	public void drawOn(GC gc, XYViewer viewer) {
		XYTransform xform = viewer.getTransform();
		int x0 = xform.dataToPixelX(0);
		int y0 = xform.dataToPixelY(0);
		int x1 = xform.dataToPixelX(1);
		int y1 = xform.dataToPixelY(1);
		gc.drawRectangle(x0,  y0, (x1-x0), (y1-y0));
		gc.drawRectangle(x0,  y0, (x1-x0)/4, (y1-y0)/4);
	}

}
