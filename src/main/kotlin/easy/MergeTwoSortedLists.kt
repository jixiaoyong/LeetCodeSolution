package easy

import ListNode
import Utils.createLinkedList
import Utils.printLinkedList

/*
* @description: 21. 合并两个有序链表
* 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
* https://leetcode-cn.com/problems/merge-two-sorted-lists/
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2021/12/20
*/
class MergeTwoSortedLists {
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        if (list1 == null) return list2
        if (list2 == null) return list1

        // head of list
        var head: ListNode? = null
        // point of current list
        var current: ListNode? = null

        var list1Head = list1
        var list2Head = list2

        while (list1Head != null && list2Head != null) {
            val min = if (list1Head.`val` < list2Head.`val`) {
                val listFirst = list1Head
                list1Head = list1Head?.next
                listFirst
            } else {
                val listFirst = list2Head
                list2Head = list2Head?.next
                listFirst
            }

            // current point to the end of list
            if (head == null) {
                head = min
                current = head
            } else {
                current?.next = min
                current = min
            }
        }

        if (list1Head != null) {
            current?.next = list1Head
        } else if (list2Head != null) {
            current?.next = list2Head
        }

        return head
    }
}

fun main() {
    val list1 = createLinkedList("[0,1,5,7,8,9,33,45,48,323]")
    val list2 = createLinkedList("[3,65,72,272]")
    val result = MergeTwoSortedLists().mergeTwoLists(list1, list2)
    printLinkedList(result)
}