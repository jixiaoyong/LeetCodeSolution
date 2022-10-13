package base

/**
 * @author : jixiaoyong
 * @description ： 动态规划算法
 * 参考：
 * 什么是动态规划（Dynamic Programming）？动态规划的意义是什么？ - 阮行止的回答 - 知乎
 * https://www.zhihu.com/question/23995189/answer/613096905
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/13/2022
 */
class DynamicProgramming {

    /**
     * 动态规划算法将复杂问题分解为一个个小问题，然后再求解
     * 比如使用最少的面值为1，5，11货币组合出指定面值：
     * 假设要凑齐15元，则只需要计算出先使用1、5、11面额中使用货币最少的个数再加1即可，可以表示为：
     * f(15) = min{ f(n-1),f(n-5),f(n-11) } + 1
     *
     */
    fun getMoney(amount: Int): Int {
        if (amount == 0) {
            return 0
        }

        if (amount < 0) {
            return Int.MAX_VALUE
        }

        if (amount == 1 || amount == 5 || amount == 11) {
            return 1
        }

        val a1 = getMoney(amount - 1)
        val a5 = getMoney(amount - 5)
        val a11 = getMoney(amount - 11)

        val minimum = Math.min(Math.min(a1, a5), a11)

        return minimum + 1
    }


    /**
     * 求最长递增子序列
     * 思路：要计算数组array中i位置的最长递增子序列，只需要找到0~i-1位置中比array[i]小的、递增序列最长的值array[j]的递增序列长度并+1即可
     */
    fun longestIncreaseSubsequence(array: IntArray): Int {
        if (array.isEmpty()) {
            return 0
        }

        val f = IntArray(array.size) { index ->
            1
        }
        var max = 1
        for (i in 0 until array.size) {
            for (j in 0 until i) {
                if (array[j] < array[i]) {
                    f[i] = Math.max(f[i], f[j] + 1)
                    max = Math.max(max, f[i])
                }
            }
        }

        return max
    }

    /**
     * 青蛙一次可以跳1步或2步，现在青蛙在n台阶，请问青蛙可能有几种方式跳上来
     * 递归，耗时
     */
    fun frogStepCount(step: Int): Int {

        if (step <= 0) {
            return 0
        } else if (step == 1) {
            return 1
        } else if (step == 2) {
            return 2
        }

        return frogStepCount(step - 1) + frogStepCount(step - 2)
    }

    /**
     * 从最底层比如台阶1，2开始计算，耗时短
     * 比如在45台阶，计算得出1836311903种，耗时1ms，而frogStepCount耗时3204ms
     */
    fun frogStepCountTraverse(step: Int): Int {
        val stepArray = IntArray(step)
        stepArray[0] = 1
        stepArray[1] = 2

        if (step <= 2) {
            return stepArray[step - 1]
        }

        for (x in 2 until step) {
            stepArray[x] = stepArray[x - 1] + stepArray[x - 2]
        }

        return stepArray[step - 1]
    }
}

fun main() {
//    println("money mini count:${DynamicProgramming().getMoney(15)}")
//    println(
//        "longestIncreaseSubsequence:${
//            DynamicProgramming().longestIncreaseSubsequence(
//                intArrayOf(
//                    1,
//                    5,
//                    3,
//                    4,
//                    6,
//                    9,
//                    7,
//                    8
//                )
//            )
//        }"
//    )

    var time = System.currentTimeMillis()
    println(DynamicProgramming().frogStepCountTraverse(45))
    println("frogStepCountTraverse time:${System.currentTimeMillis() - time}ms")
    time = System.currentTimeMillis()
    println(DynamicProgramming().frogStepCount(45))
    println("frogStepCount time:${System.currentTimeMillis() - time}ms")
}