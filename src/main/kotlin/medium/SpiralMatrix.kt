package medium

import utils.Utils

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/10
 * description: 54. Spiral Matrix
 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
 */
object SpiralMatrix {

    /**
     * 解题思路：
     * 1. 先处理矩阵为空、只有一行、只有一列的情况
     * 2. 然后每次将第一行从左到右数字依次放入结果数组，并删除该行
     * 3. 逆时针旋转矩阵，进入下次循环
     * 336 ms	37.9 MB
     */
    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        // m|n == 0
        if (matrix.isEmpty()) return emptyList()
        // m == 1
        if (matrix.size == 1) return matrix[0].toList()
        // n == 1
        if (matrix[0].size == 1) return matrix.flatMap { it.toList() }
        // m>1, n>1

        val m = matrix.size
        var n = matrix[0].size

        val result = mutableListOf<Int>()
        val firstArray = matrix[0]
        firstArray.forEach { result.add(it) }

        val newMatrix = Array<IntArray>(n) { IntArray(m - 1) }
        var x = 0

        // 逆时针旋转矩阵
        while (n > 0) {
            for ((y, i) in (1 until m).withIndex()) {
                newMatrix[x][y] = matrix[i][n - 1]
            }
            x++
            n--
        }

        return result + spiralOrder(newMatrix)
    }


    /**
     *  5.52%
     *  370 ms	37.6 MB
     */
    fun spiralOrder2(matrix: Array<IntArray>): List<Int> {
        // m|n == 0
        if (matrix.isEmpty()) return emptyList()
        // m == 1
        if (matrix.size == 1) return matrix[0].toList()
        // n == 1
        if (matrix[0].size == 1) return matrix.flatMap { it.toList() }
        // m>1, n>1

        val m = matrix.size
        val n = matrix[0].size

        return spiralOrderInRange(matrix, 0, n - 1, 0, m - 1)
    }

    /**
     * 解题思路：矩阵的坐标为（top，left）->（bottom，right）
     * 遍历时：
     * 1. 先遍历第一行：坐标为(top, left)->(top, right)
     * 2. 再遍历最后一列：坐标为(top+1, right)->(bottom, right)
     * 3. 再遍历最后一行：坐标为(bottom, right-1)->(bottom, left)
     * 4. 再遍历第一列：坐标为(bottom-1, left)->(top+1, left)
     * 5. 缩小矩阵的范围，即矩阵坐标变为（top+1，left+1）->（bottom-1，right-1），重复1-4步骤
     */
    fun spiralOrderInRange(matrix: Array<IntArray>, left: Int, right: Int, top: Int, bottom: Int): List<Int> {
        if (left > right || top > bottom) return emptyList()

        // 处理只有一行的情况
        if (top == bottom) {
            return matrix[top].slice(left..right).toList()
        }

        // 处理只有一列的情况
        if (right == left) {
            val result = matrix.slice(top..bottom)
            val r = result.flatMap { it.slice(left..right).toList() }
            return r
        }

        val result = mutableListOf<Int>()

        for (i in left..right) {
            result.add(matrix[top][i])
        }

        for (i in top + 1..bottom) {
            result.add(matrix[i][right])
        }

        for (i in right - 1 downTo left) {
            result.add(matrix[bottom][i])
        }

        for (i in bottom - 1 downTo top + 1) {
            result.add(matrix[i][left])
        }

        return result + spiralOrderInRange(matrix, left + 1, right - 1, top + 1, bottom - 1)
    }

    /**
     * 296 ms	38.3 MB
     */
    fun spiralOrderWithLoop(matrix: Array<IntArray>): List<Int> {
        // m|n == 0
        if (matrix.isEmpty()) return emptyList()
        // m == 1
        if (matrix.size == 1) return matrix[0].toList()
        // n == 1
        if (matrix[0].size == 1) return matrix.flatMap { it.toList() }
        // m>1, n>1

        val m = matrix.size
        val n = matrix[0].size

        val levelCount = (Math.max(m, n) + 1) / 2
        val result = mutableListOf<Int>()


        var i = 0
        while (i < levelCount) {

            val left = i
            val right = n-1 - i
            val top = left
            val bottom = m-1 - i

            if (left > right || top > bottom) break

            if (left == right && top == bottom) {
                result.add(matrix[top][bottom])
            } else if (left == right) {
                for (i in top..bottom) {
                    result.add(matrix[i][left])
                }
            } else if (top == bottom) {
                for (i in left..right) {
                    result.add(matrix[top][i])
                }
            } else {
                for (i in left..right) {
                    result.add(matrix[top][i])
                }

                for (i in top + 1..bottom) {
                    result.add(matrix[i][right])
                }

                for (i in right - 1 downTo left) {
                    result.add(matrix[bottom][i])
                }

                for (i in bottom - 1 downTo top + 1) {
                    result.add(matrix[i][left])
                }
            }

            i++
        }

        return result

    }
}

fun main() {
    val matrix = Utils.createMatrixFromString("[[1,2],[11,12],[21,22]]")
    println("spiralOrder:           " + SpiralMatrix.spiralOrder(matrix))
    println("spiralOrder2:          " + SpiralMatrix.spiralOrder2(matrix))
    println("spiralOrderWithLoop:   " + SpiralMatrix.spiralOrderWithLoop(matrix))
    println("SpiralMatrixFast:      " + SpiralMatrixFast.spiralOrder(matrix))
}