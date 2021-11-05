object IntersectionOfTwoLinkedLists {

    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        if (headA == null || headB == null) {
            return null
        }

        var currentA = headA
        var currentB = headB

        while (currentA != currentB) {
            // 当A链表遍历完了，从B链表开始遍历，如果他们链相交了则必定会在某个时刻满足currentA == currentB
            // 否则当两个指针第二次遍历，会同时到队伍末尾都会是null从而退出循环
            currentA = if (currentA == null) headB else currentA.next
            currentB = if (currentB == null) headA else currentB.next
        }
        return currentA
    }
}

fun main() {
    var A = Utils.createLinkedList("[0,9,1,2,4]")
    var B = Utils.createLinkedList("[3,2,4]")
    val pair = Utils.createIntersectLinkedList(A, B, 3, 1)
    A = pair.first
    B = pair.second
    val intersectionNode = IntersectionOfTwoLinkedLists.getIntersectionNode(A, B)
    Utils.printLinkedList(intersectionNode)
}