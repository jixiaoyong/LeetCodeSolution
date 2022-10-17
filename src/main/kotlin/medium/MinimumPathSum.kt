package medium

import kotlin.random.Random


/**
 * @author : jixiaoyong
 * @description ： 64. 最小路径和
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/17/2022
 */
class MinimumPathSum {

    /**
     * 动态规划
     * f(n) = min(f(left) + f(top)) + grid[current]
     * 	366 ms	43.4 MB
     */
    fun minPathSum(grid: Array<IntArray>): Int {
        val firstRow = grid[0]

        val m = grid.size
        val n = firstRow.size

        var pre = IntArray(n)
        val cur = IntArray(n)

        pre[0] = firstRow[0]
        for (x in 1 until n) {
            pre[x] = pre[x - 1] + firstRow[x]
        }

        if (m == 1) {
            return pre[n-1]
        }

        for (i in 1 until m) {
            val row = grid[i]
            for (j in 0 until n) {
                if (j == 0) {
                    cur[j] = pre[j] + row[j]
                } else {
                    cur[j] = Math.min(cur[j - 1], pre[j]) + row[j]
                }
            }
            pre = cur.clone()
        }

        return cur[n - 1]
    }
}

fun main() {
    val obj = MinimumPathSum()
    var grid = arrayOf(intArrayOf(1, 3, 1), intArrayOf(1, 5, 1), intArrayOf(4, 2, 1))
    grid = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6))
    grid = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(4, 5, 26), intArrayOf(4, 15, 6))
    grid = arrayOf(intArrayOf(1, 2, 3))


    println(obj.minPathSum(grid))

}