package medium


/**
 * date: 2022/3/10
 * description:
 * copy from: https://leetcode.com/problems/spiral-matrix/discuss/1307175/Clean-and-Readable-Kotlin-Code-beats-100-percent-kotlin-solutions.
 */

object SpiralMatrixFast {
    enum class Direction {
        UP, DOWN, LEFT, RIGHT;

        fun nextDirection() = when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }

        fun nextElem(i: Int, j: Int): Pair<Int, Int> = when (this) {
            UP -> Pair(i - 1, j)
            DOWN -> Pair(i + 1, j)
            LEFT -> Pair(i, j - 1)
            RIGHT -> Pair(i, j + 1)
        }
    }

    // 	176 ms	32.9 MB
    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        var k = 0
        var currDirection = Direction.RIGHT
        var i = 0
        var j = 0

        val result = ArrayList<Int>()

        while (k < matrix.size * matrix[0].size) {
            // 添加此元素
            result.add(matrix[i][j])
            matrix[i][j] = Int.MIN_VALUE

            var (nextI, nextJ) = currDirection.nextElem(i, j)

            // 判断是否越界，或者是否已读，若是，则更改方向
            if (nextI !in matrix.indices || nextJ !in matrix[nextI].indices || matrix[nextI][nextJ] == Int.MIN_VALUE) {
                currDirection = currDirection.nextDirection()
                val nextIJ = currDirection.nextElem(i, j)
                nextI = nextIJ.first
                nextJ = nextIJ.second
            }

            i = nextI
            j = nextJ
            k++
        }

        return result
    }
}