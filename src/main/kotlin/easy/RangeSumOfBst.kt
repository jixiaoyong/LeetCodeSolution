package easy

import utils.TreeNode
import utils.Utils

/**
 * @author : jixiaoyong
 * @description ： 938. 二叉搜索树的范围和
 * 给定二叉搜索树的根结点 root，返回值位于范围 [low, high] 之间的所有结点的值的和。
 *
 * 提示：
 * 树中节点数目在范围 [1, 2 * 10^4] 内
 * 1 <= Node.val <= 10^5
 * 1 <= low <= high <= 10^5
 * 所有 Node.val 互不相同
 *
 * https://leetcode.cn/problems/range-sum-of-bst/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/7/2022
 */
class RangeSumOfBst {
    fun rangeSumBST(root: TreeNode?, low: Int, high: Int): Int {
        val intRange = IntRange(low, high)
        val nodes = mutableListOf<TreeNode>()
        var res = 0

        root?.let { nodes.add(it) }

        while (nodes.isNotEmpty()) {
            val node = nodes.removeAt(0)
            val value = node.`val`
            node.left?.let {
                nodes.add(it)
            }
            if (value < high) {
                node.right?.let {
                    nodes.add(it)
                }
            }
            if (intRange.contains(value)) {
                res += value
            }
        }

        return res
    }
}


fun main() {
    val obj = RangeSumOfBst()
    val cases = mutableListOf(
        Triple("[10,5,15,3,7,null,18]", 7, 15),
        Triple("[10,5,15,3,7,13,18,1,null,6]", 6, 10),
        Triple("[10,5,15,3,7,13,18,1,null,6]", 6, 100),
        Triple("[10,5,15,3,7,13,18,1,null,6]", 6, 100),
    )



    for (case in cases) {
        println("${obj.rangeSumBST(Utils.createTreeFromString(case.first), case.second, case.third)}   $case")
    }
}