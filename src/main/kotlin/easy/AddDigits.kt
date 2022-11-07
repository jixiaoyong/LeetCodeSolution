package easy

/**
 * @author : jixiaoyong
 * @description ： 258. 各位相加
 * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果。
 * 提示：
 * 0 <= num <= 2^31 - 1
 *
 * 进阶：你可以不使用循环或者递归，在 O(1) 时间复杂度内解决这个问题吗？
 *
 * https://leetcode.cn/problems/add-digits/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/7/2022
 */
class AddDigits {

    /**
     * 递归
     * 268 ms	34.1 MB
     * 时间复杂度：O(log num)
     * 空间复杂度：O(1)。
     */
    fun addDigits(num: Int): Int {
        if (num < 10) {
            return num
        }

        var sum = 0
        for (x in "$num") {
            sum += Integer.parseInt(x.toString())
        }
        return addDigits(sum)
    }

    /**
     * 数根
     * 一个数所有位的数字反复相加，直到其小于10，得到的数字。也就是这个数字对9取余的余数
     * 时间复杂度：O(1)。
     * 空间复杂度：O(1)。
     */
    fun addDigitsPlus(num: Int): Int {
        if (num == 0) {
            return 0
        }
        val r = num % 9
        return if (r > 0) r else 9
    }
}

fun main() {
    val obj = AddDigits()
    val cases = intArrayOf(0, 10, 1, 12, 27, 3, 34343, 342, 545, 9, 38, 1000, 100000032, 192, Int.MAX_VALUE)

    cases.forEach {
        println("${obj.addDigits(it)}  /  ${obj.addDigitsPlus(it)}    ---> $it")
    }
}