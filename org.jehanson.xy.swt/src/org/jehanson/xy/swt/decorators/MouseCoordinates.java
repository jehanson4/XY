package org.jehanson.xy.swt.decorators;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.jehanson.xy.swt.XYDrawing;
import org.jehanson.xy.swt.XYViewer;

public class MouseCoordinates implements XYDrawing, MouseMoveListener,
		MouseTrackListener {

	private XYViewer viewer;
	private Control control;
	private int mouseX;
	private int mouseY;
	private String text;
	private boolean inactive;

	public MouseCoordinates() {
		super();
		viewer = null;
		control = null;
		mouseX = 0;
		mouseY = 0;
		text = null;
		inactive = true;
	}

	@Override
	public void drawOn(GC gc) {
		if (control == null)
			setControl(viewer.getControl());
		if (text == null)
			text = (inactive) ? "" : viewer.getTransform()
					.pixelToData(mouseX, mouseY).toString();
		Rectangle r = viewer.getControl().getClientArea();
		gc.drawText(text, r.x, r.y);
	}

	protected void setControl(Control control) {
		this.control = control;
		this.control.addMouseMoveListener(this);
		this.control.addMouseTrackListener(this);
	}

	@Override
	public void mouseMove(MouseEvent e) {
		mouseX = e.x;
		mouseY = e.y;
		text = null;
		viewer.refresh();
	}

	@Override
	public void addedToViewer(XYViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void removedFromViewer(XYViewer viewer) {
		if (control != null) {
			control.removeMouseMoveListener(this);
			control.removeMouseTrackListener(this);
			control = null;
		}
		this.viewer = null;
	}

	@Override
	public void mouseEnter(MouseEvent e) {
		mouseX = e.x;
		mouseY = e.y;
		text = null;
		inactive = false;
		viewer.refresh();
	}

	@Override
	public void mouseExit(MouseEvent e) {
		text = null;
		inactive = true;
		viewer.refresh();
	}

	@Override
	public void mouseHover(MouseEvent e) {
		// TODO NOP
	}

}
