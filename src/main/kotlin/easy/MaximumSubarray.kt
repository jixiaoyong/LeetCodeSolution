package easy

/*
* @description: 53. 最大子数组和
* 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
* 子数组 是数组中的一个连续部分。
* https://leetcode.com/problems/maximum-subarray/
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2022/1/29
*/
object MaximumSubarray {
    fun maxSubArray(nums: IntArray): Int {

        var currentSum = 0

        if (nums.isEmpty()) {
            return 0
        }

        var maxSum = nums.first()

        nums.forEach { i ->
            val current = i + currentSum
            currentSum = if (current > 0) {
                current
            } else {
                i
            }

            if (currentSum > maxSum) {
                maxSum = currentSum
            }

            if (currentSum < 0) {
                currentSum = 0
            }
        }

        return maxSum
    }
}

fun main() {
    val nums = intArrayOf(-2, 1, -3, 4, -1, -2, -1, -5, -4)
    println(MaximumSubarray.maxSubArray(nums))
}