package com.baidu.yuepingxu.demo.suanfa;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyueping
 * @date 2020-04-11
 * @describe 实现一个lrucache算法，最近最少使用放到最后，最近使用过的放到前面
 * 需要实现查找和插入的时间复杂度为O(1)
 * 分析：链表的插入时间复杂度为O(1)
 * 数组的查找时间复杂度为O(1)
 * 可以使用hashmap+双向链表实现
 * <p>
 * 1. put：根据key放到map中，如果没有，新建一个node放到链表第一个
 * 如果有，找到key对应的node更新value值，并且把node放到链表第一个位置
 * 如果容量满了，删掉最后一个，最近最少使用的
 * <p>
 * 2. get: 根据key得到一个node，并且把它放到第一个
 * <p>
 * <p>
 * key： 1                key： 2
 * <p>
 * value： 11  <----->   value： 22
 */
public class LruCache {
    public static void main(String[] args) {
        MyLruCache lruCache = new MyLruCache(3);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        LinkNode node0 = lruCache.get(1);
        LinkNode node1 = lruCache.put(4, 4);
        LinkNode node2 = lruCache.put(5, 5);
    }
}

class LinkNode {
    int key;
    int value;
    LinkNode preNode;
    LinkNode nextNode;

    public LinkNode(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class MyLruCache {
    // 初始容量
    int capacity;
    // 存放第一个
    LinkNode headNode;
    // 存放最后一个
    LinkNode tailNode;

    Map<Integer, LinkNode> map;

    public MyLruCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
    }


    // put方法 返回值是remove掉的最后一个
    public LinkNode put(int key, int value) {
        LinkNode removeNode = null;
        if (map.size() == capacity) {
            // 删除最后一个节点
            LinkNode tailPre = tailNode.preNode;
            tailNode.nextNode = null;
            tailNode.preNode = null;
            removeNode = tailNode;

            tailPre.nextNode = null;
            tailNode = tailPre;
            map.remove(tailNode.key);
        }
        if (map.size() == 0) {
            LinkNode node = new LinkNode(key, value);
            headNode = node;
            tailNode = node;
            map.put(key, node);
            return removeNode;
        }
        // 先判断是否在map中有同一个key的node
        LinkNode node = map.get(key);
        if (node == null) {
            // 如果不存在
            node = new LinkNode(key, value);
            // 需要放到第一个链表位置
            moveToHead(node);
            map.put(key, node);
        } else {
            node.value = value;
            moveToHead(node);
            map.put(key, node);
        }
        return removeNode;
    }

    private void moveToHead(LinkNode node) {
        if (!(node.preNode == null && node.nextNode == null)) {
            // 不是新插入的需要控制前后节点
            LinkNode pre = node.preNode;
            LinkNode next = node.nextNode;
            if (pre != null) {
                pre.nextNode = next;
            }

            if (next != null) {
                next.preNode = pre;
            } else {
                tailNode = pre;
            }

            node.nextNode = null;
            node.preNode = null;
        }

        node.nextNode = headNode;
        headNode.preNode = node;
        headNode = node;

    }


    // get方法
    public LinkNode get(int key) {
        LinkNode node = map.get(key);
        // 找到了node需要移动到前面
        if (node != null) {
            moveToHead(node);
        }
        return node;
    }

}
