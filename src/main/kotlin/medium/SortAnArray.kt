package medium

import java.util.*

/*
* @description: 912. Sort an Array
* https://leetcode.com/problems/sort-an-array/
*
* Given an array of integers nums, sort the array in ascending order.
*
* Constraints:

1 <= nums.length <= 5 * 104
-5 * 104 <= nums[i] <= 5 * 104
*
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 22/03/13
*/
object SortAnArray {
    /**
     * 快速排序
     * 对超大且有序数组会Time Limit Exceeded
     */
    fun sortArray(nums: IntArray): IntArray {
        if (nums.size <= 1) {
            return nums
        }

        return quickSort(nums, 0, nums.size - 1)

    }

    /**
     * 固定选取基准的快速排序
     * 对超大且有序数组会Time Limit Exceeded
     */
    fun quickSort(nums: IntArray, left: Int, right: Int): IntArray {
        // array size is 0
        if (left < 0 || right > nums.size - 1 || right < left) {
            return intArrayOf()
        }

        // array size is 1
        if (right == left && right > 0) {
            return intArrayOf(nums[right])
        }

        // array size is 2
        if (right - left == 1) {
            if (nums[left] > nums[right]) {
                swap(nums, left, right)
            }
            return nums
        }

        // array size is > 2
        var i = left + 1
        var j = right

        // 1. 固定基准的位置，对有序超大数组会性能很差直至StackOverflowError
//        var pIndex = left
//        val pivot = nums[pIndex]

        // 2. 随机选取基准位置，避免超大有序数组导致的极端情况
//        val randomIndex = (left..right).random()
//        swap(nums, left, randomIndex)
//        var pIndex = left
//        val pivot = nums[pIndex]

        // 3.三元取中，从左中右三个数中选取中间的数作为基准
        // 875 ms	72.6 MB
        val leftNum = nums[left]
        val rightNum = nums[right]
        val midIndex = left + (right - left) / 2
        val midNum = nums[midIndex]
        val randomIndex = if (leftNum >= rightNum) {
            if (leftNum <= midNum) {
                left
            } else if (midNum <= rightNum) {
                right
            } else {
                midIndex
            }
        } else {
            if (rightNum <= midNum) {
                right
            } else if (midNum <= leftNum) {
                left
            } else {
                midIndex
            }
        }
        if (randomIndex != left) {
            swap(nums, left, randomIndex)
        }
        var pIndex = left
        val pivot = nums[pIndex]

        while (i < j) {
            // 找到第一个小于基准的数
            while (j > i) {
                if (nums[j] < pivot) {
                    break
                }
                j--
            }

            // 找到第一个大于基准的数
            while (i < j) {
                if (nums[i] > pivot) {
                    break
                }
                i++
            }

            // 如果此时i和j还未相遇，则交换这两个值
            if (i < j) {
                swap(nums, i, j)
            }
        }

        // 将基准值放到正确的位置:left <= pivot < j
        if (nums[i] < pivot) {
            swap(nums, i, pIndex)
            pIndex = i
        }


        quickSort(nums, pIndex + 1, right)
        quickSort(nums, left, pIndex - 1)
        return nums
    }

    fun swap(nums: IntArray, i: Int, j: Int) {
        val temp = nums[i]
        nums[i] = nums[j]
        nums[j] = temp
    }

    /**
     * 递归排序
     * @param src
     * @param begin
     * @param end
     */
    private fun quickSortP(src: IntArray, begin: Int, end: Int) {
        if (begin < end) {
            val key = src[begin]
            var i = begin
            var j = end
            while (i < j) {
                while (i < j && src[j] > key) {
                    j--
                }
                if (i < j) {
                    src[i] = src[j]
                    i++
                }
                while (i < j && src[i] < key) {
                    i++
                }
                if (i < j) {
                    src[j] = src[i]
                    j--
                }
            }
            src[i] = key
            quickSortP(src, begin, i - 1)
            quickSortP(src, i + 1, end)
        }
    }

