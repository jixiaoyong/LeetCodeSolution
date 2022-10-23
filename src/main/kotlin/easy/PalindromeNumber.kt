package easy

/**
 * @author : jixiaoyong
 * @description ：9. 回文数
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * 例如，121 是回文，而 123 不是。
 *
 * https://leetcode.cn/problems/palindrome-number/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/23/2022
 */
class PalindromeNumber {

    /**
     * 493 ms	39.5 MB
     * 思路是将x转为string，然后分别从首尾遍历对比每个数字是否一致，直到right<=left
     * 时间复杂度O(N) 遍历次数和x的位数N有关
     * 空间复杂度O(N) 额外一个字符串保存X的位数
     */
    fun isPalindrome(x: Int): Boolean {
        if (x < 0) {
            return false
        }
        if (x < 10) {
            return true
        }

        val xStr = "$x"
        var left = 0
        var right = xStr.length - 1

        while (left < right) {
            if (xStr[left++] != xStr[right--]) {
                return false
            }
        }

        return true
    }

    /**
     * LeetCode官方题解
     * 250 ms	35.1 MB
     *
     * 思路是，不将int转为string，而是按照回文的特性，将整数x拆分为前后2部分，并翻转后半部分：
     * （最开始后半部分小于前半部分，继续拆分）
     * 如果后半部分等于前半部分则是回文；
     * 如果后半部分已经大于前半部分了，说明不是回文
     *
     * 此方法弊端在于需要一直拆分数字长度一半才能知道结果，但是因为数字Int位数不会很长，所以性能影响不大
     * 此外，<0、除0外尾数为0的不是回文;个位数都是回文
     */
    fun isPalindromeLeetcode(x: Int): Boolean {
        if (x < 0) {
            return false
        }
        if (x < 10) {
            return true
        }

        if (x % 10 == 0) {
            return false
        }

        var prefix = x
        var tail = 0
        while (tail < prefix) {
            tail = tail * 10 + prefix % 10
            prefix /= 10
        }
        // 1221 || 121
        return tail == prefix || prefix == tail / 10
    }
}

fun main() {
    val obj = PalindromeNumber()
    val cases =
        mutableListOf(
            -1,
            0,
            1,
            12,
            123,
            121,
            12321,
            123454321,
            123321,
            11,
            22,
            10,
            -121,
            2147447412,
            Int.MAX_VALUE,
            Math.pow(2.0, 31.0) - 1
        )

    cases.forEach {
        println("${obj.isPalindromeLeetcode(it.toInt())} ： $it")
    }
}