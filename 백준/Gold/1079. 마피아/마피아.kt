import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    // 마피아, 시민
    // 참가자 짝수 : 밤 -> 아무나 1명 죽임
    // 참가자 홀수 : 낮 -> 검거할 사람 죽임
    // 은진이는 마지막으로 남은 마피아
    // 유죄 지수 : 낮에 시민들이 누굴 죽일지 고를 때 사용 -> 반응은 2차원 배열 R
    val n = readLine().toInt()

    val st = StringTokenizer(readLine())
    val status = IntArray(n) { st.nextToken().toInt() }

    val guilty = Array(n) {
        val st = StringTokenizer(readLine())
        IntArray(n) {
            st.nextToken().toInt()
        }
    }

    val mafia = readLine().toInt()

    var max = 0

    val dead = BooleanArray(n) { false }

    fun game(n: Int, mafia: Int, remainPeopleCount: Int, day: Int) {
        // 시민이 이긴 경우, 마피아가 이긴 경우
        if (dead[mafia]) {
            max = maxOf(max, day)
            return
        }

        if (remainPeopleCount % 2 == 0) {
            // 밤(사람 하나를 죽인 것으로 처리)
            for (target in 0 until n) {
                if (target != mafia && !dead[target]) {
                    dead[target] = true
                    addGuiltyScore(guilty, status, dead, n, target)

                    game(n, mafia, remainPeopleCount - 1, day + 1)

                    restoreGuiltyScore(guilty, status, dead, n, target)
                    dead[target] = false
                }
            }
        } else {
            // 낮(유죄 지수가 가장 높은 사람 사망)
            var maxStat = 0
            var maxStatIndex = 0

            for (index in 0 until n) {
                if (dead[index]) continue
                if (maxStat < status[index]) {
                    maxStat = status[index]
                    maxStatIndex = index
                }
            }

            if (maxStat != 0) {
                dead[maxStatIndex] = true

                game(n, mafia, remainPeopleCount - 1, day)

                dead[maxStatIndex] = false
            }
        }
    }

    game(n, mafia, n, 0)

    println(max)

    close()
}

private fun addGuiltyScore(
    guilty: Array<IntArray>,
    status: IntArray,
    dead: BooleanArray,
    n: Int,
    die: Int
) {
    for (target in 0 until n) {
        if (!dead[target]) {
            status[target] += guilty[die][target]
        }
    }
}

private fun restoreGuiltyScore(
    guilty: Array<IntArray>,
    status: IntArray,
    dead: BooleanArray,
    n: Int,
    die: Int
) {
    for (target in 0 until n) {
        if (!dead[target]) {
            status[target] -= guilty[die][target]
        }
    }
}