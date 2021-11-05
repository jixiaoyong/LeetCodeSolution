/*
* @description: 3. 无重复字符的最长子串
* https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2021/11/5
*/
object LongestSubstringWithoutRepeatingCharacters {

    fun lengthOfLongestSubstring(s: String): Int {
        var maxLen = 0
        var longestString = ""
        s.forEach {
            if (!longestString.contains(it)) {
                longestString += it
                // 只记录最大的长度
                if (longestString.length > maxLen) {
                    maxLen = longestString.length
                }
            } else {
                // 如果包含了，则从最后一个重复字符的下一个位置开始
                longestString = longestString.removeRange(0, longestString.indexOf(it) + 1)
                longestString += it.toString()
            }
        }

        return maxLen
    }
}

fun main() {
    val strList = arrayListOf("dvdf", "abc", "abcabcbb", "pwwkew", "aaaa", "deafgrwege435fse", "", "decefvvdeada")
    strList.forEach {
        println(it + ":" + LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring(it))
    }

}