package utils

import java.util.*

/*
* @description: 遍历二叉树
*
* tree的遍历分为：DFS（深度优先）和BFS（广度优先）
*
* DFS：
* 从根开始，深度优先遍历二叉树，每次遍历一个节点。根据根的位置可以分为以下三种
*
* 前序遍历：根节点->左子树->右子树
* 中序遍历：左子树->根节点->右子树
* 后序遍历：左子树->右子树->根节点
*
* BFS：
* 从根开始，按照层次遍历
*
* 前序遍历：144. Binary Tree Preorder Traversal https://leetcode.com/problems/binary-tree-preorder-traversal/
* 中序遍历：94. Binary Tree Inorder Traversal https://leetcode.com/problems/binary-tree-inorder-traversal/
* 后序遍历：145. Binary Tree Postorder Traversal https://leetcode.com/problems/binary-tree-postorder-traversal/
*
* 解题思路：有一个currentNode指向当前节点；有一个FILO的容器，保存哪些没有输出的节点
* 然后再在满足条件时更新currentNode，直到其为null，结束遍历
*
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2022/1/11
*/
object TreeTraverse {

    // 前序遍历的递归实现
    fun preorderTraversalRecursion(treeNode: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        if (treeNode != null) {
            result.add(treeNode.`val`)
            result.addAll(preorderTraversalRecursion(treeNode.left))
            result.addAll(preorderTraversalRecursion(treeNode.right))
        }
        return result
    }

    // 中序遍历的递归实现
    fun inorderTraversalRecursion(treeNode: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        if (treeNode != null) {
            result.addAll(inorderTraversalRecursion(treeNode.left))
            result.add(treeNode.`val`)
            result.addAll(inorderTraversalRecursion(treeNode.right))
        }
        return result
    }

    // 后序遍历的递归实现
    fun postorderTraversalRecursion(treeNode: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        if (treeNode != null) {
            result.addAll(postorderTraversalRecursion(treeNode.left))
            result.addAll(postorderTraversalRecursion(treeNode.right))
            result.add(treeNode.`val`)
        }
        return result
    }

    // 前序遍历的非递归实现
    fun preorderTraversalCycle(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()

        var currentNode = root
        val nodeStack = Stack<TreeNode>()

        while (currentNode != null) {
            // 将当前节点的值添加到结果集中
            result.add(currentNode.`val`)
            // 如果当前节点的右子树不为空，则将右子树添加到栈中
            if (currentNode.right != null) {
                nodeStack.push(currentNode.right)
            }
            currentNode = if (currentNode.left != null) {
                // 如果当前节点的左子树不为空，则将继续遍历左子树
                currentNode.left
            } else if (nodeStack.isNotEmpty()) {
                // 否则遍历右子树
                nodeStack.pop()
            } else {
                // 遍历完成，退出
                null
            }
        }
        return result
    }

    // 中序遍历的非递归实现
    fun inorderTraversal(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        var currentNode = root
        val nodeLink = LinkedList<TreeNode>()
        while (currentNode != null) {
            val leftNode = currentNode.left
            currentNode = if (leftNode != null) {
                // 如果左子树不为空，则将root节点加入nodeLink以便后面遍历
                currentNode.left = null
                nodeLink.add(currentNode)
                leftNode
            } else {
                // 如果左子树为空，则将root节点的值添加到结果集中
                result.add(currentNode.`val`)
                // 如果右子树不为空，则将右子树加入nodeLink以便后面遍历，否则取父节点遍历
                if (currentNode.right != null) {
                    currentNode.right
                } else if (nodeLink.isNotEmpty()) {
                    nodeLink.pollLast()
                } else {
                    null
                }
            }
        }
        return result
    }

    // 后序遍历的非递归实现
    fun postorderTraversal(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        var currentNode = root
        val nodeLink = LinkedList<TreeNode>()
        while (currentNode != null) {
            val leftNode = currentNode.left
            val rightNode = currentNode.right
            currentNode = if (leftNode != null) {
                // 如果左子树不为空，则将root节点(此时左节点为null)加入nodeLink以便后面遍历
                currentNode.left = null
                nodeLink.add(currentNode)
                leftNode
            } else if (rightNode != null) {
                // 如果右子树不为空，则将root节点（此时左右节点为null）加入nodeLink以便后面遍历
                currentNode.right = null
                nodeLink.add(currentNode)
                rightNode
            } else {
                // 为左右节点都为空，输出root节点的值，并取nodeLink中的最后一个节点（即离本node最近的一个节点）
                result.add(currentNode.`val`)
                nodeLink.pollLast()
            }
        }
        return result
    }

