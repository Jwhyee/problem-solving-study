import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Queue
import java.util.StringTokenizer

private lateinit var map: Array<IntArray>
private lateinit var visited: Array<BooleanArray>
private lateinit var pq: PriorityQueue<Pos>
private var n: Int = 0
private var m: Int = 0

private data class Pos(val y: Int, val x: Int, val h: Int)

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    n = st.nextToken().toInt()
    m = st.nextToken().toInt()

    map = Array(n) { IntArray(m) }
    visited = Array(n) { BooleanArray(m) }
    pq = PriorityQueue {a, b -> b.h - a.h}

    for (y in 0 until n) {
        st = StringTokenizer(readLine())
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
            bfs(y, x)
            cnt++
        }

    }

    println(cnt)

}

private val dx = intArrayOf(1, 0, -1, 0, -1, -1, 1, 1)
private val dy = intArrayOf(0, 1, 0, -1, -1, 1, -1, 1)

private fun bfs(y: Int, x: Int) {
    val queue: Queue<Pair<Int, Int>> = LinkedList()
    queue += (y to x)

    while (queue.isNotEmpty()) {
        val cur = queue.poll()
        val cy = cur.first
        val cx = cur.second
        visited[cy][cx] = true

        for (i in 0..7) {
            val ny = cy + dy[i]
            val nx = cx + dx[i]
            if (ny in 0 until n && nx in 0 until m) {
                if (visited[ny][nx].not() && map[ny][nx] != 0) {
                    val diff = map[cy][cx] - map[ny][nx]
                    if (diff >= 0) {
                        queue += (ny to nx)
                    }
                }
            }
        }

    }
}