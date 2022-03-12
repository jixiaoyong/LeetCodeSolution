package medium

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/12
 * description:
 * 300. Longest Increasing Subsequence
 * https://leetcode.com/problems/longest-increasing-subsequence/
 * Given an unsorted array of integers, find the length of the longest increasing subsequence.
 * A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the
 * order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
 *
 * Constraints:

1 <= nums.length <= 2500
-104 <= nums[i] <= 104

 */
object LongestIncreasingSubsequence {

    /**
     * 1052 ms	faster than 9.33%
     * 40.7 MB  less than 74.61%
     */
    fun lengthOfLIS(nums: IntArray): Int {
        if (nums.size == 1) return 1

        val numAndLength = HashMap<Int, Int>()

        var length = 1
        numAndLength[nums[0]] = length

        for (i in 1 until nums.size) {
            val num = nums[i]
            val preNums = numAndLength.keys.toList()

            for (preNum in preNums) {
                if (preNum < num) {
                    val newPreLength = numAndLength[preNum]!! + 1
                    // 只有比当前记录的长度更长的才更新，防止丢失记录
                    numAndLength[num] = Math.max(numAndLength[num] ?: 0, newPreLength)
                    length = Math.max(length, newPreLength)
                } else if (preNum > num && !numAndLength.containsKey(num)) {
                    // 如果已有的数字比当前数字大，并且没有记录过当前num，才将其长度设置为1，以防重置了已有的计算结果
                    numAndLength[num] = 1
                }
            }
        }

        return length
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(lengthOfLIS(intArrayOf(10, 9, 2, 5, 3, 7, 101, 18)))
        println(lengthOfLIS(intArrayOf(10, 9, 2, -5, 3, 7, 101, 18)))
        println(lengthOfLIS(intArrayOf(10)))
        println(lengthOfLIS(intArrayOf(-10)))
        println(lengthOfLIS(intArrayOf(10, 10, 10, 10, 10, 10)))
        println(lengthOfLIS(intArrayOf(10, 10, -10, 10, 10, 10)))
        println(lengthOfLIS(intArrayOf(0, 1, 0, 3, 2, 3)))
    }
}