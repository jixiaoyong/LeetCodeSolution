package medium

/**
 * @author : jixiaoyong
 * @description ： 62. 不同路径
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * 问总共有多少条不同的路径？
 *
 * https://leetcode.cn/problems/unique-paths/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/13/2022
 */
class UniquePaths {
    /**
     * 12*28 output 1203322288, timeout
     */
    fun uniquePaths(m: Int, n: Int): Int {
        if (m < 0 || n < 0) {
            return 0
        }
        if (n == 1 || m == 1) {
            return 1
        }
        return uniquePaths(m - 1, n) + uniquePaths(m, n - 1)
    }

    // 这里计算下标从0开始，相比于uniquePaths1，初始化pathsArray时节省了时间
    // 121 ms	32.6 MB
    // 空间复杂度O(MN)
    fun uniquePaths2(m: Int, n: Int): Int {
        if (m == 1 || n == 1) {
            return 1
        }

        val pathsArray = Array(m) { i ->
            if (i == 0) {
                IntArray(n) { index -> 1 }
            } else {
                IntArray(n) { index ->
                    if (index == 0) 1 else 0
                }
            }
        }

        for (i in 1 until m) {
            for (j in 1 until n) {
                pathsArray[i][j] = pathsArray[i - 1][j] + pathsArray[i][j - 1]
            }
        }

        return pathsArray[m - 1][n - 1]
    }


    /**
     * 参考leetcode优化 作者：powcai
     * 链接：https://leetcode.cn/problems/unique-paths/solution/dong-tai-gui-hua-by-powcai-2
     ***/

    /**
     * 此例的思路是，表格为横向*竖向：m*n，沿着横向从0到m开始遍历，计算每列n对应的路径数目，直到第m列
     * 空间复杂度为O(2N)
     * 277 ms	33.5 MB 有一次为Runtime: 137 ms，Memory Usage: 32.7 MB
     */
    fun uniquePaths3(m: Int, n: Int): Int {

        var pre = IntArray(n) { _ -> 1 }
        val cur = IntArray(n) { _ -> 1 }

        for (i in 1 until m) {
            for (j in 1 until n) {
                cur[j] = pre[j] + cur[j - 1]
            }
            pre = cur.clone()
        }

        return pre[n - 1]
    }

    /**
     * uniquePaths3的进化版本，空间复杂度为O(2min(M,N))
     * 213 ms	33.6 MB
     */
    fun uniquePaths4(m: Int, n: Int): Int {

        var min = 0
        var max = 0

        if (m > n) {
            max = m
            min = n
        } else {
            max = n
            min = m
        }

        var pre = IntArray(min) { _ -> 1 }
        val cur = IntArray(min) { _ -> 1 }

        for (i in 1 until max) {
            for (j in 1 until min) {
                cur[j] = pre[j] + cur[j - 1]
            }
            pre = cur.clone()
        }

        return pre[min - 1]
    }

    /**
     * uniquePaths4的进化版本，空间复杂度为O(min(M,N))
     * 247 ms	33.6 MB
     */
    fun uniquePaths5(m: Int, n: Int): Int {

        var min = 0
        var max = 0

        if (m > n) {
            max = m
            min = n
        } else {
            max = n
            min = m
        }

        val cur = IntArray(min) { _ -> 1 }

        for (i in 1 until max) {
            for (j in 1 until min) {
                cur[j] = cur[j] + cur[j - 1]
            }
        }

        return cur[min - 1]
    }
    /**
     * uniquePaths4的进化版本，空间复杂度为O(N))
     * 207 ms	33.5 MB
     */
    fun uniquePaths6(m: Int, n: Int): Int {
        val cur = IntArray(n) { _ -> 1 }

        for (i in 1 until m) {
            for (j in 1 until n) {
                cur[j] = cur[j] + cur[j - 1]
            }
        }

        return cur[n - 1]
    }

    /**
     * 从0,0开始，计算每个节点的路径数目，直到m,n
     * 209 ms	33.1 MB
     * 作者：帅地
     * 链接：https://www.zhihu.com/question/23995189/answer/1094101149
     */
    fun uniquePaths1(m: Int, n: Int): Int {
        if (m <= 0 || n <= 0) {
            return 0
        }
        val dp = Array(m) { IntArray(n) } //
// 初始化
        for (i in 0 until m) {
            dp[i][0] = 1
        }
        for (i in 0 until n) {
            dp[0][i] = 1
        }
// 推导出 dp[m-1][n-1]
        for (i in 1 until m) {
            for (j in 1 until n) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
            }
        }
        return dp[m - 1][n - 1]
    }
}

fun main() {
    println(UniquePaths().uniquePaths1(12, 28))
    println(UniquePaths().uniquePaths2(12, 28))
    println(UniquePaths().uniquePaths3(12, 28))
}