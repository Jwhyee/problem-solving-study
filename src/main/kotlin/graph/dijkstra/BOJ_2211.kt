package graph.dijkstra

import java.util.PriorityQueue
import java.util.StringTokenizer

private class Node2(val idx: Int, val cost: Int) : Comparable<Node2> {
    override fun compareTo(other: Node2): Int {
        return this.cost - other.cost
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val networks = Array(n + 1) { ArrayList<Node2>() }

    repeat(m) {
        val st = StringTokenizer(readLine())
        val u = st.nextToken().toInt()
        val v = st.nextToken().toInt()
        val w = st.nextToken().toInt()

        networks[u].add(Node2(v, w))
        networks[v].add(Node2(u, w))
    }

    val prev = IntArray(n + 1) { 0 }

    dijkstra(networks, prev, n)

    val sb = StringBuilder()
    sb.append(n - 1).append('\n')

    for (node in 2..n) {
        sb.append(node).append(' ').append(prev[node]).append('\n')
    }
    print(sb)

    close()
}

private fun dijkstra(
    networks: Array<ArrayList<Node2>>,
    prev: IntArray,
    n: Int
) {
    val pq = PriorityQueue<Node2>()
    val dist = IntArray(n + 1) { Int.MAX_VALUE }

    dist[1] = 0
    pq.add(Node2(1, 0))

    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val curNode = cur.idx
        val curCost = cur.cost

        if (dist[curNode] < curCost) continue

        for (next in networks[curNode]) {
            val nextNode = next.idx
            val nextCost = curCost + next.cost

            if (dist[nextNode] > nextCost) {
                dist[nextNode] = nextCost
                prev[nextNode] = curNode
                pq.add(Node2(nextNode, nextCost))
            }
        }
    }
}