package easy

/**
 * @author : jixiaoyong
 * @description ：118. 杨辉三角
 * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
 *
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 *
 * 提示:
 *
 * 1 <= numRows <= 30
 *
 * https://leetcode.cn/problems/pascals-triangle/
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/5/2022
 */
class PascalsTriangle {
    fun generate(numRows: Int): List<List<Int>> {
        val result: List<MutableList<Int>> = List(numRows) { MutableList(it + 1) { 1 } }
        for (i in 2 until numRows) {
            val pre = result[i - 1]
            val cur = result[i]
            for (j in 1 until i) {
                cur[j] = pre[j - 1] + pre[j]
            }
        }
        return result
    }
}

fun main() {

    val obj = PascalsTriangle()
    val cases = listOf(1, 2, 3, 4, 5, 6, 10, 20, 30)

    for (case in cases) {
        println("${obj.generate(case)}  ---> $case")
    }
}