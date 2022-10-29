package medium

import utils.ListNode
import utils.Utils
import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 2. 两数相加
 * 给你两个非空 的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0开头。
 *
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 *
 * 提示：
 *
 * 每个链表中的节点数在范围 [1, 100] 内
 * 0 <= Node.val <= 9
 * 题目数据保证列表表示的数字不含前导零
 *
 * https://leetcode.cn/problems/add-two-numbers/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/29/2022
 */
class AddTwoNumbers {

    /**
     * 时间复杂度O(max(M,N))
     * 空间复杂度O(1)
     */
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val result = ListNode(-1)

        var current: ListNode? = result
        var extra = 0

        var l1 = l1
        var l2 = l2

        // 先相加相同位数
        while (l1 != null && l2 != null) {
            val sum = l1.`val` + l2.`val` + extra
            extra = sum / 10
            current?.next = ListNode(sum % 10)
            current = current?.next
            l1 = l1.next
            l2 = l2.next
        }

        // 再将多余的位数，与之前的进位extra相加
        var longerListNode = l1 ?: l2
        if (longerListNode != null) {
            while (extra > 0) {
                val sum = (longerListNode?.`val` ?: 0) + extra
                extra = sum / 10
                current?.next = ListNode(sum % 10)
                current = current?.next
                longerListNode = longerListNode?.next
            }
            current?.next = longerListNode
        } else if (extra > 0) {
            current?.next = ListNode(extra)
        }

        return result.next
    }
}

fun main() {
    val obj = AddTwoNumbers()
    val random = Random(System.currentTimeMillis())

    val cases =
        mutableListOf(
            Pair("0,2,3", "0"),
            Pair("0,2,3", "1,2"),
            Pair("9,4,3", "5,6,4,3,4"),
            Pair("2,4,3", "5,6,4"),
            Pair("0", "0"),
            Pair("0", "0,2"),
            Pair("9,9,9,9,9,9,9", "9,9,9,9"),
            Pair("9,9", "9,9,9,9")
        )

    val largestLinks = Pair(StringBuffer(), StringBuffer())
    for (x in 0 until 99) {
        largestLinks.first.append("${random.nextInt(0, 9)},")
        largestLinks.second.append("${random.nextInt(0, 9)},")
    }
    largestLinks.first.append(random.nextInt(1, 9))
    largestLinks.second.append(random.nextInt(1, 9))

    cases.add(Pair(largestLinks.first.toString(), largestLinks.second.toString()))

    cases.forEach {
        val listNode1 = Utils.createLinkedList(it.first)
        val listNode2 = Utils.createLinkedList(it.second)

        val result = obj.addTwoNumbers(listNode1, listNode2)
        println("\nResult:")
        Utils.printLinkedList(result)
        println(" ---> ${it}\n")
    }
}