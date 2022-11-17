package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ：792. 匹配子序列的单词数
 *
 * 给定字符串 s和字符串数组words, 返回words[i]中是s的子序列的单词个数。
 *
 * 字符串的 子序列 是从原始字符串中生成的新字符串，可以从中删去一些字符(可以是none)，而不改变其余字符的相对顺序。
 *
 * 例如， “ace” 是 “abcde” 的子序列。
 *
 * 提示:
 *
 * 1 <= s.length <= 5 * 10^4
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 50
 * words[i]和 s都只由小写字母组成。
 *
 * https://leetcode.cn/problems/number-of-matching-subsequences/
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/17/2022
 */
class NumberOfMatchingSubsequences {
    fun numMatchingSubseq(s: String, words: Array<String>): Int {
        val none = -1
        val aCharInt = 'a'.toInt()
        val dp = Array(s.length + 1) { IntArray(26) { none } }

        for (i in s.length - 1 downTo 0) {
            val index = s[i].toInt() - aCharInt
            val preMap = dp[i + 1].copyOf()
            preMap[index] = i
            dp[i] = preMap
        }

        var count = 0

        words.forEach { str ->
            var dpIndex = 0
            for (i in 0 until str.length) {
                dpIndex = dp[dpIndex][str[i].toInt() - aCharInt]
                if (dpIndex == none) {
                    return@forEach
                }
                dpIndex++
            }
            count++
        }

        return count
    }

}

fun main() {
    val obj = NumberOfMatchingSubsequences()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        Pair("abcde", arrayOf("a", "bb", "acd", "ace")),
        Pair("dsahjpjauf", arrayOf("ahjpjau", "ja", "ahbwzgqnuk", "tnmlanowax")),
        Pair("dsahjpjauf", arrayOf("ahjxxspjau", "jxa", "ahbwzgqnuk", "tnmlanowax")),
        Pair("abcde", arrayOf("a", "abb", "acxd", "ace")),
        Pair("a", arrayOf("a", "abb", "acxd", "ace")),
    )

    val sb = StringBuffer()
    for (i in 0 until 10000) {
        sb.append(Char(random.nextInt('a'.code, 'z'.code + 1)))
    }
    cases.add(
        Pair(
            sb.toString(),
            arrayOf(
                "ddadeadaojfaeofajfaeofadjfaeofadmxadxadawedadxddadeadaojfaeofajfaeofadjfaeofadmxadxadawedadxddadeadaojfaeofajfaeofadjfaeofadmxadxadawedadx",
                "ddadeadaojfaeofajfaeofadjfaeofadmxadxadawedadx"
            )
        )
    )

    cases.forEach {
        val result = obj.numMatchingSubseq(it.first, it.second)
        println("$result  ${it.second.joinToString()}  /// ${it.first}")
    }
}