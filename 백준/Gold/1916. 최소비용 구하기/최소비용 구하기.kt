import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    // 도시의 수
    val n = readLine().toInt()
    // 버스의 수
    val m = readLine().toInt()

    // Pair -> end(도착지), weight(가중치)
    val graph = Array(n + 1) { ArrayList<Pair<Int, Int>>() }

    repeat(m) {
        val (s, e, w) = StringTokenizer(readLine()).run {
            Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
        }

        graph[s].add(e to w)
    }

    val (s, e) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    println(dijkstra(graph, s, e))

    close()
}

private fun dijkstra(graph: Array<ArrayList<Pair<Int, Int>>>, s: Int, e: Int): Int {
    val dist = IntArray(graph.size) { Int.MAX_VALUE }
    dist[s] = 0

    // Pair -> 현재 노드, 누적 가중치
    val pq = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })
    pq.add(s to 0)

    while (pq.isNotEmpty()) {
        val (node, totalWeight) = pq.poll()

        if(dist[node] < totalWeight) continue
        if(node == e) return totalWeight

        val linkedNodes = graph[node]
        for ((nextNode, weight) in linkedNodes) {
            val nextWeight = totalWeight + weight
            if (dist[nextNode] > nextWeight) {
                dist[nextNode] = nextWeight
                pq.add(nextNode to nextWeight)
            }
        }
    }

    return -1
}