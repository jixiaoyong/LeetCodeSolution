package easy

/**
 * @author : jixiaoyong
 * @description ：2418. 按身高排序
 * 给你一个字符串数组 names ，和一个由 互不相同 的正整数组成的数组 heights 。两个数组的长度均为 n 。
 *
 * 对于每个下标 i，names[i] 和 heights[i] 表示第 i 个人的名字和身高。
 *
 * 请按身高 降序 顺序返回对应的名字数组 names 。
 *
 * 提示：
 *
 * n == names.length == heights.length
 * 1 <= n <= 10^3
 * 1 <= names[i].length <= 20
 * 1 <= heights[i] <= 10^5
 * names[i] 由大小写英文字母组成
 * heights 中的所有值互不相同
 *
 * https://leetcode.cn/problems/sort-the-people/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/4/25
 */
class SortThePeople {
    fun sortPeople(names: Array<String>, heights: IntArray): Array<String> {
        val peopleMap = HashMap<Int, String>(heights.size)
        heights.forEachIndexed { index, height ->
            peopleMap.put(height, names[index])
        }

        popSort(heights)

        return heights.map { peopleMap.get(it)!! }.toTypedArray()
    }

    fun popSort(array: IntArray) {

        for (i in array.size - 1 downTo 0) {
            var min = array[i]
            var index = i
            for (j in i - 1 downTo 0) {
                if (array[j] < min) {
                    min = array[j]
                    index = j
                }
            }
            array[index] = array[i]
            array[i] = min
        }
    }

}

fun main() {
    val obj = SortThePeople()
    val cases = arrayListOf(
        Pair(arrayOf("Mary", "John", "Emma"), intArrayOf(180, 165, 170)),
        Pair(arrayOf("Alice", "Bob", "Bob"), intArrayOf(155, 185, 150)),
        Pair(arrayOf("Mary"), intArrayOf(180)),
        Pair(arrayOf("Mary", "John", "Emma"), intArrayOf(180, 165, 170)),
        Pair(arrayOf("Mary", "John", "Emma"), intArrayOf(170, 165, 180)),
        Pair(arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
        Pair(arrayOf("J", "I", "H", "G", "F", "E", "D", "C", "B", "A"), intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)),
        Pair(arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)),
        Pair(arrayOf("J", "I", "H", "G", "F", "E", "D", "C", "B", "A"), intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
        Pair(arrayOf("Mary", "John", "Emma"), intArrayOf(100000, 50000, 75000)),
        Pair(arrayOf("Mary", "John", "Emma"), intArrayOf(1, 2, 3)),
        Pair(arrayOf("Mary", "John", "Emma"), intArrayOf(50000, 100000, 75000))
    )

    cases.forEach {
        val res = obj.sortPeople(it.first, it.second)
        println("${res.joinToString()} --- ${it.first.joinToString()}   ${it.second.joinToString()}")
    }
}