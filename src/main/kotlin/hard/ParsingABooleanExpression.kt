package hard

import java.util.Stack

/**
 * @author : jixiaoyong
 * @description ： 1106. 解析布尔表达式
 * 给你一个以字符串形式表述的布尔表达式（boolean） expression，返回该式的运算结果。
 *
 * 有效的表达式需遵循以下约定：
 *
 * "t"，运算结果为 True
 * "f"，运算结果为 False
 * "!(expr)"，运算过程为对内部表达式 expr 进行逻辑 非的运算（NOT）
 * "&(expr1,expr2,...)"，运算过程为对 2 个或以上内部表达式 expr1, expr2, ... 进行逻辑 与的运算（AND）
 * "|(expr1,expr2,...)"，运算过程为对 2 个或以上内部表达式 expr1, expr2, ... 进行逻辑 或的运算（OR）
 *
 * 提示：
 *
 * 1 <= expression.length <= 20000
 * expression[i] 由 {'(', ')', '&', '|', '!', 't', 'f', ','} 中的字符组成。
 * expression 是以上述形式给出的有效表达式，表示一个布尔值。
 *
 * https://leetcode.cn/problems/parsing-a-boolean-expression/
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/5/2022
 */
class ParsingABooleanExpression {

    /**
     * 236 ms	39.3 MB
     * 思路是，依次遍历[expression]，使用两个Stack分别存储要计算的字符charStack和操作符号operationStack，每次遇到一个')'时，
     * 向后查找到最近的一个'('确定要计算的字符，然后从operationStack中取距其最近的操作符对其进行计算，将计算结果加入charStack中，
     * 如此直到完全遍历完[expression]，最后charStack中就是计算结果
     *
     * 时间复杂度：O(N) ，N为[expression]的长度，最坏可能需要遍历完整个字符[expression]，然后再依次将各个字符倒序遍历一次计算结果
     * 空间复杂度：O(N) 需要Stack来存储其值
     */
    fun parseBoolExpr(expression: String): Boolean {
        val operations = listOf('!', '&', '|')

        val exprLen = expression.length
        if (exprLen == 1) {
            return expression.first() == 't'
        }

        val charStack = Stack<Char>()
        val operationStack = Stack<Char>()
        expression.forEach {
            if (it == ',') {
                return@forEach
            }
            if (operations.contains(it)) {
                operationStack.push(it)
                return@forEach
            }
            if (')' != it) {
                charStack.push(it)
            } else {
                val oper = operationStack.pop()
                if ('!' == oper) {
                    val localResult = !(charStack.pop() == 't')
                    charStack.pop()// remove '('
                    charStack.push(if (localResult) 't' else 'f')
                } else if ('&' == oper) {
                    // &&
                    var localResult = charStack.pop() == 't'
                    while ('(' != charStack.peek()) {
                        localResult = (charStack.pop() == 't') && localResult
                    }
                    charStack.pop()// remove '('
                    charStack.push(if (localResult) 't' else 'f')
                } else {
                    // ||
                    var localResult = charStack.pop() == 't'
                    while ('(' != charStack.peek()) {
                        localResult = (charStack.pop() == 't') || localResult
                    }
                    charStack.pop()// remove '('
                    charStack.push(if (localResult) 't' else 'f')
                }

            }
        }

        return charStack.pop() == 't'
    }


    /**
     * 236 ms	37.3 MB
     * https://leetcode.cn/problems/parsing-a-boolean-expression/solution/jie-xi-bu-er-biao-da-shi-by-leetcode-sol-vmvg/
     * Leetcode官方题解，思路与上述相似，但是在找到‘）’之后，并不实际计算，而是统计’t‘和’f‘的个数分别为t和f，最终按照下列规则计算结果：
     * 1）操作符是! ，f==1则为ture（对false取反结果为true）
     * 2）操作符是& ，f==0则为ture（逻辑与运算只有全为true结果才为true）
     * 3）操作符是| ，t>=1则为ture（逻辑或运算，只有有一个true结果就为true）
     * 时间复杂度：O(n)，其中 n 是布尔表达式 expression 的长度。需要遍历布尔表达式一次并解析。
     * 空间复杂度：O(n)，其中 n 是布尔表达式 expression 的长度。空间复杂度主要取决于栈空间，栈内字符个数不超过 n。
     */
    fun parseBoolExprLeetcode(expression: String): Boolean {
        val operations = listOf('!', '&', '|')

        val exprLen = expression.length
        if (exprLen == 1) {
            return expression.first() == 't'
        }

        val charStack = Stack<Char>()
        val operationStack = Stack<Char>()
        expression.forEach {
            if (it == ',') {
                return@forEach
            }
            if (operations.contains(it)) {
                operationStack.push(it)
                return@forEach
            }
            if (')' != it) {
                charStack.push(it)
            } else {
                val oper = operationStack.pop()
                var t = 0
                var f = 0
                while ('(' != charStack.peek()) {
                    if ('t' == charStack.pop()) {
                        t++
                    } else {
                        f++
                    }
                }

                charStack.pop()// remove '('

                val localResult = when (oper) {
                    '!' -> {
                        f == 1
                    }

                    '&' -> {
                        f == 0
                    }

                    else -> {
                        t > 0
                    }
                }
                charStack.push(if (localResult) 't' else 'f')
            }
        }

        return charStack.pop() == 't'
    }

}

fun main() {
    val obj = ParsingABooleanExpression()
    val cases = mutableListOf(
        "|(&(t,f,t),!(f),&(f,t,f))",
        "t", "!(t)", "|(f,t)", "&(t,f)", "|(&(t,f,t),!(t))", "|(&(t,f,t),!(t),&(f,f,f))", "!(!(!(t)))"
    )
    var longestExpressionString = "!(t)"
    for (x in 1..6665) {
        longestExpressionString = "!($longestExpressionString)"
    }
    cases.add(longestExpressionString)// true
    println(longestExpressionString)

    cases.forEach {
        val result = obj.parseBoolExpr(it)
        val result2 = obj.parseBoolExprLeetcode(it)
        println("$result / $result2 -> ${if (it.length < 1000) it else it.length}")
    }
}