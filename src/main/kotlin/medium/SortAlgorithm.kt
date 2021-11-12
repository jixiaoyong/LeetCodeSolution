package medium

/**
 * 从小到大排序
 * 各种排序算法思想对比：
 * [算法总结 十大排序算法 (附示意图)] https://zhuanlan.zhihu.com/p/42586566
 * [九大经典排序算法总结]https://www.upupcode.com/2020/01/07/%E4%B9%9D%E5%A4%A7%E7%BB%8F%E5%85%B8%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E6%80%BB%E7%BB%93/#%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F
 */
object SortAlgorithm {

    // 冒泡排序：每次比较相邻的两个数，如果前一个比后一个大，则交换位置
    fun popSort(array: Array<Int>) {
        if (array.isEmpty() || array.size == 1) {
            return
        }
        array.forEachIndexed { index, i ->
            val subListLen = array.size - index - 2
            for (x in 0..subListLen) {
                if (array[x] > array[x + 1]) {
                    val max = array[x + 1]
                    array[x + 1] = array[x]
                    array[x] = max
                }
            }
        }
    }

    // 选择排序：每次选择最小的数，放在最前面，相比冒泡排序，选择排序更加简单，减少了交换次数
    fun selectSort(array: Array<Int>) {
        if (array.isEmpty() || array.size == 1) {
            return
        }
        array.forEachIndexed { index, item ->
            var minIndex = index
            var minValue = item
            for (x in index + 1 until array.size) {
                // find the smallest value and index
                if (minValue > array[x]) {
                    minIndex = x
                    minValue = array[x]
                }
            }

            // exchange the smallest and current item
            array[index] = minValue
            array[minIndex] = item
        }
    }

    // 插入排序：每次将一个数插入到已排好序的有序数组中，直到插入到正确的位置
    fun insertSort(array: Array<Int>) {
        if (array.isEmpty() || array.size == 1) {
            return
        }
        for (x in 1 until array.size) {
            if (array[x] < array[x - 1]) {
                // 将array[x]插入到正确的位置
                for (j in 0 until x) {
                    // 如果要插入的值array[x]小于已经排好序的列表中某个值，则将他两替换位置
                    if (array[x] < array[j]) {
                        val min = array[x]
                        array[x] = array[j]
                        array[j] = min
                    }
                }
            }
        }
    }

    /**
     * 希尔排序：是插入排序的升级版，排序过程中，令gap=array大小的一半，将数组按照间隔gap分为n个 1对1的子数组，比较子数组中的元素，将最小的放在前面；
     * 然后再将gap缩小为原来的一半，重复上述操作，直到gap=1，排序完成
     *
     * 无论是插入排序还是冒泡排序，如果数组的最大值刚好是在第一位，要将它挪到正确的位置就需要 n - 1 次移动。
     */
    fun shellSort(array: Array<Int>) {
        if (array.isEmpty() || array.size == 1) {
            return
        }
        var gap = array.size / 2
        while (gap > 0) {
            for (x in gap until array.size) {
                if (array[x] < array[x - gap]) {
                    val min = array[x]
                    array[x] = array[x - gap]
                    array[x - gap] = min
                }
            }
            gap /= 2
        }
    }

    /**
     * 归并排序,从大到小
     */
    fun mergeSort(arr: IntArray) {
        val temp = IntArray(arr.size)
        internalMergeSort(arr, temp, 0, arr.size - 1)
    }

    private fun internalMergeSort(arr: IntArray, temp: IntArray, left: Int, right: Int) {
        //当left==right的时，已经不需要再划分了
        if (left < right) {
            val middle = (left + right) / 2
            internalMergeSort(arr, temp, left, middle) //左子数组
            internalMergeSort(arr, temp, middle + 1, right) //右子数组
            mergeSortedArray(arr, temp, left, middle, right) //合并两个子数组
        }
    }

    // 合并两个有序子序列
    private fun mergeSortedArray(arr: IntArray, temp: IntArray, left: Int, middle: Int, right: Int) {
        var i = left
        var j = middle + 1
        var k = 0
        while (i <= middle && j <= right) {
            temp[k++] = if (arr[i] <= arr[j]) arr[i++] else arr[j++]
        }
        while (i <= middle) {
            temp[k++] = arr[i++]
        }
        while (j <= right) {
            temp[k++] = arr[j++]
        }
        //把数据复制回原数组
        i = 0
        while (i < k) {
            arr[left + i] = temp[i]
            ++i
        }
    }

    // 归并排序：将两个有序的列表合并称为归并
//    fun mergeSort(array: Array<Int>, startIndex: Int = 0, endIndex: Int = array.size - 1) {
//        if (array.isEmpty() || array.size == 1 || startIndex == endIndex || endIndex - startIndex == 1) {
//            return
//        }
//        // 将2个列表排序
//        val middleIndex = (startIndex + endIndex) / 2
//        mergeSort(array, startIndex, middleIndex)
//        mergeSort(array, middleIndex, endIndex)
//
//        // 合并2个有序列表
//        val startLen = middleIndex - startIndex
//        val endLen = endIndex - middleIndex
//        var beginIndex = startIndex
//        var finishIndex = middleIndex
//        if (startLen < endLen) {
//            mergeTwoSortedList(array, middleIndex, endIndex, startIndex, middleIndex)
//        }else{
//            mergeTwoSortedList(array, startIndex, middleIndex, middleIndex, endIndex)
//        }
//    }
//
//    /**
//     * 需要保证长列表在前，短列表在后
//     */
//    fun mergeTwoSortedList(array: Array<Int>, startIndex1: Int, endIndex1: Int, startIndex2: Int, endIndex2: Int) {
//        val copyArray = array.copyOf()
//        var array1Index = startIndex1
//        var array2Index = startIndex2
//
//        var currentIsFirst = true
//        for (i in startIndex1..endIndex1) {
//            if (array2Index <= endIndex2) {
//                var otherValue = 0
//                val mainValue = if (currentIsFirst) {
//                    otherValue = copyArray[array2Index]
//                    copyArray[array1Index++]
//                } else {
//                    otherValue = copyArray[array1Index]
//                    copyArray[array2Index++]
//                }
//                val min = if (otherValue < mainValue) {
//                    currentIsFirst = false
//                    otherValue
//                } else {
//                    mainValue
//                }
//                array[i] = min
//            } else {
//                array[i] = copyArray[array1Index++]
//            }
//        }
//    }

    @JvmStatic
    fun main(args: Array<String>) {
        val testCaseList = arrayListOf(

            "3,2,3,1,2,4,5,5,6",
            "3,2,3,-1,2,4,-5,5,-6",
            "3,2,3,-1,2,4,-5,5,-6",
            "3,2,3,1,2,4,5,5,6",
            "3,3,3,3,3",
        )

        testCaseList.forEach {
            val array = it.split(",").map { it.toInt() }.toTypedArray()
//            mergeTwoSortedList(array, 0, array.size / 2 + 1, array.size / 2 + 1, array.size)
            mergeSort(array.toIntArray())
            println("${it} -> ${array.joinToString(",")} ")
        }
    }

}