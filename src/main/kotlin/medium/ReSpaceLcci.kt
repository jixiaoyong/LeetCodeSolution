package medium

/**
 * @author : jixiaoyong
 * @description ： 面试题 17.13. 恢复空格
 * 哦，不！你不小心把一个长篇文章中的空格、标点都删掉了，并且大写也弄成了小写。
 * 像句子"I reset the computer. It still didn’t boot!"已经变成了"iresetthecomputeritstilldidntboot"。
 * 在处理标点符号和大小写之前，你得先把它断成词语。当然了，你有一本厚厚的词典dictionary，不过，有些词没在词典里。
 * 假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。
 *
 * 注意：本题相对原题稍作改动，只需返回未识别的字符数
 *
 * 提示：
 *
 * 0 <= len(sentence) <= 1000
 * dictionary中总字符数不超过 150000。
 * 你可以认为dictionary和sentence中只包含小写字母。
 *
 * https://leetcode.cn/problems/re-space-lcci
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/9/2022
 */
class ReSpaceLcci {

    fun respace(dictionary: Array<String>, sentence: String): Int {
      val  hashSet = dictionary.toHashSet()
      val  result = sentence.length



        return result
    }


}

fun main() {
    val obj = ReSpaceLcci()
    val cases = mutableListOf(
        Pair(arrayOf("looked", "just", "like", "her", "brother"), "jesslookedjustliketimherbrother"),
        Pair(arrayOf("looked", "jess", "je", "her", "brother"), "jesslookedjustliketimherbrother"),
    )

    cases.forEach {
        println("result:${obj.respace(it.first, it.second)}")
    }
}