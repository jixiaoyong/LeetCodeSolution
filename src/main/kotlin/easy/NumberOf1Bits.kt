package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 191. 位1的个数
 * 编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中数字位数为 '1' 的个数（也被称为汉明重量）。
 * 提示：
 *
 * 请注意，在某些语言（如 Java）中，没有无符号整数类型。在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，
 * 因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
 * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。因此，在上面的 示例 3 中，输入表示有符号整数 -3。
 *
 * https://leetcode.cn/problems/number-of-1-bits/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/20/2022
 */
class NumberOf1Bits {
    /**
     * 247 ms	33.8 MB
     * you need treat [n] as an unsigned value
     * 时间复杂度O（K），k是二进制位数，本例中固定为32
     * 空间复杂度O(1)
     */
    fun hammingWeight(n: Int): Int {

        // 将“无符号”int转化为long
        // Int.MAX_VALUE+1即2147483648，二进制为 10000000000000000000000000000000，时 n=-1
        var num = n * 1.0
        if (n < 0) {
            num = Int.MAX_VALUE + Int.MIN_VALUE * -1.0 + 1.0 * num + 1
        }

        var countOne = 0
        while (num >= 1) {
            val r = num % 2
            if (r == 1.0) {
                countOne++
            }
            num = (num - r) / 2
        }

        return countOne
    }

    // 以下为LeetCode官方题解

    fun hammingWeight2(n: Int): Int {
        return n.countOneBits()
    }

    // 观察这个运算：n&(n - 1)，其运算结果恰为把 n 的二进制位中的最低位的 1 变为 0 之后的结果
    // 时间复杂度：O(logn)。循环次数等于 n 的二进制位中 11 的个数，最坏情况下 nn 的二进制位全部为 11。我们需要循环 logn 次。
    // 空间复杂度：O(1)，我们只需要常数的空间保存若干变量。
    // 链接：https://leetcode.cn/problems/number-of-1-bits/solution/wei-1de-ge-shu-by-leetcode-solution-jnwf/
    fun hammingWeight3(n: Int): Int {
        var n = n
        var ret = 0
        while (n != 0) {
            n = n and n - 1
            ret++
        }
        return ret
    }
}

fun main() {
    val obj = NumberOf1Bits()
    val random = Random(System.currentTimeMillis())


    println("${obj.hammingWeight(-1)} -> ${4294967295}\n")
    println("${obj.hammingWeight(2)} -> 2\n")
    println("${obj.hammingWeight(3)} -> 3\n")
    println("${obj.hammingWeight(4)} -> 4\n")
    println("${obj.hammingWeight(5)} -> 5\n")

//    for (x in 0..20) {
//        val num = random.nextInt()
//        println("${obj.hammingWeight(num)} -> $num\n")
//    }

}