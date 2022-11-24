package medium

import easy.intArrayOrSizeToString
import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 795. 区间子数组个数
 *
 * 给你一个整数数组 nums 和两个整数：left 及 right 。
 * 找出 nums 中连续、非空且其中最大元素在范围[left, right] 内的子数组，并返回满足条件的子数组的个数。
 *
 * 生成的测试用例保证结果符合 32-bit 整数范围。
 *
 * 提示：
 *
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 * 0 <= left <= right <= 10^9
 *
 * https://leetcode.cn/problems/number-of-subarrays-with-bounded-maximum/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/24/2022
 */
class NumberOfSubarraysWithBoundedMaximum {

    /**
     * 模拟
     * 4606 ms	45.7 MB
     * 思路是依次以每个nums[i]为起点，组成子数组，如果子数组满足条件则计数
     * 时间复杂度O(n^2)，n为nums长度，会超时
     * 空间复杂度O(1)
     */
    fun numSubarrayBoundedMax(nums: IntArray, left: Int, right: Int): Int {
        var count = 0
        val range = IntRange(left, right)
        for (i in 0 until nums.size) {
            var max = nums[i]
            for (j in i until nums.size) {
                // 等效与从i开始，向后找到最后一个满足range.contains(max)的并累加数目，如果大于right则换下一个nums[i]
                val cur = nums[j]
                max = Math.max(max, cur)
                if (range.contains(max)) {
                    count++
                } else if (max > right) {
                    // 如果以num[i]为起点，向后组成子数组中最大值大于right，那么不满足条件，返回
                    break
                }
            }
        }

        return count
    }

    /**
     * 对[numSubarrayBoundedMax]的优化
     * 通过对题目要求分析：
     * 对于每个在nums中的数字i：
     * 1. 如果在[left,right]中，则以其为末尾的子数组数目为(i+1)-j个（即i到j的数字个数，不包括j），其中j为上一次数字大于right的下标+1（默认为0）
     * 比如[1,2,3]中，left为3，right为3，则满足条件的可以组成的子数组为[1,2,3],[2,3],[3]，为3个，也即（2+1）-0
     * 2. 如果小于left，则其满足条件的子数组个数取决于其左侧连续位置在[left,right]中的数字的个数
     * 3. 如果大于right，则更新大于right数字的下标
     *
     * 解题关键在于，满足条件的子数组以nums[i]为结尾，这样求解满足条件的子数组个数，可以以[73, 55, 36, 5, 55, 14, 9, 7, 72, 52] 32, 69
     * 为例，分别划分上述三种数字，统计子数组个数规律以理解解法
     *
     * 时间复杂度O(n)，n是nums长度
     * 空间复杂度O（1）
     */
    fun numSubarrayBoundedMaxPlus(nums: IntArray, left: Int, right: Int): Int {
        var count = 0
        val range = IntRange(left, right)
        var bigRight = 0
        var lastInnerIndex = 0
        var max = nums[0]
        for (i in 0 until nums.size) {
            val cur = nums[i]
            max = Math.max(max, cur)
            if (range.contains(cur)) {
                // 当前数字在[left,right]中，则其可以组成的满足条件的子数组个数为i向左侧第一个大于right数字之间的数字个数
                count += (i + 1) - bigRight
                lastInnerIndex = i
            } else if (range.contains(max)) {
                // 如果数字小于left，并且其左侧连续位置有满足[left,right]的数字，则其可以组成的满足条件的子数组个数为“[left,right]的数字”的个数
                count += lastInnerIndex + 1 - bigRight
            } else if (cur > right) {
                // 如果大于right，则更新下标
                bigRight = i + 1
                max = Int.MIN_VALUE
            }
        }

        return count
    }

    /**
     * 来自LeetCode官方解法
     * https://leetcode.cn/problems/number-of-subarrays-with-bounded-maximum/solution/qu-jian-zi-shu-zu-ge-shu-by-leetcode-sol-7it1/
     *
     */
    fun numSubarrayBoundedMaxLeetcode(nums: IntArray, left: Int, right: Int): Int {
        var res = 0
        var last2 = -1
        var last1 = -1
        for (i in nums.indices) {
            if (nums[i] >= left && nums[i] <= right) {
                last1 = i
            } else if (nums[i] > right) {
                last2 = i
                last1 = -1
            }
            if (last1 != -1) {
                res += last1 - last2
            }
        }
        return res
    }
}

fun main() {
    val obj = NumberOfSubarraysWithBoundedMaximum()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        Triple(intArrayOf(73, 55, 36, 5, 55, 14, 9, 7, 72, 52), 32, 69),/*22*/
        Triple(intArrayOf(2, 1, 4, 3), 2, 3),
        Triple(intArrayOf(2, 9, 2, 5, 6), 2, 8),
        Triple(intArrayOf(2, 9, 2, 5, 62, 5, 6262, 5, 12, 51, 2, 5, 6), 2, 18),
        Triple(/*19*/intArrayOf(
            876,
            880,
            482,
            260,
            132,
            421,
            732,
            703,
            795,
            420,
            871,
            445,
            400,
            291,
            358,
            589,
            617,
            202,
            755,
            810,
            227,
            813,
            549,
            791,
            418,
            528,
            835,
            401,
            526,
            584,
            873,
            662,
            13,
            314,
            988,
            101,
            299,
            816,
            833,
            224,
            160,
            852,
            179,
            769,
            646,
            558,
            661,
            808,
            651,
            982,
            878,
            918,
            406,
            551,
            467,
            87,
            139,
            387,
            16,
            531,
            307,
            389,
            939,
            551,
            613,
            36,
            528,
            460,
            404,
            314,
            66,
            111,
            458,
            531,
            944,
            461,
            951,
            419,
            82,
            896,
            467,
            353,
            704,
            905,
            705,
            760,
            61,
            422,
            395,
            298,
            127,
            516,
            153,
            299,
            801,
            341,
            668,
            598,
            98,
            241
        ), 658, 719
        ),
    )
    val largestArr = IntArray(100000) { i -> 100000 - i }
    cases.add(Triple(largestArr, 1, 50000))

    cases.forEach {
        println(
            "${
                obj.numSubarrayBoundedMaxPlus(
                    it.first,
                    it.second,
                    it.third
                )
            }  ||| ${it.second} -- ${it.third}  ---> ${intArrayOrSizeToString(it.first)}"
        )
//        println(
//            "${
//                obj.numSubarrayBoundedMaxLeetcode(
//                    it.first,
//                    it.second,
//                    it.third
//                )
//            }  ||| ${it.second} -- ${it.third}  ---> ${intArrayOrSizeToString(it.first)}"
//        )
    }
}