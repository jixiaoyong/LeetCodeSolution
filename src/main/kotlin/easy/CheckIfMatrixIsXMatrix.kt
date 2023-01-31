package easy

/**
 * @author : jixiaoyong
 * @description ：2319. 判断矩阵是否是一个 X 矩阵
 * 如果一个正方形矩阵满足下述 全部 条件，则称之为一个 X 矩阵 ：
 *
 * 矩阵对角线上的所有元素都 不是 0
 * 矩阵中所有其他元素都是 0
 * 给你一个大小为 n x n 的二维整数数组 grid ，表示一个正方形矩阵。如果 grid 是一个 X 矩阵 ，返回 true ；否则，返回 false 。
 *
 * 提示：
 *
 * n == grid.length == grid[i].length
 * 3 <= n <= 100
 * 0 <= grid[i][j] <= 10^5
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/31/2023
 */
class CheckIfMatrixIsXMatrix {
    fun checkXMatrix(grid: Array<IntArray>): Boolean {
        val n = grid.size
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (j == i || j == n - i - 1) {
                    if (grid[i][j] == 0) {
                        return false
                    }
                } else if (grid[i][j] != 0) {
                    return false
                }
            }
        }
        return true
    }
}

fun main() {
    val obj = CheckIfMatrixIsXMatrix()
    val cases = mutableListOf(
        arrayOf(intArrayOf(2, 0, 0, 1), intArrayOf(0, 3, 1, 0), intArrayOf(0, 5, 2, 0), intArrayOf(4, 0, 0, 2)),
        arrayOf(intArrayOf(5, 7, 0), intArrayOf(0, 3, 1), intArrayOf(0, 5, 0)),
        arrayOf(intArrayOf(5, 0, 2), intArrayOf(0, 3, 0), intArrayOf(2, 0, 3)),
    )

    cases.forEach {
        println("${obj.checkXMatrix(it)} /// ${it.joinToString { it.joinToString() }}")
    }
}