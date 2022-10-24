package medium

/**
 * @author : jixiaoyong
 * @description ： 915. 分割数组
 * 给定一个数组 nums ，将其划分为两个连续子数组 left 和 right， 使得：
 * left 中的每个元素都小于或等于 right 中的每个元素。
 * left 和 right 都是非空的。
 * left 的长度要尽可能小。
 *
 * 在完成这样的分组后返回 left 的 长度 。用例可以保证存在这样的划分方法。
 *
 * 提示：
 *
 * 2 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^6
 * 可以保证至少有一种方法能够按题目所描述的那样对 nums 进行划分。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/24/2022
 */
class PartitionArrayIntoDisjointIntervals {
    /**
     * 思路：从left和right开始遍历数组，left中最大的数字为max，right中最小的数字为min；
     * 需要始终满足max<=min
     * 使用两个数组分别记录从左到右最大值，和从右到左最小值
     * 在遍历时，如果右侧最小值>=左侧最大值，则right--；否则left++，这样最终left会和right相遇于一点
     * 此时，如果满足right+1的右→左最小值大于等于left的左→右最大值，即nums[right+1~len-1]>=nums[0~left]则满足条件，退出遍历
     * 否则将left和right一起向右移动，直到满足上述条件（只要nums满足条件，则必定会找到满足条件的right和left）
     *
     * 时间复杂度O（N)，最坏情况下会遍历两次nums
     * 空间复杂度O(N)，需要额外的两个数组存储最值
     *
     * 560 ms	50.1 MB
     */
    fun partitionDisjoint(nums: IntArray): Int {
        val len = nums.size
        var right = len - 1
        var left = 0

        // 记录从0→len-1方向的最大值
        val leftMax = IntArray(len)
        // 记录从0←len-1方向的最小值
        val rightMin = IntArray(len)

        leftMax[0] = nums[left]
        rightMin[len - 1] = nums[right]

        while (true) {
            // 填充两个记录最大最小值的表格
            val leftNum = nums[left]
            val rightNum = nums[right]
            if (left > 0) {
                leftMax[left] = Math.max(leftMax[left - 1], leftNum)
            }
            if (right < len - 1) {
                rightMin[right] = Math.min(rightMin[right + 1], rightNum)
            }

            if (left != right) {
                if (rightMin[right] >= leftMax[left]) {
                    right--
                } else {
                    left++
                }
            } else {
                if (rightMin[right + 1] >= leftMax[left]) {
                    break
                } else {
                    left++
                    right++
                }
            }

        }


        return left + 1
    }

    /**
     * LeetCode官方解法
     * 一次遍历
     * left必须满足条件，其【左侧最大值小于等于右侧最小值】
     * 假定有nums[left]，其最大值为maxLeft，如果有nums[i]< maxLeft，那么说明之前的left不满足条件，令left=i，并更新maxLeft
     * 时间复杂度O(N) ，只需遍历一次
     * 空间复杂度O(1)
     * 516 ms	52.1 MB
     */
    fun partitionDisjoint1(nums: IntArray): Int {
        var left = 0
        var maxLeft = nums[left]
        var i = 1
        var maxCur = Math.max(nums[left], nums[i])

        while (i < nums.size) {
            val cur = nums[i]
            maxCur = Math.max(maxCur, cur)
            if (cur < maxLeft) {
                left = i
                maxLeft = maxCur
            }
            i++
        }

        return left + 1
    }

}

fun main() {
    val obj = PartitionArrayIntoDisjointIntervals()

    val cases = mutableListOf(
        intArrayOf(5, 0, 3, 8, 6),  // 3
        intArrayOf(1, 1, 1, 0, 6, 12),  // 4
        intArrayOf(1, 6, 1, 0, 5, 1, 1, 3, 8),//8
        intArrayOf(1, 2, 6, 1, 5, 1, 1, 4, 8),//1
        intArrayOf(1, 2),// 1
        intArrayOf(5, 1, 2, 6, 1, 4, 7), //6
        intArrayOf(5, 1, 2, 6, 1, 7, 6, 9) //5
    )

    cases.forEach {
        println("${it.joinToString()} --> ${obj.partitionDisjoint1(it)}")
    }
}