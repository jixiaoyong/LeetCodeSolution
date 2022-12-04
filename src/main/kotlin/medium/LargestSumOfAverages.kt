package medium

/**
 * @author : jixiaoyong
 * @description ： 813. 最大平均值和的分组
 *
 * 给定数组nums和一个整数k。我们将给定的数组nums分成最多k个相邻的非空子数组 。分数 由每个子数组内的平均值的总和构成。
 *
 * 注意我们必须使用 nums 数组中的每一个数进行分组，并且分数不一定需要是整数。
 *
 * 返回我们所能得到的最大 分数 是多少。答案误差在10-6内被视为是正确的。
 *
 * 提示:
 *
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 104
 * 1 <= k <= nums.length
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/28/2022
 */
class LargestSumOfAverages {

    /**
     * 参考：https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-813-largest-sum-of-averages/
     * 思路图：https://zxi.mytechroad.com/blog/wp-content/uploads/2018/04/813-ep179.png
     * 时间复杂度：O(k×n^2)
     * 空间复杂度:O(k×n)
     */
    fun largestSumOfAverages(nums: IntArray, K: Int): Double {
        val numsSize = nums.size

        // 前缀和，sumArr[i]代表nums中前i个数字之和
        val sumArr = DoubleArray(numsSize + 1)
        for (i in 1..numsSize) {
            sumArr[i] = nums[i - 1] + sumArr[i - 1]
        }

        // dp[k][i] 表示将前i个元素分为k份时的最大平均和，k为1时，最大平均和即nums平均数
        val dp = Array(K + 1) { DoubleArray(numsSize + 1) }
        for (i in 1..numsSize) {
            dp[1][i] = sumArr[i] / i.toDouble()
        }

        // 思路图：https://zxi.mytechroad.com/blog/wp-content/uploads/2018/04/813-ep179.png
        // dp[k][i] = 所有 将nums分为[0~j]和[j~i-1]两部分得到的平均和 中的最大值，其中j在(k-1)~(i-1)
        //          = max( dp[k-1][j] + avg( nums[j], nums[i-1] ) ), j in (k-1)~(i-1)
        for (k in 2..K) {
            for (i in k..numsSize) {
                for (j in k - 1 until i) {
                    dp[k][i] = Math.max(dp[k][i], dp[k - 1][j] + (sumArr[i] - sumArr[j]) / (i - j).toDouble())
                }
            }
        }

        return dp[K][numsSize]
    }

    fun largestSumOfAveragesLeetcode(nums: IntArray, k: Int): Double {
        val n = nums.size
        val prefix = DoubleArray(n + 1)
        for (i in 0 until n) {
            prefix[i + 1] = prefix[i] + nums[i]
        }

        val dp = Array(n + 1) { DoubleArray(k + 1) }
        for (i in 1..n) {
            dp[i][1] = prefix[i] / i
        }
        for (j in 2..k) {
            for (i in j..n) {
                for (x in j - 1 until i) {
                    dp[i][j] = Math.max(dp[i][j], dp[x][j - 1] + (prefix[i] - prefix[x]) / (i - x))
                }
            }
        }
        return dp[n][k]
    }
}

fun main() {
    val obj = LargestSumOfAverages()
    val cases = mutableListOf(
        Pair(intArrayOf(9, 1, 2, 3, 9), 3),
        Pair(intArrayOf(1, 2, 3, 4, 5, 6, 7), 4),
        Pair(intArrayOf(1, 2, 3, 4, 5, 6, 7), 3),
        Pair(intArrayOf(9, 1, 2, 3, 9), 2),
        Pair(intArrayOf(9, 1, 2, 3, 9, 3, 6, 77, 5), 3),
    )

    for (case in cases) {
        println(
            "${
                obj.largestSumOfAverages(
                    case.first,
                    case.second
                )
            }   -->${case.second} / ${case.first.joinToString()}"
        )
        println(
            "${
                obj.largestSumOfAveragesLeetcode(
                    case.first,
                    case.second
                )
            } Leetcode  -->${case.second} / ${case.first.joinToString()}"
        )
    }
}