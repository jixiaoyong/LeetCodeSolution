package easy

import kotlin.math.sqrt
import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1668. 最大重复子字符串
 * 给你一个字符串sequence，如果字符串 word连续重复k次形成的字符串是sequence的一个子字符串，那么单词word 的 重复值为 k 。
 * 单词 word的 最大重复值是单词word在sequence中最大的重复值。如果word不是sequence的子串，那么重复值k为 0 。
 *
 * 给你一个字符串 sequence和 word，请你返回 最大重复值k 。
 *
 * 提示：
 *
 * 1 <= sequence.length <= 100
 * 1 <= word.length <= 100
 * sequence 和word都只包含小写英文字母。
 *
 * https://leetcode.cn/problems/maximum-repeating-substring/
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/3/2022
 */
class MaximumRepeatingSubstring {

    /**
     * 317 ms	36.3 MB
     * 思路：以sequence中每一个char为起点开始向后查找连续的word并计数，在此期间查找并统计最大值maxCount
     * 如果剩余的字符长度小于maxCount则无需再查找，直接返回maxCount即可
     * 时间复杂度：O(M*M/N),M为sequence长度，N为word长度，遍历M次，其中最坏情况[findContinuousWord]里面需要遍历M/N次
     * 空间复杂度：O(1) 使用常数存储结果
     */
    fun maxRepeating(sequence: String, word: String): Int {
        if (!sequence.contains(word)) {
            return 0
        }

        var maxCount = 0

        for (x in 0 until sequence.length) {
            val count = findContinuousWord(sequence, word, x)
            maxCount = Math.max(maxCount, count)
            if (sequence.length - x < maxCount) {
                break
            }
        }


        return maxCount
    }

    private fun findContinuousWord(sequence: String, word: String, x: Int): Int {
        var count = 0
        val wordLen = word.length
        if (sequence.length - x < wordLen) {
            return 0
        }

        var curIndex = x
        while (curIndex <= sequence.length - wordLen) {
            if (sequence.substring(curIndex, curIndex + wordLen ) != word) {
                break
            }
            count++
            curIndex += wordLen
        }

        return count
    }



    // 无法处理重叠的情况 "aaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba"
    fun maxRepeatingError(sequence: String, word: String): Int {
        if (!sequence.contains(word)) {
            return 0
        }

        val replacement = 'R'
        val replacedString = sequence.replace(word, replacement.toString())
        var maxCount = 0
        var curCount = 0

        replacedString.forEach { c: Char ->
            if (c == replacement) {
                curCount++
            } else {
                curCount = 0
            }
            maxCount = Math.max(maxCount, curCount)
        }

        return maxCount
    }


    /**
     * https://leetcode.cn/problems/maximum-repeating-substring/solution/zui-da-zhong-fu-zi-zi-fu-chuan-by-leetco-r4cp/
     * 此外还有暴力解法，分别将word重复1,2...n次，直到重复的word不在sequence中，退出循环，返回word最后一次有效的重复次数
     * TODO KMP方法待补充 https://oi-wiki.org/string/kmp/
     */
}

fun main() {
    val obj = MaximumRepeatingSubstring()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        Pair("aaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba"),//5
        Pair("aaabaaaabaaabaaaabaaaabaaaabaaaabaaaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba"),//7
        Pair("a", "ababc"),
        Pair("abab", "ababc"),
        Pair("ababc", "ab"),
        Pair("ababc", "ba"),
        Pair("ababc", "ac"),
        Pair("ababc", "a"),
        Pair("a", "a"),
        Pair("aaaaa", "a"),
        Pair("acaaaa", "a"),
    )

    val veryLargestString1 = StringBuffer()
    for (x in 0..99) {
        veryLargestString1.append(Char(random.nextInt('a'.code, 'z'.code)))
    }
    val veryLargestString2 = StringBuffer()
    for (x in 0..99) {
        veryLargestString2.append(Char(random.nextInt('a'.code, 'z'.code)))
    }

    cases.add(Pair(veryLargestString1.toString(), veryLargestString2.toString()))
    cases.add(Pair(veryLargestString2.toString(), veryLargestString1.toString()))
    cases.add(Pair(veryLargestString1.toString(), veryLargestString1.toString()))
    cases.add(Pair(veryLargestString1.toString(), "dacd"))

    cases.forEach {
        val result = obj.maxRepeating(it.first, it.second)
        println("$result --> $it")
    }
}