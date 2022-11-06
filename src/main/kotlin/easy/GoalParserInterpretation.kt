package easy

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2022/11/6
 * description: 1678. 设计 Goal 解析器
 *
 * 请你设计一个可以解释字符串 command 的 Goal 解析器 。command 由 "G"、"()" 和/或 "(al)" 按某种顺序组成。
 * Goal 解析器会将 "G" 解释为字符串 "G"、"()" 解释为字符串 "o" ，"(al)" 解释为字符串 "al" 。
 * 然后，按原顺序将经解释得到的字符串连接成一个字符串。
 * 给你字符串 command ，返回 Goal 解析器 对 command 的解释结果。
 *
 * 提示：
 * 1 <= command.length <= 100
 * command 由 "G"、"()" 和/或 "(al)" 按某种顺序组成
 *
 * https://leetcode.cn/problems/goal-parser-interpretation/
 */
class GoalParserInterpretation {

    /**
     * 	136 ms	32.9 MB
     * 	只需遍历一次，遇到i处为"("则向后查看，如果i+1处是")"则将i和i+1处替换为"o"，如果i+1处是a则遇到了"(al)"，将i到i+3都替换为al
     * 	时间复杂度O(N)，最多查询N次
     * 空间复杂度：O(1)。除返回值以外不需要额外的空间。
     */
    fun interpret(command: String): String {
        var index = 0
        val len = command.length

        val sb = StringBuffer()
        while (index < len) {
            val char = command[index]
            if ('(' == char) {
                if (')' == command[index + 1]) {
                    sb.append('o')
                    index += 2
                } else if ('a' == command[index + 1]) {
                    sb.append("al")
                    index += 4
                }

            } else {
                sb.append(char)
                index++
            }
        }

        return sb.toString()
    }


    /**
     * 228 ms	36.2 MB
     * 内置算法，两次遍历
     * 时间复杂度O(N)
     */
    fun interpretInnerMethod(command: String): String {
        return command.replace("(al)", "al").replace("()", "o")
    }
}


fun main() {
    val obj = GoalParserInterpretation()
    val cases = listOf<String>("G", "()", "()(al)G", "G()()()()(al)", "(al)G(al)()()G")

    cases.forEach {
        println("${obj.interpret(it)}  --> $it")
    }
}