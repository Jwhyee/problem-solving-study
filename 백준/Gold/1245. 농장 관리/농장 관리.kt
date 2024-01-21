import java.util.PriorityQueue
import java.util.StringTokenizer

private lateinit var map: Array<IntArray>
private lateinit var visited: Array<BooleanArray>
private data class Pos(val y: Int, val x: Int, val h: Int)

fun main() {
    val br = System.`in`.bufferedReader()
    val bw = System.out.bufferedWriter()
    var st = StringTokenizer(br.readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    // 지도 및 방문 배열 초기화
    map = Array(n) { IntArray(m) }
    visited = Array(n) { BooleanArray(m) }
    val pq = PriorityQueue<Pos> {a, b -> b.h - a.h}

    for (y in 0 until n) {
        st = StringTokenizer(br.readLine())
        for (x in 0 until m) {
            val h = st.nextToken().toInt()
            map[y][x] = h
            if (h != 0) pq += Pos(y, x, h)
        }
    }


    var cnt = 0
    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val y = cur.y
        val x = cur.x
        if (!visited[y][x]) {
            dfs(y, x, n, m)
            cnt++
        }
    }

    bw.append("$cnt")
    bw.flush()
    bw.close()
    br.close()
}

private val dx = intArrayOf(1, 0, -1, 0, -1, -1, 1, 1)
private val dy = intArrayOf(0, 1, 0, -1, -1, 1, -1, 1)

private fun dfs(y: Int, x: Int, n: Int, m: Int) {
    visited[y][x] = true

    for (i in 0..7) {
        val ny = y + dy[i]
        val nx = x + dx[i]
        if (ny in 0 until n && nx in 0 until m) {
            if (visited[ny][nx].not() && map[ny][nx] != 0) {
                val diff = map[y][x] - map[ny][nx]
                if (diff >= 0) {
                    dfs(ny, nx, n, m)
                }
            }
        }
    }
}