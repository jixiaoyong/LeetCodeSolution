package easy

import javax.swing.text.html.HTML.Tag.I

/**
 * @author : jixiaoyong
 * @description ： 13. 罗马数字转整数
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做II，即为两个并列的 1 。12 写做XII，即为X+II。 27 写做XXVII, 即为XX+V+II。
 *
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。
 * 数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为IX。
 * 这个特殊的规则只适用于以下六种情况：
 *
 * I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
 * X可以放在L(50) 和C(100) 的左边，来表示 40 和90。
 * C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
 *
 * 给定一个罗马数字，将其转换成整数。
 *
 * 提示：
 *
 * 1 <= s.length <= 15
 * s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
 * 题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
 * 题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
 * IL 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
 * 关于罗马数字的详尽书写规则，可以参考 罗马数字 - Mathematics 。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/1/2022
 */
class RomanToInteger {

    /**
     * 从左往右，先匹配两个罗马字符的，再匹配1个罗马字符的
     * 	411 ms	42.7 MB
     */
    fun romanToInt(s: String): Int {
        var result = 0
        val romanToInteger = hashMapOf(
            Pair("I", 1),
            Pair("V", 5),
            Pair("X", 10),
            Pair("L", 50),
            Pair("C", 100),
            Pair("D", 500),
            Pair("M", 1000),
            Pair("IV", 4),
            Pair("IX", 9),
            Pair("XL", 40),
            Pair("XC", 90),
            Pair("CD", 400),
            Pair("CM", 900),
        )
        val sLen = s.length
        if (sLen == 1) {
            return romanToInteger[s] ?: -1
        }

        var cur = 0
        var next = 1
        while (cur < sLen) {
            if (next < sLen && romanToInteger.contains(s.substring(cur, next + 1))) {
                result += romanToInteger.get(s.subSequence(cur, next + 1))!!
                cur = next + 1
                next = cur + 1
            } else {
                result += romanToInteger.get(s.subSequence(cur, next))!!
                cur++
                next++
            }
        }

        return result
    }

    /**
     * 428 ms	46.3 MB
     * 从右往左，如果数字比前一个大则相加，否则相减
     */
    fun romanToIntRTL(s: String): Int {
        var result = 0
        val romanToInteger = hashMapOf(
            Pair("I", 1),
            Pair("V", 5),
            Pair("X", 10),
            Pair("L", 50),
            Pair("C", 100),
            Pair("D", 500),
            Pair("M", 1000),
        )

        var pre = 0
        for (i in s.length - 1 downTo 0) {
            val cur = romanToInteger.get(s[i].toString())!!
            if (cur >= pre) {
                result += cur
            } else {
                result -= cur
            }
            pre = cur
        }

        return result
    }

    /**
     * 此外还有一种，先替换所有IX、XL这样组合类型的罗马数字为单一字符，比如A、B、C
     * 然后使用包含A、B、C的表格依次识别String并累加得到结果
     */
}

fun main() {
    val obj = RomanToInteger()
    val cases = mutableListOf(
        "I", "X", "V", "III", "IX", "IV", "LVIII", "MCMXCIV", "MMMCMXCIX"/*3999*/, "CCCXXXII"/*332*/
    )
    cases.forEach {
        val result = obj.romanToInt(it)
        println("$result  ${obj.romanToIntRTL(it)} ---> $it")
    }
}