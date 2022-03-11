package medium

import utils.ListNode
import utils.Utils.printLinkedList

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/11
 * description: 19. Remove Nth Node From End of List
 * Given a linked list, remove the n-th node from the end of list and return its head.
 * The number of nodes in the list is sz.
1 <= sz <= 30
0 <= Node.val <= 100
1 <= n <= sz
 */
object RemoveNthNodeFromEndOfList {

    /**
     * 解法一：双指针
     * 342 ms	35.1 MB
     */
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        if (head == null) return head

        if (head.next == null && n == 1) {
            return null
        }

        var fast = head
        var slow = head

        // 快指针先走n步
        for (i in 0 until n) {
            fast = fast?.next
        }

        // 两个指针同时走，当快指针到达末尾(最后一个节点)时，慢指针到达倒数第n+1个节点
        while (fast?.next != null) {
            fast = fast.next
            slow = slow?.next
        }

        // 删除倒数第n个节点
        return if (fast == null) {
            // 如果fast为null，说明n为链表长度，删除头节点
            head.next
        } else {
            slow?.next = slow?.next?.next
            head
        }

    }

    /**
     * 回溯法后序遍历
     * author：Malex
     * 231 ms	35.2 MB
     * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/solution/shan-chu-lian-biao-de-dao-shu-di-nge-jie-dian-b-61/857681
     */
    fun removeNthFromEndMalex(head: ListNode, n: Int): ListNode? {
        val traverse = traverse(head, n)
        return if (traverse == n) head.next else head
    }

    /**
     * 递归查询链表直到最后一个节点
     * 然后对比当前节点到最后一个节点的距离是否等于n（如果等于n，则删除当前节点）；
     * @param n 待删除节点在链表倒数的位置
     * @return 当前节点[node]到最后一个节点的距离
     */
    private fun traverse(node: ListNode?, n: Int): Int {
        if (node == null) return 0
        val num = traverse(node.next, n)
        if (num == n) node.next = node.next!!.next
        return num + 1
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val head = ListNode(1)
        head.next = ListNode(2)
        head.next?.next = ListNode(3)
//        head.next?.next?.next = ListNode(4)
//        head.next?.next?.next?.next = ListNode(5)
        printLinkedList(removeNthFromEndMalex(head, 3))
    }
}