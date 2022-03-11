package medium

import medium.ReverseWordsInAString.reverseWords

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/11
 * description:
 * 151. Reverse Words in a String
 * https://leetcode.com/problems/reverse-words-in-a-string/
 *
 * Given an input string, reverse the string word by word.
 * A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.

Return a string of the words in reverse order concatenated by a single space.

Note that s may contain leading or trailing spaces or multiple spaces between two words.
The returned string should only have a single space separating the words. Do not include any extra spaces.

Constraints:

1 <= s.length <= 104
s contains English letters (upper-case and lower-case), digits, and spaces ' '.
There is at least one word in s.
 */
object ReverseWordsInAString {

    /**
     * 422 ms	42.4 MB
     */
    fun reverseWords(s: String): String {
        val formattedString = s.trim().replace("[ ]+".toRegex(), " ")
        val words = formattedString.split(" ")
        return words.reversed().joinToString(" ")
    }

    /**
     * 双指针
     * 304 ms	39.7 MB
     */
    fun reverseWordsWithoutSplit(s: String): String {
        val result = StringBuffer()

        // 二者都在s最末尾
        var wordRightIndex = s.length - 1
        var wordLeftIndex = s.length - 1

        // 从s最后一个词开始往前找，找到第一个空格，就是该词的左边界，如此直到wordLeftIndex == -1
        while (wordLeftIndex >= 0) {
            // find the right of word
            while (wordRightIndex >= 0 && s[wordRightIndex] == ' ') {
                wordRightIndex--
            }

            // find the left of word
            wordLeftIndex = wordRightIndex
            while (wordLeftIndex >= 0 && s[wordLeftIndex] != ' ') {
                wordLeftIndex--
            }

            if (wordLeftIndex <= wordRightIndex && wordRightIndex >= 0) {
                // append the word to result
                result.append(s.substring(wordLeftIndex + 1, wordRightIndex + 1) + " ")
                wordRightIndex = wordLeftIndex - 1
            } else {
                break
            }

        }

        if (result.last() == ' ') {
            result.deleteCharAt(result.length - 1)
        }

        return result.toString()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(reverseWordsWithoutSplit("Let's take LeetCode contest"))
        println(reverseWordsWithoutSplit("Let's"))
        println(reverseWordsWithoutSplit("Let'    s"))
        println(reverseWordsWithoutSplit("   L  et    s  "))
        println(reverseWordsWithoutSplit(" 0  "))
    }
}