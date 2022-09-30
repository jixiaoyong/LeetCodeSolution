package easy

import utils.TreeNode
import utils.Utils
import java.util.LinkedList

/**
 * @author : jixiaoyong
 * @description ： 112. 路径总和
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。
 * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
 * 如果存在，返回 true ；否则，返回 false 。
 *
 * 树中节点的数目在范围 [0, 5000] 内
 * -1000 <= Node.val <= 1000
 * -1000 <= targetSum <= 1000
 * https://leetcode.cn/problems/path-sum/
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/30/2022
 */
class PathSum {

    // 递归，时间复杂度O(N),空间复杂度O(H)
    // 200 ms	34.8 MB
    fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
        if (root == null) {
            return false
        }

        if (root.left == null && root.right == null) {
            return root.`val` == targetSum
        }

        val sum = targetSum - root.`val`
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum)
    }

    // bfs，时间复杂度O(N),空间复杂度O(N)
    // 220 ms	34.4 MB
    fun hasPathSumBfs(root: TreeNode?, targetSum: Int): Boolean {
        if (root == null) {
            return false
        }

        val nodeQueue = LinkedList<TreeNode>()
        val sumQueue = LinkedList<Int>()

        nodeQueue.push(root)
        sumQueue.push(0)

        while (nodeQueue.isNotEmpty()) {

            val currentNode = nodeQueue.pop()
            val sum = sumQueue.pop() + currentNode.`val`

            if (currentNode.left == null && currentNode.right == null) {
                if (sum == targetSum) {
                    return true
                } else {
                    continue
                }
            }

            currentNode.left?.let {
                nodeQueue.push(it)
                sumQueue.push(sum)
            }

            currentNode.right?.let {
                nodeQueue.push(it)
                sumQueue.push(sum)
            }
        }

        return false
    }
}

fun main() {
//    val tree = Utils.createTreeFromString("[]")
    val tree = Utils.createTreeFromString("[1,0,null,0,1]")
//    val tree = Utils.createTreeFromString("[5,4,8,11,null,13,4,7,1,-4,null,null,1]") //22
//    val tree = Utils.createTreeFromString("[5,null,1,null,1,1,0]") //22
//    val tree = Utils.createTreeFromString("[1,2,3]")

    tree?.let { Utils.printTree(it) }
    println("result:\n")

    val targetSum = 1
    println(PathSum().hasPathSum(tree, targetSum))
    println(PathSum().hasPathSumBfs(tree, targetSum))
}
