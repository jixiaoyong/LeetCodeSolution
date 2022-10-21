package easy

import java.util.LinkedList
import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 496. 下一个更大元素 I
 * nums1中数字x的 下一个更大元素 是指x在nums2 中对应位置 右侧 的 第一个 比x大的元素。
 * 给你两个 没有重复元素 的数组nums1 和nums2 ，下标从 0 开始计数，其中nums1是nums2的子集。
 *
 * 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定 nums2[j] 的 下一个更大元素 。
 * 如果不存在下一个更大元素，那么本次查询的答案是 -1 。
 *
 * 返回一个长度为nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
 *
 * 提示：
 *
 * 1 <= nums1.length <= nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 104
 * nums1和nums2中所有整数 互不相同
 * nums1 中的所有整数同样出现在 nums2 中
 *
 * https://leetcode.cn/problems/next-greater-element-i/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/21/2022
 */
class NextGreaterElementI {

    /**
     * 342 ms	40.1 MB
     * 思路：按照题干做法，依次遍历nums1，每个nums1元素定位到其在nums2中的位置，向后查询满足条件的元素并记录
     * 时间复杂度O(MN) M/N分别是nums1、nums2的长度
     * 空间复杂度O(M) 需要一个列表记录结果
     */
    fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray {
        val result = IntArray(nums1.size) { -1 }
        nums1.forEachIndexed { index, num ->
            val startIndex = nums2.indexOf(num) + 1
            for (x in startIndex until nums2.size) {
                if (nums2[x] > num) {
                    result[index] = nums2[x]
                    break
                }
            }
        }

        return result
    }


    /**
     * 单调栈+hashMap
     * 339 ms	39.8 MB
     * 对num2进行遍历，记录每个元素的下标，并生成单调递减栈（因为题目是找右侧较大值，所以放入数字num时，如果检测到栈顶小于num，满足条件，记录；
     * 并退出这个栈顶数字(它已经满足条件，无需再后续对比)，然后后后移，这样栈里面的数字都是不满足条件的）
     * 时间复杂度O(M+N)
     * 空间复杂度O(N)
     */
    fun nextGreaterElementStack(nums1: IntArray, nums2: IntArray): IntArray {
        // 保存每个数字的右侧较大值，没有的话则为-1
        val hashMap = hashMapOf<Int, Int>()
        // 单调递减栈，这里面的数字都暂时没有找到右侧较大值
        val stack = LinkedList<Int>()
        stack.add(Int.MAX_VALUE)
        val result = IntArray(nums1.size) { -1 }

        nums2.forEachIndexed { index, i ->
            // 【循环是为了处理6,5,4,3,7这样的情况】，前面几个数字的右侧较大值都是7，因此要遍历stack
            while (i > stack.peekLast() && stack.isNotEmpty()) {
                val num = stack.removeLast()
                hashMap.put(num, i)
            }
            stack.addLast(i)
        }

        nums1.forEachIndexed { index, i ->
            result[index] = hashMap.getOrDefault(i, -1)
        }

        return result
    }
}

fun main() {
    val obj = NextGreaterElementI()
    val random = Random(System.currentTimeMillis())

    val numPairList = mutableListOf(
        Pair(intArrayOf(1, 3, 5, 2, 4), intArrayOf(6, 5, 4, 3, 2, 1, 7)),//注意这种特殊情况
        Pair(intArrayOf(4, 1, 2), intArrayOf(1, 3, 4, 2)),
        Pair(intArrayOf(2, 4), intArrayOf(1, 2, 3, 4)),
        Pair(intArrayOf(2), intArrayOf(1, 2, 1, 3, 4)),
    )

    val largestNumArr2 = IntArray(1000) { random.nextInt(0, 1000) }
    val largestNumArr1 = IntArray(1000) { index ->
        largestNumArr2[999 - index]
    }


//    println("1-----:${largestNumArr1.joinToString()}")
//    println("2-----:${largestNumArr2.joinToString()}")


    numPairList.add(Pair(largestNumArr1, largestNumArr2))

    numPairList.forEach {
        println(
            "${
                intArrayOrSizeToString(obj.nextGreaterElementStack(it.first, it.second))
            }  :   ${intArrayOrSizeToString(it.first)}--${intArrayOrSizeToString(it.second)}"
        )
    }
}

fun intArrayOrSizeToString(intArray: IntArray, maxSize: Int = 20): String {
    return if (intArray.size >= maxSize) {
        "${intArray.size}"
    } else {
        intArray.joinToString()
    }
}