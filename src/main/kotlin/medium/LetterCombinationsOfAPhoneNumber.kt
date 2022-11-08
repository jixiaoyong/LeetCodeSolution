package medium

/**
 * @author : jixiaoyong
 * @description ： 17. 电话号码的字母组合
 * 给定一个仅包含数字2-9的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * ![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2021/11/09/200px-telephone-keypad2svg.png)
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 0 <= digits.length <= 4
 * digits[i] 是范围 ['2', '9'] 的一个数字。
 *
 * https://leetcode.cn/problems/letter-combinations-of-a-phone-number/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/8/2022
 */
class LetterCombinationsOfAPhoneNumber {

    val hashMap = hashMapOf(
        Pair('2', "abc"), Pair('3', "def"), Pair('4', "ghi"), Pair('5', "jkl"),
        Pair('6', "mno"), Pair('7', "pqrs"), Pair('8', "tuv"), Pair('9', "wxyz")
    )
    val result = mutableListOf<String>()

    /**
     * 回溯法
     * 使用hashMap存储数字与字符对应关系，然后每次取出一个数字对应的字符串，依次取该字符串的字符与之前的prefix组合，作为后续字符的prefix。
     * 如此遍历直到遍历完成[digits]，得到结果。
     *
     * 时间复杂度：O(3^m*4^n)，m、n分别是[digits]中对应字符串长度为3和4的数字个数
     * 空间复杂度：O(m+n)，除了返回值[result]以外，空间复杂度主要取决于[hashMap]和递归调用层数：
     *  [hashMap]表大小固定，与输入无关，而递归层数最大为m+n次
     */
    fun letterCombinations(digits: String): List<String> {
        result.clear()
        if (digits.isEmpty()) {
            return result
        }

        letterCombinations("", digits, 0)

        return result
    }

    private fun letterCombinations(prefix: String, digits: String, curIndex: Int) {
        if (curIndex == digits.length - 1) {
            hashMap[digits[curIndex]]?.forEach {
                result.add("$prefix$it")
            }
        } else {
            hashMap[digits[curIndex]]?.forEach {
                letterCombinations("$prefix$it", digits, curIndex + 1)
            }
        }

    }
}

fun main() {
    val obj = LetterCombinationsOfAPhoneNumber()
    val cases = mutableListOf("", "2", "23", "3", "7799")

    cases.forEach {
        println("$it   --->   ${obj.letterCombinations(it).joinToString()} ")
    }
}