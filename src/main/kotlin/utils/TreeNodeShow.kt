package utils

/*
 * @description: TODO
 * https://jueee.github.io/2021/03/2021-03-05-Java%E6%8C%89%E7%85%A7%E6%A0%91%E5%BD%A2%E7%BB%93%E6%9E%84%E6%89%93%E5%8D%B0%E4%BA%8C%E5%8F%89%E6%A0%91/
 * @date: 2021/12/22
 */
object TreeNodeShow {
    // 用于获得树的层数
    private fun getTreeDepth(root: TreeNode?): Int {
        return if (root == null) 0 else 1 + Math.max(getTreeDepth(root.left), getTreeDepth(root.right))
    }

    private fun writeArray(
        currNode: TreeNode?,
        rowIndex: Int,
        columnIndex: Int,
        res: Array<Array<String?>>,
        treeDepth: Int
    ) {
        // 保证输入的树不为空
        if (currNode == null) return
        // 先将当前节点保存到二维数组中
        res[rowIndex][columnIndex] = currNode.`val`.toString()

        // 计算当前位于树的第几层
        val currLevel = (rowIndex + 1) / 2
        // 若到了最后一层，则返回
        if (currLevel == treeDepth) return
        // 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
        val gap = treeDepth - currLevel - 1

        // 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
        if (currNode.left != null) {
            res[rowIndex + 1][columnIndex - gap] = "/"
            writeArray(currNode.left, rowIndex + 2, columnIndex - gap * 2, res, treeDepth)
        }

        // 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
        if (currNode.right != null) {
            res[rowIndex + 1][columnIndex + gap] = "\\"
            writeArray(currNode.right, rowIndex + 2, columnIndex + gap * 2, res, treeDepth)
        }
    }

    fun show(root: TreeNode?) {
        if (root == null) {
            println("EMPTY!")
            return
        }
        // 得到树的深度
        val treeDepth = getTreeDepth(root)

        // 最后一行的宽度为2的（n - 1）次方乘3，再加1
        // 作为整个二维数组的宽度
        val arrayHeight = treeDepth * 2 - 1
        val arrayWidth = (2 shl treeDepth - 2) * 3 + 1
        // 用一个字符串数组来存储每个位置应显示的元素
        val res = Array(arrayHeight) { arrayOfNulls<String>(arrayWidth) }
        // 对数组进行初始化，默认为一个空格
        for (i in 0 until arrayHeight) {
            for (j in 0 until arrayWidth) {
                res[i][j] = " "
            }
        }

        // 从根节点开始，递归处理整个树
        // res[0][(arrayWidth + 1)/ 2] = (char)(root.val + '0');
        writeArray(root, 0, arrayWidth / 2, res, treeDepth)

        // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
        for (line in res) {
            val sb = StringBuilder()
            var i = 0
            while (i < line.size) {
                sb.append(line[i])
                if (line[i]!!.length > 1 && i <= line.size - 1) {
                    i += if (line[i]!!.length > 4) 2 else line[i]!!.length - 1
                }
                i++
            }
            println(sb.toString())
        }
    }
}