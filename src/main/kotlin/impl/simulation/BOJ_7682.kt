package impl.simulation

import kotlin.math.abs

/**
 * 문제 이름(난이도) : 틱택토(GOL5)
 * 시간 : 1 ms
 * 메모리 : 1 KB
 * 링크 : https://www.acmicpc.net/problem/7682
 */
private const val INVALID = "invalid"
private const val VALID = "valid"
fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.`out`.bufferedWriter()
    while (true) {
        val line = readLine()
        if(line == "end") break

        val arr = line.toCharArray()
        val map = Array(3) { CharArray(3) }

        var xCnt = 0
        var oCnt = 0
        var emptyCnt = 0

        repeat(3) { y ->
            repeat(3) { x ->
                val cur = arr[(3 * y) + x]
                if(cur == 'X') xCnt++ else if(cur == 'O') oCnt++ else emptyCnt++
                map[y][x] = cur
            }
        }

        val xoDiff = abs(xCnt - oCnt)

        // X, O의 개수 차이가 1을 초과할 경우 불가능한 게임
        if (xoDiff > 1) {
            bw.append("$INVALID\n")
            continue
        }

        // 조건이 X, O, N에 따라 나누는게 아닌 꽉 찼거나 꽉 차지 않았을 경우로 다시 풀이 *********
        when(findWinner(map)) {
            // x가 이기는 경우 (xCnt > oCnt, diff == 1)
            'X' -> {
                if(xCnt - oCnt == 1) bw.append("$VALID\n")
                else bw.append("$INVALID\n")
            }
            // o가 이기는 경우 (xCnt == oCnt, diff == 0)
            'O' -> {
                if(xCnt == oCnt) bw.append("$VALID\n")
                else bw.append("$INVALID\n")
            }
            // 꽉 채워서 비기는 경우 (xCnt > oCnt, diff == 1)
            'N' -> {
                if(xCnt - oCnt == 1) bw.append("$VALID\n")
                else bw.append("$INVALID\n")
            }
        }
    }
    bw.flush()
    bw.close()
}

fun findWinner(map: Array<CharArray>): Char {
    // 가로 탐색
    repeat(3) { y ->
        val cur = map[y][0]
        if(cur != '.' && cur == map[y][1] && cur == map[y][2]) return cur
    }

    // 세로 탐색
    repeat(3) { x ->
        val cur = map[0][x]
        if(cur != '.' && cur == map[1][x] && cur == map[2][x]) return cur
    }

    // 대각선 탐색
    repeat(2) { y ->
        // (0, 0), (0, 2)
        val cur = map[0][y * 2]
        if (cur != '.' && cur == map[1][1]) {
            val nextXPoint = if(y == 0) 2 else 0
            if(cur == map[2][nextXPoint]) return cur
        }
    }
    return 'N'
}