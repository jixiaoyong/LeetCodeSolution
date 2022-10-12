package medium

import utils.Utils
import java.util.LinkedList

/**
 * @author : jixiaoyong
 * @description ： 200. 岛屿数量
 * 给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 *
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/11/2022
 */
class NumberOfIslands {


    /**
     * 832 ms	78.8 MB
     * 思路：每次对比当前节点、上方top和左边left相邻节点，如果是1则加入其中，记录当前节点所在的岛序号
     * 如果当前节点的top或left不在同一个岛，则合并两个岛，并且更新这些岛的序号
     */
    fun numIslands2(grid: Array<CharArray>): Int {

        val linkListArray = hashMapOf<String, LinkedList<Pair<Int, Int>>>()
        val nodeLinkKey = hashMapOf<Pair<Int, Int>, String>()

        for (x in 0 until grid.size) {
            val charArray = grid.get(x)
            for (j in 0 until charArray.size) {
                val node = charArray.get(j)
                if ('1' == node) {
                    val currentNode = Pair(x, j)
                    val top = Pair(x - 1, j)
                    val left = Pair(x, j - 1)

                    var hasIsland = false
                    val aroundNode = arrayOf(top, left)
                    aroundNode.forEach { around ->
                        if ('1' == grid.getOrNull(around.first)?.getOrNull(around.second)) {
                            val oldIslandsKey = nodeLinkKey.get(around)
                            val oldIsland = linkListArray.get(oldIslandsKey)

                            if (hasIsland) {
                                // 合并多个含有同一个node的列表
                                val exitIndex = nodeLinkKey.get(currentNode)
                                val exitIsland = linkListArray.get(exitIndex)
                                if (exitIndex != null && exitIsland != null && exitIndex != oldIslandsKey) {
                                    oldIsland?.let {
                                        exitIsland.addAll(it)
                                        linkListArray.put(exitIndex, exitIsland)
                                        // 更新被合并岛的节点岛序号，并移除当前岛
                                        it.forEach {
                                            nodeLinkKey.put(it,exitIndex)
                                        }
                                        linkListArray.remove(oldIslandsKey)
                                        hasIsland = true
                                    }
                                }
                            } else if (oldIsland != null) {
                                // 如果相邻节点有岛，则加入岛中
                                oldIsland.add(currentNode)
                                oldIslandsKey?.let { nodeLinkKey.put(currentNode, it) }
                                hasIsland = true
                            } else {
                                // 如果相邻节点没有岛，则新建岛
                                hasIsland = true
                                val linkList = LinkedList<Pair<Int, Int>>()
                                linkList.add(currentNode)
                                linkListArray.put(currentNode.toString(), linkList)
                                nodeLinkKey.put(currentNode, currentNode.toString())
                            }

                        }
                    }
                    if (!hasIsland) {
                        // 处理单个节点组成岛的情况：[[1]]
                        val linkList = LinkedList<Pair<Int, Int>>()
                        linkList.add(currentNode)
                        linkListArray.put(currentNode.toString(), linkList)
                        nodeLinkKey.put(currentNode, currentNode.toString())
                    }
                }
            }
        }

        return linkListArray.size
    }
}

fun main() {
    val grid = Utils.createCharMatrixFromString(
        """[
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
  ]"""
//        """[
// ["1","1","0","0","0"],
//  ["1","1","1","0","0"],
//  ["0","0","1","0","0"],
//  ["0","0","0","1","1"]
//  ]"""
//        """[
// ["0","0","1","0","0"]
//  ]"""
//        """[["1"]]"""
//
//        """[["1","1","1"],["0","1","0"],["1","1","1"]]"""
//        """[["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]"""
//        """[["1","1","1","1","1","0","1","1","1","1"],["1","0","1","0","1","1","1","1","1","1"],["0","1","1","1","0","1","1","1","1","1"],["1","1","0","1","1","0","0","0","0","1"],["1","0","1","0","1","0","0","1","0","1"],["1","0","0","1","1","1","0","1","0","0"],["0","0","1","0","0","1","1","1","1","0"],["1","0","1","1","1","0","0","1","1","1"],["1","1","1","1","1","1","1","1","0","1"],["1","0","1","1","1","1","1","1","1","0"]]"""

//        maxMatrixString
    )

    println(NumberOfIslands().numIslands2(grid))

}


