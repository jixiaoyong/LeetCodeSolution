package easy

/**
 * @author : jixiaoyong
 * @description ：2185. 统计包含给定前缀的字符串
 * 给你一个字符串数组 words 和一个字符串 pref 。
 *
 * 返回 words 中以 pref 作为 前缀 的字符串的数目。
 *
 * 字符串 s 的 前缀 就是 s 的任一前导连续字符串。
 *
 *
 * 提示：
 *
 * 1 <= words.length <= 100
 * 1 <= words[i].length, pref.length <= 100
 * words[i] 和 pref 由小写英文字母组成
 *
 * https://leetcode.cn/problems/counting-words-with-a-given-prefix/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/10/2023
 */
class CountingWordsWithAGivenPrefix {

    /**
     * 时间复杂度：O(n×m)
     * 空间复杂度：O(1)
     */
    fun prefixCount(words: Array<String>, pref: String): Int {
        var res = 0
        words.forEach {
            if (it.startsWith(pref)) {
                res++
            }
        }

        return res
    }
}

fun main() {
    val obj = CountingWordsWithAGivenPrefix()
    val cases = listOf(
        Pair(arrayOf("pay", "attention", "practice", "attend"), "at"),
        Pair(arrayOf("leetcode", "win", "loops", "success"), "code"),
    )

    for (case in cases) {
        println("${obj.prefixCount(case.first, case.second)}")
    }
}