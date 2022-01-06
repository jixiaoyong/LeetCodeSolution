package medium

import utils.TreeNode
import utils.Utils

/**
 * 958. 二叉树的完全性检验
 * 给定一个二叉树，确定它是否是一个完全二叉树。

百度百科中对完全二叉树的定义如下：

若设二叉树的深度为 h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。（注：第 h 层可能包含 1~2h个节点。）

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/check-completeness-of-a-binary-tree
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
object CheckCompletenessOfABinaryTree {

    /**
     * 思路：
     * 1. 如果右节点不为空、左节点为空，则为false
     * 2. 如果当前节点个数和本应该的个数不一致，并且不是最后一层，则为false
     * 3. 如果左边不为空，右边为空，但却不是最后一层的最后一对子节点，则为false
     */
    fun isCompleteTree(root: TreeNode?): Boolean {
        if (root == null || (root.left == null && root.right == null)) {
            return true
        }

        if (root.left == null) {
            return false
        }

        var currentNodes = mutableListOf(root)
        var currentLevel = 0
        while (currentNodes.isNotEmpty()) {
            val expectSize = Math.pow(2.0, currentLevel.toDouble()).toInt()
            val isFull = currentNodes.size == expectSize

            val tempNodeSetData = mutableListOf<TreeNode>()
            var isSubLevelFull = true
            currentNodes.forEachIndexed { index, treeNode ->
                if (treeNode.right != null && treeNode.left == null) {
                    // 如果右边有节点，左边没有节点，则不是完全二叉树
                    return false
                }

                if (!isSubLevelFull && (treeNode.left != null || treeNode.right != null)) {
                    // 如果不是最后一层的最后一对节点，则左右节点必须都有
                    return false
                }


                if (treeNode.left != null) {
                    tempNodeSetData.add(treeNode.left!!)
                }
                if (treeNode.right != null) {
                    tempNodeSetData.add(treeNode.right!!)
                }

                if (treeNode.right == null || treeNode.left == null) {
                    // 有一个节点没有值，标记一下
                    isSubLevelFull = false
                }
            }

            // 如果当前层级的二叉树不是完整的，并且不是最后一层，就返回false
            if (!isFull && tempNodeSetData.isNotEmpty()) {
                return false
            }
            currentLevel++
            currentNodes = tempNodeSetData
        }

        return true
    }
}

fun main() {
    val nodeTree = Utils.createTreeFromString("[1,2,3,5,null,7,8]")
    val isCompleteTree = CheckCompletenessOfABinaryTree.isCompleteTree(nodeTree)
    print(isCompleteTree)
}