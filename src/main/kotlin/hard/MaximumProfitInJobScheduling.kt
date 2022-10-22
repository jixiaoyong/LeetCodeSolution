package hard

/**
 * @author : jixiaoyong
 * @description ： 1235. 规划兼职工作
 * 你打算利用空闲时间来做兼职工作赚些零花钱。
 * 这里有 n 份兼职工作，每份工作预计从 startTime[i] 开始到 endTime[i] 结束，报酬为 profit[i]。
 * 给你一份兼职工作表，包含开始时间 startTime，结束时间 endTime 和预计报酬 profit 三个数组，请你计算并返回可以获得的最大报酬。
 * 注意，时间上出现重叠的 2 份工作不能同时进行。
 * 如果你选择的工作在时间 X 结束，那么你可以立刻进行在时间 X 开始的下一份工作。
 *
 * 提示：
 *
 * 1 <= startTime.length == endTime.length ==profit.length<= 5 * 10^4
 * 1 <=startTime[i] <endTime[i] <= 10^9
 * 1 <=profit[i] <= 10^4
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/22/2022
 */
class MaximumProfitInJobScheduling {

    /**
     * 1747 ms	118 MB
     * 思路：
     * 动态规划，f(i) = max(f(i+1) + i),
     * 即以当前start开始的最大收益等于所有以此时间开始的所有兼职收益+其后续开始时间的兼职收益中的最大值
     * 而区分当前开始时间的下一个时间则需满足开始时间晚于当前开始时间的结束时间\
     * 【需对startTime排序，才能保证上述等式成立】
     * 时间复杂度为O（(2+N)*N^2）
     * 空间复杂度O(N)
     */
    fun jobScheduling(startTime: IntArray, endTime: IntArray, profit: IntArray): Int {

        // key: startTime , value: pair( index, list of endTime-profit)
        // 一个startTime可能对应多个endTime及profit
        val timeHashMap = hashMapOf<Int, MutableList<Pair<Int, Pair<Int, Int>>>>()
        val timeDurationList = MutableList(startTime.size) { index ->
            // key：startTime value:endTime-profit
            Pair(startTime[index], Pair(endTime[index], profit[index]))
        }

        timeDurationList.sortBy { it.first }

        timeDurationList.forEachIndexed { index, value ->
            // timeHashMap记录开始时间、在timeDurationList排序之后的index，以及对应的兼职结束时间和收益
            val list = timeHashMap.getOrDefault(value.first, mutableListOf())
            list.add(Pair(index, value.second))
            timeHashMap.put(value.first, list)
        }

        // key：开始时间，value：此开始时间所能获取的最大收益
        val profitHashMap = hashMapOf<Int, Int>()

        val startTimeSorted = timeHashMap.keys.sorted()
        val lastKey = startTimeSorted[startTimeSorted.size - 1]
        profitHashMap[lastKey] = findMax(timeHashMap.get(lastKey)!!.map { it.second.second })
        var result = profitHashMap[lastKey]!!

        for (x in startTimeSorted.size - 2 downTo 0) {
            val key = startTimeSorted[x]
            val startTimes = timeHashMap.get(key)!!
            val profits = startTimes.map {
                // 查找此开始时间下一阶段的时间，并计算最大profit
                val nextTimesProfit = findNextAvailableTime(timeDurationList, it.first).map {
                    // it -> key：startTime value:endTime-profit
                    profitHashMap[it.first]!!
                }.toList()
                // 当前收益 + 下一个时间开始的最大收益
                it.second.second + findMax(nextTimesProfit)
            }.toList()
            // 保存当前开始时间段的最大收益
            val maxProfit = findMax(profits)
            result = Math.max(result, maxProfit)
            profitHashMap.put(key, maxProfit)
        }

        return result
    }

    private fun findMax(nums: List<Int>): Int {
        var result = 0
        nums.forEach {
            result = Math.max(it, result)
        }
        return result
    }

    /**
     * 查找当前开始时间可用的下一段开始时间
     */
    private fun findNextAvailableTime(
        timeDurationList: List<Pair<Int, Pair<Int, Int>>>,
        currentIndex: Int
    ): List<Pair<Int, Pair<Int, Int>>> {
        val minStartTime = timeDurationList.get(currentIndex).second.first
        var minEndTime: Int? = null
        val result = mutableListOf<Pair<Int, Pair<Int, Int>>>()

        for (x in currentIndex + 1 until timeDurationList.size) {
            val currentStartTime = timeDurationList.get(x).first
            val currentEndTime = timeDurationList.get(x).second.first

            // 必须在指定开始时间之后
            if (currentStartTime < minStartTime) {
                continue
            }
            minEndTime = if (null == minEndTime) {
                currentEndTime
            } else {
                Math.min(minEndTime, currentEndTime)
            }

            // 此阶段的开始时间必须在此阶段所有兼职时间结束之前
            if (currentStartTime >= minEndTime) {
                break
            }

            result.add(timeDurationList.get(x))
        }

        return result
    }


