package utils

import java.util.*

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

    /**
     ** 从类似[5,4,8,11,null,13,4,7,2,null,null,5,1]的数组中生成一个tree
     */
    fun createTreeFromString(str: String): TreeNode? {
        val treeValueList = str.trim().replace("[", "").replace("]", "").split(",").map { it.toIntOrNull() }
        if (str.isEmpty() || treeValueList.isEmpty()) {
            return null
        }
        val tree = treeValueList.first()?.let { TreeNode(it) }
        val queue = LinkedList<TreeNode>()
        queue.offer(tree)
        var currentIndex = 0
        while (currentIndex < treeValueList.size - 1) {
            val currentNode = queue.pop()
            if (currentNode.left == null && currentIndex + 1 < treeValueList.size) {
                currentNode.left = treeValueList.get(++currentIndex).let { it?.let { it1 -> TreeNode(it1) } }
            }
            if (currentNode.right == null && currentIndex + 1 < treeValueList.size) {
                currentNode.right = treeValueList.get(++currentIndex).let { it?.let { it1 -> TreeNode(it1) } }
            }
            if (currentNode.left != null) {
                queue.offer(currentNode.left)
            }
            if (currentNode.right != null) {
                queue.offer(currentNode.right)
            }
        }
        return tree
    }

    /**
     * 从[[1,2,3,4],[5,6,7,8],[9,10,11,12]]创建一个二维数组
     */
    fun createMatrixFromString(str: String): Array<IntArray> {
        var matrixRowMaxLength = 0
        val matrixRowStrArr = str.trim().split("],[")
        matrixRowStrArr.map {
            val arr = it.replace("[", "").replace("]", "").split(",")
            if (arr.size > matrixRowMaxLength) {
                matrixRowMaxLength = arr.size
            }
            arr.map { it.toInt() }
        }

        val matrix = Array(matrixRowStrArr.size) { IntArray(matrixRowMaxLength) }
        matrixRowStrArr.forEachIndexed { index, s ->
            val arr = s.replace("[", "").replace("]", "").split(",")
            arr.forEachIndexed { index1, s1 ->
                matrix[index][index1] = s1.toInt()
            }
        }
        return matrix
    }

    /**
     * 打印二叉树[tree], 从上到下，从左到右，带着枝杈的树
               5
            /     \
         4           8
       /           /   \
      11          13      4
     / \                 / \
    7   2               5   1

     */
    fun printTree(tree: TreeNode) {
        TreeNodeShow.show(tree)
    }
}

fun main() {
//    val tree = Utils.createTreeFromString("[5,4,8,11,null,13,4,7,2,null,null,5,1]")
//    tree?.let { Utils.printTree(it) }

    val matrix = Utils.createMatrixFromString("[[1,2,3,4],[5,6,7,8],[9,10,11,12]]")
}

