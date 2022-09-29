package hard

import sun.management.MemoryUsageCompositeData.getMax
import utils.TreeNode
import utils.Utils


/**
 * @author : jixiaoyong
 * @description ： 124. 二叉树中的最大路径和
 * 给你一个二叉树的根节点 root ，返回其最大路径和。
 * 【注意有负值】
 * 提示：

树中节点数目范围是 [1, 3 * 10^4]
-1000 <= Node.val <= 1000
 *
 * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。
 * 该路径 至少包含一个 节点，且不一定经过根节点。
 * 路径和 是路径中各节点值的总和。
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/binary-tree-maximum-path-sum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/29/2022
 */
class BinaryTreeMaximumPathSum {
    private var maxSum = 0

    fun maxPathSum(root: TreeNode?): Int {
        maxSum = root?.`val` ?: 0
//        dfs(root)
        getMaxValue(root)

        return maxSum
    }

    private fun getMaxValue(r: TreeNode?): Int {
        if (r == null) return 0
        val left = Math.max(0, getMaxValue(r.left))// 如果子树路径和为负则应当置0表示最大路径不包含子树

        val right = Math.max(0, getMaxValue(r.right))
        maxSum = Math.max(maxSum, r.`val` + left + right) // 判断在该节点包含左右子树的路径和是否大于当前最大路径和

        return Math.max(left, right) + r.`val`
    }

    /// 解题思路：left、right为负值对root加和无意义，可舍弃；root为负需要看其与left、right的和判断是否保留
    /// Runtime: 425 ms, faster than 39.53% of Kotlin online submissions for Binary Tree Maximum Path Sum.
    //  Memory Usage: 44.3 MB, less than 69.19% of Kotlin online submissions for Binary Tree Maximum Path Sum.
    private fun dfs(root: TreeNode?): Int? {
        var currentMaxSum = root?.`val`
        if (root == null || (root.left == null && root.right == null)) {
            return currentMaxSum
        }

        val leftSum = dfs(root.left)
        val rightSum = dfs(root.right)

        // 最大子树边长总和
        val subMaxSum = if (leftSum == null) {
            rightSum!!
        } else if (rightSum == null) {
            leftSum
        } else {
            Math.max(leftSum, rightSum)
        }

        // 当前子树能够组合的最大值:可为left/right/root或者其之和
        currentMaxSum = Math.max(
            Math.max((leftSum ?: 0), 0) + Math.max((rightSum ?: 0), 0) + root.`val`, // 三者之和（排除负值）
            subMaxSum // 左右子树最大值
        )

        maxSum = Math.max(currentMaxSum, maxSum)
        // 此root子树某边的最大和（如果小于root则返回root），让父节点使用：
        return if (subMaxSum > 0) subMaxSum + root.`val` else root.`val`
    }
}

fun main() {
//    val tree = Utils.createTreeFromString("[-2,-1]")
    val tree = Utils.createTreeFromString("[-10,-1,-2,-4,-5]")
//    val tree = Utils.createTreeFromString("[10,-1]")
//    val tree = Utils.createTreeFromString("[-11]")
//    val tree = Utils.createTreeFromString("[11]")
//    val tree = Utils.createTreeFromString("[0,1,1]")
//    val tree = Utils.createTreeFromString("[1,2,3,4,5]")
//    val tree = Utils.createTreeFromString("[1,2,3]")
//    val tree = Utils.createTreeFromString("[1,2,null,3,null,4,null,5]")
//    val tree = Utils.createTreeFromString("[1,null,2,null,3,null,4,null,5]")
//    val tree = Utils.createTreeFromString("[8,9,-6,null,null,5,9]")
//    val tree =
//        Utils.createTreeFromString("[-1,8,2,null,-9,0,null,null,null,-3,null,null,9,null,2]")
    if (tree != null) {
        Utils.printTree(tree)
    }
    println("maxPathSum ${BinaryTreeMaximumPathSum().maxPathSum(tree)}")
}