import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer
import kotlin.math.min

private data class Position(
    val x: Int,
    val y: Int
)

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    val positions = Array(n) { Position(-1, -1) }

    var st: StringTokenizer

    var maxX = Int.MIN_VALUE
    var maxY = Int.MIN_VALUE

    repeat(n) {
        st = StringTokenizer(readLine())
        val x = st.nextToken().toInt()
        val y = st.nextToken().toInt()

        maxX = maxOf(maxX, x)
        maxY = maxOf(maxY, y)

        positions[it] = Position(x, y)
    }

    val map = Array(maxY + 1) { IntArray(maxX) { -1 } }

    // y 좌표 기준으로 가장 밑에서 높은 좌표 찾기
    var maxHeight = Int.MIN_VALUE
    for(i in 0 until positions.size - 1) {
        val (x1, y1) = positions[i]
        val (x2, y2) = positions[i + 1]

        if (x1 == x2) {
            maxHeight = maxOf(maxHeight, y1, y2)
        } else {
            for (j in x1 until x2) {
                for (k in 0 until minOf(maxHeight, y1, y2)) {
                    map[k][j] = 1
                }
            }
        }

    }

    val k = readLine().toInt()
    val holes = Array(k) { Position(1, 1) }
    repeat(k) {
        st = StringTokenizer(readLine())
        val (x1, y1) = st.nextToken().toInt() to st.nextToken().toInt()
        val (x2, _) = st.nextToken().toInt() to st.nextToken().toInt()

        // map[y1][x1] ~ map[y2][x2]
        for (x in x1 until x2) {
            map[y1][x] = -2
            holes[it] = Position(x, y1)
        }
    }

    bfs(map, holes)

    var sum = 0
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (map[i][j] == 1) {
                sum++
            }
        }
    }
    println(sum)
}

private val dx = intArrayOf(1, 0, -1)
private val dy = intArrayOf(0, -1, 0)

private fun bfs(map: Array<IntArray>, holes: Array<Position>) {
    val visited = Array(map.size) { BooleanArray(map[0].size) }

    for (hole in holes) {
        val queue: Queue<Position> = LinkedList()

        queue += hole

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            val (x, y) = current

            visited[y][x] = true

            for (i in 0 until 3) {
                val ny = y + dy[i]
                val nx = x + dx[i]

                if (ny < 0 || ny >= map.size || nx < 0 || nx >= map[0].size || map[ny][nx] == -1 || map[ny][nx] == -2) continue

                if(!visited[ny][nx] && hole.y > ny) {
                    map[ny][nx] = -1
                    queue += Position(nx, ny)
                }
            }
        }
    }
}