package com.goingkilo.textlayout.data;

import java.util.ArrayList;

public class ImageBlock extends Block{

	
	public ImageBlock(){
		images = new ArrayList<Image>();
	}
	
	public ImageBlock(Image image){
		this();
		images.add(image);
	}
	
	public ImageBlock(String imagePath, ImageFormat format ){
		this();
		images.add(new Image(imagePath,format));
	}
	
	public void addFragment(String text, FragmentFormat f)						{}
	public void insert(String text, FragmentFormat f, int absindex)				{}
	public void insertAt(String text, FragmentFormat f, int fragnum, int offset){}
	private int[] translate(int abspos)								{ return null; }
	

	public int getH() {
		return images.get(0).bmp.getHeight()+ (2*images.get(0).getPadding());
	}

	public int dataLength(){ return 0; }
	
	public Image getImage(){
		return images.get(0);
	}

}
