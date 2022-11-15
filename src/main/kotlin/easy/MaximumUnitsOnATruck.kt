package easy

/**
 * @author : jixiaoyong
 * @description ： 1710. 卡车上的最大单元数
 * 请你将一些箱子装在 一辆卡车 上。给你一个二维数组 boxTypes ，其中 boxTypes[i] = [numberOfBoxesi, numberOfUnitsPerBoxi] ：
 *
 * numberOfBoxesi 是类型 i 的箱子的数量。
 * numberOfUnitsPerBoxi 是类型 i每个箱子可以装载的单元数量。
 * 整数 truckSize 表示卡车上可以装载 箱子 的 最大数量 。只要箱子数量不超过 truckSize ，你就可以选择任意箱子装到卡车上。
 *
 * 返回卡车可以装载单元 的 最大 总数。
 *
 * 提示：
 *
 * 1 <= boxTypes.length <= 1000
 * 1 <= numberOfBoxesi, numberOfUnitsPerBoxi <= 1000
 * 1 <= truckSize <= 10^6
 *
 * https://leetcode.cn/problems/maximum-units-on-a-truck/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/15/2022
 */
class MaximumUnitsOnATruck {

    /**
     * 先按照每个units的大小排序，然后按照从大到小，依次取箱子并计算最大数量，直到达到[truckSize]或者用完了所有的箱子
     * 时间复杂度：O(nlogn)，N是boxTypes长度，排序耗时为O(nlogn)
     * 空间复杂度：O(logn)，排序需要O(logn)空间
     */
    fun maximumUnits(boxTypes: Array<IntArray>, truckSize: Int): Int {
        boxTypes.sortBy {
            it[1]
        }
        var size = 0
        var totalSize = 0
        var curIndex = boxTypes.size - 1
        while (size < truckSize && curIndex >= 0) {
            var curBoxSize = boxTypes[curIndex][0]
            val curBoxUnits = boxTypes[curIndex][1]

            while (curBoxSize-- > 0 && size < truckSize) {
                totalSize += curBoxUnits
                size++
            }

            curIndex--
        }

        return totalSize
    }
}

fun main() {
    val obj = MaximumUnitsOnATruck()
    val cases = mutableListOf(
        Pair(arrayOf(intArrayOf(1, 3), intArrayOf(2, 2), intArrayOf(3, 1)), 4),
        Pair(arrayOf(intArrayOf(1, 3), intArrayOf(2, 2), intArrayOf(3, 1)), 1000000),
        Pair(arrayOf(intArrayOf(5, 10), intArrayOf(2, 5), intArrayOf(4, 7), intArrayOf(3, 9)), 10),
    )

    cases.forEach {
        val result = obj.maximumUnits(it.first, it.second)
        println("result:$result")

    }
}