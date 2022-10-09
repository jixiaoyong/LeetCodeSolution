package medium

import utils.TreeNode
import utils.Utils
import java.util.*


/**
 * @author : jixiaoyong
 * @description ：662. 二叉树最大宽度
 * 给你一棵二叉树的根节点 root ，返回树的 最大宽度 。
 * 树的 最大宽度 是所有层中最大的 宽度 。
 * 每一层的 宽度 被定义为该层最左和最右的非空节点（即，两个端点）之间的长度。
 * 将这个二叉树视作与满二叉树结构相同，两端点间会出现一些延伸到这一层的 null 节点，这些 null 节点也计入长度。
 * https://leetcode.cn/problems/maximum-width-of-binary-tree/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/9/2022
 */
class WidthOfBinaryTree {

    fun widthOfBinaryTree(root: TreeNode?): Int {
        var maxWidth = 0

        if (root == null) {
            return maxWidth
        }

        maxWidth = 1

        if (root.left == null && root.right == null) {
            return maxWidth
        }

        val queue = LinkedList<Pair<TreeNode, Int>>()
        val subQueue = LinkedList<Pair<TreeNode, Int>>()
        queue.add(Pair(root, 1))

        var count = 0

        while (queue.isNotEmpty()) {
            subQueue.clear()

            while (queue.isNotEmpty()) {
                val pair = queue.removeFirst()
                val node = pair.first
                val index = pair.second
                if (node.left != null) {
                    subQueue.add(Pair(node.left!!, 2 * index))
                }

                if (node.right != null) {
                    subQueue.add(Pair(node.right!!, 2 * index + 1))
                }
            }
            if (subQueue.isNotEmpty()) {
                maxWidth = Math.max(maxWidth, subQueue.last.second - subQueue.first.second + 1)
            }
            queue.addAll(subQueue)
            println("count ${count++},maxWidth:${maxWidth}")
        }

        return maxWidth
    }


    /**
     * https://leetcode.cn/problems/maximum-width-of-binary-tree/solution/er-cha-shu-zui-da-kuan-du-by-leetcode-so-9zp3/
     * 时间复杂度：O(n)，其中 nn 是二叉树的节点个数。需要遍历所有节点。
     * 空间复杂度：O(n)。广度优先搜索的空间复杂度最多为 O(n)。
     */
    fun widthOfBinaryTree3(root: TreeNode): Int {
        var res = 1
        var arr: MutableList<Pair<TreeNode, Int>> = ArrayList()
        arr.add(Pair(root, 1))
        while (!arr.isEmpty()) {
            val tmp: MutableList<Pair<TreeNode, Int>> = ArrayList()
            for (pair in arr) {
                val node: TreeNode = pair.first
                val index: Int = pair.second
                if (node.left != null) {
                    tmp.add(Pair(node.left!!, index * 2))
                }
                if (node.right != null) {
                    tmp.add(Pair(node.right!!, index * 2 + 1))
                }
            }
            res = Math.max(res, arr[arr.size - 1].second - arr[0].second + 1)
            arr = tmp
        }
        return res
    }



    /**
     * 无法处理大规模的tree
     */
    fun widthOfBinaryTree2(root: TreeNode?): Int {
        var maxWidth = 0

        if (root == null) {
            return maxWidth
        }

        maxWidth = 1

        if (root.left == null && root.right == null) {
            return maxWidth
        }

        val queue = LinkedList<TreeNode?>()
        val subQueue = LinkedList<TreeNode?>()
        queue.add(root)

        var count = 0

        while (queue.isNotEmpty()) {
            maxWidth = Math.max(maxWidth, queue.size)
            println("count ${count++},maxWidth:${maxWidth}")

            var lastNotNullIndex = 0
            subQueue.clear()


            while (queue.isNotEmpty()) {
//                println("queue:${queue.size},subQueue:${subQueue.size}")
                val node = queue.removeFirst()

                // 主要问题在于这里将null的node也添加到了列表中，耗时、耗空间,无法处理大一些的tree
                if (subQueue.isNotEmpty() || node?.left != null) {
                    subQueue.add(node?.left)
                    if (node?.left != null) lastNotNullIndex = subQueue.size
                }

                if (subQueue.isNotEmpty() || node?.right != null) {
                    subQueue.add(node?.right)
                    if (node?.right != null) lastNotNullIndex = subQueue.size
                }
            }

            queue.addAll(subQueue.subList(0, lastNotNullIndex))
        }

        return maxWidth
    }

    /**************************************************************/
    var levelMin: MutableMap<Int, Int> = HashMap()

    fun widthOfBinaryTreeDfs(root: TreeNode?): Int {
        return dfs(root, 1, 1)
    }

    /**
     * dfs会先遍历每一层的left节点，所以levelMin会保存所有层级left节点的index，然后再遍历右节点，此时右节点index减去levelMin中保存的最左边
     * 的节点的index，其中最大值就是tree的最大宽度
     */
    fun dfs(node: TreeNode?, depth: Int, index: Int): Int {
        if (node == null) {
            return 0
        }
        levelMin.putIfAbsent(depth, index) // 每一层最先访问到的节点会是最左边的节点，即每一层编号的最小值
        return Math.max(
            index - levelMin[depth]!! + 1,
            Math.max(dfs(node.left, depth + 1, index * 2), dfs(node.right, depth + 1, index * 2 + 1))
        )
    }

}

fun main() {
//    val tree = Utils.createTreeFromString("[1,3,2,5,3,null,9]")
//    val tree = Utils.createTreeFromString("[1,3,2,5,null,null,9,6,null,7]")
//    val tree = Utils.createTreeFromString("[1,3,2,5]")
//    val tree = Utils.createTreeFromString("[1]")
//    val tree = Utils.createTreeFromString("[1,2,6,3,null,4,null,3,4,5,6,9]")
    val timeStart = System.currentTimeMillis()
    val tree =
        Utils.createTreeFromString("[1,1,1,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,null,1,1,null,1,null,1,null,1,null,1,null]")
//    Utils.printTree(tree!!)
//    println("width of tree:${WidthOfBinaryTree().widthOfBinaryTree(tree!!)}")
//    println("width of tree:${WidthOfBinaryTree().widthOfBinaryTree2(tree!!)}")
//    println("width of tree:${WidthOfBinaryTree().widthOfBinaryTree3(tree!!)}")
    println("width of tree:${WidthOfBinaryTree().widthOfBinaryTreeDfs(tree!!)}")

    println("total spent time:${(System.currentTimeMillis() - timeStart) / 1000}s")
}