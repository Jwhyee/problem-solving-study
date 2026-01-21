import java.util.PriorityQueue
import java.util.StringTokenizer

private const val INF = Long.MAX_VALUE

fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val adj = Array(n + 1) { arrayListOf<Pair<Int, Int>>() }

    repeat(m) {
        val edgeSt = StringTokenizer(readLine())
        val a = edgeSt.nextToken().toInt()
        val b = edgeSt.nextToken().toInt()
        val d = edgeSt.nextToken().toInt()
        adj[a].add(Pair(b, d))
        adj[b].add(Pair(a, d))
    }

    val foxDist = runFoxDijkstra(n, adj)
    val wolfDist = runWolfDijkstra(n, adj)

    var count = 0
    for (i in 2..n) {
        val wolfMinTime = minOf(wolfDist[i][0], wolfDist[i][1])
        if (foxDist[i] < wolfMinTime) {
            count++
        }
    }

    println(count)
}

private fun runFoxDijkstra(n: Int, adj: Array<ArrayList<Pair<Int, Int>>>): LongArray {
    val dist = LongArray(n + 1) { INF }
    val pq = PriorityQueue<Pair<Long, Int>>(compareBy { it.first })

    dist[1] = 0L
    pq.add(Pair(0L, 1))

    while (pq.isNotEmpty()) {
        val (time, node) = pq.poll()

        if (dist[node] < time) continue

        for ((nextNode, distance) in adj[node]) {
            val nextTime = time + distance * 2L
            if (nextTime < dist[nextNode]) {
                dist[nextNode] = nextTime
                pq.add(Pair(nextTime, nextNode))
            }
        }
    }
    return dist
}

private fun runWolfDijkstra(n: Int, adj: Array<ArrayList<Pair<Int, Int>>>): Array<LongArray> {
    val dist = Array(n + 1) { LongArray(2) { INF } }
    val pq = PriorityQueue<Triple<Long, Int, Int>>(compareBy { it.first })

    dist[1][0] = 0L
    pq.add(Triple(0L, 1, 0))

    while (pq.isNotEmpty()) {
        val (time, node, parity) = pq.poll()

        if (dist[node][parity] < time) continue

        for ((nextNode, distance) in adj[node]) {
            if (parity == 0) {
                val nextTime = time + distance * 1L
                val nextParity = 1
                if (nextTime < dist[nextNode][nextParity]) {
                    dist[nextNode][nextParity] = nextTime
                    pq.add(Triple(nextTime, nextNode, nextParity))
                }
            } else {
                val nextTime = time + distance * 4L
                val nextParity = 0
                if (nextTime < dist[nextNode][nextParity]) {
                    dist[nextNode][nextParity] = nextTime
                    pq.add(Triple(nextTime, nextNode, nextParity))
                }
            }
        }
    }
    return dist
}
