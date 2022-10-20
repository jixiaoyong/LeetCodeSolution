package hard

import utils.ListNode
import utils.Utils
import java.util.*
import kotlin.random.Random


/**
 * @author : jixiaoyong
 * @description ： 23. 合并K个升序链表
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 *
 * 例如：
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 *
 * https://leetcode.cn/problems/merge-k-sorted-lists/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/20/2022
 */
class MergeKSortedLists {

    /**
     * 458 ms	47.8 MB
     * 时间复杂度O(N*LogK) N是链表的所有node数目，每次从PriorityQueue取出最小值的复杂度是O(LogK)
     * 空间复杂度O(K)，需要K个空间同时存储多个链表的头部以供比较
     */
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {

        if (lists.isEmpty()) {
            return null
        } else if (lists.size == 1) {
            return lists.first()
        }

        val result = ListNode(-1)
        var cur: ListNode? = null

        // 算法时间优化点在于此PriorityQueue，每次remove出的都是最小的，从而节省了反复排序的时间
        val curList: PriorityQueue<ListNode> = PriorityQueue(compareBy {
            it.`val`
        })
        curList.addAll(lists.filterNotNull())

        while (curList.isNotEmpty()) {

            // 每次remove出的都是最小的
            val node = curList.remove()
            node.next?.let {
                curList.add(it)
            }

            if (cur == null) {
                cur = node
                result.next = cur
            } else {
                cur.next = node
                cur = cur.next
            }

        }

        return result.next
    }

    /**
     * 官网分治法：
     * https://leetcode.cn/problems/merge-k-sorted-lists/solution/he-bing-kge-pai-xu-lian-biao-by-leetcode-solutio-2/
     * 414 ms	46.8 MB
     * 将lists两两合并，直到只有一个链表为止
     * 时间复杂度O(KN*LogK)
     * 空间复杂度O(N) 使用lists储存结果，依次为N,N/2,N/4...
     */
    fun mergeKListsDivide(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) {
            return null
        } else if (lists.size == 1) {
            return lists.first()
        }

        val lists = lists.toMutableList()

        while (lists.size > 1) {
            val subResult = mutableListOf<ListNode?>()
            for (x in lists.size - 1 downTo 0 step 2) {
                println("x $x lists.size${lists.size} ")
                if (lists.size > 1) {
                    mergeTwoSortedList(lists.removeLast(), lists.removeLast())?.let {
                        subResult.add(it)
                    }
                }
            }
            lists.addAll(subResult)
        }

        return lists.firstOrNull()
    }

    private fun mergeTwoSortedList(listNode1: ListNode?, listNode2: ListNode?): ListNode? {

        if (null == listNode1) {
            return listNode2
        }
        if (null == listNode2) {
            return listNode1
        }

        var listNode1 = listNode1
        var listNode2 = listNode2

        val dummy = ListNode(-1)
        var cur: ListNode? = dummy

        while (null != listNode1 && null != listNode2) {
            if (listNode1.`val` < listNode2.`val`) {
                cur?.next = listNode1
                cur = cur?.next
                listNode1 = listNode1.next
            } else {
                cur?.next = listNode2
                cur = cur?.next
                listNode2 = listNode2.next
            }
        }

        if (null == listNode1) {
            cur?.next = listNode2
        }
        if (null == listNode2) {
            cur?.next = listNode1
        }

        return dummy.next
    }

}

fun main() {
    val obj = MergeKSortedLists()
    val random = Random(System.currentTimeMillis())

    val lists = mutableListOf(
        arrayOf("[]"),
        arrayOf("[]", "[]"),
        arrayOf("[1,4,5]", "[1,3,4]", "[2,6]"),
        arrayOf("[1,4,5]", "[1,1,4]", "[2,6,9,9]"),
        arrayOf("[1,4,5]", "[1,1,4]", "[1,2,90,91]", "[2,6,9,9]"),
    )

    // 测试极端值耗时
    val longestListStrings = mutableListOf<String>()
    for (x in 0..10000) {
        var start = random.nextInt(10000)
        var intArr = IntArray(random.nextInt(500)) { index ->
            start + index
        }

        longestListStrings.add("[${intArr.joinToString()}]")
    }

    lists.add(longestListStrings.toTypedArray())

    lists.forEach {
        println()
        println()
        val linkLists = it.map {
            Utils.createLinkedList(it)
        }.toTypedArray()
        val timeMs = System.currentTimeMillis()
        //END 262405MS
        val result = obj.mergeKListsDivide(linkLists)
//        print("\n${it.joinToString()} -- ")
        Utils.printLinkedList(result)
        println("\nEND ${System.currentTimeMillis() - timeMs}MS")
    }


}