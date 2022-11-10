package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 1047. 删除字符串中的所有相邻重复项
 * 给出由小写字母组成的字符串S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 *
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 *
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 *
 * 提示：
 *
 * 1 <= S.length <= 20000
 * S 仅由小写英文字母组成。
 *
 * https://leetcode.cn/problems/remove-all-adjacent-duplicates-in-string/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/10/2022
 */
class RemoveAllAdjacentDuplicatesInString {

    /**
     * 	572 ms	36.9 MB
     * 	双指针，前后两个指针分别向后遍历
     * 	遇到前后两个指针指向的数字相同时，移出对应的两个数字（耗时）、并将其统一后移
     */
    fun removeDuplicates(s: String): String {
        if (s.length <= 1) {
            return s
        }

        val strList = s.toMutableList()
        var pre = 0
        var cur = 1
        while (true) {
            if (cur >= strList.size) {
                break
            }

            if (strList[cur] == strList[pre]) {
                strList.removeAt(cur--)
                strList.removeAt(pre--)
            } else {
                cur++
                pre++
            }

            if (pre < 0) {
                pre = 0
            }

            if (cur < 1) {
                cur = 1
            }

        }
        return strList.joinToString("")
    }

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     * 来自LeetCode用户liyinze1解法
     * https://leetcode.cn/problems/remove-all-adjacent-duplicates-in-string/solution/shan-chu-zi-fu-chuan-zhong-de-suo-you-xi-4ohr/
     */
    fun removeDuplicatesTop(s: String): String {
        var top = -1
        val chars = s.toCharArray()
        for (i in 0 until s.length) {
            if (top == -1 || chars[top] != chars[i]) {
                chars[++top] = chars[i]
            } else {
                top--
            }
        }
        return String(chars, 0, top + 1)
    }
}

fun main() {
    val obj = RemoveAllAdjacentDuplicatesInString()
    val random = Random(System.currentTimeMillis())

    val cases = mutableListOf("abbaca", "a", "aaaaaa", "aaaaa", "aa", "aab")

    val largestStr = StringBuffer()
    for (x in 0 until 19999) {
        largestStr.append(random.nextInt('a'.code, 'z'.code + 1).toChar())
    }

    cases.add(largestStr.toString())

    for (case in cases) {
        println("${obj.removeDuplicatesTop(case)}   ---> $case")
        println("${obj.removeDuplicates(case)}   ---> $case")
    }

}