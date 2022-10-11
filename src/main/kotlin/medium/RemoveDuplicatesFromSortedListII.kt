package medium

import utils.ListNode
import utils.Utils

/**
 * @author : jixiaoyong
 * @description ： 82. 删除排序链表中的重复元素 II
 * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
 * 提示：
 *
 * 链表中节点数目在范围 [0, 300] 内
 * -100 <= Node.val <= 100
 * 题目数据保证链表已经按升序 排列
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/11/2022
 */
class RemoveDuplicatesFromSortedListII {

    // 350 ms	39.1 MB
    // 此算法适用于已排序链表，时间复杂度O(N),空间复杂度O(1)
    fun deleteDuplicates(head: ListNode?): ListNode? {
        var result: ListNode? = null
        var resultNodeTail: ListNode? = null

        var currentNode = head
        var currentNodeCount = 1
        while (currentNode != null) {
            val nextNode = currentNode.next
            if (currentNode.`val` == nextNode?.`val`) {
                currentNodeCount++
            } else if (currentNodeCount == 1) {
                if (resultNodeTail == null) {
                    result = ListNode(currentNode.`val`)
                    resultNodeTail = result
                } else {
                    resultNodeTail.next = ListNode(currentNode.`val`)
                    resultNodeTail = resultNodeTail.next
                }
            } else {
                currentNodeCount = 1
            }
            currentNode = nextNode
        }

        return result
    }

    /**
     * 351 ms	37.4 MB
     * 这个解法没有利用本题【链表已按升序排列】的优势，适用于普通链表
     * 时间复杂度O(N),空间复杂度O(N)
     */
    fun deleteDuplicatesNormal(head: ListNode?): ListNode? {
        val numberCountMap = HashMap<Int, Int>()

        if (head?.next == null) {
            return head
        }

        var currentNode = head
        while (currentNode != null) {
            val value = currentNode.`val`
            numberCountMap.put(value, numberCountMap.getOrDefault(value, 0) + 1)
            currentNode = currentNode.next
        }

        if (numberCountMap.size == 1) {
            return null
        }

        var result: ListNode? = null
        var resultTail: ListNode? = null
        currentNode = head
        while (currentNode != null) {
            val value = currentNode.`val`
            if (numberCountMap.get(value) == 1) {
                if (result == null) {// 链表的头
                    result = ListNode(value)
                    result.next = null
                } else if (resultTail == null) {// 链表的第二个节点
                    resultTail = ListNode(value)
                    result.next = resultTail
                } else {// 链表的其他节点
                    resultTail.next = ListNode(value)
                    resultTail = resultTail.next
                }
            }
            currentNode = currentNode.next
        }

        return result
    }


    /***递归方式**/

    /// 作者：yu-le-1
    // https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/solution/zii-by-yu-le-1-32el/
    fun deleteDuplicates1(head: ListNode?): ListNode? {
        // 1.终止条件: 节点为null或只有一个节点，无需删除
        var head = head
        if (head == null || head.next == null) {
            return head
        }
        // 2.本层逻辑
        if (head.`val` === head.next!!.`val`) {
            // 这里移除了重复的node
            while (head!!.next != null && head!!.`val` === head!!.next!!.`val`) {
                head = head!!.next
            }
            head = deleteDuplicates1(head!!.next)
        } else {
            head.next = deleteDuplicates1(head.next)
        }
        // 3.返回值
        return head
    }
}

fun main() {
    val link = Utils.createLinkedList("[1,2,3,3,4,4,5,8,9]")
//    val link = Utils.createLinkedList("[1,1,1,2,3]")
//    val link = Utils.createLinkedList("[1,1,1]")
//    val link = Utils.createLinkedList("[1,1,1]")
//    val link = Utils.createLinkedList("[1,2,3]")
//    val link = Utils.createLinkedList("[1]")
    val result = RemoveDuplicatesFromSortedListII().deleteDuplicates(link)
    print("result:")
    Utils.printLinkedList(result)
}