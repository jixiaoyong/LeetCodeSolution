package easy

import utils.TreeNode
import utils.Utils
import java.util.*

/**
 * @author : jixiaoyong
 * @description ：101. Symmetric Tree
 * Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
 * https://leetcode.com/problems/symmetric-tree/
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/26/2022
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
class SymmetricTree {
    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) {
            return true
        }

        return isNodeEqual(root.left, root.right)
    }

    /**
     * 递归，
     * 时间复杂度为O(n)，需要遍历n节点
     * 空间复杂度为O(n)，最坏情况为链表，需要递归n层
     */
    fun isNodeEqual(left: TreeNode?, right: TreeNode?): Boolean {
        if (left == null && right == null) {
            return true
        }

        if (left?.`val` != right?.`val`) {
            return false
        }

        return isNodeEqual(left?.left, right?.right) && isNodeEqual(left?.right, right?.left)
    }

    /** 使用队列实现
     * 时间复杂度为O(n)，需要遍历n节点
     * 空间复杂度为O(n)，最坏情况为链表，需要递归n层
     */
    fun isSymmetricByQueue(root: TreeNode?): Boolean {
        if (root == null || root.left == null && root.right == null) return true

        val queue = LinkedList<TreeNode?>()
        queue.add(root.left)
        queue.add(root.right)

        while (queue.isNotEmpty()) {
            val left = queue.removeFirst()
            val right = queue.removeFirst()

            // 都为null，则返回，检测下一个
            if (left?.`val` == null && right?.`val` == null) {
                continue
            }

            // 不相等则不对称
            if (left?.`val` != right?.`val`) {
                return false
            }

            // 递归对比
            // 外层节点
            queue.add(left?.left)
            queue.add(right?.right)

            // 内层节点
            queue.add(left?.right)
            queue.add(right?.left)
        }

        return true
    }

}

fun main() {
    val root1 = Utils.createTreeFromString("[1,2,2,3,4,4,3]")
//    val root1 = Utils.createTreeFromString("[1,2,2,null,3,3,2,2,null,3,null,3,2,2,null,3,null,3]")
    root1?.let { Utils.printTree(it) }
    print("isSymmetric:${SymmetricTree().isSymmetricByQueue(root1)}")
}