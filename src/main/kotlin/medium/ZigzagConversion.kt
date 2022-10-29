package medium

/**
 * @author : jixiaoyong
 * @description ： 6. Z 字形变换
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行Z 字形排列。
 *
 * 比如输入字符串为 "PAYPALISHIRING"行数为 3 时，排列如下：
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
 *
 * 请你实现这个将字符串进行指定行数变换的函数：
 *
 * string convert(string s, int numRows);
 *
 * 提示：
 *
 * 1 <= s.length <= 1000
 * s 由英文字母（小写和大写）、',' 和 '.' 组成
 * 1 <= numRows <= 1000
 *
 * https://leetcode.cn/problems/zigzag-conversion/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/29/2022
 */
class ZigzagConversion {

    /**
     * 	208 ms	35.2 MB
     * 时间复杂度O(N)，N为s的长度
     * 空间复杂度O(N)，需要StringBuffer存储计算结果，按照LeetCode的说法返回值不计入时间复杂度，那么这里为O(1)
     *
     * 思路是【找规律】：
     * 以s：PAYPALISHIRING，numRows：4为例，记numRows为n
     * 0 P      6 I      12 N
     * 1 A   5 L 7 S  11 I  13G
     * 2 Y 4 A   8 H 10 R
     * 3 P       9 I
     *
     * 以V字形状为一轮循环round（从0计数）,则每一轮的第一个节点top（比如上例的0P）坐标为（2*n -2）*round，
     * top的垂直底部bottom（比如上例的3P）坐标为top+n-1 = （2*n -2）*round + n-1
     * 每行节点index的下一个index按情况为：
     * 1. 如果index为top或者bottom，则下一个index = index + （2*n -2）
     * 2. 如果index在top和bottom之间，则下一个index = 2 * bottomIndex - index
     * 3. 否则下一个index = （下一个round的top的index - index）*2 + index
     *                  = （下一个round的top的index）*2 - index
     *                  = （2*n -2）* （round+1） - index
     * 按照这个规则，从0开始，一行一行的遍历，直到第numRows即可
     */
    fun convert(s: String, numRows: Int): String {
        val len = s.length
        if (len == 1 || numRows == 1 || len <= numRows) {
            return s
        }

        val stringBuffer = StringBuffer()
        for (i in 0 until numRows) {
            var index = i
            var round = 0
            // 遍历每一行的字符

            while (index < len) {
                // 在这里横向计算每一行可能的String下标
                val topIndex = (2 * numRows - 2) * round
                val bottomIndex = topIndex + numRows - 1
                stringBuffer.append(s[index])
                if (index == topIndex || index == bottomIndex) {
                    index += 2 * numRows - 2
                    round++
                } else if (index < bottomIndex) {
                    index = 2 * bottomIndex - index
                } else {
                    index = 2 * (2 * numRows - 2) * (round + 1) - index
                    round++
                }

            }
        }

        return stringBuffer.toString()
    }

    /***
     * 补充LeetCode官方的两个解法，思路都是构造一个二维数组（numRows*stringLength或者numRow*dynamicLen），然后先按照规则依次填充这个数组
     * 此后再依次遍历这个数组，拼接string得的结果
     * https://leetcode.cn/problems/zigzag-conversion/solution/z-zi-xing-bian-huan-by-leetcode-solution-4n3u/
     */
}

fun main() {
    val obj = ZigzagConversion()

    val cases = mutableListOf(
        Pair("PAYPALISHIRING", 3),
        Pair("PAYPALISHIRING", 4),
        Pair("PAYPALISHIRING", 6),
        Pair("PAYPALISHIRING", 1),
        Pair("PAYPALISHIRING", 40),
    )

    cases.forEach {
        val result = obj.convert(it.first, it.second)
        println("$result ---> ${it}")
    }
}