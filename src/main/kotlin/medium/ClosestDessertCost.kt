package medium

import kotlin.math.cos

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/12/4
 * description: 1774. 最接近目标价格的甜点成本
 *
 * 你打算做甜点，现在需要购买配料。目前共有 n 种冰激凌基料和 m 种配料可供选购。而制作甜点需要遵循以下几条规则：
 *
 * 必须选择 一种 冰激凌基料。
 * 可以添加 一种或多种 配料，也可以不添加任何配料。
 * 每种类型的配料 最多两份 。
 * 给你以下三个输入：
 *
 * baseCosts ，一个长度为 n 的整数数组，其中每个 baseCosts[i] 表示第 i 种冰激凌基料的价格。
 * toppingCosts，一个长度为 m 的整数数组，其中每个 toppingCosts[i] 表示 一份 第 i 种冰激凌配料的价格。
 * target ，一个整数，表示你制作甜点的目标价格。
 * 你希望自己做的甜点总成本尽可能接近目标价格 target 。
 *
 * 返回最接近 target 的甜点成本。如果有多种方案，返回成本相对较低 的一种。
 *
 * 提示：
 *
 * n == baseCosts.length
 * m == toppingCosts.length
 * 1 <= n, m <= 10
 * 1 <= baseCosts[i], toppingCosts[i] <= 10^4
 * 1 <= target <= 10^4
 *
 *
 * https://leetcode.cn/problems/closest-dessert-cost/
 */
class ClosestDessertCost {

    val allNums = mutableSetOf<Int>()

    /**
     * 1066 ms	61.2 MB
     * 思路：先遍历一次找到所有的[toppingCosts]可能组成的数字，然后依次遍历[baseCosts]，找到和其组合之后，与[target]差值最小的值
     * 时间复杂度：O(LogM+M*N),N是[baseCosts]长度，M是[toppingCosts]长度
     * 空间复杂度：O(M^3)
     */
    fun closestCost(baseCosts: IntArray, toppingCosts: IntArray, target: Int): Int {
        var minCost = Int.MAX_VALUE

        allNums.clear()
        rescure(toppingCosts, 0, 0)
        val storeOrderNums = allNums.toMutableList().sorted()

        baseCosts.forEach { i ->
            val curMin = findStep(target, i, storeOrderNums)
            val costed = Math.abs(curMin - target)
            if (Math.abs(minCost - target) > costed) {
                minCost = curMin
            } else if (Math.abs(minCost - target) == costed) {
                minCost = Math.min(curMin, minCost)
            }
        }

        return minCost
    }

    private fun rescure(toppingCosts: IntArray, index: Int, cost: Int) {
        if (index == toppingCosts.size) {
            allNums.add(cost)
            return
        }
        val curCost = toppingCosts[index]
        rescure(toppingCosts, index + 1, cost)
        rescure(toppingCosts, index + 1, cost + curCost)
        rescure(toppingCosts, index + 1, cost + curCost * 2)
    }


    /**
     * 找到使用[storeOrderNums]组合出的和target最接近的差值，可为正负
     */
    fun findStep(target: Int, costed: Int, storeOrderNums: List<Int>): Int {
        if (target <= 0) {
            return target
        }

        var cost = Int.MAX_VALUE
        var preCosted = Int.MAX_VALUE
        for (storeOrderNum in storeOrderNums) {
            val curCost = storeOrderNum + costed
            val curCosed = Math.abs(curCost - target)
            if (preCosted > curCosed) {
                cost = curCost
                preCosted = curCosed
            } else if (preCosted == curCosed) {
                cost = Math.min(cost, curCost)
            } else if (curCosed > preCosted && curCost > target) {
                break
            }
        }

        return cost
    }


    /**
     *     TODO 官方提解
     * https://leetcode.cn/problems/closest-dessert-cost/solution/zui-jie-jin-mu-biao-jie-ge-de-tian-dian-2ck06/
     */


}

fun main() {
    val obj = ClosestDessertCost()
    val cases = mutableListOf(
        Triple(intArrayOf(1, 7), intArrayOf(3, 4), 10),
        Triple(intArrayOf(1, 7), intArrayOf(3, 4), 1),
        Triple(intArrayOf(1, 7), intArrayOf(3, 4), 0),
        Triple(intArrayOf(2, 3), intArrayOf(4, 5, 100), 18),
        Triple(intArrayOf(3, 10), intArrayOf(2, 5), 9),
        Triple(intArrayOf(10, 3), intArrayOf(2, 5), 9),
        Triple(intArrayOf(10), intArrayOf(1), 1),
        Triple(
            intArrayOf(10, 3, 55, 10000, 19, 2333, 121, 334, 332),
            intArrayOf(2, 5, 948, 23, 342, 545, 342, 23),
            10000
        ),
    )

    cases.forEach {
        println(
            "${
                obj.closestCost(
                    it.first,
                    it.second,
                    it.third
                )
            } ------ ${it.third} / ${it.first.joinToString()} /  ${it.second.joinToString()} "
        )
    }
}