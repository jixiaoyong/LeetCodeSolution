package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1812. 判断国际象棋棋盘中一个格子的颜色
 *
 * 给你一个坐标 coordinates ，它是一个字符串，表示国际象棋棋盘中一个格子的坐标。下图是国际象棋棋盘示意图。
 * 如果所给格子的颜色是白色，请你返回true，如果是黑色，请返回false。
 *
 * 给定坐标一定代表国际象棋棋盘上一个存在的格子。坐标第一个字符是字母，第二个字符是数字。
 *
 * 提示：
 *
 * coordinates.length == 2
 * 'a' <= coordinates[0] <= 'h'
 * '1' <= coordinates[1] <= '8'
 *
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/8/2022
 */
class DetermineColorOfAChessboardSquare {

    /**
     * 思路，观察棋盘，如果同为奇数或者同为偶数则为黑色，否则为白色
     * 时间复杂度O(1)
     * 空间复杂度O(1)
     */
    fun squareIsWhite(coordinates: String): Boolean {
        val isFirstOdd = (coordinates[0] - 'a' + 1) % 2 == 0
        val isSecondOdd = (coordinates[1] - '1' + 1) % 2 == 0
        return isFirstOdd xor isSecondOdd
    }
}

fun main() {
    val obj = DetermineColorOfAChessboardSquare()
    val cases = mutableListOf("a1", "h3", "c7", "c8", "c2")

    for (case in cases) {
        println("${obj.squareIsWhite(case)}   -> $case")
    }
}