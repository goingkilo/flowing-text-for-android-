package com.goingkilo.textlayout.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.goingkilo.textlayout.boxlayout.Box;

public class Image {
	public Box box;
	public String path;
	public Bitmap bmp;
	ImageFormat format;
	
	public static final int LEFT 	= 0;
	public static final int RIGHT 	= 1;
	
	public Image(String path, ImageFormat format) {
		this.path 		= path;
		this.format 	= format;

//		System.out.println("DEBUG - image:"+path);
//		System.out.println("DEBUG - image[]:"+format);
		this.bmp = BitmapFactory.decodeFile(path);
//		System.out.println("DEBUG - image:"+this.bmp);
		this.box = new Box(0,0, bmp.getWidth()+ 2*format.getPadding() , bmp.getHeight()+2*format.getPadding());
	}
	
	public boolean isLeft(){
		return format.getAlign() == Document.LEFT;
	}
	
	public boolean isRight(){
		return format.getAlign() == Document.RIGHT;
	}
	
	public int getPadding(){
		return format.getPadding();
	}

	public ImageFormat getFormat() {
		return format;
	}

}
