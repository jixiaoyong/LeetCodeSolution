package medium

/**
 * @author : jixiaoyong
 * @description ： 1802. 有界数组中指定下标处的最大值
 * 给你三个正整数 n、index 和 maxSum 。你需要构造一个同时满足下述所有条件的数组 nums（下标 从 0 开始 计数）：
 *
 * nums.length == n
 * nums[i] 是 正整数 ，其中 0 <= i < n
 * abs(nums[i] - nums[i+1]) <= 1 ，其中 0 <= i < n-1
 * nums 中所有元素之和不超过 maxSum
 * nums[index] 的值被 最大化
 * 返回你所构造的数组中的 nums[index] 。
 *
 * 注意：abs(x) 等于 x 的前提是 x >= 0 ；否则，abs(x) 等于 -x 。
 * 提示：
 *
 * 1 <= n <= maxSum <= 10^9
 * 0 <= index < n
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/4/2023
 */
class MaximumValueAtAGivenIndexInABoundedArray {

    /**
     * 思路：按照二分法从maxSum和0之中查找符合条件的值——即指定index处的值为maxSum和0之间某值时，数组的总和小于等于maxSum
     * 其中，按照已知数学知识，1+2+3+……+n等于n*(n+1)/2，所以可知推知，当index出的值为n时，数组的总和为：
     * sum = n*(n+1)/2 - nums[0]! + (n-1)*n/2 - nums[n-1]!
     * （按照题目要求，我们假设数组nums中每个数最小为1，然后以numx[index]处为最大值，然后依次向两侧递减1，以满足题设）
     * 计算时需要注意sum可能超过int范围，所以需要使用long、double等保存计算结果
     *
     * 时间复杂度：O(lg(maxSum))，二分搜索上下界的差为 maxSum。
     * 空间复杂度：O(1)
     */
    fun maxValue(n: Int, index: Int, maxSum: Int): Int {
        // 这里先给每个数组填充数字1，以满足题设数组中都为正整数
        val maxSum = (maxSum - n).toLong()
        var left = 0
        var right = maxSum
        var res = 0
        val leftCount = n - 1 - index

        while (right >= left) {
            val mid = ((right + left) / 2).toLong()
            val leftNumber = (if (mid - 1 <= index) 0 else mid - index - 1).toLong()

            val rightNumber = (if (mid - 1 <= leftCount) 0 else mid - leftCount - 1).toLong()
            // 计算此时的和，需要考虑和超出int范围的情况 sum = sum（0~index） + sum（index~n）
            val sum = mid * (mid + 1) / 2 - leftNumber * (leftNumber + 1) / 2 +
                    (mid - 1) * mid / 2 - rightNumber * (rightNumber + 1) / 2
            if (sum == maxSum) {
                res = mid.toInt()
                break
            } else if (sum < maxSum) {
                res = mid.toInt()
                left = mid.toInt() + 1
            } else {
                right = mid - 1
            }
        }

        // 加上之前给数组每个数字填充1的那一个数字
        return res + 1
    }

    fun maxValueLeetCode(n: Int, index: Int, maxSum: Int): Int {
        var left = 1
        var right = maxSum
        while (left < right) {
            val mid = (left + right + 1) / 2
            if (valid(mid, n, index, maxSum)) {
                left = mid
            } else {
                right = mid - 1
            }
        }
        return left
    }

    fun valid(mid: Int, n: Int, index: Int, maxSum: Int): Boolean {
        val right = n - index - 1
        return mid + cal(mid, index) + cal(mid, right) <= maxSum
    }

    fun cal(big: Int, length: Int): Long {
        return if (length + 1 < big) {
            val small = big - length
            (big - 1 + small).toLong() * length / 2
        } else {
            val ones = length - (big - 1)
            big.toLong() * (big - 1) / 2 + ones
        }
    }
}

fun main() {
    val obj = MaximumValueAtAGivenIndexInABoundedArray()
    val cases = mutableListOf(
        Triple(20896, 1, 1000000000), Triple(2220896, 31, 100000000),
        Triple(6, 1, 23),
        Triple(3, 2, 10),
        Triple(6, 2, 10),
        Triple(4, 2, 6), Triple(6, 1, 10),
        Triple(3, 4, 10),
        Triple(23, 14, 1230),
    )

    cases.forEach {
        val res = obj.maxValue(it.first, it.second, it.third)
        val res2 = obj.maxValueLeetCode(it.first, it.second, it.third)
        println("$res ${if (res == res2) "" else "/ $res2 "}---> $it")
    }
}