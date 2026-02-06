package graph.dijkstra

import java.util.StringTokenizer

private class Node3(val y: Int, val x: Int, var cost: Int)

// 시계 방향
private val dy = intArrayOf(-1, 0, 1, 0)
private val dx = intArrayOf(0, 1, 0, -1)

fun main() = with(System.`in`.bufferedReader()) {
    val (h, w) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    var sy = 0
    var sx = 0

    val map = Array(h) { y ->
        val line = readLine()
        CharArray(w) { x ->
            line[x].also {
                if (it == 'S') {
                    sy = y
                    sx = x
                }
            }
        }
    }

    println(dijkstra(map, sy, sx, h, w))

    close()
}

private fun dijkstra(map: Array<CharArray>, sy: Int, sx: Int, h: Int, w: Int): Int {
    val dist = Array(h) { IntArray(w) { Int.MAX_VALUE } }
    val deque = ArrayDeque<Node3>()
    deque.add(Node3(sy, sx, 0))

    while(deque.isNotEmpty()) {
        val cur = deque.removeFirst()
        val y = cur.y
        val x = cur.x
        val cost = cur.cost

        if (dist[y][x] < cost) continue
        if (map[y][x] == 'E') return cost

        val canRide = canRide(map, y, x)

        for (dir in 0..3) {
            val ny = y + dy[dir]
            val nx = x + dx[dir]

            if (ny in 0 until h && nx in 0 until w) {
                if (map[ny][nx] == '#') continue

                if(canRide) {
                    val canRideNext = canRide(map, ny, nx)
                    if(canRideNext) {
                        if (dist[ny][nx] > cost) {
                            dist[ny][nx] = cost
                            deque.addFirst(Node3(ny, nx, cost))
                        }
                    } else {
                        if (dist[ny][nx] > cost + 1) {
                            dist[ny][nx] = cost + 1
                            deque.addLast(Node3(ny, nx, cost + 1))
                        }
                    }
                } else {
                    val nextCost = cost + 1
                    if (dist[ny][nx] > nextCost) {
                        dist[ny][nx] = nextCost
                        deque.addLast(Node3(ny, nx, nextCost))
                    }
                }
            }
        }
    }

    return 0
}

private fun canRide(map: Array<CharArray>, y: Int, x: Int): Boolean {
    for (dir in 0..3) {
        val ny = y + dy[dir]
        val nx = x + dx[dir]

        if (ny in 0 until map.size && nx in 0 until map[0].size) {
            if (map[ny][nx] == '#') {
                return true
            }
        }
    }

    return false
}