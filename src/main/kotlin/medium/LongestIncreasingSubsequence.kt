package medium

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/3/12
 * description:
 * 300. Longest Increasing Subsequence
 * https://leetcode.com/problems/longest-increasing-subsequence/
 * Given an unsorted array of integers, find the length of the longest increasing subsequence.
 * A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the
 * order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
 *
 * Constraints:

1 <= nums.length <= 2500
-104 <= nums[i] <= 104

 */
object LongestIncreasingSubsequence {

    /**
     * 1052 ms	faster than 9.33%
     * 40.7 MB  less than 74.61%
     */
    fun lengthOfLIS(nums: IntArray): Int {
        if (nums.size == 1) return 1

        val numAndLength = HashMap<Int, Int>()

        var length = 1
        numAndLength[nums[0]] = length

        for (i in 1 until nums.size) {
            val num = nums[i]
            val preNums = numAndLength.keys.toList()

            for (preNum in preNums) {
                if (preNum < num) {
                    val newPreLength = numAndLength[preNum]!! + 1
                    // 只有比当前记录的长度更长的才更新，防止丢失记录
                    numAndLength[num] = Math.max(numAndLength[num] ?: 0, newPreLength)
                    length = Math.max(length, newPreLength)
                } else if (preNum > num && !numAndLength.containsKey(num)) {
                    // 如果已有的数字比当前数字大，并且没有记录过当前num，才将其长度设置为1，以防重置了已有的计算结果
                    numAndLength[num] = 1
                }
            }
        }

        return length
    }

    /**
     * 方法二：贪心 + 二分查找
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * 思路解析：
    [引用START]

    Dreamy Jacksony4O https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/836831
    2021-03-16
    @spike 方法二讲一下个人的理解，虽然可能还是不够清晰

    贪心实际是最接近人的理解的方法，我们的难点是怎么将思维整理并转化为代码实现。

    先不考虑题解， 假如给出序列 mylist=[1， 3， 5， 6， 7， 2， 5， 7， 9， 10]，我们肉眼来找最长序列的时候，看到1, 3, 5, 6, 7递增，
    长度len1=5，我们怀疑这个就是目标序列，暂且记为答案一。
    但是当遇到2(mylist[5])时，我们就考虑另一种答案，有没有可能从1, 2, ... (mylist[0], mylist[5]...)会找到一个新的子序列，
    且比之前找的序列1, 3, 5, 6, 7更长呢？（记为答案二）我相信大部分人分析题目时，会有这个想法

    接下来，当我们继续验证答案二时，需要依赖2以后的数字，看是否能找到新的递增序列。

    然而，我们的目的是找最长的长度， 有两种结果，
    新的答案比第一次的答案短， 则我们保留原始答案一，
    新的答案比第一次的答案长， 我们用新的方案。

    但是过程我们要记录， 1）我新的答案，找到哪个数字了，以便继续往下找 2）如果新的答案不够长，保留答案一的结果。
    回到题解，当我找到2时， 新的答案，第二个数字是2， 原来的[1, 3, 5, 6, 7], 3已经不在我新的答案序列了，我就把3替换为2，
    依次进行， 如果我新的答案比第一次答案长， 整个序列被替换，
    如果没有第一个长，我替换的次数不够，原来方案一里最大的数字还在末尾， 原始的d的长度不会被替换。

    那么我们始终能用d的长度表示我们的结果。

    [引用END]

    ***************对官网的例子进行分析********************

    以输入序列 [0, 8, 4, 12, 2]为例：

    第一步插入 0，d = [0]；

    第二步插入 8，d = [0, 8]；

    第三步插入 4，d = [0, 4]；// 贪心算法要求，每次数字变化尽量小，这样结果才是最优解

    第四步插入 12，d = [0, 4, 12]；

    第五步插入 2，d = [0, 2, 12]。
    第五步同第三步，2替代了4，也就是此时列表中有2种子序列：
     1. 长度为2的，根据贪心算法，只记录[0,2]，如果输入序列之后还有其他数字的话，这个序列可能继续延长
     2. 长度为3的，有[0,8,12]和[0,4,12]，根据贪心算法只保留了[0,4,12]，
     由于d[len]的作用是记录当前最长子序列，和下一个可能的最长子序列，所以第五步这里的d其实是[0,4,12]和下一个可能的最优解[0,2]的合体（当然
    对于该输入序列[0, 8, 4, 12, 2]来说[0,2]之后再没有其他数字了，所以最终的d才表现为[0, 2, 12]）
     */
     fun lengthOfLISLeetCode(nums: IntArray): Int {
        var len = 1
        val n = nums.size
        if (n == 0) {
            return 0
        }
        // 用来保存中间计算的子序列，下标为len的长度，从1开始计数，所以d的长度为n+1
        val d = IntArray(n + 1)
        d[len] = nums[0]
        for (i in 1 until n) {
            if (nums[i] > d[len]) {
                // 如果当前数字比已经保存的最长的子序列的最后一个数字还大，则延长自序列长度
                d[++len] = nums[i]
            } else {
                // 如果当前数字比已经保存的最长子序列的最后一个数字小，则可能有以下情况：
                // 1. 该数字是最终的答案
                // 2. 该数字组成的子序列比当前序列短
                // 不论如何将其放入d中对应的位置，
                // 对于情况2，则该数字只组成了从d[1]到其位置处的短序列，不影响整体结果
                // 对于情况1，则该数字组从d[1]一直开始替换了当前最长的d[len]，并且还会在未来将其余数字按照nums[i] > d[len]的规则增加len长度
                var l = 1
                var r = len
                var pos = 0 // 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
                while (l <= r) {
                    // 二分法查找只是为了尽快给nums[i]确定一个合适的位置
                    val mid = l + r shr 1
                    if (d[mid] < nums[i]) {
                        pos = mid
                        l = mid + 1
                    } else {
                        r = mid - 1
                    }
                }
                d[pos + 1] = nums[i]
            }
        }
        return len
    }


    @JvmStatic
    fun main(args: Array<String>) {
        println(lengthOfLISLeetCode(intArrayOf(10, 9, 2, 5, 3, 7, 101, 18)))
        println(lengthOfLISLeetCode(intArrayOf(10, 9, 2, -5, 3, 7, 101, 18)))
        println(lengthOfLISLeetCode(intArrayOf(10)))
        println(lengthOfLISLeetCode(intArrayOf(-10)))
        println(lengthOfLISLeetCode(intArrayOf(10, 10, 10, 10, 10, 10)))
        println(lengthOfLISLeetCode(intArrayOf(10, 10, -10, 10, 10, 10)))
        println(lengthOfLISLeetCode(intArrayOf(0, 1, 0, 3, 2, 3)))
    }
}