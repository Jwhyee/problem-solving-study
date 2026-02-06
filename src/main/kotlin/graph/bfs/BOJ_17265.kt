package graph.bfs

import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

private var n = 0
private lateinit var map: Array<IntArray>
private const val PLUS = "+"
private const val PLUS_INT = -1
private const val MINUS = "-"
private const val MINUS_INT = -2
private const val MUL = "*"
private const val MUL_INT = -3

private var rMax = Int.MIN_VALUE
private var rMin = Int.MAX_VALUE

private val dx = intArrayOf(0, 1)
private val dy = intArrayOf(1, 0)

/**
 * 문제 이름(난이도) : 나의 인생에는 수학과 함께(GOL5)
 * 시간 : 88 ms
 * 메모리 : 12316 KB
 * 링크 : https://www.acmicpc.net/problem/17265
 */
fun main() = with(System.`in`.bufferedReader()) {

    n = readLine().toInt()
    map = Array(n) { IntArray(n) }

    for (i in 0 until n) {
        val st = StringTokenizer(readLine())
        for (j in 0 until n) {
            val cur = st.nextToken()
            map[i][j] = when(cur) {
                PLUS -> PLUS_INT
                MINUS -> MINUS_INT
                MUL -> MUL_INT
                else -> cur.toInt()
            }
        }
    }

    dfs(0, 0, map[0][0], 0)

    println("$rMax $rMin")

}

private fun dfs(y: Int, x: Int, cur: Int, op: Int) {
    // 지도의 끝에 다다르면 최대, 최솟값 갱신
    if (y == n - 1 && x == n - 1) {
        rMax = max(cur, rMax)
        rMin = min(cur, rMin)
        return
    }

    for (i in 0 until 2) {
        val ny = y + dy[i]
        val nx = x + dx[i]
        if (ny in 0 until n && nx in 0 until n) {
            // 연산자가 없을 경우 다음 연산자 찾기
            if (op == 0) {
                dfs(ny, nx, cur, map[ny][nx])
            }
            // 연산자가 있을 경우 다음 수 찾기
            else {
                val next = map[ny][nx]
                when (op) {
                    PLUS_INT -> dfs(ny, nx, cur + next, 0)
                    MINUS_INT -> dfs(ny, nx, cur - next, 0)
                    MUL_INT -> dfs(ny, nx, cur * next, 0)
                }
            }
        }
    }
}