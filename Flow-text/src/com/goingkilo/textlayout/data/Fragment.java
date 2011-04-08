package com.goingkilo.textlayout.data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.graphics.Paint;
import android.graphics.Typeface;

public class Fragment  {
	
	
	public 				String 			data;
	public 				Paint 			paint;
	public 				FragmentFormat 	format;
	public 				boolean 		isNewLine;
	
	public static final String 			WORD_SEPARATORS = " ;:-,.";
		
	public Fragment(String data, FragmentFormat f) {
		this.data = data+" ";
		

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(f.getColor());
		paint.setTextSize(f.getTextSize());
		String s = null;
		if( f.getTypeface() == null){
			paint.setTypeface(Typeface.create(s,f.getStyle()));
		}
		else {
			Typeface tf = Typeface.createFromFile(f.getTypeface());
			paint.setTypeface(Typeface.create(tf,f.getStyle()));
		}
		this.format = f;
	}

	public Fragment[] breakAt(int breakpoint){
		Fragment[] ret = new Fragment[2];
		ret[0] = new Fragment( data.substring(0,breakpoint), format);
		ret[1] = new Fragment( data.substring(breakpoint)  , format);
		return ret;
	}
	
	public int measuredSize(){
		return (int) paint.measureText(String.valueOf(data)); 
	}
	
	public int length(){ 
		return data.length();
	}

	public boolean isSpecial() {
		return false;
	}

	public boolean isNewLine() {
		return isNewLine;
	}
	
	public int slice(int length, int offset, int[] ret){
		float[] measured = new float[1];
		int index = paint.breakText(data, offset ,data.length(), true, length, measured);
//		System.out.println( "-------------[A]"+offset+".."+index+"/"+data.length()+" in "+length);
//		System.out.println( "-------------[B]"+data.substring(offset,offset+index));
		
		if( (offset+index) != data.length() ){	//if using up fragment, then don't bother breaking.
			for( int i = (offset+index) ; i > offset ; i-- ){
//				System.out.println( "-------------[C]"+data.charAt(i));		
				if( isBreaker(data.charAt(i)) 	){
//						System.out.println( "-------------[D]");
					ret[0] = (int)paint.measureText(data,offset,i);
					return (i-offset);
				}
			}
		}
		
		ret[0] = (int)measured[0];
		return index;
	}
	
	
	private boolean isBreaker(char c) {
		for( int i = 0 ; i < WORD_SEPARATORS.length() ; i++ ){
			if( WORD_SEPARATORS.charAt(i) == c ){
				return true;
			}
		}
		return false;
	}
//	public int sliceOld(int length, int offset, int[] ret){
//		float[] measured = new float[1];
//		int i = paint.breakText(data,offset,data.length(),true,length,measured);
//
//		ret[0] = (int)measured[0];
//		return i;
//	}

	public FragmentFormat getFormat() {
		return format;
	}

	public String getData() {
		return data;
	}

	//to move this to a helper function
	//public static final String WORD_SEPARATORS = " ;:-,.";
	public List<String> getWords(String text,String WORD_SEPARATORS){
		List<String> words =  new ArrayList<String>();
		char[] chars = text.toCharArray();
		StringBuilder word = new StringBuilder();
		for( int i = 0 ; i < chars.length ; i++ ){
			if( WORD_SEPARATORS.indexOf(chars[i]) >= 0 ){
				words.add(word.toString());
				words.add(String.valueOf(chars[i]));
				word = new StringBuilder();
			}
			else {
				word.append(chars[i]);
			}
		}
		if(word.length() != 0 ){
			words.add(word.toString());
		}
//		return newlineSplit(words);
		return words;
	}
	
	//needed ?
	private List<String> newlineSplit(List<String> words){
		List<String> words1= new ArrayList<String>();
		for(String str : words ){
			if( str.indexOf("\n")>=	0){
				StringTokenizer tok = new StringTokenizer(str,"\n");
				while(tok.hasMoreTokens()){
					words1.add(tok.nextToken());
					words1.add("\n");
				}
				if(!str.endsWith("\n")){
					words1.remove(words1.size()-1);
				}
			}
			else {
				words1.add(str);
			}
		}
		return words1;
	}
	
	public int getHeight(){
		int a = Math.abs(paint.getFontMetricsInt().ascent);
		int b = paint.getFontMetricsInt().descent;
		return  a + b + format.getLineSpacing();
	}
	
	public void replaceStringContent(String s){
		this.data = s+" ";
	}
}