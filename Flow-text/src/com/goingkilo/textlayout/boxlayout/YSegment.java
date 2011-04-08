package com.goingkilo.textlayout.boxlayout;

public class YSegment {

	public int start,end,x;

	public YSegment(int x0, int x1,int x) {
		this.start 	= x0;
		this.end	= x1;
		this.x 		= x;
	}
	
	public int size(){
		return end -start;
	}
	public String toString(){
		return start +","+end+" @" + x;
	}
}
