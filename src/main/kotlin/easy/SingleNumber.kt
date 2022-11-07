package easy

/**
 * @author : jixiaoyong
 * @description ： 136. 只出现一次的数字
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 说明：
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * https://leetcode.cn/problems/single-number
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/7/2022
 */
class SingleNumber {

    /**
     * 304 ms	37.5 MB
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    fun singleNumber(nums: IntArray): Int {
        val set = mutableSetOf<Int>()
        for (num in nums) {
            if (set.contains(num)) {
                set.remove(num)
            } else {
                set.add(num)
            }
        }
        return set.first()
    }


    /**
     * 240 ms	36.7 MB
     * 【异或运算】有三个性质：
     * 1. 0 异或任何数等于该数本身
     * 2. 任何数和自身异或结果是0
     * 3. 异或运算满足结合律和交换律，也就是a 异或 b 异或 a == b 异或 a 异或 a == b 异或 （a 异或 a） = b 异或 0 == b
     *
     * 这里相同的数字异或之后为0，最后剩下仅有的一个数字与0异或运算，得到这个数自身
     * https://leetcode.cn/problems/single-number/solution/zhi-chu-xian-yi-ci-de-shu-zi-by-leetcode-solution/
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    fun singleNumberPlus(nums: IntArray): Int {
        var result = 0
        for (num in nums) {
            result = result xor num
        }
        return result
    }
}

fun main() {
    val obj = SingleNumber()
    val cases = mutableListOf(
        intArrayOf(2, 2, 1),
        intArrayOf(2),
        intArrayOf(4, 1, 2, 1, 2),
        intArrayOf(4, 1, 2, 1, 2, 4, 3, 3, 7),
    )

    val largestArr = IntArray(100000) { it }
    val largestCase = IntArray(200001)
    for (x in 0..99999) {
        largestCase[x] = largestArr[x]
        largestCase[x + 100000] = largestArr[x]
    }
    largestCase[200000] = Int.MAX_VALUE
    cases.add(largestCase)

    cases.forEach {
        println("result:${obj.singleNumber(it)} / ${obj.singleNumberPlus(it)} --> ${intArrayOrSizeToString(it)}")
    }
}