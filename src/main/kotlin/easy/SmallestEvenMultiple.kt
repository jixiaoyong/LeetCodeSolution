package easy

/**
 * @author : jixiaoyong
 * @description ：2413. 最小偶倍数
 *
 * 给你一个正整数 n ，返回 2 和 n 的最小公倍数（正整数）。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/4/21
 */
class SmallestEvenMultiple {
    fun smallestEvenMultiple(n: Int): Int {
        return if (n % 2 == 0) n else 2 * n
    }
}

fun main() {
    val obj = SmallestEvenMultiple()
    val cases = arrayListOf(intArrayOf(1, 2), intArrayOf(6, 6), intArrayOf(5, 10))

    for (case in cases) {
        val res = obj.smallestEvenMultiple(case[0])
        println("res: $res /${case.joinToString()} ---> case: ${case.joinToString()}")
    }
}