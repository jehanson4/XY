package org.jehanson.xy.swt.decorators;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.jehanson.xy.swt.XYDraggableDrawing;

public class XYMouseCoordinates extends XYDraggableDrawing {

	private int mouseX;
	private int mouseY;
	private String text;

	public XYMouseCoordinates() {
		super();
		mouseX = 0;
		mouseY = 0;
		text = null;
	}

	@Override
	public void drawOn(GC gc) {
		super.drawOn(gc);
		if (isArmed()) {
			if (text == null)
				text = getViewer().getTransform().pixelToData(mouseX, mouseY).toString();
			gc.drawText(text, getX(), getY());
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		mouseX = e.x;
		mouseY = e.y;
		text = null;
		super.mouseMove(e);
	}

	@Override
	public void mouseEnter(MouseEvent e) {
		mouseX = e.x;
		mouseY = e.y;
		text = null;
		super.mouseEnter(e);
	}

	@Override
	public void mouseExit(MouseEvent e) {
		text = null;
		super.mouseExit(e);
	}

	@Override
	public int getHeight() {
		return 16;
	}

	@Override
	public int getWidth() {
		return 64;
	}

	@Override
	protected void setControl(Canvas control) {
		super.setControl(control);
		Rectangle ca = control.getClientArea();
		super.setPosition(ca.x, ca.y);
	}

}
