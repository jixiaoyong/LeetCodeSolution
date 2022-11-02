package medium

/**
 * @author : jixiaoyong
 * @description ：1620. 网络信号最好的坐标
 * 给你一个数组 towers和一个整数 radius 。
 *
 * 数组 towers 中包含一些网络信号塔，其中towers[i] = [xi, yi, qi]表示第i个网络信号塔的坐标是(xi, yi)且信号强度参数为qi。
 * 所有坐标都是在 X-Y 坐标系内的整数坐标。两个坐标之间的距离用 欧几里得距离计算。
 *
 * 整数radius表示一个塔 能到达的 最远距离。如果一个坐标跟塔的距离在 radius以内，那么该塔的信号可以到达该坐标。
 * 在这个范围以外信号会很微弱，所以 radius以外的距离该塔是 不能到达的。
 *
 * 如果第 i个塔能到达 (x, y)，那么该塔在此处的信号为⌊qi / (1 + d)⌋，其中d是塔跟此坐标的距离。一个坐标的 信号强度 是所有 能到达该坐标的塔的信号强度之和。
 *
 * 请你返回数组 [cx, cy] ，表示 信号强度 最大的 整数 坐标点(cx, cy) 。如果有多个坐标网络信号一样大，请你返回字典序最小的 非负 坐标。
 *
 * 注意：
 *
 * 坐标(x1, y1)字典序比另一个坐标(x2, y2) 小，需满足以下条件之一：
 * 要么x1 < x2，
 * 要么x1 == x2 且y1 < y2。
 * ⌊val⌋表示小于等于val的最大整数（向下取整函数）。
 *
 * 提示：
 *
 * 1 <= towers.length <= 50
 * towers[i].length == 3
 * 0 <= xi, yi, qi <= 50
 * 1 <= radius <= 50
 *
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/2/2022
 */
class CoordinateWithMaximumNetworkQuality {

    /**
     * 思路是，以每个tower为原点，在原点加减radius范围内查找更新所有满足条件的节点的信号值
     * 在此期间，不断的计算刷新最大信号值和坐标
     * 注意同一个tower不要重复扫描同一个点
     * 时间复杂度O（N*R^2）,N表示towers中塔的数目，R是radius的大小，最大范围是每个tower都扫描一个2R*2R的范围
     * 空间复杂度O(1)，固定一个51*51的表格记录结果
     * 930 ms	74.3 MB
     */
    var maxSignal = 0
    var coordinator = intArrayOf(Int.MAX_VALUE, Int.MAX_VALUE)
    fun bestCoordinate(towers: Array<IntArray>, radius: Int): IntArray {
        if (towers.size == 1) {
            val tower = towers[0]
            return if (tower[2] > 0) intArrayOf(tower[0], tower[1]) else intArrayOf(0, 0)
        }
        // 这里50+1即可，因为x，y的范围在0~50范围，超出之后没有其他tower，也不会比50内的点信号更强
        val maxDistance = 50 + 1
        val grid = Array(maxDistance) { IntArray(maxDistance) { 0 } }
        maxSignal = 0

        towers.forEach {
            lookupAllNode(
                grid,
                intArrayOf(it[0] - radius, it[1] + radius),
                intArrayOf(it[0] + radius, it[1] - radius),
                it, radius
            )
        }

        return coordinator
    }

