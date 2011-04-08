package com.goingkilo.textlayout.data;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class FragmentFormat {
	
	private int 		color 		= Color.BLACK;
	private int 		style  		= Typeface.NORMAL;
	private int 		textSize	= 24;
	private int 		lineSpacing = 3;
	private String	typeface ;


	public FragmentFormat(String typeface, int textSize, int style, int color ) {
		this.color = color;
		this.style = style;
		this.textSize = textSize;
		this.typeface = typeface;
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public int getTextSize() {
		return textSize;
	}
	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}
	public String getTypeface() {
		return typeface;
	}
	public void setTypeface(String typeface) {
		this.typeface = typeface;
	}

	public int getLineSpacing() {
		return lineSpacing;
	}

	public void setLineSpacing(int lineSpacing) {
		this.lineSpacing = lineSpacing;
	}

}
