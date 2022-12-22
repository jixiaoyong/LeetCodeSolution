package hard

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1799. N 次操作后的最大分数和
 * 给你nums，它是一个大小为2 * n的正整数数组。你必须对这个数组执行 n次操作。
 *
 * 在第i次操作时（操作编号从 1开始），你需要：
 *
 * 选择两个元素x 和y。
 * 获得分数i * gcd(x, y)。
 * 将x和y 从nums中删除。
 * 请你返回 n次操作后你能获得的分数和最大为多少。
 *
 * 函数gcd(x, y)是x 和y的最大公约数。
 *
 * 提示：
 *
 * 1 <= n <= 7
 * nums.length == 2 * n
 * 1 <= nums[i] <= 10^6
 *
 * https://leetcode.cn/problems/maximize-score-after-n-operations/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/22/2022
 */
class MaximizeScoreAfterNOperations {

    /**
     * 无法处理多个gcd相同的情况
     */
    fun maxScore(nums: IntArray): Int {
        val gcds = mutableListOf<IntArray>()

        for (i in 0 until nums.size - 1) {
            for (j in i + 1 until nums.size) {
                val gcd = gcd(nums[i], nums[j])
                gcds.add(intArrayOf(gcd, i, j))
            }
        }

        gcds.sortBy { it[0] }

        val n = nums.size / 2
        var gcdIndex = gcds.size - 1
        val usedNumbers = mutableListOf<Int>()
        var result = 0

        // 如果同时有多个最大公约数相同的组合，需要依次对比并选取最优解
        for (i in n downTo 1) {
            while (gcdIndex >= 0) {
                val gcd = gcds[gcdIndex--]
                if (usedNumbers.contains(gcd[1]) || usedNumbers.contains(gcd[2])) {
                    continue
                }
                usedNumbers.add(gcd[1])
                usedNumbers.add(gcd[2])
                result += (gcd[0] * i)
                break
            }
        }

        return result
    }

     fun gcd(a: Int, b: Int): Int {
        var a = a
        var b = b
        while (b != 0) {
            val t = a
            a = b
            b = t % a
        }
        return a
    }

    /**
     * https://leetcode.cn/problems/maximize-score-after-n-operations/solution/n-ci-cao-zuo-hou-de-zui-da-fen-shu-he-by-i9k5/
     * Leetcode官方解法
     * 状态压缩 + 动态规划
     * 时间复杂度：O(2 ^ m \times m ^ 2 + \log C \times m ^ 2)
     * 空间复杂度：O(2 ^ m + m ^ 2)
     */
    fun maxScoreLeetcode(nums: IntArray): Int {
        val m = nums.size
        val dp = IntArray(1 shl m)
        val gcdTmp = Array(m) { IntArray(m) }
        for (i in 0 until m) {
            for (j in i + 1 until m) {
                gcdTmp[i][j] = gcd(nums[i], nums[j])
            }
        }

        // 每个nums[i]都有 丢弃/保留 2中选择，所以总的可能是2的m次方
        val all = 1 shl m
        for (s in 1 until all) {
            // s转化为二进制之后，1表示保留，0表示丢弃，代表nums每个数字的保留与丢弃情况
            val t = Integer.bitCount(s)
            if (t and 1 != 0) {
                // t是奇数
                continue
            }
            for (i in 0 until m) {
                if (s shr i and 1 != 0) {
                    // s包含了i
                    for (j in i + 1 until m) {
                        if (s shr j and 1 != 0) {
                            // s包含了j

                            // 保留nums[i]和nums[j]的情况下，最大分数和dp[s]为之前计算出的值，与此时这种情况计算出的值中较大值
                            // s xor (1 shl i) xor (1 shl j) 表示，从s中删除nums[i]和nums[j]之后的状态
                            // t/2表示此时n的值
                            // 整个式子的含义是，dp[s]的值取之前dp[s]的值，以及s中没有nums[i]和nums[j]值的情况时的dp值
                            // 与n*gcd[i,j]之和，中的最大值
                            // 因为是从小往大计算的，所以dp[s xor (1 shl i) xor (1 shl j)]此时肯定已知
                            dp[s] = Math.max(dp[s], dp[s xor (1 shl i) xor (1 shl j)] + t / 2 * gcdTmp[i][j])
                        }
                    }
                }
            }
        }
        // 这里取dp[all - 1]是因为，以4个数字为例，dp[all]表示的是10000的情况，dp[all-1]表示的是1111的情况，也正是题解
        return dp[all - 1]
    }
}

fun main() {
    val obj = MaximizeScoreAfterNOperations()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        intArrayOf(3, 3, 3, 3),
        intArrayOf(1, 2),
        intArrayOf(3, 4, 6, 8),
        intArrayOf(1, 2, 3, 4, 5, 6),
        intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
        intArrayOf(878394, 878394, 878394, 878394),//2635182
        intArrayOf(415, 230, 471, 705, 902, 87),//23
    )

    for (i in 1..7) {
        val array = IntArray(2 * i) {
            random.nextInt(1, 1000000)
        }
        cases.add(array)
    }

    for (case in cases) {
        println("${obj.maxScoreLeetcode(case)}   ${case.joinToString()}")
    }
}