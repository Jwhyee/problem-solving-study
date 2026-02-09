package graph.dijkstra

private class Node4(
    val y: Int, val x: Int, val weight: Int
)

private val dy = intArrayOf(0, 1, 0, -1)
private val dx = intArrayOf(1, 0, -1, 0)

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val map = Array(n) {
        val line = readLine()
        IntArray(n) {
            line[it].digitToInt()
        }
    }

    println(dijkstra(map, n))

    close()
}

private fun dijkstra(map: Array<IntArray>, n: Int): Int {
    val dist = Array(n) { IntArray(n) { Int.MAX_VALUE } }
    val deque = ArrayDeque<Node4>()

    deque.add(Node4(0, 0, 0))

    while (deque.isNotEmpty()) {
        val cur = deque.removeFirst()
        val curY = cur.y
        val curX = cur.x
        val curWeight = cur.weight

        if(dist[curY][curX] < curWeight) continue
        if(curY == n - 1 && curX == n - 1) return curWeight

        for (dir in 0 until 4) {
            val ny = curY + dy[dir]
            val nx = curX + dx[dir]

            if (ny in 0 until n && nx in 0 until n) {
                val isWhiteRoom = map[ny][nx] == 1
                val nWeight = curWeight + if (isWhiteRoom) 0 else 1
                if (dist[ny][nx] > nWeight) {
                    dist[ny][nx] = nWeight
                    if (isWhiteRoom) {
                        deque.addFirst(Node4(ny, nx, nWeight))
                    } else {
                        deque.addLast(Node4(ny, nx, nWeight))
                    }
                }
            }
        }
    }

    return 0
}