    private fun lookupAllNode(
        grid: Array<IntArray>,
        topLeft: IntArray,
        rightBottom: IntArray,
        tower: IntArray,
        radius: Int
    ) {
        val topLeft = formatCoordinate(topLeft)
        val rightBottom = formatCoordinate(rightBottom)

        if (topLeft[0] > rightBottom[0] || topLeft[1] < rightBottom[1]) {
            return
        }

        val nodes = mutableListOf<Pair<Int, Int>>()
        for (x in topLeft[0]..rightBottom[0]) {
            // 横向遍历topLeft和rightBottom的x轴上的坐标
            if (!nodes.contains(Pair(x, topLeft[1]))) {
                calNodeSignal(grid, x, topLeft[1], tower, radius)
                nodes.add(Pair(x, topLeft[1]))
            }
            if (!nodes.contains(Pair(x, rightBottom[1]))) {
                calNodeSignal(grid, x, rightBottom[1], tower, radius)
                nodes.add(Pair(x, rightBottom[1]))
            }

        }

        for (y in topLeft[1] downTo rightBottom[1]) {
            // 竖向遍历topLeft和rightBottom的y轴上的坐标
            // 针对于1,3 --> 2,1这样的坐标，避免重复计算，移除上一个for循环中已经横向遍历的坐标
            if (!nodes.contains(Pair(topLeft[0], y))) {
                calNodeSignal(grid, topLeft[0], y, tower, radius)
                nodes.add(Pair(topLeft[0], y))
            }
            if (!nodes.contains(Pair(rightBottom[0], y))) {
                calNodeSignal(grid, rightBottom[0], y, tower, radius)
                nodes.add(Pair(rightBottom[0], y))
            }
        }

        lookupAllNode(
            grid,
            intArrayOf(topLeft[0] + 1, topLeft[1] - 1),
            intArrayOf(rightBottom[0] - 1, rightBottom[1] + 1),
            tower,
            radius
        )
    }

    private fun formatCoordinate(xy: IntArray): IntArray {
        val x = if (xy[0] < 0) {
            0
        } else if (xy[0] > 50) {
            50
        } else {
            xy[0]
        }
        val y = if (xy[1] < 0) {
            0
        } else if (xy[1] > 50) {
            50
        } else {
            xy[1]
        }
        return intArrayOf(x, y)
    }

    private fun calNodeSignal(grid: Array<IntArray>, x: Int, y: Int, tower: IntArray, radius: Int) {
        if (x < 0 || y < 0 || x >= grid[0].size || y >= grid.size) {
            return
        }

        val distance = Math.sqrt(Math.pow((x - tower[0]).toDouble(), 2.0) + Math.pow((y - tower[1]).toDouble(), 2.0))
        if (radius < distance) {
            return
        }

        val signal = (tower[2] / (1 + distance)).toInt()
        grid[x][y] += signal
        if (grid[x][y] > maxSignal) {
            maxSignal = grid[x][y]
            coordinator = intArrayOf(x, y)
        } else if (grid[x][y] == maxSignal) {
            if (x < coordinator[0]) {
                coordinator = intArrayOf(x, y)
            } else if (x == coordinator[0] && y < coordinator[1]) {
                coordinator = intArrayOf(x, y)
            }
        }
    }
}

fun main() {
    val obj = CoordinateWithMaximumNetworkQuality()

    val cases = mutableListOf(
        Pair(
            arrayOf(intArrayOf(50, 20, 31), intArrayOf(43, 36, 29)),
            38
        ),
        Pair(
            arrayOf(intArrayOf(45, 21, 37)),
            14
        ),
        Pair(
            arrayOf(intArrayOf(8, 39, 4), intArrayOf(45, 21, 37), intArrayOf(37, 9, 48), intArrayOf(13, 10, 38)),
            14
        ),// 预期 37，9，实际45, 21
        Pair(arrayOf(intArrayOf(1, 2, 13), intArrayOf(2, 1, 7), intArrayOf(0, 1, 9)), 2),
        Pair(arrayOf(intArrayOf(1, 2, 5), intArrayOf(2, 1, 7), intArrayOf(3, 1, 9)), 2),
        Pair(arrayOf(intArrayOf(23, 11, 21)), 9),
        Pair(arrayOf(intArrayOf(42, 0, 0)), 7),
    )

    cases.forEach {
        val result = obj.bestCoordinate(it.first, it.second)
        println("${result.joinToString()}   -> ${it.second} -- ${it.first.joinToString { it.joinToString() }}")
    }
}