package org.jehanson.xy.swt;

import org.eclipse.swt.graphics.GC;

public interface XYDrawing {

	/**
	 * Informs this drawing that it has been added to the given viewer.
	 *
	 * @param viewer
	 */
	public void addedToViewer(XYViewer viewer);

	/**
	 * Informs this drawing that it has been removed from the given viewer.
	 * 
	 * @param viewer
	 */
	public void removedFromViewer(XYViewer viewer);

	/**
	 * Displays this drawing on the given GC.
	 * 
	 * @param gc
	 */
	public void drawOn(GC gc);

}
