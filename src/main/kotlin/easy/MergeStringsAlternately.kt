package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1768. 交替合并字符串
 * 给你两个字符串 word1 和 word2 。请你从 word1 开始，通过交替添加字母来合并字符串。
 * 如果一个字符串比另一个字符串长，就将多出来的字母追加到合并后字符串的末尾。
 * 返回 合并后的字符串 。
 *
 * 提示：
 *
 * 1 <= word1.length, word2.length <= 100
 * word1 和 word2 由小写英文字母组成
 *
 * https://leetcode.cn/problems/merge-strings-alternately/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/23/2022
 */
class MergeStringsAlternately {

    /**
     * 148 ms	33.3 MB
     */
    fun mergeAlternately(word1: String, word2: String): String {
        val sb = StringBuffer()
        var cur = 0
        word1.forEachIndexed { index, s1 ->
            cur = index
            if (index < word2.length) {
                sb.append(s1)
                sb.append(word2[index])
            } else {
                // word1.length > word2.length
                sb.append(word1.subSequence(index, word1.length))
                return sb.toString()
            }

        }
        if (word2.length > cur) {
            // word1.length < word2.length
            sb.append(word2.subSequence(cur + 1, word2.length))
        }
        return sb.toString()
    }

    /**
     * 168 ms	33 MB
     */
    fun mergeAlternatelyArr(word1: String, word2: String): String {
        val wordLen1 = word1.length
        val wordLen2 = word2.length
        val sb = CharArray(wordLen1 + wordLen2)
        var count = 0

        val miniLen = Math.min(wordLen1, wordLen2)
        for (x in 0 until miniLen) {
            sb[count++] = word1[x]
            sb[count++] = word2[x]
        }

        if (wordLen1 > miniLen) {
            for (x in miniLen until wordLen1) {
                sb[count++] = word1[x]
            }
        } else if (wordLen2 > miniLen) {
            for (x in miniLen until wordLen2) {
                sb[count++] = word2[x]
            }
        }

        return String(sb)
    }

    /**
     * LeetCode官方题解双指针
     * 148 ms	33 MB
     */
    fun mergeAlternatelyTwoIndex(word1: String, word2: String): String {
        val wordLen1 = word1.length
        val wordLen2 = word2.length
        var index1 = 0
        var index2 = 0

        val sb = CharArray(wordLen1 + wordLen2)
        var count = 0

        while (index1 < wordLen1 || index2 < wordLen2) {
            if (index1 < wordLen1) {
                sb[count++] = word1[index1++]
            }
            if (index2 < wordLen2) {
                sb[count++] = word2[index2++]
            }
        }

        return String(sb)
    }
}

fun main() {
    val obj = MergeStringsAlternately()
    val random = Random(System.currentTimeMillis())

    val cases = mutableListOf(
        Pair("a", "b"),
        Pair("abcdefg", "b"),
        Pair("a", "bcdefg"),
        Pair("abc", "pqr"),
        Pair("ab", "pqrs"),
        Pair("abcd", "pq"),
    )

    val largestSb1 = StringBuffer()
    val largestSb2 = StringBuffer()
    for (x in 0 until 100) {
        largestSb1.append(random.nextInt('a'.code, 'z'.code).toChar())
        largestSb2.append(random.nextInt('a'.code, 'z'.code).toChar())
    }
    cases.add(Pair(largestSb1.toString(), largestSb2.toString()))

    cases.forEach {
        val result = obj.mergeAlternatelyTwoIndex(it.first, it.second)
        println("$result --> $it")
    }
}