package org.jehanson.xy.swt.decorators;

import org.eclipse.swt.graphics.GC;
import org.jehanson.xy.swt.XYDrawing;
import org.jehanson.xy.swt.XYViewer;

public class DrawingAreaBorder implements XYDrawing {

	private XYViewer viewer;
	
	
	public DrawingAreaBorder() {
		super();
		viewer = null;
	}

	@Override
	public void drawOn(GC gc) {
		gc.drawRectangle(viewer.getDrawingArea());
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
