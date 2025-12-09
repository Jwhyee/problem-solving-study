package dijkstra

import java.util.*
import kotlin.math.max

private data class City(
    val to: Int, val time: Int
)

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (n, m) = st.nextToken().toInt() to st.nextToken().toInt()

    val graph = Array(n + 1) { arrayListOf<City>() }

    repeat(m) {
        st = StringTokenizer(readLine())

        val (a, b, t) = Triple(
            st.nextToken().toInt(), st.nextToken().toInt(), st.nextToken().toInt()
        )

        graph[a] += City(b, t)
        graph[b] += City(a, t)
    }

    val prev = IntArray(n + 1) { 0 }

    val min = bfs(n, 1, n, 0, 0, prev, graph)

    var from = n
    var max = 0
    while (prev[from] != from) {
        val to = prev[from]

        val time = (bfs(n, n, 1, from, to, prev, graph))
        max = max(time, max)

        if (max == Int.MAX_VALUE) {
            break
        }

        from = prev[from]
    }

    println(if (max == Int.MAX_VALUE) -1 else max - min)

    close()
}

private fun bfs(
    n: Int,
    start: Int,
    end: Int,
    from: Int,
    to: Int,
    prev: IntArray,
    graph: Array<ArrayList<City>>,
): Int {
    val minQueue = PriorityQueue<Pair<Int, Int>> { a, b -> a.second - b.second }

    val minTimes = IntArray(n + 1) { Int.MAX_VALUE }

    minQueue += start to 0
    minTimes[start] = 0

    while (minQueue.isNotEmpty()) {
        val (curNode, curTime) = minQueue.poll()

        if (minTimes[curNode] < curTime) continue
        if (curNode == end) return minTimes[end]

        val nextCityList = graph[curNode]

        for ((nextNode, time) in nextCityList) {
            if (curNode == from && nextNode == to) {
                continue
            }
            if (minTimes[nextNode] > minTimes[curNode] + time) {
                if (start == 1) {
                    prev[nextNode] = curNode
                }

                minTimes[nextNode] = minTimes[curNode] + time

                minQueue += nextNode to minTimes[nextNode]
            }
        }
    }

    return Int.MAX_VALUE
}