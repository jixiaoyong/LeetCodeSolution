package easy

/**
 * @author : jixiaoyong
 * @description ： 1207. 独一无二的出现次数
 *
 * 给你一个整数数组 arr，请你帮忙统计数组中每个数的出现次数。
 *
 * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
 *
 * 提示：
 *
 * 1 <= arr.length <= 1000
 * -1000 <= arr[i] <= 1000
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/30/2022
 */
class UniqueNumberOfOccurrences {

    /**
     * 思路，先统计各个数字出现次数，然后再遍历出现次数，如果有重复的则返回false，否则返回true
     * 时间复杂度O(N+LogN)，第一次遍历时间复杂度为O(N)，第二次遍历的时候有排序，所以多了O(LogN)
     * 空间复杂度O(N)
     */
    fun uniqueOccurrences(arr: IntArray): Boolean {
        val numAndCountMap = hashMapOf<Int, Int>()
        arr.forEach {
            val count = numAndCountMap.getOrDefault(it, 0) + 1
            numAndCountMap.put(it, count)
        }

        var pre = 0
        numAndCountMap.values.sorted().forEach {
            if (it == pre) {
                return false
            }
            pre = it
        }

        return true
    }

    /**
     * 思路： 先统计每个数字出现次数，然后再将各个出现次数保存到set中，如果set和出现次数的个数一致，则没有个数重复的，否则有
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    fun uniqueOccurrencesLeetcode(arr: IntArray): Boolean {
        val numAndCountMap = hashMapOf<Int, Int>()
        arr.forEach {
            val count = numAndCountMap.getOrDefault(it, 0) + 1
            numAndCountMap.put(it, count)
        }

        val countSet = mutableSetOf<Int>()
        for (value in numAndCountMap.values) {
            countSet.add(value)
        }

        return countSet.size == numAndCountMap.size
    }
}

fun main() {
    val obj = UniqueNumberOfOccurrences()
    val cases =
        mutableListOf(
            intArrayOf(
                26,
                2,
                16,
                16,
                5,
                5,
                26,
                2,
                5,
                20,
                20,
                5,
                2,
                20,
                2,
                2,
                20,
                2,
                16,
                20,
                16,
                17,
                16,
                2,
                16,
                20,
                26,
                16
            ),
            intArrayOf(1, 2, 2, 1, 1, 3), intArrayOf(1, 2), intArrayOf(-3, 0, 1, -3, 1, 1, 1, -3, 10, 0),
            intArrayOf(
                -3,
                0,
                1,
                -3,
                1,
                1,
                1,
                -3,
                10,
                0,
                -3,
                0,
                1,
                -3,
                1,
                1,
                1,
                -3,
                10,
                0,
                -3,
                0,
                1,
                -3,
                1,
                1,
                1,
                -3,
                10,
                0
            ),

            )

    for (case in cases) {
        println(
            "${obj.uniqueOccurrences(case)} / ${obj.uniqueOccurrencesLeetcode(case)}   --> ${
                intArrayOrSizeToString(
                    case
                )
            }"
        )
    }
}