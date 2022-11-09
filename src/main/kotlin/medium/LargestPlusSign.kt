package medium

import java.util.*
import kotlin.collections.HashSet

/**
 * @author : jixiaoyong
 * @description ：764. 最大加号标志
 * 在一个 n x n 的矩阵grid中，除了在数组mines中给出的元素为0，其他每个元素都为1。mines[i] = [xi, yi]表示grid[xi][yi] == 0
 * 返回 grid 中包含1的最大的 轴对齐 加号标志的阶数 。如果未找到加号标志，则返回 0 。
 *
 * 一个k阶由1组成的 “轴对称”加号标志 具有中心网格grid[r][c] == 1，以及4个从中心向上、向下、向左、向右延伸，长度为k-1，由1组成的臂。
 * 注意，只有加号标志的所有网格要求为 1 ，别的网格可能为 0 也可能为 1 。
 *
 * 提示：
 *
 * 1 <= n <= 500
 * 1 <= mines.length <= 5000
 * 0 <= xi, yi < n
 * 每一对 (xi, yi) 都 不重复
 *
 * https://leetcode.cn/problems/largest-plus-sign/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/9/2022
 */
class LargestPlusSign {

    /**
     * 思路是，对于一个点来说，如果他作为中心点生成最大的十字的阶数，取决于他上下左右方向最小的连续为1的长度
     * 那么，构建dp表格，分别存储从4个方向开始连续的1的个数，那么每个点取这四个方向最小值，则为其能产生的最大十字阶数
     * 选取每个点能产生的十字最大阶数中最大的作为结果输出
     * 时间复杂度：O(n^2)，4个方向的表格要计算出来结果，以及最后计算result，都需要n*n次计算
     * 空间复杂度：O(n^2)，四个方向的连续1的个数要耗费n*n个大小空间
     */
    fun orderOfLargestPlusSign(n: Int, mines: Array<IntArray>): Int {
        val grid = Array(n) { IntArray(n) { 1 } }
        val leftGrid = Array(n) { IntArray(n) { 1 } }
        val upGrid = Array(n) { IntArray(n) { 1 } }
        val rightGrid = Array(n) { IntArray(n) { 1 } }
        val bottomGrid = Array(n) { IntArray(n) { 1 } }
        mines.forEach {
            grid[it[0]][it[1]] = 0
            leftGrid[it[0]][it[1]] = 0
            upGrid[it[0]][it[1]] = 0
            rightGrid[it[0]][it[1]] = 0
            bottomGrid[it[0]][it[1]] = 0
        }


        for (i in 1 until n) {
            for (j in 1 until n) {
                val num = grid[i][j]

                val left = leftGrid[i][j - 1]
                val top = upGrid[i - 1][j]

                if (num == 0) {
                    leftGrid[i][j] = 0
                    upGrid[i][j] = 0
                } else {
                    leftGrid[i][j] = left + 1
                    upGrid[i][j] = top + 1
                }

                val num2 = grid[n - i - 1][n - j - 1]
                val right = rightGrid[n - i - 1][n - j]
                val bottom = bottomGrid[n - i][n - j - 1]
                if (num2 == 0) {
                    rightGrid[n - i - 1][n - j - 1] = 0
                    bottomGrid[n - i - 1][n - j - 1] = 0
                } else {
                    rightGrid[n - i - 1][n - j - 1] = right + 1
                    bottomGrid[n - i - 1][n - j - 1] = bottom + 1
                }
            }
        }

        var result = 0
        for (i in 0 until n) {
            for (j in 0 until n) {
                val left = leftGrid[i][j]
                val up = upGrid[i][j]
                val right = rightGrid[i][j]
                val bottom = bottomGrid[i][j]
                val minimum = Math.min(Math.min(left, up), Math.min(right, bottom))
                result = Math.max(minimum, result)
            }
        }
        return result
    }


    /**
     * https://leetcode.cn/problems/largest-plus-sign/solution/zui-da-jia-hao-biao-zhi-by-leetcode-solu-jirt/
     * LeetCode官方方法
     * 遍历两次
     * 首先，从上到下，依次计算横轴上，左右方向的最小值
     * 然后，从左到右，依次计算竖轴上，上下方向与之前左右方向最小值的最小值，在此期间计算最大的阶数
     * 时间复杂度、空间复杂度 O(n^2)
     */
    fun orderOfLargestPlusSignLeetCode(n: Int, mines: Array<IntArray>): Int {
        val dp = Array(n) { IntArray(n) }
        for (i in 0 until n) {
            Arrays.fill(dp[i], n)
        }
        val banned: MutableSet<Int> = HashSet()
        for (vec in mines) {
            banned.add(vec[0] * n + vec[1])
        }
        var ans = 0
        for (i in 0 until n) {
            var count = 0
            /* left */for (j in 0 until n) {
                if (banned.contains(i * n + j)) {
                    count = 0
                } else {
                    count++
                }
                dp[i][j] = Math.min(dp[i][j], count)
            }
            count = 0
            /* right */for (j in n - 1 downTo 0) {
                if (banned.contains(i * n + j)) {
                    count = 0
                } else {
                    count++
                }
                dp[i][j] = Math.min(dp[i][j], count)
            }
        }
        for (i in 0 until n) {
            var count = 0
            /* up */for (j in 0 until n) {
                if (banned.contains(j * n + i)) {
                    count = 0
                } else {
                    count++
                }
                dp[j][i] = Math.min(dp[j][i], count)
            }
            count = 0
            /* down */for (j in n - 1 downTo 0) {
                if (banned.contains(j * n + i)) {
                    count = 0
                } else {
                    count++
                }
                dp[j][i] = Math.min(dp[j][i], count)
                ans = Math.max(ans, dp[j][i])
            }
        }
        return ans
    }
}

fun main() {
    val obj = LargestPlusSign()
    val cases = mutableListOf(
        Pair(2, arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(1, 1))),// 1
        Pair(8, arrayOf(intArrayOf(2, 3), intArrayOf(1, 2), intArrayOf(3, 7))),// 4
        Pair(9, arrayOf(intArrayOf(2, 3), intArrayOf(1, 2), intArrayOf(3, 7))),
        Pair(19, arrayOf(intArrayOf(2, 3), intArrayOf(1, 2), intArrayOf(3, 7))),
        Pair(5, arrayOf(intArrayOf(4, 2))),
        Pair(5, arrayOf(intArrayOf(2, 2))),
        Pair(1, arrayOf(intArrayOf(0, 0))),
    )

    cases.forEach {
        println()
        println("${obj.orderOfLargestPlusSign(it.first, it.second)}")
        println("${obj.orderOfLargestPlusSignLeetCode(it.first, it.second)}")
    }
}