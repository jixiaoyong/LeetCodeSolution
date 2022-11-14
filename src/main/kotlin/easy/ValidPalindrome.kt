package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ：125. 验证回文串
 * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
 *
 * 字母和数字都属于字母数字字符。
 *
 * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
 *
 * 提示：
 *
 * 1 <= s.length <= 2 * 10^5
 * s 仅由可打印的 ASCII 字符组成
 *
 * https://leetcode.cn/problems/valid-palindrome/
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/14/2022
 */
class ValidPalindrome {

    /**
     * 双指针
     * 将left和right指针不断向内移动，直到二者都遇到了数字或字母，然后对比，如果不同则返回false，否则继续向内移动
     * 如果直到left和right相遇还没有不同的数字或字母，则是回文
     * 时间复杂度O(N)，N是字符串长度
     * 空间复杂度O(1)，只需常量保存两个指针位置
     */
    fun isPalindrome(s: String): Boolean {
        var left = 0
        var right = s.length - 1
        val s = s.toLowerCase()
        val lowerCaseRange = IntRange('a'.toInt(), 'z'.toInt())
        val numberRange = IntRange('0'.toInt(), '9'.toInt())

        while (left < right) {
            if (left >= s.length || right < 0) {
                break
            }

            var leftChar = s[left++].toInt()
            while (!lowerCaseRange.contains(leftChar) && !numberRange.contains(leftChar) && left < s.length) {
                leftChar = s[left++].toInt()
            }
            var rightChar = s[right--].toInt()
            while (!lowerCaseRange.contains(rightChar) && !numberRange.contains(rightChar) && right >= 0) {
                rightChar = s[right--].toInt()
            }

            if ((right + 1) >= (left - 1) && leftChar != rightChar) {
                return false
            }
        }
        return true
    }

    /**
     * Leetcode提供另外一种解法：
     * 将字符串中非数字和字符去除，然后翻转，对比翻转前后字符串是否相等，如果相等则是回文
     * https://leetcode.cn/problems/valid-palindrome/solution/yan-zheng-hui-wen-chuan-by-leetcode-solution/
     * 时间复杂度：O(∣s∣)，其中 ∣s∣ 是字符串 s 的长度。
     * 空间复杂度：O(|s|)，由于我们需要将所有的字母和数字字符存放在另一个字符串中，在最坏情况下，新的字符串sgood 与原字符串 s 完全相同，
     *  因此需要使用O(∣s∣) 的空间。
     */
}

fun main() {
    val obj = ValidPalindrome()
    val random = Random(System.currentTimeMillis())

    val cases = mutableListOf(
        "A man, a plan, a canal: Panama",
        "ab2a", "0P",
        "aaa",
        "xs",
        "sxdsaxsxdsax",
        "race a car",
        "a",
        "293.xs",
        "aaaaaaaa"
    )
    val largestString = StringBuffer(100000)
    for (i in 0 until 100000) {
        largestString.append(Char(random.nextInt(32, 122)))
    }
//    cases.add(largestString.toString())

    cases.forEach {
        println("${obj.isPalindrome(it)}   ---> $it")
    }
}