package medium

/**
 * @author : jixiaoyong
 * @description ： 7. 整数反转
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 *
 * 如果反转后整数超过 32 位的有符号整数的范围[−2^31, 2^31− 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 *
 * -2^31 <= x <= 2^31 - 1
 *
 * https://leetcode.cn/problems/reverse-integer/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/29/2022
 */
class ReverseInteger {

    /**
     * 思路是将数字转为String，然后再将其倒转，每次*10^n累加，并检查是否超出范围
     * 	228 ms	36.8 MB
     * 	时间复杂度O(N)
     */
    fun reverse(x: Int): Int {
        var x = x
        if (x < 10 && x > -10) {
            return x
        }
        while (x % 10 == 0) {
            x /= 10
        }
        val intMaxValueStr = Int.MAX_VALUE.toString()
        val intMinValueStr = (Int.MIN_VALUE).toString().substring(1)

        val str = x.toString().reversed().replace("-", "")

        val strLen = str.length
        var result = 0
        val isSameLen = intMaxValueStr.length == strLen
        val firstNumOfStr = Integer.parseInt(str[0].toString())
        val firstNumOfMaxValue = Integer.parseInt(intMaxValueStr[0].toString())
        if (isSameLen && firstNumOfStr > firstNumOfMaxValue) {
            return result
        }

        var needCareful = isSameLen && firstNumOfStr == firstNumOfMaxValue

        var index = 0
        if (x < 0) {
            while (index < strLen) {
                val tenTimes = Math.pow(10.0, strLen - index - 1.0).toInt()
                val curMinValue = Integer.parseInt(intMinValueStr[index].toString())
                val curValue = Integer.parseInt(str[index].toString())
                if (needCareful) {
                    if (curValue > curMinValue) {
                        return 0
                    }
                    needCareful = curValue == curMinValue
                }
                result += tenTimes * curValue
                index++

            }
        } else {
            while (index < strLen) {
                val tenTimes = Math.pow(10.0, strLen - index - 1.0).toInt()
                val curMaxValue = Integer.parseInt(intMaxValueStr[index].toString())
                val curValue = Integer.parseInt(str[index].toString())
                if (needCareful) {
                    if (curValue > curMaxValue) {
                        return 0
                    }
                    needCareful = curValue == curMaxValue
                }
                result += tenTimes * curValue
                index++
            }

        }


        return if (x < 0) {
            -result
        } else {
            result
        }
    }
}

fun main() {
    val obj = ReverseInteger()
    val cases =
        mutableListOf(
            Int.MIN_VALUE,
            123,
            1,
            -123,
            100000,
            100009,
            120,
            0,
            Int.MAX_VALUE,
            1000000008,
            1000000000,
            -1000000008
        )

    cases.forEach {
        val result = obj.reverse(it)
        println("$result   -> $it")
    }
}