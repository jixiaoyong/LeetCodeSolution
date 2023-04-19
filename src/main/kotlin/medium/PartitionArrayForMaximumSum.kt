package medium

/**
 * @author : jixiaoyong
 * @description ：1043. 分隔数组以得到最大和
 * 给你一个整数数组 arr，请你将该数组分隔为长度 最多 为 k 的一些（连续）子数组。分隔完成后，每个子数组的中的所有值都会变为该子数组中的最大值。
 *
 * 返回将数组分隔变换后能够得到的元素最大和。本题所用到的测试用例会确保答案是一个 32 位整数。
 *
 * 提示：
 *
 * 1 <= arr.length <= 500
 * 0 <= arr[i] <= 10^9
 * 1 <= k <= arr.length
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/4/19
 */
class PartitionArrayForMaximumSum {

    /**
     * 思路：分隔数组以获得最大和，使用dp记录之前0到i-1的arr满足题设的最大和，此时如果有一个新的数字arr[i]，则可能有如下情况：
     * 1. 直接将arr[i]添加到之前的数组之后
     * 2. 将包括arr[i]在内的向前第1~k个数字(令其第一个数字的下标为j)作为一个数组，其组成的数组的最大和为其中最大值*数组长度，
     *    此时arr对应的最大和为0~j的最大和+当前子数组的最大和
     *    那么从上述几种情况中选择最大的情况作为dp[i+1]的值
     *
     * 最后返回dp[len]的值则为arr的最大和
     *
     * 时间复杂度：O(KN)
     * 控件复杂度：O(N)
     */
    fun maxSumAfterPartitioning(arr: IntArray, k: Int): Int {
        val len = arr.size
        // dp[i]表示arr[0]到arr[i-1]的值
        val dp = IntArray(len + 1)

        for (i in 1..len) {
            // 从后（arr[i-1]）向前(arr[j])遍历
            var max = arr[i - 1]
            // 只需对比当前数字，和其之前的k-1个数字组成子数组的情况
            for (j in i downTo i - k + 1) {
                if (j < 1) {
                    break
                }
                // 每次都需要更新max的值，以便计算当前区间的最大和
                max = Math.max(max, arr[j - 1])
                // 这里dp[i]的值为所有情况中最大的值
                // dp[j-1] + (i - j + 1) * max 表示当前arr[j-1]的最大区间和+arr[j~i]区间的最大和
                dp[i] = Math.max(dp[i], dp[j - 1] + (i - j + 1) * max)
            }
        }

        return dp[len]
    }
}

fun main() {
    val obj = PartitionArrayForMaximumSum()
    val cases = arrayListOf(
        Pair(intArrayOf(1, 15, 7, 9, 2, 5, 10), 3),
        Pair(intArrayOf(1, 15, 7, 9, 2, 5, 10), 1),
        Pair(intArrayOf(1, 15, 7, 9, 2, 5, 10), 7),
        Pair(intArrayOf(1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3), 4),
        Pair(intArrayOf(1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3), 5),
        Pair(intArrayOf(1), 1),
    )

    cases.forEach {
        val res = obj.maxSumAfterPartitioning(it.first, it.second)
        println("$res  --${it.first.joinToString()}  --- ${it.second}")
    }
}