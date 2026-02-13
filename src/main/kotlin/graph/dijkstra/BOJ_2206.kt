package graph.dijkstra

import java.util.StringTokenizer
import kotlin.collections.ArrayDeque

private class Node5(
    val y: Int,
    val x: Int,
    var broken: Int,
    val time: Int
)

fun main() = System.`in`.bufferedReader().use { br ->
    val (n, m, k) = StringTokenizer(br.readLine()).run {
         Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val map = Array(n) { br.readLine().toCharArray() }

    println(dijkstra(map, n, m, k))
}

private val dy = intArrayOf(0, 1, 0, -1)
private val dx = intArrayOf(1, 0, -1, 0)

private const val EMPTY = '0'
private const val WALL = '1'

private fun dijkstra(map: Array<CharArray>, n: Int, m: Int, k: Int): Int {
    if (n == 1 && m == 1) return 1

    val visited = Array(n) { Array(m) { BooleanArray(k + 1) } }
    val deque = ArrayDeque<Node5>()

    deque.add(Node5(0, 0, 0, 1))
    visited[0][0][0] = true

    while (deque.isNotEmpty()) {
        val cur = deque.removeFirst()

        if (cur.y == n - 1 && cur.x == m - 1) return cur.time

        for (i in 0 until 4) {
            val ny = cur.y + dy[i]
            val nx = cur.x + dx[i]

            if (ny in 0 until n && nx in 0 until m) {
                val next = map[ny][nx]
                val nextTime = cur.time + 1

                when(next) {
                    EMPTY -> {
                        if (!visited[ny][nx][cur.broken]) {
                            visited[ny][nx][cur.broken] = true
                            deque.add(Node5(ny, nx, cur.broken, nextTime))
                        }
                    }
                    WALL -> {
                        if (cur.broken < k && !visited[ny][nx][cur.broken + 1]) {
                            visited[ny][nx][cur.broken + 1] = true
                            deque.add(Node5(ny, nx, cur.broken + 1, nextTime))
                        }
                    }
                }
            }
        }
    }

    return -1
}