package medium

/**
 * @author : jixiaoyong
 * @description ： 779. 第K个语法符号
 * 我们构建了一个包含 n 行( 索引从 1  开始 )的表。首先在第一行我们写上一个 0。接下来的每一行，将前一行中的0替换为01，1替换为10。
 * 例如，对于 n = 3 ，第 1 行是 0 ，第 2 行是 01 ，第3行是 0110 。
 * 给定行数 n 和序数 k，返回第 n 行中第 k 个字符。（ k 从索引 1 开始）
 *
 * 提示:
 *
 * 1 <= n <= 30
 * 1 <= k <= 2^(n - 1)
 * https://leetcode.cn/problems/k-th-symbol-in-grammar/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/20/2022
 */
class KthSymbolInGrammar {

    /**
     * 211 ms	33.8 MB
     * 时间复杂度O(N) 需要递归N次才能找到结果
     * 空间复杂度O(N) 需要递归N次才能找到结果
     * 从1到n行，对应的字符长度为2^(n-1)个，即每一行（除第一行外）都是前一行的2倍，所以f(n,k) 取决于 f(n-1,k/2)处的数字
     * 如果k为奇数，则是f(n-1,k/2)的第一个字符，否则是第二个
     */
    val n0 = intArrayOf(0, 1)
    val n1 = intArrayOf(1, 0)
    fun kthGrammar(n: Int, k: Int): Int {
        val n2 = n0
        if (n <= 2) {
            return n2[k - 1]
        }

        val pre = kthGrammar(n - 1, (k + 1) / 2)

        val index = if (k % 2 == 0) 1 else 0
        val cur = if (pre == 0) n0 else n1
        return cur[index]
    }

    /**
     * 官方方案
     * 方法二：找规律 + 递归
     * 0
     * 0 1
     * 01 10
     * 0110 1001
     *
     * 每一行后半部分是前半部分的翻转，所以除了k==0时对应字符为0外，其余k都可以认为是前半部分的翻转，而前半部分又是刚好是上一行的数字
     * 所以问题就成了求解上一行的数字
     *
     * https://leetcode.cn/problems/k-th-symbol-in-grammar/solution/di-kge-yu-fa-fu-hao-by-leetcode-solution-zgwd/
     *
     * 时间复杂度O(N) 最差情况2^(n - 1)时，需要递归N次才能找到结果
     * 空间复杂度O(N) 最差情况2^(n - 1)时，需要递归N次才能找到结果
     *
     *
     * 方法三：找规律 + 位运算
     * 在「方法二」的基础上，我们来进行优化，本质上我们其实只需要求在过程中的“翻转”总次数，如果“翻转”为偶数次则原问题求解为 0，否则为 1。
     * 	120 ms	32.1 MB
     * 	时间复杂度：O(logk) 其中 k 为题目给定查询的下标。
     * 	空间复杂度：O(1)，仅使用常量变量。
     */
     fun kthGrammar2(n: Int, k: Int): Int {
        // return Integer.bitCount(k - 1) & 1;
        var k = k
        k--
        var res = 0
        while (k > 0) {
            k = k and k - 1
            res = res xor 1
        }
        return res
    }

}

fun main() {
    val obj = KthSymbolInGrammar()

    val cases = arrayOf(
        Pair(30, 6),
        Pair(1, 1),
        Pair(1, 2),
        Pair(2, 1),
        Pair(2, 2),
        Pair(5, 2),
        Pair(15, 23),
    )

    cases.forEach {
        println("${obj.kthGrammar2(it.first, it.second)}  --- > $it")
    }
}