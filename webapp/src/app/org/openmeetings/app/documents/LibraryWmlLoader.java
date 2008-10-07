package org.openmeetings.app.documents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class LibraryWmlLoader {
	
	private static final Logger log = LoggerFactory.getLogger(LibraryWmlLoader.class);
	
	private static final String fileExt = ".wml";
	
	private static final String wmlFolderName = "stored/";
	
	private static LibraryWmlLoader instance;

	private LibraryWmlLoader() {}

	public static synchronized LibraryWmlLoader getInstance() {
		if (instance == null) {
			instance = new LibraryWmlLoader();
		}
		return instance;
	}

	public LinkedHashMap loadWmlFile(String filePath, String fileName){
		try {
			LinkedHashMap lMap = new LinkedHashMap();
			String filepathComplete = filePath+wmlFolderName+fileName+fileExt;
			
			log.error("filepathComplete: "+filepathComplete);
			
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.NO_REFERENCES);
			
			BufferedReader reader = new BufferedReader(new FileReader(filepathComplete));
		    String xmlString = "";
		    while (reader.ready()) {
		    	xmlString += reader.readLine();
		    }
			
		    lMap = (LinkedHashMap) xStream.fromXML(xmlString);
			
			return lMap;
		} catch (Exception err){
			log.error("loadWmlFile",err);
		}
		
		return null;
		
	}
	
	/**
	 * @deprecated
	 * @param paintElement
	 * @param subMap
	 */
	private void createListObjectPaintByNode(Element paintElement, LinkedHashMap<Integer,Object> subMap){
		try {
			
			LinkedHashMap<Integer,LinkedHashMap> pointMap = new LinkedHashMap<Integer,LinkedHashMap>();
			Element pointElements = paintElement.element("points");
			Integer k = 0;
			
			for ( Iterator i = pointElements.elementIterator( "point" ); i.hasNext(); ) {
				Element pointElement = (Element) i.next();
				LinkedHashMap<Integer,Object> singlePoint = new LinkedHashMap<Integer,Object>();
				singlePoint.put(0, pointElement.getName());
				singlePoint.put(1, Integer.valueOf(pointElement.attribute("val1").getText()).intValue());
				singlePoint.put(2, Integer.valueOf(pointElement.attribute("val2").getText()).intValue());
				singlePoint.put(3, Integer.valueOf(pointElement.attribute("val3").getText()).intValue());
				singlePoint.put(4, Integer.valueOf(pointElement.attribute("val4").getText()).intValue());
				pointMap.put(k, singlePoint);
				log.debug("createListObjectPaintByNode",singlePoint);
				k++;
			}
			subMap.put(1, pointMap);

			subMap.put(2, paintElement.element("fillstyle").getText());
			subMap.put(3, Integer.valueOf(paintElement.element("linewidth").getText()).intValue());
			subMap.put(4, Integer.valueOf(paintElement.element("strokestyle").getText()).intValue());
			subMap.put(5, Integer.valueOf(paintElement.element("counter").getText()).intValue());
			subMap.put(6, Float.valueOf(paintElement.element("x").getText()).floatValue());
			subMap.put(7, Float.valueOf(paintElement.element("y").getText()).floatValue());
			subMap.put(8, Float.valueOf(paintElement.element("width").getText()).floatValue());
			subMap.put(9, Float.valueOf(paintElement.element("height").getText()).floatValue());
			subMap.put(10, paintElement.element("layername").getText());		
			
		} catch (Exception err) {
			log.error("createListObjectPaintByNode",err);
		}
	}
	
	/**
	 * @deprecated
	 * @param paintElement
	 * @param subMap
	 */
	public void createListObjectLetterByNode(Element paintElement, LinkedHashMap<Integer,Object> subMap){
		try {

			subMap.put(1, paintElement.element("textforfield").getText());
			subMap.put(2, Integer.valueOf(paintElement.element("fgcolor").getText()).intValue());
			subMap.put(3, Integer.valueOf(paintElement.element("fontsize").getText()).intValue());
			subMap.put(4, paintElement.element("fontstyle").getText());
			subMap.put(5, Integer.valueOf(paintElement.element("counter").getText()).intValue());
			subMap.put(6, Float.valueOf(paintElement.element("x").getText()).floatValue());
			subMap.put(7, Float.valueOf(paintElement.element("y").getText()).floatValue());			
			subMap.put(8, Float.valueOf(paintElement.element("width").getText()).floatValue());		
			subMap.put(9, Float.valueOf(paintElement.element("height").getText()).floatValue());		
			subMap.put(10, paintElement.element("layername").getText());
			
		} catch (Exception err) {
			log.error("createListObjectLetterByNode",err);
		}
	}	
	
	/**
	 * @deprecated
	 * @param paintElement
	 * @param subMap
	 */
	public void createListObjectImageByNode(Element paintElement, LinkedHashMap<Integer,Object> subMap){
		try {

			subMap.put(1, paintElement.element("urlname").getText());
			subMap.put(2, paintElement.element("baseurl").getText());
			subMap.put(3, paintElement.element("filename").getText());
			subMap.put(4, paintElement.element("modulename").getText());
			subMap.put(5, paintElement.element("parentpath").getText());
			subMap.put(6, paintElement.element("room").getText());
			subMap.put(7, paintElement.element("domain").getText());
			subMap.put(8, Integer.valueOf(paintElement.element("counter").getText()).intValue());
			subMap.put(9, Float.valueOf(paintElement.element("x").getText()).floatValue());
			subMap.put(10, Float.valueOf(paintElement.element("y").getText()).floatValue());
			subMap.put(11, Float.valueOf(paintElement.element("width").getText()).floatValue());
			subMap.put(12, Float.valueOf(paintElement.element("height").getText()).floatValue());
			subMap.put(13, paintElement.element("layername").getText());		
			
		} catch (Exception err) {
			log.error("createListObjectImageByNode",err);
		}
	}	
	
	/**
	 * @deprecated
	 * @param paintElement
	 * @param subMap
	 */
	public void createListObjectObjecByNode(Element paintElement, LinkedHashMap<Integer,Object> subMap){
		try {	
			
			subMap.put(1, paintElement.element("fillstyle").getText());
			subMap.put(2, paintElement.element("linewidth").getText());
			subMap.put(3, Integer.valueOf(paintElement.element("strokestyle").getText()).intValue());
			subMap.put(4, Float.valueOf(paintElement.element("startx").getText()).floatValue());
			subMap.put(5, Float.valueOf(paintElement.element("starty").getText()).floatValue());
			subMap.put(6, Float.valueOf(paintElement.element("endx").getText()).floatValue());
			subMap.put(7, Float.valueOf(paintElement.element("endy").getText()).floatValue());
			subMap.put(8, Integer.valueOf(paintElement.element("counter").getText()).intValue());
			subMap.put(9, Float.valueOf(paintElement.element("x").getText()).floatValue());
			subMap.put(10, Float.valueOf(paintElement.element("y").getText()).floatValue());
			subMap.put(11, Float.valueOf(paintElement.element("width").getText()).floatValue());
			subMap.put(12, Float.valueOf(paintElement.element("height").getText()).floatValue());
			subMap.put(13, paintElement.element("layername").getText());
			
		} catch (Exception err) {
			log.error("createListObjectObjecByNode",err);
		}
	}		
	
	/**
	 * @deprecated
	 * @param paintElement
	 * @param subMap
	 */
	public void createListObjectRectAndEllipseByNode(Element paintElement, LinkedHashMap<Integer,Object> subMap){
		try {	
			
			subMap.put(1, Integer.valueOf(paintElement.element("stroke").getText()).intValue());
			subMap.put(2, paintElement.element("line").getText());
			subMap.put(3, Integer.valueOf(paintElement.element("counter").getText()).intValue());
			subMap.put(4, Float.valueOf(paintElement.element("x").getText()).floatValue());		
			subMap.put(5, Float.valueOf(paintElement.element("y").getText()).floatValue());
			subMap.put(6, Float.valueOf(paintElement.element("width").getText()).floatValue());
			subMap.put(7, Float.valueOf(paintElement.element("height").getText()).floatValue());
			subMap.put(8, paintElement.element("layername").getText());

		} catch (Exception err) {
			log.error("createListObjectObjecByNode",err);
		}
	}
	
	/**
	 * @deprecated
	 * @param paintElement
	 * @param subMap
	 */
	public void createListObjectSWFByNode(Element paintElement, LinkedHashMap<Integer,Object> subMap){
		try {

			subMap.put(1, paintElement.element("urlname").getText());
			subMap.put(2, paintElement.element("baseurl").getText());
			subMap.put(3, paintElement.element("filename").getText());
			subMap.put(4, paintElement.element("modulename").getText());
			subMap.put(5, paintElement.element("parentpath").getText());
			subMap.put(6, paintElement.element("room").getText());
			subMap.put(7, paintElement.element("domain").getText());
			subMap.put(8, Integer.valueOf(paintElement.element("slideNumber").getText()).intValue());
			subMap.put(9, Float.valueOf(paintElement.element("innerx").getText()).floatValue());
			subMap.put(10, Float.valueOf(paintElement.element("innery").getText()).floatValue());
			subMap.put(11, Float.valueOf(paintElement.element("innerwidth").getText()).floatValue());
			subMap.put(12, Float.valueOf(paintElement.element("innerheight").getText()).floatValue());
			subMap.put(13, Integer.valueOf(paintElement.element("zoomlevel").getText()).intValue());
			subMap.put(14, Float.valueOf(paintElement.element("initwidth").getText()).floatValue());
			subMap.put(15, Float.valueOf(paintElement.element("initheight").getText()).floatValue());
			subMap.put(16, Integer.valueOf(paintElement.element("currentzoom").getText()).intValue());
			subMap.put(17, Integer.valueOf(paintElement.element("counter").getText()).intValue());
			subMap.put(18, Float.valueOf(paintElement.element("x").getText()).floatValue());
			subMap.put(19, Float.valueOf(paintElement.element("y").getText()).floatValue());
			subMap.put(20, Float.valueOf(paintElement.element("width").getText()).floatValue());
			subMap.put(21, Float.valueOf(paintElement.element("height").getText()).floatValue());
			subMap.put(22, paintElement.element("layername").getText());		
			
		} catch (Exception err) {
			log.error("createListObjectImageByNode",err);
		}
	}		
	
}
