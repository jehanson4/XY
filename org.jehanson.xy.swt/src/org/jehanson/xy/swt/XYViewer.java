package org.jehanson.xy.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.jehanson.xy.XYRect;

public class XYViewer extends Viewer {

	// ==================================
	// Inner classes
	// ==================================

	public static class Entry {

		boolean enabled;
		final XYDrawing drawing;

		public Entry(XYDrawing drawing) {
			super();
			this.drawing = drawing;
			this.enabled = true;
		}

		public XYDrawing getDrawing() {
			return this.drawing;
		}

		public boolean isEnabled() {
			return this.enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public void drawOn(GC gc) {
			if (enabled)
				drawing.drawOn(gc);
		}

	}

	private class CListener implements ControlListener, PaintListener {

		@Override
		public void controlMoved(ControlEvent e) {}

		@Override
		public void controlResized(ControlEvent e) {
			fixBounds();
		}

		@Override
		public void paintControl(PaintEvent e) {
			final GC gc = e.gc;
			final Color bg0 = gc.getBackground();
			gc.setBackground(background);
			gc.fillRectangle(canvas.getClientArea());
			for (Entry drawing : drawings) {
				drawing.drawOn(gc);
			}
			gc.setBackground(bg0);
		}
	}

 	// ===============================
	// Variables
	// ===============================

	private final XYRect dataBounds;
	private XYTransform transform;
	private Canvas canvas;
	private Color background;
	private Color foreground;
	
	private final XYMargins margins;
	private final Rectangle drawingArea;
	private boolean fitToCanvas;
	private boolean aspectRatioPreserved;
	private final List<Entry> drawings;

	/** Not used but its existence is implied by superclass. */
	private Object inputObj;

	// ===============================
	// Creation
	// ===============================

	public XYViewer() {
		super();
		this.dataBounds = new XYRect(0, 0, 1, 1);
		this.transform = new XYLinearTransform();
		this.canvas = null;
		this.background = null;
		this.foreground = null;
		this.margins = new XYMargins(32, 32, 32, 32);

		// Initial values are important here.
		this.drawingArea = new Rectangle(0, 0, 32, 32);
		this.fitToCanvas = true;
		this.aspectRatioPreserved = true;

		this.drawings = new ArrayList<Entry>();
		this.inputObj = null;
	}

	// ===============================
	// Operation
	// ===============================

	@Override
	public Canvas getControl() {
		return canvas;
	}

	public void createControl(Composite parent, int style) {
		canvas = new Canvas(parent, style);
		
		// SOMETHING SOMEWHERE sets the canvas background to (240, 240, 240).
		// I suspect the workbench, which likes to change background colors
		// when things get selected/deselected. 
		// has no effecxt: canvas.setBackgroundMode(SWT.INHERIT_NONE);
		
		background = canvas.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		foreground = canvas.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		canvas.setForeground(foreground);
		CListener clistener = new CListener();
		canvas.addControlListener(clistener);
		canvas.addPaintListener(clistener);
		fixBounds();
	}

	public XYMargins getMargins() {
		return new XYMargins(this.margins);
	}

	public void setMargins(XYMargins margins) {
		if (margins == null)
			throw new IllegalArgumentException("margins cannot be null");
		this.margins.copyFrom(margins);
	}

	/**
	 * Indicates whether the drawing area is automatically resized to fit the
	 * canvas.
	 */
	public boolean isFitToCanvas() {
		return this.fitToCanvas;
	}

	/**
	 * Sets the width and height of the drawing area. Calling this method
	 * disables automatic resizing of the drawing area.
	 * 
	 * @param width
	 * @param height
	 */
	public void setDrawingAreaSize(int width, int height) {
		if (!(width > 0))
			throw new IllegalArgumentException("width=" + width + " -- must be > 0");
		if (!(height > 0))
			throw new IllegalArgumentException("height=" + height + " -- must be > 0");
		this.fitToCanvas = false;
		this.drawingArea.width = width;
		this.drawingArea.height = height;
	}

	/**
	 * Enables automatic resizing of the drawing area to fit the canvas.
	 */
	public void fitToCanvas() {
		this.fitToCanvas = true;
	}

