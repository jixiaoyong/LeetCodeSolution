package easy

/*
* @description: 1. 两数之和
* 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target的那两个整数，并返回它们的数组下标。
你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
你可以按任意顺序返回答案。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/two-sum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2021/11/24
*/
object TwoSum {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val result = IntArray(2)
        nums.forEachIndexed { index, num ->
            val otherValue = target - num
            val otherValueIndex = nums.indexOf(otherValue)
            if (otherValueIndex != -1 && otherValueIndex != index) {
                result[0] = index
                result[1] = otherValueIndex
                return intArrayOf(index, otherValueIndex)
            }
        }

        return result
    }
}

fun main() {
    val nums = intArrayOf(2, 7, 11, 15)
    val target = 9

    println(TwoSum.twoSum(nums, target).joinToString())
}