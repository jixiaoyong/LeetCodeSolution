package easy

import utils.Utils.createTreeFromString
import utils.Utils.printTree
import kotlin.jvm.JvmStatic
import utils.TreeNode
import java.util.ArrayList

/**
 * 257. 二叉树的所有路径
 * 给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。
 * https://leetcode.cn/problems/binary-tree-paths/
 * 输入：root = [1,2,3,null,5]
 * 输出：["1->2->5","1->3"]
 *
 *
 * 提示：
 *
 *
 * 树中节点的数目在范围 [1, 100] 内
 * -100 <= Node.val <= 100
 *
 * @author: jixiaoyong
 * @email: jixiaoyong1995@gmail.com
 * @date: 22/04/02
 */
class BinaryTreePaths {
    private val pathList: MutableList<String> = ArrayList()
    private val linkSymbol = "->"

    /**
     * Runtime: 391 ms, faster than 5.49% of Kotlin online submissions for Binary Tree Paths.
     * Memory Usage: 36.8 MB, less than 31.87% of Kotlin online submissions for Binary Tree Paths.
     */
    fun binaryTreePaths(root: TreeNode?): List<String> {
        if (root == null) return emptyList()
        if (root.left == null && root.right == null) {
            return listOf(root.`val`.toString())
        }

        root.left?.let { binaryTreePaths(it, root.`val`.toString() + "") }
        root.right?.let { binaryTreePaths(it, root.`val`.toString() + "") }
        return pathList
    }

    private fun binaryTreePaths(root: TreeNode, parentPath: String) {
        if (root.left == null && root.right == null) {
            // leaf node
            pathList.add(parentPath + linkSymbol + root.`val`)
            return
        }

        if (root.left != null) {
            binaryTreePaths(root.left!!, parentPath + linkSymbol + root.`val`)
        }
        if (root.right != null) {
            binaryTreePaths(root.right!!, parentPath + linkSymbol + root.`val`)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            val treeNode = createTreeFromString("[1,2,3,null,5]")
//            val treeNode = createTreeFromString("[1,null,2,3,null,5]")
//            val treeNode = createTreeFromString("[1,null,2,4,3,null,5]")
//            val treeNode = createTreeFromString("[1,2,3]")
//            val treeNode = createTreeFromString("[1,2,3,5]")
//            val treeNode = createTreeFromString("[1,2,3,5,null,9]")
//            val treeNode = createTreeFromString("[1]")
            val treeNode = createTreeFromString("[1,2]")
            printTree(treeNode!!)
            val list = BinaryTreePaths().binaryTreePaths(treeNode)
            for (s in list) {
                print("$s,")
            }
        }
    }
}