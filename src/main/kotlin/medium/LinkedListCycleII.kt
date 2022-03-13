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
     * 思路：fast和slow指针同时在链表中移动,slow每次移动1格，fast移动2格，必定相遇于一点，如果该点不是链表的末尾（即没有next），那么相遇的点就是在环中
     * ![快慢指针相遇路程示意图](https://assets.leetcode-cn.com/solution-static/142/142_fig1.png)
     * 设从链表起点到环入口的距离为a，相遇时slow到环入口走的距离为b，环剩下的长度为c
     * 那么，在相遇的时候：
     * fast走了：a+b+n(b+c)次
     * slow走了：a+b次
     * 又有fast同一时刻走过的节点数量是slow的2倍即：a+b+n(b+c) = 2(a+b)
     * 则得出：a = n(b+c)-b = (n-1)(b+c)+c
     * 即，两个相同速度的指针，A从链表head的走a,B从fast和slow相遇点刚好走了 (n-1)(b+c)+c，二者相遇于环的起点
     *
     */
    fun detectCycleByGithub(head: ListNode?): ListNode? {
        if (head == null) return null
        var slow = head
        var fast = head
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
        // 如果有环的话，则fast指针和slow相遇在环之中某个位置
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