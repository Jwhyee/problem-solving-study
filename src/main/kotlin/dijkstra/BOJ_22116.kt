package dijkstra

import java.util.StringTokenizer
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private var n = 0
private lateinit var map: Array<IntArray>
private lateinit var visited: Array<BooleanArray>
private var dy = intArrayOf(0, 1, 0, -1)
private var dx = intArrayOf(1, 0, -1, 0)
private var result = Int.MAX_VALUE

fun main() = with(System.`in`.bufferedReader()) {
    n = readLine().toInt()
    map = Array(n) { IntArray(n) }
    visited = Array(n) { BooleanArray(n) }

    for (y in 0 until n) {
        val st = StringTokenizer(readLine())
        for (x in 0 until n) {
            map[y][x] = st.nextToken().toInt()
        }
    }

    dfs(0, 0, 0, -1)
    visited = Array(n) { BooleanArray(n) }
    dfs(0, 0, 1, -1)
    close()
    println(result)
}

private fun dfs(y: Int, x: Int, dir: Int, min: Int) {
    if (y == (n - 1) && x == (n - 1)) {
        result = min(min, result)
        return
    }

    visited[y][x] = true

    for (i in dir until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]

        if (nx in 0 until n && ny in 0 until n) {
            if (!visited[ny][nx]) {
                val cur = map[y][x]
                val next = map[ny][nx]
                val diff = abs(cur - next)
                dfs(ny, nx, 0, max(min, diff))
            }
        }

    }

}