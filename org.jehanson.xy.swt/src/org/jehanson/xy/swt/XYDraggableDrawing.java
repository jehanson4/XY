package org.jehanson.xy.swt;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

/**
 * 
 * @author jehanson
 */
public abstract class XYDraggableDrawing implements XYDrawing, MouseListener,
		MouseTrackListener, MouseMoveListener {

	private XYViewer viewer;
	private Canvas control;
	private boolean armed;
	private Rectangle bounds;
	private int prevX;
	private int prevY;
	private boolean dragging;
	private int dragOffsetX;
	private int dragOffsetY;

	public XYDraggableDrawing() {
		super();
		this.viewer = null;
		this.control = null;
		this.armed = false;
		this.bounds = new Rectangle(0, 0, 1, 1);

		this.prevX = 0;
		this.prevY = 0;
		this.dragging = false;
		this.dragOffsetX = 0;
		this.dragOffsetY = 0;
	}

	// ====================================
	// Operation
	// ====================================

	@Override
	public void mouseMove(MouseEvent e) {
		print("mouse move!");
		if (dragging) {
			print("drag!");
			bounds.x = e.x - dragOffsetX;
			bounds.y = e.y - dragOffsetY;
			viewer.refresh();
		}
	}

	@Override
	public void mouseEnter(MouseEvent e) {
		print("armed!");
		armed = true;
	}

	@Override
	public void mouseExit(MouseEvent e) {
		print("disarmed!");
		armed = false;
	}

	@Override
	public void mouseHover(MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseDown(MouseEvent e) {
		bounds.width = getWidth();
		bounds.height = getHeight();
		if (armed && bounds.contains(e.x, e.y)) {
			print("start dragging!");
			dragging = true;
			prevX = bounds.x;
			prevY = bounds.y;
			dragOffsetX = e.x - bounds.x;
			dragOffsetY = e.y - bounds.y;
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		print("mouse up!");
		if (dragging) {
			print("stop dragging!");
			dragging = false;
			if (!getControl().getClientArea().contains(bounds.x, bounds.y)) {
				bounds.x = prevX;
				bounds.y = prevY;
			}
		}
	}

	@Override
	public void addedToViewer(XYViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void removedFromViewer(XYViewer viewer) {
		if (control != null) {
			control.removeMouseListener(this);
			control.removeMouseMoveListener(this);
			control.removeMouseTrackListener(this);
			control = null;
		}
		this.viewer = null;
	}

	@Override
	public void drawOn(GC gc) {
		if (control == null)
			setControl(viewer.getControl());
	}

	protected void setControl(Canvas control) {
		this.control = control;
		this.control.addMouseListener(this);
		this.control.addMouseMoveListener(this);
		this.control.addMouseTrackListener(this);
	}

	public void setPosition(int x, int y) {
		bounds.x = x;
		bounds.y = y;
	}

	public final int getX() {
		return bounds.x;
	}

	public final int getY() {
		return bounds.y;
	}

	public abstract int getHeight();

	public abstract int getWidth();

	public boolean isArmed() {
		return armed;
	}

	public XYViewer getViewer() {
		return viewer;
	}

	public Canvas getControl() {
		return control;
	}

	private void print(String msg) {
		System.out.println(msg);
	}
}
