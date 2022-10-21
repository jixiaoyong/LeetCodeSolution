package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ：219. 存在重复元素 II
 * 给你一个整数数组nums 和一个整数k ，判断数组中是否存在两个 不同的索引i和j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。
 * 如果存在，返回 true ；否则，返回 false 。
 *
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 0 <= k <= 10^5
 *
 * https://leetcode.cn/problems/contains-duplicate-ii/
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/21/2022
 */
class ContainsDuplicateII {
    /**
     * 3359 ms	73.9 MB
     * 思路：nums每个数字向后查找1~K次，有对应的值则返回true，如果一直没有则返回false
     * 时间复杂度O(NK)
     * 空间复杂度O(1)
     */
    fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
        for (x in 0 until nums.size) {
            for (j in 1..k) {
                val after = x + j
                if (after < nums.size && nums[x] == nums[after]) {
                    return true
                }
            }
        }
        return false
    }

    /*****以下来自LeetCode官方解法***/

    /**
     * 哈希表
     * 582 ms	49.4 MB
     * 使用hash表格保存每个数字最后一次出现的坐标j，当出现和他相等的数字坐标i时，如果满足i-j<=k则return true
     * 否则更新其坐标值，继续遍历
     * 时间复杂度O(N)，其中hash表操作时间为O(1)
     * 空间复杂度为O(N)，最坏可能要存储整个nums数据
     */
    fun containsNearbyDuplicateHash(nums: IntArray, k: Int): Boolean {
        val hashMap = hashMapOf<Int, Int>()

        nums.forEachIndexed { index, i ->
            if (hashMap.contains(i) && index - hashMap.get(i)!! <= k) {
                return true
            }
            hashMap.put(i, index)
        }

        return false
    }

    /**
     * 滑动窗口
     * 480 ms	48.3 MB
     * 创建一个长度为1~K（避免越界）的窗口，如果窗口内有重复的数字则满足要求，返回true；如果将窗口从左到右滑动到末尾还是没有，则返回false
     * 借助set的特性，判断是否已经有对应的值
     * 时间复杂度O（N） 最坏需要遍历完nums
     * 空间复杂度O（K）set只需要保存最多K+1个元素
     */
    fun containsNearbyDuplicateSet(nums: IntArray, k: Int): Boolean {
        val set = mutableSetOf<Int>()
        nums.forEachIndexed { index, i ->
            if (set.size > k) {
                set.remove(nums[index - k - 1])
            }

            if (!set.add(i)) {
                return true
            }

        }
        return false
    }
}

fun main() {
    val obj = ContainsDuplicateII()
    val random = Random(System.currentTimeMillis())

    val numsList = mutableListOf(
        Pair(intArrayOf(1), 3),
        Pair(intArrayOf(1, 2, 3, 1), 3),
        Pair(intArrayOf(1, 0, 1, 1), 1),
        Pair(intArrayOf(1, 2, 3, 1, 2, 3), 2),
    )


    val largestArr = IntArray(100000) { index ->
        random.nextInt()
    }
    largestArr[largestArr.size - 1] = largestArr.first()
    numsList.add(Pair(largestArr, 100000))

    numsList.forEach {
        println(
            "${
                obj.containsNearbyDuplicateSet(
                    it.first,
                    it.second
                )
            } : ${it.second}--${if (it.first.size < 100) it.first.joinToString() else it.first.size}"
        )
    }
}