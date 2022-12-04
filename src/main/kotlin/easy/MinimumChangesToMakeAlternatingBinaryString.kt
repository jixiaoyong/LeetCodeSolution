package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1758. 生成交替二进制字符串的最少操作数
 * 给你一个仅由字符 '0' 和 '1' 组成的字符串 s 。一步操作中，你可以将任一 '0' 变成 '1' ，或者将 '1' 变成 '0' 。
 *
 * 交替字符串 定义为：如果字符串中不存在相邻两个字符相等的情况，那么该字符串就是交替字符串。
 * 例如，字符串 "010" 是交替字符串，而字符串 "0100" 不是。
 *
 * 返回使 s 变成 交替字符串 所需的 最少 操作数。
 *
 * 提示：
 *
 * 1 <= s.length <= 10^4
 * s[i] 是 '0' 或 '1'
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/29/2022
 */
class MinimumChangesToMakeAlternatingBinaryString {

    /**
     * 分别模拟以0和1开头，进行转换的进度
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     */
    fun minOperations(s: String): Int {
        // 分别以0和1开头
        var stepPrefix0 = 0
        var stepPrefix1 = 0

        var preCharPrefix0 = '1'
        var preCharPrefix1 = '0'

        for (i in 0 until s.length) {
            val curChar = s[i]
            if (curChar == preCharPrefix0) {
                // change cur char
                preCharPrefix0 = if ('1' == curChar) '0' else '1'
                stepPrefix0++
            } else {
                preCharPrefix0 = curChar
            }
            if (curChar == preCharPrefix1) {
                // change cur char
                preCharPrefix1 = if ('1' == curChar) '0' else '1'
                stepPrefix1++
            } else {
                preCharPrefix1 = curChar
            }
        }

        return if (stepPrefix0 > stepPrefix1) {
            stepPrefix1
        } else {
            stepPrefix0
        }
    }


    /**
     * [minOperations]的优化版本，只需判断一种情况，另外一种情况的步数是字符串长度减去其步数
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     */
    fun minOperationsLeetcode(s: String): Int {
        // 分别以0和1开头
        var stepPrefix0 = 0

        var preCharPrefix0 = '1'

        for (i in 0 until s.length) {
            val curChar = s[i]
            if (curChar == preCharPrefix0) {
                // change cur char
                preCharPrefix0 = if ('1' == curChar) '0' else '1'
                stepPrefix0++
            } else {
                preCharPrefix0 = curChar
            }

        }

        return Math.min(stepPrefix0, s.length - stepPrefix0)
    }
}


fun main() {
    val obj = MinimumChangesToMakeAlternatingBinaryString()
    val random = Random(System.currentTimeMillis())

    val cases = mutableListOf("0100", "10", "1111", "1100"/*2*/, "00110"/*2*/)

    val largestString = CharArray(10000)
    for (i in 0 until 10000) {
        largestString[i] = if (random.nextBoolean()) '1' else '0'
    }

    cases.add(String(largestString))

    cases.forEach {
        println("${obj.minOperations(it)} / ${obj.minOperationsLeetcode(it)}   ---> $it")
    }

}