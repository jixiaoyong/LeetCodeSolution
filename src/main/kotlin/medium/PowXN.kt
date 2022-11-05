package medium

/**
 * @author : jixiaoyong
 * @description ： 50. Pow(x, n)
 * 实现 pow(x, n) ，即计算 x 的整数 n 次幂函数（即，x^n ）。
 *
 * 提示：
 *
 * -100.0 < x < 100.0
 * -2^31 <= n <= 2^31-1
 * -10^4 <= xn <= 10^4
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/5/2022
 */
class PowXN {
    /**
     * 	1160 ms	36.1 MB
     * 	【不推荐】
     * 思路是：将x^n划分为了个x^（10*（n/10）） 与 x^(n%10)的乘积
     * 时间复杂度是O(N/10)
     */
    fun myPow(x: Double, n: Int): Double {
        if (x == 0.0 || x == 1.0) {
            return x
        }
        if (n == 0) return 1.0
        if (n == 1) return x

        val nDivider2 = Math.abs(n / 10)
        var result = 1.0

        for (i in 1..nDivider2) {
            result *= (x * x) * (x * x) * (x * x) * (x * x) * (x * x)
        }
        var nLeft4 = Math.abs(n % 10)
        while (nLeft4-- > 0) {
            result *= x
        }

        return if (n > 0) result else (1 / result)
    }

    /**
     * 196 ms	36.4 MB
     * LeetCode方法
     * 每次将x^n分为x^(n/2)*x^(n/2)，如果n是奇数，则再补上一个*x
     * 时间、空间复杂度：O(logn)
     * https://leetcode.cn/problems/powx-n/solution/powx-n-by-leetcode-solution/
     */
    fun myPowLeetcode(x: Double, n: Int): Double {
        // 将n转化为long，这样负数[Int.MIN_VALUE]转为正数也不会溢出
        val N = n.toLong()
        return if (N >= 0) quickMul(x, N) else 1.0 / quickMul(x, -N)
    }

    fun quickMul(x: Double, N: Long): Double {
        if (N == 0L) {
            return 1.0
        }
        val y = quickMul(x, N / 2)
        // 这里保存了quickMul的值，然后将二者相乘，避免了一次递归，【也避免了耗时】
        return if (N % 2 == 0L) y * y else y * y * x
    }

    // 将上述递归转为循环，时间复杂度O(logn),空间复杂度O(1)
    fun myPow2(x: Double, n: Int): Double {
        val N = n.toLong()
        return if (N >= 0) quickMul2(x, N) else 1.0 / quickMul2(x, -N)
    }

    fun quickMul2(x: Double, N: Long): Double {
        var N = N
        var ans = 1.0
        // 贡献的初始值为 x
        var x_contribute = x
        // 在对 N 进行二进制拆分的同时计算答案
        while (N > 0) {
            if (N % 2 == 1L) {
                // 如果 N 二进制表示的最低位为 1，那么需要计入贡献
                ans *= x_contribute
            }
            // 将贡献不断地平方
            x_contribute *= x_contribute
            // 舍弃 N 二进制表示的最低位，这样我们每次只要判断最低位即可
            N /= 2
        }
        return ans
    }
}

fun main() {
    val obj = PowXN()
    val cases = mutableListOf(
        Pair(2.0, -2),
        Pair(1.3, 31),
        Pair(0.0, 0),
        Pair(0.0, 1),
        Pair(0.0, 2),
        Pair(2.0, 0),
        Pair(2.0, 2),
        Pair(2.0, 1),
        Pair(2.0, 3),
        Pair(0.06, 313131),
        Pair(0.07000, Int.MAX_VALUE),
        Pair(2.0, Int.MIN_VALUE),
        Pair(1.0, Int.MIN_VALUE),
//        Pair(1 / 2.0, Int.MIN_VALUE), // 值超范围
    )

    val timeMs = System.currentTimeMillis()
    cases.forEach {
        val result = obj.myPow(it.first, it.second)
        println("${result}   ${it}")
        if (result < -10000 || result > 10000) {
            throw IndexOutOfBoundsException()
        }
    }

    println("time:${System.currentTimeMillis() - timeMs} ms")
}