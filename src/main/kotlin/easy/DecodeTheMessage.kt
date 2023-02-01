package easy

/**
 * @author : jixiaoyong
 * @description ： 2325. 解密消息
 * 给你字符串 key 和 message ，分别表示一个加密密钥和一段加密消息。解密 message 的步骤如下：
 *
 * 使用 key 中 26 个英文小写字母第一次出现的顺序作为替换表中的字母 顺序 。
 * 将替换表与普通英文字母表对齐，形成对照表。
 * 按照对照表 替换 message 中的每个字母。
 * 空格 ' ' 保持不变。
 * 例如，key = "happy boy"（实际的加密密钥会包含字母表中每个字母 至少一次），据此，可以得到部分对照表（'h' -> 'a'、'a' -> 'b'、'p' -> 'c'、'y' -> 'd'、'b' -> 'e'、'o' -> 'f'）。
 * 返回解密后的消息。
 *
 * 26 <= key.length <= 2000
 * key consists of lowercase English letters and ' '.
 * key contains every letter in the English alphabet ('a' to 'z') at least once.
 * 1 <= message.length <= 2000
 * message consists of lowercase English letters and ' '.
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2/1/2023
 */
class DecodeTheMessage {
    fun decodeMessage(key: String, message: String): String {
        val hashMap = hashMapOf<Char, Char>()
        hashMap.put(' ', ' ')
        var curChar = 'a'

        for (c in key) {
            if (c == ' ') {
                continue
            }

            if (!hashMap.contains(c)) {
//                hashMap.put(c, Char(curChar++))
                hashMap.put(c, curChar++)
            }

            if (hashMap.keys.size == 27) {
                break
            }
        }

        val result = CharArray(message.length) { index ->
            hashMap[message[index]]!!
        }

        return String(result)
    }
}

fun main() {
    val obj = DecodeTheMessage()
    val cases = mutableListOf(
        arrayOf("the quick brown fox jumps over the lazy dog", "vkbs bs t suepuv", "this is a secret"),
        arrayOf(
            "eljuxhpwnyrdgtqkviszcfmabo",
            "zwx hnfx lqantp mnoeius ycgk vcnjrdb",
            "the five boxing wizards jump quickly"
        ),
        arrayOf("ecs the quick brown fox jumps over the lazy dog", "vkbs bs t suepuv"),
    )

    cases.forEach {
        val res = obj.decodeMessage(it[0], it[1])
        println("$res   --- isSame:${if (it.size == 2) "--" else res == it.getOrNull(2)}")
    }
}