package com.goingkilo.textlayout.screen;

public class ScreenLineInfo {

	public int startFrag,startOffset,endFrag,endOffset,x,y;
	
	public String toString(){
		return startFrag+"."+startOffset+" , "+endFrag+"."+endOffset+" @("+x+","+y+")";
	}
	
	public boolean zeroWidth(){
		return (startFrag == endFrag && startOffset == endOffset); 
	}
}
