package easy

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/11
 * description: 415. Add Strings
 * Given two non-negative integers, num1 and num2 represented as string,
 * return the sum of num1 and num2 as a string.
You must solve the problem without using any built-in library for handling large integers (such as BigInteger).
You must also not convert the inputs to integers directly.

Constraints:

1 <= num1.length, num2.length <= 10^4
num1 and num2 consist of only digits.
num1 and num2 don't have any leading zeros except for the zero itself.


 */
object AddStrings {
    /**
     * 349 ms	99.3 MB
     */
    fun addStrings(num1: String, num2: String): String {
        var longNum = ""
        var shortNum = ""

        if (num1.length > num2.length) {
            longNum = num1
            shortNum = num2
        } else {
            longNum = num2
            shortNum = num1
        }

        val maxLen = longNum.length

        var resultNum = ""
        var shortNumIndex = shortNum.length - 1
        var extraNum = 0
        for (i in maxLen - 1 downTo 0) {

            val currentLongNum = longNum[i].toString().toInt()
            val currentShortNum = if (shortNumIndex < 0) 0 else shortNum[shortNumIndex].toString().toInt()

            var sum = currentLongNum + currentShortNum + extraNum
            if (sum > 9) {
                extraNum = 1
                sum -= 10
            } else {
                extraNum = 0
            }

            resultNum = sum.toString() + resultNum
            shortNumIndex--

            // 如果短的数字已经遍历完了
            if (shortNumIndex < 0 && extraNum == 0) {
                if (i > 0) {
                    resultNum = longNum.subSequence(0, i).toString() + resultNum
                }
                break
            }
        }

        // 这里处理的是那种2个数字加完了，还有额外的进位1的情况
        return if (extraNum == 0) resultNum else "1" + resultNum
    }

    /**
     * 216 ms	35.7 MB
     * https://leetcode-cn.com/problems/add-strings/solution/si-lu-jian-dan-xing-neng-gao-xiao-da-dao-7qp4/
     */
    fun addStringsUltra(num1: String, num2: String): String {
        var extra = 0
        var result = ""
        var i = num1.length - 1
        var j = num2.length - 1

        // 无论数字长短，都从最后一位开始遍历，直到2个数字都完全遍历完
        while (i >= 0 || j >= 0) {
            val a = if (i >= 0) num1[i--].toString().toInt() else 0
            val b = if (j >= 0) num2[j--].toString().toInt() else 0
            val sum = a + b + extra
            extra = sum / 10
            result = (sum % 10).toString() + result

            // 加上下面的判断后反而降低了性能
            // Runtime: 322 ms
            // Memory Usage: 99.1 MB
//            if ((i < 0 || j < 0) && extra == 0) {
//                // 规避某个数字特别长的情况
//                if (i>=0) {
//                    result = num1.substring(0, i+1) + result
//                }
//                if (j>=0) {
//                    result = num2.substring(0, j+1) + result
//                }
//                break
//            }
        }

        // 这里处理的是那种2个数字加完了，还有额外的进位1的情况
        return if (extra == 0) result else "1" + result
    }


    @JvmStatic
    fun main(args: Array<String>) {
        println(addStringsUltra("1", "9"))
        println(addStringsUltra("1", "99"))
        println(addStringsUltra("0", "99"))
        println(addStringsUltra("13130", "99"))
        println(addStringsUltra("1320", "2"))
        println(addStringsUltra("135757575677520", "2"))
        println(addStringsUltra("2312", "34"))
    }
}