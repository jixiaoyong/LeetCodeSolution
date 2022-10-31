package medium

/**
 * @author : jixiaoyong
 * @description ： 481. 神奇字符串
 * 神奇字符串 s 仅由 '1' 和 '2' 组成，并需要遵守下面的规则：
 *
 * 神奇字符串 s 的神奇之处在于，串联字符串中 '1' 和 '2' 的连续出现次数可以生成该字符串。
 * s 的前几个元素是 s = "1221121221221121122……" 。如果将 s 中连续的若干 1 和 2 进行分组，可以得到 "1 22 11 2 1 22 1 22 11 2 11 22 ......" 。
 * 每组中 1 或者 2 的出现次数分别是 "1 2 2 1 1 2 1 2 2 1 2 2 ......" 。上面的出现次数正是 s 自身。
 *
 * 给你一个整数 n ，返回在神奇字符串 s 的前 n 个数字中 1 的数目。
 *
 * 提示：
 *
 * 1 <= n <= 10^5
 *
 * https://leetcode.cn/problems/magical-string/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/31/2022
 */
class MagicalString {
    /**
     * 双指针
     * 这个字符串的生成规则是：
     * 1. 插入个数：如果是数字1，那么当前应该插入1个待插入数字i，如果是2则应该插入2个i。
     * 2. 插入数字i，前面一个数字用完毕之后，则换下一个数字，从第一位数字为例，首先是1，插入一个1，结束，那么第二个数字轮到了数字2，插入2，此时
     * 字符串为【12】，那么生成字符的下标此时也指向了2，那么意味着下个插入的数字应该是插入2次，而此次插入的数字是2，所以这里插入2次2，此时为【122】
     * 第三次，生成下标指向了第二个2，数字也轮到了1，所以字符为【12211】
     * 思路是，一个指针指向应该插入字符的位置resultIndex，一个是要插入的数字个数下标curGenIndex，一个是当前的数字curNum，及他剩余可用的次数availableCount
     * 每次挪动resultIndex插入一个数字，如果数字用完了，就换下一个数字，每次换数字的时候，移动curGenIndex获取下一个数字的插入个数availableCount
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    fun magicalString(n: Int): Int {
        var result = 0

        var resultIndex = 0
        var curGenIndex = 0

        val stringArr = IntArray(n)
        stringArr[0] = 1
        var curNum = 1
        var availableCount = stringArr[curGenIndex]

        if (n >= 2) {
            stringArr[1] = 2
        }

        while (resultIndex < n) {
            curNum = getNextNum(curNum, availableCount--)
            stringArr[resultIndex++] = curNum
            if (curNum == 1) {
                result++
            }
            if (availableCount == 0 && curGenIndex + 1 < n) {
                curNum = getNextNum(curNum, 0)
                curGenIndex++
                availableCount = stringArr[curGenIndex]
            }
        }
        return result
    }

    private fun getNextNum(curValue: Int, availableCount: Int): Int {
        if (availableCount > 0) {
            return curValue
        }
        return if (curValue == 1) 2 else 1
    }
}

fun main() {
    val obj = MagicalString()

    for (x in 10..100000) {
        val result = obj.magicalString(x)
        println("result:$result ---> $x\n")
    }
}