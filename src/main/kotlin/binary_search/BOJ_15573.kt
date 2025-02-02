package binary_search

import java.util.LinkedList
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())

    val (n, m, k) = Triple(st.nextToken().toInt(), st.nextToken().toInt(), st.nextToken().toInt())
    val map = Array(n) { IntArray(m) { 0 } }

    var minD = Int.MAX_VALUE
    var maxD = Int.MIN_VALUE

    for (i in 0 until n) {
        st = StringTokenizer(readLine())
        for (j in 0 until m) {
            map[i][j] = st.nextToken().toInt()
            minD = Math.min(minD, map[i][j])
            maxD = Math.max(maxD, map[i][j])
        }
    }

    while(minD <= maxD) {
        val mid = (minD + maxD) / 2
        if (bfs(map, n, m, mid) >= k) {
            maxD = mid - 1
        } else {
            minD = mid + 1
        }
    }

    println(minD)
}

private val dx = arrayOf(1, 0, -1, 0)
private val dy = arrayOf(0, 1, 0, -1)

private fun bfs(map: Array<IntArray>, n: Int, m: Int, d: Int): Int {
    val visited = Array(n) { BooleanArray(m) { false } }
    val queue = LinkedList<Pair<Int, Int>>()

    for (j in 0 until m) {
        if (map[0][j] <= d) {
            queue.add(0 to j)
            visited[0][j] = true
        }
    }

    for (i in 1 until n) {
        if (map[i][0] <= d) {
            queue.add(i to 0)
            visited[i][0] = true
        }
        if (map[i][m - 1] <= d) {
            queue.add(i to m - 1)
            visited[i][m - 1] = true
        }
    }

    var cnt = 0

    while (queue.isNotEmpty()) {
        val (y, x) = queue.poll()
        cnt++
        for (i in 0 until 4) {
            val ny = y + dy[i]
            val nx = x + dx[i]
            if (ny in 0 until n && nx in 0 until m && !visited[ny][nx] && map[ny][nx] <= d) {
                queue.add(ny to nx)
                visited[ny][nx] = true
            }
        }
    }

    return cnt
}