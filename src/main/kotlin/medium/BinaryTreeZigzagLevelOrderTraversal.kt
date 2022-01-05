package medium

import utils.TreeNode
import utils.Utils

/**
 * 103. 二叉树的锯齿形层序遍历
给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。

例如：
给定二叉树 [3,9,20,null,null,15,7],

3
/ \
9  20
/  \
15   7
返回锯齿形层序遍历如下：

[
[3],
[20,9],
[15,7]
]
通过次数193

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
object BinaryTreeZigzagLevelOrderTraversal {
    fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) return emptyList()

        val result = ArrayList<List<Int>>()
        result.add(listOf(root.`val`))

        var currentList = listOf(root)
        var index = 1
        while (currentList.isNotEmpty()) {
            val nextList = ArrayList<TreeNode>()
            for (node in currentList) {
                if (node.left != null) nextList.add(node.left!!)
                if (node.right != null) nextList.add(node.right!!)
            }
            if (nextList.isNotEmpty()) {
                // 如果是奇数层，则反转
                val tempList = if (index++ % 2 != 0) {
                    nextList.reversed()
                } else {
                    nextList
                }
                result.add(tempList.map { it.`val` })
            }
            currentList = nextList
        }

        return result
    }
}

fun main() {
    val nodeTree = Utils.createTreeFromString("[1,2,3,4,null,null,5]")
    val treeLevelOrder = BinaryTreeZigzagLevelOrderTraversal.zigzagLevelOrder(nodeTree)
    print(treeLevelOrder.joinToString())

}