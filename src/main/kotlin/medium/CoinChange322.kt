package medium

import java.util.*
import kotlin.collections.HashMap
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


    /**
     * 递归
     * 676 ms	99.2 MB
     * 思路是每次都尝试每个coins的以获取最小的硬币数目，依次递归直到amount等于0时，表示可以使用该组合组成指定钱币数目
     * 需要使用hashMap保存已经计算过的amount最小个数，否则耗时会剧增
     */
    var hashMap = HashMap<Int, Int>()
    fun coinChangeRecursion(coins: IntArray, amount: Int): Int {
        if (amount < 0) {
            return -1
        } else if (amount == 0) {
            return 0
        }

        if (hashMap.contains(amount)) {
            return hashMap.get(amount)!!
        }


        var minimum = Int.MAX_VALUE
        for (coin in coins) {
            val count = coinChangeRecursion(coins, amount - coin)
            if (count >= 0) {
                minimum = Math.min(count + 1, minimum)
            }
        }

        val result = if (minimum == Int.MAX_VALUE) -1 else minimum
        hashMap.put(amount, result)

        return result
    }


    /**
     * 来自https://leetcode.cn/u/dao-fa-zi-ran-2/
     * 对于[2,4,6,8,10,12,14,16,18,20,22,24]，9999会超时
     */
    fun coinChange3(coins: IntArray, amount: Int): Int {
        coins.sort()
        minCount = Int.MAX_VALUE
        recursion(coins, amount, 0, coins.size - 1)
        return if (minCount == Int.MAX_VALUE) -1 else minCount
    }

    var minCount = Int.MAX_VALUE

    /**
     * 1、按金额从大到小，从多到少（排序，用余数一步到位）
     * 2、预判低于最优解，终止递归（可以返回最优解，不过提升有限，意义不大）
     * 3、能整除即可返回
     */
    fun recursion(coins: IntArray, amount: Int, count: Int, index: Int) {
        if (index < 0 || count + amount / coins[index] >= minCount) return
        if (amount % coins[index] == 0) {
            // 这里每次使用上次coin的组合数目，所以可直接计算总最小硬币数目
            minCount = Math.min(minCount, count + amount / coins[index])
            return
        }
        // 递归核心算法，依次计算使用0~amount/coin[index]个coin[index]和其他coin[0~index]时的最小count
        // 避免使用了某个coin而导致无法凑齐价钱数目的情况
        // 这里的downTo 0特别关键
        // 但是这种逻辑，对于amount特别大，而coins特别小的情况，会导致for循环特别大，特别耗时，从而超时
        for (i in amount / coins[index] downTo 0) {
            recursion(coins, amount - i * coins[index], count + i, index - 1)
        }
    }

}

fun main() {
    val obj = CoinChange322()
    val random = Random(System.currentTimeMillis())

    println(obj.coinChange(intArrayOf(1, 2, 5), 11))
    println(obj.coinChange3(intArrayOf(1, 2, 5), 11))
    println(obj.coinChange(intArrayOf(2), 3))
    println(obj.coinChange3(intArrayOf(2), 3))
    println(obj.coinChange(intArrayOf(1), 0))
    println(obj.coinChange3(intArrayOf(1), 0))
    println(obj.coinChange(intArrayOf(1, 2, 6, 5), 30))
    println(obj.coinChange3(intArrayOf(1, 2, 6, 5), 30))
    println(obj.coinChange(intArrayOf(186, 419, 83, 408), 6249))
    println(obj.coinChange3(intArrayOf(186, 419, 83, 408), 6249))
    println(obj.coinChange(intArrayOf(2,4,6,8,10,12,14,16,18,20,22,24), 9999))
    println(obj.coinChange3(intArrayOf(2,4,6,8,10,12,14,16,18,20,22,24), 9999))

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
        println(obj.coinChange3(coin, amount))
    }


    obj.hashMap.clear()
    println(obj.coinChangeRecursion(intArrayOf(1, 2, 5), 11))
    obj.hashMap.clear()
    println(obj.coinChangeRecursion(intArrayOf(2), 3))
    obj.hashMap.clear()
    println(obj.coinChangeRecursion(intArrayOf(1), 0))
    obj.hashMap.clear()
    println(obj.coinChangeRecursion(intArrayOf(1, 2, 6, 5), 30))
    println("6249 result:")
    obj.hashMap.clear()
    println(obj.coinChangeRecursion(intArrayOf(186, 419, 83, 408), 6249))

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
        obj.hashMap.clear()
        println(obj.coinChangeRecursion(coin, amount))
    }
}