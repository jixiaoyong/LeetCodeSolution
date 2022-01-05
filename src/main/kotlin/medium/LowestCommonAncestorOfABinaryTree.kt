package medium

import utils.TreeNode
import utils.Utils

/**
 * 236. 二叉树的最近公共祖先
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。

百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
object LowestCommonAncestorOfABinaryTree {

    /**
     * 从根节点开始，递归查找
     * 思路是：先找到两个节点的位置，然后依次向上查找两个节点的祖先（左右两个子树都能找到要查找的p、q）
     */
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || root.`val` == p?.`val` || root.`val` == q?.`val`) return root

        // 从左子树或右子树中找到p或q的节点集合
        val rightNode = lowestCommonAncestor(root.right, p, q)
        val leftNode = lowestCommonAncestor(root.left, p, q)

        // 如果左子树和右子树都找到了p或q的节点集合，则返回root（即该root就是他们的最近公共祖先）
        if (rightNode != null && leftNode != null) {
            return root
        }

        // 如果只有左/右子树找到了p或q的节点集合，则返回左/右子树
        if (rightNode != null) {
            return rightNode
        }

        if (leftNode != null) {
            return leftNode
        }

        return null
    }
}

fun main() {
    val nodeTree = Utils.createTreeFromString("[3,5,1,6,2,0,8,null,null,7,4]")
    val commonFatherNode =
        LowestCommonAncestorOfABinaryTree.lowestCommonAncestor(nodeTree, TreeNode(3), TreeNode(5))
    print(commonFatherNode?.`val`)
}