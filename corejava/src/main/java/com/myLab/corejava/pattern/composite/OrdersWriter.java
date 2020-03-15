package com.myLab.corejava.pattern.composite;

import java.util.ArrayList;
import java.util.List;

public class OrdersWriter {
	private Orders orders;
	
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public String getContents(){
		StringBuffer xml = new StringBuffer();
		writeOrderTo(xml);
		return xml.toString();
	}
	
	private void writeOrderTo(StringBuffer xml) {
		//-----重构0代码-----
//		xml.append("<orders>");
//		for(int i = 0; i < orders.getOrderCount(); i++){
//			Order order = orders.getOrder(i);
//			xml.append("<order");
//			xml.append(" id='");
//			xml.append(order.getOrderId());
//			xml.append("'>");
//			writeProductsTo(xml, order);
//			xml.append("</order>");
//		}
//		xml.append("</orders>");
		//-----重构0代码-----
		//-----重构1代码，使用通用的叶子节点-----
		TagNode ordersTag = new TagNode("orders");
		for(int i = 0; i < orders.getOrderCount(); i++){
			Order order = orders.getOrder(i);
			TagNode orderTag = new TagNode("order");
			orderTag.addAttribute("id", "" + order.getOrderId());
			writeProductsTo(orderTag, order);
			ordersTag.add(orderTag);
		}
		xml.append(ordersTag.toString());
		//-----重构1代码-----
	}

	private void writeProductsTo(TagNode orderTag, Order order) {
		for(int i = 0; i < order.getProductCount(); i++){
			Product p = order.getProduct(i);
			TagNode productTag = new TagNode("product");
			productTag.addAttribute("id", "" + p.getId());
			if(p.getSize() != Product.NOT_APPLICABLE){
				productTag.addAttribute("size", "" + p.getSize());
			}
			
			writePriceTo(productTag, p);
			orderTag.add(productTag);
		}
	}

	private void writePriceTo(TagNode productTag, Product p) {
		TagNode priceNode = new TagNode("price");
		priceNode.addAttribute("currency", p.getCurrency());
		priceNode.addValue("" + p.getPrice());
		productTag.add(priceNode);
	}

	//-----重构0代码-----
//	private void writeProductsTo(StringBuffer xml, Order order) {
//		for(int i = 0; i < order.getProductCount(); i++){
//			Product p = order.getProduct(i);
//			xml.append("<product");
//			xml.append(" id='");
//			xml.append(p.getId());
//			xml.append("'");
//			if(p.getSize() != Product.NOT_APPLICABLE){
//				xml.append(" size='");
//				xml.append(p.getSize());
//				xml.append("'");
//			}
//			xml.append(">");
//			writePriceTo(xml, p);
//			xml.append(p.getName());
//			xml.append("</product>");
//		}
//	}
	//-----重构0代码-----

	private void writePriceTo(StringBuffer xml, Product p) {
		//重构1代码，使用隐式叶子节点
		TagNode priceNode = new TagNode("price");
		priceNode.addAttribute("currency", "USD");
		priceNode.addValue("" + 8.95);
		xml.append(priceNode.toString());
		//------重构0代码-----
//		xml.append("<price");
//		xml.append(" currency='");
//		xml.append(p.getCurrency());
//		xml.append("'>");
//		xml.append(p.getPrice());
//		xml.append("</price>");
		//------重构0代码-----
	}

	class Orders {

		private List<Order> orders = new ArrayList<Order>();
		public int getOrderCount() {
			return orders.size();
		}

		public Order getOrder(int i) {
			return orders.get(i);
		}
		
		public void addOrder(Order o){
			orders.add(o);
		}
	}
	
	class Order {

		private int orderId;
		private List<Product> ps = new ArrayList<Product>();
		
		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public int getOrderId() {
			return orderId;
		}

		public Product getProduct(int i) {
			return ps.get(i);
		}

		public int getProductCount() {
			return ps.size();
		}
		
		public void addProduct(Product p){
			ps.add(p);
		}
	}
	
	class Product {
		private final static String NOT_APPLICABLE = "no";
		
		private int id;
		private String name;
		private String currency;
		private String size;
		private Double price;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		public Double getPrice() {
			return price;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		
	}
}
