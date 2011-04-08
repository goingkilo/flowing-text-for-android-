package com.goingkilo.textlayout.screen;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.goingkilo.textlayout.data.Document;
import com.goingkilo.textlayout.textlayout.Layouter;


public class DocumentViewer extends View implements Observer {

	private Scroller scroller;
	private Layouter layouter;

	public DocumentViewer(Context context, Document doc) {
		super(context);
		scroller = new Scroller();
		doc.addObserver(this);
		layouter = new Layouter(doc);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
//		System.out.println("onSizeCHanged *************"+layouter.getDoc().get(0).fragments.get(0).data);
		layouter.doLayout( w );
		scroller.setHeights(h , layouter.getDocumentFullHeight());
	}

	@Override
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		layouter.draw(canvas);
	}
	
	
	/* scroll strategy implemented. for pre scroller painter, see backup 15 mar 2.45 pm */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int y = (int)e.getY();
		
		switch (e.getAction()){

		case MotionEvent.ACTION_DOWN :
			scroller.fingerDown(y);
			break;
		
		case MotionEvent.ACTION_MOVE :
			int s = scroller.fingerMove(y);
			if( s != 0 ){
				scrollBy(0,s);
			}
			break;
			
		case MotionEvent.ACTION_UP :
			scroller.reset();
			break;
		
		}	
		return true;
	}

	@Override
	public void update(Observable observable, Object data) {
//		System.out.println("hello world");
		layouter.doLayout( getWidth() );
		scroller.setHeights(getHeight() , layouter.getDocumentFullHeight());
		invalidate();
//		layouter.getDoc().clearChangedFlag();
		
	}
}	