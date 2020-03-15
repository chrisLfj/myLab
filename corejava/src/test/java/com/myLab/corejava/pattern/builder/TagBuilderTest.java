package com.myLab.corejava.pattern.builder;

import org.junit.Assert;
import org.junit.Test;

public class TagBuilderTest {

	@Test
	public void testBuildOneNode(){
		String expectedXml = "<flavors></flavors>";
		String actualXml = new TagBuilder("flavors").toXml();
		Assert.assertEquals(expectedXml, actualXml);
	}
	
	@Test
	public void testBuildOneChild(){
		String expectedXml = "<flavors><flavor></flavor></flavors>";
		TagBuilder builder = new TagBuilder("flavors");
		builder.addChild("flavor");
		String actualXml = builder.toXml();
		Assert.assertEquals(expectedXml, actualXml);
	}
	
	@Test
	public void testBuildOneGrandChild(){
		String expectedXml = "<flavors><flavor><requirements></requirements></flavor></flavors>";
		TagBuilder builder = new TagBuilder("flavors");
		builder.addChild("flavor");
		builder.addChild("requirements");
		String actualXml = builder.toXml();
		Assert.assertEquals(expectedXml, actualXml);
	}
	
	@Test
	public void testBuildSibling(){
		String expectedXml = 
				"<flavors>" + 
				"<flavor1>" + 
				"</flavor1>" + 
				"<flavor2>" + 
				"</flavor2>" + 
				"</flavors>";
		TagBuilder builder = new TagBuilder("flavors");
		builder.addChild("flavor1");
		builder.addSibling("flavor2");
		String actualXml = builder.toXml();
		Assert.assertEquals(expectedXml, actualXml);
	}
	
	@Test
	public void testRepeatingChildrenAndGrandchildren() {
		String expectedXml = 
				"<flavors>" + 
				"<flavor>" + 
					"<requirements>" + 
						"<requirement>" +
						"</requirement>" +
					"</requirements>" + 
				"</flavor>" + 
				"<flavor>" + 
					"<requirements>" + 
						"<requirement>" +
						"</requirement>" +
					"</requirements>" + 
				"</flavor>" + 
				"</flavors>";
		
		TagBuilder builder = new TagBuilder("flavors");
		for(int i = 0; i < 2; i++){
			builder.addToParent("flavors", "flavor");
			builder.addChild("requirements");
			builder.addChild("requirement");
		}
//		builder.addChild("flavor");
//		builder.addChild("requirements");
//		builder.addChild("requirement");
//		builder.addToParent("flavors", "flavor");
//		builder.addChild("requirements");
//		builder.addChild("requirement");
		String actualXml = builder.toXml();
		Assert.assertEquals(expectedXml, actualXml);		
	}
	
	@Test
	public void testParentNameNotFound() {
		TagBuilder builder = new TagBuilder("flavors");
		try{
			for(int i = 0; i < 2; i++){
				builder.addToParent("favors", "flavor");
				builder.addChild("requirements");
				builder.addChild("requirement");
			}
			Assert.fail("should not allow adding to parent that doesn't exist.");
		}catch(Exception e){
			String expected = "missing parent tag: favors";
			Assert.assertEquals(expected, e.getMessage());
		}
	}
}
