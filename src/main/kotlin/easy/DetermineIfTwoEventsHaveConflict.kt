package easy

/**
 * @author : jixiaoyong
 * @description ：2446. 判断两个事件是否存在冲突
 * 给你两个字符串数组 event1 和event2，表示发生在同一天的两个闭区间时间段事件，其中：
 *
 * event1 = [startTime1, endTime1] 且
 * event2 = [startTime2, endTime2]
 * 事件的时间为有效的 24 小时制且按HH:MM格式给出。
 *
 * 当两个事件存在某个非空的交集时（即，某些时刻是两个事件都包含的），则认为出现 冲突。
 *
 * 如果两个事件之间存在冲突，返回true；否则，返回false 。
 * 提示：
 *
 * evnet1.length == event2.length == 2
 * event1[i].length == event2[i].length == 5
 * startTime1 <= endTime1
 * startTime2 <= endTime2
 * 所有事件的时间都按照HH:MM格式给出
 *
 * https://leetcode.cn/problems/determine-if-two-events-have-conflict/
 *
 * @email : jixiaoyong1995@gmail.com
 * @date : 2023/5/17
 */
class DetermineIfTwoEventsHaveConflict {

    fun haveConflict(event1: Array<String>, event2: Array<String>): Boolean {
        var result = true
        val eventRange1 = event1.map { time ->
            time2Minutes(time)
        }

        val eventRange2 = event2.map { time ->
            time2Minutes(time)
        }

        // end < start, 则不相交
        if (eventRange1[1] < eventRange2[0] || eventRange2[1] < eventRange1[0]) {
            result = false
        }

        return result
    }

    private fun time2Minutes(timeHHMM: String): Int {
        return timeHHMM.substring(0, 2).toInt() * 60 + timeHHMM.substring(3, 5).toInt()
    }

}

fun main() {
    val obj = DetermineIfTwoEventsHaveConflict()
    val cases = mutableListOf(
        Pair(arrayOf("01:15", "02:00"), arrayOf("02:00", "03:00")),
        Pair(arrayOf("01:15", "02:00"), arrayOf("02:01", "03:00")),
        Pair(arrayOf("00:00", "01:00"), arrayOf("01:00", "02:00")),
        Pair(arrayOf("00:00", "01:00"), arrayOf("02:00", "03:00")),
        Pair(arrayOf("00:00", "01:00"), arrayOf("01:30", "02:00")),
        Pair(arrayOf("00:00", "01:00"), arrayOf("00:30", "01:30")),
        Pair(arrayOf("00:00", "00:30"), arrayOf("00:30", "01:00")),
        Pair(arrayOf("00:00", "00:30"), arrayOf("00:15", "00:45")),
        Pair(arrayOf("23:30", "23:59"), arrayOf("00:00", "00:01")),
        Pair(arrayOf("23:30", "23:59"), arrayOf("00:01", "00:02")),
        // 不符合题设开始时间小于等于结束时间
        Pair(arrayOf("23:59", "00:01"), arrayOf("00:00", "00:02")),
        Pair(arrayOf("23:59", "00:00"), arrayOf("00:00", "00:01")),
    )

    cases.forEach {
        val res = obj.haveConflict(it.first, it.second)
        println("${it.first.joinToString()} <==> ${it.second.joinToString()}    => $res")
    }
}