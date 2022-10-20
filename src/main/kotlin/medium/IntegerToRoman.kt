package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 12. 整数转罗马数字
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做II，即为两个并列的 1。12 写做XII，即为X+II。 27 写做XXVII, 即为XX+V+II。
 *
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。
 * 数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为IX。
 * 这个特殊的规则只适用于以下六种情况：
 * I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
 * X可以放在L(50) 和C(100) 的左边，来表示 40 和90。
 * C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
 *
 * 给你一个整数，将其转为罗马数字。
 *
 * 1 <= num <= 3999
 *
 * https://leetcode.cn/problems/integer-to-roman
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/20/2022
 */
class IntegerToRoman {

    /**
     * 模拟算法
     * 由于罗马数字和他的组合方式固定与num无关，所以时间和空间复杂度为O(1)
     * 352 ms	38.3 MB	kotlin
     */
    fun intToRoman(num: Int): String {

        var num = num
        var sb = StringBuffer()

        var mCount = num / 1000
        num -= mCount * 1000
        while (mCount-- > 0) {
            sb.append("M")
        }

        var dCount = num / 100
        num -= dCount * 100
        if (dCount == 9) {
            sb.append("CM")
            dCount = 0
        } else if (dCount >= 5) {
            sb.append("D")
            dCount -= 5
        }
        if (dCount == 4) {
            sb.append("CD")
        } else {
            while (dCount-- > 0) {
                sb.append("C")
            }
        }

        var xCount = num / 10
        num -= xCount * 10
        if (xCount == 9) {
            sb.append("XC")
            xCount = 0
        } else if (xCount >= 5) {
            sb.append("L")
            xCount -= 5
        }
        if (xCount == 4) {
            sb.append("XL")
        } else {
            while (xCount-- > 0) {
                sb.append("X")
            }
        }

        if (num == 9) {
            sb.append("IX")
            num = 0
        } else if (num >= 5) {
            sb.append("V")
            num -= 5
        }
        if (num == 4) {
            sb.append("IV")
        } else {
            while (num-- > 0) {
                sb.append("I")
            }
        }

        return sb.toString()
    }

    // 参考
    val hashMap = hashMapOf(
        Pair(1, "I"),
        Pair(4, "IV"),
        Pair(5, "V"),
        Pair(9, "IX"),
        Pair(10, "X"),
        Pair(40, "XL"),
        Pair(50, "L"),
        Pair(90, "XC"),
        Pair(100, "C"),
        Pair(400, "CD"),
        Pair(500, "D"),
        Pair(900, "CM"),
        Pair(1000, "M"),
    )

    /**
     * 	196 ms	36 MB
     * 官方题解思路：
     * 使用罗马字符将1000~3000，100~900,10~90,1~9全部翻译一次形成映射表，然后按照num从千/百/十/个位数依次翻译即可
     * https://leetcode.cn/problems/integer-to-roman/solution/zheng-shu-zhuan-luo-ma-shu-zi-by-leetcod-75rs/
     * 时间和空间复杂度与num无关，均为O(1)
     */
    fun intToRoman2(num: Int): String {

        val thousands = listOf("", "M", "MM", "MMM")
        val hundreds = listOf("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
        val tens = listOf("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
        val ones = listOf("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")

        val sb = StringBuffer()
        sb.append(thousands[num / 1000])
        sb.append(hundreds[num % 1000 / 100])
        sb.append(tens[num % 100 / 10])
        sb.append(ones[num % 10])

        return sb.toString()
    }


}

fun main() {
    val obj = IntegerToRoman()
    val random = Random(System.currentTimeMillis())

    println("1 --> ${obj.intToRoman(1)}")
    println("17 --> ${obj.intToRoman(17)}")
    println("1937 --> ${obj.intToRoman(1937)}")
    println("3682 --> ${obj.intToRoman(3682)}")
    println("3682 --> ${obj.intToRoman2(3682)}")

    for (x in 0..20) {
        val num = random.nextInt(0, 4000)
        println("$num --> ${obj.intToRoman2(num)}")
    }
}