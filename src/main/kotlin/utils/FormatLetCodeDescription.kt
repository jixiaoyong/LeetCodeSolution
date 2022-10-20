package utils

/**
 * @author : jixiaoyong
 * @description ： TODO
 * @email : jixiaoyong1995@gmail.com
 * @date : 10/20/2022
 */
class FormatLetCodeDescription {

    companion object{

        @JvmStatic
        fun main(args: Array<String>) {
            // TODO inert description here
            var string = """

            """.trimIndent()
            string = string.replace(" ","")
                .substringBefore("来源：力扣")
                .replace("\n","\n * ")
                .trim()

            println(" * $string")

        }
    }
}