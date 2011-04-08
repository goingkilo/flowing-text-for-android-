package com.goingkilo.textlayout.xml;

import com.goingkilo.textlayout.app.DataFillerUp;
import com.goingkilo.textlayout.data.Block;
import com.goingkilo.textlayout.data.Document;
import com.goingkilo.textlayout.data.Fragment;
import com.goingkilo.textlayout.data.FragmentFormat;
import com.goingkilo.textlayout.data.Image;
import com.goingkilo.textlayout.data.ImageBlock;
import com.goingkilo.textlayout.data.ImageFormat;

public class Writer {

	private static final String 	DOCUMENT 		= "DOCUMENT";
	private static final String 	BLOCK 			= "BLOCK";
	private static final String 	IMAGE 			= "IMAGE";
	private static final String 	IMAGEFORMAT 	= "IMAGEFORMAT";
	private static final String 	IMAGEBLOCK 		= "IMAGEBLOCK";
	private static final String 	FRAGMENT 		= "FRAGMENT";
	private static final String  	FRAGMENTFORMAT  = "FRAGMENTFORMAT";
	
	private static final String HEADER 		= "<?xml version='1.0'?>";
	private static final String LEFT 		= "<";
	private static final String RIGHT 		= ">";
	private static final String LEFTCLOSE 	= "</";
	private static final String NEWLINE 	= "\n";
	
	private static final String 	FRAG_DATA 			= "DATA";
	private static final String 	FRAGFMT_COLOR 		= "COLOR";
	private static final String 	FRAGFMT_STYLE 		= "STYLE";
	private static final String 	FRAGFMT_TEXTSIZE 	= "TEXTSIZE";
	private static final String 	FRAGFMT_TYPEFACE 	= "TYPEFACE";
	
	private static final String 	IMG_PATH 			= "PATH";
	private static final String 	IMGFMT_ALIGN 		= "ALIGN";
	private static final String 	IMGFMT_PADDING 		= "PADDING";
	

	
	public String getXMLString(Document doc){
		StringBuilder sb = new StringBuilder(HEADER);
		openTag(sb,DOCUMENT);
		for(Block block : doc.blocks ){
			addBlock(sb,block);
		}
		closeTag(sb,DOCUMENT);
		return sb.toString();
	}

	private void addBlock(StringBuilder sb, Block block){
		if( block.getClass().getName().equals("com.goingkilo.textlayout.data.ImageBlock")){
			addImageBlock(sb,(ImageBlock)block);
			return;
		}
		openTag(sb,BLOCK);
		for( Image image : block.images ){
			addImage(sb,image);
		}
//		System.out.println("adding fragment");
		for( Fragment frag : block.fragments ){
			addFragment(sb, frag);
		}
		closeTag(sb,BLOCK);
	}

	private void addImageBlock(StringBuilder sb, ImageBlock imageBlock){
		openTag(sb,IMAGEBLOCK);
		addImage(sb,imageBlock.getImage());
		closeTag(sb,IMAGEBLOCK);
	}
	
	private void addImage(StringBuilder sb, Image image){
		openTag(sb,IMAGE);
		openTag(sb,IMG_PATH);
		sb.append(image.path);
		closeTag(sb,IMG_PATH);
		addImageFormat(sb, image.getFormat());
		closeTag(sb,IMAGE);
	}

	private void addImageFormat(StringBuilder sb, ImageFormat imgfrmt){
		openTag(sb, IMAGEFORMAT);
		addTag( sb, IMGFMT_ALIGN, imgfrmt.getAlign());
		addTag( sb, IMGFMT_PADDING, imgfrmt.getAlign());
		closeTag(sb, IMAGEFORMAT);
	}
	
	private void addFragment(StringBuilder sb, Fragment frag){
		openTag(sb,FRAGMENT);
		addTag(sb,FRAG_DATA, frag.getData());
//		System.out.println("adding fragment format : "+frag.getFormat());
		addFragmentFormat(sb, frag.getFormat());
		closeTag(sb,FRAGMENT);
	}

	private void addFragmentFormat(StringBuilder sb, FragmentFormat fragformat){
		openTag(sb,FRAGMENTFORMAT);
		addTag(sb,FRAGFMT_COLOR, 		fragformat.getColor());
		addTag(sb,FRAGFMT_STYLE, 		fragformat.getStyle()); 
		addTag(sb,FRAGFMT_TEXTSIZE, 	fragformat.getTextSize());
		addTag(sb,FRAGFMT_TYPEFACE, 	fragformat.getTypeface());
		closeTag(sb,FRAGMENTFORMAT);
	}
	
	private void addTag(StringBuilder sb, String name, String value){
		openTag(sb,name);
		sb.append( ((value == null ) ? "" : value ));
		closeTag(sb,name);
	}
	
	private void addTag(StringBuilder sb, String name, int value){
		openTag(sb,name);
		sb.append(value);
		closeTag(sb,name);
	}

	private void openTag(StringBuilder sb, String name){
		sb.append(NEWLINE);
		sb.append(LEFT); sb.append(name); sb.append(RIGHT);
	}

	private void closeTag(StringBuilder sb, String name){
		sb.append(LEFTCLOSE); sb.append(name); sb.append(RIGHT); 
		//sb.append(NEWLINE);
	}

	public static void main1(String[] args){
		Document doc = new Document ();
		DataFillerUp.fillDoc(doc);
	}
}
