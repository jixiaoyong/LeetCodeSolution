package medium

/*
* @description: 215. 数组中的第K个最大元素
* https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
* 1 <= k <= nums.length <= 104
* -104 <= nums[i] <= 104
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2021/11/7
*/
object KthLargestElementInAnArray {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        nums.sort()
        println("sorted list: " + nums.joinToString())
        return nums.get(nums.size - k)
    }
}

fun main() {
    val testCaseList = arrayListOf<Pair<String, Int>>(
        Pair("1", 1),
        Pair("3,2,3,1,2,4,5,5,6", 2),
        Pair("3,2,3,-1,2,4,-5,5,-6", 2),
        Pair("3,2,3,-1,2,4,-5,5,-6", 6),
        Pair("3,2,3,1,2,4,5,5,6", 4),
        Pair("3,3,3,3,3", 3),
    )
    testCaseList.forEach {
        val array = it.first.split(",").map { it.toInt() }.toIntArray()
        val k = it.second
        println(array.joinToString())
        val kthLargestElement = KthLargestElementInAnArray.findKthLargest(array, k)
        println("$k thLargestElement:$kthLargestElement")
        println()
    }
}