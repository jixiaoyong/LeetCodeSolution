package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1753. 移除石子的最大得分
 *
 * 你正在玩一个单人游戏，面前放置着大小分别为 a、b 和 c 的 三堆 石子。
 *
 * 每回合你都要从两个 不同的非空堆 中取出一颗石子，并在得分上加 1 分。当存在 两个或更多 的空堆时，游戏停止。
 *
 * 给你三个整数 a 、b 和 c ，返回可以得到的 最大分数 。
 *
 * 提示：
 *
 * 1 <= a, b, c <= 10^5
 *
 * https://leetcode.cn/problems/maximum-score-from-removing-stones/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/21/2022
 */
class MaximumScoreFromRemovingStones {

    /**
     * 递归，会Stack Overflow
     */
    fun maximumScore(a: Int, b: Int, c: Int): Int {
        if (a + b == 0 || a + c == 0 || b + c == 0) {
            return 0
        }

        val arr = intArrayOf(a, b, c)
        arr.sort()
        val min = arr[0]
        val mid = arr[1]
        val max = arr[2]

        var score = mid - min
        if (score == 0) {
            score = 1
        }
        return score + maximumScore(min, mid - score, max - score)
    }

    /**
     * 递归转循环
     * 思路是：每次先减最大的两个数字（max，mid）直到等于最小值（min），然后这个过程得分为score=mid-min
     * 如果mid==min，则记得分score为1，给max和mid分别减一
     * 然后将上述的mid，mid-score，max-score分别作为接下来的三个数字继续进行计算，并累加得分
     * 直到三个数中两个及以上数字为0，返回总分
     *
     * 时间复杂度O(N) ，N为a，b，c中最大数字
     * 空间复杂度O(1)
     */
    fun maximumScoreLoop(a: Int, b: Int, c: Int): Int {
        var scores = 0
        val arr = intArrayOf(a, b, c)

        while (true) {
            if (arr[0] + arr[1] == 0 || arr[0] + arr[2] == 0 || arr[1] + arr[2] == 0) {
                break
            }
            arr.sort()
            val min = arr[0]
            val mid = arr[1]
            val max = arr[2]

            var score = mid - min
            if (score == 0) {
                score = 1
            }
            scores += score

            arr[0] = min
            arr[1] = mid - score
            arr[2] = max - score
        }

        return scores
    }

    /**
     * https://leetcode.cn/problems/maximum-score-from-removing-stones/solution/yi-chu-shi-zi-de-zui-da-de-fen-by-leetco-e5wm/
     * Leetcode官方题解
     * 时间和空间复杂度为O(1)
     * 思路是：
     * 先假设a<b<c，三者之和为sum
     * 1) a+b <= c时，分别用a和b与c一起减，则最终分数为a+b
     * 2) a+b > c时，先将a和c一起减k1，再将b和c一起减k2，直到c为0，再将a和b一起减到某一个为0
     *              在这其中，k1+k2 == c，而“a和b一起减到某一个为0”的分数为((a-k1)+(b-k2))/2
     *              那么，此时这个情况下总分数为：k1 + k2 + ((a-k1)+(b-k2))/2 == （a+b+c）/2
     * 又因为，a+b <= c时，sum/2一定大于等于a+b，而a+b > c时，sum/2一定小于a+b
     * 所以sum/2 大于等于a+b时，是第一种情况，反之则是第二种情况
     * 故而，取二者中最小值即可
     */
    fun maximumScoreLeetCode(a: Int, b: Int, c: Int): Int {
        val sum = a + b + c
        val maxVal = Math.max(Math.max(a, b), c)
        return Math.min(sum - maxVal, sum / 2)
    }
}

fun main() {
    val obj = MaximumScoreFromRemovingStones()
    val random = Random(System.currentTimeMillis())

    val cases = mutableListOf(Triple(2, 4, 6), Triple(4, 4, 6), Triple(1, 8, 8), Triple(15505, 15505, 78664))

    for (i in 0 until 5) {
        cases.add(Triple(random.nextInt(1, 100000), random.nextInt(1, 100000), random.nextInt(1, 100000)))
    }

    cases.forEach {
        println("${obj.maximumScoreLoop(it.first, it.second, it.third)}   ---${it}")
        println("${obj.maximumScoreLeetCode(it.first, it.second, it.third)}   ---${it}  maximumScoreLeetCode")
    }
}