package medium

/**
 * @author : jixiaoyong
 * @description ： 1806. 还原排列的最少操作步数
 * 给你一个偶数 n ，已知存在一个长度为 n 的排列 perm ，其中 perm[i] == i（下标 从 0 开始 计数）。
 *
 * 一步操作中，你将创建一个新数组 arr ，对于每个 i ：
 *
 * 如果 i % 2 == 0 ，那么 arr[i] = perm[i / 2]
 * 如果 i % 2 == 1 ，那么 arr[i] = perm[n / 2 + (i - 1) / 2]
 * 然后将 arr 赋值给 perm 。
 *
 * 要想使 perm 回到排列初始值，至少需要执行多少步操作？返回最小的 非零 操作步数。
 *
 * 提示：
 *
 * 2 <= n <= 1000
 * n 是一个偶数
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/9/2023
 */
class MinimumNumberOfOperationsToReinitializeAPermutation {

    lateinit var arr: IntArray

    /**
     * 按照题目要求进行模拟
     * 时间复杂度：O(N^2)
     * 空间复杂度：O(N)
     */
    fun reinitializePermutation(n: Int): Int {
        var step = 0
        var isSame = false
        arr = IntArray(n)

        var perm = IntArray(n) { i ->
            i
        }

        while (!isSame) {
            isSame = genArr(perm)
            perm = arr.copyOf()
            step++
        }
        return step
    }

    private fun genArr(perm: IntArray): Boolean {
        val n = perm.size
        var isSame = true
        for (i in 0 until n) {
            val num = if (i % 2 == 0) {
                perm[i / 2]
            } else {
                perm[n / 2 + (i - 1) / 2]
            }
            isSame = isSame && num == i
            arr[i] = num
        }
        return isSame
    }

    /**
     * LeetCode官方解法
     * https://leetcode.cn/problems/minimum-number-of-operations-to-reinitialize-a-permutation/solution/huan-yuan-pai-lie-de-zui-shao-cao-zuo-bu-d9cn/
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     */
    fun reinitializePermutationLeetcode(n: Int): Int {
        if (n == 2) {
            return 1
        }
        var step = 1
        var pow2 = 2
        while (pow2 != 1) {
            step++
            pow2 = pow2 * 2 % (n - 1)
        }
        return step
    }
}

fun main() {
    val obj = MinimumNumberOfOperationsToReinitializeAPermutation()
    val cases = listOf(2, 4, 6, 8, 10, 12, 16, 18, 22, 28, 30, 100, 500, 1000)
    for (case in cases) {
        println("${obj.reinitializePermutation(case)}   ---> $case")
    }
}