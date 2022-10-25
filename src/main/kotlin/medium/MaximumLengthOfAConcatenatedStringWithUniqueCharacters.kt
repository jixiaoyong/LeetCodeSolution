package medium

/**
 * @author : jixiaoyong
 * @description ： 1239. 串联字符串的最大长度
 * 给定一个字符串数组 arr，字符串 s 是将 arr 的含有 不同字母 的 子序列 字符串 连接 所得的字符串。
 * 请返回所有可行解 s 中最长长度。
 * 子序列 是一种可以从另一个数组派生而来的数组，通过删除某些元素或不删除元素而不改变其余元素的顺序。
 *
 * 提示：
 *
 * 1 <= arr.length <= 16
 * 1 <= arr[i].length <= 26
 * arr[i] 中只含有小写英文字母
 *
 * https://leetcode.cn/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
 *
 * 此题破题的关键之一，在于通过<< & | 位运算 使用int（32个比特位）保存一个string中各个字符a-z的有无。
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/24/2022
 */
class MaximumLengthOfAConcatenatedStringWithUniqueCharacters {

    /**
     * 回溯法 + 位运算
     * 时间复杂度：O(|Σ|+2^n)。其中 ∣Σ∣ 是 arr 中所有字符串的长度之和，n 是 arr 的长度。
     * 遍历所有字符串需要 O(∣Σ∣)，回溯时由于每个元素有选或不选两种情况，最坏情况下会有 2^n种组合方式。
     * 空间复杂度：O(n)。
     */
    var result = 0
    fun maxLength(arr: List<String>): Int {

        val masks = mutableListOf<Int>()
        arr.forEach loop@{
            var strNum = 0
            it.forEach {
                // 即 val c = 1 << (it - 'a')，在二进制层面，将1向左移动n个位置
                val charNum = 1 shl (it - 'a')
                if (strNum and charNum != 0) {
                    // 如果与运算不为0，则说明str中有重复字符，不用参与后续运算
                    return@loop
                }
                strNum = strNum or charNum
            }
            masks.add(strNum)
        }

        backTrack(masks, 0, 0)
        return result
    }

    /**
     * 回溯法
     * 每一个字符，分别按照连接后续字符 or 不连接后续字符 计算
     */
    fun backTrack(masks: List<Int>, currentIndex: Int, mask: Int) {
        if (currentIndex == masks.size) {
            result = Math.max(result, Integer.bitCount(mask))
            return
        }

        val curMask = masks[currentIndex]
        if (curMask and mask == 0) {
            backTrack(masks, currentIndex + 1, mask or curMask)
        }

        backTrack(masks, currentIndex + 1, mask)
    }


    /**
     * Leetcode 官方解法
     * 迭代 + 位运算
     * 时间复杂度：O(∣Σ∣+2^n) ，遍历所有的字符串需要∣Σ∣（字符串长度之和），每次循环会将 masks 的大小增加至多一倍，
     * 因此总的时间复杂度为 O(∣Σ∣+2^0+2^1+...+2^{n-1})=O(∣Σ∣+2^n)
     * 空间复杂度：O(2^n) 最坏情况所有aar子级都可以构成可行解，有2^n个（每个string有参与、不参与拼接两种情况）
     */
    public fun maxLength2(arr: List<String>): Int {
        var result = 0
        val masks = mutableListOf<Int>()
        val aCode = 'a'.code
        // 添加一个“”以便于各个str连接成单个字符串
        masks.add(0)

        arr.forEach stringLoop@{
            var strMask = 0
            var finish = true
            it.forEach { char ->
                val charNum = 1 shl (char.code - aCode)
                if (strMask and charNum != 0) {
                    finish = false
                    return@stringLoop
                }
                strMask = strMask or charNum
            }
            if (finish) {
                masks.add(strMask)
            }

            for (x in 0 until masks.size) {
                val curMask = masks[x]
                if (curMask and strMask == 0) {
                    val mask = strMask or curMask
                    result = Math.max(result, Integer.bitCount(mask))
                    masks.add(mask)
                }
            }
        }

        return result
    }
}

fun main() {
    val obj = MaximumLengthOfAConcatenatedStringWithUniqueCharacters()

    val cases = mutableListOf(
        listOf("uu","u"),//0
        listOf("un", "iq", "ue"),//4
        listOf("un", "iq", "ae", "bcd"),//9
    )

    cases.forEach {
        println("${obj.maxLength2(it)}   -> ${it.joinToString()}")
    }
}