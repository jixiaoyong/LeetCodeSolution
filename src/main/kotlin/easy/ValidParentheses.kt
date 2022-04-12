package easy

import java.util.*

/*
* @description: 20. Valid Parentheses (括号)
* Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
* An input string is valid if:

Open brackets (支架) must be closed by the same type of brackets.
Open brackets (支架) must be closed in the correct order.

* https://leetcode.com/problems/valid-parentheses/
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 22/04/12
*/
object ValidParentheses {
    fun isValid(s: String): Boolean {
        val stack = Stack<Char>()

        for (c in s) {
            when (c) {
                '(' -> stack.push(c)
                ')' -> if (stack.isEmpty() || stack.pop() != '(') return false
                '[' -> stack.push(c)
                ']' -> if (stack.isEmpty() || stack.pop() != '[') return false
                '{' -> stack.push(c)
                '}' -> if (stack.isEmpty() || stack.pop() != '{') return false
            }
        }

        return stack.isEmpty()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(isValid(")"))
        println(isValid("()"))
        println(isValid("()[]{}"))
        println(isValid("(]"))
        println(isValid("([)]"))
        println(isValid("{[]}"))
    }
}