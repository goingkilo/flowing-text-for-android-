package com.goingkilo.textlayout.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.goingkilo.textlayout.data.Block;
import com.goingkilo.textlayout.data.Document;
import com.goingkilo.textlayout.data.Fragment;
import com.goingkilo.textlayout.data.FragmentFormat;
import com.goingkilo.textlayout.data.Image;
import com.goingkilo.textlayout.data.ImageBlock;
import com.goingkilo.textlayout.data.ImageFormat;


public class Reader {

	private static final String 	DOCUMENT 		= "DOCUMENT";
	private static final String 	BLOCK 			= "BLOCK";
	private static final String 	IMAGE 			= "IMAGE";
	private static final String 	IMAGEFORMAT 	= "IMAGEFORMAT";
	private static final String 	IMAGEBLOCK 		= "IMAGEBLOCK";
	private static final String 	FRAGMENT 		= "FRAGMENT";
	private static final String  	FRAGMENTFORMAT  = "FRAGMENTFORMAT";

	private static final String 	FRAG_DATA 			= "DATA";
	private static final String 	FRAGFMT_COLOR 		= "COLOR";
	private static final String 	FRAGFMT_STYLE 		= "STYLE";
	private static final String 	FRAGFMT_TEXTSIZE 	= "TEXTSIZE";
	private static final String 	FRAGFMT_TYPEFACE 	= "TYPEFACE";

	private static final String 	IMG_PATH 			= "PATH";
	private static final String 	IMGFMT_ALIGN 		= "ALIGN";
	private static final String 	IMGFMT_PADDING 		= "PADDING";

	public Document readXMLString(String xmlfile){
		if( !xmlfile.startsWith("file://")){
			xmlfile = "file://"+xmlfile;
		}
		Document doc = new Document();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try  {
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document dom = builder.parse( xmlfile );
			dom.getDocumentElement().normalize();

			NodeList entry = dom.getElementsByTagName(DOCUMENT);
			NodeList n = entry.item(0).getChildNodes();

			for( int i = 0 ; i < n.getLength() ; i++ ){
				Node n1 = n.item(i);

				if( n1.getNodeType() == Node.ELEMENT_NODE ){

					String name = n1.getNodeName();
					String value = null;
					try {
						value = n1.getChildNodes().item(0).getNodeValue();
					}
					catch(NullPointerException noe){
						continue;
					}

					if (name.equalsIgnoreCase(BLOCK)) {
						doc.addBlock( getBlock(n1) );
					}
					else if (name.equalsIgnoreCase(IMAGEBLOCK)) {
						doc.addBlock( getImageBlock(n1) );
					}
				}
			}
		}
		catch (Exception e) { e.printStackTrace();}
		return doc;
	}

	private ImageBlock getImageBlock(Node n2) {
//		System.out.println("getImageBlock");
		NodeList n = n2.getChildNodes();

		for( int i = 0 ; i < n.getLength() ; i++ ){
			Node n1 = n.item(i);

			if( n1.getNodeType() == Node.ELEMENT_NODE ){

				String name = n1.getNodeName();
				String value = null;
				try {
					value = n1.getChildNodes().item(0).getNodeValue();
				}
				catch(NullPointerException noe){
					continue;
				}
				if( name.equalsIgnoreCase( IMAGE) ){
					return new ImageBlock(getImage(n1));
				}
			}
		}
		return null;
	}

	private Block getBlock(Node n1) {
//		System.out.println("getBlock");
	
		Block b = new Block();
		
		NodeList nl = n1.getChildNodes();
		for( int i = 0 ; i < nl.getLength() ; i++ ){

			if( nl.item(i).getNodeType() != Node.ELEMENT_NODE ) continue;
			else {
				String name = nl.item(i).getNodeName();
				String val  = nl.item(i).getChildNodes().item(0).getNodeValue();			

				if( name.equalsIgnoreCase(FRAGMENT) ){
					b.addFragment( getFragment(nl.item(i)) );
				}
				else if ( name.equalsIgnoreCase(IMAGE) ){
					b.addImage( getImage(nl.item(i)) );
				}
			}
		}
		return b;
	}

