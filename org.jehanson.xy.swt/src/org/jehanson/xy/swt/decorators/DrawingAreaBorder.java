package org.jehanson.xy.swt.decorators;

import org.eclipse.swt.graphics.GC;
import org.jehanson.xy.swt.XYDrawing;
import org.jehanson.xy.swt.XYViewer;

public class DrawingAreaBorder implements XYDrawing {

	@Override
	public void drawOn(GC gc, XYViewer viewer) {
		gc.drawRectangle(viewer.getDrawingArea());
	}

}
