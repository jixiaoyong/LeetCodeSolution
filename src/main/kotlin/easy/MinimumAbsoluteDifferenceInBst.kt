package easy

import utils.TreeNode
import utils.Utils
import java.util.LinkedList

/**
 * @author : jixiaoyong
 * @description ： 530. 二叉搜索树的最小绝对差
 * [二叉搜索树] 或者是一颗空树，或者满足left<root<right
 * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
 * 差值是一个正数，其数值等于两值之差的绝对值。
 * https://leetcode.cn/problems/minimum-absolute-difference-in-bst/
 *
 * 树中节点的数目范围是 [2, 104]
 * 0 <= Node.val <= 105
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/10/2022
 */
class MinimumAbsoluteDifferenceInBst {

    val nodesBst = LinkedList<Int>()
    // 399 ms	44.2 MB
    fun getMinimumDifference(root: TreeNode?): Int {
        mfsBst(root)

        var minimumDiff = Int.MAX_VALUE
        var pre = nodesBst.removeFirst()
        while (nodesBst.isNotEmpty()) {
            val current = nodesBst.removeFirst()
            minimumDiff = Math.min(minimumDiff, current - pre)
            pre = current
        }

        return minimumDiff
    }

    // 二叉搜索树中序遍历是有序递增序列
    private fun mfsBst(root: TreeNode?) {
        if (root == null) {
            return
        }

        root.left?.let {
            mfsBst(it)
        }
        nodesBst.add(root.`val`)
        root.right?.let {
            mfsBst(it)
        }
    }


    /***下面的方法是处理普通二叉树的方式，431 ms	45 MB**/

    val nodes = LinkedList<Int>()

    fun getMinimumDifferenceBinaryTree(root: TreeNode?): Int {
        dfs(root)

        nodes.sort()

        var minimumDiff = Int.MAX_VALUE
        var pre = nodes.removeFirst()
        while (nodes.isNotEmpty()) {
            val current = nodes.removeFirst()
            minimumDiff = Math.min(minimumDiff, current - pre)
            pre = current
        }

        return minimumDiff
    }

    private fun dfs(root: TreeNode?) {
        if (root == null) {
            return
        }
        nodes.add(root.`val`)
        dfs(root.left)
        dfs(root.right)
    }
}

fun main() {

//    val tree = Utils.createTreeFromString("[4,2,6,1,3]")
//    val tree = Utils.createTreeFromString("[1,0,48,null,null,12,49]")
//    val tree = Utils.createTreeFromString("[1,null,3]")
    val tree = Utils.createTreeFromString("[236,104,701,null,227,null,911]")

    Utils.printTree(tree!!)
    println("minimum difference:${MinimumAbsoluteDifferenceInBst().getMinimumDifference(tree)}")
}