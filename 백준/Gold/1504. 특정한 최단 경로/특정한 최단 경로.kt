import java.util.PriorityQueue
import java.util.StringTokenizer

private class Node6(
    val cur: Int,
    val total: Int,
    val v1Pass: Boolean,
    val v2Pass: Boolean,
) : Comparable<Node6> {
    val vPassCount: Int
        get() = when {
            !v1Pass && !v2Pass -> 0
            v1Pass && !v2Pass -> 1
            !v1Pass && v2Pass -> 2
            else -> 3
        }

    override fun compareTo(other: Node6) = total - other.total
}

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

    println(
        dijkstra(nodes, n, v1, v2)
    )

    close()
}

private fun dijkstra(nodes: Array<ArrayList<Pair<Int, Int>>>, n: Int, v1: Int, v2: Int): Int {
    val dist = Array(4) { IntArray(n + 1) { Int.MAX_VALUE } }
    dist[0][1] = 0

    val pq = PriorityQueue<Node6>()
    pq.add(Node6(1, 0, 1 == v1, 1 == v2))

    while (pq.isNotEmpty()) {
        val node = pq.poll()
        val cur = node.cur
        val vPassCount = node.vPassCount
        val v1Pass = node.v1Pass
        val v2Pass = node.v2Pass
        val total = node.total

        if (dist[vPassCount][cur] < total) continue
        if (node.v1Pass && node.v2Pass && cur == n) return total

        for ((target, weight) in nodes[cur]) {
            val nextV1Pass = v1Pass || target == v1
            val nextV2Pass = v2Pass || target == v2
            val nextVPassCount = when {
                !nextV1Pass && !nextV2Pass -> 0
                nextV1Pass && !nextV2Pass -> 1
                !nextV1Pass && nextV2Pass -> 2
                else -> 3
            }
            val nextWeight = total + weight

            if (dist[nextVPassCount][target] > nextWeight) {
                dist[nextVPassCount][target] = nextWeight
                pq.add(Node6(target, nextWeight, nextV1Pass, nextV2Pass))
            }
        }
    }

    return -1
}