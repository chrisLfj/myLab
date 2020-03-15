package com.myLab.corejava.pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐式叶子节点的通用类
 * @author chris
 *
 */
public class TagNode {
	private String name = "";
	private String value = "";
	private StringBuffer attributes;
	private List<TagNode> children;
	private TagNode parent;
	
	public TagNode(String name) {
		this.name = name;
		attributes = new StringBuffer("");
	}

	public void addAttribute(String attrName, String attrValue) {
		attributes.append(" ");
		attributes.append(attrName);
		attributes.append("='");
		attributes.append(attrValue);
		attributes.append("'");
	}

	public void addValue(String value) {
		this.value = value;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(name);
		sb.append(attributes);
		sb.append(">");
		if(children().size() > 0){
			for(TagNode childNode: children)
			sb.append(childNode.toString());
		}
		sb.append(value);
		sb.append("</");
		sb.append(name);
		sb.append(">");
		return sb.toString();
	}

	public void add(TagNode tagNode) {
		tagNode.setParent(this);
		children().add(tagNode);
	}
	
	private List children(){
		if(children == null){
			children = new ArrayList();
		}
		return children;
	}

	public TagNode getParent() {
		return parent;
	}

	public void setParent(TagNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public StringBuffer getAttributes() {
		return attributes;
	}

	public void setAttributes(StringBuffer attributes) {
		this.attributes = attributes;
	}

	public List<TagNode> getChildren() {
		return children;
	}

	public void setChildren(List<TagNode> children) {
		this.children = children;
	}

}
