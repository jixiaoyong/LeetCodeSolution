package medium

import utils.Utils
import java.util.*

/**
 * @author : jixiaoyong
 * @description ： 200. 岛屿数量
 * 给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 *
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/11/2022
 */
class NumberOfIslands {

    /**
     * 416 ms	57.5 MB
     * 感染函数
     * 来自mata川 https://leetcode.cn/problems/number-of-islands/comments/
     *
     * 思路是：遇到一个1，则将其以及能触达的所有1都感染为同一个岛（将遍历过的1感染为2），并计次+1，
     * 依次重复直到遍历所有节点
     *
     * 时间复杂度：O(MN)，其中 M 和 N 分别为行数和列数。
     * 空间复杂度：O(MN)，在最坏情况下，整个网格均为陆地，深度优先搜索的深度达到 MN。
     */
    fun numIslands(grid: Array<CharArray>): Int {
        var numberOfIslands = 0

        for (i in 0 until grid.size) {
            val row = grid[i]
            val rowLength = row.size
            for (j in 0 until row.size) {
                if ('1' == row[j]) {
                    inflectIsland(grid, i, j, rowLength)
                    numberOfIslands++
                }
            }
        }

        return numberOfIslands
    }

    private fun inflectIsland(grid: Array<CharArray>, x: Int, y: Int, rowLength: Int) {
        if (x > grid.size - 1 || x < 0 || y < 0 || y > rowLength - 1 || '1' != grid[x][y]) {
            return
        }

        grid[x][y] = '2'

        // top
        inflectIsland(grid, x - 1, y, rowLength)
        // bottom
        inflectIsland(grid, x + 1, y, rowLength)
        // left
        inflectIsland(grid, x, y - 1, rowLength)
        // right
        inflectIsland(grid, x, y + 1, rowLength)

    }


    /**
     * 832 ms	78.8 MB
     * 思路：每次对比当前节点、上方top和左边left相邻节点，如果是1则加入其中，记录当前节点所在的岛序号
     * 如果当前节点的top或left不在同一个岛，则合并两个岛，并且更新这些岛的序号
     */
    fun numIslands2(grid: Array<CharArray>): Int {

        // 记录了岛的组成部分，用于计数
        val linkListArray = hashMapOf<String, LinkedList<Pair<Int, Int>>>()
        // 记录了每个扫描过的节点的岛编号，使用编号在linkListArray查询对应的"岛"
        val nodeLinkKey = hashMapOf<Pair<Int, Int>, String>()

        for (x in 0 until grid.size) {
            val charArray = grid.get(x)
            for (j in 0 until charArray.size) {
                val node = charArray.get(j)
                if ('1' == node) {
                    val currentNode = Pair(x, j)
                    val top = Pair(x - 1, j)
                    val left = Pair(x, j - 1)

                    var hasIsland = false
                    val aroundNode = arrayOf(top, left)
                    aroundNode.forEach { around ->
                        if ('1' == grid.getOrNull(around.first)?.getOrNull(around.second)) {
                            val oldIslandsKey = nodeLinkKey.get(around)
                            val oldIsland = linkListArray.get(oldIslandsKey)

                            if (hasIsland) {
                                // 合并多个含有同一个node的列表
                                val exitIndex = nodeLinkKey.get(currentNode)
                                val exitIsland = linkListArray.get(exitIndex)
                                if (exitIndex != null && exitIsland != null && exitIndex != oldIslandsKey) {
                                    oldIsland?.let {
                                        exitIsland.addAll(it)
                                        linkListArray.put(exitIndex, exitIsland)
                                        // 更新被合并岛的节点岛序号，并移除当前岛
                                        it.forEach {
                                            nodeLinkKey.put(it, exitIndex)
                                        }
                                        linkListArray.remove(oldIslandsKey)
                                        hasIsland = true
                                    }
                                }
                            } else if (oldIsland != null) {
                                // 如果相邻节点有岛，则加入岛中
                                oldIsland.add(currentNode)
                                oldIslandsKey?.let { nodeLinkKey.put(currentNode, it) }
                                hasIsland = true
                            } else {
                                // 如果相邻节点没有岛，则新建岛
                                hasIsland = true
                                val linkList = LinkedList<Pair<Int, Int>>()
                                linkList.add(currentNode)
                                linkListArray.put(currentNode.toString(), linkList)
                                nodeLinkKey.put(currentNode, currentNode.toString())
                            }

                        }
                    }
                    if (!hasIsland) {
                        // 处理单个节点组成岛的情况：[[1]]
                        val linkList = LinkedList<Pair<Int, Int>>()
                        linkList.add(currentNode)
                        linkListArray.put(currentNode.toString(), linkList)
                        nodeLinkKey.put(currentNode, currentNode.toString())
                    }
                }
            }
        }

        return linkListArray.size
    }

