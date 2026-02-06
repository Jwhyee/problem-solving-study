package search.backtracking

import java.util.StringTokenizer

private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, 0, -1)
private var max = 0

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (n, m) = st.nextToken().toInt() to st.nextToken().toInt()

    val map = Array(n) { IntArray(m) }
    val visited = Array(n) { BooleanArray(m) }

    for (i in 0 until n) {
        st = StringTokenizer(readLine())
        for (j in 0 until m) {
            map[i][j] = st.nextToken().toInt()
        }
    }

    st = StringTokenizer(readLine())
    val (x, y, p) = Triple(st.nextToken().toInt(), st.nextToken().toInt(), st.nextToken().toInt())

    visited[x][y] = true
    backTracking(x, y, p, map, visited, map[x][y], -1)
    println(max)
}

private fun backTracking(x: Int, y: Int, pipe: Int, map: Array<IntArray>, visited: Array<BooleanArray>, sum: Int, dir: Int) {
    max = maxOf(sum, max)

    if (pipe <= 0) return

    for (i in 0 until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]

        if (nx !in map.indices || ny !in map[0].indices || visited[nx][ny]) continue

        val cost = if (dir == -1 || dir == i) 1 else 2
        if (pipe < cost) continue

        visited[nx][ny] = true
        backTracking(nx, ny, pipe - cost, map, visited, sum + map[nx][ny], i)
        visited[nx][ny] = false
    }
}