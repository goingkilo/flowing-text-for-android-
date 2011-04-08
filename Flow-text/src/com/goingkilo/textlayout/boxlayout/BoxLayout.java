package com.goingkilo.textlayout.boxlayout;

import java.util.ArrayList;
import java.util.List;

import com.goingkilo.textlayout.data.Block;
import com.goingkilo.textlayout.data.Image;

public class BoxLayout {

	int width;
	int buffer = 0 ;

	int height= 0;
	
	public List<Box> left;
	public List<Box> rt;

	public List<YSegment> lintercepts;
	public List<YSegment> rintercepts;

	public BoxLayout(int w){
		this.width = w;
		left = new ArrayList<Box>();
		rt = new ArrayList<Box>();
	}

	private int lastOfList(List<Box> list,int lBuffer, int defaultVal){
		return (list.size() > 0 ) ?(list.get(list.size()-1).y + list.get(list.size()-1).h + lBuffer):defaultVal;
	}
	
	public void addL(int w,int h ){
		int y = lastOfList(left,buffer,0);
		left.add(new Box(0, y, w , h ));
		if( (y+h) > height ) {height = y+h; }
	}

	public void addR(int w,int h ){
		int y = lastOfList(rt,buffer,0);
		rt.add(new Box(width -w, y, w, h ));
		if( (y+h) > height ) {height = y+h; }
	}

	public List<YSegment> getIntercepts(List<Box> boxes,int y0 , int x, boolean left){
		List<YSegment> ls = new ArrayList<YSegment>();
		
		if (boxes.size() == 0) {
			return ls;
		}
		 
		int start = y0;
		int end   = -1;

		Box b;
		int i = 0;
		int lwidth = left ? 0 : width;
		
		while(i < boxes.size()){
			b = boxes.get(i);
			if( b.y > start ){
				end  = b.y;
				ls.add(new YSegment(start,end,lwidth));
			}

			start = b.y;
			end = b.y+b.h;
			
			if(left){
				ls.add(new YSegment(start,end, b.x+b.w));
			}
			else {
				ls.add(new YSegment(start,end, b.x));
			}
//			System.out.println (""+start+","+end);
			start = end;
			i++;
		}
		return ls;
	}

	public List<Box> pinchOff(List<YSegment> lls,List<YSegment> rls){
		List<Box> b  = new ArrayList<Box>();
		
		if( lls.size() == 0 && rls.size() == 0 ){
			return b;
		}
		else if ( lls.size() == 0 && rls.size() > 0 ){
			lls.add(new YSegment(rls.get(0).start ,rls.get(rls.size()-1).end, 0));
		}
		else if ( lls.size() > 0 && rls.size() == 0 ){
			rls.add(new YSegment(lls.get(0).start ,lls.get(lls.size()-1).end, width));
		}

		if( lls.get(lls.size()-1).end >  rls.get(rls.size()-1).end  ) {
			rls.add(new YSegment(rls.get(rls.size()-1).end ,lls.get(lls.size()-1).end, width));
		}
		else if ( lls.get(lls.size()-1).end <  rls.get(rls.size()-1).end  ) {
			lls.add(new YSegment(lls.get(lls.size()-1).end ,rls.get(rls.size()-1).end, 0));
		}
		
		int il = 0;
		int ir = 0;

		// 1. If the
		YSegment l = null;
		YSegment r = null;
		l = lls.get(il);
		r = rls.get(ir);

		while (true){
			if( l.size() >= r.size() ){		//l is bigger
				if( b.size() >= 1) {
					Box bb = b.get(b.size()-1);
					if( bb.x == l.x && bb.w == r.x-l.x ) {
						b.get(b.size()-1).h += r.size();
					}
					else {
						b.add(new Box(l.x, l.start, r.x-l.x, r.size()));
					}
				}
				else {
					b.add(new Box(l.x, l.start, r.x-l.x, r.size()));
				}
				l.start = r.end;

				ir ++;
				if( ir >= rls.size() ) {
					break;
				}
				r = rls.get(ir);
			}
			else {	// r is bigger
				if( b.size() >= 1) {
					Box bb = b.get(b.size()-1);
					if( bb.x == l.x && bb.w == r.x-l.x ) {
						b.get(b.size()-1).h += l.size();
					}
					else {
						b.add(new Box(l.x, l.start, r.x-l.x, l.size()));
					}
				}
				else { 						
					b.add(new Box(l.x, l.start, r.x-l.x, l.size()));
				}
				
				r.start = l.end;

				il ++;
				if( il >= lls.size() ) {
					break;
				}
				l = lls.get(il);
			}
		}
		return b;
	}

	public int getHeight() { return height;}
	
	public List<Box>  getTextBoxes(Block block, int screenWidth){
		
		for( int i = 0 ; i < block.images.size() ; i++ ){
			Image img = block.images.get(i);
			if( img.isLeft()){
				addL(img.box.w, img.box.h );
				img.box.x = left.get(left.size()-1).x;
				img.box.y = left.get(left.size()-1).y;
//				System.out.println( ">>"+img.align);
			}
			else {
				addR(img.box.w,  img.box.h );
				img.box.x = rt.get(rt.size()-1).x;
				img.box.y = rt.get(rt.size()-1).y;
//				System.out.println( "<>>"+img.align);
			}
		}
//		for(Image img : block.images){
//			System.out.println( "laid out box : "+ img.box.toString());
//		}
		List<YSegment> lls 		= getIntercepts(left, 0 , 0,  true);
		List<YSegment> rls 		= getIntercepts(rt  , 0 , screenWidth, false);
		
		List<Box> textBoxes 	= pinchOff(lls, rls);
//		for( Box bb : textBoxes  ){
//			System.out.println("[]"+bb.toString() );
//		}
		if( textBoxes.size() == 0){
			textBoxes.add(new Box(0,0,screenWidth,Integer.MAX_VALUE));
		}
		else {
			textBoxes.add(new Box(0, textBoxes.get(textBoxes.size()-1).y + textBoxes.get(textBoxes.size()-1).h, screenWidth,Integer.MAX_VALUE));
		} 
		//filter
		List<Box> ret = new ArrayList<Box>();
		for( Box box : textBoxes) {
			if( box.w > 0 && box.h > 0 ){
				ret.add(box);
			}
		}
//		for(Box bex : ret){ System.out.println("DEBUG:"+bex.toString());}
		return ret;
	}

	
	public static void main1(String[] args){
		
		BoxLayout boxes;
		boxes = new BoxLayout(300);

//		boxes.addL(100,100); boxes.addR(100,100); boxes.addR(100,100);
//		boxes.addR(100,100); boxes.addR(100,100); boxes.addR(100,140); boxes.addR(100,100);
//		boxes.addL(100,120); boxes.addR(100,120); boxes.addR(100,120);
		//these cases don't work !!!
//		boxes.addL(100,100);//boxes.addR(100,120);boxes.addR(100,120);

		List<YSegment> lls = boxes.getIntercepts(boxes.left, 0, 0,   true);
		List<YSegment> rls = boxes.getIntercepts(boxes.rt  , 0, 300, false);
		
//		for( YSegment y : lls  ){ System.out.println("L:"+y.start +","+y.end+"/"+y.x); }
//		System.out.println("--------------------");
//		for( YSegment y : rls  ){ System.out.println("R:"+y.start +","+y.end+"/"+y.x); }
		
//		System.out.println("--------------------");
		
		List<Box> emptyboxen = boxes.pinchOff(lls, rls);
		for( Box bb : emptyboxen  ){
//			System.out.println("[]"+bb.toString() );
		}
	}
}