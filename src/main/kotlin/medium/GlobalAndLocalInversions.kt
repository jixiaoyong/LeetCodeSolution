package medium

/**
 * @author : jixiaoyong
 * @description ： 775. 全局倒置与局部倒置
 * 给你一个长度为 n 的整数数组 nums ，表示由范围 [0, n - 1] 内所有整数组成的一个排列。
 *
 * 全局倒置 的数目等于满足下述条件不同下标对 (i, j) 的数目：
 *
 * 0 <= i < j < n
 * nums[i] > nums[j]
 * 局部倒置 的数目等于满足下述条件的下标 i 的数目：
 *
 * 0 <= i < n - 1
 * nums[i] > nums[i + 1]
 * 当数组 nums 中 全局倒置 的数量等于 局部倒置 的数量时，返回 true ；否则，返回 false 。
 *
 * 提示：
 *
 * n == nums.length
 * 1 <= n <= 5000
 * 0 <= nums[i] < n
 * nums 中的所有整数 互不相同
 * nums 是范围 [0, n - 1] 内所有数字组成的一个排列
 *
 * https://leetcode.cn/problems/global-and-local-inversions/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/16/2022
 */
class GlobalAndLocalInversions {

    /**
     * 全局倒置数量肯定 >= 局部倒置数量
     * 检查每个数字  A[i]，它的前面 [0,i−2] （相邻的不需要检查）存在比它大的数，肯定不满足题意
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    fun isIdealPermutation(nums: IntArray): Boolean {
        var max = nums[0]
        for (i in 2 until nums.size) {
            val cur = nums[i]
            if (cur < max) {
                return false
            }
            max = Math.max(max, nums[i - 1])

        }
        return true
    }
}

fun main() {
    val obj = GlobalAndLocalInversions()
    val cases = listOf(
        intArrayOf(2),
        intArrayOf(2,1),
        intArrayOf(2,3),
        intArrayOf(2, 0, 1),
        intArrayOf(1, 0, 2),
        intArrayOf(1, 2, 0),
        intArrayOf(1, 3, 2),
        intArrayOf(
            1,
            3,
            2,
            456,
            32,
            6,
            867,
            34,
            23,
            89,
            54,
            23,
            344,
            22,
            0,
            324,
            43,
            2334,
            232,
            653,
            634,
            222,
            4535,
            643,
            234,
            5000
        )
    )

    cases.forEach {
        val result = obj.isIdealPermutation(it)
        println("$result --> ${it.joinToString()}")
    }
}