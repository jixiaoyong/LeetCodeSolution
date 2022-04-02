package easy

import kotlin.random.Random

/*
* @description: 704. Binary Search
* Given an array of integers nums which is sorted in ascending order, and an integer target,
* write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.
* You must write an algorithm with O(log n) runtime complexity.
*
* Constraints:
* 1 <= nums.length <= 104
* -104 < nums[i], target < 104
* All the integers in nums are unique.
* nums is sorted in ascending order.
*
* https://leetcode.com/problems/binary-search/
*
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 22/04/02
*/
object BinarySearch {
    /**
     * @description: 二分查找
     * Runtime: 240 ms, faster than 94.12% of Kotlin online submissions for Binary Search.
     * Memory Usage: 37.8 MB, less than 94.36% of Kotlin online submissions for Binary Search.
     */
    fun search(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1

        if (nums.isEmpty() || target < nums[left] || target > nums[right]) {
            return -1
        }

        while (left <= right) {
            val mid = (right + left) / 2
            if (nums[left] == target) {
                return left
            } else if (nums[right] == target) {
                return right
            } else if (nums[mid] == target) {
                return mid
            } else if (nums[mid] < target) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        return -1
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val random = Random(System.currentTimeMillis())
        for (i in 0..100) {
            val arraySize = random.nextInt(10000)
            val set = hashSetOf<Int>()
            for (j in 0 until arraySize) {
                val num = random.nextInt(10000)
                set.add(num)
            }
            val nums = set.toIntArray()
            nums.sort()
            val target = random.nextInt(10000)

            val isFindTheIndex = search(nums, target) == nums.indexOf(target)
            if (!isFindTheIndex) {
                println()
                println("array: ${nums.contentToString()}")
                println("target: $target")
                println()
            }
            println("result: $isFindTheIndex")
        }
    }
}