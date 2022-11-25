package medium

/**
 * @author : jixiaoyong
 * @description ：809. 情感丰富的文字
 * 有时候人们会用重复写一些字母来表示额外的感受，比如 "hello" -> "heeellooo", "hi" -> "hiii"。
 * 我们将相邻字母都相同的一串字符定义为相同字母组，例如："h", "eee", "ll", "ooo"。
 *
 * 对于一个给定的字符串 S ，如果另一个单词能够通过将一些字母组扩张从而使其和 S 相同，我们将这个单词定义为可扩张的（stretchy）。
 * 扩张操作定义如下：选择一个字母组（包含字母c），然后往其中添加相同的字母c使其长度达到 3 或以上。
 *
 * 例如，以"hello" 为例，我们可以对字母组"o" 扩张得到 "hellooo"，但是无法以同样的方法得到 "helloo" 因为字母组 "oo" 长度小于3。
 * 此外，我们可以进行另一种扩张 "ll" -> "lllll" 以获得"helllllooo"。
 * 如果S = "helllllooo"，那么查询词"hello" 是可扩张的，因为可以对它执行这两种扩张操作使得query = "hello" -> "hellooo" ->"helllllooo" = S。
 *
 * 输入一组查询单词，输出其中可扩张的单词数量。
 *
 * 提示：
 *
 * 0 <= len(S) <= 100。
 * 0 <= len(words) <= 100。
 * 0 <= len(words[i]) <= 100。
 * S和所有在words中的单词都只由小写字母组成。
 *
 * https://leetcode.cn/problems/expressive-words/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/25/2022
 */
class ExpressiveWords {

    /**
     * 思路是模拟处s中各个字母组，然后与words中各个字符串的字母组对比
     * 时间复杂度O(N+M)，N是[s]长度，M是[words]中字符总数
     * 空间复杂度O(N+M)
     * 228 ms	35.5 MB
     */
    fun expressiveWords(s: String, words: Array<String>): Int {
        val strCharList = mutableListOf<Pair<Char, Int>>()
        if (s.isEmpty() || words.isEmpty()) {
            return 0
        }

        var preChar = s[0]
        var preIndex = 0
        strCharList.add(Pair(preChar, 1))
        for (i in 1 until s.length) {
            val cur = s[i]
            if (cur == preChar) {
                val prePair = strCharList[preIndex]
                strCharList[preIndex] = Pair(prePair.first, prePair.second + 1)
            } else {
                strCharList.add(Pair(cur, 1))
                preChar = cur
                preIndex++
            }
        }

        var count = 0

        words.forEach { word ->
            if (word[0] != s[0]) {
                return@forEach
            }

            val wordCharList = mutableListOf<Pair<Char, Int>>()

            var preChar = '*'
            var preIndex = -1
            for (i in 0..word.length) {
                val cur = word.getOrNull(i)

                if (cur == preChar) {
                    val prePair = wordCharList[preIndex]
                    wordCharList[preIndex] = Pair(prePair.first, prePair.second + 1)
                } else {
                    if (preIndex >= 0) {
                        val strPair = strCharList.getOrNull(preIndex)
                        if (strPair == null) {
                            // word的字母组个数比s长
                            return@forEach
                        }
                        // 如果s和word前一个字母组的字符不一样，则退出
                        if (strCharList[preIndex].first != wordCharList[preIndex].first) {
                            return@forEach
                        }
                        // 如果word中某个字符连续个数大于s中对应字符，则退出
                        if (wordCharList[preIndex].second > strPair.second) {
                            return@forEach
                        }
                        // s里面此处字符连续个数小于3，则要求必须word个数必须与之相等
                        if (strPair.second < 3 && wordCharList[preIndex].second != strPair.second) {
                            return@forEach
                        }
                    }

                    if (cur != null) {
                        wordCharList.add(Pair(cur, 1))
                        preChar = cur
                        preIndex++
                    } else if (i != word.length) {
                        return@forEach
                    }

                }
            }

            if (preIndex == strCharList.size - 1) {
                // 确保s和word的字母组个数一致
                count++
            }
        }

        return count
    }

    /**
     * Leetcode 双指针
     * 思路是：
     * 使用双指针i，j分别从s和word从左向右遍历，遇到相同的字符则一直向后遍历并计数，直到遇到不一样的字符，或者到达了字符串末尾
     * 此时对比i和j分别对应的字符的长度，j对应的字符不能大于i对应的字符数，如果i对应的字符长度<3，则j对应的必须与之相等。
     * 如果上述条件都满足，并且s和word都遍历到了末尾，则说明可以由word拓展到s，计数加一
     *
     * 179 ms	35.3 MB
     * 时间复杂度O(N+M)，N是[s]长度，M是[words]中字符总数
     * 空间复杂度O(1)
     * https://leetcode.cn/problems/expressive-words/solution/qing-gan-feng-fu-de-wen-zi-by-leetcode-s-vmnm/
     */
    fun expressiveWordsLeetCode(s: String, words: Array<String>): Int {
        var count = 0
        words.forEach {
            if (expandWords(s, it)) {
                count++
            }
        }
        return count
    }

    private fun expandWords(s: String, word: String): Boolean {
        var i = 0
        var j = 0

        while (i < s.length && j < word.length) {
            val sChar = s[i++]
            val wChar = word[j++]
            if (sChar != wChar) {
                return false
            }

            var sCharCount = 1
            while (i < s.length && s[i] == sChar) {
                i++
                sCharCount++
            }

            var wCharCount = 1
            while (j < word.length && word[j] == wChar) {
                j++
                wCharCount++
            }

            if (sCharCount < wCharCount) {
                return false
            } else if (sCharCount < 3 && wCharCount != sCharCount) {
                return false
            }

        }

        return i == s.length && j == word.length
    }
}

fun main() {
    val obj = ExpressiveWords()
    val cases = mutableListOf(
        Pair("heeellooo", arrayOf("heeelloooworld")),//0
        Pair("heeelllloooheeelllloooheeelllloooheeellllooo", arrayOf("helllllllllo", "hi", "helo")),//0
        Pair("zzzzzyyyyy", arrayOf("zzyy", "zy", "zyy")),
        Pair("heeellooo", arrayOf("hello", "hi", "helo")),
        Pair("heeellllooo", arrayOf("hello", "hi", "helo")),// hello->heeellllooo
        Pair("heeellllooo", arrayOf("helllo", "hi", "helo")),
        Pair("heeellllooo", arrayOf("helllllllllo", "hi", "helo")),
    )

    cases.forEach {
        println(
            "${obj.expressiveWords(it.first, it.second)} / ${
                obj.expressiveWordsLeetCode(
                    it.first,
                    it.second
                )
            }  -> ${it.first} : ${it.second.joinToString()}"
        )
    }
}