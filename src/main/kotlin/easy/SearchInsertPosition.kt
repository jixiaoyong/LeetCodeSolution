package easy

/**
 * @author : jixiaoyong
 * @description ： 35. 搜索插入位置
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 请必须使用时间复杂度为 O(log n) 的算法。
 *
 * 提示:
 *
 * 1 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 为无重复元素的升序排列数组
 * -10^4 <= target <= 10^4
 *
 * https://leetcode.cn/problems/search-insert-position/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/23/2022
 */
class SearchInsertPosition {
    /**
     * 303 ms	39 MB
     * 二分查找
     * 时间复杂度为 O(log n)
     * 空间复杂度O(1)
     */
    fun searchInsert(nums: IntArray, target: Int): Int {
        val len = nums.size
        if (len == 1) {
            val num = nums[0]
            return if (target > num) 1 else 0
        }

        var left = 0
        var right = len - 1
        val numL = nums[left]
        val numR = nums[right]

        if (target <= numL) {
            return left
        } else if (target == numR) {
            return right
        } else if (target > numR) {
            return len
        }

        while (left < right) {
            val mid = (left + right) / 2
            val numM = nums[mid]

            if (target == numM) {
                return mid
            } else if (target > numM) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }

        val mid = (left + right) / 2
        return if (target <= nums[mid]) {
            mid
        } else {
            mid + 1
        }
    }

    /**
     *  LeetCode官方给出一个优化思路：将题目转化为【在一个有序数组中找第一个大于等于 target 的下标】
     *  因为只有第一个>=target才能直接返回其下标index；否则应该是最后一个<target的index+1
     * 295 ms	37.7 MB
     * 时间复杂度为 O(log n)
     * 空间复杂度O(1)
     */
    fun searchInsertLeetCode(nums: IntArray, target: Int): Int {
        var result = nums.size

        var left = 0
        var right = result - 1

        while (left <= right) {
            val mid = (left + right) / 2
            val midValue = nums[mid]
            if (midValue >= target) {
                result = mid
                right = mid - 1
            } else {
                left = mid + 1
            }
        }

        return result
    }

}

fun main() {
    val obj = SearchInsertPosition()

    val cases = mutableListOf(
        Pair(intArrayOf(1, 2, 3, 4, 5, 10), 2),
        Pair(intArrayOf(1, 3, 5, 6), 7),
        Pair(intArrayOf(1, 3, 5, 6, 9, 11, 20), 7),
        Pair(intArrayOf(1), 1),
        Pair(intArrayOf(1), 2),
        Pair(intArrayOf(1), -1),
        Pair(intArrayOf(1, 3, 5, 6), -1),
        Pair(intArrayOf(1, 3, 5, 6), 5),
        Pair(intArrayOf(1, 3, 5, 6), 2),
    )

    cases.forEach {
        val result = obj.searchInsertLeetCode(it.first, it.second)
        println("$result --> target ${it.second} : arr ${it.first.joinToString()}")
    }
}