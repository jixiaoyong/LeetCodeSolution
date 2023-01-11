package easy

/**
 * @author : jixiaoyong
 * @description ：2283. 判断一个数的数字计数是否等于数位的值
 * 给你一个下标从 0开始长度为 n的字符串num，它只包含数字。
 *
 * 如果对于 每个0 <= i < n的下标i，都满足数位i在 num中出现了num[i]次，那么请你返回true，否则返回false。
 *
 * 提示：
 *
 * n == num.length
 * 1 <= n <= 10
 * num 只包含数字。
 *
 * https://leetcode.cn/problems/check-if-number-has-equal-digit-count-and-digit-value/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/11/2023
 */
class CheckIfNumberHasEqualDigitCountAndDigitValue {

    /**
     * N为num长度
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     */
    fun digitCount(num: String): Boolean {
        val digitCounts = IntArray(10) { i -> 0 }
        val zeroCharInt = '0'.toInt()
        // real count of digit
        num.forEach {
            val digit = it.toInt() - zeroCharInt
            val count = digitCounts[digit]
            digitCounts[digit] = count + 1
        }

        num.forEachIndexed { index, c ->
            // expect count in num
            val count = c.toInt() - zeroCharInt
            val realCount = digitCounts[index]
            if (count != realCount) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val obj = CheckIfNumberHasEqualDigitCountAndDigitValue()
    val cases = listOf("2010","1210","030","1")
    cases.forEach {
        println("${obj.digitCount(it)}   ---> $it")
    }
}