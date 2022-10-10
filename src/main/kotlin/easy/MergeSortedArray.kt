package easy

/**
 * @author : jixiaoyong
 * @description ： 88. 合并两个有序数组
 * 给你两个按 非递减顺序 排列的整数数组nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 *
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。
 * 为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 *
 * 链接：https://leetcode.cn/problems/merge-sorted-array
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/10/2022
 */
class MergeSortedArray {

    // 375 ms	35.6 MB
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        if (n == 0) {
            return
        }

        if (m == 0) {
            for (x in 0 until n) {
                nums1[x] = nums2[x]
            }
        }

        var startIndex = 0
        for (i in 0 until n) {
            val j = nums2[i]
            var hasInserted = false
            for (x in startIndex until m + i) {
                if (j < nums1[x]) {
                    insertIntoArray(nums1, x, j)
                    startIndex = x
                    hasInserted = true
                    break
                }
            }

            if (!hasInserted) {
                nums1[m + i] = j
            }
        }
    }

    private fun insertIntoArray(nums: IntArray, insertIndex: Int, value: Int) {
        var temp = value
        for (x in insertIndex until nums.size) {
            val oldValue = nums[x]
            nums[x] = temp
            temp = oldValue
        }
    }


    /******************other solution*****************/

    /**
     * 参考[王振](https://leetcode.cn/u/charleswone/)在https://leetcode.cn/problems/merge-sorted-array/comments/的评论
     * 思路是，两个有序非递减数组，从后开始合并
     * 266 ms	35.3 MB
     */
    fun merge2(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        var m = m
        var n = n
        // num1的末尾
        var currentIndex = m-- + --n
        while (n >= 0) {
            // 当前num1的末尾 = 如果num1有值并且大于num2 ： num1的值 否则 num2的值
            nums1[currentIndex--] = if (m >= 0 && nums1[m] > nums2[n]) nums1[m--] else nums2[n--]
        }
    }
}

fun main() {
//    val intArr1 = intArrayOf(1, 2, 3, 4, 5, 0, 0, 0)
//    val intArr2 = intArrayOf(2, 3, 4)

//    val intArr1 = intArrayOf()
//    val intArr2 = intArrayOf()

//    val intArr1 = intArrayOf(0)
//    val intArr2 = intArrayOf(4)

    val intArr1 = intArrayOf(1, 2, 3, 0, 0, 0)
    val intArr2 = intArrayOf(2, 5, 6)

//    MergeSortedArray().merge(intArr1, intArr1.size - intArr2.size, intArr2, intArr2.size)
    MergeSortedArray().merge2(intArr1, intArr1.size - intArr2.size, intArr2, intArr2.size)
    println("merge:${intArr1.joinToString()}")
}