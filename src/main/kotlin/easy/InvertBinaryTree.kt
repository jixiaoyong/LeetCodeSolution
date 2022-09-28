package easy

import utils.TreeNode
import utils.Utils
import java.util.LinkedList

/**
 * @author : jixiaoyong
 * @description ：226. 翻转二叉树
 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
 * https://leetcode.cn/problems/invert-binary-tree/
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/28/2022
 */
/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
class Solution {

    // 279 ms	34.2 MB dfs
    // 时间复杂度 O(n)
    // 空间复杂度 O(n)
    fun invertTree(root: TreeNode?): TreeNode? {

        if (root == null) {
            return null
        }

        val left = root.left
        root.left = invertTree(root.right)
        root.right = invertTree(left)

        return root
    }

    // 270 ms	33.7 MB bfs
    // 时间复杂度 O(n)
    // 空间复杂度 O(n)
    fun invertTreeByStack(root: TreeNode?): TreeNode? {
        if (root == null) {
            return null
        }

        val stack = LinkedList<TreeNode?>()
        stack.addLast(root)
        while (!stack.isEmpty()) {
            val treeNode = stack.pop() ?: continue
            val left = treeNode.left
            treeNode.left = treeNode.right
            treeNode.right = left

            stack.add(treeNode.left)
            stack.add(treeNode.right)
        }

        return root
    }

}


fun main() {
    val root = Utils.createTreeFromString("[1,2,null,null,3]")
    if (root != null) {
        Utils.printTree(root)
        val revertTree = Solution().invertTree(root)
        revertTree?.let { Utils.printTree(it) }
        Utils.printTree(root)
        val revertTree2 = Solution().invertTreeByStack(root)
        revertTree2?.let { Utils.printTree(it) }
    }
}