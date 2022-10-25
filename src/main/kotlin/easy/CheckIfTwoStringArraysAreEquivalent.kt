package easy

/**
 * @author : jixiaoyong
 * @description ： 1662. 检查两个字符串数组是否相等
 * 给你两个字符串数组 word1 和 word2 。如果两个数组表示的字符串相同，返回 true ；否则，返回 false 。
 * 数组表示的字符串 是由数组中的所有元素 按顺序 连接形成的字符串
 *
 * 提示：
 *
 * 1 <= word1.length, word2.length <= 10^3
 * 1 <= word1[i].length, word2[i].length <= 10^3
 * 1 <= sum(word1[i].length), sum(word2[i].length) <= 10^3
 * word1[i] 和 word2[i] 由小写字母组成
 *
 * https://leetcode.cn/problems/check-if-two-string-arrays-are-equivalent/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/25/2022
 */
class CheckIfTwoStringArraysAreEquivalent {
    fun arrayStringsAreEqual(word1: Array<String>, word2: Array<String>): Boolean {
        val wordCount1 = word1.size
        val wordCount2 = word2.size

        var index1 = 0
        var subIndex1 = 0
        var index2 = 0
        var subIndex2 = 0

        while (true) {
            if (index1 >= wordCount1 || index2 >= wordCount2) {
                break
            }

            val string1 = word1[index1]
            val string2 = word2[index2]

            if (string1[subIndex1++] != string2[subIndex2++]) {
                return false
            }

            if (subIndex1 > string1.length - 1) {
                subIndex1 = 0
                index1++
            }

            if (subIndex2 > string2.length - 1) {
                subIndex2 = 0
                index2++
            }

        }

        return index1 == wordCount1 && index2 == wordCount2 && subIndex1 ==0 && subIndex2 == 0
    }

}

fun main() {
    val obj = CheckIfTwoStringArraysAreEquivalent()

    val cases = mutableListOf(
        Pair(arrayOf("a",), arrayOf("a", "bc")),
        Pair(arrayOf("a",), arrayOf("a", "aa")),
        Pair(arrayOf("ab", "c"), arrayOf("a", "bc")),
        Pair(arrayOf("a", "cb"), arrayOf("ab", "c")),
        Pair(arrayOf("abc", "d", "defg"), arrayOf("abcddefg")),
        Pair(arrayOf("abc", "d", "defg"), arrayOf("abcddefg", "c")),
    )

    cases.forEach {
        val result = obj.arrayStringsAreEqual(it.first, it.second)
        println("result:$result ---> ${it.first.joinToString()} <>  ${it.second.joinToString()}")

    }
}