    // 层次遍历
    fun bfsTraverse(treeNode: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        val queue = LinkedList<TreeNode>()
        if (treeNode != null) {
            queue.add(treeNode)
        }
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            result.add(node.`val`)
            if (node.left != null) {
                queue.add(node.left!!)
            }
            if (node.right != null) {
                queue.add(node.right!!)
            }
        }
        return result
    }

    // 层次遍历打印(有瑕疵)
    fun bfsTraversePrint(root: TreeNode?): String {
        val result = mutableListOf<List<TreeNode?>>()
        var queueList = mutableListOf<TreeNode?>()

        var maxWidthValue = root?.`val`?.toString() ?: ""
        var hasNoNullNode = root != null
        queueList.add(root)
        while (queueList.isNotEmpty() && hasNoNullNode) {
            val tempNodeList = mutableListOf<TreeNode?>()
            hasNoNullNode = false

            queueList.forEach {
                tempNodeList.add(it?.left)
                tempNodeList.add(it?.right)

                hasNoNullNode = it?.left != null || it?.right != null || hasNoNullNode
                if ((it?.left?.`val`?.toString()?.length ?: 0) > maxWidthValue.length) {
                    maxWidthValue = (it?.left?.`val` ?: "").toString()
                }
                if ((it?.right?.`val`?.toString()?.length ?: 0) > maxWidthValue.length) {
                    maxWidthValue = (it?.right?.`val` ?: "").toString()
                }
            }
            result.add(queueList)
            queueList = tempNodeList
        }

        val stringBuilder = mutableListOf<String>()
        val length = result.size
        var preLevelWidth = 0
        result.reverse()
        result.forEachIndexed { index, treeNodes ->
            if (index == 0) {
                val levelTotalWidth = treeNodes.size.times(maxWidthValue.length) + (treeNodes.size - 1) * 2
                val perNode = levelTotalWidth / treeNodes.size
                val levelString = treeNodes.joinToString(" ") {
                    val leftEmptyNodeWidth = (perNode - (it?.`val`?.toString()?.length ?: 0)) / 2
                    " ".repeat(leftEmptyNodeWidth) + (it?.`val`?.toString() ?: " ") + " ".repeat(leftEmptyNodeWidth)
                }
                preLevelWidth = levelTotalWidth
                stringBuilder.add(levelString)
                stringBuilder.add("\n")
            } else {
                val perNode = preLevelWidth / treeNodes.size
                val levelString = treeNodes.joinToString("") {
                    val leftEmptyNodeWidth = (perNode - (it?.`val`?.toString()?.length ?: 0)) / 2
                    " ".repeat(leftEmptyNodeWidth) + (it?.`val`?.toString() ?: "") + " ".repeat(leftEmptyNodeWidth)

                }
                stringBuilder.add(levelString)
                stringBuilder.add("\n")
            }
        }

        return stringBuilder.reversed().joinToString("")
    }
}


fun main() {
    val nodeTree = Utils.createTreeFromString("[3,9,20,4,8,15,7,5,6,22,11,19,30,1,90]")

    println("层次遍历打印：\n${TreeTraverse.bfsTraversePrint(nodeTree)}")

    println("层次遍历：${TreeTraverse.bfsTraverse(nodeTree)}")

/*    // 后序
    val postorderTraversalRecursion = TreeTraverse.postorderTraversalRecursion(nodeTree)
    println("后序遍历递归：${postorderTraversalRecursion}")
    val postorderTraversal = TreeTraverse.postorderTraversal(nodeTree)
    println("后序遍历：  $postorderTraversal")


    // 中序
    val inorderTraversal = TreeTraverse.inorderTraversalRecursion(nodeTree)
    println("中序遍历递归：${inorderTraversal}")
    val inorderTraversal1 = TreeTraverse.inorderTraversal(nodeTree)
    println("中序遍历：  $inorderTraversal1")

    // 前序
    val preorderTraversalRecursion = TreeTraverse.preorderTraversalRecursion(nodeTree)
    println(preorderTraversalRecursion)
    val preorderTraversalCycle = TreeTraverse.preorderTraversalCycle(nodeTree)
    println(preorderTraversalCycle)*/

}