package back_tracking

import java.util.StringTokenizer

private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, 0, -1)
private var max = 0

fun main() = with(System.`in`.bufferedReader()){
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

    backTracking(x, y, p, map, visited, map[x][y])
    println(max)
}

private fun backTracking(x: Int, y: Int, pipe: Int, map: Array<IntArray>, visited: Array<BooleanArray>, sum: Int = 0, dir: Int = 0) {
    if(pipe == 0) {
        max = maxOf(sum, max)
        return
    }

    visited[x][y] = true

    for (i in 0 until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]

        if(nx < 0 || nx >= map.size || ny < 0 || ny >= map[0].size || visited[nx][ny]) {
            max = maxOf(sum, max)
            continue
        }

        val curPipe = (if(ny == y || nx == x) pipe - 1 else pipe - 2) - if(dir != i) 1 else 0
        if(curPipe < 0) {
            max = maxOf(sum, max)
            continue
        }

        visited[nx][ny] = true
        backTracking(nx, ny, curPipe, map, visited, sum + map[nx][ny], i)
        visited[nx][ny] = false
    }
}