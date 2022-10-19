package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 33. 搜索旋转排序数组
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为[4,5,6,7,0,1,2] 。
 *
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 *
 * 提示：
 * 1 <= nums.length <= 5000
 * -104 <= nums[i] <= 104
 * nums 中的每个值都 独一无二
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -104 <= target <= 104
 * https://leetcode.cn/problems/search-in-rotated-sorted-array
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/19/2022
 */
class SearchInRotatedSortedArray {

    /**
     * 263 ms	35.7 MB
     * 二分法变体
     * 时间复杂度： O(logn)
     * 空间复杂度： O(1)
     */
    fun search(nums: IntArray, target: Int): Int {
        var index = -1

        if (nums.size == 1) {
            return if (nums[0] == target) 0 else index
        }

        var begin = 0
        var end = nums.size - 1

        while (begin < end) {
            if (end - begin == 1) {
                index = when (target) {
                    nums[begin] -> begin
                    nums[end] -> end
                    else -> -1
                }
                break
            }

            val mid = (end + begin) / 2
            val midValue = nums[mid]
            val midBiggerBegin = midValue > nums[begin]
            val midBiggerEnd = midValue > nums[end]

            if (target == midValue) {
                index = mid
                break
            } else if (midBiggerBegin && midBiggerEnd) {
                // k 小于等于mid，mid左边是升序，右边是升序或者旋转排序数组
                if (target < midValue && target >= nums[begin]) {
                    end = mid
                } else {
                    begin = mid
                }
            } else {
                // k > mid, mid右边是升序，左边是旋转排序数组
                if (target > midValue && target <= nums[end]) {
                    begin = mid
                } else {
                    end = mid
                }
            }


        }

        return index
    }
}

fun main() {
    val obj = SearchInRotatedSortedArray()
    val random = Random(System.currentTimeMillis())


    val cases = mutableListOf(
        Pair(intArrayOf(3, 1), 1),
        Pair(intArrayOf(4, 5, 6, 7, 0, 1, 2), 0), Pair(intArrayOf(4, 5, 6, 7, 0, 1, 2), 3),
        Pair(intArrayOf(1), 0),
        Pair(intArrayOf(1, 2), 2),
        Pair(intArrayOf(4, 5, 0, 1, 2, 3), 2),// k > size/2
        Pair(intArrayOf(4, 5, 6, 7, 8, 1, 2, 3), 2),// k < size/2
        Pair(intArrayOf(4, 5, 6, 1, 2, 3), 2),// k == size/2
    )

    for (x in 0..20) {
        var base = random.nextInt(10)
        var target = 0
        val arr = IntArray(random.nextInt(1, if (x == 20) 50000 else 20)) { index ->
            base += random.nextInt(1, 20)
            if (random.nextBoolean()) target = base
            base
        }
        var k = random.nextInt(0, arr.size - 1)
        var arrCopy = IntArray(arr.size)
        var curIndex = k
        for (x in 0 until arr.size) {
            if (curIndex < arr.size) {
                arrCopy[x] = arr[curIndex++]
            } else if (curIndex == arr.size) {
                curIndex = 0
                arrCopy[x] = arr[curIndex++]
            } else {
                arrCopy[x] = arr[curIndex++]
            }
        }
        cases.add(Pair(arrCopy, target))

    }

    cases.forEach {
        println("${obj.search(it.first, it.second)}   --->  ${it.second}  -- ${it.first.joinToString()}")
    }
}