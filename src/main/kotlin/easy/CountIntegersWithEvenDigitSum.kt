package easy

/**
 * @author : jixiaoyong
 * @description ： 2180. 统计各位数字之和为偶数的整数个数
 * 给你一个正整数 num ，请你统计并返回 小于或等于 num 且各位数字之和为 偶数 的正整数的数目。
 *
 * 正整数的 各位数字之和 是其所有位上的对应数字相加的结果。
 *
 * https://leetcode.cn/problems/count-integers-with-even-digit-sum/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 1/6/2023
 */
class CountIntegersWithEvenDigitSum {


    /**
     * 依次遍历每个数字，然后将其各位数字之和相加，判断是否为偶数
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     */
    fun countEven(num: Int): Int {
        var res = 0
        for (i in 2..num) {
           val  sum = i / 10 + i / 100 + i / 1000 + i % 10
            if (sum % 2 == 0) {
                res++
            }
        }
        return res
    }
}

fun main() {
    val obj = CountIntegersWithEvenDigitSum()
    val cases = mutableListOf(1, 2, 4, 30, 40, 1000)
    cases.forEach {
        println("${obj.countEven(it)}   ---> $it")
    }
}