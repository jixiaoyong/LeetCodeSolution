package easy

/**
 * @author : jixiaoyong
 * @description ： 1704. 判断字符串的两半是否相似
 * 给你一个偶数长度的字符串 s 。将其拆分成长度相同的两半，前一半为 a ，后一半为 b 。
 *
 * 两个字符串 相似 的前提是它们都含有相同数目的元音（'a'，'e'，'i'，'o'，'u'，'A'，'E'，'I'，'O'，'U'）。
 * 注意，s 可能同时含有大写和小写字母。
 *
 * 如果 a 和 b 相似，返回 true ；否则，返回 false 。
 *
 * 提示：
 *
 * 2 <= s.length <= 1000
 * s.length 是偶数
 * s 由 大写和小写 字母组成
 *
 * https://leetcode.cn/problems/determine-if-string-halves-are-alike/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/11/2022
 */
class DetermineIfStringHalvesAreAlike {

    /**
     * 时间复杂度O(N) 需要遍历字符串长度
     * 空间复杂度O(1) 只需hashSet保存元音
     */
    fun halvesAreAlike(s: String): Boolean {
        val hashSet = hashSetOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
        var count1 = 0
        var count2 = 0
        for (i in 0 until s.length / 2) {
            if (hashSet.contains(s[i])) {
                count1++
            }
        }
        for (i in s.length / 2 until s.length) {
            if (hashSet.contains(s[i])) {
                count2++
            }
        }
        return count1 == count2
    }
}

fun main() {
    val obj = DetermineIfStringHalvesAreAlike()
    val cases = mutableListOf("book", "oO", "ds", "ox", "Booooookka")

    cases.forEach {
        println("${obj.halvesAreAlike(it)}   $it")
    }
}