package org.jehanson.xy.swt;

/**
 * These are pixel values.
 * @author jehanson
 */
public class XYMargins {
	
	public int top;
	public int bottom;
	public int left;
	public int right;

	public XYMargins() {
		this(0,0,0,0);
	}
	
	public XYMargins(int top, int bottom, int left, int right) {
		super();
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}

	public XYMargins(XYMargins m) {
		this.top = m.top;
		this.bottom = m.bottom;
		this.left = m.left;
		this.right = m.right;
	}
	
	public void copyFrom(XYMargins m) {
		this.top = m.top;
		this.bottom = m.bottom;
		this.left = m.left;
		this.right = m.right;
	}
}
