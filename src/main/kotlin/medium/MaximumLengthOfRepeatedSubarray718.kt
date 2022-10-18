package medium

/**
 * @author : jixiaoyong
 * @description ： 718. 最长重复子数组
 * 给两个整数数组 nums1 和 nums2 ，返回 两个数组中 公共的 、长度最长的子数组的长度 。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/18/2022
 */
class MaximumLengthOfRepeatedSubarray718 {
    /**
     * 思路：动态规划，构建一个num1（竖轴i）*num2（横轴j）的表格，grid[i][j]表示num1[0~i-1]和num2[0~j-1]之间的最大重复子数组
     * 优化，由于每次只需当前行以及上一行数据以计算最大重复子数组长度，所以将表格简化为两个row
     *
     * 此题需要注意的是，最长重复子数组需要【连续】，所以如果num1[i]==num[j]:
     * - 相等：则需要看num1[i-1]是否等于num[j-1]，相等则说明是连续子数组，可以加上其长度；否则则以num1[i]和num2[j]开启新的子数组
     * - 不相等：此处重复子数组长度为0，后续重复子数组重新开始计算长度
     *
     *  时间复杂度： O(N×M)。
     *  空间复杂度： O(N)。
     *
     *  204 ms
     *  35.3 MB
     */
    fun findLength(nums1: IntArray, nums2: IntArray): Int {
        val m = nums1.size + 1
        val n = nums2.size + 1

        var max = 0

        // pre[0],cur[0] == 0,表示数组与空数组交集，以避免数组下标越界
        var pre = IntArray(n)
        val cur = IntArray(n)

        for (i in 1 until m) {
            for (j in 1 until n) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    cur[j] = pre[j - 1] + 1
                } else {
                    cur[j] = 0
                }
                max = Math.max(max, cur[j])
            }
            pre = cur.clone()
        }

        return max
    }


    /**
     * 滑动窗口
     * 441 ms	41.1 MB
     * 来自：https://leetcode.cn/problems/maximum-length-of-repeated-subarray/solution/zui-chang-zhong-fu-zi-shu-zu-by-leetcode-solution/
     * 思路是两个数组的重复序列位置不一致（未对齐），所以遍历无法直接查找到，但是如果能够将其移动X格，那么在遍历的时候能够很方便的找到重复的序列
     * 时间复杂度是O(M+N)*min(M,N)
     * 空间复杂度是O(1)
     */
    fun findLengthSlide(nums1: IntArray, nums2: IntArray): Int {
        var result = 0

        val min: IntArray
        val max: IntArray
        if (nums1.size > nums2.size) {
            max = nums1
            min = nums2
        } else {
            max = nums2
            min = nums1
        }

        // 将min末尾放置到max的最右边，然后将min向右滑动，一直到min的开头在max的末尾
        // 在此过程对比计算最长的重复子数组
        for (i in -(min.size - 1) until max.size) {
            var len = 0
            for (j in 0 until min.size) {
                if (i + j > max.size - 1 || i + j < 0) {
                    continue
                }
                if (min[j] == max[j + i]) {
                    len++
                } else {
                    len = 0
                }
                result = Math.max(result, len)
            }
        }

        return result
    }


}

fun main() {
    val obj = MaximumLengthOfRepeatedSubarray718()

    val pairs = arrayOf(
        Pair(intArrayOf(1, 3, 2, 2, 1), intArrayOf(3, 2, 1, 4, 7)),
        Pair(intArrayOf(1, 2, 3, 2, 1), intArrayOf(3, 2, 1, 4, 7)),
        Pair(intArrayOf(0, 0, 0, 0, 0), intArrayOf(0, 0, 0, 0, 0)),
        Pair(intArrayOf(0), intArrayOf(0, 0, 0, 0, 0)),
        Pair(intArrayOf(0), intArrayOf(0)),
    )

    pairs.forEach {
        print(obj.findLength(it.first, it.second))
        print(" - ${obj.findLengthSlide(it.first, it.second)}")
        println(":      ${it.first.joinToString()}-----${it.second.joinToString()}")

    }
}