	/**
	 * Indicates whether the drawing's aspect ratio should be preserved when
	 * fitting the drawing area to the canvas.
	 * 
	 * @return
	 */
	public boolean isAspectRatioPreserved() {
		return this.aspectRatioPreserved;
	}

	public void setAspectRatioPreserved(boolean aspectRatioPreserved) {
		this.aspectRatioPreserved = aspectRatioPreserved;
	}

	public XYRect getDataBounds() {
		return new XYRect(this.dataBounds);
	}

	public void setDataBounds(XYRect newBounds) {
		dataBounds.copyFrom(newBounds);
		if (canvas != null && !canvas.isDisposed())
			fixBounds();
	}

	public Rectangle getDrawingArea() {
		return new Rectangle(drawingArea.x, drawingArea.y, drawingArea.width,
				drawingArea.height);
	}

	public XYTransform getTransform() {
		return transform;
	}

	public void setTransform(XYTransform transform) {
		this.transform = transform;
	}

	@Override
	public Object getInput() {
		return inputObj;
	}

	@Override
	public void setInput(Object input) {
		this.inputObj = input;
	}

	@Override
	public ISelection getSelection() {
		return new ISelection() {
			@Override
			public boolean isEmpty() {
				return true;
			}
		};
	}

	@Override
	public void setSelection(ISelection arg0, boolean arg1) {
		// NOP
	}

	@Override
	public void refresh() {
		canvas.redraw();
	}

	public Entry addDrawing(XYDrawing drawing) {
		return addDrawing(drawings.size(), drawing);
	}

	public Entry addDrawing(int idx, XYDrawing drawing) {
		drawing.addedToViewer(this);
		Entry entry = new Entry(drawing);
		drawings.add(idx, entry);
		return entry;
	}

	public XYDrawing removeDrawing(int idx) {
		Entry entry = drawings.remove(idx);
		XYDrawing drawing = entry.getDrawing();
		drawing.removedFromViewer(this);
		return drawing;
	}

	protected void fixBounds() {
		Rectangle canvasArea = canvas.getClientArea();

		// 1. Set drawing are size.
		if (!fitToCanvas) {
			// NOP: Drawing area size stays same.
		}
		else if (!aspectRatioPreserved) {
			// Drawing area size is canvas size less margins
			drawingArea.width = canvasArea.width - (margins.left + margins.right);
			if (drawingArea.width < 1)
				drawingArea.width = 1;
			drawingArea.height = canvasArea.height - (margins.top + margins.bottom);
			if (drawingArea.height < 1)
				drawingArea.height = 1;
		}
		else {
			double drawingAspectRatio =
					(double) drawingArea.width / (double) drawingArea.height;
			double canvasAspectRatio =
					(double) canvasArea.width / (double) canvasArea.height;
			if (canvasAspectRatio >= drawingAspectRatio) {
				// fit drawing area height to canvas height less margins
				drawingArea.height = canvasArea.height - (margins.top + margins.bottom);
				drawingArea.width =
						(int) (drawingAspectRatio * (double) drawingArea.height);
				if (drawingArea.height < 1)
					drawingArea.height = 1;
				if (drawingArea.width < 1)
					drawingArea.width = 1;
			}
			else {
				// fit drawing area width to canvas width less margins.
				drawingArea.width = canvasArea.width - (margins.left + margins.right);
				drawingArea.height =
						(int) ((double) drawingArea.width / drawingAspectRatio);
				if (drawingArea.height < 1)
					drawingArea.height = 1;
				if (drawingArea.width < 1)
					drawingArea.width = 1;
			}
		}

		// 2. Re-center drawing area.
		drawingArea.x = canvasArea.x + (canvasArea.width - drawingArea.width) / 2;
		if (drawingArea.x < canvasArea.x)
			drawingArea.x = canvasArea.x;
		drawingArea.y = canvasArea.y + (canvasArea.height - drawingArea.height) / 2;
		if (drawingArea.y < canvasArea.y)
			drawingArea.y = canvasArea.y;

		// 3. update the transform
		transform.initialize(dataBounds, drawingArea);
	}
}
