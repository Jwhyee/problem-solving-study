package graph.bfs

import java.util.*

private const val INF = 100_000_000

fun main() = with(System.`in`.bufferedReader()){
    val bw = System.out.bufferedWriter()

    var st = StringTokenizer(readLine())

    val (v, e) = st.nextToken().toInt() to st.nextToken().toInt()

    val k = readLine().toInt() - 1

    val graph = Array(v) { arrayListOf<Pair<Int, Int>>() }
    val distance = IntArray(v) { INF }

    repeat(e) {
        st = StringTokenizer(readLine())
        val (u, v, w) = Triple(
            st.nextToken().toInt() - 1, st.nextToken().toInt() - 1, st.nextToken().toInt()
        )

        graph[u] += v to w
    }

    bfs(k, distance, graph)

    repeat(v) { i ->
        val target = distance[i]
        val result = if(target == INF) "INF" else "$target"

        bw.write(result)
        bw.newLine()
    }

    bw.flush()
    bw.close()
    close()
}

private fun bfs(
    k: Int,
    distance: IntArray,
    graph: Array<ArrayList<Pair<Int, Int>>>,
) {
    val queue = PriorityQueue<Pair<Int, Int>> { a, b -> a.second - b.second }
    val visited = BooleanArray(graph.size) { false }

    queue += k to 0
    distance[k] = 0

    while (queue.isNotEmpty()) {
        val (curNode, curWeight) = queue.poll()

        if(visited[curNode]) continue

        visited[curNode] = true

        val nextNodeList = graph[curNode]

        nextNodeList.forEach { (nextNode, weight) ->
            if (distance[nextNode] > distance[curNode] + weight) {
                distance[nextNode] = distance[curNode] + weight
                queue += nextNode to distance[nextNode]
            }
        }
    }
}