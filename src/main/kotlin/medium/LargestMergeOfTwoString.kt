package medium

import kotlin.random.Random

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/12/24
 * description: 1754. 构造字典序最大的合并字符串
 * 给你两个字符串 word1 和 word2 。你需要按下述方式构造一个新字符串 merge ：如果 word1 或 word2 非空，选择 下面选项之一 继续操作：
 *
 * 如果 word1 非空，将 word1 中的第一个字符附加到 merge 的末尾，并将其从 word1 中移除。
 * 例如，word1 = "abc" 且 merge = "dv" ，在执行此选项操作之后，word1 = "bc" ，同时 merge = "dva" 。
 * 如果 word2 非空，将 word2 中的第一个字符附加到 merge 的末尾，并将其从 word2 中移除。
 * 例如，word2 = "abc" 且 merge = "" ，在执行此选项操作之后，word2 = "bc" ，同时 merge = "a" 。
 * 返回你可以构造的字典序 最大 的合并字符串 merge 。
 *
 * 长度相同的两个字符串 a 和 b 比较字典序大小，如果在 a 和 b 出现不同的第一个位置，a 中字符在字母表中的出现顺序位于 b 中相应字符之后，就认为字符串 a 按字典序比字符串 b 更大。例如，"abcd" 按字典序比 "abcc" 更大，因为两个字符串出现不同的第一个位置是第四个字符，而 d 在字母表中的出现顺序位于 c 之后。
 *
 * 提示：
 *
 * 1 <= word1.length, word2.length <= 3000
 * word1 和 word2 仅由小写英文组成
 *
 * https://leetcode.cn/problems/largest-merge-of-two-strings/
 *
 */
class LargestMergeOfTwoString {


    /**
     * todo
     * 【仍然存在问题】
     * 思路：使用双指针同时遍历两个字符串，优先选取比较大的那个char添加到result里面；
     * 如果在这个过程中遇到了相同的字符串，则记录下当前的坐标index，然后一直向后查找，直到遇到下一个不同的字符diff，这期间走了offset步，
     * 在此过程中需要将遍历过的char添加到result中比较遇到的两个不同字符：
     * 如果word1里面比word2里面的char大，那么就遍历word1以后字符，反之则是word2之后的字符
     * 此时，相当于两个字符串，一个遍历的位置在index+offset，一个遍历的位置在index处，
     * 【注意这里我们有index~index+offset个字符没有加入到result中，我们称其为“重复字符串”】
     * 那么，继续往下遍历位置在index+offset的字符串时时，如果遇到比重复字符串开头大的字符，直接加入到result中，
     * 反之如果比其小，则将重复字符串加入其中，如果二者相等，则需要找到他们下一个不一样的字符，然后选取较大的一方，添加入result
     *
     * 理想情况下时间复杂度为O(M+N),空间复杂度为O(1)
     *
     */
    fun largestMerge(word1: String, word2: String): String {
        val len1 = word1.length
        val len2 = word2.length
        val charArr = CharArray(len1 + len2)

        var index1 = 0
        var index2 = 0
        var charIndex = 0
        var offset = 0

        while (index1 + offset < len1 && index2 + offset < len2) {
            val char1 = word1[index1 + offset]
            val char2 = word2[index2 + offset]

            if (char2 > char1) {
                index2 += offset
                offset = 0
                index2++
                charArr[charIndex++] = char2
            } else if (char1 > char2) {
                index1 += offset
                offset = 0
                index1++
                charArr[charIndex++] = char1
            } else {
                // 相等的情况，需要看他们接下来第一个不相等的char来决定选谁
                var curChar = char1

                if (offset > 0) {
                    var cur = index1 + offset
                    var preChar = word1[cur - offset]
                    var step = 0
                    while (preChar == curChar) {
                        // 如果比较时，这批重复的chars的开头和当前的char相同
                        // 那么，此时是从当前char开始拼接，还是从重复的chars开头拼接
                        // 取决与他们后续的char中哪个较大
                        charArr[charIndex++] = preChar
                        preChar = word1[cur - offset + ++step] ?: Char.MAX_VALUE
                        curChar = word1.getOrNull(cur + step) ?: Char.MIN_VALUE
                    }

                    if (preChar > curChar) {
                        // 这批重复的chars的开头后续的char比"当前的char"之后的char大
                        // 之前遍历中消耗的是重复的chars中的字符，"当前的char"并未被消耗，所以要指向原位
                        // 之后中重复的chars中剩余部分 和 "当前的char"对比
                        index1 += step
                        index2 += step
                        offset -= step
                        curChar = char1
                    } else {
                        // 这批重复的chars的开头后续的char比"当前的char"之后的char小
                        // 那么，继续从新的"当前的char"开始拼接，"重复的chars"长度继续延长
                        offset += step
                    }



                    while (preChar > curChar && offset > 0) {
                        charArr[charIndex++] = preChar
                        offset--
                        index1++
                        index2++
                        preChar = word1[cur - offset]
                    }


                }
                offset++
                charArr[charIndex++] = curChar
            }

        }

        for (i in index1 + offset until len1) {
            charArr[charIndex++] = word1[i]
        }

        for (i in index2 + offset until len2) {
            charArr[charIndex++] = word2[i]
        }

        while (offset > 0) {
            charArr[charIndex++] = word1[len1 - offset]
            offset--
        }

        return String(charArr)
    }


