package medium

/**
 * @author : jixiaoyong
 * @description ： 46. 全排列
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 *
 * https://leetcode.cn/problems/permutations/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/19/2022
 */
class Permutations {

    /**
     * 444 ms	46 MB
     * 时间复杂度为O(N!*N)
     * 空间复杂度为O(N)
     * 思路，将大的nums分为nums.size个小的nums，每个小nums是大nums移除了一个数字的组合，这样累计会有[nums.size]!个结果
     */
    fun permute(nums: IntArray): List<List<Int>> {
        if (nums.size == 1) {
            return listOf(nums.toList())
        }

        val result = mutableListOf<List<Int>>()

        for (x in nums) {
            val copyNums = nums.copyOf().toMutableList()
            copyNums.remove(x)
            val list = permute(copyNums.toIntArray())
            list.forEach {
                val copyIt = it.toMutableList()
                copyIt.add(0, x)
                result.add(copyIt)
            }
        }

        return result
    }

    /**
     * 232 ms	37.1 MB
     * permute的优化版本
     * 时间复杂度为O(N!)
     * 空间复杂度为O(N)
     */
    fun permute2(nums: IntArray): List<List<Int>> {
        if (nums.size == 1) {
            return listOf(nums.toList())
        }
        result.clear()

        genList(nums.toMutableList(), intArrayOf())

        return result
    }

    val result: MutableList<List<Int>> = mutableListOf()
    private fun genList(nums: MutableList<Int>, prefixArray: IntArray) {
        if (nums.size == 1) {
            val list = mutableListOf<Int>()
            list.addAll(prefixArray.toList())
            list.addAll(nums.toList())
            result.add(list)
            return
        }

        for (x in nums) {
            val copyNums = mutableListOf<Int>()
            copyNums.addAll(nums)
            copyNums.remove(x)
            val list = mutableListOf<Int>()
            list.addAll(prefixArray.toList())
            list.add(x)
            genList(copyNums, list.toIntArray())
        }
    }

}

fun main() {
    val obj = Permutations()
    val numsList =
        mutableListOf(
            intArrayOf(1),
            intArrayOf(1, 0),
            intArrayOf(1, 2, 3),
            intArrayOf(1, -2, 4),
            intArrayOf(1, 2, 3, -4, 5, 6)
        )

    numsList.forEach {
        println("${obj.permute2(it)}  -> ${it.joinToString()}")
        println("${obj.permute(it)}  -> ${it.joinToString()}\n")
    }
}