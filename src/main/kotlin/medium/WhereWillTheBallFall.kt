package medium

/**
 * @author : jixiaoyong
 * @description ： 1706. 球会落何处
 * 用一个大小为 m x n 的二维网格 grid 表示一个箱子。你有 n 颗球。箱子的顶部和底部都是开着的。
 *
 * 箱子中的每个单元格都有一个对角线挡板，跨过单元格的两个角，可以将球导向左侧或者右侧。
 *
 * 将球导向右侧的挡板跨过左上角和右下角，在网格中用 1 表示。
 * 将球导向左侧的挡板跨过右上角和左下角，在网格中用 -1 表示。
 * 在箱子每一列的顶端各放一颗球。每颗球都可能卡在箱子里或从底部掉出来。如果球恰好卡在两块挡板之间的 "V" 形图案，或者被一块挡导向到箱子的任意一侧边上，就会卡住。
 *
 * 返回一个大小为 n 的数组 answer ，其中 answer[i] 是球放在顶部的第 i 列后从底部掉出来的那一列对应的下标，如果球卡在盒子里，则返回 -1 。
 *
 * 提示：
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * grid[i][j] 为 1 或 -1
 *
 * https://leetcode.cn/problems/where-will-the-ball-fall/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/1/2022
 */
class WhereWillTheBallFall {
    /**
     * 	252 ms	44.6 MB
     * 思路是每个入口都dfs，每到一个节点，区分1还是-1，找他的下方或者左右方是否可以走通，如果可以则继续dfs，否则返回-1
     * 时间复杂度，O(MN），最坏情况下每个网格都遍历一次
     * 空间复杂度O(1)
     */
    fun findBall(grid: Array<IntArray>): IntArray {
        val columnCount = grid[0].size
        val result = IntArray(columnCount) { -1 }

        if (columnCount == 1) {
            return result
        }

        for (index in 0 until columnCount) {
            result[index] = dfs(grid, 0, index, true, columnCount)
        }

        return result
    }


    private fun dfs(grid: Array<IntArray>, row: Int, col: Int, up: Boolean, columnCount: Int): Int {
        if (col < 0 || col >= columnCount) {
            // 越界了
            return -1
        }
        if (row >= grid.size) {
            // 到达出口
            return col
        }

        val node = grid[row][col]
        return if (node == 1) {
            if (up) {
                // 看右侧是否有1，如果是-1则返回
                if (col + 1 >= columnCount || grid[row][col + 1] == -1) {
                    -1
                } else {
                    dfs(grid, row, col + 1, false, columnCount)
                }

            } else {
                // 看下方是1还是-1，或者为末尾
                dfs(grid, row + 1, col, true, columnCount)
            }
        } else {
            if (up) {
                // 看左侧是否为-1，不是则返回
                if (col - 1 < 0 || grid[row][col - 1] == 1) {
                    -1
                } else {
                    dfs(grid, row, col - 1, false, columnCount)
                }
            } else {
                // 看下方是1还是-1，或者为末尾
                dfs(grid, row + 1, col, true, columnCount)
            }
        }

    }

    /**
     * 官方思路利用了grid中的数字，1则右移，-1则左移，避免了判断-1还是1然后再移动的逻辑
     * 复杂度分析
     * 时间复杂度：O(m×n)，其中 m 和 n 是网格的行数和列数。外循环消耗 O(n)，内循环消耗 O(m)。
     * 空间复杂度：O(1)。返回值不计入空间。
     * https://leetcode.cn/problems/where-will-the-ball-fall/solution/qiu-hui-luo-he-chu-by-leetcode-solution-xqop/
     */
    fun findBallLeetcode(grid: Array<IntArray>): IntArray {
        val n = grid[0].size
        val ans = IntArray(n)
        for (j in 0 until n) {
            var col = j // 球的初始列
            for (row in grid) {
                val dir = row[col]
                col += dir // 移动球
                if (col < 0 || col == n || row[col] != dir) {  // 到达侧边或 V 形
                    col = -1
                    break
                }
            }
            ans[j] = col // col >= 0 为成功到达底部
        }
        return ans
    }
}

fun main() {
    val obj = WhereWillTheBallFall()

    val cases = mutableListOf(
        arrayOf(
            intArrayOf(1, 1, 1, 1, 1, 1),
            intArrayOf(-1, -1, -1, -1, -1, -1),
            intArrayOf(1, 1, 1, 1, 1, 1),
            intArrayOf(-1, -1, -1, -1, -1, -1),
        ),
        arrayOf(
            intArrayOf(1, 1, 1, -1, -1),
            intArrayOf(1, 1, 1, -1, -1),
            intArrayOf(-1, -1, -1, 1, 1),
            intArrayOf(1, 1, 1, 1, -1),
            intArrayOf(-1, -1, -1, -1, -1),
        ),
        arrayOf(
            intArrayOf(1, 1, 1, -1, -1),
            intArrayOf(1, 1, 1, -1, -1),
            intArrayOf(-1, -1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, -1),
            intArrayOf(-1, -1, -1, -1, -1),
        ),
        arrayOf(intArrayOf(1)),
        arrayOf(intArrayOf(-1)),
    )

    cases.forEach {
        val result = obj.findBall(it)
        println(
            "${result.joinToString()} --> \n${
                it.joinToString("") {
                    it.joinToString("") + "\n"
                }.replace("-1", "/").replace("1", "\\")
            }"
        )
    }
}