    /**
     * 作者：lcbin
     *
     * https://leetcode.cn/problems/largest-merge-of-two-strings/solution/by-lcbin-t1mu/
     * 时间复杂度 O(n^2)其中 n 是字符串 word1 和 word2 的长度之和。
     */
    fun largestMergeLeetcode(word1: String, word2: String): String? {
        val m = word1.length
        val n = word2.length
        var i = 0
        var j = 0
        val ans = StringBuilder()
        while (i < m && j < n) {
            // 使用系统的api，每次都是将两个string剩余部分的char依次对比，如果有一个char比对方大，就先将其的第一个char添加到结果
            val gt = word1.substring(i).compareTo(word2.substring(j)) > 0
            ans.append(if (gt) word1[i++] else word2[j++])
        }
        ans.append(word1.substring(i))
        ans.append(word2.substring(j))
        return ans.toString()
    }

}

fun main() {
    val obj = LargestMergeOfTwoString()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        arrayOf(
            "gfjyjximobyfvmtcugsuiawovxyldglqkgooqpvwqkmewpkxldpxygvfajcfcqgvygwskywrryvhllmcnvcldoldkcurdkdgkitmbjralmnyyclw",
            "gfjyjximobyfvmtcugsuiawovxyldglqkgooqpvwqkmewpkxldpxygvfajcfcqgvygwskywrryvhllmcnvcldoldkcurdkdgkitmbjralmnyyclw",
        ),
        arrayOf(
            "gfjyjximobyfvmtcugsuiawovxyldglqkgooqpvwqkaaamewpkxldpxygvfajcfcqgvygwskywrryvhllmcnvcldoldkcurdkdgkitmbjralmnyyclw",
            "gfjyjximobyfvmtcugsuiawovxyldglqkgooqpvwqkaaamewpkxldpxygvfajcfcqgvygwskywrryvhllmcnvcldoldkcurdkdgkitmbjralmnyyclw",
        ),
        arrayOf(
            "uuurruuuruuuuuuuuruuuuu",
            "urrrurrrrrrrruurrrurrrurrrrruu",
        ),
        arrayOf(
            "nnnnnnnnnnnennnnnn",
            "nnnnennennnnnnnennn",
        ),
        arrayOf("a", "a"),
        arrayOf("a", "b"),
        arrayOf("aaaa", "aaaa"),
        arrayOf("aaaa", "bbb"),
        arrayOf("abcabc", "abdcaba", "abdcabcabcaba"),
        arrayOf("cabaa", "bcaaa"),
    )


    val string1 = CharArray(3000) { Char(random.nextInt('a'.toInt(), 'z'.toInt())) }
    val string2 = CharArray(3000) { Char(random.nextInt('a'.toInt(), 'z'.toInt())) }
    cases.add(arrayOf(String(string1), String(string2)))


    cases.forEach {
        val res = obj.largestMerge(it[0], it[1])
        val resOK = obj.largestMergeLeetcode(it[0], it[1])
        println("[${res == resOK}]  ${res}  || ok ${resOK}   ---${it.joinToString()}")
    }
}