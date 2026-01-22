import java.util.PriorityQueue
import java.util.StringTokenizer

private data class Link(
    val y: Int, val x: Int, val blackRupoor: Int
)

fun main() = with(System.`in`.bufferedReader()){
    var i = 1
    while(true) {
        val n = readLine().toInt()

        if (n == 0) break

        val map = Array(n) {
            val st = StringTokenizer(readLine())
            IntArray(n) { st.nextToken().toInt() }
        }

        println("Problem ${i++}: ${dijkstra(map, n)}")
    }
}

private val dx = intArrayOf(0, 1, 0, -1)
private val dy = intArrayOf(1, 0, -1, 0)

private fun dijkstra(
    map: Array<IntArray>,
    n: Int
): Int {
    val pq = PriorityQueue<Link>(compareBy { it.blackRupoor })

    val min = Array(n) { IntArray(n) { Int.MAX_VALUE } }

    pq.add(Link(0, 0, map[0][0]))

    while (pq.isNotEmpty()) {
        val (curY, curX, totalBlackRupoor) = pq.poll()

        if (min[curY][curX] < totalBlackRupoor) continue
        if (curY == n - 1 && curX == n - 1) return min[n - 1][n - 1]

        for (dir in 0 until 4) {
            val ny = curY + dy[dir]
            val nx = curX + dx[dir]

            if (ny in 0 until n && nx in 0 until n) {
                val nextTotalBlackRupoor = totalBlackRupoor + map[ny][nx]

                if (min[ny][nx] > nextTotalBlackRupoor) {
                    min[ny][nx] = nextTotalBlackRupoor
                    pq.add(Link(ny, nx, nextTotalBlackRupoor))
                }
            }
        }
    }

    return -1
}