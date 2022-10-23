package easy

import kotlin.random.Random

/**
 * @author : jixiaoyong
 * @description ： 645. 错误的集合
 * 集合 s 包含从 1 到 n 的整数。
 * 不幸的是，因为数据错误，导致集合里面某一个数字复制了成了集合里面的另外一个数字的值，导致集合 丢失了一个数字 并且 有一个数字重复 。
 * 给定一个数组 nums 代表了集合 S 发生错误后的结果。
 * 请你找出重复出现的整数，再找到丢失的整数，将它们以数组的形式返回。
 *
 * 提示：
 *
 * 2 <= nums.length <= 10^4
 * 1 <= nums[i] <= 10^4
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/23/2022
 */
class SetMismatch {

    /**
     * 336 ms	38.5 MB
     * 时间复杂度 O(n log(n))
     * 空间复杂度 O(log(n))
     */
    fun findErrorNums(nums: IntArray): IntArray {
        var lost = Int.MIN_VALUE
        var duplicate = lost
        var pre = lost
        var offset = 0

        nums.sort()
        for (x in 0 until nums.size) {
            if (pre == nums[x]) {
                duplicate = pre
                if (lost != Int.MIN_VALUE) {
                    break
                }
                offset = -1
            }
            pre = nums[x]
            if (lost == Int.MIN_VALUE && pre != x + 1 + offset) {
                // 找到了缺失的
                lost = x + 1 + offset
                if (duplicate != Int.MIN_VALUE) {
                    break
                }
            }
        }

        if (lost == Int.MIN_VALUE) {
            lost = nums.size
        }

        return intArrayOf(duplicate, lost)
    }

    /**
     * 352 ms	38 MB
     * Hash表
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    fun findErrorNumsHash(nums: IntArray): IntArray {
        var lost = Int.MIN_VALUE
        var duplicate = lost

        val hashMap = hashMapOf<Int, Int>()
        for (x in 0 until  nums.size) {
            hashMap.put(x + 1, 1)
        }

        for (x in 0 until nums.size) {
            val num = nums[x]
            val count = hashMap.getOrDefault(num,0)
            if (count == 1) {
                // 所有数字都默认为1，说明这个值是第一次发现，移除它
                hashMap.remove(num)
            }else{
                // 等于0，说明之前已经出现过一次了，那么这个数字是重复的数字
                duplicate = num
                hashMap.remove(num)
            }
        }

        // 到这里hashMap里面还有一个数字，因为缺失，所以是lost
        lost = hashMap.keys.first()

        return intArrayOf(duplicate, lost)
    }

    /**
     * 312 ms	37.8 MB
     * Hash表 LeetCode官方题解，无需移除hashMap项目
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    fun findErrorNumsHashLeetCode(nums: IntArray): IntArray {
        var lost = Int.MIN_VALUE
        var duplicate = lost

        val hashMap = hashMapOf<Int, Int>()
        nums.forEach {
            val count = hashMap.getOrDefault(it,0)+1
            hashMap.put(it,count)
        }

        for (x in 1 .. nums.size) {
            val count = hashMap.getOrDefault(x,0)
            if (count == 0) {
                lost = x
            }else if (count==2){
                duplicate = x
            }
        }


        return intArrayOf(duplicate, lost)
    }
}

fun main() {
    val obj = SetMismatch()
    val random = Random(System.currentTimeMillis())

    val cases =
        mutableListOf(
            intArrayOf(1, 5, 3, 2, 2, 7, 6, 4, 8, 9), //少了10，但是多了2 重复在中间，缺失在末尾
            intArrayOf(3, 2, 3, 4, 6, 5), //少了1，但是多了3，而不是2 重复在中间，缺失在头部
            intArrayOf(1, 2, 2, 4),// 重复、缺失在中间
            intArrayOf(1, 1),// 重复、缺失在头部
            intArrayOf(3, 2, 2),// 重复、缺失在头部
            intArrayOf(1, 1, 3, 4, 5),// 重复在头部，缺失在末尾
            intArrayOf(1, 2, 3, 4, 4)// 重复、缺失在末尾
        )

    val largestCase = IntArray(10000) { i -> i + 1 }
    val index = random.nextInt(1, 10000)
    println("randomNum:$index ,nums:(old ${largestCase[index]}  --> ${largestCase[index - 1]})")
    largestCase[index] = largestCase[index - 1]
    cases.add(largestCase)

    val timeMs = System.currentTimeMillis()

//    for (x in 0..1000) {
        cases.forEach {
//            val result = obj.findErrorNums(it)// 1000次，920 ms
//            val result = obj.findErrorNumsHash(it)// 1000次， 1168 ms
            val result = obj.findErrorNumsHashLeetCode(it)// 1000次， 1165 ms
            println("${result.joinToString()} --> ${it.joinToString()}")
        }
//    }

    println("total time ${System.currentTimeMillis() - timeMs} ms")
}