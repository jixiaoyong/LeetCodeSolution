package medium

/**
 * @author : jixiaoyong
 * @description ：1653. 使字符串平衡的最少删除次数
 * 给你一个字符串s，它仅包含字符'a' 和'b' 。
 *
 * 你可以删除s中任意数目的字符，使得s 平衡。当不存在下标对(i,j)满足i < j ，且s[i] = 'b' 的同时s[j]= 'a' ，此时认为 s 是 平衡 的。
 *
 * 请你返回使 s平衡的 最少删除次数。
 * 提示：
 *
 * 1 <= s.length <= 105
 * s[i] 要么是 'a' 要么是 'b' 。
 *
 * https://leetcode.cn/problems/minimum-deletions-to-make-string-balanced/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/3/6
 */
object MinimumDeletionsToMakeStringBalanced {


    /**
     * 思路：
     * 将题目要求转化为：
     * 平衡的字符串需要满足条件：全部是a或者b，或者存在一个点，右侧全部是a左侧全部是b
     * 那么要计数需要删除的最小次数，只要依次计算这个点分别在String的1~len-1时需要的删除次数即可
     * 每个点需要的删除次数，则取决于当前点右侧和左侧的a、b字符个数，所以需要首先计算出这个数字来
     */
    fun minimumDeletions(s: String): Int {
        // 分别表示右侧的a的数量、左侧的b的数量
        var rightA = 0
        var leftB = 0

        val charA = 'a'

        s.forEach {
            if (it == charA) {
                rightA += 1
            }
        }

        if (rightA == 0 || rightA == s.length) {
            // 全部为a或者全部为b
            return 0
        }

        // 满足题设有三种情况：1.全部是a，此时leftB为0；2.全部是b，此时rightA为0，3.在s中间某个位置，左侧全部为a，右侧全部为b
        var res = rightA
        s.forEach {
            // 此处计算的为去除当前char的话，总共需要去除的char个数
            if (it == charA) {
                // 在这个位置要去除的a数量为右侧总共的a数目rightA减去当前的a
                rightA--
            } else {
                // 在这个位置要去除的b的数量为之前已经去除的+当前要去除的char
                leftB++
            }
            res = Math.min(res, rightA + leftB)
        }
        return res
    }
}

fun main() {
    val cases = arrayListOf<String>("aababbab", "bbaaaaabb","aaa", "bb", "bbaaaaaabb", "aababbbabb", "bbaaaaabb",)
    cases.forEach {
        val res = MinimumDeletionsToMakeStringBalanced.minimumDeletions(it)
        println("$res --> $it")
    }
}