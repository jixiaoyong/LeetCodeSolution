package medium

import kotlin.random.Random

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

    /**
     * 参考https://www.cnblogs.com/labuladong/p/13945482.html
     * 递归方法
     * 思路：从后往前查，如果text1[i]==text2[j]，那么他们一定在最长子序列LCS中，否则可能在（构成子序列），也可能不在（可能不构成子序列，长度为0）
     */
    fun longestCommonSubsequence(text1: String, text2: String): Int {

        return dp(text1, text1.length - 1, text2, text2.length - 1)
    }

    private fun dp(text1: String, endIndex1: Int, text2: String, endIndex2: Int): Int {
        if (endIndex1 < 0 || endIndex2 < 0) {
            return 0
        }

        if (text1[endIndex1] == text2[endIndex2]) {
            return dp(text1, endIndex1 - 1, text2, endIndex2 - 1) + 1
        } else {
            return Math.max(dp(text1, endIndex1, text2, endIndex2 - 1), dp(text1, endIndex1 - 1, text2, endIndex2))
        }
    }

    /**
     * 动态规划法
     * 时间复杂度O(MN)
     * 空间复杂度O(MN)
     *
     * 思路： 从前往后，计算字符串长度为0、1、2……n/m时，他们的最大公共子序列LCS的长度
     * 其中：后一个最大子序列的长度=前一个最大子序列长度+1
     * 对于abcde和ace为例，构建一个竖轴为["",a,b,c,d,e]]——text1，横轴为["",a,c,e]——text2，的4*6表格grid，
     *
     * 对于点grid[i][j]（i,j>=1）来说：
     * 如果text1[i-1]和text2[j-1]相等，那就说明他们在公共子序列中，那么text1[0~i-1]和text[0~j-1]的最长公共子序列LCS长度就是text1[0~i-2]
     * 和text[0~j-2]对应的最长公共子序列长度+1，也就是grid[i-1][j-1]+1，以grid[1][1]为例，a和a的最长公共子序列=""的LCS + 1 = 0+1 = 1
     * 如果他们不相等，那么这两个子字符串的最长公共子序列长度应该是上一个子字符串的最长公共子序列长度，以grid[2,1]为例，a和ab的公共子序列=
     * a（text1没有b）和a的LCS 和 ab和""（text2没有a）的LCS的最大值 = Max(1,0) = 1
     *    ["", a , c,e] n轴
     * "" [0, 0, 0, 0]
     *  a [0, 1, 1, 1]
     *  b [0, 1, 1, 1]
     *  c [0, 1, 2, 2]
     *  d [0, 1, 2, 2]
     *  e [0, 1, 2, 3]
     *  m轴
     */
    fun longestCommonSubsequenceDp(text1: String, text2: String): Int {
        var maxLength = 0

        val m = text1.length + 1
        val n = text2.length + 1

        val dpGrid = Array(m) { index ->
            IntArray(n)
        }

        for (i in 1 until m) {
            for (j in 1 until n) {
                dpGrid[i][j] =
                    if (text1[i - 1] == text2[j - 1]) {
                        dpGrid[i - 1][j - 1] + 1
                    } else {
                        Math.max(dpGrid[i][j - 1], dpGrid[i - 1][j])
                    }

                maxLength = Math.max(dpGrid[i][j], maxLength)
            }
        }

        return maxLength
    }

    /**
     * 162 ms	35.3 MB
     * Runtime: 162 ms, faster than 100.00% of Kotlin online submissions for Longest Common Subsequence.
     * Memory Usage: 35.3 MB, less than 96.88% of Kotlin online submissions for Longest Common Subsequence.
     * 对longestCommonSubsequenceDp的优化版本，使得空间复杂度为O(2N)
     * 思路是构建一个text1为竖轴m，text2为横轴n，然后按照0~n列，从左往右，分别计算text1[0~m]和text2[0~n]的LCS长度
     * 每次row记录的text1[0~i]和text2[0~j]的字符串的LCS长度
     */
    fun longestCommonSubsequenceDp2(text1: String, text2: String): Int {
        var maxLength = 0

        val m = text1.length + 1
        val n = text2.length + 1

        val cur = IntArray(m)
        var pre = IntArray(m)

        for (i in 1 until n) {
            for (j in 1 until m) {
                cur[j] =
                    if (text1[j - 1] == text2[i - 1]) {
                        pre[j - 1] + 1
                    } else {
                        Math.max(pre[j], cur[j - 1])
                    }
                maxLength = Math.max(cur[j], maxLength)
            }
            pre = cur.clone()
        }

        return maxLength
    }

}

fun main() {
    var text = Pair("abcde", "ace")
//    text = Pair("cabade", "aceae")
//    text = Pair("abcba", "abcbcba")
//    text = Pair("abc", "def")
//    text = Pair(
//        "bsbininm",
//        "jmjkbkjkv"
//    )
//    text = Pair(
//        "bsbinewwwwcasdcsadcsinm",
//        "jmjkbdasdwzqaakjkv"
//    )
//     text = Pair("abc", "abc")
    text = Pair(
        "ezupkr",
        "ubmrapg"
    )
    // 210
    text = Pair(
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    )

    println("result:${LongestCommonSubsequence().longestCommonSubsequenceDp(text.first, text.second)}")
    println("result:${LongestCommonSubsequence().longestCommonSubsequenceDp2(text.first, text.second)}")

    println('z' - 'a')

    val random = Random(System.currentTimeMillis())
    for (x in 0..100) {
        val str1 = CharArray(random.nextInt(x + 1)) { index ->
            'a' + random.nextInt('z' - 'a')
        }.toString()
        val str2 = CharArray(random.nextInt(x + 1)) { index ->
            'a' + random.nextInt('z' - 'a')
        }.toString()

        val dp1 = LongestCommonSubsequence().longestCommonSubsequenceDp(str1, str2)
        val dp2 = LongestCommonSubsequence().longestCommonSubsequenceDp2(str1, str2)
//        if (dp1 != dp2) {
            println("$str1,$str2 ($dp1,$dp2)")
//        }
    }
}