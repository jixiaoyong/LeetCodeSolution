package medium

import utils.Utils

/**
 * @author : jixiaoyong
 * @description ：1105. 填充书架
 * 给定一个数组 books ，其中books[i] = [thicknessi, heighti]表示第 i 本书的厚度和高度。你也会得到一个整数 shelfWidth 。
 *
 * 按顺序将这些书摆放到总宽度为 shelfWidth 的书架上。
 *
 * 先选几本书放在书架上（它们的厚度之和小于等于书架的宽度 shelfWidth ），然后再建一层书架。重复这个过程，直到把所有的书都放在书架上。
 *
 * 需要注意的是，在上述过程的每个步骤中，摆放书的顺序与指定好的顺序相同。
 *
 * 例如，如果这里有 5 本书，那么可能的一种摆放情况是：第一和第二本书放在第一层书架上，第三本书放在第二层书架上，第四和第五本书放在最后一层书架上。
 * 每一层所摆放的书的最大高度就是这一层书架的层高，书架整体的高度为各层高之和。
 *
 * 以这种方式布置书架，返回书架整体可能的最小高度。
 *
 * 提示：
 *
 * 1 <= books.length <= 1000
 * 1 <= thicknessi<= shelfWidth <= 1000
 * 1 <= heighti<= 1000
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/4/23
 */
class FillingBookcaseShelves {

    /**
     * 关键思路：dp[i]表示前i本书放置时，书架的最小高度
     * 那么当放置书时，存在一个j，j在[0,i)之间，满足条件：j及之前的书本刚好形成最小的书架高度，然后在(j,i]之间的书本构成最后一个书架，其高度为
     * (j,i]间的书本的最大高度，那么dp[i]的值就是dp[j]和最后一层书架的高度之和
     * dp的作用是记录一下当前书架的最小高度，避免重复计算
     * https://leetcode.cn/problems/filling-bookcase-shelves/solution/tian-chong-shu-jia-by-leetcode-solution-b7py/
     */
    fun minHeightShelves(books: Array<IntArray>, shelfWidth: Int): Int {
        val booksSize = books.size
        val dp = IntArray(booksSize) { Int.MAX_VALUE }
        dp[0] = books[0][1]

        for (i in 1 until booksSize) {
            var maxHeight = 0
            var totalWidth = 0
            // 此处分为两步，第一步为计算前i本书单独放置到最后一层时，书架的最小高度
            // 第二步为计算前i-1到0本书放置到最后一层时，书架的最小高度
            for (j in i downTo 0) {
                totalWidth += books[j][0]
                if (totalWidth > shelfWidth) {
                    break
                }
                maxHeight = Math.max(maxHeight, books[j][1])
                // 如果j==0,那么他之前的书架高度为0
                val preLevelHeight = if (j == 0) 0 else dp[j - 1]
                dp[i] = Math.min(dp[i], preLevelHeight + maxHeight)
            }
        }

        return dp[booksSize - 1]
    }

    fun minHeightShelves1(books: Array<IntArray>, shelfWidth: Int): Int {
        val n = books.size
        val dp = IntArray(n + 1) { Int.MAX_VALUE }
        dp[0] = 0

        for (i in 1..n) {
            var curWidth = 0
            var curMaxHeight = 0
            for (j in i downTo 1) {
                curWidth += books[j - 1][0]
                if (curWidth > shelfWidth) {
                    break
                }
                curMaxHeight = maxOf(curMaxHeight, books[j - 1][1])
                dp[i] = minOf(dp[i], dp[j - 1] + curMaxHeight)
            }
        }

        return dp[n]
    }

    fun minHeightShelves2(books: Array<IntArray>, shelfWidth: Int): Int {
        val n = books.size
        val dp = IntArray(n) { Int.MAX_VALUE }

        for (i in 0 until n) {
            var totalWidth = 0
            var maxHeight = 0
            for (j in i downTo 0) {
                totalWidth += books[j][0]
                if (totalWidth > shelfWidth) {
                    break
                }
                maxHeight = maxOf(maxHeight, books[j][1])
                dp[i] = if (j == 0) maxHeight else minOf(dp[i], dp[j - 1] + maxHeight)
            }
        }

        return dp[n - 1]
    }
}

fun main() {
    val obj = FillingBookcaseShelves()
    val cases = arrayListOf(
        arrayListOf("[[7,3],[8,7],[2,7],[2,5]]", 10, 15),
        arrayListOf("[[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]]", 4, 6),
        arrayListOf("[[1,3],[2,4],[3,2]]", 6, 4),
        arrayListOf("[[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]]", 4, 6),
        arrayListOf("[[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]]", 6, 5),
        arrayListOf("[[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]]", 8, 4),
        arrayListOf("[[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]]", 10, 3),
        arrayListOf("[[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2]]", 2, 20),
        arrayListOf("[[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2]]", 3, 20),
        arrayListOf("[[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2]]", 4, 10),
        arrayListOf("[[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2]]", 5, 10),
        arrayListOf("[[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2]]", 6, 8),
        arrayListOf("[[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2],[2,2]]", 7, 8)
    )

    cases.forEach {
        val res = obj.minHeightShelves(Utils.createMatrixFromString(it[0] as String), it[1] as Int)
        if (it[2] != res) {
            println("${it[0]}  - ${it[1]} expect: ${it.getOrNull(2)}, actual:$res")
        }
    }
}