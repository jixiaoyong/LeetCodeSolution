package medium

import utils.TreeNode
import utils.Utils

/*
* @description:
* 105. 从前序与中序遍历序列构造二叉树
* https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
* 给定一棵树的前序遍历 preorder 与中序遍历  inorder。请构造二叉树并返回其根节点。
*
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2022/1/11
*/object ConstructBinaryTreeFromPreorderAndInorderTraversal {

    // 从前序与中序遍历序列构造二叉树（递归）
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        if (preorder.isEmpty() || inorder.isEmpty()) {
            return null
        }

        // 前序遍历的第一个元素为根节点
        val tree = TreeNode(preorder.first())

        if (preorder.size == 1) {
            return tree
        }

        // 中序遍历的根节点在中序遍历的中间，左边为左子树，右边为右子树
        val indexOfRootInOrder = inorder.indexOf(tree.`val`)
        val leftInOrder = inorder.toMutableList().subList(0, indexOfRootInOrder).toIntArray()
        val rightInOrder = inorder.toMutableList().subList(indexOfRootInOrder + 1, inorder.size).toIntArray()

        val leftPreOrder = mutableListOf<Int>()
        val rightPreOrder = mutableListOf<Int>()

        // 将前序遍历也分为左右子树的前序遍历结果
        for (i in 1 until preorder.size) {
            val it = preorder[i]
            if (leftInOrder.contains(it)) {
                leftPreOrder.add(it)
            } else {
                rightPreOrder.add(it)
            }
        }

        // 通过递归查找当前root节点的左右子树对应的节点（遍历结果的数组小了，难度降低）
        tree.left = buildTree(leftPreOrder.toIntArray(), leftInOrder)
        tree.right = buildTree(rightPreOrder.toIntArray(), rightInOrder)

        return tree
    }
}

fun main() {
    val node = ConstructBinaryTreeFromPreorderAndInorderTraversal.buildTree(
//        intArrayOf(3, 9, 20, 15, 7),
//        intArrayOf(9, 3, 15, 20, 7)
//        intArrayOf(1,2,4,5,3,6,7),
//        intArrayOf(4,2,5,1,6,3,7),
//        intArrayOf(1, 2,3),
//        intArrayOf(3,2,1),
//        intArrayOf(1, 2, 3),
//        intArrayOf(1,3,2),
        intArrayOf(1, 2, 3),
        intArrayOf(1, 2, 3),
    )
    node?.let { Utils.printTree(it) }
}