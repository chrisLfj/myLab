package com.myLab.corejava.pattern.builder;

import com.myLab.corejava.pattern.composite.TagNode;

/**
 * 利用构造器来封装TagNode实例化的复杂性，对外暴漏统一的方法。
 * @author chris
 *
 */
public class TagBuilder {

	private TagNode rootNode;
	private TagNode currentNode;
	
	public TagBuilder(String string) {
		rootNode = new TagNode(string);
		currentNode = rootNode;
	}

	/**
	 * 添加子节点
	 * @param name
	 */
	public void addChild(String name){
		addTo(currentNode, name);

	}
	
	/**
	 * 添加兄弟节点
	 * @param name
	 */
	public void addSibling(String name) {
		addTo(currentNode.getParent(), name);
	}
	
	
	private void addTo(TagNode parentNode, String tagName) {
		currentNode = new TagNode(tagName);
		parentNode.add(currentNode);		
	}

	public String toXml() {
		return rootNode.toString();
	}

	public void addToParent(String parentName, String childName) {
//		TagNode parent = null;
//		while(true){
//			parent = currentNode.getParent();
//			if(null == parent){
//				System.out.println("something wrong!");
//				break;
//			}
//			if(parent.getName().equals(parentName)){
//				addSibling(childName);
//				break;
//			}
//			else
//				currentNode = parent;
//		}
		TagNode parent = findParentBy(parentName);
		if(null == parent)
			throw new RuntimeException("missing parent tag: " + parentName);
		addTo(parent, childName);
	}

	private TagNode findParentBy(String parentName) {
		TagNode parentNode = currentNode;
		while(parentNode != null){
			if(parentNode.getName().equals(parentName))
				return parentNode;
			else
				parentNode = parentNode.getParent();
		}
		return null;
	}

}
