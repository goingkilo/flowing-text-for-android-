package com.goingkilo.textlayout.boxlayout;

public class Box {

	public int x,y,w,h;

	public Box(int x, int y, int w, int h) {
		this.h = h;
		this.w = w;
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "BOX:"+x+","+y+","+w+","+h;
	}
}
