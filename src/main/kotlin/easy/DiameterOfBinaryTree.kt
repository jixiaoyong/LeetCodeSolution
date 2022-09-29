package easy

import utils.TreeNode
import utils.Utils

/**
 * @author : jixiaoyong
 * @description ： 543. 二叉树的直径
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。
 * 这条路径可能穿过也可能不穿过根结点。
 * https://leetcode.cn/problems/diameter-of-binary-tree/
 *
 *
 * 二叉树某个节点的深度：从其子树的叶子节点开始向上，直到该节点的节点数量（从0开始计数）
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/29/2022
 */
fun diameterOfBinaryTree(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }

    val result = getMaxAndTotalDiameter(root)
    return Math.max(result.first, result.second)
}

/**
 * 	318 ms	39 MB
 * 返回的两个值：Pair(root到left或right的叶子节点最长路径A，路径A和root的left和right两个叶子节点之间最长路径B的最大值)
 * return pair(singleLineMax,singleLineOrSubTreeMax)
 */
private fun getMaxAndTotalDiameter(root: TreeNode?): Pair<Int, Int> {
    if (root == null || (root.left == null && root.right == null)) {
        return Pair(0, 0)
    }
    val leftMaxDiameter = getMaxAndTotalDiameter(root.left)
    val rightMaxDiameter = getMaxAndTotalDiameter(root.right)

    // 左右子树中最长的边，等效于当前root的深度
    val max = Math.max(leftMaxDiameter.first, rightMaxDiameter.first) + 1
    // 子树的总长度
    var total = leftMaxDiameter.first + rightMaxDiameter.first
    if (root.left != null) {
        total++
    }
    if (root.right != null) {
        total++
    }
    // 左右子树中最长的路径值
    val subMax = Math.max(leftMaxDiameter.second, rightMaxDiameter.second)
    return Pair(max, Math.max(total, subMax))
}

fun main() {
    val tree = Utils.createTreeFromString("[1]")!!
//    val tree = Utils.createTreeFromString("[1,2,null,5,10]")!!
//    val tree = Utils.createTreeFromString("[4,-7,-3,null,-9,-3,9,-7,-4,null,6,null,-6,-6,null,null,0,6,5,null,9,null,null,-1,-4,null,null,null,-2]")!!
    Utils.printTree(tree)
    println("tree diameter:${diameterOfBinaryTree(tree)}")
}