package greedy

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * 문제 이름(난이도) : 지뢰찾기(GOL4)
 * 시간 : 100 ms
 * 메모리 : 12448 KB
 * 링크 : https://www.acmicpc.net/problem/2140
 */

val MINE = '*'
val WALL = '#'
val EMPTY = 'X'
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val N = readLine().toInt()
    val map = Array(N) { CharArray(N) }

    for (i in 0 until N) {
        val line = readLine()
        for (j in 0 until N) {
            map[i][j] = line[j]
        }
    }

    var cnt = 0

    fun chkMine(y: Int, x: Int, cur: Int) {

        val dx = intArrayOf(1, 0, -1, 0, -1, -1, 1, 1)
        val dy = intArrayOf(0, 1, 0, -1, -1, 1, -1, 1)

        var aroundMineCnt = cur

        for (i in 0..7) {
            val ny = y + dy[i]
            val nx = x + dx[i]

            // 다음 좌표가 범위 내에 있는지 확인
            if (nx in 0 until N && ny in 0 until N) {
                // 현재 좌표 기준으로 주변에 지뢰가 있고, 가려졌다면 지뢰로 처리
                if (map[ny][nx] == WALL && aroundMineCnt != 0) {
                    aroundMineCnt--
                    map[ny][nx] = MINE
                }
                // 현재 좌표가 지뢰이고, 주변에 이미 지뢰가 있을 경우 카운트만 줄이기
                else if (map[ny][nx] == MINE && aroundMineCnt != 0) {
                    aroundMineCnt--
                }
                // 현재 좌표가 가려져있고, 주변에 지뢰가 없을 경우 비어있는 칸으로 처리
                else if (map[ny][nx] == WALL && aroundMineCnt == 0) {
                    map[ny][nx] = EMPTY
                }
            }
        }
    }

    repeat(N) { i ->
        repeat(N) { j ->
            // 현재 좌표가 숫자일 경우
            if (map[i][j].isDigit()) {
                val cur = map[i][j] - '0'
                chkMine(i, j, cur)
            }
        }
    }

    repeat(N) { i ->
        repeat(N) { j ->
            if (map[i][j] == '*' || map[i][j] == '#') {
                cnt++
            }
        }
    }
    println(cnt)
}

/*
5
11100
2###1
3###1
2###1
12210
*/