package org.jehanson.xy.swt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * 
 * @author jehanson
 */
public class XYViewerOptions {

	private final XYViewer viewer;
	private Composite control;
	/**
	 * @param viewer
	 */
	public XYViewerOptions(XYViewer viewer) {
		super();
		this.viewer = viewer;
	}
	
	public Control getControl() {
		return control;
	}
	
	public void createControl(Composite parent, int style) {
		
	}
}
