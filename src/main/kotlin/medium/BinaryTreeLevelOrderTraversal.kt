package medium

import utils.TreeNode
import utils.Utils

/**
 * 102. 二叉树的层序遍历
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 *
 * 例如：
 * 二叉树：[3,9,20,null,null,15,7],

3
/ \
9     20
/  \
15   7
返回其层序遍历结果：

[
[3],
[9,20],
[15,7]
]

 */
object BinaryTreeLevelOrderTraversal {
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) return emptyList()

        val result = ArrayList<List<Int>>()
        result.add(listOf(root.`val`))

        var currentList = listOf(root)
        while (currentList.isNotEmpty()) {
            val nextList = ArrayList<TreeNode>()
            for (node in currentList) {
                if (node.left != null) nextList.add(node.left!!)
                if (node.right != null) nextList.add(node.right!!)
            }
            if (nextList.isNotEmpty()) result.add(nextList.map { it.`val` })
            currentList = nextList
        }

        return result
    }
}

fun main() {
    val nodeTree = Utils.createTreeFromString("[3,9,20,null,null,15,7,20]")
    val treeLevelOrder = BinaryTreeLevelOrderTraversal.levelOrder(nodeTree)
    print(treeLevelOrder.joinToString())

}