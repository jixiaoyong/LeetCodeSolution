package easy

/**
 * @author : jixiaoyong
 * @description ：2441. 与对应负数同时存在的最大正整数
 * 给你一个 不包含 任何零的整数数组 nums ，找出自身与对应的负数都在数组中存在的最大正整数 k 。
 * 返回正整数 k ，如果不存在这样的整数，返回 -1 。
 * 提示：
 * 1 <= nums.length <= 1000
 * -1000 <= nums[i] <= 1000
 * nums[i] != 0
 * https://leetcode.cn/problems/largest-positive-integer-that-exists-with-its-negative/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/19/2022
 */
class LargestPositiveIntegerThatExistsWithItsNegative {

    /**
     * 使用hashSet保存遍历过的数字，然后每次对比看有无对应的负值，从其中选取最大值
     * 261 ms	37.1 MB
     * 时间复杂度O(N)
     * 空间复杂度O(n)
     */
    fun findMaxK(nums: IntArray): Int {
        var result = -1

        if (nums.size == 1) {
            return result
        }

        val numsSet = mutableSetOf<Int>()
        nums.forEach { num ->
            numsSet.add(num)
            if (numsSet.contains(-num)) {
                result = Math.max(result, Math.abs(num))
            }
        }

        return result
    }
}

fun main() {
    val obj = LargestPositiveIntegerThatExistsWithItsNegative()
    val numsList = listOf(
        intArrayOf(-1),
        intArrayOf(1),
        intArrayOf(-1, 2, -2),
        intArrayOf(-1, 2, -3, 3),
        intArrayOf(-1, 10, 6, 7, -7, 1),
        intArrayOf(-10, 8, 6, 7, -2, -3)
    )

    numsList.forEach {
        val result = obj.findMaxK(it)
        println("$result  --> ${it.joinToString()}")
    }
}