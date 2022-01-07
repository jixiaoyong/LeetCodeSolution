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

    /**
     * BFS 广度优先搜索
     * 思路：
     * 先从左往右层遍历，如果遇到空节点，则记录下来
     * 如果在遇到空节点之后，还有遇到非空节点，那么就不是完全二叉树
     */
    fun isCompleteTreeBfs(root: TreeNode?): Boolean {
        if (root == null || (root.left == null && root.right == null)) {
            return true
        }

        if (root.left == null) {
            return false
        }

        var currentNodeList = mutableListOf(root)
        var hasNullNode = false
        while (currentNodeList.isNotEmpty()) {
            val tempNodeList = mutableListOf<TreeNode>()
            for (treeNode in currentNodeList) {
                if (treeNode.left == null) {
                    // 如果有空的标记一下
                    hasNullNode = true
                } else if (hasNullNode) {
                    // 如果在非空值之前已经有空的了，则不是完全二叉树
                    return false
                } else {
                    tempNodeList.add(treeNode.left!!)
                }
                if (treeNode.right == null) {
                    hasNullNode = true
                } else if (hasNullNode) {
                    return false
                } else {
                    tempNodeList.add(treeNode.right!!)
                }
            }
            currentNodeList = tempNodeList
        }

        return true
    }


    /**
     * DFS 深度优先搜索
     * 思路：
     * 记录树所有节点数量
     * 计算树是完全二叉树的时候应该有的节点数量
     * 二者不相等则不是完全二叉树
     */
    fun isCompleteTreeDfs(root: TreeNode?): Boolean {
        if (root == null || (root.left == null && root.right == null)) {
            return true
        }

        if (root.left == null) {
            return false
        }

        return dfs(root, 1, calculateTreeNodeNumber(root))
    }

    /**
     * 判断是否完全二叉树
     * dfs遍历node，判断期望的下标是否大于实际的节点总数
     * （如果期望下标大于实际节点总数，则说明在其之前有null节点，则不是完全二叉树）
     * 期望下标：按照二叉树的节点，对于下标为n的节点，其左节点下标为2n，其右节点下标为2n+1
     * 实际下标：以root下标为1，按照从左往右，从上往下层次遍历编号的数目
     * @param node 当前节点
     * @param currentIndex 当前节点的期望下标
     * @param totalNodeCount 整个二叉树所有节点总的数目
     * @return 是否完全二叉树
     */
    private fun dfs(node: TreeNode?, currentIndex: Int, totalNodeCount: Int): Boolean {
        if (node == null) {
            return true
        }

        if (currentIndex > totalNodeCount) {
            return false
        }

        return dfs(node.left, currentIndex * 2, totalNodeCount) && dfs(node.right, currentIndex * 2 + 1, totalNodeCount)
    }

    /**
     * 计算treeNode实际节点数量
     */
    private fun calculateTreeNodeNumber(treeNode: TreeNode?): Int {
        if (treeNode == null) {
            return 0
        }

        val leftCount = calculateTreeNodeNumber(treeNode.left)
        val rightCount = calculateTreeNodeNumber(treeNode.right)

        return leftCount + rightCount + 1
    }
}

fun main() {
    val nodeTree = Utils.createTreeFromString("[1,2,3,5,null,7,8]")
    val isCompleteTree = CheckCompletenessOfABinaryTree.isCompleteTreeDfs(nodeTree)
    println(isCompleteTree)
}