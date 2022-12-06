package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1805. 字符串中不同整数的数目
 *
 * 给你一个字符串 word ，该字符串由数字和小写英文字母组成。
 *
 * 请你用空格替换每个不是数字的字符。例如，"a123bc34d8ef34" 将会变成 " 123 34 8 34" 。
 * 注意，剩下的这些整数为（相邻彼此至少有一个空格隔开）："123"、"34"、"8" 和 "34" 。
 *
 * 返回对 word 完成替换后形成的 不同 整数的数目。
 *
 * 只有当两个整数的 不含前导零 的十进制表示不同， 才认为这两个整数也不同。
 *
 * 提示：
 *
 * 1 <= word.length <= 1000
 * word 由数字和小写英文字母组成
 *
 * 【注意】 需要处理的异常有：1. 数字在末尾，2. 数字超长大于Int.MAX_VALUE,3.处理0034和34以及单个的0，4.处理前导0
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/6/2022
 */
class NumberOfDifferentIntegersInAstring {

    /**
     * 思路，使用一个指针left记录上次数字、非数字开始的位置，然后每次遇到数字变为非数字，或者非数字变为数字的情况，更新left的下标，
     * 在数字变为非数字时需要记录left到当前位置的有效数字（去除前导0的整数），为了处理字符串以0开始的情况，将left从-1开始
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    fun numDifferentIntegers(word: String): Int {
        val intRange = IntRange('0'.toInt(), '9'.toInt())
        val defChar = 'a'
        val stringSet = mutableSetOf<String>()
        var left = -1 // deal with prefix char is 0

        for (i in 0..word.length) {
            val curChar = word.getOrElse(i) { _ -> defChar }
            if (intRange.contains(curChar.toInt())) {
                // is number
                if (!intRange.contains(word.getOrElse(left) { _ -> defChar }.toInt())) {
                    // curChar is first digit of the number
                    if (curChar != '0') {
                        left = i
                    } else if (!intRange.contains((word.getOrElse(i + 1) { _ -> defChar }).toInt())) {
                        //digit is 0， number prefix can't be 0， deal with number zero
                        left = i
                    }
                }
                continue
            } else {
                // is character
                if (intRange.contains(word.getOrElse(left) { _ -> defChar }.toInt())) {
                    val num = word.substring(left, i)
                    stringSet.add(num)
                    left = i
                }
            }
        }
        return stringSet.size
    }
}

fun main() {
    val obj = NumberOfDifferentIntegersInAstring()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        "09a9",
        "0a9",
        "0a0",
        "9a9",
        "leet1234code234",
        "a0123bc034d8ef34",
        "a1230bc304d8ef3hj0u003i4",
        "a1b01c001",
        "1",
        "e",
        "e1",
        "100d100d2dj01"
    )

    val largestString = CharArray(1000)
    for (i in 0 until 1000) {
        largestString[i] = Char(
            if (random.nextBoolean()) random.nextInt('0'.toInt(), '9'.toInt() + 1) else random.nextInt(
                'a'.toInt(),
                'z'.toInt() + 1
            )
        )
    }
    cases.add(String(largestString))

    for (case in cases) {
        println("${obj.numDifferentIntegers(case)}  ---> $case")
    }
}