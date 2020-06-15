package com.baidu.yuepingxu.demo.suanfa;

/**
 * @author xuyueping
 * @date 2020-03-26
 * @describe
 */
public class Test {
    public static void main(String[] args) {
//        int a[] = new int[]{100, 11, 4, 22, 3, 6, 5, 9, 5};
//        quickSort(a, 0, a.length - 1);
//        for (int t : a) {
//            System.out.println("" + t);
//        }

        Node node = new Node(new Node(new Node(new Node(null, 4),3),2),1);
        Node node1 = reverseLink1(node);
        while(node1 != null){
            System.out.println("" + node1.value);
            node1 = node1.next;
        }
    }

    // 快排，先选第一个为基准，把小于它的放左边，大于的放右边，这样得到两个子序列，在按照同样的方法对两个子序列进行操作
    private static void quickSort(int[] a, int left, int right) {
        int i = left;
        int j = right;
        if (left < right) {
            //基本思想：先和右边比较，找到小于基准的，替换两个位置，再和左边比较找到大于基准的替换位置
            while (left < right) {
                while (left < right && a[right] >= a[left]) {
                    right--;
                }
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;

                while (left < right && a[left] <= a[right]) {
                    left++;
                }
                temp = a[left];
                a[left] = a[right];
                a[right] = temp;
            }

            quickSort(a, i, left-1); // 递归左边
            quickSort(a, left+1, j); // 递归右边
        }
    }

    // 单向链表反转   1-2-3-4   2-1-3-4  3-2-1-4   4-3-2-1
    // 原理是先利用一个临时变量保存当前节点的下一个节点，比如当前节点是head，创建一个新节点newHead，
    // 步骤是temp指向head的next，head指向newHead，newHead指向head
    private static Node reverseLink(Node head){
        Node newHead = null;
        while(head != null){
            Node temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }
        return newHead;
    }

    // 递归
    private static Node reverseLink1(Node head){
        if(head == null || head.next == null){
            return head;
        }

        Node newHead = reverseLink1(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

}
class Node{
    Node next;
    int value;
    Node(Node next, int value){
        this.next = next;
        this.value = value;
    }
}


