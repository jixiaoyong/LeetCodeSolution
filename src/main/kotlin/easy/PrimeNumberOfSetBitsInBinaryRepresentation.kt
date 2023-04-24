package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ：762. 二进制表示中质数个计算置位
 * 给你两个整数left和right ，在闭区间 [left, right]范围内，统计并返回 计算置位位数为质数 的整数个数。
 *
 * 计算置位位数 就是二进制表示中 1 的个数。
 *
 * 例如， 21的二进制表示10101有 3 个计算置位。
 *
 * 质数是指除了1和本身以外，没有其他正整数能够整除它的自然数。例如2、3、5、7、11、13等都是质数。
 * 提示：
 *
 * 1 <= left <= right <= 10^6
 * 0 <= right - left <= 10^4
 *
 * 链接：https://leetcode.cn/problems/prime-number-of-set-bits-in-binary-representation
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/4/24
 */
class PrimeNumberOfSetBitsInBinaryRepresentation {
    fun countPrimeSetBits(left: Int, right: Int): Int {
        var res = 0
        for (i in left..right) {
            val bits = calBits(i)
            if (isPrime(bits)) {
                res++
            }
        }
        return res
    }

    /* Brian Kernighan 算法 计算数字二进制中的1的个数
    * n&(n-1)可以去掉n的二进制表示中的最后一个1的特性，不断去掉n的二进制表示中的1，直到n为0为止
     */
    fun calBits(num: Int): Int {
        var n = num
        var count = 0
        while (n != 0) {
            count++
            n = n and (n - 1)
        }
        return count
    }

    /**
     * 计算当前数字是否为质数
     * 如果num小于等于1，则不是质数；否则，从2开始到num的平方根之间的每个整数i，如果num能被i整除，则num不是质数，否则num是质数。
     */
    fun isPrime(num: Int): Boolean {
        if (num <= 1) {
            return false
        }
        // 如果num不是质数，那么它一定可以分解为两个大于1的整数p和q的乘积，即num = p * q。
        // 如果p,q都大于num得平方根，那么p*q>num，这违反题设，所以至少存在一个数字小于num的平方根。
        // 故而这里只需要计算从2到num的平方根里面有没有数字满足可以被num整除的数字即可。
        for (i in 2..Math.sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val obj = PrimeNumberOfSetBitsInBinaryRepresentation()
    val cases = mutableListOf<IntArray>()
    val random = Random(System.currentTimeMillis())

    cases.add(intArrayOf(1, 1))
    cases.add(intArrayOf(1, 1000000))
    cases.add(intArrayOf(100, 1000000))

    for (i in 0 until 10) {
        val left = random.nextInt(0, 1000000)
        val right = random.nextInt(left, Math.min(1000000, left + 10000))
        cases.add(intArrayOf(left, right))
    }

    cases.forEach {
        println(obj.calBits(it[0]) == it[0].countOneBits())
        println(obj.calBits(it[1]) == it[1].countOneBits())
    }

    cases.forEach {
        val res = obj.countPrimeSetBits(it[0], it[1])
        println("${it.joinToString()}   $res")
    }
}