package medium

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ：1637. 两点之间不包含任何点的最宽垂直区域
 *
 * 给你n个二维平面上的点 points ，其中points[i] = [xi, yi]，请你返回两点之间内部不包含任何点的最宽垂直区域 的宽度。
 *
 * 垂直区域 的定义是固定宽度，而 y 轴上无限延伸的一块区域（也就是高度为无穷大）。 最宽垂直区域 为宽度最大的一个垂直区域。
 *
 * 请注意，垂直区域边上的点不在区域内。
 *
 * 链接：https://leetcode.cn/problems/widest-vertical-area-between-two-points-containing-no-points
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/3/30
 */
class WidestVerticalAreaBetweenTwoPointsContainsNoPoints {
    fun maxWidthOfVerticalArea(points: Array<IntArray>): Int {
        // 排序
//        quickSort(points, 0, points.size - 1)
        points.sortBy { it[0] }

        // 计算两两之间x轴的间距，记录最大值
        var max = 0
        for (i in 0 until points.size - 1) {
            max = Math.max(max, points[i + 1][0] - points[i][0])
        }
        return max
    }

    fun quickSort(points: IntArray, left: Int, right: Int) {
        if (left < right) {
            val pivotIndex = partition(points, left, right)
            // pivotIndex已经排好序了，不用再排序
            quickSort(points, left, pivotIndex - 1)
            quickSort(points, pivotIndex + 1, right)
        }
    }

    fun partition(points: IntArray, left: Int, right: Int): Int {
        val pivot = points[right]
        // i+1表示的是pivot应该在的位置，通过计算之后保证左侧都小于，右侧都大于
        var i = left - 1
        for (j in left until right) {
            if (points[j] < pivot) {
                // 遇到比这个小的后，将i位置右移
                i++
                swap(points, i, j)
            }
        }
        // 最后将pivot移动到计算的为止
        swap(points, i + 1, right)
        return i + 1
    }

    fun swap(points: IntArray, left: Int, right: Int) {
        val temp = points[left]
        points[left] = points[right]
        points[right] = temp
    }

    companion object {
        fun testQuickSort() {
            val testCases = arrayOf(
//                intArrayOf(1),
//                intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
//                intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1),
                intArrayOf(7, 6, 8, 5, 9, 4, 10, 3, 2, 1),
                intArrayOf(2, 4, 6, 8, 10, 9, 7, 5, 3, 1),
                intArrayOf(1, 3, 5, 7, 9, 10, 8, 6, 4, 2),
                intArrayOf(1, 5, 2, 6, 3, 7, 4, 8, 9, 10),
                intArrayOf(10, 8, 9, 7, 6, 4, 5, 3, 1, 2),
                intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9),
                intArrayOf(
                    15,
                    28,
                    2,
                    5,
                    10,
                    19,
                    30,
                    22,
                    12,
                    27,
                    21,
                    20,
                    11,
                    24,
                    8,
                    3,
                    7,
                    25,
                    9,
                    18,
                    29,
                    6,
                    23,
                    1,
                    13,
                    16,
                    14,
                    26,
                    17,
                    4
                )
            )

            val obj = WidestVerticalAreaBetweenTwoPointsContainsNoPoints()
            testCases.forEach {
                val tmp = it
                val res = obj.quickSort(tmp, 0, it.size - 1)
                println("res:${tmp.joinToString()}}   ori:${it.joinToString()}")
            }
        }
    }

}

