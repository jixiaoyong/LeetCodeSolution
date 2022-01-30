package easy

import java.util.*

/*
* @description: 232. 用栈实现队列
* https://leetcode-cn.com/problems/implement-queue-using-stacks/
*
* 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：

实现 MyQueue 类：

void push(int x) 将元素 x 推到队列的末尾
int pop() 从队列的开头移除并返回元素
int peek() 返回队列开头的元素
boolean empty() 如果队列为空，返回 true ；否则，返回 false
说明：

你 只能 使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/implement-queue-using-stacks
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2022/1/30
*/
class ImplementQueueUsingStacks {

    // 正常顺序栈
    var stack1 = Stack<Int>()

    // 反向顺序栈
    var stack2 = Stack<Int>()

    /**
     * 将元素 x 推到队列的末尾
     */
    fun push(x: Int) {
        stack1.push(x)

        val list = mutableListOf<Int>()
        while (stack1.isNotEmpty()) {
            list.add(stack1.pop())
        }
        stack2 = Stack<Int>()
        list.forEach {
            stack2.push(it)
        }

        list.reversed().forEach {
            stack1.push(it)
        }
    }

    /**
     * 从队列的开头移除并返回元素
     */
    fun pop(): Int {
        val first = stack2.pop()

        val list = mutableListOf<Int>()
        while (stack2.isNotEmpty()) {
            list.add(stack2.pop())
        }
        stack1 = Stack<Int>()
        list.forEach {
            stack1.push(it)
        }

        list.reversed().forEach {
            stack2.push(it)
        }

        return first
    }

    /**
     * 返回队列开头的元素
     */
    fun peek(): Int {
        return stack2.peek()
    }

    /**
     * 如果队列为空，返回 true ；否则，返回 false
     */
    fun empty(): Boolean {
        return stack2.isEmpty()
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * var obj = MyQueue()
 * obj.push(x)
 * var param_2 = obj.pop()
 * var param_3 = obj.peek()
 * var param_4 = obj.empty()
 */

fun main() {
    val queue = ImplementQueueUsingStacks()
    queue.push(1)
    queue.push(2)
    queue.push(3)
    println(queue.peek())
    println(queue.pop())
    println(queue.pop())
    println(queue.pop())
    println(queue.empty())
}