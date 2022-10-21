package medium

import java.util.LinkedList
import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 901. 股票价格跨度
 * 编写一个 StockSpanner 类，它收集某些股票的每日报价，并返回该股票当日价格的跨度。
 * 今天股票价格的跨度被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
 * 例如，如果未来7天股票的价格是 [100, 80, 60, 70, 60, 75, 85]，那么股票跨度将是 [1, 1, 1, 2, 1, 4, 6]。
 *
 * 提示：
 *
 * 调用StockSpanner.next(int price)时，将有1 <= price <= 10^5。
 * 每个测试用例最多可以调用 10000 次 StockSpanner.next。
 * 在所有测试用例中，最多调用150000次StockSpanner.next。
 * 此问题的总时间限制减少了 50%。
 *
 * https://leetcode.cn/problems/online-stock-span/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/21/2022
 */
class StockSpanner() {

    /**
     * 2446 ms	116.6 MB
     * 思路是，将每次的price存储到[list],然后每次插入新的price时，向后查询并计数返回
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    val list = mutableListOf<Int>()
    fun next(price: Int): Int {
        list.add(price)
        var count = 1
        if (list.size > 1) {
            for (x in list.size - 2 downTo 0) {
                if (price >= list[x]) {
                    count++
                } else {
                    break
                }
            }
            return count
        }
        return count
    }

    /**
     * LeetCode官方解法
     * 	1181 ms	115.8 MB
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     *
     * 和上述[next]优势在于以及计算过天数的不用重复计算
     *
     * 单调栈
     * 思路：以[100, 80, 60, 70, 60, 75, 85]为例分析
     * 每次对比之后，比当前价格小的内容在后续对比中无需再次对比。
     * 以上述70为例，60小于70，删除60，此时列表为[100（1）, 80（1）, 70（2）, 60, 75, 85]
     * 当在70之后再加入60，则【因为60<70，所以无需遍历前一个60】，此时列表为[100（1）, 80（1）, 70（2）, 60（1）, 75, 85]
     * 当75时（计数为1），第二个60被计入（计数为2），70被计入（计数为3），此时因为75>70，所以肯定也满足75>60，故而无需遍历第一个60，而只需
     * 加入70时的跨度即可，也就是在遍历到70时计数+2，因此，75的跨度=1+1+2=4,此时，此时列表为[100（1）, 80（1）, 75（4）, 85]
     * 当加入85时，最终列表为[100（1）,  85（6）]
     * https://leetcode.cn/problems/online-stock-span/solution/gu-piao-jie-ge-kua-du-by-leetcode-soluti-5cm7/
     */
    val stack = LinkedList(arrayListOf(Pair(Int.MAX_VALUE, -1)))

    fun nextStack(price: Int): Int {
        var count = 1
        var pre = stack.peekLast()
        while (price >= pre.first) {
            count += pre.second
            stack.pollLast()
            pre = stack.peekLast()
        }
        stack.add(Pair(price, count))
        return count
    }

}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * var obj = StockSpanner()
 * var param_1 = obj.next(price)
 */

fun main() {
    val obj = StockSpanner()
    val random = Random(System.currentTimeMillis())

    val prices = "[[],[100],[80],[60],[70],[60],[75],[85]]"

    val pricesList = mutableListOf(
        prices,
        "[[],[100],[20],[60],[70],[60],[75],[85]]",
        "[[],[100]",
    )

//    var veryLargeOperation = StringBuffer()
//    veryLargeOperation.append("[\"StockSpanner\"")
//    for (x in 0..9999) {
//        veryLargeOperation.append(",\"next\"")
//    }
//    veryLargeOperation.append("]")
//
//    println(veryLargeOperation)
//
//    var veryLargePrices = StringBuffer()
//    veryLargePrices.append("[")
//    for (x in 0..9999) {
//        veryLargePrices.append("[${random.nextInt(1, 100000)}]")
//        veryLargePrices.append(",")
//    }
//    veryLargePrices.append("]")
//
//    println(veryLargePrices.toString())
//
//    pricesList.add(veryLargePrices.toString())

    pricesList.forEach {
        obj.list.clear()
        obj.stack.clear()
        obj.stack.add(Pair(Int.MAX_VALUE, -1))
        val pricesArray = it.trim()
            .replace("[", "")
            .replace("]", "")
            .trim()
            .split(",")
            .filter { !it.isBlank() }
            .map {
                it.toInt()
            }
        println("\n${pricesArray.joinToString()}:")
        pricesArray.forEach {
            println("$it --> ${obj.nextStack(it)}")
        }
    }
}