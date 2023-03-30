package hard

/**
 * @author : jixiaoyong
 * @description ：1092. 最短公共超序列
 *
 * 给出两个字符串str1 和str2，返回同时以str1和str2作为子序列的最短字符串。如果答案不止一个，则可以返回满足条件的任意一个答案。
 *
 * （如果从字符串 T 中删除一些字符（也可能不删除，并且选出的这些字符可以位于 T 中的任意位置），可以得到字符串 S，那么S 就是T 的子序列）
 *
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/3/28
 */
class ShortestCommonSupersequence {

    /**
     * 这是一个使用动态规划求解两个字符串最短公共超序列的方法。
     * 通过定义dp数组来记录最短公共子序列的长度，同时记录dpRes数组来记录当前的dp是从哪个dp转移而来。
     * dpChar则为了回溯的时候更加方便
     * 最后使用StringBuilder构造最短公共超序列。
     */
    fun shortestCommonSupersequence(str1: String, str2: String): String {

        val len1 = str1.length
        val len2 = str2.length

        // dp的含义是：dp[i+1][j+1]表示str1[0..i]和str2[0..j]的最短公共子序列的长度，这样可以得出结果的长度
        val dp = Array<Array<Int>>(len1 + 1) { Array(len2 + 1) { 0 } }
        // 记录当前的dp[i][j]是从那个dp来的，方便后面构建结果时回溯
        val dpRes = Array(len1 + 1) { Array(len2 + 1) { IntArray(2) } }
        val dpChar = Array(len1 + 1) { Array(len2 + 1) { Char.MIN_VALUE } }

        for (index in 1..len1) {
            dp[index][0] = index
            dpRes[index][0] = intArrayOf(index - 1, 0)
            dpChar[index][0] = str1[index - 1]
        }
        for (index in 1..len2) {
            dp[0][index] = index
            dpChar[0][index] = str2[index - 1]
            dpRes[0][index] = intArrayOf(0, index - 1)
        }

        for (i in 1..len1) {
            for (j in 1..len2) {
                dp[i][j] = if (str1[i - 1] == str2[j - 1]) {
                    // 如果str1[i-1]和str2[j-1]相等，则dp[i][j]可以由dp[i-1][j-1]转移而来
                    dpRes[i][j] = intArrayOf(i - 1, j - 1)
                    dpChar[i][j] = str1[i - 1]
                    dp[i - 1][j - 1] + 1
                } else {
                    // 如果str1[i-1]和str2[j-1]不相等，则dp[i][j]可以由dp[i-1][j]和dp[i][j-1]中的较小值转移而来
                    val count = if (dp[i - 1][j] > dp[i][j - 1]) {
                        dpRes[i][j] = intArrayOf(i, j - 1)
                        dpChar[i][j] = str2[j - 1]
                        dp[i][j - 1]
                    } else {
                        dpRes[i][j] = intArrayOf(i - 1, j)
                        dpChar[i][j] = str1[i - 1]
                        dp[i - 1][j]
                    }
                    count + 1
                }
            }
        }

        var i = len1
        var j = len2
        var cur = dp[len1][len2]
        val res = StringBuilder()

        while (cur > 0) {
            res.append(dpChar[i][j])
            val preRes = dpRes[i][j]
            i = preRes[0]
            j = preRes[1]
            cur = dp[i][j]
        }

        return res.toString().reversed()
    }

}

fun main() {
    // 创建最短公共超序列的测试用例
    val test = ShortestCommonSupersequence()

    val testCases = arrayOf(
        arrayOf("ABCD", "EFGH"),
        arrayOf("ABCDGH", "AEDFHR"),
        arrayOf("ABCD", "BCDEF"),
        arrayOf("AGGTAB", "GXTXAYB"),
        arrayOf("ABC", "BCD")
    )

    val expectedResults = arrayOf(
        arrayOf("ABCDEFGH"),
        arrayOf("AEDBCDFGH"),
        arrayOf("ABCDEF"),
        arrayOf("AGGXTXAYB", "AGXGTXAYB"),
        arrayOf("ABCD", "BCD")
    )

    for (i in 0 until testCases.size) {
        val res = test.shortestCommonSupersequence(testCases[i][0], testCases[i][1])
        if (expectedResults[i].contains(res)) {
            println("ok")
        } else {
            println("\u001B[31merror\u001B[0m: expect:${expectedResults[i].joinToString()} but found $res for case ${testCases[i].joinToString()}")
        }
    }
}