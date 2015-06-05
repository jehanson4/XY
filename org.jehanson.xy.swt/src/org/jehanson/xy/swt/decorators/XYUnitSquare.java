package org.jehanson.xy.swt.decorators;

import org.eclipse.swt.graphics.GC;
import org.jehanson.xy.swt.XYDrawing;
import org.jehanson.xy.swt.XYTransform;
import org.jehanson.xy.swt.XYViewer;

public class XYUnitSquare implements XYDrawing {

	private XYViewer viewer;

	public XYUnitSquare() {
		super();
		viewer = null;
	}

	@Override
	public void drawOn(GC gc) {
		XYTransform xform = viewer.getTransform();
		int x0 = xform.dataToPixelX(0);
		int y0 = xform.dataToPixelY(0);
		int x1 = xform.dataToPixelX(1);
		int y1 = xform.dataToPixelY(1);
		gc.drawRectangle(x0, y0, (x1 - x0), (y1 - y0));
		// gc.drawRectangle(x0, y0, (x1 - x0) / 4, (y1 - y0) / 4);
	}

	@Override
	public void addedToViewer(XYViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void removedFromViewer(XYViewer viewer) {
		this.viewer = null;
	}

}
