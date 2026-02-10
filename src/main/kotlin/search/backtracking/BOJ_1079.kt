package search.backtracking

import java.util.StringTokenizer
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val n = readLine().toInt()

    val st = StringTokenizer(readLine())
    val status = IntArray(n) { st.nextToken().toInt() }

    val guilty = Array(n) {
        val line = StringTokenizer(readLine())
        IntArray(n) { line.nextToken().toInt() }
    }

    val mafia = readLine().toInt()
    var maxNights = 0
    val dead = BooleanArray(n)

    fun game(remainCnt: Int, currentNight: Int) {
        if (dead[mafia]) {
            maxNights = max(maxNights, currentNight)
            return
        }

        if (currentNight + (remainCnt / 2) <= maxNights) {
            return
        }

        maxNights = max(maxNights, currentNight)

        // 밤(마피아가 사람을 죽이고 유죄 수치 조정)
        if (remainCnt % 2 == 0) {
            for (target in 0 until n) {
                if (target != mafia && !dead[target]) {
                    dead[target] = true

                    for (i in 0 until n) {
                        if (!dead[i]) status[i] += guilty[target][i]
                    }

                    game(remainCnt - 1, currentNight + 1)

                    for (i in 0 until n) {
                        if (!dead[i]) status[i] -= guilty[target][i]
                    }
                    dead[target] = false
                }
            }
        }
        // 낮 (유죄 수치가 가장 높고, 그 중에 번호가 가장 낮은 사람 죽음)
        else {
            var target = -1
            var maxVal = -1

            for (i in 0 until n) {
                if (!dead[i] && status[i] > maxVal) {
                    maxVal = status[i]
                    target = i
                }
            }

            if (target != -1) {
                dead[target] = true
                game(remainCnt - 1, currentNight)
                dead[target] = false
            }
        }
    }

    game(n, 0)
    println(maxNights)
}