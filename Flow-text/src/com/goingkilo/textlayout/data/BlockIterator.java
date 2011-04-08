package com.goingkilo.textlayout.data;

import com.goingkilo.textlayout.screen.ScreenLineInfo;

public class BlockIterator {

	public Block block;
	
	public FragPointer start;
	public FragPointer end;

	public BlockIterator(Block block) {
		this(block, new FragPointer(0,0), new FragPointer(0,0));
	}
	
	public BlockIterator(Block block, int a, int b, int c, int d) {
		this(block, new FragPointer(a,b), new FragPointer(c,d));
	}
			
	public BlockIterator(Block block, FragPointer end, FragPointer start) {
		this.block = block;
		this.end   = end;
		this.start = start;
	}
	
	public boolean end(){
		if( end.fragnum > (block.dataLength()-1) )
			return true;
		if( end.fragnum == (block.dataLength()-1) && (end.offset>= block.fragments.get(block.dataLength()-1).data.length() ) )
				return true;
		return false;
	}
	
	//remember the time i went completely nuts ? here's the proof...
	/*
	public void fillBackupLine(int length, int[] pointers) {
		Fragment frag = null;
		reset(true);
		int l = length;
	
		while(true ){

			frag = currentFragment();
			int[] measured = new int[1];

			int index  =  frag.slice(l,end.offset,measured);
		

			//pure case ,doesn't happen always
			if(measured[0] == l){
//				store(pointers);
				return;
			}

			if( endOfFragment()){
				
				if(index == end.offset){
//					store(pointers);
					return;
				}
				else {
					if( endOfBlock() ){
//						store(pointers);
						return;
					}
					l -= measured[0];
					end.offset = index;
					moveToNextFragment();
					
					continue;
				}
			}
			else {
				if(index == end.offset){
					//store start,end pointers
					//end position is fine.
//					store(pointers);
					return;
				}
				end.offset = index;
				//if the man gets past the three guards, he is worthy
//				store(pointers);
				return;
			}
		}
	}
	*/
	
	//return the start and end fragpointers in int[]
	public ScreenLineInfo fillLine(int length) {
		Fragment frag = null;
		int availLength = length;
	
		reset(true);	
		
		while(!endOfBlock() && availLength != 0){
					
			frag = currentFragment();
			int[] measured = new int[1];

			//this index is from the offset
			int index  =  frag.slice(availLength,end.offset,measured);
//			System.out.println( "-------------[c]"+index);

			// if fragment used up, then go to next fragment
			// if fragment not used up, then go to next line
			// if index is shortened due to word breaks, then ignore next line
			if((end.offset+index) < frag.data.length()){  
				end.offset += index;
				return capturePointers();
			}
			
			availLength -= measured[0];
			moveToNextFragment();
		}
//		System.out.println("X3:"+measureString()+"/"+availLength);
//		System.out.println("X3:"+getString());
		return capturePointers();
	}

	private ScreenLineInfo capturePointers(){
		ScreenLineInfo scli = new ScreenLineInfo ();
		scli.startFrag   = start.fragnum;
		scli.startOffset = start.offset;
		if( end.fragnum >= block.fragments.size()) {
			scli.endFrag     = block.fragments.size()-1;
			scli.endOffset   = block.fragments.get(block.fragments.size()-1).data.length()-1;
		}
		else {
			scli.endFrag     = end.fragnum;
			scli.endOffset   = end.offset;
		}
		return scli;
	}

	private Fragment currentFragment(){
		return block.fragments.get(end.fragnum);
	}

	public boolean endOfBlock(){
		return (end.fragnum == (block.fragments.size()-1) &&  endOfFragment()) 
			   || 
			   (end.fragnum >= block.fragments.size())
			   ;
	}
	
	public boolean endOfFragment(){
		return (end.offset ==  (block.fragments.get(end.fragnum).data.length()-1) );
	}
	
	private void moveToNextFragment(){
		end.fragnum ++;
		end.offset = 0; 
	}
	
	public void reset(boolean forward){
		if( forward ){	//move start to end 
			start.fragnum = end.fragnum;
			start.offset = end.offset;
		}
		else { //move end to start
			end.fragnum  = start.fragnum;
			end.offset = start.offset;
		}
	}

	public String toString(){
		return "("+start.fragnum+"."+start.offset +" to " + end.fragnum + "." +end.offset +")";
	}
	
	public int startFragnum(){ return start.fragnum; }
	public int startOffset() { return start.offset; }
	public int endFragnum()  { return end.fragnum; }
	public int endOffset()   { return end.offset; }
	
	public int getHeight(int startFragnum, int endFragnum ){
		int fragnum      = startFragnum;
		
		int ht = 0;
		while(fragnum <=  endFragnum){
			int lht = block.fragments.get(fragnum).getHeight();
			ht = (lht > ht) ? lht : ht;
			fragnum ++;
		}
		return ht;
	}	

	//useful methods: currently not working properly
	public String getString(int s0, int s1, int e0 , int e1){

		if( s0 == e0 ){
			return block.fragments.get(s0).data.substring(s1,e1+1);
		}
		
		StringBuffer b = new StringBuffer();
		for( int i = s0 ; i <= e0 ; i++ ){

			Fragment f = block.fragments.get(i);
			String s = null;
			
			if( i == s0) {
				s = f.data.substring(s1);
			}
			else if( i == e0){
				s = f.data.substring(0,e1+1);
			}
			else{
				s = f.data;
			}
			b.append(s);
		}
		b.append("<END>");
		return b.toString();
	}
	
	public int measureString(){
		int measure = 0;
		for( int i = startFragnum() ; i <= endFragnum() ; i++ ){

			if( i == block.fragments.size())
				break;
			Fragment f = block.fragments.get(i);
			String s = null;
			if( i == startFragnum()) {
				s = f.data.substring(startOffset());
			}
			else if( i == endFragnum()){
				s = f.data.substring(0,endOffset());
			}
			else{
				s = f.data;
			}
			measure += f.paint.measureText(s);
		}
		return measure;
	}
}

class FragPointer{
	
	public int fragnum;
	public int offset;
	
	public FragPointer(int fragnum,int offset){
		this.fragnum = fragnum;
		this.offset = offset;
	}

	public boolean equals(FragPointer fp){
		return fragnum == fp.fragnum && offset == fp.offset;
	}
}

