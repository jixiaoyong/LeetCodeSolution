package medium


/**
 * @author : jixiaoyong
 * @description ： 5. 最长回文子串
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * https://leetcode.cn/problems/longest-palindromic-substring/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/17/2022
 */
class LongestPalindromicSubstring {

    /**
     * 425 ms	46.5 MB
     * 550 ms	103.4 MB
     * 动态规划法，主要突破点在于，grid[i][j]表示string从i到j的回文（i为竖轴，j为横轴），
     * 如果i等于j，那么要满足回文，必须grid[i+1][j-1]也是回文
     * 如此只需要计算出最小的回文，即可知道最大的回文，而根据grid[i+1][j-1]，也就可知，必须先从列表的左下方开始，逐渐到右上方
     * 而为了避免重复计算，只计算i<=j的情况
     * 时间复杂度O(N^2)
     * 空间复杂度O(N^2)
     */
    fun longestPalindrome(s: String): String {
        val len = s.length
        if (len <= 1) {
            return s
        }

        var result = s[0].toString()
        val grid = Array(len) { index -> IntArray(len) }

        // 从对角线右下角开始向上遍历
        for (i in len - 1 downTo 0) {
            for (j in i until len) {
                if (i == j) {
                    grid[i][j] = 1
                } else {
                    // 如果i和j的字符相等，那么去除这两个字符之后的内部子串也是回文（单个字符也是回文），那么i到j直接的字符是回文
                    if (s[i] == s[j] && (grid[i + 1][j - 1] == 1 || j - i == 1)) {
                        grid[i][j] = 1
                        if (result.length - 1 < j - i) {
                            result = s.substring(i, j + 1)
                        }
                    }
                }
            }
        }

        return result
    }


    // 416 ms	101 MB
    // longestPalindrome的优化版本
    fun longestPalindrome2(s: String): String {
        val len = s.length
        if (len <= 1) {
            return s
        }

        val grid = Array(len) { index -> IntArray(len) }
        var start = 0
        var end = 0

        // 从对角线右下角开始向上遍历
        for (i in len - 1 downTo 0) {
            for (j in i until len) {
                if (i == j) {
                    grid[i][j] = 1
                } else if (s[i] == s[j] && (grid[i + 1][j - 1] == 1 || j - i == 1)) {
                    // 如果i和j的字符相等，那么去除这两个字符之后的内部子串也是回文（单个字符也是回文），那么i到j直接的字符是回文
                    grid[i][j] = 1
                    if (end - start < j - i) {
                        start = i
                        end = j
                    }

                }
            }
        }
        return s.substring(start, end + 1)
    }

    fun isPalindrome(string: String): Boolean {
        var start = 0
        var end = string.length - 1
        if (end <= start) {
            return true
        }
        while (end > start) {
            if (string[start] != string[end]) {
                return false
            }
            end--
            start++
        }
        return true
    }
}

fun main() {
    val obj = LongestPalindromicSubstring()
    println(obj.longestPalindrome("a"))
    println(obj.longestPalindrome("aa"))
    println(obj.longestPalindrome("aba"))
    println(obj.longestPalindrome("abda"))
    println(obj.longestPalindrome("abba"))
    println(obj.longestPalindrome("abcba"))
    println(obj.longestPalindrome("aaaabcbaxx"))
}