package medium

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/11/20
 * description: 799. 香槟塔
 * 我们把玻璃杯摆成金字塔的形状，其中第一层有 1 个玻璃杯， 第二层有 2 个，依次类推到第 100 层，每个玻璃杯 (250ml) 将盛有香槟。
 *
 * 从顶层的第一个玻璃杯开始倾倒一些香槟，当顶层的杯子满了，任何溢出的香槟都会立刻等流量的流向左右两侧的玻璃杯。
 * 当左右两边的杯子也满了，就会等流量的流向它们左右两边的杯子，依次类推。（当最底层的玻璃杯满了，香槟会流到地板上）
 *
 * 例如，在倾倒一杯香槟后，最顶层的玻璃杯满了。倾倒了两杯香槟后，第二层的两个玻璃杯各自盛放一半的香槟。
 * 在倒三杯香槟后，第二层的香槟满了 - 此时总共有三个满的玻璃杯。
 * 在倒第四杯后，第三层中间的玻璃杯盛放了一半的香槟，他两边的玻璃杯各自盛放了四分之一的香槟，如下图所示。
 *
 * 现在当倾倒了非负整数杯香槟后，返回第 i 行 j 个玻璃杯所盛放的香槟占玻璃杯容积的比例（ i 和 j 都从0开始）。
 *
 * 提示:
 *
 * 0 <= poured <= 109
 * 0 <= query_glass <= query_row < 100
 *
 * https://leetcode.cn/problems/champagne-tower/
 */
class ChampagneTower {

    /**
     * 思路：模拟倒香槟的过程，从最顶端开始计算，每次计算多出来的（>1的部分），将其分为1/2发给下一层相同序号和后一个序号的杯子
     * 使用DP记录这个过程中的每个杯子中的酒容量
     * 时间复杂度O(N^2) ，N是[query_row]，最坏情况下需要从第一个杯子开始计算到[query_row]行第[query_glass]个杯子
     * 空间复杂度O(N!)，需要O(N!)的空间保存杯子的容量，可以优化到O(1)
     * 280 ms	37.8 MB
     */
    fun champagneTower(poured: Int, query_row: Int, query_glass: Int): Double {
        val dp = Array(query_row + 2) { index -> DoubleArray(index + 1) }

        if (poured == 0) {
            return 0.0
        }

        if (query_row == 0) {
            return if (poured > 0) 1.0 else 0.0
        }

        dp[0][0] = poured.toDouble()
        for (i in 0 .. query_row ) {
            for (j in 0 .. i) {
                val cur = dp[i][j]-1
                if (cur > 0) {
                    dp[i + 1][j] += cur / 2.0
                    dp[i + 1][j + 1] += cur/ 2.0
                }
            }
        }

        val value = dp[query_row][query_glass]

        return if (value >= 1) 1.0 else value
    }
}

fun main() {
    val obj = ChampagneTower()
    val cases = listOf(
        Triple(1, 1, 1), Triple(2, 1, 1),
        Triple(100000009, 33, 17)
    )

    cases.forEach {
        println("${obj.champagneTower(it.first, it.second, it.third)}  --> ${it}")
    }
}