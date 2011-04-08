package com.goingkilo.textlayout.textlayout;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.goingkilo.textlayout.boxlayout.Box;
import com.goingkilo.textlayout.boxlayout.BoxLayout;
import com.goingkilo.textlayout.data.Block;
import com.goingkilo.textlayout.data.BlockIterator;
import com.goingkilo.textlayout.data.Document;
import com.goingkilo.textlayout.data.Fragment;
import com.goingkilo.textlayout.data.Image;
import com.goingkilo.textlayout.data.ImageBlock;
import com.goingkilo.textlayout.screen.ScreenLineInfo;

public class Layouter {

	public static String IMAGE_BLOCK_CLASS = "com.goingkilo.textlayout.data.ImageBlock";

	private Paint linePaint;
	
	private Document doc;

	private int documentFullHeight;
	private int drawWidth;

	private List<ScreenBlock> docBlocks;

	public Layouter(Document doc){
		this.doc = doc;

		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(Color.DKGRAY);
		linePaint.setStyle(Style.STROKE);
	}

	public int getDocumentFullHeight() {
		return documentFullHeight;
	}

	public void doLayout(int screenWidth){
		int xOffset = doc.getLeftMargin();
		int yOffset = doc.getTopMargin();

		drawWidth = screenWidth-2* doc.getLeftMargin();

		List<ScreenBlock> ret = new ArrayList<ScreenBlock>();
		List<Block> blocks = doc.blocks;

		for( Block block : blocks) {

			if( block.getClass().getName().equalsIgnoreCase(IMAGE_BLOCK_CLASS)){
				ScreenBlock screenBlock = new ScreenBlock(block, null , xOffset, yOffset, drawWidth);
				screenBlock.setHeight(((ImageBlock)block).getH()+ doc.getBlockPadding());
				yOffset += ((ImageBlock)block).getH() + doc.getBlockPadding();
				ret.add(screenBlock);
				continue;
			}

			BoxLayout boxLayout 	= new BoxLayout(drawWidth);
			List<Box> txtBoxes 		= boxLayout.getTextBoxes(block,drawWidth);

			BlockIterator bi 		= new BlockIterator(block);
			List<ScreenLineInfo>  lines = layoutSingleBlock(txtBoxes, bi);

			//offset is the height of the previous block	
			ScreenBlock screenBlock = new ScreenBlock(block, lines ,xOffset, yOffset, drawWidth);
			screenBlock.setHeight( screenBlock.getBlockHeight(boxLayout)+ doc.getBlockPadding());
			screenBlock.shiftLines();

			//update y offset for the next guy
			//get greater of last line or last box height
			yOffset += screenBlock.getHeight();
			yOffset += doc.getBlockPadding();

			ret.add(screenBlock);
		}

		//return the last offset
		documentFullHeight = yOffset;

		this.docBlocks = ret;
	}

	//add x,y,w,h, info
	private List<ScreenLineInfo> layoutSingleBlock(List<Box> boxes, BlockIterator bi){

		List<ScreenLineInfo> ret  = new ArrayList<ScreenLineInfo>();
		for ( int i = 0 ; i < boxes.size() ; i++ ) {
			Box b   = boxes.get(i);

			Box[] borrow = new Box[1];

			//let it borrow some h from the next box
			if( i != boxes.size()-1){
				borrow[0] = boxes.get(i+1);
			}

			List<ScreenLineInfo> a = fitTextToBox(b, bi, borrow);
			ret.addAll(a);
			if (bi.endOfBlock()) {
				return ret;
			}
		}
		return ret;
	}

