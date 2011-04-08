package com.goingkilo.textlayout.textlayout;

import java.util.List;

import com.goingkilo.textlayout.boxlayout.BoxLayout;
import com.goingkilo.textlayout.data.Block;
import com.goingkilo.textlayout.data.Image;
import com.goingkilo.textlayout.screen.ScreenLineInfo;

public class ScreenBlock {

	List<ScreenLineInfo> lines;
	List<Image> 		 images;
	
	int width;
	int height;
	
	int xOffset;
	int yOffset;
	
	Block block;

	public ScreenBlock(){}
	
	public ScreenBlock( Block block, List<ScreenLineInfo> lines, int xOffset, int yOffset, int w) {
		this.block 	 = block;
		
		this.images  = block.images;
		this.lines 	 = lines;
		
		this.yOffset = yOffset;
		this.xOffset = xOffset;
		
		this.width   = w;

		// need to set height explicitly by whoever is calling this constructor
		// this.height = lines.getBlockHeight(boxlayout);
	}
	
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public List<ScreenLineInfo> getLines() {
		return lines;
	}
	public void setLines(List<ScreenLineInfo> lines) {
		this.lines = lines;
	}
	
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public int getXOffset() {
		return xOffset;
	}
	public void setXOffset(int offset) {
		xOffset = offset;
	}

	public int getYOffset() {
		return yOffset;
	}
	public void setYOffset(int offset) {
		yOffset = offset;
	}

	// greater of (y of last line) or (bottom most line of box)
	public int getBlockHeight(BoxLayout boxLayout) {
		if( lines != null && lines.size() > 0 ){
			
			int l = lines.get(lines.size()-1).y;
			//this is the height of the last box - left or right
			int b = boxLayout.getHeight() ;
			
			return ( l > b ) ? l  : b;
		}
		return boxLayout.getHeight();
	}
	
	public void shiftLines() {
		//x
		for( int i = 0 ; i < lines.size() ; i++ ){
			lines.get(i).x += xOffset; 
		}
		for ( int i = 0 ; i < block.images.size(); i++ ){
			block.images.get(i).box.x += xOffset;
		}
		
		//y
		for( int i = 0 ; i < lines.size() ; i++ ){
			lines.get(i).y += yOffset; 
		}
		for ( int i = 0 ; i < block.images.size(); i++ ){
			block.images.get(i).box.y += yOffset;
		}
		
	}	
}
