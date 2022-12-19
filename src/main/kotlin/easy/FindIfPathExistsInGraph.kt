package easy

import java.util.*

/**
 * @author : jixiaoyong
 * @description ： 1971. 寻找图中是否存在路径
 * 有一个具有 n 个顶点的 双向 图，其中每个顶点标记从 0 到 n - 1（包含 0 和 n - 1）。
 * 图中的边用一个二维整数数组 edges 表示，其中 edges[i] = [ui, vi] 表示顶点 ui 和顶点 vi 之间的双向边。
 * 每个顶点对由 最多一条 边连接，并且没有顶点存在与自身相连的边。
 *
 * 请你确定是否存在从顶点 source 开始，到顶点 destination 结束的 有效路径 。
 *
 * 给你数组 edges 和整数 n、source 和 destination，如果从 source 到 destination 存在 有效路径 ，则返回 true，否则返回 false 。
 *
 * 提示：
 *
 * 1 <= n <= 2 * 10^5
 * 0 <= edges.length <= 2 * 10^5
 * edges[i].length == 2
 * 0 <= ui, vi <= n - 1
 * ui != vi
 * 0 <= source, destination <= n - 1
 * 不存在重复边
 * 不存在指向顶点自身的边
 *
 * https://leetcode.cn/problems/find-if-path-exists-in-graph
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 12/19/2022
 */
class FindIfPathExistsInGraph {
    fun validPath(n: Int, edges: Array<IntArray>, source: Int, destination: Int): Boolean {
        // 创建一个链表，其中adj[i]表示i点指向的所有点集合
        val adj: MutableList<MutableList<Int>> = MutableList(n) { _ -> mutableListOf() }
        for (edge in edges) {
            val x = edge[0]
            val y = edge[1]
            adj[x].add(y)
            adj[y].add(x)
        }
        val visited = BooleanArray(n)
        val queue: LinkedList<Int> = LinkedList()
        queue.offer(source)
        visited[source] = true
        while (!queue.isEmpty()) {
            val vertex: Int = queue.poll()
            if (vertex == destination) {
                break
            }
            // 获取该点的下一点
            for (next in adj[vertex]) {
                // 如果没有访问该点，则访问
                if (!visited[next]) {
                    // 如果访问了该点，则将其标记为已访问，并将其下一点加入待访问
                    queue.offer(next)
                    visited[next] = true
                }
            }
        }
        return visited[destination]
    }
}

fun main() {
    val obj = FindIfPathExistsInGraph()
    val cases = mutableListOf(Pair("[0,1],[1,2],[2,0]", "3,0,2"), Pair("[0,1],[0,2],[3,5],[5,4],[4,3]", "6,0,5"))

    cases.forEach {
        val params1 = it.second.split(",").map { it.toInt() }
        val params0 = it.first.split("],[")
            .map { it.replace("[", "").replace("]", "").split(",").map { it.toInt() }.toIntArray() }.toTypedArray()
        val result = obj.validPath(params1[0], params0, params1[1], params1[2])
        println("$result  $it")
    }
}