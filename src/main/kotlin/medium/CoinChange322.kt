package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ：322. 零钱兑换
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
 * 你可以认为每种硬币的数量是无限的。
 *
 * 提示：
 *
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 2^31 - 1
 * 0 <= amount <= 10^4
 *
 * https://leetcode.cn/problems/coin-change/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/17/2022
 */
class CoinChange322 {

    /**
     * 349 ms	37.8 MB
     * 动态规划 时间复杂度O(MN),空间复杂度O(N)，M：coins个数，N：amount大小
     * 使用动态规划，最小硬币个数计算方式 f(N) = min(f(coins[0],f(coins[1])...f(coins[M]),)
     * 也就是说，要得知amount的最小个数，只需要计算出分别使用一枚coins硬币之后，剩余价格所需最小硬币个数
     * 反过来，只需要依次计算从0...N-（coins硬币）所需最小硬币个数即可
     */
    fun coinChange(coins: IntArray, amount: Int): Int {
        if (amount == 0) {
            return 0
        }


        val arr = IntArray(amount + 1) { -1 }
        arr[0] = 0

        // 从1开始，计算出直到n的所需最小硬币个数
        for (x in 1..amount) {
            var min = Int.MAX_VALUE
            // f(N) = min(f(coins[0],f(coins[1])...f(coins[M]),)
            for (coin in coins) {
                val count = getCount(arr, x - coin)
                if (count in 0 until min) {
                    min = count
                }
            }
            arr[x] = min + 1
        }

        val result = arr[amount]
        return if (result != Int.MAX_VALUE && result >= 0) result else -1
    }

    private fun getCount(array: IntArray, index: Int): Int {
        return if (index >= 0) array[index] else -1
    }

}

fun main() {
    val obj = CoinChange322()
    val random = Random(System.currentTimeMillis())

    println(obj.coinChange(intArrayOf(1, 2, 5), 11))
    println(obj.coinChange(intArrayOf(2), 3))
    println(obj.coinChange(intArrayOf(1), 0))
    println(obj.coinChange(intArrayOf(1, 2, 6, 5), 30))
    println(obj.coinChange(intArrayOf(186, 419, 83, 408), 6249))

    for (x in 0..20) {
        val coin = arrayOf(
            random.nextInt(0, Int.MAX_VALUE / 999999),
            random.nextInt(0, Int.MAX_VALUE / 99999),
            random.nextInt(0, Int.MAX_VALUE / 999999),
            random.nextInt(0, Int.MAX_VALUE / 99999),
            random.nextInt(0, Int.MAX_VALUE / 99999),
            random.nextInt(0, Int.MAX_VALUE / 999999),
            random.nextInt(0, Int.MAX_VALUE / 999999),
            random.nextInt(0, Int.MAX_VALUE / 999999),
            random.nextInt(0, Int.MAX_VALUE / 999999),
            random.nextInt(0, Int.MAX_VALUE / 999999),
            random.nextInt(0, Int.MAX_VALUE / 999999),
            random.nextInt(0, Int.MAX_VALUE / 999999),
        ).toIntArray()
        val amount = random.nextInt(0, 10000)
        print("coins:${coin.joinToString(",")},amount:$amount,result:")
        println(obj.coinChange(coin, amount))
    }
}