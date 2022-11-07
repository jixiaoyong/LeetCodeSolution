package medium

/**
 * @author : jixiaoyong
 * @description ：816. 模糊坐标
 * 我们有一些二维坐标，如"(1, 3)"或"(2, 0.5)"，然后我们移除所有逗号，小数点和空格，得到一个字符串S。返回所有可能的原始字符串到一个列表中。
 *
 * 原始的坐标表示法不会存在多余的零，所以不会出现类似于"00", "0.0", "0.00", "1.0", "001", "00.01"或一些其他更小的数来表示坐标。
 * 此外，一个小数点前至少存在一个数，所以也不会出现“.1”形式的数字。
 *
 * 最后返回的列表可以是任意顺序的。而且注意返回的两个数字中间（逗号之后）都有一个空格。
 *
 * 提示:
 *
 * 4 <= S.length <= 12.
 * S[0] = "(", S[S.length - 1] = ")", 且字符串 S 中的其他元素都是数字。
 *
 * https://leetcode.cn/problems/ambiguous-coordinates/
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/7/2022
 */
class AmbiguousCoordinates {

    /**
     * 	272 ms	37.1 MB
     * 	思路：先遍历逗号可能在的位置a，然后再遍历两个数字中小数点可能在的位置b、c（去除不满足条件的数字），那么可能的组合就是a*b*c种
     * 	时间复杂度O(N^3)
     * 	空间复杂度O(N^3)，存储结果的空间
     */
    fun ambiguousCoordinates(s: String): List<String> {
        val result = mutableListOf<String>()
        val sLen = s.length

        // 小数点、逗号在index对应数字后面，比如index==1，则逗号/小数点在1~2中间

        // 逗号所在的位置，这里从1到（length-1）-2，避免了开头的‘('和末尾的')'，以及【将数字分为前后两个】
        for (x in 1 until sLen - 2) {
            val first = s.substring(1, x + 1)
            val second = s.substring(x + 1, sLen - 1)

            // 小数点分为4种情况，下标在字符串length-1处则无小数点
            for (i in 0 until first.length) {
                if (!checkLegal(first, i)) {
                    continue
                }
                val firstResult = if (i == first.length - 1) first else {
                    val firstArr = first.toMutableList()
                    firstArr.add(i + 1, '.')
                    firstArr.joinToString("")
                }
                for (j in 0 until second.length) {
                    if (!checkLegal(second, j)) {
                        continue
                    }
                    val secondResult =
                        if (j == second.length - 1) second else {
                            val secondArr = second.toMutableList()
                            secondArr.add(j + 1, '.')
                            secondArr.joinToString("")
                        }

                    result.add("($firstResult, $secondResult)")
                }
            }
        }

        return result
    }

    private fun checkLegal(string: String, dot: Int): Boolean {
        if (string.length > 1) {
            if ('0' == string[0] && dot > 0) {
                // 00,01
                return false
            }
            if (string.map { Integer.parseInt(it.toString()) }.sum() == 0) {
                // 0.00,0.0
                return false
            }
            if ('0' == string[string.length - 1] && dot < string.length - 1) {
                // 1.0,1.00
                return false
            }
        }

        return true
    }
}

fun main() {
    val obj = AmbiguousCoordinates()
    val cases = listOf(
        "100", "00011",
        "00", "123", "0123", "010010", "010"
    )

    for (case in cases) {
        val result = obj.ambiguousCoordinates("($case)")
        println("$result --> $case")
    }
}