package com.goingkilo.textlayout.app;

import android.app.Activity;
import android.os.Bundle;

import com.goingkilo.textlayout.data.Document;
import com.goingkilo.textlayout.screen.DocumentViewer;

public class FlowText extends Activity {

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		
		Document doc = new Document();
		DataFillerUp.fillDoc(doc);
		DocumentViewer dViewer = new DocumentViewer(this,doc);
		setContentView(dViewer);

	}
}