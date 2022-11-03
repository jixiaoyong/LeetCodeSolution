package easy

import java.util.*
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
     * Brute Force 蛮力算法
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
            if (sequence.substring(curIndex, curIndex + wordLen) != word) {
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
     * 分别将word重复1,2...n次，直到重复的word不在sequence中，退出循环，返回word最后一次有效的重复次数
     */
    fun maxRepeatingForeach(sequence: String, word: String): Int {
        if (word.length > sequence.length) {
            return 0
        }
        val sb = StringBuffer()
        var count = 0
        while ((count + 1) * word.length <= sequence.length) {
            count++
            sb.append(word)
            if (!sequence.contains(sb.toString())) {
                count--
                break
            }
        }
        return count
    }

    /**
     * https://leetcode.cn/problems/maximum-repeating-substring/solution/zui-da-zhong-fu-zi-zi-fu-chuan-by-leetco-r4cp/
     * 此外还有暴力解法，分别将word重复1,2...n次，直到重复的word不在sequence中，退出循环，返回word最后一次有效的重复次数
     *  KMP方法待补充 https://oi-wiki.org/string/kmp/
     */
    fun maxRepeatingKmp(sequence: String, word: String): Int {
        var result = 0
        val wordLen = word.length

        val next = IntArray(wordLen + 1)
        for (x in 1..wordLen) {
            next[x] = findPrefixSuffixCoincide(word, x)
        }

        var curIndex = 0
        // 使用DP存储每次发现word的末尾坐标的连续次数，这样就不用在遍历每个word时还要考虑是否连续（此表已经记录了位置关系）
        val counts = IntArray(sequence.length)
        var curWordIndex = 0

        while (curIndex < sequence.length) {

            var isAdd = false
            if (word[curWordIndex] == sequence[curIndex]) {
                // 1. 字符相等的情况，继续增加sequence和word的下标
                curIndex++
                curWordIndex++
                isAdd = true
            } else if (curWordIndex > 0) {
                // 2. 正在遍历word的过程中，发现不匹配
                // curWordIndex的长度为curWordIndex+1,其前面字段的长度为curWordIndex
                curWordIndex = next[curWordIndex] // ①这里关键的一点，

            } else {
                // 3. word还没开始匹配到，继续增加sequence的下标
                curIndex++
                isAdd = true
            }

            if (curWordIndex == wordLen) {
                val index = if (isAdd) curIndex - 1 else curIndex
                counts[index] = (counts.getOrNull(index - wordLen) ?: 0) + 1
                result = Math.max(result, counts[index])
                // 这里关键的一点，即使是完成了一次完整的匹配，也不要将curWordIndex从0开始，而是从word.length对应的PMT中的下标开始
                // 这样可以覆盖ababa这样包含2个aba的情况，后续遍历时即使word=aba和ababa对不上，也可以在关键点①这里重新对齐
                curWordIndex = next[curWordIndex]
            }
        }


        return result
    }

    /**
     * 计算长度为[len]的字符的PMT，即word[0~len-1]的前缀集合和后缀集合的交集的最长元素的长度（等效于该对应前缀的下标+1）
     * len<=1则无前缀或后缀（一个字符串无法成为自身的前缀、后缀）
     * len取值为1~word.length
     */
    private fun findPrefixSuffixCoincide(word: String, len: Int): Int {
        var count = 0
        // 计算word[0~len]的长度为x时的前缀与后缀是否相等，若相等则计算长度
        for (x in 2..len) {
            val prefix = word.substring(0, x - 1)
            val suffix = word.substring(len - x + 1, len)
            if (prefix == suffix) {
                count = Math.max(count, prefix.length)
            }
        }

        return count
    }

    /**
     * KMP+DP
     * 思路：
     * 1. 创建PMT
     * 2. 然后使用KMP搜索每个[word]出现的位置
     * 3. 在2的过程中，使用DP记录每个位置[word]连续的次数
     * 4. 在DP中选出最大的并返回
     * 时间复杂度、空间复杂度：O(m+n)
     */
    fun maxRepeatingLeetcode(sequence: String, word: String): Int {
        val n = sequence.length
        val m = word.length
        if (n < m) {
            return 0
        }
        val fail = IntArray(m)
        Arrays.fill(fail, -1)
        // 计算word的前缀和后缀集合的最大公共长度
        for (i in 1 until m) {
            var j = fail[i - 1]
            while (j != -1 && word[j + 1] != word[i]) {
                j = fail[j]
            }
            if (word[j + 1] == word[i]) {
                fail[i] = j + 1
            }
        }
        val f = IntArray(n)
        var j = -1
        for (i in 0 until n) {
            // 遍历sequence，分别查找每个word的位置，并记录其连续出现的次数到f中
            while (j != -1 && word[j + 1] != sequence[i]) {
                j = fail[j]
            }
            if (word[j + 1] == sequence[i]) {
                ++j
                if (j == m - 1) {
                    f[i] = (if (i >= m) f[i - m] else 0) + 1
                    // 这里使用fail[j]，可以覆盖ababa这样，包含2个aba的情况
                    j = fail[j]
                }
            }
        }
        return Arrays.stream(f).max().getAsInt()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val s = "abababca"
            val obj = MaximumRepeatingSubstring()
            for (x in 1..s.length) {
                val result = obj.findPrefixSuffixCoincide(s, x)
                println("No.$x $result,")
            }
        }
    }

}

fun main() {
    val obj = MaximumRepeatingSubstring()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        Pair("a", "a"),
        Pair("ababc", "ab"),//5
        Pair("aaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba"),//5
        Pair("aaabaaaabaaabaaaabaaaabaaaabaaaabaaaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba"),//7
        Pair("a", "ababc"),
        Pair("abab", "ababc"),
        Pair("ababc", "ab"),
        Pair("ababc", "ba"),
        Pair("ababc", "ac"),
        Pair("ababc", "a"),
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
//        val result = obj.maxRepeating(it.first, it.second)
        val result = obj.maxRepeatingLeetcode(it.first, it.second)
        val result2 = obj.maxRepeatingKmp(it.first, it.second)
        val result3 = obj.maxRepeating(it.first, it.second)
        val result4 = obj.maxRepeatingForeach(it.first, it.second)
        println("$result $result2 $result3 $result4 --> $it")
    }
}