fun main() {

    WidestVerticalAreaBetweenTwoPointsContainsNoPoints.testQuickSort()

    return


    // 创建两点之间不包含任何点的最宽垂直区域的10个测试用例
// 两个点，横坐标相同，纵坐标不同
    val test1 = arrayOf(intArrayOf(3, 2), intArrayOf(3, 7))

// 三个点，横坐标不同，纵坐标相同
    val test2 = arrayOf(intArrayOf(1, 5), intArrayOf(5, 5), intArrayOf(7, 5))

// 四个点，横坐标和纵坐标都不同
    val test3 = arrayOf(intArrayOf(8, 7), intArrayOf(9, 9), intArrayOf(7, 4), intArrayOf(9, 7))

// 五个点，两个点横坐标相同，另外三个点纵坐标相同
    val test4 = arrayOf(intArrayOf(2, 5), intArrayOf(2, 8), intArrayOf(7, 5), intArrayOf(9, 5), intArrayOf(11, 5))

// 六个点，两个点纵坐标相同，另外四个点横坐标相同
    val test5 = arrayOf(
        intArrayOf(3, 5),
        intArrayOf(8, 5),
        intArrayOf(5, 2),
        intArrayOf(5, 7),
        intArrayOf(5, 9),
        intArrayOf(5, 11)
    )

// 七个点，横坐标和纵坐标都不同
    val test6 = arrayOf(
        intArrayOf(1, 2),
        intArrayOf(2, 5),
        intArrayOf(4, 7),
        intArrayOf(6, 4),
        intArrayOf(7, 9),
        intArrayOf(9, 6),
        intArrayOf(11, 3)
    )

// 八个点，两个点横坐标相同，另外六个点纵坐标相同
    val test7 = arrayOf(
        intArrayOf(2, 2),
        intArrayOf(2, 7),
        intArrayOf(5, 5),
        intArrayOf(8, 5),
        intArrayOf(11, 5),
        intArrayOf(14, 5),
        intArrayOf(17, 5),
        intArrayOf(20, 5)
    )

// 九个点，横坐标和纵坐标都不同
    val test8 = arrayOf(
        intArrayOf(1, 2),
        intArrayOf(3, 5),
        intArrayOf(5, 7),
        intArrayOf(7, 5),
        intArrayOf(9, 2),
        intArrayOf(11, 5),
        intArrayOf(13, 8),
        intArrayOf(15, 5),
        intArrayOf(17, 2)
    )

// 十个点，两个点纵坐标相同，另外八个点横坐标相同
    val test9 = arrayOf(
        intArrayOf(2, 5),
        intArrayOf(9, 5),
        intArrayOf(12, 5),
        intArrayOf(15, 5),
        intArrayOf(18, 5),
        intArrayOf(21, 5),
        intArrayOf(24, 5),
        intArrayOf(27, 5),
        intArrayOf(30, 2),
        intArrayOf(30, 8)
    )

// 五十个点，横坐标和纵坐标都不同
    val test10 = arrayOf(
        intArrayOf(3, 2), intArrayOf(5, 7), intArrayOf(8, 4), intArrayOf(10, 10), intArrayOf(12, 1),
        intArrayOf(15, 9), intArrayOf(17, 3), intArrayOf(20, 7), intArrayOf(22, 5), intArrayOf(24, 8),
        intArrayOf(27, 6), intArrayOf(30, 3), intArrayOf(33, 9), intArrayOf(35, 1), intArrayOf(37, 5),
        intArrayOf(40, 7), intArrayOf(42, 2), intArrayOf(45, 4), intArrayOf(48, 8), intArrayOf(50, 6),
        intArrayOf(53, 5), intArrayOf(55, 9), intArrayOf(58, 1), intArrayOf(60, 4), intArrayOf(62, 7),
        intArrayOf(65, 3), intArrayOf(67, 8), intArrayOf(70, 2), intArrayOf(72, 6), intArrayOf(74, 9),
        intArrayOf(77, 5), intArrayOf(80, 1), intArrayOf(82, 8), intArrayOf(85, 4), intArrayOf(88, 7),
        intArrayOf(90, 2), intArrayOf(92, 5), intArrayOf(95, 1), intArrayOf(97, 9), intArrayOf(100, 3),
        intArrayOf(103, 6), intArrayOf(105, 8), intArrayOf(108, 2), intArrayOf(110, 5), intArrayOf(112, 1),
        intArrayOf(115, 4), intArrayOf(117, 9), intArrayOf(120, 7), intArrayOf(123, 3), intArrayOf(125, 8)
    )


    //  6. 所有点的横坐标都相同，纵坐标不同：

    val test11 = arrayOf(intArrayOf(3, 2), intArrayOf(3, 7), intArrayOf(3, 5)) // 预期结果为 5

    // 7. 所有点的纵坐标都相同，横坐标不同：

    val test12 =
        arrayOf(intArrayOf(1, 5), intArrayOf(5, 5), intArrayOf(7, 5), intArrayOf(3, 5), intArrayOf(9, 5)) // 预期结果为 8

    //  8. 所有点都在同一条直线上，但不是水平或垂直线：

    val test13 =
        arrayOf(intArrayOf(1, 2), intArrayOf(2, 4), intArrayOf(3, 6), intArrayOf(4, 8), intArrayOf(5, 10)) // 预期结果为 4

    //  9. 只有两个点，横坐标和纵坐标都不同：

    val test14 = arrayOf(intArrayOf(1, 2), intArrayOf(3, 4)) // 预期结果为 2

    // 10. 只有两个点，横坐标或纵坐标相同：

    val test15 = arrayOf(intArrayOf(1, 2), intArrayOf(3, 2)) // 预期结果为 2
    val test16 = arrayOf(intArrayOf(1, 2), intArrayOf(1, 4)) // 预期结果为 0


    val testCases = arrayOf(
        test1,
        test2,
        test3,
        test4,
        test5,
        test6,
        test7,
        test8,
        test9,
        test10,
        test11,
        test12,
        test13,
        test14,
        test15,
        test16
    )
    val expected = arrayOf(0, 4, 1, 5, 3, 2, 3, 2, 7, 3, 0, 2, 1, 2, 2, 0)

    val obj = WidestVerticalAreaBetweenTwoPointsContainsNoPoints()
    testCases.forEachIndexed { index, value ->
        val res = obj.maxWidthOfVerticalArea(value)
        print("$res,")
        if (res == expected[index]) {
            println("$index ok")
        } else {
            println("$index error, expected ${expected[index]} but found $res")
        }
    }
}