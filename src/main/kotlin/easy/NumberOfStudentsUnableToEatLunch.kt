package easy

import java.util.Arrays

/**
 * @author : jixiaoyong
 * @description ： 1700. 无法吃午餐的学生数量
 * 学校的自助午餐提供圆形和方形的三明治，分别用数字 0 和 1 表示。所有学生站在一个队列里，每个学生要么喜欢圆形的要么喜欢方形的。
 * 餐厅里三明治的数量与学生的数量相同。所有三明治都放在一个 栈 里，每一轮：
 * 如果队列最前面的学生 喜欢 栈顶的三明治，那么会 拿走它 并离开队列。
 * 否则，这名学生会 放弃这个三明治 并回到队列的尾部。
 *
 * 这个过程会一直持续到队列里所有学生都不喜欢栈顶的三明治为止。
 *
 * 给你两个整数数组 students 和 sandwiches ，其中 sandwiches[i] 是栈里面第 i 个三明治的类型（i = 0 是栈的顶部）
 * students[j] 是初始队列里第 j 名学生对三明治的喜好（j = 0 是队列的最开始位置）。
 *
 * 请你返回无法吃午餐的学生数量。
 *
 * 提示：
 * 1 <= students.length, sandwiches.length <= 100
 * students.length == sandwiches.length
 * sandwiches[i]要么是0，要么是1。
 * students[i]要么是0，要么是1。
 *
 * https://leetcode.cn/problems/number-of-students-unable-to-eat-lunch
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/19/2022
 */
class NumberOfStudentsUnableToEatLunch {

    /**
     * 285 ms	36.8 MB
     * 思路是：不断的对比students和sandwiches，如果第一个学生喜欢三明治，则移除这个学生和三明治，否则指针指向下一个学生
     * 这样不断对比直到没有学生或者剩下所有学生都不喜欢第一个三明治
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    fun countStudents(students: IntArray, sandwiches: IntArray): Int {
        val students = students.toMutableList()
        val sandwiches = sandwiches.toMutableList()
        var stuIndex = 0
        val sanIndex = 0
        while (true) {
            if (students.size == 0 || students.indexOf(sandwiches[sanIndex]) == -1) {
                break
            }

            if (students[stuIndex] == sandwiches[sanIndex]) {
                students.removeAt(stuIndex)
                sandwiches.removeAt(sanIndex)
            } else {
                stuIndex++
            }

            if (stuIndex >= students.size) {
                stuIndex = 0
            }

        }

        return students.size
    }

    /**
     * 269 ms	34.8 MB
     * https://leetcode.cn/problems/number-of-students-unable-to-eat-lunch/solution/wu-fa-chi-wu-can-de-xue-sheng-shu-liang-fv3f5/
     * 来自官方题解的思路，学生的顺序不影响最终结果（学生会在不断排到队伍后方的过程打乱顺序），实际能吃到与否只与学生中喜欢方的或圆的数量，
     * 以及当前第一个三明治是方的或圆有关，如果当前没有学生喜欢第一个三明治则终止
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    fun countStudents2(students: IntArray, sandwiches: IntArray): Int {
        var num1 = Arrays.stream(students).sum()
        var num0 = students.size - num1

        for (x in 0 until sandwiches.size) {
            val sandwich = sandwiches[x]
            if (sandwich == 0 && num0 > 0) {
                num0--
            } else if (sandwich == 1 && num1 > 0) {
                num1--
            } else {
                // 当前的sandwich没有对应的学生喜欢吃
                break
            }
        }

        return num0 + num1
    }
}

fun main() {
    val obj = NumberOfStudentsUnableToEatLunch()
    val stuAndSanList = listOf(
        Pair(intArrayOf(1, 1, 1, 0, 0, 1), intArrayOf(1, 0, 0, 0, 1, 1)),
        Pair(intArrayOf(1), intArrayOf(1)),
        Pair(intArrayOf(1), intArrayOf(0)),
        Pair(intArrayOf(1, 1, 0, 0), intArrayOf(0, 1, 0, 1)),
    )

    stuAndSanList.forEach {
        println(
            "${
                obj.countStudents2(
                    it.first,
                    it.second
                )
            } ---> ${it.first.joinToString()}--${it.second.joinToString()}"
        )
    }
}