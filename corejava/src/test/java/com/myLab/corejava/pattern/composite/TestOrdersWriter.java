package com.myLab.corejava.pattern.composite;

import org.junit.Assert;
import org.junit.Test;

import com.myLab.corejava.pattern.composite.OrdersWriter.Order;
import com.myLab.corejava.pattern.composite.OrdersWriter.Orders;
import com.myLab.corejava.pattern.composite.OrdersWriter.Product;

public class TestOrdersWriter {

	/**
	 * 
<orders>
	<order id='250'>
		<product id='123' size='ok'>
			<price currency='USD'>
			8.95
			</price>
			测试产品
		</product>
	</order>
</orders>
	 */
	@Test
	public void testOrdersXml(){
		OrdersWriter ow = new OrdersWriter();
		//对每个节点分别定义对象，就会导致实例化上的复杂性，如果节点间的关系变化，代码修改也会相对复杂
		Orders orders = ow.new Orders();
		ow.setOrders(orders);
		
		Order o = ow.new Order();
		o.setOrderId(250);
		orders.addOrder(o);
		
		Product p = ow.new Product();
		p.setCurrency("USD");
		p.setId(123);
		p.setName("测试产品");
		p.setPrice(8.95);
		p.setSize("ok");
		o.addProduct(p);
		
		System.out.println(ow.getContents());
	}
	
	@Test
	public void testTagNode(){
		//通过分析xml的结构，发现节点可以抽象为由名称，属性，值组成的对象
		//java代码的重构，一个很重要的能力是通过对需求或结果的分析，能够尽量将共性的东西抽象出来
		TagNode priceTag = new TagNode("price");
		priceTag.addAttribute("currency", "USD");
		priceTag.addValue("" + 8.95);
		String expected = "<price currency='" + "USD" + "'>" 
				+ 8.95 + "</price>";
		Assert.assertEquals("price XML", expected, priceTag.toString());
	}
	
	@Test
	public void testCompositeTagOneChild(){
		TagNode productTag = new TagNode("product");
		productTag.add(new TagNode("price"));
		String expected = 
				"<product>" +
				"<price>" + 
				"</price>" + 
				"</product>";
		Assert.assertEquals("price XML", expected, productTag.toString());
	}
	
	@Test
	public void testCompositeTagTwoChild(){
		TagNode productTag = new TagNode("product");
		productTag.add(new TagNode("price"));
		productTag.add(new TagNode("address"));
		String expected = 
				"<product>" +
				"<price>" + 
				"</price>" + 
				"<address>" + 
				"</address>" + 
				"</product>";
		Assert.assertEquals("price XML", expected, productTag.toString());
	}
	
	@Test
	public void testAddingChildrenAndGrandchildren(){
		TagNode ordersTag = new TagNode("orders");
		TagNode orderTag = new TagNode("order");
		TagNode productTag = new TagNode("product");
		TagNode priceTag = new TagNode("price");
		ordersTag.add(orderTag);
		orderTag.add(productTag);
		productTag.add(priceTag);
		String expected = 
				"<orders>" +
				"<order>" +
				"<product>" +
				"<price>" + 
				"</price>" + 
				"</product>" + 
				"</order>" + 
				"</orders>";
		Assert.assertEquals("price XML", expected, ordersTag.toString());
	}
	
	@Test
	public void testParents(){
		TagNode root = new TagNode("root");
		Assert.assertNull(root.getParent());
		
		TagNode childNode = new TagNode("child");
		root.add(childNode);
		Assert.assertEquals(root, childNode.getParent());
		Assert.assertEquals("root", childNode.getParent().getName());
	}
}
