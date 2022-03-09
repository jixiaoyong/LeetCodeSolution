package easy

import utils.ListNode

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/9
 * description:
 * 876. Middle of the Linked List
 * Given the head of a singly linked list, return the middle node of the linked list.
 * If there are two middle nodes, return the second middle node.
 */
object MiddleOfTheLinkedList {

    /**
     * 双指针
     * 快慢指针同时指向起点，然后慢指针每次一格，快指针每次2格，同时前进，直到快指针无法前进，
     * 此时如果快指针next为空则是奇数链表，慢指针指向的就是中间节点，否则是偶数指针，慢指针next是中间节点。
     *
     * Runtime: 197 ms,faster than 62.99%
     * Memory Usage: 34.4 MB,less than 25.67 %
     */
    fun middleNode(head: ListNode?): ListNode? {
        if (head?.next == null) return head

        var slow = head
        var fast = head

        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }

        return if (fast?.next == null) slow else slow?.next
    }

    /**
     * 单个指针
     * 先遍历一次链表找到链表长度，然后再遍历一次链表，每次走一步，直到走到链表长度的一半，
     * 164 ms,faster than 77.01%	33.3 MB,less than 100.00%
     */
    fun middleNodeWithOnePoint(head: ListNode?): ListNode? {
        if (head?.next == null) return head

        var current = head
        var length = 0;
        while (current != null) {
            length++
            current = current.next
        }

        var middleNode = head
        val middleIndex = length / 2
        for (i in 0 until middleIndex) {
            middleNode = middleNode?.next
        }

        return middleNode
    }
}