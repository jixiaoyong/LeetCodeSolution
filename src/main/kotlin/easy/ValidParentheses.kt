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
    /**
     * 148 ms	33.9 MB
     */
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

    /**
     * 669 ms	66.8 MB
     */
    fun isValid2(s: String): Boolean {
        val lengthHalf = s.length / 2
        var newS = s
        for (i in 0..lengthHalf) {
            newS = newS.replace(Regex("\\(\\)|\\[\\]|\\{}"), "")
            if (newS.isEmpty()) {
                return true
            }
        }
        return newS.isEmpty()
    }

    /**
     * 420 ms	90.4 MB
     */
    fun isValid3(s: String): Boolean {
        val lengthHalf = s.length / 2
        var newS = s
        for (i in 0..lengthHalf) {
            newS = newS.replace("()", "").replace("[]", "").replace("{}", "")
            if (newS.isEmpty()) {
                return true
            }
        }
        return newS.isEmpty()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(isValid3(")"))
        println(isValid3("()"))
        println(isValid3("{}"))
        println(isValid3("()[]{}"))
        println(isValid3("(]"))
        println(isValid3("([)]"))
        println(isValid3("{[()]}"))
    }
}