package medium

import utils.ListNode
import utils.Utils
import java.util.*

/**
 * @author : jixiaoyong
 * @description ：92. 反转链表 II
 * 给你单链表的头指针 head 和两个整数left 和 right ，其中left <= right 。
 * 请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 *
 * 提示：
 *
 * 链表中节点数目为 n
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 *
 * https://leetcode.cn/problems/reverse-linked-list-ii/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/19/2022
 */
class ReverseLinkedListII {

    /**
     * 263 ms	35 MB
     * 算法思路，从左到右，依次遍历链表，将[left,right]的node加入到stack里面，然后再在right之后将stack里面的node出栈即完成翻转部分链表
     * 时间复杂度O(N),极端情况left为1，right为链表长度，需要先遍历一次将所有node添加到stack，再遍历一次stack翻转所有node
     * 空间复杂度为O(N)，极端情况下stack深度为链表长度N
     */
    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
        if (head?.next == null || left == right) {
            return head
        }

        val stack = LinkedList<ListNode>()
        var result = if (left == 1) null else head

        var currentIndex = 1
        var currentNode = head
        var beforeReverseNode = currentNode

        while (currentNode != null) {
            if (currentIndex < left) {
                // 在left之前，继续遍历即可
                beforeReverseNode = currentNode
                currentNode = currentNode.next
                currentIndex++
            } else if (currentIndex >= right) {
                // 此处纳入了right，避免了right为linkedList末尾而无法进入此分支，从而导致无法将left及之后的node加入result的情况
                stack.push(currentNode)
                currentNode = currentNode.next

                var cur = if (result == null) {
                    // 处理left为linkedList的第一个node，所以result需要指向逆转之后的第一个node
                    result = stack.pop()
                    result
                } else {
                    beforeReverseNode
                }

                while (stack.isNotEmpty()) {
                    cur?.next = stack.pop()
                    cur = cur?.next
                }
                cur?.next = currentNode
                break
            } else {
                // 在[left,right)之间，将其加入stack里面
                stack.push(currentNode)
                currentNode = currentNode.next
                currentIndex++
            }
        }

        return result
    }

    /**
     * 300 ms	34.4 MB
     * 来自 Fuzz ， https://leetcode.cn/problems/reverse-linked-list-ii/comments/
     * 时间复杂度O(N)只需遍历一次
     * 空间复杂度O(1)只需常量存储过渡值
     */
    fun reverseBetween2(head: ListNode?, m: Int, n: Int): ListNode? {
        var head = head

        // 因为head会在翻转的过程被修改，所以这里使用dummy持有链表的头部以便输出
        val dummy = ListNode(0)
        dummy.next = head

        var pre: ListNode? = dummy

        // 1. 遍历直到left
        for (i in 1 until m) {
            pre = pre!!.next
        }
        head = pre!!.next

        // 2. 翻转left~right
        for (i in m until n) {
            // 下述代码的作用是，将head和head.next换位
            // 也即：从pre->head->nex->other 改为 per->nex-head->other
            // 实现的效果是，pre固定不变，而不断的将m~n之间的node依次插入到pre后面（在此过程中实现了m~n直接倒序排列）
            val nex = head!!.next
            head.next = nex!!.next
            nex.next = pre.next
            pre.next = nex
        }
        return dummy.next
    }
}

fun main() {
    val obj = ReverseLinkedListII()

    val linkedLists = mutableListOf(
        Pair("[1,2,3,4,5]", Pair(1, 5)),
        Pair("[1,2,3,4,5,6,7]", Pair(2, 6)),
        Pair("[1,2]", Pair(1, 2)),
        Pair("[1,2,3,4,5]", Pair(1, 2)),
        Pair("[1]", Pair(1, 1)),
        Pair("[1,2]", Pair(1, 1)),
    )

    linkedLists.forEach {
        println("\n")
        val linkedList = Utils.createLinkedList(it.first)
        val nums = it.second
        println("reverse ${nums.first}--${nums.second}:")
        val result = obj.reverseBetween2(linkedList, nums.first, nums.second)
        Utils.printLinkedList(result)
    }

}