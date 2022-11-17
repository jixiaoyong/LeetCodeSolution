package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ：392. 判断子序列
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 *
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。
 * （例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 *
 * 进阶：
 *
 * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。
 * 在这种情况下，你会怎样改变代码？
 *
 * 提示：
 *
 * 0 <= s.length <= 100
 * 0 <= t.length <= 10^4
 * 两个字符串都只由小写字符组成。
 *
 * https://leetcode.cn/problems/is-subsequence/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/17/2022
 */
class IsSubsequence {
    /**
     * 思路：分别使用指针依次从s和t的最左侧开始向右遍历，遇到相同的字符则将s的下标后移，直到遍历到s或t的末尾
     * 时间复杂度：O(N+M)，N为t的长度，M为s长度，每次匹配成功与否，都需要移动一次指针（t或s的），总的最大移动距离为N+M
     * 空间复杂度：O(1)
     */
    fun isSubsequence(s: String, t: String): Boolean {
        var indexs = 0
        var indext = 0
        while (indexs < s.length && indext < t.length) {
            val curS = s[indexs]
            while (indext < t.length) {
                if (curS == t[indext++]) {
                    indexs++
                    break
                }
            }
        }
        return indexs == s.length
    }

    /**
     * 动态规划
     * 思路是：
     * 方法[isSubsequence]的每次是向后查找某个字符在字符串t中下一次的位置，那么如果可以提前计算出t中每个位置某个字符的下一个位置，并保存到dp中，
     * 那么当给定一个字符s时，只需要依次对s中每个字符c，读取dp[i][c]找到其在t中的位置，直到遍历完s
     * 令dp[i][c]为t中i位置处，其后方下一个字符c的位置，则从右往左遍历
     *
     * 时间复杂度O(m+n) ，n为s长度，m为t长度，C为字符集
     * 空间复杂度O(m*C)
     */
    fun isSubsequenceDp(s: String, t: String): Boolean {
        val dp = Array(t.length + 1) { IntArray(26) { -1 } }
        for (i in t.length - 1 downTo 0) {
            val charIndex = t[i].toInt() - 'a'.toInt()
            val curMap = dp[i + 1].copyOf()
            curMap[charIndex] = i
            dp[i] = curMap
        }

        var dpIndex = 0
        for (i in 0 until s.length) {
            val c = s[i].toInt() - 'a'.toInt()
            dpIndex = dp[dpIndex][c]
            if (dpIndex == -1) {
                return false
            }
            dpIndex++
        }

        return true
    }
}

fun main() {
    val obj = IsSubsequence()
    val random = Random(System.currentTimeMillis())
    val cases =
        mutableListOf(
            Pair("addd", "adxssxd"),
            Pair("addd", "adxsdsxsea"),
            Pair("abc", "ahbgdc"),
            Pair("axc", "ahbgdc"),
            Pair("axc", ""),
            Pair("", "ahbgdc"),
            Pair("", ""),
            Pair("a", "a"),
            Pair("a", "aaaa"),
        )

    val sb = StringBuffer()
    for (i in 0 until 10000) {
        sb.append(Char(random.nextInt('a'.code, 'z'.code + 1)))
    }
    cases.add(Pair("ddadeadaojfaeofajfaeofadjfaeofadmxadxadawedadx", sb.toString()))
    cases.add(Pair("ddadeadaojfaeofajfaeofadmxadxadawedadxddadeadaojfaeofajfaeofadmxadxadawedadx", sb.toString()))

    cases.forEach {
        val result = obj.isSubsequence(it.first, it.second)
        val result2 = obj.isSubsequenceDp(it.first, it.second)
        println("$result/$result2 -->${it.first}  / ${it.second}")
    }
}