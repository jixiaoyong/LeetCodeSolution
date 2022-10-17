package easy

/**
 * @author : jixiaoyong
 * @description ： 70. 爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * https://leetcode.cn/problems/climbing-stairs/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/17/2022
 */
class ClimbingStairs {

    /**
     * 时间复杂度O(N),空间复杂度O(1)
     * 140 ms	32.9 MB
     */
    fun climbStairs(n: Int): Int {

        if (n <= 2) {
            return n
        }

        // n2,n1分别表示n-2，n-1时对应的步数,比如n为3时，n1为2对应的步数2，n2为1对应的步数1
        var n1 = 2
        var n2 = 1
        var curr = 0
        for (x in 3..n) {
            curr = n2 + n1
            n2 = n1
            n1 = curr
        }
        return curr
    }


    /**
     * LeetCode官方解法
     * https://leetcode.cn/problems/climbing-stairs/solution/pa-lou-ti-by-leetcode-solution/
     * 通向公式，f(n)=f(n-1)+f(n-2)，得到x^2 = x+1
     */
     fun climbStairs2(n: Int): Int {
        val sqrt5 = Math.sqrt(5.0)
        val fibn = Math.pow((1 + sqrt5) / 2, (n + 1).toDouble()) - Math.pow((1 - sqrt5) / 2, (n + 1).toDouble())
        return Math.round(fibn / sqrt5).toInt()
    }
}

fun main() {
    val obj = ClimbingStairs()
    for (x in 1..45) {
        val xVal = obj.climbStairs(x)
        val x12 = obj.climbStairs(x - 1) + obj.climbStairs(x - 2)
        println("No.$x, steps:${xVal},   f(x-1)+f(x-2):${x12}    equals:    ${xVal == x12}")
    }
}