package medium

import utils.Utils

/*
* @description: 113. 路径总和 II
* 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。

叶子节点 是指没有子节点的节点。
* https://leetcode-cn.com/problems/path-sum-ii/
 * Example:
 * var ti = utils.TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class utils.TreeNode(var `val`: Int) {
 *     var left: utils.TreeNode? = null
 *     var right: utils.TreeNode? = null
 * }
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2021/12/22
*/
object TreePathSum {

    /**
     * 使用递归
     */
    fun pathSum(root: utils.TreeNode?, targetSum: Int): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        val rootValue = root?.`val`

        // 这里不需要判断targetSum和rootValue的大小，因为可能会有负值
        if (rootValue == null) {
            return result
        }

        // 到达叶子节点了，如果符合条件就返回，从此叶子节点一直上溯到root的路径即为满足条件的路径
        if (rootValue == targetSum && root.left == null && root.right == null) {
            result.add(arrayListOf(rootValue))
            return result
        }

        // 这里的思想：将不断的向下查询符合条件的叶子节点的任务，转化为查询和为sumValue的路径，
        // 如此可以递归的查询所有的路径
        val sumValue = targetSum - rootValue
        val leftSumArr = pathSum(root.left, sumValue).toMutableList()
        val rightSumArr = pathSum(root.right, sumValue).toMutableList()

        if (leftSumArr.isNotEmpty()) {
            leftSumArr.forEach {
                // 注意这里就可以认为it中的数组为满足条件的路径，我们只需要把root的值加到这个路径的最前方即可
                val list = it.toMutableList()
                list.add(0, rootValue)
                result.add(list)
            }
        }
        if (rightSumArr.isNotEmpty()) {
            rightSumArr.forEach {
                val list = it.toMutableList()
                list.add(0, rootValue)
                result.add(list)
            }
        }
        return result
    }
}

fun main() {
    val tree = Utils.createTreeFromString("[-2,null,-3]")
    val result = TreePathSum.pathSum(tree, -5).joinToString()
    println(result)
}


