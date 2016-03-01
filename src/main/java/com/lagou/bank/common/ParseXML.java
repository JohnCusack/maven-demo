/**
 * 
 */
package com.lagou.bank.common;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.DocumentHelper;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Peker
 * @time  2015年12月17日下午5:20:00
 */
public class ParseXML {

	/**
	 * w3c DOM
	 * 
	 * @author Peker
	 * @title parseWithDOM
	 * @param
	 * @return void
	 */
	public static void parseWithDOM(String protocolXML) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(protocolXML)));

			Element root = doc.getDocumentElement();
			NodeList books = root.getChildNodes();
			if (books != null) {
				for (int i = 0; i < books.getLength(); i++) {
					Node book = books.item(i);
					System.out.println("节点=" + book.getNodeName() + "\ttext=" + book.getFirstChild().getNodeValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Simple API for XML
	 * need to create Handler class, quick
	 * @author Peker
	 * @title parse
	 * @param
	 * @return void
	 */
	public static void parseWithSAX(String protocolXML) {

		try {
			SAXParserFactory saxfac = SAXParserFactory.newInstance();
			SAXParser saxparser = saxfac.newSAXParser();
			TestSAX tsax = new TestSAX();
			saxparser.parse(new InputSource(new StringReader(protocolXML)), tsax);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * need to download jar, slow
	 * @author  Peker
	 * @title   parseWithDOM4J   
	 * @param   
	 * @return  void
	 */
	public static void parseWithDOM4J(String protocolXML) {

		try {

			org.dom4j.Document doc = (org.dom4j.Document) DocumentHelper.parseText(protocolXML);
			org.dom4j.Element books = doc.getRootElement();
			System.out.println("根节点" + books.getName());
			// Iterator users_subElements =
			// books.elementIterator("UID");//指定获取那个元素
			Iterator<?> Elements = books.elementIterator();
			while (Elements.hasNext()) {
				org.dom4j.Element user = (org.dom4j.Element) Elements.next();
				System.out.println("节点" + user.getName() + "\ttext=" + user.getText());
				List<?> subElements = user.elements();
				// List user_subElements = user.elements("username");指定获取那个元素
				// System.out.println("size=="+subElements.size());
				// for( int i=0;i<subElements.size();i++){
				// Element ele = (Element)subElements.get(i);
				// System.out.print(ele.getName()+" : "+ele.getText()+" ");
				// }
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * additional jar
	 * @author  Peker
	 * @title   parse   
	 * @param   
	 * @return  void
	 */
	public static void parseWithJDOM(String protocolXML) {
		SAXBuilder builder = new SAXBuilder(false);
		try {
			org.jdom.Document doc = builder.build(new InputSource(new StringReader(protocolXML)));
			org.jdom.Element eles = doc.getRootElement(); // 得到根元素
			System.out.println("根节点" + eles.getName());

			List<?> list = eles.getChildren(); // 得到元素的集合
			// List studentList = students.getChildren("student"); //
			// 得到指定元素（节点）的集合

			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					org.jdom.Element book = (org.jdom.Element) list.get(i);
					System.out.println("节点=" + book.getName() + "\ttext=" + book.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}

class TestSAX extends DefaultHandler {

	private StringBuffer buf;
	private String str;

	public TestSAX() {
		super();
	}

	public void startDocument() throws SAXException {
		buf = new StringBuffer();
		System.out.println("*******开始解析XML*******");
	}

	public void endDocument() throws SAXException {
		System.out.println("*******XML解析结束*******");
	}

	public void endElement(String namespaceURI, String localName, String fullName) throws SAXException {
		str = buf.toString();
		System.out.println("节点=" + fullName + "\tvalue=" + buf + " 长度=" + buf.length());
		System.out.println();
		buf.delete(0, buf.length());
	}

	public void characters(char[] chars, int start, int length) throws SAXException {
		// 将元素内容累加到StringBuffer中
		buf.append(chars, start, length);
	}
}
