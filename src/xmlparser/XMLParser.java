/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.io.File;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author jacob
 */
public class XMLParser {
    
    static XMLNode root;
    
    public static XMLNode load(File xmlFile) throws Exception {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
                Stack<XMLNode> stack;
                XMLNode currentNode = null;
                String currentElementName = null;
                String currentElementData = "";
                
                @Override
                public void startDocument() {
                    root = null;
                    stack = new Stack<XMLNode>();
                }
            
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    XMLNode nextNode = new XMLNode(qName);
                    int attributeLength = attributes.getLength();
                    for(int i = 0; i < attributeLength; i++) {
                        nextNode.setAttributes(attributes.getQName(i), attributes.getValue(i));
                    }
                    stack.push(nextNode);
                    
                    if(currentNode != null) {
                        currentNode.addProperty(qName, nextNode);
                    }
                    
                    currentNode = nextNode;
                }
                
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if(stack != null) {
                        XMLNode poppedNode = stack.pop();
                        poppedNode.setValue(poppedNode.getValue().trim());
                        if(stack.empty()) {
                            root = poppedNode;
                            currentNode = null;
                        } else {
                            currentNode = stack.lastElement();
                        }
                    }
                }
                
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    currentNode.setValue(currentNode.getValue() + String.valueOf(ch, start, length));
                }
            };
            parser.parse(xmlFile, handler);
        } catch (Exception ex) {
            throw ex;
        }
        return root;
    }
}
