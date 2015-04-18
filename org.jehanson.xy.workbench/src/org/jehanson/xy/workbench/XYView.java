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
public class XYView extends ViewPart {

	private XYViewer viewer;
	private XYViewer.Entry unitSquareEntry;
	private XYViewer.Entry borderEntry;
	private XYViewer.Entry mouseCoordsEntry;
	
	private Action zoomIn;
	private Action zoomOut;
	private Action unitSquareAction;
	private Action borderAction;
	private Action mouseCoordsAction;
	
	public XYView() {
		super();
		this.viewer = new XYViewer();
		this.unitSquareEntry = this.viewer.addDrawing(new UnitSquare());
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

	protected void makeActions() {
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
		
		unitSquareAction = new Action() {
			@Override
			public void run() {
				unitSquareEntry.setEnabled(!unitSquareEntry.isEnabled());
				viewer.refresh();
			}
		};
		unitSquareAction.setText("Toggle unit square");
		unitSquareAction.setToolTipText("Toggle unit square");
		
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
				XYView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	protected void fillContextMenu(IMenuManager manager) {
		// manager.add(zoomOut);
		// manager.add(zoomIn);
		manager.add(unitSquareAction);
		manager.add(borderAction);
		manager.add(mouseCoordsAction);
	}

	protected void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		IMenuManager menuManager = bars.getMenuManager();
		// menuManager.add(zoomOut);
		// menuManager.add(zoomIn);
		// menuManager.add(new Separator());
		menuManager.add(unitSquareAction);
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
	
}
