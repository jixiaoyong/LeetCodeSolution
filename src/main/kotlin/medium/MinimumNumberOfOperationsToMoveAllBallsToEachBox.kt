package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1769. 移动所有球到每个盒子所需的最小操作数
 * 有 n 个盒子。
 * 给你一个长度为 n 的二进制字符串 boxes ，其中 boxes[i] 的值为 '0' 表示第 i 个盒子是 空 的，而 boxes[i] 的值为 '1' 表示盒子里有 一个 小球。
 *
 * 在一步操作中，你可以将 一个 小球从某个盒子移动到一个与之相邻的盒子中。第 i 个盒子和第 j 个盒子相邻需满足 abs(i - j) == 1 。
 * 注意，操作执行后，某些盒子中可能会存在不止一个小球。
 *
 * 返回一个长度为 n 的数组 answer ，其中 answer[i] 是将所有小球移动到第 i 个盒子所需的 最小 操作数。
 *
 * 每个 answer[i] 都需要根据盒子的 初始状态 进行计算。
 *
 * 提示：
 *
 * n == boxes.length
 * 1 <= n <= 2000
 * boxes[i] 为 '0' 或 '1'
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/2/2022
 */
class MinimumNumberOfOperationsToMoveAllBallsToEachBox {

    /**
     * 思路，遍历两次，每次计算将[boxes]的元素从[j]到[i]处的总和并记录
     * 时间复杂度O(N^2)
     * 空间复杂度O(1)
     * 712 ms	46.2 MB
     */
    fun minOperations(boxes: String): IntArray {
        val n = boxes.length
        val totalSteps = IntArray(n)

        for (i in 0 until n) {
            var curTotalSteps = 0
            for (j in 0 until n) {
                val box = boxes[j]
                if (box == '1') {
                    curTotalSteps += Math.abs(j - i)
                }
            }
            totalSteps[i] = curTotalSteps
        }

        return totalSteps
    }


    /**
     * Leetcode解法2
     * 思路是，先遍历一次找到将所有1移动到boxes的0位置处所需步数
     * 然后，如果是将所有1移动到i+1处，那么i及左侧的1需要+1，而i右侧的1需要-1，所以就是在上次的步数基础上+left-right
     * https://leetcode.cn/problems/minimum-number-of-operations-to-move-all-balls-to-each-box/solution/yi-dong-suo-you-qiu-dao-mei-ge-he-zi-suo-y50e/
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     * 236 ms	36.9 MB
     */
    fun minOperationsOptimize(boxes: String): IntArray {
        val n = boxes.length
        val totalSteps = IntArray(n)
        val preSteps = IntArray(n)
        var left = if (boxes[0] == '1') 1 else 0
        var right = 0

        var curTotalSteps = 0
        for (i in 1 until n) {
            val step = if (boxes[i] == '1') {
                right++
                i
            } else {
                0
            }
            curTotalSteps += step
            preSteps[i] = step
        }
        totalSteps[0] = curTotalSteps

        for (i in 1 until n) {
            curTotalSteps += left - right
            if (boxes[i] == '1') {
                left++
                right--
            }
            totalSteps[i] = curTotalSteps
        }

        return totalSteps
    }
}

fun main() {
    val obj = MinimumNumberOfOperationsToMoveAllBallsToEachBox()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf("110", "001011", "000000", "1", "0", "10000000000000001")

    val largestString = CharArray(2000)
    for (i in 0 until largestString.size) {
        largestString[i] = if (random.nextBoolean()) '1' else '0'
    }
    cases.add(String(largestString))

    cases.forEach {
        println("${obj.minOperations(it).joinToString()}\n${obj.minOperationsOptimize(it).joinToString()}   ${it}")
    }
}