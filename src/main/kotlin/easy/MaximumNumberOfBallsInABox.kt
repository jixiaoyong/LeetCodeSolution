package easy

/**
 * @author : jixiaoyong
 * @description ： 1742. 盒子中小球的最大数量
 * 你在一家生产小球的玩具厂工作，有 n 个小球，编号从 lowLimit 开始，到 highLimit 结束
 * （包括 lowLimit 和highLimit ，即n == highLimit - lowLimit + 1）。另有无限数量的盒子，编号从 1 到 infinity 。
 *
 * 你的工作是将每个小球放入盒子中，其中盒子的编号应当等于小球编号上每位数字的和。
 * 例如，编号 321 的小球应当放入编号 3 + 2 + 1 = 6 的盒子，而编号 10 的小球应当放入编号 1 + 0 = 1 的盒子。
 *
 * 给你两个整数 lowLimit 和 highLimit ，返回放有最多小球的盒子中的小球数量。
 * 如果有多个盒子都满足放有最多小球，只需返回其中任一盒子的小球数量。
 *
 * 提示：
 *
 * 1 <= lowLimit <= highLimit <= 10^5
 *
 * https://leetcode.cn/problems/maximum-number-of-balls-in-a-box/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/23/2022
 */
class MaximumNumberOfBallsInABox {

    /**
     * 使用hash表，保存每个数字每个位数上面的数字之和的个数
     * 时间复杂度：O(n*log highLimit),n是数字区间的长度，也即highLimit-lowLimit
     * 空间复杂度：O(log highLimit)
     */
    fun countBalls(lowLimit: Int, highLimit: Int): Int {
        val hashMap = hashMapOf<Int, Int>()
        var max = 0
        for (i in lowLimit..highLimit) {
            var num = i
            var count = 0
            while (num > 0) {
                count += num % 10
                num /= 10
            }
            val ballsCount = hashMap.getOrDefault(count, 0) + 1
            max = Math.max(max, ballsCount)
            hashMap.put(count, ballsCount)
        }

        return max
    }
}

fun main() {
    val obj = MaximumNumberOfBallsInABox()
    val cases = listOf(Pair(1, 10), Pair(5, 15), Pair(19, 28), Pair(1, 100000))

    cases.forEach {
        val result = obj.countBalls(it.first, it.second)
        println("$result   -> ${it}")
    }
}