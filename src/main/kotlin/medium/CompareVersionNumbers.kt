package medium

/**
 * @author : jixiaoyong
 * @description ： 165. 比较版本号
 * 给你两个版本号 version1 和 version2 ，请你比较它们。
 * 版本号由一个或多个修订号组成，各修订号由一个 '.' 连接。每个修订号由 多位数字 组成，可能包含 前导零 。每个版本号至少包含一个字符。
 * 修订号从左到右编号，下标从 0 开始，最左边的修订号下标为 0 ，下一个修订号下标为 1 ，以此类推。例如，2.5.33 和 0.1 都是有效的版本号。
 *
 * 比较版本号时，请按从左到右的顺序依次比较它们的修订号。比较修订号时，只需比较 忽略任何前导零后的整数值 。也就是说，修订号 1 和修订号 001 相等 。
 * 如果版本号没有指定某个下标处的修订号，则该修订号视为 0 。例如，版本 1.0 小于版本 1.1 ，因为它们下标为 0 的修订号相同，而下标为 1 的修订号
 * 分别为 0 和 1 ，0 < 1 。
 *
 * 返回规则如下：
 *
 * 如果 version1 > version2 返回 1，
 * 如果 version1 < version2 返回 -1，
 * 除此之外返回 0。
 *
 * 1 <= version1.length, version2.length <= 500
 * version1 和 version2 仅包含数字和 '.'
 * version1 和 version2 都是 有效版本号
 * version1 和 version2 的所有修订号都可以存储在 32 位整数 中
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/compare-version-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/11/2022
 */
class CompareVersionNumbers {
    // 	317 ms	36.1 MB
    // 时间复杂度、空间复杂度O(n+m)
    fun compareVersion(version1: String, version2: String): Int {
        val result = 0

        if (version1 == version2) {
            return result
        }

        val verArr1 = version1.split(".").map { it.toInt() }
        val verArr2 = version2.split(".").map { it.toInt() }

        val maxLen = Math.max(verArr1.size, verArr2.size)
        for (x in 0 until maxLen) {
            val ver1 = verArr1.getOrNull(x) ?: 0
            val ver2 = verArr2.getOrNull(x) ?: 0

            if (ver1 > ver2) {
                return 1
            } else if (ver2 > ver1) {
                return -1
            }

        }
        return result
    }
}

fun main() {
//    val versions = Pair("001", "1.0")
//    val versions = Pair("1", "1")
//    val versions = Pair("1.0.0.0.0", "1")
//    val versions = Pair("1.0.0.0.01", "001.1")
//    val versions = Pair("1.01", "1.001")
//    val versions = Pair("1.0", "1.0.0")
    val currentTimeMs = System.currentTimeMillis()
    val versions = Pair("0.1", "1.1")
    println("versions:${CompareVersionNumbers().compareVersion(versions.first, versions.second)}")
    println("time ms :${System.currentTimeMillis() - currentTimeMs}")
}