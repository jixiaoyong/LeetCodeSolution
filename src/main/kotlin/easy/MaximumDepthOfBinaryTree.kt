package easy

import utils.TreeNode
import utils.Utils
import java.util.*

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/18
 * description: 104. Maximum Depth of Binary Tree
 *
 * Given the root of a binary tree, return its maximum depth.
 *
 * A binary tree's maximum depth is the number of nodes along the longest path
 * from the root node down to the farthest leaf node.
 *
 * Constraints:

The number of nodes in the tree is in the range [0, 104].
-100 <= Node.val <= 100
 */
object MaximumDepthOfBinaryTree {

    /**
     * 深度优先搜索 DFS
     * 289 ms	38.7 MB
     * 时间复杂度：O(n) , n 为二叉树的节点数
     * 空间复杂度：O(height) , height 为二叉树的高度
     */
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        if (root.left == null && root.right == null) {
            return 1
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1
    }

    /**
     * 广度优先搜索 BFS
     * 	256 ms	39.9 MB
     * 时间复杂度：O(n)
     * 空间复杂度：O(n) , n 为二叉树的节点数,最坏情况下会需要存储 n 个节点
     */
    fun maxDepthBFS(root: TreeNode?): Int {
        if(root == null) {
            return 0
        }
        if (root.left == null && root.right == null) {
            return 1
        }
        val queue = LinkedList<TreeNode>()
        queue.offer(root)
        var depth = 0

        while (queue.isNotEmpty()) {
            val size = queue.size
            depth++
            // 遍历当前层的节点，将其子节点加入LinkedList中
            for (i in 0 until size) {
                val node = queue.poll()
                if (node.left != null) {
                    queue.offer(node.left)
                }
                if (node.right != null) {
                    queue.offer(node.right)
                }
            }
        }
        return depth
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val size = 10000
        val nums = IntArray(size)
        val random = Random(System.currentTimeMillis())
        for (i in 0 until size) {
            nums[i] = random.nextInt(100)-random.nextInt(20)
        }
        val root= Utils.createTreeFromString(nums.joinToString())
        println(maxDepth(root)== maxDepthBFS(root))
    }
}