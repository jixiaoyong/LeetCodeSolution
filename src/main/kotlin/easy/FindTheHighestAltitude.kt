package easy

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/11/19
 * description: 1732. 找到最高海拔
 * 有一个自行车手打算进行一场公路骑行，这条路线总共由n + 1个不同海拔的点组成。自行车手从海拔为 0的点0开始骑行。
 *
 * 给你一个长度为 n的整数数组gain，其中 gain[i]是点 i和点 i + 1的 净海拔高度差（0 <= i < n）。请你返回 最高点的海拔 。
 *
 * 提示：
 *
 * n == gain.length
 * 1 <= n <= 100
 * -100 <= gain[i] <= 100
 */
class FindTheHighestAltitude {

    /**
     * 思路：第一步为0，其余为正则海拔向上，为负则海拔向下
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    fun largestAltitude(gain: IntArray): Int {
        var highest = 0
        var cur = 0
        for (i in gain) {
            cur += i
            highest = Math.max(highest, cur)
        }

        return highest
    }
}

fun main() {
    val obj = FindTheHighestAltitude()
    val cases = mutableListOf(
        intArrayOf(-5, 1, 5, 0, -7),
        intArrayOf(-4, -3, -2, -1, 4, 3, 2),
        intArrayOf(1),
        intArrayOf(-1),
        intArrayOf(-1, 3),
        intArrayOf(0)
    )

    cases.forEach {
        println("${obj.largestAltitude(it)}   ---> ${it.joinToString()}")
    }
}