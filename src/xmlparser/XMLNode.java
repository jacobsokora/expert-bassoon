/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jacob
 */
public class XMLNode {
    private String key;
    private String value = "";
    private Map<String, List<XMLNode>> properties;
    private Map<String, String> attributes;
    
    public XMLNode() {}
    
    public XMLNode(String key) {
        this.key = key;
        this.attributes = new HashMap<String, String>();
    }
    
    public XMLNode(String key, String value, Map<String, List<XMLNode>> properties) {
        this(key);
        this.value = value;
        this.properties = properties;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public void addProperty(String name, XMLNode node) {
        if(properties == null) {
            properties = new HashMap<String, List<XMLNode>>();
        }
        properties.putIfAbsent(name, new ArrayList<XMLNode>());
        properties.get(name).add(node);
    }
    
    public void deleteProperty(String name) {
        if(properties == null) {
            return;
        }
        properties.remove(name);
    }
    
    public boolean propertyExists(String name){
        if (properties == null){
            return false;
        }
        return this.properties.containsKey(name);
    }
    
    public List<XMLNode> getProperty(String name){
        return properties.get(name);
    }
    
    public Map<String, List<XMLNode>> getProperties(){
        return properties;
    }
    
    public void setAttributes(String key, String value){
        this.attributes.put(key, value);
    }
    
    public Map<String,String> getAttributes(){
        return attributes;
    }
    
    public int getAttributesLength(){
        return attributes.size();
    }
}
