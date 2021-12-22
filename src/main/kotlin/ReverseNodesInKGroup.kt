/*
* @description: 25. K 个一组翻转链表
* 给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表。
k是一个正整数，它的值小于或等于链表的长度。
如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序。

进阶：

你可以设计一个只使用常数额外空间的算法来解决此问题吗？
你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*
*
* 解题思路：
* 1. 将链表按照要求分为长度为k的子链表
* 2. 将各个符合长度要求的链表翻转
* 3. 拼接各个子链表
*
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2021/10/30
*/

/**
 * Example:
 * var li = utils.ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class utils.ListNode(var `val`: Int) {
 *     var next: utils.ListNode? = null
 * }
 */
class Solution {
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        // 空链表，只有一个节点的链表，或者k为1而无需翻转的链表：直接返回
        if (head?.next == null || k == 1) {
            return head
        }

        var reverseLinkedList: ListNode? = null
        var current = head
        while (current != null) {
            //1. 将长链表切割为长度为k的子链表
            var next = current
            var linkedListLength = 1
            for (i in 1 until k) {
                // 防止将null也计入链表长度
                if (next?.next != null) {
                    linkedListLength++
                    next = next.next
                }
            }
            // 保存下半截linkedList
            val nextLinkedList = next?.next
            val subLinkedList = if (linkedListLength == k) {
                // 2. 如果是符合要求的链表，就翻转
                next?.next = null
                reverseLinkedList(current)
            } else {
                // 否则直接拼接
                current
            }

            // 3. 将各个list拼接起来
            if (reverseLinkedList == null) {
                reverseLinkedList = subLinkedList
            } else {
                linkedListAdd(reverseLinkedList.next, subLinkedList)
            }

            current = nextLinkedList
        }

        return reverseLinkedList
    }

    // 将[node]添加到[linkedList]的最后
    fun linkedListAdd(linkedList: ListNode?, node: ListNode?): ListNode? {
        if (linkedList == null || node == null) {
            return linkedList
        }
        var next = linkedList
        while (next?.next != null) {
            next = next.next
        }
        next?.next = node
        return linkedList
    }

    // 翻转单个链表
    private fun reverseLinkedList(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }
        val reversedList = reverseLinkedList(head.next)
        head.next?.next = head
        head.next = null
        return reversedList
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }
}

fun main() {
    val linkedListStr = "[1,2,3,4,5,1,2,3,4,5]".replace("[", "").replace("]", "").split(",").map { it.toInt() }
    val k = 4


    val linkedList: Solution.ListNode? = Solution.ListNode(linkedListStr.first())
    var currentNode = linkedList
    linkedListStr.forEachIndexed { index, i ->
        currentNode?.next = linkedListStr.getOrNull(index + 1)?.let { Solution.ListNode(it) }
        currentNode = currentNode?.next
    }

    var current: Solution.ListNode? = linkedList
    while (current != null) {
        print("${current.`val`},")
        current = current.next
    }

    println("\nresult:")
    val reverseLinkedList = Solution().reverseKGroup(linkedList, k)
    var reCurrent: Solution.ListNode? = reverseLinkedList
    while (reCurrent != null) {
        print("${reCurrent.`val`},")
        reCurrent = reCurrent.next
    }
}