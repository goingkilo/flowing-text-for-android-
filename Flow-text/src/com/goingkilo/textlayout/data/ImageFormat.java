package com.goingkilo.textlayout.data;

public class ImageFormat {
	int align;
	int padding;
	
	public ImageFormat() {}
	
	public ImageFormat(int align, int padding) {
		this.align = align;
		this.padding = padding;
	}
	
	public int getAlign() {
		return align;
	}
	
	public void setAlign(int align) {
		this.align = align;
	}
	
	public int getPadding() {
		return padding;
	}
	
	public void setPadding(int padding) {
		this.padding = padding;
	}
}
