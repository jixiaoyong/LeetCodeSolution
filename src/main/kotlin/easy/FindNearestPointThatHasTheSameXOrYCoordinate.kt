package easy

/**
 * @author : jixiaoyong
 * @description ： 1779. 找到最近的有相同 X 或 Y 坐标的点
 * 给你两个整数x 和y，表示你在一个笛卡尔坐标系下的(x, y)处。
 * 同时，在同一个坐标系下给你一个数组points，其中points[i] = [ai, bi]表示在(ai, bi)处有一个点。
 * 当一个点与你所在的位置有相同的 x 坐标或者相同的 y 坐标时，我们称这个点是 有效的。
 *
 * 请返回距离你当前位置曼哈顿距离最近的有效点的下标（下标从 0 开始）。如果有多个最近的有效点，请返回下标最小的一个。如果没有有效点，请返回-1。
 *
 * 两个点 (x1, y1)和 (x2, y2)之间的 曼哈顿距离为abs(x1 - x2) + abs(y1 - y2)。
 *
 * 提示：
 *
 * 1 <= points.length <= 104
 * points[i].length == 2
 * 1 <= x, y, ai, bi <= 104
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/1/2022
 */
class FindNearestPointThatHasTheSameXOrYCoordinate {
    fun nearestValidPoint(x: Int, y: Int, points: Array<IntArray>): Int {
        var distance = Int.MAX_VALUE
        var minimumIndex = Int.MAX_VALUE

        points.forEachIndexed { index, ints ->
            if (x != ints[0] && y != ints[1]) {
                return@forEachIndexed
            }
            val curDistance = Math.abs(ints[0] - x) + Math.abs(ints[1] - y)
            if (curDistance < distance) {
                distance = curDistance
                minimumIndex = index
            } else if (curDistance == distance) {
                minimumIndex = Math.min(minimumIndex, index)
            }
        }

        return if (minimumIndex == Int.MAX_VALUE) -1 else minimumIndex
    }
}

fun main() {
    val obj = FindNearestPointThatHasTheSameXOrYCoordinate()
    val cases = mutableListOf(
        Triple(
            3,
            4,
            arrayOf(intArrayOf(1, 2), intArrayOf(3, 1), intArrayOf(2, 4), intArrayOf(2, 3), intArrayOf(4, 4))
        ),
        Triple(
            3,
            4,
            arrayOf(intArrayOf(3, 4))
        ),
        Triple(
            3,
            4,
            arrayOf(intArrayOf(2, 3))
        ),
        Triple(
            4,
            4,
            arrayOf(
                intArrayOf(2, 3),
                intArrayOf(1, 2),
                intArrayOf(3, 1),
                intArrayOf(2, 4),
                intArrayOf(2, 3),
                intArrayOf(4, 4)
            )
        ),
    )

    cases.forEach {
        println(
            "${
                obj.nearestValidPoint(
                    it.first,
                    it.second,
                    it.third
                )
            }   --${it.first}/${it.second}/${it.third.joinToString { it.joinToString() }}"
        )
    }
}