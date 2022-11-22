package hard

/**
 * @author : jixiaoyong
 * @description ： 878. 第 N 个神奇数字
 * 一个正整数如果能被 a 或 b 整除，那么它是神奇的。
 *
 * 给定三个整数 n ,a , b ，返回第 n 个神奇的数字。因为答案可能很大，所以返回答案对109+ 7 取模后的值。
 *
 * 提示：
 *
 * 1 <= n <= 10^9
 * 2 <= a, b <= 4 * 10^4
 *
 * 掌握程度 ★☆☆☆☆
 * https://leetcode.cn/problems/nth-magical-number/
 * @email : jixiaoyong1995@gmail.com
 * @date : 11/22/2022
 */
class NthMagicalNumber {


    /**
     * 从头开始依次计算a、b的1...n倍，直到个数满足n时返回结果
     * 时间复杂度O（N），会超时
     * 空间复杂度O(1)
     */
    fun nthMagicalNumber(n: Int, a: Int, b: Int): Int {
        val MOD = (Math.pow(10.0, 9.0) + 7).toInt()

        val max: Int
        val min: Int

        if (a > b) {
            max = a
            min = b
        } else {
            max = b
            min = a
        }

        var minN = 1.0
        var maxN = 1.0

        var count = 0
        var result = 0.0
        while (count < n) {
            val minNumber = minN * min
            val maxNumber = maxN * max
            result = if (minNumber < maxNumber) {
                minN++
                count++
                minNumber
            } else if (minNumber > maxNumber) {
                maxN++
                count++
                maxNumber
            } else {
                minN++
                maxN++
                count++
                maxNumber
            }

        }
        return (result % MOD).toInt()
    }

    /**
     * https://leetcode.cn/problems/nth-magical-number/solution/di-n-ge-shen-qi-shu-zi-by-leetcode-solut-6vyy/
     * n里面满足是a或b的倍数的数字总数=a的倍数个数+b的倍数个数-ab的公约数个数
     * 时间复杂度O(a+b)
     * 空间复杂度O（1）
     */
    fun nthMagicalNumberDichotomy(n: Int, a: Int, b: Int): Int {
        val lcm = lcm(a.toLong(), b.toLong())
        val time = lcm / a + lcm / b - 1
        var left = n / time * lcm
        var right = (n / time + 1) * lcm
        while (left < right) {
            val mid = (right - left) / 2 + left
            val cnt = mid / a + mid / b - mid / lcm
            if (cnt < n) {
                left = mid + 1
            } else {
                right = mid
            }
        }
        return (right % (1e9 + 7).toLong()).toInt()
    }

    // 最小公倍数
    private fun lcm(a: Long, b: Long): Long {
        val gcd = gcd(a, b)
        return a / gcd * b / gcd * gcd
    }

    // 最大公约数
    private fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }
}

fun main() {
    val timeMs = System.currentTimeMillis()

    val obj = NthMagicalNumber()
    val cases = listOf(
        Triple(887859796, 29911, 37516),
        Triple(234, 2, 3),/*351*/
        Triple(23, 43, 232),/*860*/
        Triple(1000000000, 40000, 39999),/*999860007*/
        Triple(1000000000, 40000, 39999),/*999860007*/
        Triple(1000000000, 40000, 39999),/*999860007*/
        Triple(1, 2, 3), Triple(4, 2, 3), Triple(6, 2, 3),
        Triple(1000000000, 2, 3),
        Triple(223233232, 1212, 32332),/*814045076*/
    )

    cases.forEach {
        println("${obj.nthMagicalNumberDichotomy(it.first, it.second, it.third)}  --> $it")
//        println("${obj.nthMagicalNumber(it.first, it.second, it.third)}  --> $it")
    }

    // 4211ms,
    println("${System.currentTimeMillis() - timeMs}ms")
}