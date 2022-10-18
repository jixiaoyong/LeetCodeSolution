package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 69. x 的平方根
 * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
 * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
 * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
 *
 * 0 <= x <= 231 - 1
 * https://leetcode.cn/problems/sqrtx/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/18/2022
 */
class SqrtX69 {

    /**
     * 293 ms	34.2 MB
     * 暴力计算
     */
    fun mySqrt(x: Int): Int {
        if (x <= 1) {
            return x
        }

        // 使用double而非int，避免计算值超出Int.MAX_VALUE
        var pre = 1.0
        var cur = 2.0

        while (cur * cur <= x) {
            pre = cur
            cur++
        }
        return pre.toInt()
    }

    /**
     * 二分法查找
     * 239 ms	34.5 MB
     * 一个数x的平方根必定在0~x之间
     * 时间复杂度：O(logx)，即为二分查找需要的次数。
     * 空间复杂度：O(1)。
     * 来自https://leetcode.cn/problems/sqrtx/solution/x-de-ping-fang-gen-by-leetcode-solution/
     */
    fun mySqrtDichotomy(x: Int): Int {
        if (x <= 1) {
            return x
        }

        var min = 0
        var max = x
        while (min < max) {
            val mid: Int = (max + min) / 2
            // double以保存超过Int.Max的数据
            val midPow = mid * 1.0 * mid
            if (midPow == x.toDouble()) {
                return mid
            } else if (midPow < x) {
                min = mid
            } else {
                max = mid
            }

            if (max - min == 1) {
                return min
            }
        }

        return (max + min) / 2
    }

}

fun main() {
    val obj = SqrtX69()
    val random = Random(System.currentTimeMillis())

    val nums = arrayListOf(0, 1, 2, 3, 4, 8, 9, 2147395600, 687771546, 1229654820, Int.MAX_VALUE)

    for (x in 0..20) {
        val num = random.nextInt(0, Int.MAX_VALUE)
        nums.add(num)

    }
    nums.forEach { num ->
        val mySqrt = obj.mySqrtDichotomy(num)
        val sqrt = Math.sqrt(num.toDouble())
        println("$num, mySqrt:${mySqrt}, sqrt:${sqrt},      is equal ${mySqrt == sqrt.toInt()}")
    }
}