	private Image getImage(Node item) {
//		System.out.println("getImage");
		NodeList n = item.getChildNodes();

		String path = null;
		ImageFormat imgf = null;
		
		for( int i = 0 ; i < n.getLength() ; i++ ){
			Node n1 = n.item(i);

			if( n1.getNodeType() == Node.ELEMENT_NODE ){

				String name = n1.getNodeName();
				String value = null;
				try {
					value = n1.getChildNodes().item(0).getNodeValue();
				}
				catch(NullPointerException noe){
					continue;
				}
				if( name.equalsIgnoreCase(IMG_PATH) ){
					path = value;
				}
				else if( name.equalsIgnoreCase(IMAGEFORMAT) ){
					imgf = getImageFormat(n1);
				}
			}
		}
		return new Image(path,imgf);
	}

	private Fragment getFragment(Node item) {
//		System.out.println("getFragment");
		NodeList n = item.getChildNodes();

		FragmentFormat fft = null;
		String data = null;
		
		for( int i = 0 ; i < n.getLength() ; i++ ){
			Node n1 = n.item(i);

			if( n1.getNodeType() == Node.ELEMENT_NODE ){

				String name = n1.getNodeName();
				String value = null;
				try {
					value = n1.getChildNodes().item(0).getNodeValue();
				}
				catch(NullPointerException noe){
					continue;
				}
				if( name.equalsIgnoreCase(FRAG_DATA) ){
					data = value;
				}
				else if( name.equalsIgnoreCase(FRAGMENTFORMAT) ){
					fft = getFragmentFormat(n1);
				}
			}
		}
//		if( fft.getTypeface() == null ){
//			System.out.println("DEBUG:"+fft.getTypeface());
//		} else {
//			System.out.println(">>>>");
//		}
		return new Fragment(data,fft);
	}

	private ImageFormat getImageFormat(Node item) {
//		System.out.println("getImageFormat");
		NodeList n = item.getChildNodes();

		int padding = -1, align = -1;
		
		for( int i = 0 ; i < n.getLength() ; i++ ){
			Node n1 = n.item(i);

			if( n1.getNodeType() == Node.ELEMENT_NODE ){

				String name = n1.getNodeName();
				String value = null;
				try {
					value = n1.getChildNodes().item(0).getNodeValue();
				}
				catch(NullPointerException noe){
//					System.out.println("imageformat null pointer");
					noe.printStackTrace();
					continue;
				}
				if( name.equalsIgnoreCase(IMGFMT_ALIGN) ){
					align = Integer.parseInt(value);
//					System.out.println("::got align"+align);
				}
				else if( name.equalsIgnoreCase(IMGFMT_PADDING) ){
					padding = Integer.parseInt(value);
//					System.out.println("::got padding"+padding);
				}
			}
		}
		return new ImageFormat(align,padding);
	}

	private FragmentFormat getFragmentFormat(Node item) {
//		System.out.println("getFragmentFormat");
		NodeList n = item.getChildNodes();

		int color = -1,textsize = -1, style = -1;
		String typeface = null;
		
		for( int i = 0 ; i < n.getLength() ; i++ ){
			Node n1 = n.item(i);

			if( n1.getNodeType() == Node.ELEMENT_NODE ){

				String name = n1.getNodeName();
				String value = null;
				try {
					value = n1.getChildNodes().item(0).getNodeValue();
				}
				catch(NullPointerException noe){
					continue;
				}
				if( name.equalsIgnoreCase(FRAGFMT_COLOR) ){
					color = Integer.parseInt(value);
				}
				else if( name.equalsIgnoreCase(FRAGFMT_STYLE) ){
					style = Integer.parseInt(value);
				}
				else if( name.equalsIgnoreCase(FRAGFMT_TEXTSIZE) ){
					textsize = Integer.parseInt(value);
				}
				else if( name.equalsIgnoreCase(FRAGFMT_TYPEFACE) ){
					typeface = value;
				}

			}
		}
		return new FragmentFormat(typeface,textsize,style,color);
	}

	public static void main1(String[] args){
		try {
			//			FileInputStream fis = new FileInputStream(new File("d:/kr/a.xml"));
			//			byte[] b  = new byte[fis.available()];
			//			fis.read(b);
			//			fis.close();
			//			String s = new String(b);
			new Reader().readXMLString("d:/kr/a.xml");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