    /**
     * 堆排序
     * 参考：https://blog.csdn.net/u010452388/article/details/81283998
     */
    fun heapSort(nums: IntArray): IntArray {

        var right = nums.size - 1;
        while (right >= 0) {
            right = buildMaxHead(nums, right)
        }

        return nums
    }

    /**
     * 构建最大堆
     * 	Time Limit Exceeded
     * 最大堆的定义：父节点的值大于子节点的值
     * 构建最大堆的思路即：
     * 将数组依次遍历，每个节点的数与他的父节点对比，如果父节点小于子节点，则交换位置，直到父节点大于子节点
     * 这样就构建好了一个最大堆，堆的根节点就是最大值，将这个值放到数组最后，则数组剩下的length-1个数需要排序
     * 这样子重复直到所有的数都拍好序
     */
    fun buildMaxHead(nums: IntArray, right: Int): Int {

        for (index in 1..right) {
            var currentIndex = index
            var parentIndex = (currentIndex - 1) / 2

            while (parentIndex >= 0 && nums[currentIndex] > nums[parentIndex]) {
                swap(nums, currentIndex, parentIndex)
                currentIndex = parentIndex
                parentIndex = (currentIndex - 1) / 2
            }
        }

        // 将最大堆的根节点放到最后
        swap(nums, 0, right)
        // 只有剩下的0~right-1个数需要排序
        return right - 1
    }


    /*******LeetCode cn官方 堆排序 start ********/

    fun sortArrayWithHeapSort(nums: IntArray): IntArray {
        heapSortLC(nums)
        return nums
    }

    fun heapSortLC(nums: IntArray) {
        var len = nums.size - 1
        buildMaxHeap(nums, len)
        for (i in len downTo 1) {
            swap(nums, i, 0)
            len -= 1
            maxHeapify(nums, 0, len)
        }
    }

    fun buildMaxHeap(nums: IntArray, len: Int) {
        for (i in len / 2 downTo 0) {
            maxHeapify(nums, i, len)
        }
    }

    fun maxHeapify(nums: IntArray, i: Int, len: Int) {
        var i = i
        while ((i shl 1) + 1 <= len) {
            val lson = (i shl 1) + 1
            val rson = (i shl 1) + 2
            var large: Int
            large = if (lson <= len && nums[lson] > nums[i]) {
                lson
            } else {
                i
            }
            if (rson <= len && nums[rson] > nums[large]) {
                large = rson
            }
            i = if (large != i) {
                swap(nums, i, large)
                large
            } else {
                break
            }
        }
    }


    /*******LeetCode cn 官方堆排序 end ********/


    @JvmStatic
    fun main(args: Array<String>) {
        val nums = intArrayOf(-4, 0, 7, 4, 9, -5, -1, 0, -7, -1)
//        val nums = intArrayOf(10, 2, 5, 3, 2, 8, 9, 7, 1, 4)
//        val nums = intArrayOf(1, 2, 5, 3, 2, 8, 9, 7, 1, 4)
//        val size = 50000
//        val nums = IntArray(size)
//        var random = Random(System.currentTimeMillis())
//        for (i in 0 until size) {
//            nums[i] = random.nextInt(size)
//        }
        var numberCopy = nums.copyOf()
        val startTime = System.currentTimeMillis()
//        for (i in 0 until 10000) {
//            sortArray(nums)
//        }
        val result = sortArrayWithHeapSort(nums)
        val middleTime = System.currentTimeMillis()
//        println(result.joinToString(","))

        sortArray(numberCopy)
        println(result.joinToString(",").equals(numberCopy.joinToString(",")))
        println("time: ${middleTime - startTime}")


//        quickSortP(nums, 0, nums.size - 1)
//        for (i in 0 until 10000) {
//            quickSortP(nums, 0, nums.size - 1)
//        }
//        println(nums.joinToString(","))
        println("time: ${System.currentTimeMillis() - middleTime}")
    }
}