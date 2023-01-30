package medium

import utils.ListNode
import utils.Utils

/**
 * @author : jixiaoyong
 * @description ：1669. 合并两个链表
 * 给你两个链表list1 和list2，它们包含的元素分别为n 个和m 个。
 *
 * 请你将list1中下标从 a 到 b 的全部节点都删除，并将list2接在被删除节点的位置。
 *
 * 请你返回结果链表的头指针。
 *
 * 提示：
 *
 * 3 <= list1.length <= 10^4
 * 1 <= a <= b < list1.length - 1
 * 1 <= list2.length <= 10^4
 *
 * 链接：https://leetcode.cn/problems/merge-in-between-linked-lists
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/30/2023
 */
class MergeInBetweenLinkedList {
    fun mergeInBetween(list1: ListNode?, a: Int, b: Int, list2: ListNode?): ListNode? {
        var curIndex = 1
        var preNode = list1
        var curNode = list1?.next
        while (curNode != null) {
            if (curIndex < a) {
                preNode = curNode
            } else if (curIndex == a) {
                preNode?.next = list2
                var curNode2 = list2
                while (curNode2?.next != null) {
                    curNode2 = curNode2.next
                }
                preNode = curNode2
            }
            // 可能有 a == b
            if (curIndex == b) {
                preNode?.next = curNode.next
                break
            }
            curNode = curNode.next
            curIndex++
        }

        return list1
    }
}

fun main() {
    val obj = MergeInBetweenLinkedList()
    val cases = mutableListOf(
        listOf("0,1,2,3,4,5,6", 2, 5, "1000000,1000001,1000002,1000003,1000004"),
        listOf("0,1,2,3,4,5,6", 1, 5, "1000000,1000001,1000002,1000003,1000004"),
        listOf("0,1,2,3,4,5,6", 5, 5, "1000000,1000001,1000002,1000003,1000004"),
        listOf("0,1,2,3,4,5", 3, 4, "1000000,1000001,1000002"),
    )

    cases.forEach {
        val list1 = Utils.createLinkedList(it[0].toString())
        val list2 = Utils.createLinkedList(it[3].toString())
        val result = obj.mergeInBetween(list1, it[1] as Int, it[2] as Int, list2)
        Utils.printLinkedList(result)
        println(" ->${it.joinToString()}\n")
    }
}