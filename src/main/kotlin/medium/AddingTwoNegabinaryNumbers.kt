package medium


/**
 * @author : jixiaoyong
 * @description ：1073. 负二进制数相加
 * 给出基数为 -2的两个数arr1 和arr2，返回两数相加的结果。
 *
 * 数字以数组形式给出：数组由若干 0 和 1 组成，按最高有效位到最低有效位的顺序排列。
 * 例如，arr= [1,1,0,1]表示数字(-2)^3+ (-2)^2 + (-2)^0 = -3。
 * 数组形式中的数字 arr 也同样不含前导零：即arr == [0]或arr[0] == 1。
 *
 * 返回相同表示形式的 arr1 和 arr2 相加的结果。两数的表示形式为：不含前导零、由若干 0 和 1 组成的数组。
 *
 * 例如：
 * 输入：arr1 = [1,1,1,1,1], arr2 = [1,0,1]
 * 输出：[1,0,0,0,0]
 * 解释：arr1 表示 11，arr2 表示 5，输出表示 16 。
 *
 *
 * 提示：
 *
 * 1 <= arr1.length,arr2.length <= 1000
 * arr1[i]和arr2[i]都是0或1
 * arr1和arr2都没有前导0
 *
 * https://leetcode.cn/problems/adding-two-negabinary-numbers
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/5/18
 */
class AddingTwoNegabinaryNumbers {

    /**
     * 此题关键在考虑清楚进制转化的计算
     * 可参考文章：https://blog.csdn.net/qq_41219157/article/details/124558787
     * 简要思路：
     * 对于以-2进制表示的数字n来说，他的每一位上的数字可能为-1、0、1（如果为-2则进1位，为2则减1位）。
     * 对于这个数（10进制的4表示为100，从左到右依次是1，0，0，假设数字a=(-2)^b+c表示）
     * 上面的每一位数c，他的左侧和右侧位数字都和他的符号相反，比如从个位数开始(-2)^b的值分别是，1，-2，4，-8，16……
     * 【低位相当于高位来说永远是负数，而低位的进位贡献，相当于高位来说自然是一个负贡献，因此向高位的进位就是减1】
     * 所以，如果遇到1+1=2 的情况，就是将当前为置为0，令高位-1
     * 以-2进制的11（10进制的-1）+ -2进制的1（10进制的1）来说
     *   1  1
     * + 0  1
     * ------
     *   1  0
     *  -1  0
     *  -----
     *   0  0
     *   结果为0
     *
     * 而如果遇到高位为0，低位进位-1之后，形成了-1+0=-1的情况，又题目要求，只能用0，1表示，那么令此时高位-1为对应的(-2)^b的值为n的话
     * 那么，令高位的高位+1，再将高位+1，就相当于原先的数字+多了两个n的负数+一个当前高位对应n= -2*n+n = -n 从而实现了高位为-1的效果
     * 对于满足题设的范围两个-2进制相加，-2进制各个位上的数字(0,1)可能有一下情况：
     * 1） 0或1 ---> 维持原样
     * 2） 2 ---> 高位-1，当前为置为0
     * 3） 3---> 高位-1，当前为置为1
     * 4）-1---> -1---> 当前位置为1，高位置+1
     */
    fun addNegabinary(arr1: IntArray, arr2: IntArray): IntArray {
        val len1 = arr1.size
        val len2 = arr2.size
        val maxLen = Math.max(arr1.size, arr2.size) + 2
        val result = MutableList(maxLen) { 0 }

        // 对于满足题设的范围两个 - 2 进制相加 ， - 2 进制各个位上的数字 (0, 1)可能有一下情况：
        // 1） 0或1---> 维持原样
        // 2） 2---> 高位-1，当前为置为0
        // 3） 3---> 高位-1，当前为置为1
        // 4）-1---> 当前位置为1，高位置+1
        for (i in maxLen - 1 downTo 2) {
            val step = maxLen - i
            val sum = arr1.getOrElse(len1 - step) { 0 } + arr2.getOrElse(len2 - step) { 0 }  + result[i]

            result[i] = when (sum) {
                0, 1 -> sum
                2 -> {
                    result[i - 1] += -1
                    0
                }

                3 -> {
                    result[i - 1] += -1
                    1
                }

                else -> {
                    // -1的情况
                    // 将高位+1
                    result[i - 1] += 1
                    1
                }
            }
        }

        // 处理前置进位的情况
        if(result[1]==-1){
            result[0]=1
            result[1]=1
        }

        // 移除前导0
        while (result.size > 1) {
            if (result.first() == 0) {
                result.removeAt(0)
            } else {
                break
            }
        }

        return result.toIntArray()
    }
}

fun main() {
    val obj = AddingTwoNegabinaryNumbers()
    // 如何表示1+1=2的情况
    val cases = mutableListOf(
        Pair(intArrayOf(1, 0, 1), intArrayOf(1,0,1)),//[1,1,1,1,0]
        Pair(intArrayOf(1, 0, 1), intArrayOf(1)),
        Pair(intArrayOf(1, 1, 1, 1, 1), intArrayOf(1, 0, 1)),
        Pair(intArrayOf(0), intArrayOf(0)),
        Pair(intArrayOf(0), intArrayOf(1)),
        Pair(intArrayOf(1, 0), intArrayOf(1)),
        Pair(intArrayOf(1, 1, 1), intArrayOf(1, 1, 1)),
        Pair(intArrayOf(1, 1, 1), intArrayOf(1, 0, 1)),
        Pair(intArrayOf(1, 1, 1, 1), intArrayOf(1, 1, 1)),
        Pair(intArrayOf(1, 0, 1), intArrayOf(1, 0, 1)),
        Pair(intArrayOf(1, 0, 1), intArrayOf(1, 1, 1)),
        Pair(intArrayOf(1, 0, 1), intArrayOf(1, 1)),
        Pair(intArrayOf(1, 1), intArrayOf(1, 0, 1)),
        Pair(intArrayOf(1, 1, 1), intArrayOf(1))
    )
    cases.forEach {
        val res = obj.addNegabinary(it.first, it.second)
        println("${it.first.contentToString()} + ${it.second.contentToString()} = ${res.contentToString()}")
    }
}