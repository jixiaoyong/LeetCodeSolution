package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1781. 所有子字符串美丽值之和
 * 一个字符串的 美丽值定义为：出现频率最高字符与出现频率最低字符的出现次数之差。
 *
 * 比方说，"abaacc"的美丽值为3 - 1 = 2。
 * 给你一个字符串s，请你返回它所有子字符串的美丽值之和。
 *
 * 提示：
 *
 * 1 <= s.length <= 500
 * s 只包含小写英文字母。
 *
 * https://leetcode.cn/problems/sum-of-beauty-of-all-substrings/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/12/2022
 */
class SumOfBeautyOfAllSubstrings {

    /**
     * 思路：
     * 先计算每个字符的前缀和prefixSums：即s[0,i]处时各个字符的个数
     * 然后，双层遍历计算得到所有的子字符串s[i,j]，此时，其中所有字符的个数即为prefixSums[j]中各个字符数减去prefixSums[i-1]
     * 中各个字符数目，由此得知此子字符串中各个字符的最大、最小个数，从而计算出“美丽值”，累加即得出答案
     *
     * 时间复杂度:O(C*N^2),C是字符串s中字符个数，最大为26，N为字符串长度
     * 空间复杂度：O(N*C)
     */
    fun beautySum(s: String): Int {
        val len = s.length
        val prefixSums = MutableList(len) { hashMapOf<Char, Int>() }
        prefixSums[0][s[0]] = 1
        for (i in 1 until len) {
            val preHashMap = prefixSums[i - 1]
            val curHashMap = prefixSums[i]

            curHashMap.putAll(preHashMap)
            val curChar = s[i]
            curHashMap[curChar] = preHashMap.getOrDefault(curChar, 0) + 1
        }

        var result = 0
        for (i in 0 until len - 1) {
            for (j in i + 1 until len) {
                var min = Int.MAX_VALUE
                var max = Int.MIN_VALUE
                val iMap = prefixSums.getOrElse(i - 1) { _ -> hashMapOf() }
                prefixSums[j].forEach { key, value ->
                    val newValue = value - iMap.getOrDefault(key, 0)
                    if (newValue == 0) {
                        return@forEach
                    }
                    min = Math.min(min, newValue)
                    max = Math.max(max, newValue)
                }
                result += if (max > min) max - min else 0
            }
        }
        return result
    }
}

fun main() {
    val obj = SumOfBeautyOfAllSubstrings()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf("aabcb", "aabcbaa")

    val largestCharArr = CharArray(500)
    for (i in 0 until largestCharArr.size) {
        largestCharArr[i] = Char(random.nextInt('a'.toInt(), 'z'.toInt()))
    }
    cases.add(String(largestCharArr))

    cases.forEach {
        println("${obj.beautySum(it)}   ----- $it")
    }
}