	private List<ScreenLineInfo> fitTextToBox(Box  box , BlockIterator blockIterator, Box[] borrow){
		List<ScreenLineInfo> ret = new ArrayList<ScreenLineInfo>();

		int accHeight 		= 0;
		while(!blockIterator.endOfBlock()){

			ScreenLineInfo screenLineInfo = blockIterator.fillLine(box.w);

			//System.out.println( "DEBUG:"+ blockIterator.getString( 
			//					screenLineInfo.startFrag, 
			//					screenLineInfo.startOffset,
			//					screenLineInfo.endFrag,
			//					screenLineInfo.endOffset )
			//			);

			if(screenLineInfo.zeroWidth()){
				break;
			}
			accHeight += blockIterator.getHeight(screenLineInfo.startFrag, screenLineInfo.endFrag);

			if( accHeight > box.h){	
				blockIterator.reset(false);

				//special case effing up my neat generic code
				if( (accHeight - box.h) < borrow[0].h){
					//we borrow some from the next box
					int newWidth = (borrow[0].w > box.w) ? box.w : borrow[0].w;
					ScreenLineInfo tailScreenLineInfo = blockIterator.fillLine(newWidth);
					tailScreenLineInfo.x = box.x;
					tailScreenLineInfo.y = box.y + accHeight;
					ret.add(tailScreenLineInfo);

					//adjusting the next borrowed-from box downwards...
					borrow[0].y += (accHeight - box.h);
					borrow[0].h -= (accHeight - box.h);
				}
				break;
			}

			screenLineInfo.x = box.x;
			screenLineInfo.y = box.y+accHeight;
			//			System.out.println("DEBUG:"+scli.toString());
			ret.add(screenLineInfo);
		}
		return ret;
	}

	public void draw(Canvas canvas){
		for( ScreenBlock scb : docBlocks ){
			if( scb.block.getClass().getName().equalsIgnoreCase("com.goingkilo.textlayout.data.ImageBlock")){
				handleImageBlock(canvas,scb);
			}
			else {
				handleBlock(canvas,scb);
			}
		}		
	}

	private void handleBlock(Canvas canvas2, ScreenBlock scb) {
		//		System.out.println("*******************************************");
		drawLines(canvas2, scb);
		//		System.out.println("===========================================");
		drawImages(canvas2, scb);
		//		drawBorder(canvas2, scb);
	}

	private void drawLines(Canvas canvas, ScreenBlock scb) {
		if(scb.lines == null) {
			return;
		}

		for( ScreenLineInfo sclinfo : scb.getLines() ){
			int x = sclinfo.x;
			int y = sclinfo.y;
			for( int i = sclinfo.startFrag ; i <= sclinfo.endFrag ; i++ ){

				Fragment f = scb.getBlock().fragments.get(i);
				String s = null;
				if(sclinfo.startFrag ==  sclinfo.endFrag){
					s = f.data.substring(sclinfo.startOffset,sclinfo.endOffset);
				}
				else if( i == sclinfo.startFrag) {
					s = f.data.substring(sclinfo.startOffset);
				}
				else if( i == sclinfo.endFrag){
					s = f.data.substring(0,sclinfo.endOffset);
				}
				else{
					s = f.data;
				}

				//canvas.drawLine(x, y , x + (int) f.paint.measureText(s), y , linePaint);
				//				System.out.println("         "+s);
				canvas.drawText(s, x, y , f.paint);
				x += f.paint.measureText(s);
			}
		}
	}

	private void drawImages(Canvas canvas, ScreenBlock scb) {
		if(scb.images == null) {
			return;
		}

		for( int i = 0 ; i < scb.getBlock().images.size() ; i++ ){
			Image img = scb.getBlock().images.get(i);
			Box b = img.box;

			//			canvas.drawRect(b.x +img.getPadding(), b.y + img.getPadding(), 
			//							b.x + b.w, b.y+b.h, 
			//							linePaint);
			canvas.drawBitmap( img.bmp, b.x+ img.getPadding(), b.y+img.getPadding()
					,linePaint);
		}
	}

	private void drawBorder(Canvas canvas2, ScreenBlock scb) {
		if (scb.block.hasBorder()){
			int x = scb.getXOffset();
			int y = scb.getYOffset();
			int x1 = x + scb.getWidth();
			int y1 = y + scb.getHeight();

			canvas2.drawRect( x,y, x1,y1, linePaint);
			System.out.println("BORDER:("+x+","+y+","+x1+","+y1+")");
		}
	}

	private void handleImageBlock(Canvas canvas, ScreenBlock scb ) {
		Image img = scb.block.images.get(0);
		int x = drawWidth/2 - img.bmp.getWidth()/2;
		int y = scb.getYOffset() + img.getPadding();
		canvas.drawBitmap(img.bmp, x, y, linePaint);
	}

	public Document getDoc() {
		return doc;
	}

}
