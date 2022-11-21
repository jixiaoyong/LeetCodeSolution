package medium

import kotlin.math.roundToInt

/**
 * @author : jixiaoyong
 * @description ：808. 分汤
 * 有A和B 两种类型的汤。一开始每种类型的汤有n毫升。有四种分配操作：
 *
 * 提供 100ml 的 汤A 和 0ml 的 汤B 。
 * 提供 75ml 的 汤A 和 25ml 的 汤B 。
 * 提供 50ml 的 汤A 和 50ml 的 汤B 。
 * 提供 25ml 的 汤A 和 75ml 的 汤B 。
 * 当我们把汤分配给某人之后，汤就没有了。每个回合，我们将从四种概率同为 0.25 的操作中进行分配选择。
 * 如果汤的剩余量不足以完成某次操作，我们将尽可能分配。当两种类型的汤都分配完时，停止操作。
 *
 * 注意不存在先分配 100 ml 汤B 的操作。
 *
 * 需要返回的值：汤A先分配完的概率 +汤A和汤B同时分配完的概率 / 2。返回值在正确答案10-5的范围内将被认为是正确的。
 *
 *
 * 提示:
 *
 * 0 <= n <= 10^9
 *
 * 掌握程度：★☆☆☆☆
 *
 * https://leetcode.cn/problems/soup-servings/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/21/2022
 */
class SoupServings {

    var result = 0.0
    fun soupServings(n: Int): Double {
        result = 0.0
        recursion(n, n, 1.0)
        return result
    }

    /**
     * 递归，会Stack Overflow
     */
    fun recursion(a: Int, b: Int, percent: Double) {
        if (a <= 0 && b > 0) {
            result += percent
            return
        } else if (a <= 0 && b <= 0) {
            result += percent * 0.5
            return
        } else if (b <= 0) {
            return
        }

        recursion(a - 100, b, percent * 0.25)
        recursion(a - 75, b - 25, percent * 0.25)
        recursion(a - 50, b - 50, percent * 0.25)
        recursion(a - 25, b - 75, percent * 0.25)
    }

    /**
     * https://juejin.cn/post/6844904148513587208
     * https://leetcode.cn/problems/soup-servings/solution/fen-tang-by-leetcode-solution-0yxs/
     * 时间复杂度为O(C^2)，C为常数4475，即当大于C时，直接返回结果1，时间复杂度为O(1)；小于C时，则需要的时间复杂度为O(n^2)，
     *      所以总的时间复杂度为O(C^2)
     * 空间复杂度：O(C^2)
     */
    fun soupServingsLoop(n: Int): Double {
        if (n >= 4475) {
            // a比b先倒完的概率大，当 n≥4475 时，所求概率已经大于 0.99999了，已经满足题目精度：返回值在正确答案10-5的范围内将被认为是正确的。
            return 1.0
        }

        val size = Math.ceil(n / 25.0).toInt()
        // dp[a][b] = 在剩余 a mLA汤 和 b mLB汤的情况下，需要计算出的概率
        val dp = Array(size + 1) { DoubleArray(size + 1) }
        dp[0][0] = 0.5 // a,b都为0时，同时分配完毕的概率为1，根据题目要求，这里只记录其概率*0.5，所以为1*0.5
        for (i in 1..size) {
            // b已经分配完毕了，而a还未分完，所以概率是0%
            dp[i][0] = 0.0
            // a剩余0，而b还未分完，所以概率是100%
            dp[0][i] = 1.0
        }

        for (i in 1..size) {
            for (j in 1..size) {
                dp[i][j] =
                    0.25 * (dp[Math.max(i - 4, 0)][j]
                            + dp[Math.max(i - 3, 0)][j - 1]
                            + dp[Math.max(i - 2, 0)][Math.max(j - 2, 0)]
                            + dp[Math.max(i - 1, 0)][Math.max(j - 3, 0)])
            }
        }

        return dp[size][size]
    }
}

fun main() {
    val obj = SoupServings()
    val cases = intArrayOf(25, 50, 75, 100, 125, 1238,5000,6000, 1000003, 1000000000)
    cases.forEach {
//        val result = obj.soupServings(it)
        val result = ""
        val result2 = obj.soupServingsLoop(it)
        println("$result / $result2 -->$it")
    }
}