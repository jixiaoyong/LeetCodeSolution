package easy

import java.util.*


/**
 * @author : jixiaoyong
 * @description ：剑指 Offer 09. 用两个栈实现队列
 * https://leetcode.cn/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/27/2022
 */

/**
 * 基于官方Stack，底层实现是数组
 * 636 ms	61.2 MB
 * 空间复杂度 O(N)。最差情况下两个栈保存N个对象
 */
class CQueueStack {

    private var stackPositive = Stack<Int>()
    private var stackReverse = Stack<Int>()

    // 时间复杂度 O(1)
    fun appendTail(value: Int) {
        stackPositive.push(value)
    }

    // 时间复杂度 O(N)
    fun deleteHead(): Int {
        if (!stackReverse.empty()) {
            return stackReverse.pop()
        }

        if (stackPositive.empty()) {
            return -1
        }

        // 将正序的数字倒序放入stackReverse，之后再pop
        stackReverse = reverseStack(stackPositive)
        return stackReverse.pop()
    }

    private fun reverseStack(stackSource: Stack<Int>): Stack<Int> {
        val stackTarget = Stack<Int>()
        while (!stackSource.empty()) {
            val item = stackSource.pop()
            stackTarget.push(item)
        }
        return stackTarget
    }

    override fun toString(): String {
        return "r:$stackReverse,p:$stackPositive"
    }
}

/**
 * 556 ms	60.2 MB
 */
class CQueue {

    private var stackPositive = LinkedList<Int>()
    private var stackReverse = LinkedList<Int>()

    fun appendTail(value: Int) {
        stackPositive.push(value)
    }

    fun deleteHead(): Int {
        if (!stackReverse.isEmpty()) {
            return stackReverse.pop()
        }

        if (stackPositive.isEmpty()) {
            return -1
        }

        // 将正序的数字倒序放入stackReverse，之后再pop
        stackReverse = reverseStack(stackPositive)
        return stackReverse.pop()
    }

    private fun reverseStack(stackSource: LinkedList<Int>): LinkedList<Int> {
        val stackTarget = LinkedList<Int>()
        while (!stackSource.isEmpty()) {
            val item = stackSource.pop()
            stackTarget.push(item)
        }
        return stackTarget
    }

    override fun toString(): String {
        return "r:$stackReverse,p:$stackPositive"
    }
}
/**
 * Your CQueue object will be instantiated and called as such:
 * var obj = CQueue()
 * obj.appendTail(value)
 * var param_2 = obj.deleteHead()
 */

fun main() {
    val obj = CQueue()
    obj.appendTail(21)
    obj.appendTail(3)
    obj.appendTail(13)
    obj.appendTail(2)
    println("$obj.deleteHead():${obj.deleteHead()}")
    obj.appendTail(12)
    obj.appendTail(22)
    println("$obj.deleteHead():${obj.deleteHead()}")
    obj.appendTail(32)
    println("$obj.deleteHead():${obj.deleteHead()}")
    println("$obj.deleteHead():${obj.deleteHead()}")
    println("$obj.deleteHead():${obj.deleteHead()}")
    println("$obj.deleteHead():${obj.deleteHead()}")
    println("$obj.deleteHead():${obj.deleteHead()}")
    println("$obj.deleteHead():${obj.deleteHead()}")
}