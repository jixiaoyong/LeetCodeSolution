package medium


/**
 * @author : jixiaoyong
 * @description ：790. 多米诺和托米诺平铺
 * 有两种形状的瓷砖：一种是2 x 1 的多米诺形，另一种是形如"L" 的托米诺形。两种形状都可以旋转。
 * 多米诺  □□   托米诺    □□
 *                      □
 *
 * 给定整数 n ，返回可以平铺2 x n 的面板的方法的数量。返回对10^9+ 7取模的值。
 * 平铺指的是每个正方形都必须有瓷砖覆盖。两个平铺不同，当且仅当面板上有四个方向上的相邻单元中的两个，使得恰好有一个平铺有一个瓷砖占据两个正方形。
 *
 * 提示：
 *
 * 1 <= n <= 1000
 * https://leetcode.cn/problems/domino-and-tromino-tiling/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/12/2022
 */
class DominoAndTrominoTiling {


    /**
     * DP
     * 132 ms	32 MB
     * 思路：
     * 参考自 https://blog.csdn.net/magicbean2/article/details/79744561
     * 将铺设的情况分为2种：
     * 1. f(n)为铺设到第2*n面板时的方法数量
     * 2. f2(n)表示，铺设到2*n+1块面板时的方法数量，此时这个多出来的正方形在上面
     *
     * 那么对于f(n)来说，他有如下情况：
     * 1) f(n-1) + d，之前的f(n-1)加上一个竖着的domino
     * 2) f(n-2) + d，之前的f(n-2)加上两个横着的domino组成的正方形,两个竖着的domino包含在情况1）中了，所以不用加
     * 3) f(n-3) + tt/tt,之前的f(n-3)加上两个tromino组成的2*3长方形，tt有两种组成方法，所以这里加2次
     * 4) f2(n-3) + td/dt，之前的f2(n-3)比2*n少了5块面板，需要t和d各一块，共有2种组装方法：td和dt
     *
     * 对于f2(n)来说，他有如下情况：
     * 1） f2(n-1) + d，在之前的f2(n-1)的基础上，添加一块竖着的domino
     * 2） f(n-1) + t，在之前的f(n-1)的基础上，添加一块tromino
     *
     * 所以 f(n) = f(n-1) + f(n-2) + f(n-3） + f(n-3) + f2(n-3) + f2(n-3)
     * f2(n) = f2(n-1) + f(n-1)
     *
     * 上述等式中，将f2(n)代入f(n)，可以推导出
     * f2(n-2) = f2(n-3) + f(n-3)
     * f(n) = f(n-1) + f(n-2) + f(n-3） + f(n-3) + f2(n-3) + f2(n-3)
     *      = f(n-1) + f(n-2) + 2*f(n-3) + 2 * (f2(n-2) - f(n-3))
     *      = f(n-1) + (f(n-2) + f2(n-2)) + f2(n-2)
     *      = f(n-1) + f2(n-1) + f2(n-2)
     *      = f2(n) + f2(n-2)
     *
     * 可以看出，计算时一般需要追溯到n-3的，所以我们使用长度为4的数组分别保存f(n)和f2(n)最近的几条结果
     * 又有当n<=4时，我们预先计算出对应的结果分别为
     * f(n) 1,2,5,11
     * f2(n) 1,2,4,9
     *
     * 时间复杂度O（N)
     * 空间复杂度O(1)
     */
    fun numTilings(n: Int): Int {
        val MOD = Math.pow(10.0, 9.0).toInt() + 7
        val array = intArrayOf(1, 2, 5, 11)
        // 对应f2
        val array2 = intArrayOf(1, 2, 4, 9)
        if (n <= 4) {
            return array[n - 1]
        }
        var cur = 4
        var result = 11
        while (cur < n) {
            cur++
            // array[3]即cur对应的方法数量 f(n) = f(n-1) + f(n-2) + f(n-3） + f(n-3) + f2(n-3) + f2(n-3)
            // 上述等式含义 = 移除一个d + 移除一个d+d + 移除一个t+t + 移除另外一种t+t + 移除一种t+ 移除另外一种t
            // 对于2*n+1即上面多出来一个方格的情况，当n>3时，f2(n) = f2(n-1) + f(n-1) ，想象去除了一个domino或者只去除一个多出来的方格
            val fn1 = result

            val f2n1 = array2[3]
            val f2n2 = array2[2]
            val f2n3 = array2[1]

            array2[3] = (f2n1 % MOD + fn1 % MOD) % MOD
            array2[2] = f2n1 % MOD
            array2[1] = f2n2 % MOD
            array2[0] = f2n3 % MOD

            result = (array2[3] + f2n2) % MOD
        }

        return result
    }


    /**
     * 	120 ms	32.7 MB
     * LeetCode官方的DP思路
     * 假设第i列前的所有正方形瓷砖都被填满，其之后的所有瓷砖都没有被覆盖，那么对于第i列的瓷砖来说，他有如下情况：
     * 1）完全没有被覆盖，状态为0，
     * 2）顶部1个正方形被覆盖，状态为1
     * 3）底部1个正方形被覆盖，状态为2
     * 4）完全被覆盖，状态为3，
     * 假设dp[i][s]为铺设到第i列时，各个状态s对应的铺设方法数量，那么对于dp[i-1][s]，他们的【状态转移】关系为：
     * dp[i][0] -> d[i-1][3]
     * dp[i][1] -> d[i-1][0]（加上一个t形成dp[i][1]的效果）或者d[i-1][2]（上面+横着的d）
     * dp[i][2] -> d[i-1][0]（加上一个d形成dp[i][2]的效果）或者d[i-1][1]（下面+横着的d）
     * dp[i][3] -> d[i-1][0]（加上两个横着的d）、d[i-1][1]（加上一个t）、d[i-1][2]（加上一个t）、d[i-1][3]（加上一个d）
     *
     * 所以题目要求的数目dp[i][3]=d[i-1][0] + d[i-1][1] + d[i-1][2] + d[i-1][3]
     *
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    fun numTilingsLeetCode(n: Int): Int {
        val MOD = 1000000007
        val dp = Array(n + 1) { IntArray(4) }
        dp[0][3] = 1
        for (i in 1..n) {
            dp[i][0] = dp[i - 1][3]
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % MOD
            dp[i][3] = (((dp[i - 1][0] + dp[i - 1][1]) % MOD + dp[i - 1][2]) % MOD + dp[i - 1][3]) % MOD
        }

        return dp[n][3]
    }

}

fun main() {
    val obj = DominoAndTrominoTiling()

    val cases = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 145, 123, 400, 100, 1000)

    cases.forEach {
        println("${obj.numTilings(it)}  --> $it")
        println("${obj.numTilingsLeetCode(it)}  --> $it")
    }
}