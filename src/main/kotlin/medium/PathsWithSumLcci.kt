package medium

import utils.TreeNode
import utils.Utils


/**
 * @author : jixiaoyong
 * @description ： 面试题 04.12. 求和路径
 * 给定一棵二叉树，其中每个节点都含有一个整数数值(该值或正或负)。
 * 设计一个算法，打印节点数值总和等于某个给定值的所有路径的数量。
 * 注意，路径不一定非得从二叉树的根节点或叶节点开始或结束，但是其方向必须向下(只能从父节点指向子节点方向)。
 *
 * >>>>>>>此题只验证单向路径，比如root -> left/right，不验证双向路径，比如root -> left且root ->right的情况
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/paths-with-sum-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/30/2022
 */
class PathsWithSumLcci {

    fun pathSum(root: TreeNode?, sum: Int): Int {
        if (root == null) {
            return 0
        }

        var pathCount = 0

        pathCount += subTreeSum(root, sum)
        pathCount += pathSum(root.left, sum)
        pathCount += pathSum(root.right, sum)

        return pathCount
    }

    private fun subTreeSum(root: TreeNode, sum: Int): Int {
        var pathCount = 0
        if (root.`val` == sum) {
            pathCount++
        }
        root.left?.let { pathCount += subTreeSum(it, sum - root.`val`) }
        root.right?.let { pathCount += subTreeSum(it, sum - root.`val`) }

        return pathCount
    }
}

////////以下解法来自 https://leetcode.cn/problems/paths-with-sum-lcci/solution/qiu-he-lu-jing-by-leetcode-solution-wjy7/

// 时间复杂度：O(N^2) ，每个节点都要从当前节点到叶子节点求一次，复杂度为O(N)；共有N个节点要重复前述步骤，复杂度为O(N)
// 空间复杂度：O(N)
internal class PathSumSolution1 {
    fun pathSum(root: TreeNode?, sum: Int): Int {
        if (root == null) {
            return 0
        }
        var ret = rootSum(root, sum)
        ret += pathSum(root.left, sum)
        ret += pathSum(root.right, sum)
        return ret
    }

    fun rootSum(root: TreeNode?, sum: Int): Int {
        var ret = 0
        if (root == null) {
            return 0
        }
        val `val` = root.`val`
        if (`val` == sum) {
            ret++
        }
        ret += rootSum(root.left, sum - `val`)
        ret += rootSum(root.right, sum - `val`)
        return ret
    }
}

/**
 * 思路是，从root开始到node加值（curr），并保存到prefix中，记录其次数
 * 如果存在某个node，他到root的和（称为前缀和），与之前某个节点到root的和相差为sum，则说明他们之间的和为sum
 * 如此就可以找到binary tree中的所有满足条件的路径
 * 时间复杂度：O(N)，需要依次遍历每个节点
 * 空间复杂度：O(N)，需要递归遍历binary tree每一层
 */
internal class PathSumSolution {
    fun pathSum(root: TreeNode?, sum: Int): Int {
        val prefix: MutableMap<Long, Int> = HashMap()
        prefix[0L] = 1
        return dfs(root, prefix, 0, sum)
    }

    fun dfs(root: TreeNode?, prefix: MutableMap<Long, Int>, curr: Long, sum: Int): Int {
        var curr = curr
        if (root == null) {
            return 0
        }
        var ret = 0
        curr += root.`val`
        ret = prefix.getOrDefault(curr - sum, 0)
        prefix[curr] = prefix.getOrDefault(curr, 0) + 1
        ret += dfs(root.left, prefix, curr, sum)
        ret += dfs(root.right, prefix, curr, sum)
        prefix[curr] = prefix.getOrDefault(curr, 0) - 1
        return ret
    }
}


// 根据前缀和的思虑，编写代码
internal class PathSumPrefixSolution {

    // key:prefixSum,value:sumPathCount
    // 因为binary tree的node值可能很大，所以用Long存储
    val hashMap = LinkedHashMap<Long, Int>()

    fun pathSum(root: TreeNode?, sum: Int): Int {
        if (root == null) return 0

        // binary tree的root前缀值认为是0，计次为1
        hashMap.putIfAbsent(0L, 1)

        return dfs(root, sum, 0)
    }

    /**
     * @param root 当前子树的根节点
     * @param sum 需要计算的和
     * @param prefixSum 前缀值
     */
    private fun dfs(root: TreeNode?, sum: Int, prefixSum: Long): Int {
        var pathCount = 0
        if (root == null) return pathCount

        val currentPrefixSum = prefixSum + root.`val`
        // 这种情况包含：
        // root与前面某个路径之和为sum的情况；
        // 以及root本身等于sum的情况（此时currentPrefixSum - sum == prefixSum，hashMap中一定存在key）
        // 总之，是以root结尾的path之和为sum的情况
        pathCount = hashMap.getOrDefault(currentPrefixSum - sum, 0)

        // 下面的情况，可能包含root，也可能不包含，相当于对left和right重新进行第一种情况的判断
        hashMap.put(currentPrefixSum, hashMap.getOrDefault(currentPrefixSum, 0) + 1)
        pathCount += dfs(root.left, sum, currentPrefixSum)
        pathCount += dfs(root.right, sum, currentPrefixSum)
        // 判断完roo、以及下面的left和right之后，需要返回root父级，所以这里需要删除当前root节点的后缀值
        hashMap.put(currentPrefixSum, hashMap.getOrDefault(currentPrefixSum, 0) - 1)

        return pathCount
    }

}


fun main() {
//    val tree = Utils.createTreeFromString("[1]")
    val tree = Utils.createTreeFromString("[0,4,8,11,null,13,4,7,2,null,null,5,1]") // 单边和 =22
//    val tree = Utils.createTreeFromString("[5,4,8,1,3,4,2,null,null,null,null,1,0]")// 双边和=4
    println("tree:")
    Utils.printTree(tree!!)
    println("result:")
    println("PathsWithSumLcci:${PathSumPrefixSolution().pathSum(tree, 4)}")
    println("PathSumSolution:${PathSumSolution().pathSum(tree, 4)}")
}