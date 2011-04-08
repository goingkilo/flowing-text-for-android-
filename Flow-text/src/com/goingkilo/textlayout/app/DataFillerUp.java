package com.goingkilo.textlayout.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Color;
import android.graphics.Typeface;

import com.goingkilo.textlayout.data.Block;
import com.goingkilo.textlayout.data.Document;
import com.goingkilo.textlayout.data.FragmentFormat;
import com.goingkilo.textlayout.data.ImageBlock;
import com.goingkilo.textlayout.data.ImageFormat;
import com.goingkilo.textlayout.xml.Reader;
import com.goingkilo.textlayout.xml.Writer;

//this is a dummy class that i use for helping here and there.
public class DataFillerUp {


	static String font1			= null;
	static String font2			= null;
	static String font3 		= null;
	static String font4 		= null;

	public static void fillDoc(Document doc, int i) {
		String text1 = "GitHub Terms of Service By using the GitHub.com web site (“Service”), " +
						"or any services of GitHub Inc (“GitHub”), you are agreeing to be bound by the following " +
						"terms and conditions (“Terms of Service”). IF YOU ARE ENTERING INTO THIS AGREEMENT ON " +
						"BEHALF OF A COMPANY OR OTHER LEGAL ENTITY, YOU REPRESENT THAT YOU HAVE THE AUTHORITY TO" +
						" BIND SUCH ENTITY, ITS AFFILIATES AND ALL USERS WHO ACCESS OUR SERVICES THROUGH YOUR ACCOUNT" ;

		String text2 =  "TO THESE TERMS AND CONDITIONS, IN WHICH CASE THE TERMS “YOU” OR “YOUR” SHALL REFER TO " ;


		String text3 = "SUCH ENTITY, ITS AFFILIATES AND USERS ASSOCIATED WITH IT. IF YOU DO NOT HAVE SUCH " +
						"AUTHORITY, OR IF YOU DO NOT AGREE WITH THESE TERMS AND CONDITIONS, YOU MUST NOT ACCEPT THIS " +
						"AGREEMENT AND MAY NOT USE THE SERVICES.; GitHub reserves the right to update and change the Terms " +
						"of Service from time to time without notice. Any new features that augment or enhance the current " +
						"";

		Block b1 = new Block(true);
		b1.addFragment( text1, getFragment_helper(font1,  24, Typeface.NORMAL, Color.DKGRAY, 4));
		b1.addFragment( text2, getFragment_helper(font4, 24, Typeface.NORMAL, Color.GRAY, 5));
		b1.addFragment( text3, getFragment_helper(font1,  24, Typeface.NORMAL, Color.DKGRAY, 3));

		doc.setBlockPadding(20);	//between blocks
		doc.setTopMargin(20);
		doc.setLeftMargin(20);

		doc.addBlock(b1);

	}

