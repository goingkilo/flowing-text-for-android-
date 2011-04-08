package com.goingkilo.textlayout.data;

import java.util.ArrayList;
import java.util.List;

//a store to maintain format information for chunks of text
// color 0 is transparent in  android
public class Block{
	
	boolean hasBorder  = false;
	
	public List<Fragment> fragments;
	
	public List<Image> 		images;

	public Block(){
		fragments  = new ArrayList<Fragment>();
		images 	   = new ArrayList<Image>();
	}
	
	public Block(boolean border){
		this();
		hasBorder = border;
	}
	
	public void addImage(String path, ImageFormat format ){
		images.add(new Image(path, format));
	}
	
	public void addImage(Image img){
		images.add(img);
	}
	
	public void addFragment(String text, FragmentFormat f){
		fragments.add( new Fragment(text,f));
	}
	
	public void addFragment(Fragment frag){
		fragments.add( frag );
	}
	
	//removes one last fragment
	public void removeFragment(){
		if( fragments != null && fragments.size() > 0 ){
			fragments.remove(fragments.size()-1);
		}
	}
	
	public void insert(String text, FragmentFormat f, int absindex){
		if( dataLength() < absindex ){
			addFragment(text, f);
		}
		else {
			int[] pos = translate(absindex);
			insertAt(text, f, pos[0],pos[1]);
		}
	}
	
	public void insertAt(String text, FragmentFormat f, int fragnum, int offset){
		Fragment[] f2 = fragments.get(fragnum).breakAt(offset);
		fragments.remove(fragnum);
		fragments.add(fragnum, f2[0]);
		fragments.add(fragnum+1, new Fragment(text, f) );
		fragments.add(fragnum+2, f2[1]);
	}
	
	//returns pagenum ,offset
	private int[] translate(int abspos){
		int sum  = 0;
		for( int i = 0 ; i < fragments.size() ; i++ ){
			sum += fragments.get(i).data.length();
			if( (abspos - sum) < 0 ){
				return new int[]{i,(abspos - (sum-fragments.get(i).data.length()))};
			}
		}
		return new int[]{fragments.size(),dataLength()-1};//add at end
	}
	
	public int dataLength(){
		int i = 0;
		for( Fragment f : fragments ){
			i += f.data.length();
		}
		return i;
	}

	public boolean hasBorder() {
		return hasBorder;
	}

	public void hasBorder(boolean hasBorder) {
		this.hasBorder = hasBorder;
	}
	
}
