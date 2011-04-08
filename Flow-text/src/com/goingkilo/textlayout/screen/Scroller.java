package com.goingkilo.textlayout.screen;

public class Scroller {

	int scrolled = 0;
	int documentDrawnHeight = 0;
	int fingerDownY = -1, fingerUpY;
	int fingerMoveY0 = -1,fingerMoveY1 = -1;

	int screenHeight;
	int scrollOvershoot = 10;
	
	boolean enableScroll = false;
	
	public Scroller(){	}
	
	public void fingerDown(int y){
		if( fingerDownY == -1 && y < documentDrawnHeight){
			fingerDownY = y;
		}
		if( fingerMoveY0 == -1 && y < documentDrawnHeight){
			fingerMoveY0 = y;
		}
	}
	
	public int fingerMove(int y){
		
		if( enableScroll && fingerMoveY0 != -1){
			fingerMoveY1 = y;
//			System.out.println("[]>"+documentDrawnHeight + "," + fingerDownY+"," + fingerUpY);
//			System.out.println("(" + fingerMoveY0+"," + fingerMoveY1+")");
			int scroll = fingerMoveY0 - fingerMoveY1;
			if( scroll >= 0){
				if( scroll -(documentDrawnHeight  - screenHeight -scrolled) > 0 ){
					scroll = (documentDrawnHeight - screenHeight -scrolled);
				}
			}
			else {
				if( Math.abs(scroll) > (scrolled )){
					scroll = -(scrolled );
				}
			}
			 
//			scrollBy(0,scroll);
			scrolled += scroll;
			fingerMoveY0 = fingerMoveY1;
			return scroll;
		}	
		return 0;
	}
	
	public void reset(){
		fingerMoveY0 = -1;
		fingerMoveY1 = -1;
	}

	public void setHeights(int h, int d) {
		this.screenHeight  		 = h;
		this.documentDrawnHeight = d+2*scrollOvershoot;
		if( documentDrawnHeight < screenHeight){
			enableScroll = false;
		}
		else{
			enableScroll = true;
		}	}
	
}
