import java.util.*

private class Robot2(val y: Int, val x: Int, val dir: Int, val cost: Int) : Comparable<Robot2> {
    override fun compareTo(other: Robot2): Int = cost - other.cost
}

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val m = st.nextToken().toInt()
    val n = st.nextToken().toInt()

    val map = Array(m) {
        st = StringTokenizer(readLine())
        IntArray(n) { st.nextToken().toInt() }
    }

    val (ry, rx, rd) = position(readLine())
    val (ey, ex, ed) = position(readLine())

    val dy = intArrayOf(0, 0, 1, -1)
    val dx = intArrayOf(1, -1, 0, 0)

    val dist = Array(m) { Array(n) { IntArray(4) { Int.MAX_VALUE } } }
    val pq = PriorityQueue<Robot2>()

    dist[ry][rx][rd] = 0
    pq.offer(Robot2(ry, rx, rd, 0))

    while (pq.isNotEmpty()) {
        val curr = pq.poll()
        val y = curr.y
        val x = curr.x
        val dir = curr.dir
        val cost = curr.cost

        if (y == ey && x == ex && dir == ed) {
            println(cost)
            return@with
        }

        if (dist[y][x][dir] < cost) continue

        for (k in 1..3) {
            val ny = y + dy[dir] * k
            val nx = x + dx[dir] * k

            if (ny !in 0 until m || nx !in 0 until n || map[ny][nx] == 1) break

            val nextCost = cost + 1
            if (dist[ny][nx][dir] > nextCost) {
                dist[ny][nx][dir] = nextCost
                pq.offer(Robot2(ny, nx, dir, nextCost))
            }
        }

        for (nd in 0..3) {
            if (dir == nd) continue

            val turnCost = if (
                (dir == 0 && nd == 1) || (dir == 1 && nd == 0) ||
                (dir == 2 && nd == 3) || (dir == 3 && nd == 2)
            ) 2 else 1

            val nextCost = cost + turnCost
            if (dist[y][x][nd] > nextCost) {
                dist[y][x][nd] = nextCost
                pq.offer(Robot2(y, x, nd, nextCost))
            }
        }
    }
}

private fun position(line: String) = StringTokenizer(line).run { 
    Triple(nextToken().toInt() - 1, nextToken().toInt() - 1, nextToken().toInt() - 1)
}