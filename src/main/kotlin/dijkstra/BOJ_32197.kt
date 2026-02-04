package dijkstra

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val graph = Array(n + 1) { ArrayList<Pair<Int, Int>>() }

    repeat(m) {
        val (s, e, t) = StringTokenizer(readLine()).run {
            Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
        }

        graph[s].add(e to t)
        graph[e].add(s to t)
    }

    val (s, e) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    println(bfs(graph, s, e))

    close()
}

private fun bfs(graph: Array<ArrayList<Pair<Int, Int>>>, start: Int, end: Int): Int {
    val deque = ArrayDeque<Triple<Int, Int, Int>>()
    val dist = Array(graph.size + 1) { IntArray(2) { Int.MAX_VALUE } }
    deque.add(Triple(start, -1, 0))

    while (deque.isNotEmpty()) {
        val (curStation, curT, cost) = deque.removeFirst()

        if (curT != -1 && dist[curStation][curT] < cost) continue
        if (curStation == end) return cost

        val nextStations = graph[curStation]

        for ((nextStation, nextT) in nextStations) {
            if (curT == -1) {
                if (dist[nextStation][nextT] > cost) {
                    dist[nextStation][nextT] = cost
                    deque.addFirst(Triple(nextStation, nextT, cost))
                }
            } else {
                if(curT == nextT) {
                    if (dist[nextStation][nextT] > cost) {
                        dist[nextStation][nextT] = cost
                        deque.addFirst(Triple(nextStation, nextT, cost))
                    }
                } else {
                    val nextCost = cost + 1
                    if (dist[nextStation][nextT] > nextCost) {
                        dist[nextStation][nextT] = nextCost
                        deque.addLast(Triple(nextStation, nextT, nextCost))
                    }
                }
            }
        }
    }

    return 0
}