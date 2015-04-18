package org.jehanson.xy.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

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
		if (this.control != null)
			throw new IllegalStateException("controls already created");

		int nCols = 3;
		this.control = new Composite(parent, style);
		this.control.setLayout(new GridLayout(nCols, false));

		Label placeHolder = new Label(this.control, SWT.NONE);
		placeHolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				nCols, 1));
		placeHolder.setText("(controls go here)");
	}
}
