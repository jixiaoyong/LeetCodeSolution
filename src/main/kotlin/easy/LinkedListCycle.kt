package easy

import utils.ListNode

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/9
 * description:
 * 141. 环形链表
 *
 * 给你一个链表的头节点 head ，判断链表中是否有环。
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
 * 注意：pos 不作为参数进行传递。仅仅是为了标识链表的实际情况。
 * 如果链表中存在环，则返回 true 。 否则，返回 false 。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/linked-list-cycle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
object LinkedListCycle {
    fun hasCycle(head: ListNode?): Boolean {
        if (head?.next == null) return false

        return compareLinkedList(head, head.next, 1)
    }

    private fun compareLinkedList(head: ListNode?, currentNode: ListNode?, step: Int): Boolean {
        if (currentNode == null) return false

        var preNode = head
        for (i in 0 until step) {
            if (preNode == currentNode) {
                // 如果前面的节点和当前节点相等，说明有环
                return true
            }
            preNode = preNode?.next
        }
        return compareLinkedList(head, currentNode.next, step + 1)
    }
}

fun main() {
    val head = ListNode(3)
    head.next = ListNode(2)
    head.next?.next = ListNode(0)
    head.next?.next?.next = ListNode(-4)
    head.next?.next?.next?.next = head.next
    println(LinkedListCycle.hasCycle(head))
}