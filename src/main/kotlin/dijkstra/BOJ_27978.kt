package dijkstra

import java.util.ArrayDeque
import java.util.StringTokenizer

private const val SEA = '.'
private const val REEF = '#'
private const val TREASURE = '*'
private const val SHIP = 'K'

fun main() = with(System.`in`.bufferedReader()) {
    val (h, w) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    // 0: 배, 1: 보물
    val info = Array(2) { 0 to 0 }

    val map = Array(h) { i ->
        val line = readLine()
        CharArray(w) { j ->
            val mark = when (val cur = line[j]) {
                SHIP -> {
                    info[0] = i to j
                    SEA
                }

                TREASURE -> {
                    info[1] = i to j
                    cur
                }

                else -> cur
            }

            mark
        }
    }

    println(dijkstra(map, info, h, w))

    close()
}

private data class State(val y: Int, val x: Int, val cost: Int)

private fun dijkstra(
    map: Array<CharArray>,
    info: Array<Pair<Int, Int>>,
    h: Int,
    w: Int
): Int {
    // 12시부터 시계 방향으로 8방향
    val dy = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
    val dx = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)

    val deque = ArrayDeque<State>()
    val minCost = Array(h) { IntArray(w) { Int.MAX_VALUE } }

    val (entryY, entryX) = info[0]
    val (treasureY, treasureX) = info[1]

    minCost[entryY][entryX] = 0
    deque.add(State(entryY, entryX, 0))

    while (deque.isNotEmpty()) {
        val (y, x, cost) = deque.removeFirst()

        if (cost > minCost[y][x]) {
            continue
        }

        if (y == treasureY && x == treasureX) {
            return cost
        }

        for (dir in 0 until 8) {
            val ny = y + dy[dir]
            val nx = x + dx[dir]

            if (ny in 0 until h && nx in 0 until w && map[ny][nx] != REEF) {
                val add = when (dir) {
                    1, 2, 3 -> 0
                    else -> 1
                }
                val nextCost = cost + add

                if (minCost[ny][nx] > nextCost) {
                    minCost[ny][nx] = nextCost
                    if (add == 0) {
                        deque.addFirst(State(ny, nx, nextCost))
                    } else {
                        deque.addLast(State(ny, nx, nextCost))
                    }
                }
            }
        }
    }
    return -1
}