package easy

import java.util.*


/**
 * @author : jixiaoyong
 * @description ： 1827. 最少操作使数组递增
 *
 * 给你一个整数数组nums（下标从 0 开始）。每一次操作中，你可以选择数组中一个元素，并将它增加1。
 *
 * 比方说，如果nums = [1,2,3]，你可以选择增加nums[1]得到nums = [1,3,3]。
 * 请你返回使 nums严格递增的 最少操作次数。
 *
 * 我们称数组nums是 严格递增的，当它满足对于所有的0 <= i < nums.length - 1都有nums[i] < nums[i+1]。一个长度为 1的数组是严格递增的一种特殊情况。
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 5000
 * 1 <= nums[i] <= 10^4
 *
 * https://leetcode.cn/problems/minimum-operations-to-make-the-array-increasing/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/20/2022
 */
class MinimumOperationsToMakeArrayIncreasing {
    fun minOperations(nums: IntArray): Int {
        var minOpera = 0

        for (x in 1 until nums.size) {
            val pre = nums[x - 1]
            val cur = nums[x]
            val step = if (cur > pre) {
                0
            } else {
                pre + 1 - cur
            }
            nums[x] = cur + step
            minOpera += step
        }

        return minOpera
    }
}

fun main() {
    val obj = MinimumOperationsToMakeArrayIncreasing()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(intArrayOf(1, 1, 1), intArrayOf(8), intArrayOf(1, 5, 2, 4, 1), intArrayOf(1, 2, 3))

    val largestArr = IntArray(5000) {
        random.nextInt(10000)
    }
    cases.add(largestArr)

    cases.forEach {
        println("arr:${it.joinToString()}\noperations:${obj.minOperations(it)}\n")
    }
}