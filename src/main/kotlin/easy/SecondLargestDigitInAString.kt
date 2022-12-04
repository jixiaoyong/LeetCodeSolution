package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1796. 字符串中第二大的数字
 * 给你一个混合字符串 s ，请你返回 s 中 第二大 的数字，如果不存在第二大的数字，请你返回 -1 。
 *
 * 混合字符串 由小写英文字母和数字组成。
 *
 * 提示：
 *
 * 1 <= s.length <= 500
 * s 只包含小写英文字母和（或）数字。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/3/2022
 */
class SecondLargestDigitInAString {

    /**
     * 思路，先遍历一次查找到所有数字，然后排序取出倒数第二个数字
     * 时间复杂度O(N+LogM)，遍历查找数字N和排序时间LogM（M范围为0~10）
     * 空间复杂度O(M),主要大小为数字占据的空间
     * 288 ms	36.3 MB
     */
    fun secondHighest(s: String): Int {
        val len = s.length
        val codeOfZero = '0'.toInt()
        val numSet = mutableSetOf<Int>()

        for (i in 0 until len) {
            val c = s[i]
            if (c in '0'..'9') {
                // c is number
                val num = (c.toInt() - codeOfZero)
                numSet.add(num)
            }
        }

        val sortedList = numSet.toMutableList().sorted()
        val numCount = sortedList.size

        return if (numCount < 2) -1 else sortedList[numCount - 2]
    }

    /**
     * 优化版本，一次遍历，
     * 思路：每次遍历先查到到最大值，如果当前遍历的数字比最大值大，那么其为新的最大值，原先的最大值现在为倒数第二个最大值
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     * 168 ms	34.3 MB
     */
    fun secondHighestOptimized(s: String): Int {
        val len = s.length
        val codeOfZero = '0'.toInt()

        var maximum = -1
        var secondMaximum = -1

        for (i in 0 until len) {
            val c = s[i]
            if (c in '0'..'9') {
                // c is number
                val num = (c.toInt() - codeOfZero)
                if (num > maximum) {
                    secondMaximum = maximum
                    maximum = num
                } else if (num < maximum && num > secondMaximum) {
                    // 对的例如321这种倒序的情况
                    secondMaximum = num
                }
            }
        }

        return secondMaximum
    }

}


fun main() {
    val obj = SecondLargestDigitInAString()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf("123", "321", "dfa12321afd", "323", "a", "1111", "00")

    val largestString = CharArray(500)
    for (i in 0 until largestString.size) {
        largestString[i] =
            (if (random.nextBoolean()) random.nextInt('0'.toInt(), '9'.toInt() + 1) else random.nextInt(
                'a'.toInt(),
                'z'.toInt() + 1
            )).toChar()
    }
    cases.add(String(largestString))

    for (case in cases) {
        println("${obj.secondHighest(case)}/${obj.secondHighestOptimized(case)} ------ $case")
    }
}