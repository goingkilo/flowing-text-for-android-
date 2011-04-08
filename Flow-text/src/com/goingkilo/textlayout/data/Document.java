package com.goingkilo.textlayout.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Document extends Observable {

	public static final int LEFT 	= 0;
	public static final int RIGHT 	= 1;
	
	private int blockPad 	= 0;
	private int leftMargin 	= 0;
	private int topMargin 	= 0;
	
	
	public List<Block> blocks;
	
	public Document() {
		this.blocks = new ArrayList<Block>();
	}
	
	public Document(List<Block> blocks) {
		this.blocks = blocks;
	}

	public void addBlock(Block b){
		this.blocks.add(b);
		notifyObservers();
	}
	
	public void removeBlock(int i){
		if(i < blocks.size() ){
			blocks.remove(i);
		}
		fireChanged();
	}
	
	public Block get(int i){
		return blocks.get(i);
	}
	
	public int size(){
		return blocks.size();
	}

	public int getBlockPadding() {
		return blockPad;
	}

	public void setBlockPadding(int blockSpacing) {
		this.blockPad = blockSpacing;
	}

	public int getLeftMargin() {
		return leftMargin;
	}
	public int getTopMargin() {
		return topMargin;
	}
	public void setLeftMargin(int i) {
		this.leftMargin = i;
	}
	public void setTopMargin(int i) {
		this.topMargin = i;
	}

	public void clearChangedFlag() {
		clearChanged();
	}
	
	public void fireChanged(){
		setChanged();
		notifyObservers();
		clearChanged();
	}
	
}
