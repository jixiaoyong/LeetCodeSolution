object Utils {

    /**
     * 根据输入的[1,2,3]这样文字获取对应的链表
     */
    fun createLinkedList(source: String): ListNode? {
        if (source.isEmpty() || source.isBlank() || "[]" == source) {
            return null
        }
        val linkedListStr = source.trim().replace("[", "").replace("]", "").split(",").map { it.toInt() }
        val linkedList: ListNode? = ListNode(linkedListStr.first())
        var currentNode = linkedList
        linkedListStr.forEachIndexed { index, i ->
            currentNode?.next = linkedListStr.getOrNull(index + 1)?.let { ListNode(it) }
            currentNode = currentNode?.next
        }

        printLinkedList(linkedList)
        println()
        return linkedList;
    }

    fun printLinkedList(linkedList: ListNode?) {
        var current: ListNode? = linkedList
        while (current != null) {
            print("${current.`val`},")
            current = current.next
        }
    }

    /**
     * 创建相交的LinkedList
     * @param skipA,skipB 相交的点下标（从0开始）
     */
    fun createIntersectLinkedList(
        headA: ListNode?,
        headB: ListNode?,
        skipA: Int,
        skipB: Int
    ): Pair<ListNode?, ListNode?> {
        if (headA == null || headB == null) {
            return Pair(headA, headB)
        }

        var pointA = headA
        var pointB = headB
        for (x in 1 until skipA) {
            pointA = pointA?.next
        }
        for (x in 1 until skipB) {
            pointB = pointB?.next
        }
        val commonNext = pointA?.next
        pointB?.next = commonNext
        return Pair(headA, headB)
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}