package graph.dijkstra

import java.util.PriorityQueue
import java.util.StringTokenizer

private class Node5(
    val y: Int,
    val x: Int,
    var broken: Int,
    val time: Int
) : Comparable<Node5> {
    override fun compareTo(other: Node5) = time - other.time
}

fun main() = System.`in`.bufferedReader().use { br ->
    val (n, m, k) = StringTokenizer(br.readLine()).run {
         Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val map = Array(n) {
        val line = br.readLine().toCharArray()
        CharArray(m) {
            line[it]
        }
    }

    println(dijkstra(map, n, m, k))
}

private val dy = intArrayOf(0, 1, 0, -1)
private val dx = intArrayOf(1, 0, -1, 0)

private const val EMPTY = '0'
private const val WALL = '1'

private fun dijkstra(map: Array<CharArray>, n: Int, m: Int, k: Int): Int {
    val dist = Array(n) { Array(m) { IntArray(k + 1) { Int.MAX_VALUE } } }
    val pq = PriorityQueue<Node5>()

    pq.add(Node5(0, 0, 0, 1))

    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val y = cur.y
        val x = cur.x
        val broken = cur.broken
        val time = cur.time

        if (dist[y][x][broken] < time) continue
        if (y == n - 1 && x == m - 1) return time

        for (dir in 0 until 4) {
            val ny = y + dy[dir]
            val nx = x + dx[dir]

            if (ny in 0 until n && nx in 0 until m) {
                val next = map[ny][nx]
                val nextTime = time + 1

                when (next) {
                    EMPTY -> {
                        if (dist[ny][nx][broken] > nextTime) {
                            dist[ny][nx][broken] = nextTime
                            pq.add(Node5(ny, nx, broken, nextTime))
                        }
                    }
                    WALL -> {
                        val nextBroken = broken + 1
                        if (broken < k && dist[ny][nx][nextBroken] > nextTime) {
                            dist[ny][nx][nextBroken] = nextTime
                            pq.add(Node5(ny, nx, nextBroken, nextTime))
                        }
                    }
                }
            }
        }
    }

    return -1
}