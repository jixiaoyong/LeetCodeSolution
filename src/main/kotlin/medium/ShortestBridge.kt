package medium

/**
 * @author : jixiaoyong
 * @description ： 934. 最短的桥
 * 给你一个大小为 n x n 的二元矩阵 grid ，其中 1 表示陆地，0 表示水域。
 * 岛 是由四面相连的 1 形成的一个最大组，即不会与非组内的任何其他 1 相连。grid 中 恰好存在两座岛 。
 * 你可以将任意数量的 0 变为 1 ，以使两座岛连接起来，变成 一座岛 。
 * 返回必须翻转的 0 的最小数目。
 *
 * 提示：
 *
 * n == grid.length == grid[i].length
 * 2 <= n <= 100
 * grid[i][j] 为 0 或 1
 * grid 中恰有两个岛
 *
 * https://leetcode.cn/problems/shortest-bridge/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/25/2022
 */
class ShortestBridge {

    /**
     * 368 ms	50.1 MB
     */
    var firstIslands = mutableListOf<IntArray>()
    fun shortestBridge(grid: Array<IntArray>): Int {
        firstIslands.clear()
        val range = IntRange(0, grid.size - 1)


        val n = grid.size
        var result = 2
        // 先找到一个小岛，将其标记为2
        loop@ for (x in 0 until n) {
            for (y in 0 until n) {
                val cur = grid[x][y]
                if (cur == 1) {
                    findNextIsland(grid, x, y)
                    break@loop
                }
            }
        }

        val aroundSeaNode = mutableListOf<IntArray>()
        loop2@ while (true) {
            val plus = ++result
            firstIslands.forEach {

                val x = it[0]
                val y = it[1]
                val aroundPositions = mutableListOf(
                    intArrayOf(x - 1, y), intArrayOf(x + 1, y), intArrayOf(x, y - 1), intArrayOf(x, y + 1)
                )
                aroundPositions.forEach {
                    val nx = it[0]
                    val ny = it[1]
                    if (range.contains(nx) && range.contains(ny)) {
                        val num = grid[nx][ny]
                        if (num == 1) {
                            return grid[x][y] - 2
                        } else if (num == 0) {
                            grid[nx][ny] = plus
                            aroundSeaNode.add(intArrayOf(nx, ny))
                        }
                    }
                }
            }

            firstIslands.clear()
            firstIslands.addAll(aroundSeaNode)
            aroundSeaNode.clear()
        }

    }

    fun findNextIsland(grid: Array<IntArray>, x: Int, y: Int) {
        val range = IntRange(0, grid.size - 1)
        if (!range.contains(x) || !range.contains(y)) {
            return
        }

        grid[x][y] = 2
        firstIslands.add(intArrayOf(x, y))

        // top
        if (getXY(grid, x - 1, y) == 1 && !firstIslands.contains(intArrayOf(x, y))) {
            findNextIsland(grid, x - 1, y)
        }
        // bottom
        if (getXY(grid, x + 1, y) == 1 && !firstIslands.contains(intArrayOf(x, y))) {
            findNextIsland(grid, x + 1, y)
        }
        // left
        if (getXY(grid, x, y - 1) == 1 && !firstIslands.contains(intArrayOf(x, y))) {
            findNextIsland(grid, x, y - 1)
        }
        // right
        if (getXY(grid, x, y + 1) == 1 && !firstIslands.contains(intArrayOf(x, y))) {
            findNextIsland(grid, x, y + 1)
        }
    }

    fun getXY(grid: Array<IntArray>, x: Int, y: Int): Int {
        val range = IntRange(0, grid.size - 1)
        if (!range.contains(x) || !range.contains(y)) {
            return -1
        } else {
            return grid[x][y]
        }
    }
}

fun main() {
    val obj = ShortestBridge()

    val cases = mutableListOf(
        arrayOf(intArrayOf(0, 1, 0), intArrayOf(0, 0, 0), intArrayOf(0, 0, 1)),
        arrayOf(intArrayOf(0, 1), intArrayOf(1, 0)),

        arrayOf(
            intArrayOf(1, 1, 1, 1, 0),
            intArrayOf(1, 0, 1, 0, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1)
        ),
        arrayOf(
            intArrayOf(1, 1, 1, 1, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 1, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 1, 1, 1, 1)
        ),
        arrayOf(
            intArrayOf(1, 1, 0, 0, 0),
            intArrayOf(1, 1, 0, 0, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 1)
        ),
    )

    for (case in cases) {
        val result = obj.shortestBridge(case)
        println("result:$result")
    }
}