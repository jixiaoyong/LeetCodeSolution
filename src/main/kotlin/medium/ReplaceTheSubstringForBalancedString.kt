package medium

/**
 * @author : jixiaoyong
 * @description ：1234. 替换子串得到平衡字符串
 * 有一个只含有'Q', 'W', 'E','R'四种字符，且长度为 n的字符串。
 * 假如在该字符串中，这四个字符都恰好出现n/4次，那么它就是一个「平衡字符串」。
 *
 * 给你一个这样的字符串 s，请通过「替换一个子串」的方式，使原字符串 s 变成一个「平衡字符串」。
 * 你可以用和「待替换子串」长度相同的任何 其他字符串来完成替换。
 *
 * 请返回待替换子串的最小可能长度。
 * 如果原字符串自身就是一个平衡字符串，则返回 0。
 *
 * 注意：这里时求子串，也就是给定字符串中连续的一段子字符串
 *
 * https://leetcode.cn/problems/replace-the-substring-for-balanced-string
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/2/13
 */
class ReplaceTheSubstringForBalancedString {

    /**
     * 思路：使用right和left两个指针从左到右依次查找对应的子字符串，
     * 先移动right指针找到符合要求的子字符串，然后计算并记录起长度，然后将left指针向right指针靠近，以求得满足条件的最短子字符串长度
     * 重复上述过程直到遍历到字符串最后一个字符
     */
    fun balancedString(s: String): Int {
        val count = s.length
        val expectedCount = count / 4
        val charMap = HashMap<Char, Int>()

        for (c in s) {
            val currentCount = charMap.getOrPut(c) { 0 }
            charMap[c] = currentCount + 1
        }

        // 检测原始字符串是否为平衡字符串
        if (checkString(charMap, hashMapOf(), expectedCount)) {
            return 0
        }

        var result = Int.MAX_VALUE
        var left = 0
        var right = 0
        // 使用其记录当前子字符串中各个字符的个数，以尽量快的求得是否满足条件
        val subStringMap = HashMap<Char, Int>()
        subStringMap[s[0]] = 1
        // 先使用right查找满足条件的子字符串
        while (right <= count - 1) {
            if (checkString(charMap, subStringMap, expectedCount)) {
                result = Math.min(result, right - left + 1)
                // 然后使用left求得最短的子字符串长度
                while (left < right) {
                    val char = s[left++]
                    subStringMap[char] = subStringMap[char]!! - 1
                    if (checkString(charMap, subStringMap, expectedCount)) {
                        result = Math.min(result, right - left + 1)
                    } else {
                        left--
                        subStringMap[char] = subStringMap[char]!! + 1
                        break
                    }
                }

            }
            if (right + 1 <= count - 1) {
                val char = s[++right]
                subStringMap[char] = subStringMap.getOrDefault(char, 0) + 1
            } else {
                break
            }
        }

        return result
    }

    /**
     * 检测当前的子字符串是否满足条件
     */
    private fun checkString(
        charMap: HashMap<Char, Int>,
        subStringMap: HashMap<Char, Int>,
        expectedCount: Int
    ): Boolean {
        charMap.forEach { (t, u) ->
            if (u - subStringMap.getOrDefault(t, 0) > expectedCount) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val obj = ReplaceTheSubstringForBalancedString()
    val cases = arrayListOf(
        Pair("WWWEQRQEWWQQQWQQQWEWEEWRRRRRWWQE", 5),
        Pair("WQWRQQQW", 3),
        Pair("WWEQERQWQWWRWWERQWEQ", 4),
        Pair("QQWE", 1),
        Pair("QQQW", 2),
        Pair("QQQQ", 3),
        Pair("QQQQWEQQ", null),
        Pair("QWERQWER", 0),
        Pair("QWERQQER", 1),
        Pair("QQQWQQQWEREWWWWEQWEE", 5),
        Pair("QWER", 0),
    )

    for (case in cases) {
        val result = obj.balancedString(case.first)
        println("$result --${case.second}   ${case.first}")
    }
}