package easy

import kotlin.random.Random

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/11/27
 * description: 1752. 检查数组是否经排序和轮转得到
 *
 * 给你一个数组 nums 。nums 的源数组中，所有元素与 nums 相同，但按非递减顺序排列。
 *
 * 如果nums 能够由源数组轮转若干位置（包括 0 个位置）得到，则返回 true ；否则，返回 false 。
 *
 * 源数组中可能存在 重复项 。
 *
 * 注意：我们称数组 A 在轮转 x 个位置后得到长度相同的数组 B ，当它们满足 A[i] == B[(i+x) % A.length] ，其中 % 为取余运算。
 *
 * 提示：
 *
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 100
 *
 * https://leetcode.cn/problems/check-if-array-is-sorted-and-rotated/
 */
class CheckIfArrayIsSortedAndRotated {


    /**
     * 思路，找规律，如果从非递减顺序排列的源数组轮转x之后得到[nums]，那么最多有一个位置会因为源数组的第一位和最后一位轮转到了一起，从
     * 而出现了递减，其他地方都不会出现递减，也就是[nums]中如果最多只有一个递减的地方，那么他就可以从源数组轮转x之后得到
     * 时间复杂度O(n)，n为[nums]的长度
     * 空间复杂度O(1)，只需要一个变量存储[nums]中递减位置的个数
     */
    fun check(nums: IntArray): Boolean {
        var decreaseCount = 0

        for (i in 0 until nums.size - 1) {

            if (nums[i] > nums[i + 1]) {
                // 如果当前值大于下一个值，则说明发生了递减，decreaseCount+1
                if (++decreaseCount > 1) {
                    // 如果递减次数大于1，则说明不满足题目要求
                    return false
                }
            }

        }

        if (nums.last() > nums.first() && decreaseCount > 0) {
            // 因为上面只判断了nums[0~len-2]与其后一位数字的对比，这里对比[nums.last]和[nums.first]是否递减
            // 之所以放到这里是为了避免每次在上面的遍历里面判断一次是否发生下标越界
            return false
        }

        return true
    }
}

fun main() {
    val obj = CheckIfArrayIsSortedAndRotated()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        intArrayOf(3, 4, 5, 1, 2), intArrayOf(2, 1, 3, 4), intArrayOf(1, 2, 3),
        intArrayOf(1, 2, 3, 4, 2), intArrayOf(1, 2, 3, 4, 1), intArrayOf(1, 2, 3, 3),
    )

    val largestArr = IntArray(100) { random.nextInt(101) }
    val largestSortedArr = IntArray(100) { i -> i }

    cases.add(largestArr)
    cases.add(largestSortedArr)

    cases.forEach {
//        println("${obj.check(it)}   --> ${intArrayOrSizeToString(it)}")
        println("${obj.check(it)}   --> [${it.joinToString()}]")
    }

}