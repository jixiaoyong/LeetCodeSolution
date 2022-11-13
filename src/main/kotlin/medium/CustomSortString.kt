package medium

import kotlin.random.Random

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/11/13
 * description: 791. 自定义字符串排序
 * 给定两个字符串 order 和 s 。order 的所有单词都是 唯一 的，并且以前按照一些自定义的顺序排序。
 *
 * 对 s 的字符进行置换，使其与排序的order相匹配。更具体地说，如果在order中的字符 x 出现字符 y 之前，那么在排列后的字符串中， x也应该出现在 y 之前。
 *
 * 返回 满足这个性质的 s 的任意排列。
 *
 * 提示:
 *
 * 1 <= order.length <= 26
 * 1 <= s.length <= 200
 * order和s由小写英文字母组成
 * order中的所有字符都 不同
 *
 */
class CustomSortString {

    /**
     * 思路：使用HashMap保存每个char对应的顺序值，将未出现的char的值记为0，以此排序
     * 时间复杂度O(n^2+m),m是order中字符长度，最多为26，n是s的长度，时间复杂度主要取决于排序算法，此处使用冒泡，最坏为O(N^2)
     * 空间复杂度O(m+n)
     */
    fun customSortString(order: String, s: String): String {
        val hashMap = HashMap<Char, Int>(order.length)
        order.forEachIndexed { index, c ->
            hashMap.put(c, index + 1)
        }

        val result = s.toCharArray()

        // sort the char array
        var insertIndex = result.size - 1
        while (insertIndex > 0) {
            var curIndex = 0
            var maxIndex = curIndex++
            while (curIndex <= insertIndex) {
                if (compareChar(hashMap, result[curIndex], result[maxIndex]) > 0) {
                    maxIndex = curIndex
                }
                curIndex++
            }
            val temp = result[insertIndex]
            result[insertIndex] = result[maxIndex]
            result[maxIndex] = temp
            insertIndex--
        }

        return String(result)
    }

    // first > second -> 1
    // first < second -> -1
    // first == second -> 0
    private fun compareChar(hashMap: HashMap<Char, Int>, first: Char, second: Char): Int {
        val firstValue = hashMap.getOrDefault(first, 0)
        val secondValue = hashMap.getOrDefault(second, 0)
        return if (firstValue > secondValue) {
            1
        } else if (firstValue == secondValue) {
            0
        } else {
            -1
        }
    }


    /**
     * Leetcode官方题解
     * 计数排序，思路是计算每个字符出现的次数，然后按照order中的顺序依次填充结果，最后将未在order中出现的字符拼接即可
     * 时间复杂度O(m+n)，只需要分别遍历s和order即可
     * 空间复杂度O(m)，HashMap最多只需要order长度个空间即可,返回结果不计入空间复杂度
     */
    fun customSortStringLeetcode(order: String, s: String): String {
        val hashMap = HashMap<Char, Int>()
        s.forEach {
            val count = hashMap.getOrDefault(it, 0) + 1
            hashMap.put(it, count)
        }

        val result = CharArray(s.length)
        var curIndex = 0
        order.forEach {
            var count = hashMap.get(it) ?: 0
            while (count-- > 0) {
                result[curIndex++] = it
            }
            hashMap.remove(it)
        }

        hashMap.keys.forEach {
            var count = hashMap.get(it) ?: 0
            while (count-- > 0) {
                result[curIndex++] = it
            }
        }

        return String(result)
    }

}

fun main() {
    val obj = CustomSortString()
    val random = Random(System.currentTimeMillis())

    val cases = mutableListOf(
        Pair("cba", "abcd"),
        Pair("cbafg", "abcd"),
        Pair("ahdiepocn", "ahdiedpocana"),
        Pair("ahdiepocn", "ahdiedaidaedmdaddpocana"),
    )

    val largestString = StringBuffer()
    for (x in 0..199) {
        largestString.append(Char(random.nextInt('a'.code, 'z'.code + 1)))
    }
    cases.add(Pair("adsykoxzv", largestString.toString()))
    cases.add(Pair("qwertyuiopasdfghjklzxcvbnm", largestString.toString()))

    cases.forEach {
        val result = obj.customSortString(it.first, it.second)
        val result2 = obj.customSortStringLeetcode(it.first, it.second)
        println("$result|||$result2 --> ${it.first} -- ${it.second}")
    }
}