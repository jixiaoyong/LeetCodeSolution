package medium

import java.util.LinkedList

/**
 * @author : jixiaoyong
 * @description ： 433. 最小基因变化
 * 基因序列可以表示为一条由 8 个字符组成的字符串，其中每个字符都是 'A'、'C'、'G' 和 'T' 之一。
 *
 * 假设我们需要调查从基因序列start 变为 end 所发生的基因变化。一次基因变化就意味着这个基因序列中的一个字符发生了变化。
 *
 * 例如，"AACCGGTT" --> "AACCGGTA" 就是一次基因变化。
 * 另有一个基因库 bank 记录了所有有效的基因变化，只有基因库中的基因才是有效的基因序列。（变化后的基因必须位于基因库 bank 中）
 *
 * 给你两个基因序列 start 和 end ，以及一个基因库 bank ，请你找出并返回能够使start 变化为 end 所需的最少变化次数。如果无法完成此基因变化，返回 -1 。
 *
 * 注意：起始基因序列start 默认是有效的，但是它并不一定会出现在基因库中。
 *
 * 提示：
 * start.length == 8
 * end.length == 8
 * 0 <= bank.length <= 10
 * bank[i].length == 8
 * start、end 和 bank[i] 仅由字符 ['A', 'C', 'G', 'T'] 组成
 *
 * https://leetcode.cn/problems/minimum-genetic-mutation/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/2/2022
 */
class MinimumGeneticMutation {

    /**
     * BFS
     * 思路：从start开始，有8*4=32中变异可能，选取其中在bank并且没有被访问的节点，并记录一次变异，记录使用过的变异下次不再使用
     * 然后再进行下一次变异，直到找到end或者试过了所有的节点
     * 时间复杂度：O(C*M*N)，C是基因数目，n为基因序列长度，M为bank长度，可能需要遍历完bank中所有的节点，每次遍历需计算C*N次
     * 空间复杂度：O(M*N)，最多存储M个元素，其大小为N
     */
    fun minMutation(start: String, end: String, bank: Array<String>): Int {
        val indexOfResult = bank.indexOf(end)
        if (indexOfResult == -1) {
            return indexOfResult
        }

        val bank = bank.toHashSet()
        val queue = LinkedList<Pair<Int, String>>()
        val visited = mutableSetOf<String>()
        queue.add(Pair(0, start))
        visited.add(start)

        while (queue.isNotEmpty()) {// 最多遍历次数与bank中item数目一致
            val cur = queue.removeFirst()
            if (cur.second == end) {
                return cur.first
            }

            // 对于每个可能的基因序列，选择其1~8位处的任一字符替换为ACGT四者之一
            for (i in 0 until cur.second.length) {
                for (c in "ACGT") {//
                    val next = cur.second.toCharArray()
                    next[i] = c
                    val nextStr = String(next)
                    if (bank.contains(nextStr) && !visited.contains(nextStr)) {
                        visited.add(nextStr)
                        queue.add(Pair(cur.first + 1, nextStr))
                    }
                }
            }
        }

        return -1
    }
}

fun main() {
    val obj = MinimumGeneticMutation()
    val cases = mutableListOf(
        Triple("AAAAACCC", "AACCCCCC", arrayOf("AAAAAACC","AAAAACCC", "AAAACCCC", "AAACCCCC", "AACCCCCC")),
        Triple("AAAAACGC", "AACCCCCT", arrayOf("AAAAAACC","AAAAACCC", "AAAACCCC", "AAACCCCC", "AACCCCCC")),
        Triple("AACCGGTT", "AACCGGTA", arrayOf("AACCGGTA")),
        Triple("AACCGGTT", "AAACGGTA", arrayOf("AACCGGTA", "AACCGCTA", "AAACGGTA")),
        Triple("AAAAACCC", "AACCCCCC", arrayOf("AAAACCCC", "AAACCCCC", "AACCCCCC")),
    )

    cases.forEach {
        val result = obj.minMutation(it.first, it.second, it.third)
        println("$result  -> ${it.first} / ${it.second} / bank:${it.third.joinToString()}")
    }
}