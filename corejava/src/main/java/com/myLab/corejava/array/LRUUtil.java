package com.myLab.corejava.array;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 实现了LRU算法的工具类，使用LRU算法的本质是利用有限资源来实现快速的数据访问。
 * 数据访问：
 * 1.直接从存储中访问，数据容量大，但是访问效率低
 * 2.直接从内存中访问，数据容量小，但是访问效率高
 * 所谓LRU(Least Recently Used)最近最少使用，是指对某些热点数据如何能提升其访问效率而存在的数据结构和算法。
 * 我们知道距离cpu越近的存储介质，访问速度越快，但是访问速度越快的存储，其容量就越小，造价越昂贵。
 * 因此，为提升热点数据的访问速度，将这些热点数据存放到缓存或者内存中比较合适，但是由于容量的限制，就意味着不可能无限制的存放数据。
 * 这里就涉及到一个淘汰算法，即LRU，它本质上是指，一组数据中如果达到容量阈值，就将最近最少访问的元素淘汰掉。
 * 总结实现LRU算法的几个关键点:
 * 1.要有一组数据结构用于存放热点数据
 * 2.要高效的实现一种有序数据结构(按数据热度进行排序)，因为LRU最终淘汰的就是最近最少使用的那个
 * 3.要高效的查询某个数据
 * 4.要高效的删除，插入，更新某个数据
 * 基于上面的考虑，使用双向链表和HashMap来完成LRU算法，原因是
 * 1.HashMap可以高效的用Key来查询数据
 * 2.双向链表可以高效的删除，插入，更新某个数据
 * 3.双向链表用于实现LRU的淘汰算法，即新增数据插入头部，更新数据先删除原数据再插入头部，删除数据则直接从尾部删除，这样天然就会按照数据的热度来排序
 * 链表的几个算法：LRU算法，单链表反转，链表中环的检测，两个有序链表合并，删除链表倒数第n个节点，求链表的中间节点
 */
public class LRUUtil {

    private int currentSize;
    private int capcity;//总容量
    private HashMap<String, Node> caches;//存放所有的node节点
    private Node first;//双向链表的头节点
    private Node last;//双向链表的尾节点

    /**
     * 初始化数据结构
     * @param capcity
     */
    public LRUUtil(int capcity) {
        this.capcity = capcity;
        this.currentSize = 0;
        caches = new HashMap<String, Node>(capcity);
    }

    public Node get(String key){
        Node cacheNode = caches.get(key);
        if(cacheNode == null){
            if(caches.size() == capcity){
                removeTail(last);//删除尾节点
            }
            cacheNode = createNode(key);//创建一个node
            moveToHead(cacheNode);//将创建的新node插入链表头部
        }else{
            updateNode(cacheNode);//更新一个node的热度
        }
        return cacheNode;
    }

    private void updateNode(Node cacheNode) {
        if(first == cacheNode){
            return;//如果正好是头节点，那就什么也不做直接返回
        }
        if(last == cacheNode){
            removeTail(last);//如果正好是尾节点，那就调用删除尾节点方法，然后再插入到头节点
            cacheNode.pre = null;
            cacheNode.next = null;
            moveToHead(cacheNode);
            return;
        }
        deleteMidNode(cacheNode);//不是头节点也不是尾节点，就是中间节点，调用删除中间节点方法，然后再插入到头节点
        moveToHead(cacheNode);
        return;
    }

    /**
     * 删除中间节点之后肯定会调用moveToHead方法，这里不考虑将pre和next置为null，统一在moveToHead方法中处理
     * @param cacheNode
     */
    private void deleteMidNode(Node cacheNode) {
        caches.remove(cacheNode.key);//HashMap中删除原节点
        cacheNode.pre.next = cacheNode.next;
        cacheNode.next.pre = cacheNode.pre;
        return;
    }

    private void removeTail(Node last) {
        Node node = last;//创建一个临时节点引用原尾节点
        this.last = last.pre;//将尾节点指针指向的原尾节点的pre节点
        this.last.next = null;//新的尾节点的next节点置为null
        caches.remove(last.key);//HashMap中删除原尾节点
        node.pre = null;//原尾节点的pre，next都要置为null
        node.next = null;
        return;
    }

    private void moveToHead(Node cacheNode) {
        cacheNode.pre = null;//插入头节点的第一步是先将pre和next引用置为null
        cacheNode.next = null;
        caches.put(cacheNode.key, cacheNode);
        if(first == null){
            first = cacheNode;//如果first为null，说明双向链表为空，插入的第一个节点既是头节点又是尾节点
            last = cacheNode;
            return;
        }
        cacheNode.next = first;
        first.pre = cacheNode;
        first = cacheNode;
        return;
    }

    private Node createNode(String key) {
        //根据key值来得到value，创建出一个node节点，根据不同的场景，可能是从数据库中按照key来查到value，也可能是从redis等缓存中获得，这里只简单模拟获得一个value
        String value = "value = " + key;
        Node node = new Node(key, value);
        return node;
    }

    /*
        伪代码
        get(Node node){
        1.HashMap中按照node.key来查找是否存在改节点
        2.如果存在
            2.1链表中删除该节点
            2.2将该节点放入链表头部
        3.如果不存在
            判断数据结构是否达到容量阈值
            3.1如果达到容量阈值，则链表中删除尾节点，HashMap中也删除尾节点
            3.2如果没有达到容量阈值，则将其插入链表头节点，容量+1
        }

         */
    class Node{
        String key;//键
        String value;//存放的值
        Node pre;//上一个节点
        Node next;//下一个节点

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String toString(){
            return "pre:" + (pre == null ? "null" : pre.value) + " value: " + value + " next:" + (next == null ? "null" : next.value);
        }
    }

    public static void main(String[] args) {
        LRUUtil lruUtil = new LRUUtil(100);
        lruUtil.get("key1");
        lruUtil.get("key2");
        lruUtil.get("key3");
        lruUtil.get("key1");

        for(Map.Entry<String, Node> it : lruUtil.caches.entrySet()){
            System.out.println(it.getValue());
        }
    }
}
