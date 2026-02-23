import java.util.PriorityQueue
import java.util.StringTokenizer

private data class Node6(
    val node: Int,
    val weight: Int
) : Comparable<Node6> {
    override fun compareTo(other: Node6) = weight - other.weight
}

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m, r) = triple(readLine())

    val st = StringTokenizer(readLine())
    val items = Array(n) { st.nextToken().toInt() }
    val graph = Array(n) { arrayListOf<Pair<Int, Int>>() }

    repeat(r) {
        val (a, b, l) = triple(readLine(), true)

        graph[a].add(b to l)
        graph[b].add(a to l)
    }

    fun dijkstra(start: Int): Int {
        val dist = IntArray(n) { Int.MAX_VALUE }
        val pq = PriorityQueue<Node6>()
        pq.add(Node6(start, 0))

        dist[start] = 0

        var itemCount = 0

        while (pq.isNotEmpty()) {
            val (node, weight) = pq.poll()

            if (dist[node] < weight) {
                continue
            }
            if (weight > m) continue

            itemCount += items[node]

            for ((nextNode, nextWeight) in graph[node]) {
                val nextTotalWeight = weight + nextWeight
                if (dist[nextNode] > nextTotalWeight && nextTotalWeight <= m) {
                    dist[nextNode] = nextTotalWeight
                    pq.add(Node6(nextNode, nextTotalWeight))
                }
            }
        }

        return itemCount
    }

    var max = 0

    for (i in 0 until n) {
        max = maxOf(max, dijkstra(i))
    }

    println(max)

    close()
}

private fun triple(line: String, isIndex: Boolean = false) = StringTokenizer(line).run {
    Triple(
        nextToken().toInt() - if (isIndex) 1 else 0,
        nextToken().toInt() - if (isIndex) 1 else 0,
        nextToken().toInt()
    )
}