package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 27. 移除元素
 * 给你一个数组 nums和一个值 val，你需要 原地 移除所有数值等于val的元素，并返回移除后数组的新长度。
 *
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 *
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 *
 * 提示：
 *
 * 0 <= nums.length <= 100
 * 0 <= nums[i] <= 50
 * 0 <= val <= 100
 *
 * https://leetcode.cn/problems/remove-element/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/8/2022
 */
class RemoveElement {

    /**
     * 	172 ms	34 MB
     * 双指针
     * 分别从开头和结尾开始遍历nums，当[startIndex]遇到与[`val`]相等的数字时，开始从末尾找与[`val`]不相等的数字的下标[endIndex]，交换二者
     * 直到[startIndex]与[endIndex]相遇
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    fun removeElement(nums: IntArray, `val`: Int): Int {
        if (`val` > 50 || nums.isEmpty()) {
            return nums.size
        }

        var endIndex = nums.size - 1
        var startIndex = 0
        while (startIndex < endIndex) {
            while (startIndex < endIndex && nums[startIndex] != `val`) {
                startIndex++
            }
            while (startIndex < endIndex && nums[endIndex] == `val`) {
                endIndex--
            }
            val t = nums[startIndex]
            nums[startIndex] = nums[endIndex]
            nums[endIndex] = t

        }
        if (startIndex == endIndex && nums[endIndex] == `val`) {
            endIndex--
        }
        return endIndex + 1
    }
}

fun main() {
    val obj = RemoveElement()
    val random = Random(System.currentTimeMillis())

    val cases = mutableListOf(
        Pair(intArrayOf(3, 3), 5),
        Pair(intArrayOf(2), 3),
        Pair(intArrayOf(3, 2, 2, 3), 3),
        Pair(intArrayOf(), 2),
        Pair(intArrayOf(2, 2, 2, 2, 2), 2),
        Pair(intArrayOf(1, 2, 3, 4, 5), 2),
    )

    val largestArr = IntArray(100) { random.nextInt(0, 51) }

    cases.add(Pair(largestArr, random.nextInt(0, 51)))

    for (case in cases) {
        println("before:${case.second}  -- ${case.first.joinToString()}")
        println("RESULT:${obj.removeElement(case.first, case.second)} ")
        println("after:${case.first.joinToString()}\n")

    }
}