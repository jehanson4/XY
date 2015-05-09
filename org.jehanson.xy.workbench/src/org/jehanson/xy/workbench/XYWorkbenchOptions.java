package org.jehanson.xy.workbench;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * 
 * @author jehanson
 */
public class XYWorkbenchOptions extends ViewPart {

	/** must match what's in plugin.xml */
	public static final String VIEW_ID = "org.jehanson.xy.workbench.options";

	private Composite control;
	private Action showView;
	
	@Override
	public void createPartControl(Composite parent) {
		final int nCols = 3;
		control = new Composite(parent, SWT.NONE);
		control.setLayout(new GridLayout(nCols, false));

		Label tmp = new Label(control, SWT.NONE);
		tmp.setText("controls go here");
		tmp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, nCols, 1));

	}

	public Composite getControl() {
		return control;
	}

	@Override
	public void setFocus() {}

	@Override
	public void dispose() {}

	public static XYWorkbenchOptions showOptions(String secondaryID) {
		IWorkbenchPage page =
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	
		String viewID = VIEW_ID;
		int mode = IWorkbenchPage.VIEW_CREATE; // VIEW_ACTIVATE,VIEW_VISIBLE
	
		try {
			IViewPart view = page.showView(viewID, secondaryID, mode);
			return (XYWorkbenchOptions)view;
		}
		catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
