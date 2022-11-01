package easy

/**
 * @author : jixiaoyong
 * @description ： 14. 最长公共前缀
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 提示：
 *
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 *
 * https://leetcode.cn/problems/longest-common-prefix/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/1/2022
 */
class LongestCommonPrefix {

    /**
     * 竖向遍历
     * 思路：
     * 每次对比每个字符串的相同位置的字符，如果都相同则进一位，否则退出，找到最大公共前缀
     * 时间复杂度O(MN)，M字符串数量，N字符串最短的长度
     * 空间复杂度O(1)，返回值不占用复杂度
     */
    fun longestCommonPrefix(strs: Array<String>): String {
        val sb = StringBuffer()

        var curIndex = 0
        loop@ while (true) {
            var curStr: String? = null
            for (str in strs) {
                if (curIndex >= str.length) {
                    break@loop
                }
                val curChar = str[curIndex].toString()
                curStr = curStr ?: curChar
                if (curChar != curStr) {
                    break@loop
                }
            }
            sb.append(curStr)
            curIndex++
        }

        return sb.toString()
    }

    /**
     * LeetCode官方解法：
     * 【分治法】，将对strs的最长公共前缀化解为求strs[0..n/2]和strs[n/2+1..n]的最长公共前缀的最长公共前缀
     * 进而将问题简化为求2个字符串的最长公共前缀
     * 时间复杂度：O(mn)
     * 空间复杂度：O(mlogn)， m 是字符串数组中的字符串的平均长度，n 是字符串的数量。
     *
     * 【二分查找】
     * 最长公共前缀的长度一定满足：0<= lcp <= minLength，其中minLength为strs中字符串最小长度
     * 那么可以用二分查找确定最长公共前缀的长度，从而确定最长公共前缀：
     * 如果strs中所有字符的0~mid的字符都相等，那么lcp一定>=mid，否则一定<mid，如此这般便可以确定其最终长度。
     * 时间复杂度：O(mnlog（m）)
     * 空间复杂度：O(1)
     */
    fun longestCommonPrefixDichotomy(strs: Array<String>): String {
        var minLength = Int.MAX_VALUE
        var lcpIndex = -1

        strs.forEach {
            minLength = Math.min(minLength, it.length)
        }

        var l = 0
        var r = minLength - 1

        while (l <= r) {
            val mid = (r + l) / 2
            if (isCommonPrefix(strs, mid)) {
                l = mid + 1
                lcpIndex = mid
            } else {
                r = mid - 1
            }
        }


        return if (lcpIndex >= 0) {
            strs[0].substring(0, lcpIndex + 1)
        } else {
            ""
        }
    }

    private fun isCommonPrefix(strs: Array<String>, endIndex: Int): Boolean {
        val commonPrefix = strs[0].subSequence(0, endIndex + 1)
        strs.forEach {
            if (it.subSequence(0, endIndex + 1) != commonPrefix) {
                return false
            }
        }
        return true
    }

}

fun main() {
    val obj = LongestCommonPrefix()
    val cases = mutableListOf(
        arrayOf("a"),
        arrayOf("flow", "flower", "flower"),
        arrayOf("flower", "flow", "flight"),
        arrayOf("flower", "dog", "flight"),
        arrayOf("dog", "racecar", "car"),
        arrayOf("dog"),
        arrayOf(""),
        arrayOf("a"),
        arrayOf("a","ad"),
        arrayOf("flower", "flower", "flower1"),
        arrayOf("flow", "flower", "flower1"),
        arrayOf("flow", "flower", "flower"),
    )
    for (case in cases) {
//        val result = obj.longestCommonPrefix(case)
        val result = obj.longestCommonPrefixDichotomy(case)
        println("$result --> ${case.joinToString()}")
    }
}