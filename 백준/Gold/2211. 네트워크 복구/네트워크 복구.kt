import java.util.PriorityQueue
import java.util.StringTokenizer

private data class Network(
    val to: Int, val cost: Int
)

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val networks = Array(n + 1) { arrayListOf<Network>() }

    repeat(m) {
        val st = StringTokenizer(readLine())

        val computer1 = st.nextToken().toInt()
        val computer2 = st.nextToken().toInt()
        val weight = st.nextToken().toInt()

        networks[computer1].add(Network(computer2, weight))
        networks[computer2].add(Network(computer1, weight))
    }

    val prev = IntArray(n + 1) { 0 }

    dijkstra(networks, prev, n)

    sb.append(n - 1).append("\n")

    for (node in 2..n) {
        sb.append("$node ${prev[node]}").append("\n")
    }

    println(sb)

    close()
}

private fun dijkstra(
    networks: Array<ArrayList<Network>>,
    prev: IntArray,
    n: Int
): Int {
    // to, cost
    val pq = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })
    val dist = Array(n + 1) { Int.MAX_VALUE }
    dist[1] = 0

    pq.add(1 to 0)

    while (pq.isNotEmpty()) {
        val (curNode, curCost) = pq.poll()

        if (dist[curNode] < curCost) continue

        val networkList = networks[curNode]

        for ((nextNode, cost) in networkList) {
            val nextCost = curCost + cost

            if (dist[nextNode] > nextCost) {
                prev[nextNode] = curNode

                dist[nextNode] = nextCost
                pq.add(nextNode to nextCost)
            }
        }
    }

    return -1
}