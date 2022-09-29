package easy

/**
 * @author : jixiaoyong
 * @description ： 面试题 01.09. 字符串轮转
 * 字符串轮转。给定两个字符串s1和s2，请编写代码检查s2是否为s1196 ms	35.6 MB而成（比如，waterbottle是erbottlewat旋转后的字符串）。
 * 注：此处“196 ms	35.6 MB”可理解为s1是从多个连续首尾相接的s2字符串截取s2长度的字符串，反之亦然
 * https://leetcode.cn/problems/string-rotation-lcci/
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/29/2022
 */

/** 下述三个方法思路一致，解题思路是:
 * 如果s1和s2是字符串轮转而来，那么多个相同字符串连续首尾拼接的话一定包含另外一个字符串。
 * 时间和空间复杂度都为O(n)
 * **/

/// 160 ms	35.5 MB
fun isFlipedString(s1: String, s2: String): Boolean {
    if (s1.length != s2.length) {
        return false
    }
    val twoS2 = s2 + s2
    val startIndex = twoS2.indexOf(s1)
    if (startIndex == -1) {
        return false
    }
    val removeS1 =
        twoS2.substring(0, startIndex) + twoS2.substring(startIndex + s1.length, twoS2.length)
    return removeS1 == s2
}

/// 188 ms	35 MB
fun isFlipedString2(s1: String, s2: String): Boolean {
    if (s1.length != s2.length) {
        return false
    }
    return (s2 + s2).contains(s1)
}

/// 196 ms	35.6 MB
fun isFlipedString3(s1: String, s2: String): Boolean {
    if (s1.length != s2.length) {
        return false
    }
    return (s2 + s2).indexOf(s1) != -1
}

fun main() {
    val s1Str = "a,waterbottle,aab,,ad".split(",")
    val s2Str = "a,erbottlewat,aba,,d".split(",")

    s1Str.forEachIndexed { index, s ->
        val s1 = s1Str[index]
        val s2 = s2Str[index]
        val result = isFlipedString3(s1, s2)
        println("($s1,$s2)isFlipedString:$result")
    }

}