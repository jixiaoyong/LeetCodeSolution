package easy

/**
 * @author : jixiaoyong
 * @description ：2341. 数组能形成多少数对
 * 给你一个下标从 0 开始的整数数组 nums 。在一步操作中，你可以执行以下步骤：
 *
 * 从 nums 选出 两个 相等的 整数
 * 从 nums 中移除这两个整数，形成一个 数对
 * 请你在 nums 上多次执行此操作直到无法继续执行。
 *
 * 返回一个下标从 0 开始、长度为 2 的整数数组 answer 作为答案，其中 answer[0] 是形成的数对数目，answer[1] 是对 nums 尽可能执行上述操作后
 * 剩下的整数数目。
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 *
 * https://leetcode.cn/problems/maximum-number-of-pairs-in-array/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/2/16
 */
class MaximumNumberOfPairsInArray {


    /**
     * 思路：使用HashSet保存数字，如果遇到已有相同数字则将其移除，并将数对个数+1，剩余数字个数减2
     */
    fun numberOfPairs(nums: IntArray): IntArray {
        var pairCount = 0
        var leftCount = nums.size

        val set = hashSetOf<Int>()
        for (num in nums) {
            if (set.contains(num)) {
                pairCount++
                set.remove(num)
                leftCount -= 2
            } else {
                set.add(num)
            }
        }

        return intArrayOf(pairCount, leftCount)
    }
}

fun main() {
    val obj = MaximumNumberOfPairsInArray()
    val cases = arrayListOf(
        intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9), intArrayOf(1, 3, 2, 1, 3, 2, 2), intArrayOf(1, 1),
        intArrayOf(0), intArrayOf(1, 2, 3, 5, 23, 2, 12, 34, 32, 12, 4, 3)
    )

    for (case in cases) {
        val res = obj.numberOfPairs(case)
        println("res: ${res.joinToString()}  ---> case: ${case.joinToString()}")
    }
}