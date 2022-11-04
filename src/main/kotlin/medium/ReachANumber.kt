package medium

/**
 * @author : jixiaoyong
 * @description ： 754. 到达终点数字
 * 在一根无限长的数轴上，你站在0的位置。终点在target的位置。
 *
 * 你可以做一些数量的移动 numMoves :
 *
 * 每次你可以选择向左或向右移动。
 * 第 i次移动（从 i == 1开始，到i == numMoves ），在选择的方向上走 i步。
 * 给定整数target ，返回 到达目标所需的 最小移动次数(即最小 numMoves )。
 *
 * 提示:
 *
 * -10^9 <= target <= 10^9
 * target != 0
 *
 * https://leetcode.cn/problems/reach-a-number/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/4/2022
 */
class ReachANumber {
    /**
     * 数学思路
     * 此题解题思路关键在于找到规律：
     * 1. 不断累加numMoves直到总数cur大于target，【注意在大于target之前一直累加，否则会出现有+有减而数字反复，无法到达target死循环】
     * 2. 判断cur-target的差值diff是偶数还是奇数
     *    判断是偶数的话，可以将原来累加的数字diff/2改为减去diff/2，这样累计的效果相当于cur-diff,故而总数等于target
     *    所以，如果diff是偶数的话，numMoves就是所求结果，如果不是的话就继续累加numMoves，直到diff为偶数：
     *      2.1 diff为偶数，返回numMoves
     *      2.2 diff为奇数：
     *          2.2.1 numMoves为偶数，那么numMoves+1为奇数，所以cur+(numMoves+1) - target为偶数，所以只需再多一步即可，返回numMoves+1
     *          2.2.2 numMoves为奇数，那么numMoves+1为偶数，numMoves+2为奇数，所以cur+(numMoves+1)+(numMoves+2)- target为偶数，所以
     *                返回numMoves+2
     *  时间复杂度：O(根号target)
     *  空间复杂度：O(1)。只使用常数空间。
     */
    fun reachNumber(target: Int): Int {
        var cur = 0
        var count = 0
        val target = Math.abs(target)

        while (cur < target) {
            count++
            cur += count
        }

        return if (cur == target) {
            count
        } else if ((cur - target) % 2 == 0) {
            // 将一个数字num变为负数，相当于减去从count中减去2*num
            // 情况①：所以，当cur-target为偶数时，只需要将步骤里面的（cur-target）/2的加变为减即可
            // 故此这里的步骤次数不变
            count
        } else if (count % 2 == 0) {
            // 情况②：如果cur-target为奇数，并且最后一个数字count为偶数
            // 此时，count+1为奇数，所以((cur  + count + 1) - target ) % 2= ((cur - target + 1) % 2也会成为偶数，满足情况①
            // 故而还需多走1步
            count + 1
        } else {
            // 情况③：如果cur-target为奇数，并且最后一个数字count为奇数
            // 此时，count+1为偶数，count+2位奇数，那么((cur  + count + 1 + count + 2) - target ) % 2= (cur - target + 1 ) % 2
            // 也变成了偶数，满足条件①，故而还需多走2步
            count + 2
        }

    }


}

fun main() {
    val obj = ReachANumber()
    val cases = intArrayOf(1, 2, 3, 4, -4, 5, 10, 11, 36, 1000, 100000000, -100000000)
    //1000000000

    cases.forEach {
        val result = obj.reachNumber(it)
        println("result $result ---> target ${it}\n")
    }
}