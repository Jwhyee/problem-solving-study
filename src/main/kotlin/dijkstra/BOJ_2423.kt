package dijkstra

import java.util.StringTokenizer

private const val BACK_SLASH = '\\'
private const val SLASH = '/'
private val dirMap = mapOf(
    // 12시, 3시, 5시, 6시, 9시, 11시 / 5시(4, 5), 11(10, 11)시는 같은 타일이어야 함
    BACK_SLASH to intArrayOf(-1, 0, 0, 1, 1, 1, 1, 0, 0, -1, -1, -1),
    // 12시, 1시, 3시, 6시, 7시, 9시 / 1시(2, 3), 7시(8, 9)는 같은 타일이어야 함
    SLASH to intArrayOf(-1, 0, -1, 1, 0, 1, 1, 0, 1, -1, 0, -1),
)

private fun mustSameTile(tile: Char, i1: Int, i2: Int) = when(tile) {
    BACK_SLASH -> i1 == 4 && i2 == 5 || i1 == 10 && i2 == 11
    SLASH -> i1 == 2 && i2 == 3 || i1 == 8 && i2 == 9
    else -> false
}

private class Node1(
    val y: Int,
    val x: Int,
    val tile: Char,
    val cost: Int
)

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val map = Array(n) {
        val line = readLine()
        CharArray(m) {
            line[it]
        }
    }

    val result = dijkstra(map, n, m).let {
        if (it == -1) "NO SOLUTION"
        else "$it"
    }

    println(result)

    close()
}

private fun rotate90(tile: Char) = when(tile) {
    BACK_SLASH -> SLASH
    SLASH -> BACK_SLASH
    else -> '_'
}

private fun getIndex(tile: Char) = when(tile) {
    BACK_SLASH -> 0
    SLASH -> 1
    else -> -1
}

private fun dijkstra(map: Array<CharArray>, n: Int, m: Int): Int {
    // 0: BACK_SLASH, 1: SLASH
    val dist = Array(n) { Array(m) { IntArray(2) { Int.MAX_VALUE } } }
    val deque = ArrayDeque<Node1>()

    if (map[0][0] == SLASH) {
        deque.add(Node1(0, 0, BACK_SLASH, 1))
    } else {
        deque.add(Node1(0, 0, BACK_SLASH, 0))
    }

    while (deque.isNotEmpty()) {
        val cur = deque.removeFirst()

        val cost = cur.cost
        val tile = cur.tile
        val index = getIndex(tile)

        if(dist[cur.y][cur.x][index] < cost) continue
        if(cur.y == n - 1 && cur.x == m - 1 && cur.tile == BACK_SLASH) return cost

        for (d in 0 until 12 step 2) {
            val ny = cur.y + dirMap[tile]!![d]
            val nx = cur.x + dirMap[tile]!![d + 1]

            if (ny in 0 until n && nx in 0 until m) {
                val mustSame = mustSameTile(tile, d, d + 1)
                val nextTile = map[ny][nx]

                if (mustSame) {
                    if (tile == nextTile) {
                        if (dist[ny][nx][index] > cost) {
                            dist[ny][nx][index] = cost
                            deque.addFirst(Node1(ny, nx, tile, cost))
                        }
                    } else {
                        val rotatedTile = rotate90(nextTile)
                        val nextIndex = getIndex(rotatedTile)
                        val nextCost = cost + 1

                        if (dist[ny][nx][nextIndex] > nextCost) {
                            dist[ny][nx][nextIndex] = nextCost
                            deque.addLast(Node1(ny, nx, rotatedTile, nextCost))
                        }
                    }
                } else {
                    if (tile != nextTile) {
                        val nextIndex = getIndex(nextTile)
                        if (dist[ny][nx][nextIndex] > cost) {
                            dist[ny][nx][nextIndex] = cost
                            deque.addFirst(Node1(ny, nx, nextTile, cost))
                        }
                    } else {
                        val rotatedTile = rotate90(nextTile)
                        val nextIndex = getIndex(rotatedTile)
                        val nextCost = cost + 1

                        if (dist[ny][nx][nextIndex] > nextCost) {
                            dist[ny][nx][nextIndex] = nextCost
                            deque.addLast(Node1(ny, nx, rotatedTile, nextCost))
                        }
                    }
                }
            }

        }
    }

    return -1
}

/*
// 현재 루트대로 가는 방법
        val ny = cur.y + dirMap[tile]!!.first
        val nx = cur.x + dirMap[tile]!!.second

        if (ny in 0 until n && nx in 0 until m) {
            val nextTile = map[ny][nx]
            if (tile == nextTile) {
                if (dist[ny][nx][index] > cur.cost) {
                    dist[ny][nx][index] = cur.cost
                    deque.addFirst(Node1(ny, nx, nextTile, cur.cost))
                }
            } else {
                val nextCost = cur.cost + 1
                val nextIndex = getIndex(nextTile)
                if (dist[ny][nx][nextIndex] > nextCost) {
                    dist[ny][nx][nextIndex] = nextCost
                    deque.addLast(Node1(ny, nx, nextTile, nextCost))
                }
            }
        }

        // 바로 오른쪽 칸을 뒤집어서 이어지는 경우
        val ny2 = cur.y
        val nx2 = cur.x + 1
        if (ny2 in 0 until n && nx2 in 0 until m) {
            val nextTile = map[ny2][nx2]
            if (tile != nextTile) {
                val nextIndex = getIndex(nextTile)
                if (dist[ny2][nx2][nextIndex] > cur.cost) {
                    dist[ny2][nx2][nextIndex] = cur.cost
                    deque.addFirst(Node1(ny2, nx2, nextTile, cur.cost))
                }
            } else {
                val t = rotate90(nextTile)
                val nextIndex = getIndex(t)
                val nextCost = cur.cost + 1

                if (dist[ny2][nx2][nextIndex] > nextCost) {
                    dist[ny2][nx2][nextIndex] = nextCost
                    deque.addLast(Node1(ny2, nx2, t, nextCost))
                }
            }
        }
* */