package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1780. 判断一个数字是否可以表示成三的幂的和
 *
 * 给你一个整数n，如果你可以将n表示成若干个不同的三的幂之和，请你返回true，否则请返回 false。
 *
 * 对于一个整数 y，如果存在整数 x满足 y == 3^x，我们称这个整数 y是三的幂。
 *
 * 提示：
 *
 * 1 <= n <= 10^7
 *
 * https://leetcode.cn/problems/check-if-number-is-a-sum-of-powers-of-three/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/9/2022
 */
class CheckIfNumberIsASumOfPowersOfThree {

    /**
     * 思路，在题目范围内，3的次幂小于10^7，则次幂可能小于20，所以可以遍历所有的次幂计算其和，然后看是否包含指定的[n]
     * 时间复杂度：O(N！)
     * 空间复杂度 O（N）
     */
    fun checkPowersOfThree(n: Int): Boolean {
        var curPowerOfThree = 1
        val powers = mutableListOf(0)
        while (curPowerOfThree <= n) {
            if (curPowerOfThree == n) {
                return true
            }

            val curLen = powers.size
            for (i in 0 until curLen) {
                val newValue = powers[i] + curPowerOfThree
                if (newValue == n) {
                    return true
                }
                powers.add(newValue)
            }
            powers.add(curPowerOfThree)
            curPowerOfThree *= 3
        }
        return false
    }

}

fun main() {
    val obj = CheckIfNumberIsASumOfPowersOfThree()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(12, 32, 1, 14, 13)
    for (i in 0 until 20) {
        cases.add(random.nextInt(0, 10000000))
    }

    println("${cases.joinToString("\n")}")

    cases.forEach {
        println("$it   ---> ${obj.checkPowersOfThree(it)}")
    }
}