package graph.dijkstra

import java.util.PriorityQueue
import java.util.StringTokenizer
import kotlin.math.min

const val INF = 200_000_000

fun main() = with(System.`in`.bufferedReader()) {
    val (n, e) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val nodes = Array(n + 1) { arrayListOf<Pair<Int, Int>>() }
    repeat(e) {
        val st = StringTokenizer(readLine())
        val start = st.nextToken().toInt()
        val end = st.nextToken().toInt()
        val weight = st.nextToken().toInt()

        nodes[start].add(end to weight)
        nodes[end].add(start to weight)
    }

    val (v1, v2) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val distFrom1 = dijkstra(nodes, n, 1)
    val distFromV1 = dijkstra(nodes, n, v1)
    val distFromV2 = dijkstra(nodes, n, v2)

    val path1 = if (distFrom1[v1] != INF && distFromV1[v2] != INF && distFromV2[n] != INF) {
        distFrom1[v1] + distFromV1[v2] + distFromV2[n]
    } else INF

    val path2 = if (distFrom1[v2] != INF && distFromV2[v1] != INF && distFromV1[n] != INF) {
        distFrom1[v2] + distFromV2[v1] + distFromV1[n]
    } else INF

    val result = min(path1, path2)
    println(if (result >= INF) -1 else result)

    close()
}

private fun dijkstra(nodes: Array<ArrayList<Pair<Int, Int>>>, n: Int, start: Int): IntArray {
    val dist = IntArray(n + 1) { INF }
    dist[start] = 0

    val pq = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })
    pq.add(start to 0)

    while (pq.isNotEmpty()) {
        val (cur, total) = pq.poll()

        if (dist[cur] < total) continue

        for ((target, weight) in nodes[cur]) {
            val nextWeight = total + weight

            if (dist[target] > nextWeight) {
                dist[target] = nextWeight
                pq.add(target to nextWeight)
            }
        }
    }

    return dist
}