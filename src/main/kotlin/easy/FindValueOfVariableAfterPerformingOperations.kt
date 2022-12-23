package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 2011. 执行操作后的变量值
 * 存在一种仅支持 4 种操作和 1 个变量 X 的编程语言：
 *
 * ++X 和 X++ 使变量 X 的值 加 1
 * --X 和 X-- 使变量 X 的值 减 1
 * 最初，X 的值是 0
 *
 * 给你一个字符串数组 operations ，这是由操作组成的一个列表，返回执行所有操作后， X 的 最终值 。
 *
 * 提示：
 *
 * 1 <= operations.length <= 100
 * operations[i] 将会是 "++X"、"X++"、"--X" 或 "X--"
 *
 * https://leetcode.cn/problems/final-value-of-variable-after-performing-operations/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/23/2022
 */
class FindValueOfVariableAfterPerformingOperations {
    fun finalValueAfterOperations(operations: Array<String>): Int {
        val operationsMap = hashMapOf(Pair("++X", 1), Pair("X++", 1), Pair("--X", -1), Pair("X--", -1))
        var offset = 0
        operations.forEach {
            offset += operationsMap[it] ?: 0
        }
        return offset
    }
}

fun main() {
    val obj = FindValueOfVariableAfterPerformingOperations()
    val random = Random(System.currentTimeMillis())
    val cases = mutableListOf(
        arrayOf("--X", "X++", "X++"),
        arrayOf("++X", "++X", "X++"),
        arrayOf("X++", "++X", "--X", "X--")
    )

    val largestArr = Array(100) { "" }
    for (i in 0 until 100) {
        largestArr[i] = when (random.nextInt(1, 4)) {
            1 -> "X++"
            2 -> "X++"
            3 -> "X++"
            else -> "X++"
        }
    }
    cases.add(largestArr)

    for (case in cases) {
        println("${obj.finalValueAfterOperations(case)}   ---- ${case.joinToString {"\"$it\""  }}")
    }
}