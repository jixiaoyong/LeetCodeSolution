package easy

/**
 * @author : jixiaoyong
 * @description ：1684. 统计一致字符串的数目
 *
 * 给你一个由不同字符组成的字符串allowed和一个字符串数组words。
 * 如果一个字符串的每一个字符都在 allowed 中，就称这个字符串是 一致字符串 。
 * 请你返回words数组中一致字符串 的数目。
 *
 * 提示：
 *
 * 1 <= words.length <= 10^4
 * 1 <= allowed.length <= 26
 * 1 <= words[i].length <= 10
 * allowed中的字符 互不相同。
 * words[i] 和allowed只包含小写英文字母。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/8/2022
 */
class CountTheNumberOfConsistentStrings {

    /**
     * 思路：Int使用32位字节存储，而英文字符最多只有26个，可以用1表示有该字符，0表示没有
     * 位运算 or 的有如下性质：
     * 1. 0 or 0 == 0
     * 2. 1 or 0 == 1
     * 3. 1 or 1 == 1
     * 所以，分别计算[allowed] 和 [words] 各个字符串的对应的code（利用左移1实现），再将其分别进行or运算，如果计算结果与[allowed]对应的code
     * 一致，就说明[words]中这个字符串中的字符都在[allowed]中，否则不在。
     *
     * 时间复杂度：O(N + M)，N为[allowed]的字符长度，M为[words]各个字符串长度之和
     * 控件复杂度：O(1)
     */
    fun countConsistentStrings(allowed: String, words: Array<String>): Int {
        if (allowed.length == 26) {
            return words.size
        }

        val aCode = 'a'.toInt()

        var allowedCode = 0// 【注意，这里是0】
        allowed.forEach {
            allowedCode = allowedCode or (1 shl (it.toInt() - aCode))
        }

        var count = 0

        for (word in words) {
            var wordCode = 0
            word.forEach {
                wordCode = wordCode or (1 shl it.toInt() - aCode)
            }
            if (wordCode or allowedCode == allowedCode) {
                count++
            }
        }

        return count
    }

    /**
     * 另外一种思路，将allowed中各个字符做成hash表（或者直接放到一个长度为26的数组），然后遍历words中各个字符串的各个字符，查看其是否在hash表中
     */
}

fun main() {
    val obj = CountTheNumberOfConsistentStrings()
    val cases = mutableListOf(
        Pair(
            "exdohslrwipnt",
            arrayOf(
                "xrwlstne",
                "rs",
                "ioetdll",
                "lwi",
                "r",
                "pieonois",
                "r",
                "xtp",
                "stia",
                "gicfuvmnr",
                "hdntpxse",
                "sodxws",
                "v",
                "hstirooon",
                "d"
            )
        ),
        Pair("ab", arrayOf("ad", "bd", "aaab", "baa", "badab")),
        Pair("abc", arrayOf("a", "b", "c", "ab", "ac", "bc", "abc")),
        Pair("cad", arrayOf("cc", "acd", "b", "ba", "bac", "bad", "ac", "d")),
        Pair("cad", arrayOf("cc", "a", "abcdefghijklmnopkrstuvwxyz", "ba", "bac", "bad", "ac", "d")),
        Pair("abcdefghijklmnopkrstuvwxy", arrayOf("cc", "acd", "b", "ba", "bac", "bad", "ac", "d")),
    )

    cases.forEach {
        println("${obj.countConsistentStrings(it.first, it.second)}   ->  ${it.first}   ${it.second.joinToString()}")
    }
}