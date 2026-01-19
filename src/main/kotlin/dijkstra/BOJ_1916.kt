package dijkstra

import java.util.PriorityQueue
import java.util.StringTokenizer

private data class Bus(
    val to: Int,
    val price: Int
)

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val m = readLine().toInt()

    val graph = Array(n + 1) { arrayListOf<Bus>() }

    repeat(m) {
        val (start, end, price) = StringTokenizer(readLine()).run {
            Triple(
                nextToken().toInt(), nextToken().toInt(), nextToken().toInt()
            )
        }

        graph[start] += Bus(end, price)
    }
}

private fun dijkstra() {
    val pq = PriorityQueue<Pair<Int, Int>> { a, b -> a.second - b.second }
}