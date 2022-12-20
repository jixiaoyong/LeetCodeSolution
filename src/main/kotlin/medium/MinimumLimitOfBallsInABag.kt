package medium

/**
 * @author : jixiaoyong
 * @description ： 1760. 袋子里最少数目的球
 *
 * 给你一个整数数组nums，其中nums[i]表示第i个袋子里球的数目。同时给你一个整数maxOperations。
 *
 * 你可以进行如下操作至多maxOperations次：
 *
 * 选择任意一个袋子，并将袋子里的球分到2 个新的袋子中，每个袋子里都有 正整数个球。
 * 比方说，一个袋子里有5个球，你可以把它们分到两个新袋子里，分别有 1个和 4个球，或者分别有 2个和 3个球。
 * 你的开销是单个袋子里球数目的 最大值，你想要 最小化开销。
 *
 * 请你返回进行上述操作后的最小开销。
 *
 * 提示：
 *
 * 1 <= nums.length <= 10^5
 * 1 <= maxOperations, nums[i] <= 10^9
 *
 * https://leetcode.cn/problems/minimum-limit-of-balls-in-a-bag/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/20/2022
 */
class MinimumLimitOfBallsInABag {

    /**
     * https://leetcode.cn/problems/minimum-limit-of-balls-in-a-bag/solution/dai-zi-li-zui-shao-shu-mu-de-qiu-by-leet-boay/
     * 思路：
     * 每次找到最大max和最小值max，那么如果将最大值max分割为：mid([max - min]),则最大的开销就是这个分割之后的值mid[i]
     * 对于这个mid[i]来说：对于每个数字nums[i]来说（mid取（max - min）/ 2 较大的部分，比如mid(7)=4）：
     * 1） nums[i] <= mid ，则不用分割
     * 2） 否则，需要将其分割为mid和nums[i]-mid两部分，消耗1个操作，然后再判断nums[i]-mid与mid关系……
     *      如此反复，可得总共需要(nums[i]-1)/mid次操作
     * 每次分割完毕，整个数组所需要的分割次数为nums[i]/mid的总和P，
     * 如果P <= maxOperations，则降低max，否则增大min
     */
    fun minimumSize(nums: IntArray, maxOperations: Int): Int {
        var result = 0
        var min = 1
        var max = nums.max() ?: Int.MAX_VALUE

        while (min <= max) {
            val mid = (max + min) / 2
            var operations = 0
            for (num in nums) {
                // 这里的(num-1)/mid规避了num==mid的情况（无需拆分）
                operations += (num - 1) / mid
            }

            if (operations <= maxOperations) {
                result = mid
                max = mid - 1
            } else {
                min = mid + 1
            }
        }

        return result
    }
}

fun main() {
    val obj = MinimumLimitOfBallsInABag()
    val cases = mutableListOf(Pair(intArrayOf(9), 2), Pair(intArrayOf(2, 4, 8, 2), 4), Pair(intArrayOf(7, 17), 2))

    cases.forEach {
        val result = obj.minimumSize(it.first, it.second)
        println("$result  ${it.second} ${it.first.joinToString()}")
    }
}