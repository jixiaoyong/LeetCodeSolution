package easy

/**
 * @author : jixiaoyong
 * @description ：1773. 统计匹配检索规则的物品数量
 * 给你一个数组 items ，其中items[i] = [typei, colori, namei] ，描述第 i 件物品的类型、颜色以及名称。
 *
 * 另给你一条由两个字符串ruleKey 和 ruleValue 表示的检索规则。
 *
 * 如果第 i 件物品能满足下述条件之一，则认为该物品与给定的检索规则 匹配 ：
 *
 * ruleKey == "type" 且 ruleValue == typei 。
 * ruleKey == "color" 且 ruleValue == colori 。
 * ruleKey == "name" 且 ruleValue == namei 。
 * 统计并返回 匹配检索规则的物品数量 。
 *
 * https://leetcode.cn/problems/count-items-matching-a-rule/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/29/2022
 */
class CountItemsMatchingARule {
    fun countMatches(items: List<List<String>>, ruleKey: String, ruleValue: String): Int {
        var result = 0

        val index = when (ruleKey) {
            "type" -> 0
            "color" -> 1
            "name" -> 2
            else -> return result
        }

        items.forEach {
            if (it[index] == ruleValue) {
                result++
            }
        }

        return result
    }
}

fun main() {
    val obj = CountItemsMatchingARule()
    val cases = mutableListOf(
        Triple(
            listOf(
                listOf("phone", "blue", "pixel"),
                listOf("computer", "silver", "lenovo"),
                listOf("phone", "gold", "iphone"),
            ), "color", "silver"
        ),
        Triple(
            listOf(
                listOf("phone", "blue", "pixel"),
                listOf("computer", "silver", "phone"),
                listOf("phone", "gold", "iphone"),
            ), "type", "phone"
        ),
    )

    val veryLargeList = mutableListOf<List<String>>()
    for (x in 0 until 10000) {
        veryLargeList.add(listOf("phone", "blue", "pixel"))
        veryLargeList.add(listOf("computer", "silver", "phone"))
        veryLargeList.add(listOf("phone", "gold", "iphone"))
        veryLargeList.add(listOf("phone", "yellow", "pixel"))
        veryLargeList.add(listOf("phone", "blue", "byd"))
        veryLargeList.add(listOf("flower", "blue", "byd"))
        veryLargeList.add(listOf("computer", "blue", "google"))
        veryLargeList.add(listOf("hb", "blue", "google"))
        veryLargeList.add(listOf("computer", "white", "bird"))
        veryLargeList.add(listOf("hx", "blue", "google"))
    }

    cases.add(Triple(veryLargeList,"color","yellow"))


    cases.forEach {
        val result = obj.countMatches(it.first, it.second, it.third)
        println("$result --> ${it}")
    }
}