    /**
     * 897 ms	76.3 MB
     * LeetCode官方解法
     * 动态规划+ 二分查找
     * https://leetcode.cn/problems/maximum-profit-in-job-scheduling/solution/gui-hua-jian-zhi-gong-zuo-by-leetcode-so-gu0e/
     * endTime从小到大排序，dp[i]表示前i份工作可以获取的最大收益
     * i=0时 dp[0] = 0
     * i>0时，dp[i] = max(dp[i-1], dp[k]+profit[i-1])，其中k表示满足结束时间小于等于第i份工作开始时间的【兼职工作数量】
     * 即i>0时，dp[i]的值是选取和不选去当前兼职的收益中最大值，第i份工作开始时间取值是startTime[i-1]
     * （profit[i-1]是第i份兼职收入,k使用二分法查找）
     *
     * 时间复杂度：O(nlogn)，其中 n是兼职工作的数量。排序需要O(nlogn)，遍历 + 二分查找需要O(nlogn)，因此总时间复杂度为O(nlogn)。
     * 空间复杂度：O(n)。需要 O(n) 的空间来保存dp。
     */
    fun jobSchedulingDp(startTime: IntArray, endTime: IntArray, profit: IntArray): Int {
        val len = startTime.size
        val dp = IntArray(len + 1)

        // startTime,endTime,profit
        val jobs = mutableListOf<IntArray>()
        for (x in 0 until len) {
            jobs.add(intArrayOf(startTime[x], endTime[x], profit[x]))
        }
        jobs.sortBy { it[1] }

        for (x in 1..len) {
            // 注意，这里的x从1开始计数，而jobs又是从0开始计数，所以这里的jobs[x-1]意义是从jobs中获取第i份工作对应的数据
            // jobs[x - 1][0] 第i份工作的开始时间,jobs[x - 1][2] 第i份工作的收益
            // dp[i-1]则是第i-1份工作对应的最大收益
            val k = findK(jobs, x - 1, jobs[x - 1][0])
            dp[x] = Math.max(dp[x - 1], dp[k] + jobs[x - 1][2])
        }

        return dp[len]
    }

    /**
     * 从[jobs]中下标为0~[rightIndex]的item中，找到小于当前第i份工作开始时间[target]的item个数
     */
    private fun findK(jobs: MutableList<IntArray>, rightIndex: Int, target: Int): Int {
        var left = 0
        var rightIndex = rightIndex
        while (left < rightIndex) {
            val mid = (rightIndex + left) / 2
            if (target >= jobs[mid][1]) {
                left = mid + 1
            } else {
                rightIndex = mid
            }
        }

        return left
    }

}

fun main() {
    val obj = MaximumProfitInJobScheduling()

    val sysTimeMs = System.currentTimeMillis()

    val dataList = mutableListOf(
        // start end profit
        Triple(intArrayOf(1, 1, 1, 3, 4, 5), intArrayOf(2, 3, 4, 5, 6, 7), intArrayOf(60, 40, 80, 50, 35, 20)),//130
        Triple(intArrayOf(1, 2, 3, 3), intArrayOf(3, 4, 5, 6), intArrayOf(50, 10, 40, 70)),// 120
        Triple(intArrayOf(1, 2, 3, 4, 6), intArrayOf(3, 5, 10, 6, 9), intArrayOf(20, 20, 100, 70, 60)),// 150
        Triple(intArrayOf(1, 1, 1), intArrayOf(2, 3, 4), intArrayOf(5, 6, 4)),// 6
        Triple(intArrayOf(1, 3, 4, 6, 8), intArrayOf(3, 9, 5, 7, 10), intArrayOf(20, 165, 70, 60, 30)),// 6
        Triple(
            intArrayOf(6, 15, 7, 11, 1, 3, 16, 2),
            intArrayOf(19, 18, 19, 16, 10, 8, 19, 8),
            intArrayOf(2, 9, 1, 19, 5, 7, 3, 19)
        ),// 41
    )


    dataList.forEach {
        val result = obj.jobScheduling(it.first, it.second, it.third)
        val result2 = obj.jobSchedulingDp(it.first, it.second, it.third)
        println("\n$result VS $result2--->start ${it.first.joinToString()}, end ${it.second.joinToString()}, profit ${it.third.joinToString()}")
    }

    // 52
    println("sysTimeMs:${System.currentTimeMillis() - sysTimeMs} ms")
}