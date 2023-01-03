package easy

/**
 * @author : jixiaoyong
 * @description ： 2042. 检查句子中的数字是否递增
 *
 * 句子是由若干 token 组成的一个列表，token 间用 单个 空格分隔，句子没有前导或尾随空格。每个 token 要么是一个由数字 0-9 组成的不含前导零的 正整数，要么是一个由小写英文字母组成的 单词 。
 *
 * 示例，"a puppy has 2 eyes 4 legs" 是一个由 7 个 token 组成的句子："2" 和 "4" 是数字，其他像"puppy" 这样的 tokens 属于单词。
 * 给你一个表示句子的字符串 s ，你需要检查 s 中的 全部 数字是否从左到右严格递增（即，除了最后一个数字，s 中的 每个 数字都严格小于它 右侧 的数字）。
 *
 * 如果满足题目要求，返回 true，否则，返回 false 。
 *
 * https://leetcode.cn/problems/check-if-numbers-are-ascending-in-a-sentence/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/3/2023
 */
class CheckIfNumbersAreAscendingInASentence {

    /**
     * 思路：遍历一次string，遇到数字则拼接，遇到空格则判断本次遇到的是数字的话，与上一个数字比较，如果不是递增则返回false
     * 如果本次不是数字，则不进行比较，如此反复直到遍历完成string
     *
     * 时间复杂度：O(N)
     * 空间复杂度:O(1)
     */
    fun areNumbersAscending(s: String): Boolean {
        var preNum = Int.MIN_VALUE
        var curNumStr = ""
        var isDigit = false

        for (i in 0..s.length) {
            val c = s.getOrNull(i) ?: ' '
            when (c) {
                ' ' -> {
                    if (isDigit) {
                        val curNum = curNumStr.toInt()
                        if (curNum <= preNum) {
                            return false
                        }
                        preNum = curNum
                    }
                    curNumStr = ""
                }
                in '0'..'9' -> {
                    isDigit = true
                    curNumStr += c
                }
                else -> {
                    isDigit = false
                    curNumStr = ""
                }
            }
        }

        return true
    }
}

fun main() {
    val obj = CheckIfNumbersAreAscendingInASentence()
    val cases = mutableListOf(
        "hello world 5 x 5",
        "1 box has 3 blue 4 red 6 green and 12 yellow marbles",
        "sunset is at 7 51 pm overnight lows will be in the low 50 and 60 s",
        "4 5 11 26",
        "66 55 44 33 22 11",
    )

    cases.forEach {
        val res = obj.areNumbersAscending(it)
        println("${res}   ---> $it")
    }
}