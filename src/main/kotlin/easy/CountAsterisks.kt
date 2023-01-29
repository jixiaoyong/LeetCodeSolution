package easy

/**
 * @author : jixiaoyong
 * @description ：2315. 统计星号
 * 给你一个字符串s，每两个连续竖线'|'为 一对。换言之，第一个和第二个'|'为一对，第三个和第四个'|'为一对，以此类推。
 *
 * 请你返回 不在 竖线对之间，s中'*'的数目。
 *
 * 注意，每个竖线'|'都会 恰好属于一个对。
 *
 * 提示：
 *
 * 1 <= s.length <= 1000
 * s 只包含小写英文字母，竖线 '|' 和星号 '*' 。
 * s 包含 偶数 个竖线 '|' 。
 *
 * https://leetcode.cn/problems/count-asterisks/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/29/2023
 */
class CountAsterisks {
    fun countAsterisks(s: String): Int {
        var result = 0
        var hasLine = false
        for (c in s) {
            if (!hasLine) {
                if (c == '*') {
                    result++
                } else if (c == '|') {
                    hasLine = true
                }
            } else {
                if (c == '|') {
                    hasLine = false
                }
            }
        }

        return result
    }
}

fun main() {
    val obj = CountAsterisks()
    val cases =
        mutableListOf("l|*e*et|c**o|*de|", "iamprogrammer", "yo|uar|e**|b|e***au|tifu|l", "*", "||", "|*|", "*|*|")

    for (case in cases) {
        val res = obj.countAsterisks(case)
        println("$res --- $case")
    }
}