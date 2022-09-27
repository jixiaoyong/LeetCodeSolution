package easy

import java.util.LinkedList

/**
 * @author : jixiaoyong
 * @description ：225. 用队列实现栈
 * 请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。
 * 只能使用队列的基本操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 这些操作。
 * https://leetcode.cn/problems/implement-stack-using-queues/
 * @email : jixiaoyong1995@gmail.com
 * @date : 9/27/2022
 */
class MyStack {

    // 172 ms	34.8 MB
    var queueFirst = LinkedList<Int>()
    var queueSecond = LinkedList<Int>()

    fun push(x: Int) {
        if (!queueFirst.isEmpty()) {
            queueFirst.addLast(x)
        } else if (!queueSecond.isEmpty()) {
            queueSecond.addLast(x)
        } else {
            queueFirst.addLast(x)
        }
    }

    fun pop(): Int {
        return if (!queueFirst.isEmpty()) {
            while (queueFirst.size > 1) {
                val first = queueFirst.pop()
                queueSecond.addLast(first)
            }
            if (!queueFirst.isEmpty()) {
                queueFirst.pop()
            } else {
                -1
            }
        } else {
            while (queueSecond.size > 1) {
                val first = queueSecond.pop()
                queueFirst.addLast(first)
            }
            if (!queueSecond.isEmpty()) {
                queueSecond.pop()
            } else {
                -1
            }
        }
    }

    fun top(): Int {

        var lastValue = -1

        if (!queueFirst.isEmpty()) {
            while (!queueFirst.isEmpty()) {
                lastValue = queueFirst.pop()
                queueSecond.addLast(lastValue)
            }
        } else {
            while (!queueSecond.isEmpty()) {
                lastValue = queueSecond.pop()
                queueFirst.addLast(lastValue)
            }
        }
        return lastValue
    }

    fun empty(): Boolean {
        return queueFirst.size + queueSecond.size == 0
    }

}

/**
 * 官方题解
 * https://leetcode.cn/problems/implement-stack-using-queues/solution/yong-dui-lie-shi-xian-zhan-by-leetcode-solution/
 */
class MyStackOffice {

    // 156 ms	34.8 MB
    var queueFirst = LinkedList<Int>()
    var queueSecond = LinkedList<Int>()

    fun push(x: Int) {
        queueSecond.addLast(x)

        // 逆转queueFirst顺序：从1，逆转为x,1，也就是说最后入queue的数字X在其第一位，满足LIFO
        while (!queueFirst.isEmpty()) {
            queueSecond.addLast(queueFirst.pop())
        }

        queueFirst = queueSecond
        queueSecond = LinkedList()
    }

    fun pop(): Int {
        return queueFirst.pop()
    }

    fun top(): Int {
        return queueFirst.peek()
    }

    fun empty(): Boolean {
        return queueFirst.isEmpty()
    }

}

/**
 * Your MyStack object will be instantiated and called as such:
 * var obj = MyStack()
 * obj.push(x)
 * var param_2 = obj.pop()
 * var param_3 = obj.top()
 * var param_4 = obj.empty()
 */

fun main() {
    val myStack = MyStackOffice()
    myStack.push(1)
    myStack.push(2)
    println(myStack.top())
    println(myStack.top())
    println(myStack.pop())
    println(myStack.pop())
    println(myStack.empty())
}