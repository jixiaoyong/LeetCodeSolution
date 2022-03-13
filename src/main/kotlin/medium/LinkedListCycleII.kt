package medium

import utils.ListNode
import utils.Utils

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/13
 * description:
 * 142. Linked List Cycle II
 * https://leetcode.com/problems/linked-list-cycle-ii/
 *
 * Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously
 * following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer
 * is connected to (0-indexed). It is -1 if there is no cycle. Note that pos is not passed as a parameter.
 *
 * Do not modify the linked list.
 *
 * Constraints:

 * The number of the nodes in the list is in the range [0, 104].
 * -105 <= Node.val <= 105
 * pos is -1 or a valid index in the linked-list.
 *
 */
object LinkedListCycleII {

    /**
     * 快慢指针
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * 思路参考：https://leetcode-cn.com/problems/linked-list-cycle-ii/solution/linked-list-cycle-ii-kuai-man-zhi-zhen-shuang-zhi-/
     * fast和slow指针同时在链表中移动,slow每次移动1格，fast移动2格，必定相遇于一点，如果该点不是链表的末尾（即没有next），那么相遇的点就是在环中
     * 对于有环链表：A-*-*-*-*-*-*-*-*-B-*-*-*-*-*-|
     *                               \-*-*-*-*-/
     * 来说，假设由A到环入口B的距离为a，环的长度为b
     * 当快慢指针在环内相遇时，slow走过的步数s，fast走过的步数f
     * 那么转化为他们走过对应的节点数目：会有 f = s + nb，（即fast比slow多走了n圈环长度之后，二者相遇）
     * 又因为fast速度是slow的2倍，所以f = 2s
     * 由此得到：
     * s = nb
     * f = 2s
     * 又已知，如果从链表的head开始走，只需要走a+nb步，则会到达环入口B
     * 而slow现在已经走了nb步，也就是slow从他们相交的点开始走a步，就能到达环入口B
     * 所以，设一个新的指针X从head开始走a步（X走到B点刚好需要a步），与此同时slow也从相交点开始走a步，二者会同时到达环入口B，此时相遇的点就是环的入口B
     *
     */
    fun detectCycleByGithub(head: ListNode?): ListNode? {
        if (head == null) return null

        var slow = head // 慢指针,每次移动1格
        var fast = head // 快指针,每次移动2格

        while (fast?.next != null && fast.next?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
            if (slow == fast) {
                // 快慢指针相遇
                break
            }
        }
        if (fast?.next == null || fast.next?.next == null) {
            // 快指针下个节点，或者下下个节点为null，没有环
            return null
        }
        // 如果有环的话，则fast指针和slow在同时走了a步之后，一定会相遇在环的入口
        slow = head
        while (slow != fast) {
            slow = slow?.next
            fast = fast?.next
        }
        return slow
    }


    /**
     * 将链表遍历一次，记录之前遍历过的节点，如果遍历到的节点是之前遍历过的节点，则说明有环
     * 373 ms	37.9 MB
     */
    fun detectCycle(head: ListNode?): ListNode? {
        val array = mutableListOf<ListNode>()
        var currentNode = head

        while (currentNode != null) {
            if (array.contains(currentNode)) {
                return currentNode
            }
            array.add(currentNode)
            currentNode = currentNode.next
        }
        return null
    }

    @JvmStatic
    fun main(args: Array<String>) {
//        val head = Utils.createLinkedList("[1,2,3,4,5,6,7,8,9,10,11]")
//        head?.next?.next?.next?.next?.next?.next?.next?.next?.next?.next ?.next = head?.next?.next?.next

//        val head = Utils.createLinkedList("[1,2]")
//        head?.next?.next = head

        val head = Utils.createLinkedList("[1]")

        println(detectCycle(head)?.`val`)
    }
}