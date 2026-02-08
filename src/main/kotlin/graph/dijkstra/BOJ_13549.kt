package graph.dijkstra

import java.util.StringTokenizer
import kotlin.collections.ArrayDeque
import kotlin.collections.isNotEmpty

fun main() = with(System.`in`.bufferedReader()) {
    val (n, k) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    println(dijkstra(n, k))

    close()
}

private fun dijkstra(n: Int, k: Int): Int {
    val size = 100_000
    val dist = BooleanArray(size + 1) { false }
    dist[n] = true

    // Pair -> 현재 위치, 누적 가중치
    val deque = ArrayDeque<Pair<Int, Int>>()
    deque.addFirst(n to 0)

    while (deque.isNotEmpty()) {
        val (cur, totalSecond) = deque.removeFirst()

        if (cur == k) return totalSecond

        val n1 = cur - 1
        val n1Second = totalSecond + 1
        if (n1 in 0..size && !dist[n1]) {
            dist[n1] = true
            deque.addLast(n1 to n1Second)
        }

        val n2 = cur + 1
        val n2Second = totalSecond + 1
        if (n2 in 0..size && !dist[n2]) {
            dist[n2] = true
            deque.addLast(n2 to n2Second)
        }

        val n3 = cur * 2
        val n3Second = totalSecond
        if (n3 in 0..size && !dist[n3]) {
            dist[n3] = true
            deque.addFirst(n3 to n3Second)
        }
    }

    return 0
}