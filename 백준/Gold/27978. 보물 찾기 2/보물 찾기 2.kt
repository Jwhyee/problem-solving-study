import java.util.PriorityQueue
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

private data class Ship(
    val y: Int,
    val x: Int,
    val dir: Int,
    val totalCost: Int
) {
    fun useFuelToNextDir(index: Int) = when (index) {
        1, 2, 3 -> false
        else -> true
    }
}

private fun dijkstra(
    map: Array<CharArray>,
    info: Array<Pair<Int, Int>>,
    h: Int,
    w: Int
): Int {
    // 12시부터 시계 방향으로 8방향
    val dy = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
    val dx = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)

    val pq = PriorityQueue<Ship>(compareBy { it.totalCost })
    val minCost = Array(h) { Array(w) { IntArray(8) { Int.MAX_VALUE } } }

    val (entryY, entryX) = info[0]
    for (dir in 0 until 8) {
        val ny = entryY + dy[dir]
        val nx = entryX + dx[dir]

        if (ny in 0 until h && nx in 0 until w) {
            val next = map[ny][nx]
            when (next) {
                SEA, TREASURE -> pq.add(Ship(entryY, entryX, dir, 0))
            }
        }
    }

    while (pq.isNotEmpty()) {
        val ship = pq.poll()
        val (y, x, dir, totalCost) = ship

        if (minCost[y][x][dir] < totalCost) continue
        if (map[y][x] == TREASURE) return totalCost

        for (nextDir in 0 until 8) {
            val ny = y + dy[nextDir]
            val nx = x + dx[nextDir]

            if (ny in 0 until h && nx in 0 until w) {
                val next = map[ny][nx]
                when (next) {
                    SEA, TREASURE -> {
                        val add = if (ship.useFuelToNextDir(nextDir)) 1 else 0
                        val nextCost = totalCost + add
                        if (minCost[ny][nx][dir] > nextCost) {
                            minCost[ny][nx][dir] = nextCost
                            pq.add(Ship(ny, nx, dir, nextCost))
                        }
                    }
                }
                ship.useFuelToNextDir(nextDir)
            }
        }
    }

    return -1
}