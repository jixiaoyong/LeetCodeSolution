import utils.TreeNode
import utils.Utils

/**
 * 110. 平衡二叉树
给定一个二叉树，判断它是否是高度平衡的二叉树。

本题中，一棵高度平衡二叉树定义为：

一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
 *
 *
 * 提示：

树中的节点数在范围 [0, 5000] 内
-10^4 <= Node.val <= 10^4
 *
 * @author: jixiaoyong
 * @email: jixiaoyong1995@gmail.com
 * @date: 22/10/03
 */
class BalancedBinaryTree {
    /// 388 ms	37.6 MB
    fun isBalanced(root: TreeNode?): Boolean {
        var result = true
        try {
            getMaxDepth(root)
        } catch (exception: Exception) {
            result = false
        }

        return result
    }

    private fun getMaxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        if (root.left == null && root.right == null) {
            return 1
        }

        val left = getMaxDepth(root.left)
        val right = getMaxDepth(root.right)

        if (Math.abs(left - right) > 1) {
            throw Exception()
        }

        return Math.max(left, right) + 1
    }
}

fun main() {
//    val tree = Utils.createTreeFromString("[1]")
//    val tree = Utils.createTreeFromString("[1,2,null,3]")
    val tree = Utils.createTreeFromString("[1,2,3,4,5,6,7,8,9,10,null,11,21,22,23,12]")
//    val tree = Utils.createTreeFromString("[1,2,3,4,5,6,7,8,9,10,null,11,21,22,23,12]")
    Utils.printTree(tree!!)
    println("result:${BalancedBinaryTree().isBalanced(tree)}")
}