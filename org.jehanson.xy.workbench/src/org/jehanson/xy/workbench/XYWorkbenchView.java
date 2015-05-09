package org.jehanson.xy.workbench;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jehanson.xy.XYRect;
import org.jehanson.xy.swt.XYViewer;
import org.jehanson.xy.swt.decorators.DrawingAreaBorder;
import org.jehanson.xy.swt.decorators.MouseCoordinates;
import org.jehanson.xy.swt.decorators.UnitSquare;

/**
 * 
 * @author jehanson
 */
public class XYWorkbenchView extends ViewPart {

	/** must match what's in plugin.xml */
	public static final String VIEW_ID = "org.jehanson.xy.workbench.view";
	
	private XYViewer viewer;
	private Action showOptions;
	
	private XYViewer.Entry borderEntry;
	private XYViewer.Entry mouseCoordsEntry;	
	private Action zoomIn;
	private Action zoomOut;
	private Action borderAction;
	private Action mouseCoordsAction;
	
	public XYWorkbenchView() {
		super();
		this.viewer = new XYViewer();
		this.borderEntry = this.viewer.addDrawing(new DrawingAreaBorder());
		this.mouseCoordsEntry = this.viewer.addDrawing(new MouseCoordinates());
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer.createControl(parent, SWT.NONE);	
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}

	public XYViewer getViewer() {
		return viewer;
	}
	
	protected void makeActions() {
		this.showOptions = new Action() {

			@Override
			public void run() {
				String secondaryID = getViewSite().getSecondaryId();
				XYWorkbenchOptions.showOptions(secondaryID);
			}
			
		};
		showOptions.setText("Show Options");
		
		zoomOut = new Action() {
			@Override
			public void run() {
				zoom(0.1);
			}
		};
		zoomOut.setText("[-]");
		zoomOut.setToolTipText("Zoom out");
		
		zoomIn = new Action() {
			@Override
			public void run() {
				zoom(-0.1);
			}
		};
		zoomIn.setText("[+]");
		zoomIn.setToolTipText("Zoom in");
		
		borderAction = new Action() {
			@Override
			public void run() {
				borderEntry.setEnabled(!borderEntry.isEnabled());
				viewer.refresh();
			}
		};
		borderAction.setText("Toggle drawing area border");
		borderAction.setToolTipText("Toggle drawing area border");

		mouseCoordsAction = new Action() {
			@Override
			public void run() {
				mouseCoordsEntry.setEnabled(!mouseCoordsEntry.isEnabled());
				viewer.refresh();
			}
		};
		mouseCoordsAction.setText("Toggle display of mouse coords");
		mouseCoordsAction.setToolTipText("Toggle display of mouse coords");
	}
	
	protected void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				XYWorkbenchView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	protected void fillContextMenu(IMenuManager manager) {
		manager.add(showOptions);
		
		// manager.add(zoomOut);
		// manager.add(zoomIn);
		manager.add(borderAction);
		manager.add(mouseCoordsAction);
	}

	protected void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		IMenuManager menuManager = bars.getMenuManager();
		menuManager.add(showOptions);

		// menuManager.add(zoomOut);
		// menuManager.add(zoomIn);
		// menuManager.add(new Separator());
		menuManager.add(borderAction);
		menuManager.add(mouseCoordsAction);
		
		IToolBarManager toolbarManager = bars.getToolBarManager();
		toolbarManager.add(zoomOut);
		toolbarManager.add(zoomIn);
		// toolbarManager.add(unitSquareAction);
	}

	public void zoom(double factor) {
		XYRect oldBounds = viewer.getDataBounds();
		double deltaX = 0.5*factor*(oldBounds.getXMax() - oldBounds.getXMin());
		double deltaY = 0.5*factor*(oldBounds.getYMax() - oldBounds.getYMin());
		XYRect newBounds = new XYRect(
				oldBounds.getXMin()-deltaX,
				oldBounds.getYMin()-deltaY,
				oldBounds.getXMax()+deltaX,
				oldBounds.getYMax()+deltaY
				);
		viewer.setDataBounds(newBounds);
		viewer.refresh();
	}

	public static XYWorkbenchView showView(String secondaryID) {
		IWorkbenchPage page =
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	
		String viewID = VIEW_ID;
		int mode = IWorkbenchPage.VIEW_CREATE; // VIEW_ACTIVATE,VIEW_VISIBLE
	
		try {
			IViewPart view = page.showView(viewID, secondaryID, mode);
			return (XYWorkbenchView)view;
		}
		catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