    /**
     * 方法二：广度优先搜索
     * 解法来自：https://leetcode.cn/problems/number-of-islands/solution/dao-yu-shu-liang-by-leetcode/
     * 时间复杂度：O(MN)，其中 MM 和 NN 分别为行数和列数。
     * 空间复杂度：O(min(M,N))，在最坏情况下，整个网格均为陆地，队列的大小可以达到min(M,N)。
     */
    fun numIslands3(grid: Array<CharArray>?): Int {
        if (grid == null || grid.size == 0) {
            return 0
        }
        val nr = grid.size
        val nc = grid[0].size
        var num_islands = 0
        for (r in 0 until nr) {
            for (c in 0 until nc) {
                if (grid[r][c] == '1') {
                    ++num_islands
                    grid[r][c] = '0'
                    val neighbors: Queue<Int> = LinkedList()
                    neighbors.add(r * nc + c)
                    while (!neighbors.isEmpty()) {
                        val id: Int = neighbors.remove()
                        val row = id / nc
                        val col = id % nc
                        if (row - 1 >= 0 && grid[row - 1][col] == '1') {
                            // top
                            neighbors.add((row - 1) * nc + col)
                            grid[row - 1][col] = '0'
                        }
                        if (row + 1 < nr && grid[row + 1][col] == '1') {
                            // bottom
                            neighbors.add((row + 1) * nc + col)
                            grid[row + 1][col] = '0'
                        }
                        if (col - 1 >= 0 && grid[row][col - 1] == '1') {
                            // left
                            neighbors.add(row * nc + col - 1)
                            grid[row][col - 1] = '0'
                        }
                        if (col + 1 < nc && grid[row][col + 1] == '1') {
                            // right
                            neighbors.add(row * nc + col + 1)
                            grid[row][col + 1] = '0'
                        }
                    }
                }
            }
        }
        return num_islands
    }

    internal class UnionFind(grid: Array<CharArray>) {
        var count = 0
        var parent: IntArray
        var rank: IntArray

        init {
            val m = grid.size
            val n = grid[0].size
            parent = IntArray(m * n)
            rank = IntArray(m * n)
            for (i in 0 until m) {
                for (j in 0 until n) {
                    if (grid[i][j] == '1') {
                        parent[i * n + j] = i * n + j
                        ++count
                    }
                    rank[i * n + j] = 0
                }
            }
        }

        fun find(i: Int): Int {
            if (parent[i] != i) parent[i] = find(parent[i])
            return parent[i]
        }

        fun union(x: Int, y: Int) {
            val rootx = find(x)
            val rooty = find(y)
            if (rootx != rooty) {
                if (rank[rootx] > rank[rooty]) {
                    parent[rooty] = rootx
                } else if (rank[rootx] < rank[rooty]) {
                    parent[rootx] = rooty
                } else {
                    parent[rooty] = rootx
                    rank[rootx] += 1
                }
                --count
            }
        }
    }


    /**
     * 并查集
     * 为了求出岛屿的数量，我们可以扫描整个二维网格。如果一个位置为 11，则将其与相邻四个方向上的 11 在并查集中进行合并
     * 最终岛屿的数量就是并查集中连通分量的数目。
     * https://leetcode.cn/problems/number-of-islands/solution/dao-yu-shu-liang-by-leetcode/
     */
    fun numIslands4(grid: Array<CharArray>?): Int {
        if (grid == null || grid.size == 0) {
            return 0
        }
        val nr = grid.size
        val nc = grid[0].size
        val num_islands = 0
        val uf = UnionFind(grid)
        for (r in 0 until nr) {
            for (c in 0 until nc) {
                if (grid[r][c] == '1') {
                    grid[r][c] = '0'
                    if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                        uf.union(r * nc + c, (r - 1) * nc + c)
                    }
                    if (r + 1 < nr && grid[r + 1][c] == '1') {
                        uf.union(r * nc + c, (r + 1) * nc + c)
                    }
                    if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                        uf.union(r * nc + c, r * nc + c - 1)
                    }
                    if (c + 1 < nc && grid[r][c + 1] == '1') {
                        uf.union(r * nc + c, r * nc + c + 1)
                    }
                }
            }
        }
        return uf.count
    }
}

fun main() {
    val grid = Utils.createCharMatrixFromString(
//        """[
//  ["1","1","1","1","0"],
//  ["1","1","0","1","0"],
//  ["1","1","0","0","0"],
//  ["0","0","0","0","0"]
//  ]"""
//        """[
// ["1","1","0","0","0"],
//  ["1","1","1","0","0"],
//  ["0","0","1","0","0"],
//  ["0","0","0","1","1"]
//  ]"""
//        """[
// ["0","0","1","0","0"]
//  ]"""
//        """[["1"]]"""
//
//        """[["1","1","1"],["0","1","0"],["1","1","1"]]"""
//        """[["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]"""
//        """[["1","1","1","1","1","0","1","1","1","1"],["1","0","1","0","1","1","1","1","1","1"],["0","1","1","1","0","1","1","1","1","1"],["1","1","0","1","1","0","0","0","0","1"],["1","0","1","0","1","0","0","1","0","1"],["1","0","0","1","1","1","0","1","0","0"],["0","0","1","0","0","1","1","1","1","0"],["1","0","1","1","1","0","0","1","1","1"],["1","1","1","1","1","1","1","1","0","1"],["1","0","1","1","1","1","1","1","1","0"]]"""
//        """[["1","1","1"],["1","1","1"],["1","1","1"],["1","1","1"],["1","1","1"]]"""
        """[["1","0","1","1"],["1","1","0","0"],["0","0","1","1"],["1","1","1","1"]]"""
//        maxMatrixString
    )

    println(NumberOfIslands().numIslands3(grid))

}


