package easy

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/11/26
 * description: 26. 删除有序数组中的重复项
 * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 * 元素的 相对顺序 应该保持 一致 。
 *
 * 由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。
 * 更规范地说，如果在删除重复项之后有 k 个元素，那么nums的前 k 个元素应该保存最终结果。
 *
 * 将最终结果插入nums 的前 k 个位置后返回 k 。
 *
 * 不要使用额外的空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 *
 * 判题标准:
 *
 * 系统会用下面的代码来测试你的题解:
 *
 * 提示：
 *
 * 1 <= nums.length <= 3 * 10^4
 * -104 <= nums[i] <= 10^4
 * nums 已按 升序 排列
 *
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array/
 */
class RemoveDuplicatesFromSortedArray {

    /**
     * 270 ms	37.4 MB
     * 思路是，双指针从左往右查找，遇到连续两个以上一样的数字则记录下这群数字的第一个坐标insertIndex，并继续向后查找直到遇到第一个
     * 不一样的数字checkIndex，将其插入到之前的insertIndex中，然后将insertIndex后移，并继续后移checkIndex直到nums末尾
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    fun removeDuplicates(nums: IntArray): Int {
        var checkIndex = 1
        var insertIndex = 1
        var preNum = nums[0]

        while (checkIndex < nums.size) {
            val curNum = nums[checkIndex++]
            if (curNum != preNum) {
                nums[insertIndex++] = curNum
            }
            preNum = curNum
        }

        return insertIndex
    }


    /**
     * 	449 ms	48.6 MB
     * [removeDuplicates]优化版本，避免面对[1,2,3,4,5,6]时，还需要每个数字都复制一次
     */
    fun removeDuplicatesRefactor(nums: IntArray): Int {
        var checkIndex = 1
        var insertIndex = 1
        var preNum = nums[0]

        while (checkIndex < nums.size) {
            val curNum = nums[checkIndex++]
            if (curNum != preNum) {
                if (checkIndex - insertIndex > 1) {//判断也耗时
                    nums[insertIndex] = curNum
                }
                insertIndex++
            }
            preNum = curNum
        }

        return insertIndex
    }
}

fun main() {
    val obj = RemoveDuplicatesFromSortedArray()
    val cases = listOf(
        intArrayOf(1), intArrayOf(1, 1, 2), intArrayOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4),
        intArrayOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 6, 77)
    )

    cases.forEach {
//        println("${obj.removeDuplicates(it)}   ---> ${it.joinToString()}")
        println("${obj.removeDuplicatesRefactor(it)}   ---> ${it.joinToString()}")
    }
}