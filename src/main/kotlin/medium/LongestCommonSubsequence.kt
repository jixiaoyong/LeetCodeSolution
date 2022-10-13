package medium

import java.awt.SystemColor.text

/**
 * @author : jixiaoyong
 * @description ： 1143. 最长公共子序列
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 *
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 *
 * 1 <= text1.length, text2.length <= 1000
 * text1 和 text2 仅由小写英文字符组成。
 * https://leetcode.cn/problems/longest-common-subsequence/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/13/2022
 */
class LongestCommonSubsequence {
    private var maxSubSequenceLength = 0
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        val textLen1 = text1.length
        val textLen2 = text2.length
        val i = 0
        if (textLen2 > textLen1) {
//            for (i in 0 until textLen1) {
                val currentSubSeqLen = longestCommonSubsequence(text1, i, text2, 0)
                maxSubSequenceLength = Math.max(maxSubSequenceLength, currentSubSeqLen)
//            }
        } else {
//            for (i in 0 until textLen2) {
                val currentSubSeqLen = longestCommonSubsequence(text2, i, text1, 0)
                maxSubSequenceLength = Math.max(maxSubSequenceLength, currentSubSeqLen)
//            }
        }


        return maxSubSequenceLength
    }

    private fun longestCommonSubsequence(
        text1: String,
        startIndex1: Int,
        text2: String,
        startIndex2: Int
    ): Int {
        val endIndex1 = text1.length
        val endIndex2 = text2.length

        val text1Length = endIndex1 - startIndex1
        val text2Length = endIndex2 - startIndex2

        if (text1Length <= 0 || text2Length <= 0) {
            return 0
        }

        if (text1Length == 1 && text2Length == 1) {
            return if (text1 == text2) 1 else 0
        }

        val char1 = text1[startIndex1]
        val indexOfChar1InText2 = text2.indexOf(char1, startIndex2)
        var currentSubSeqLen = 0
        var includeLength = 0
        var excludeLength = 0
        // 包含当前char的情况
        if (indexOfChar1InText2 != -1) {
            includeLength++
//            includeLength += longestCommonSubsequence(text1, startIndex1 + 1, text2, indexOfChar1InText2 + 1)
        }
        includeLength += longestCommonSubsequence(text1, startIndex1 + 1, text2, indexOfChar1InText2 + 1)
        // 不包含当前char的情况
//        excludeLength += longestCommonSubsequence(text1, startIndex1 + 1, text2, startIndex2)

        currentSubSeqLen += Math.max(includeLength, excludeLength)

        return currentSubSeqLen
    }

}

fun main() {
//    val text = Pair("abcde", "ace")
//    val text = Pair("cabade", "aceae")
//    val text = Pair("abc", "def")
//    val text = Pair("abc", "abc")
    val text = Pair("ezupkr",
        "ubmrapg")
//    val text = Pair("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
//        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//
    println("result:${LongestCommonSubsequence().longestCommonSubsequence(text.first, text.second)}")
}