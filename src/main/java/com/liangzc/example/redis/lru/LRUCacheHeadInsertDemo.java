package com.liangzc.example.redis.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * lru头插法demo
 */
public class LRUCacheHeadInsertDemo {

    private final int capacity;
    private final Map<String, NodeDemo> nodeDemoMap;
    private NodeDemo head;
    private NodeDemo tail;

    public LRUCacheHeadInsertDemo(int capacity) {
        this.capacity = capacity;
        nodeDemoMap = new HashMap<>(capacity);
        head = new NodeDemo();
        tail = new NodeDemo();
        //形成双向链表
        head.next = tail;
        tail.prev = head;
    }



    //获取key
    public String getKey(String key){
        NodeDemo nodeDemo = nodeDemoMap.get(key);
        if (nodeDemo == null){
            return null;
        }
        //获取数据后，需要将该node节点进行移动到头部节点
        moveNodeToHead(nodeDemo);
        return nodeDemo.value;
    }


    public void put(String key,String value){
        NodeDemo nodeDemo = nodeDemoMap.get(key);
        //该node不存在，需要添加进链表
        if (nodeDemo == null){
            if (nodeDemoMap.size() > capacity){//需要将尾部Node移除
                deleteNode(tail);
                nodeDemoMap.remove(tail.key);
            }
            //将Node节点添加到头部
            nodeDemo = new NodeDemo(key,value);
            addNodeToHead(nodeDemo);
            nodeDemoMap.put(key, nodeDemo);
        }
        //node存在就触发一次，将node节点移动到头部
        nodeDemo.value = value;
        moveNodeToHead(nodeDemo);
    }


    private void moveNodeToHead(NodeDemo nodeDemo) {

        //1、先删除该node节点
        deleteNode(nodeDemo);

        //2、将该node节点添加到头部
        addNodeToHead(nodeDemo);
    }

    private void addNodeToHead(NodeDemo nodeDemo) {
        nodeDemo.next = head.next;
        head.prev.next = nodeDemo;
        head.next = nodeDemo;
        nodeDemo.prev = head;
    }

    private void deleteNode(NodeDemo nodeDemo) {
        if (nodeDemo == tail){
            tail = tail.prev;
            tail.next = null;
        }else if (nodeDemo == head){
            head = head.next;
            head.prev = null;
        }else {
            nodeDemo.prev.next = nodeDemo.next;
            nodeDemo.next.prev = nodeDemo.prev;
        }
    }


    class NodeDemo{
        private String key;
        private String value;
        NodeDemo prev;
        NodeDemo next;

        public NodeDemo() {
        }

        public NodeDemo(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
