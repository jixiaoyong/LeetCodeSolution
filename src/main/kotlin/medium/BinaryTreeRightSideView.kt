package medium

import utils.TreeNode
import utils.Utils
import java.util.LinkedList

/**
 * @author : jixiaoyong
 * @description ： 199. 二叉树的右视图
 * 给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 * （注意，这里的“从右侧能看到的节点”也包括root的左子节点——也就是整个树的最右边节点）
 * https://leetcode.cn/problems/binary-tree-right-side-view/
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/28/2022
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
    fun rightSideView(root: TreeNode?): List<Int> {

        return bfsRightQueue(root)
//        return bfsRight(root)
    }

    // 383 ms	36.1 MB
    private fun bfsRight(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()

        if (root == null) {
            return result
        }

        val currentLevelList = mutableListOf<TreeNode>()
        val nextLevelList = mutableListOf<TreeNode>()
        currentLevelList.add(root)
        while (currentLevelList.isNotEmpty()) {
            nextLevelList.clear()
            currentLevelList.forEach { treeNode ->
                treeNode.left?.let {
                    nextLevelList.add(it)
                }
                treeNode.right?.let {
                    nextLevelList.add(it)
                }
            }
            result.add(currentLevelList.last().`val`)
            currentLevelList.clear()
            currentLevelList.addAll(nextLevelList)
        }

        return result
    }

    // 262 ms	35.6 MB
    private fun bfsRightQueue(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()

        if (root == null) {
            return result
        }

        val currentLevelList = LinkedList<TreeNode>()

        currentLevelList.add(root)
        while (currentLevelList.isNotEmpty()) {

            val last = currentLevelList.last
            result.add(last.`val`)

            do {
                val node = currentLevelList.pop()
                node.left?.let { currentLevelList.addLast(it) }
                node.right?.let { currentLevelList.addLast(it) }
            } while (node != last && !currentLevelList.isEmpty())

        }

        return result
    }
}

fun main() {
//    val rootNode = Utils.createTreeFromString("[1,null,2,null,3,4,5,6,7,8,9,10]")
    val rootNode = Utils.createTreeFromString("[1,null,2,3,4,5,6,7,8,9,10]")
//    val rootNode = Utils.createTreeFromString("[1,2]")
    Utils.printTree(rootNode!!)
    val result = Solution().rightSideView(rootNode)
    println("result:$result")
}