package impl.simulation

import java.util.StringTokenizer

/*
1
05:00 301
* */

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    val t = readLine().toInt()

    repeat(t) {
        val (startTime, d) = StringTokenizer(readLine()).run {
            nextToken().split(":").map { it.toInt() } to nextToken().toInt()
        }

        var currentHour = startTime[0]
        var currentMinute = startTime[1]
        var remain = d
        var totalPrice = 0

        fun oneHourProcess() {
            totalPrice += 1000
            remain -= 60
            currentHour += 1
        }

        while (remain > 0) {
            // 시간 보정
            if (currentMinute >= 60) {
                currentHour += (currentMinute / 60)
                currentMinute %= 60
            }
            if (currentHour >= 24) {
                currentHour %= 24
            }

            // 1. 야간 정액권 사용 가능 시간대 체크 (22:00 ~ 07:59)
            val isNightTime = (currentHour in 22..23) || (currentHour in 0..7)

            if (isNightTime) {
                // 아침 8시까지 몇 분 남았는지 계산
                val minutesUntil8AM = if (currentHour >= 22) {
                    // 22 ~ 24시 사이에서 8시까지 남은 시간
                    (23 - currentHour + 8) * 60 + (60 - currentMinute)
                } else {
                    // 00 ~ 08시 사이에서 8시까지 남은 시간
                    (7 - currentHour) * 60 + (60 - currentMinute)
                }

                // 8시까지 남은 시간이 5시간(300분)을 초과하는가?
                val coverTime = if (remain < minutesUntil8AM) remain else minutesUntil8AM

                if (coverTime > 300) {
                    totalPrice += 5000
                    remain -= minutesUntil8AM
                    currentHour = 8
                    currentMinute = 0
                } else {
                    // 5시간 이하면 그냥 일반 요금 1시간 추가
                    oneHourProcess()
                }
            } else {
                // 2. 일반 시간대 (08:00 ~ 21:59)
                oneHourProcess()
            }
        }

        bw.write("$totalPrice\n")
    }

    bw.flush()
    bw.close()
}