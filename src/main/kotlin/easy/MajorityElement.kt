package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 169. 多数元素
 * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * 提示：
 * n == nums.length
 * 1 <= n <= 5 * 10^4
 * -10^9 <= nums[i] <= 10^9
 *
 * https://leetcode.cn/problems/majority-element/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/18/2022
 */
class MajorityElement {
    /**
     * 290 ms	42.9 MB
     * 时间复杂度和空间复杂度为O(N)
     */
    fun majorityElement(nums: IntArray): Int {
        val n = nums.size

        if (n <= 1) {
            return nums[0]
        }

        val halfCount = n / 2
        val hashMap = hashMapOf<Int, Int>()

        nums.forEach { num ->
            val currentCount = (hashMap.get(num) ?: 0) + 1
            hashMap.put(num, currentCount)
            if (currentCount > halfCount) {
                return num
            }
        }

        return nums[0]
    }

    /**以下解法来自LeetCode官方题解**/

    /**
     * 排序法
     * 474 ms	53.5 MB
     * https://leetcode.cn/problems/majority-element/solution/duo-shu-yuan-su-by-leetcode-solution/
     * 先对数组排序，根据众数的定义，无论它是在数组前半部分还是后半部分都会在数组长度n的一半 n/2处，所以直接返回该值即可
     * 时间复杂度：O(nlogn)。将数组排序的时间复杂度为 O(nlogn)。
     * 空间复杂度：O(logn)。如果使用语言自带的排序算法，需要使用 O(logn) 的栈空间。如果自己编写堆排序，则只需要使用 O(1) 的额外空间。
     */
    fun majorityElement2(nums: IntArray): Int {
        nums.sort()
        return nums[nums.size / 2]
    }

    /// 此外还有分治法，如果majority为arr的众数，那么将arr从中间分为两半，那么majority至少是其中某一个arr的众数，由此将大数组拆分为小数组
    /// 此方法时间复杂度：O(nlogn)，空间复杂度：O(logn)


    /**
     * 投票算法
     * 537 ms	51.4 MB
     * 众数majority记为+1，其余数记为-1，如果全部加起来和一定会大于0
     * 根据此原理，设置一个待定值candidate，如果之前数字总和count为0，则将当前数字x赋值给candidate
     * 如果count不为0，那么如果candidate与x相等的话count+1，否则count-1
     * 这样遍历完成arr之后，因为众数majority总数大于其余数字数目之和，所以count大于0，candidate记录的就是众数
     *
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    fun majorityElementVote(nums: IntArray): Int {
        var candidate = nums[0]
        var count = 0

        nums.forEach {
            if (count == 0) {
                candidate = it
                count++
            } else if (candidate == it) {
                count++
            } else {
                count--
            }
        }

        return candidate
    }

}

fun main() {
    val obj = MajorityElement()
    val random = Random(System.currentTimeMillis())
    val nums =
        mutableListOf(
            intArrayOf(1),
            intArrayOf(2, 2),
            intArrayOf(1, 2, 2),
            intArrayOf(1, 3, 2, 3, 2, 3, 4, 3, 3, 3),
            intArrayOf(1, 3, 2, 4, 2, 3, 3, 9, 3)// no majority element
        )
    val numberRange = IntRange(-1 * Math.pow(10.0, 9.0).toInt(), 1 * Math.pow(10.0, 9.0).toInt())

    for (x in 0..20) {
        val len = random.nextInt(1, 30)
        var half = len / 2 + 1
        val halfNum = random.nextInt(numberRange.start, numberRange.endInclusive)
        val intArray = IntArray(len)
        for (x in 0 until len) {
            intArray[x] = if (half > 0 && random.nextBoolean()) {
                half--
                halfNum
            } else {
                random.nextInt(numberRange.start, numberRange.endInclusive)
            }
        }

        while (half > 0) {
            intArray.forEachIndexed { index, value ->
                if (value != halfNum && value % 3 != 0 && half > 0) {
                    half--
                    intArray[index] = halfNum
                }
            }
        }
        nums.add(intArray)
    }

    nums.forEach {
        println("${obj.majorityElement(it)} --- > len ${it.size},half ${it.size / 2} ---> ${it.joinToString()} ,")
        println("${obj.majorityElementVote(it)} --- > len ${it.size},half ${it.size / 2} ---> ${it.joinToString()} ,")
    }
}