	public static void fillDoc(Document doc) {

		String manMeasure = "Man is the measure of all things : of things which are, that they " +
							"are, and of things " +
							"which are not, that they are not";

		String text1 = "GitHub Terms of Service By using the GitHub.com web site (“Service”), " +
						"or any services of GitHub Inc (“GitHub”), you are agreeing to be bound by the following " +
						"terms and conditions (“Terms of Service”). IF YOU ARE ENTERING INTO THIS AGREEMENT ON " +
						"BEHALF OF A COMPANY OR OTHER LEGAL ENTITY, YOU REPRESENT THAT YOU HAVE THE AUTHORITY TO" +
						" BIND SUCH ENTITY, ITS AFFILIATES AND ALL USERS WHO ACCESS OUR SERVICES THROUGH YOUR ACCOUNT" ;

		String text2 =  "TO THESE TERMS AND CONDITIONS, IN WHICH CASE THE TERMS “YOU” OR “YOUR” SHALL REFER TO " ;


		String text3 = "SUCH ENTITY, ITS AFFILIATES AND USERS ASSOCIATED WITH IT. IF YOU DO NOT HAVE SUCH " +
						"AUTHORITY, OR IF YOU DO NOT AGREE WITH THESE TERMS AND CONDITIONS, YOU MUST NOT ACCEPT THIS " +
						"AGREEMENT AND MAY NOT USE THE SERVICES.; GitHub reserves the right to update and change the Terms " +
						"of Service from time to time without notice. Any new features that augment or enhance the current " +
						"";

		String text4 = "Violation of any of the terms below will result in the termination of " +
						"your Account. While GitHub prohibits such conduct and Content on the Service, you " +
						"understand and agree that GitHub cannot be responsible for the Content posted on the " +
						"Service and you nonetheless may be exposed to such materials. You agree to use the " +
						"Service at your own risk.;";

		String goethe = "This is the highest wisdom that I own; freedom and life are earned by " +
						"those alone who conquer them each day anew.";

		String text5 = "You must be 13 years or older to use this Service.";
		String text6 = "You must be a human. Accounts registered by “bots” or other automated methods are not permitted"; 
		String text7 = "You must provide your legal full name, a valid email address, and any other information requested in order to complete the signup process." ;
		String text8 = "Your login may only be used by one person - a single login shared by multiple people is not permitted. You may create separate logins for as many people as your plan allows.";
		String text9 = "You are responsible for maintaining the security of your account and password. GitHub cannot and will not be liable for any loss or damage from your failure to comply with this security obligation."; 

		//		String airplaneFont 		= "/sdcard/font/airplanes_in_the_night_sky.ttf";
		//		String rainFont 			= "/sdcard/font/ayearwithoutrain.ttf";
		//		String throwHandsUpFont 	= "/sdcard/font/throwmyhandsupintheair.ttf";
		//		String blockTypeFont 		= "/sdcard/font/Blockography.ttf";
		String imagePath 			= "/sdcard/AlexDiary/ADPic_small71011.jpg";
		//this one's around 80 x 80 pix or 1inc x 1 inc or 2.X x 2.X cm 

		Block b0 = new Block(true);
		b0.addFragment( text5, getFragment_helper(font1,  24, Typeface.NORMAL, Color.DKGRAY, 3));
		b0.addFragment( text6, getFragment_helper(font1,  24, Typeface.NORMAL, Color.GRAY, 3));
		b0.addFragment( text7, getFragment_helper(font1,  24, Typeface.NORMAL, Color.DKGRAY, 3));
		b0.addFragment( text8, getFragment_helper(font1,  24, Typeface.NORMAL, Color.GRAY, 3));
		b0.addFragment( text9, getFragment_helper(font1,  24, Typeface.NORMAL, Color.DKGRAY, 3));
		b0.addImage(imagePath, new ImageFormat(0,15));
		b0.addImage(imagePath, new ImageFormat(0,15));
		b0.addImage(imagePath, new ImageFormat(1,15));

		Block b1 = new Block(true);
		b1.addFragment( text1, getFragment_helper(font1,  24, Typeface.NORMAL, Color.DKGRAY, 4));
		b1.addFragment( text2, getFragment_helper(font4, 24, Typeface.NORMAL, Color.GRAY, 5));
		b1.addFragment( text3, getFragment_helper(font1,  24, Typeface.NORMAL, Color.DKGRAY, 3));

		b1.addImage(imagePath, new ImageFormat(1,5));
		b1.addImage(imagePath, new ImageFormat(0,5));
		b1.addImage(imagePath, new ImageFormat(1,5));

		ImageBlock b3 = new ImageBlock(imagePath, new ImageFormat(1,25));
		b3.hasBorder(true);
		//		b3.addImage(imagePath, new ImageFormat(1,5));

		Block b2 = new Block(true);
		b2.insert( manMeasure, 		getFragment_helper(null ,24,  Typeface.NORMAL, Color.MAGENTA, 3), 50);
		b2.addFragment(text4, 		getFragment_helper(font2,24,  Typeface.NORMAL,Color.BLUE, 3));
		b2.addFragment(goethe, 		getFragment_helper(font3,24,  Typeface.NORMAL,Color.RED, 3));

		b2.addImage(imagePath, new ImageFormat(1,5));
		b2.addImage(imagePath, new ImageFormat(1,5));

		doc.setBlockPadding(20);	//between blocks
		doc.setTopMargin(20);
		doc.setLeftMargin(20);

		doc.addBlock(b2);
		doc.addBlock(b3);
		doc.addBlock(b0);
		doc.addBlock(b3);
		doc.addBlock(b1);

		testDoc(doc);
	}

	private static FragmentFormat getFragment_helper(String a, int b, int c, int d, int e){
		FragmentFormat f = new FragmentFormat(a,b,c,d);
		f.setLineSpacing(e);
		return f;
	}

	private static void testDoc(Document doc) {
		try {

			FileOutputStream fos = new FileOutputStream(new File("/sdcard/ya/a.xml"));
			fos.write((new Writer().getXMLString(doc)).getBytes());
			fos.close();

			Document d = new Reader().readXMLString("file:///sdcard/ya/a.xml");
			String s = new Writer().getXMLString(d);
			//			System.out.println("\n============\n");
			//			System.out.println(s);
			//			System.out.println("\n============\n");

			FileOutputStream fos1 = new FileOutputStream(new File("/sdcard/ya/b.xml"));
			fos1.write(s.getBytes());
			fos1.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
