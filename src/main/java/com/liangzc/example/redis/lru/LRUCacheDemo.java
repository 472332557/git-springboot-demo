package com.liangzc.example.redis.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * 手写lru算法
 *  hashMap + 双向链表构成，采用尾插法
 *  hashMap存储node节点的索引key，更快的定位到Node节点
 *  使用过的key，则向尾部移动，头部的节点Node会被淘汰（移除）
 */
public class LRUCacheDemo {

    private final Map<String, Node> hashMap;
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCacheDemo(int capacity) {
        this.capacity = capacity;
        hashMap = new HashMap<>();
        //初始化头部、尾部节点
        head = new Node();
        tail = new Node();
        //头和尾节点，互相指向,建立双向链表的关系
        head.next = tail;
        tail.prev = head;
    }

    //获取Node，则说明该key被使用了，需要将该Node节点向尾部移动，获取数据
    public String get(String key){
        Node node = hashMap.get(key);
        if (node == null){
            return null;
        }
        //刷新当前key的位置
        moveNode(node);
        return node.getValue();
    }

    //添加数据
    public void put(String key,String value){
        Node node = hashMap.get(key);
        if(node == null){//如果不存在，添加到链表
            if (hashMap.size() >= capacity){//超过容量的话，则需要移除老的数据
                removeNode(head);//移除头部节点（head节点是属于要被淘汰的数据）
                hashMap.remove(head.getKey());//从hashMap中移除
            }
            node = new Node(key,value);
            addNodeToTail(node);
            hashMap.put(key, node);
        }else {
            //node存在就触发一次，将node节点移动到尾部
            node.setValue(value);
            moveNode(node);
        }

    }


    /**
     * 移动节点分为两个步骤：1、删除Node 2、添加Node到尾部节点
     */
    public void moveNode(Node node){
        removeNode(node);
        addNodeToTail(node);
    }

    //删除节点
    public void removeNode(Node node){
        if (node == tail){
            tail = tail.prev;
            tail.next = null;
        }else if (node == head){
            head = head.next;
            head.prev = null;
        }else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    //添加节点到尾部，其实是添加到尾部节点的上一个节点
    public void addNodeToTail(Node node){
        node.prev = tail.prev;
        tail.prev.next = node;
        node.next = tail;
        tail.prev = node;
    }

}
