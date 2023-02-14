package medium

/**
 * @author : jixiaoyong
 * @description ：1124. 表现良好的最长时间段
 * 给你一份工作时间表hours，上面记录着某一位员工每天的工作小时数。
 *
 * 我们认为当员工一天中的工作小时数大于8 小时的时候，那么这一天就是「劳累的一天」。
 *
 * 所谓「表现良好的时间段」，意味在这段时间内，「劳累的天数」是严格 大于「不劳累的天数」。
 *
 * 请你返回「表现良好时间段」的最大长度。
 *
 * https://leetcode.cn/problems/longest-well-performing-interval/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/2/14
 */
class LongestWellPerformingInterval {

    /**
     * 思路：
     * 「表现良好时间段」的定义是在这个时间段内工作超过8小时的天数总和超过不足或者刚好8小时的天数之和
     * 那么可以将天数分为大于8和小于等于8两种，分别令其为1、-1
     * 求某段时间内的和，可以使用前缀和快捷求解：preSum[j]-perSum[i]表示在j到i（不包含i，且j大于i）之间数字之和
     */
    fun longestWPI(hours: IntArray): Int {
        val wpiHours = hours.map { hour -> if (hour > 8) 1 else -1 }

        val preSumHours: Array<Int> = Array(wpiHours.size + 1) { 0 }
        for (index in 0 until wpiHours.size) {
            val hour = wpiHours[index]
            preSumHours[index + 1] = hour + preSumHours[index]
        }

        var maxWpi = 0
        for (i in preSumHours.size - 1 downTo 1) {
            for (j in i - 1 downTo 0) {
                if (preSumHours[i] - preSumHours[j] > 0) {
                    // 这里求得的是hours[i]到hours[j+1]之间数字之和
                    maxWpi = Math.max(maxWpi, i - j)
                }
            }
        }

        return maxWpi
    }
}

fun main() {
    val obj = LongestWellPerformingInterval()
    val cases = arrayListOf(
        intArrayOf(9, 9, 6, 0, 6, 6, 9), intArrayOf(9, 9, 16, 0, 6, 2, 9), intArrayOf(9, 9, 6, 10, 6, 6, 9),
        intArrayOf(9, 9, 6, 0, 6, 4, 2), intArrayOf(6, 6, 6)
    )

    for (case in cases) {
        val res = obj.longestWPI(case)
        println("RES:${res}   --->${case.joinToString()}")
    }
}