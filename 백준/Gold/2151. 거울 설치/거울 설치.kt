import java.util.PriorityQueue

private const val WALL = '*'
private const val EMPTY = '.'
private const val DOOR = '#'
private const val MIRROR = '!'

private data class Info(
    val y: Int,
    val x: Int,
    val dir: Int,
    val mirrorCount: Int
)

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()

    val doors = ArrayList<Pair<Int, Int>>(2)

    val map = Array(n) { i ->
        val line = readLine()
        CharArray(n) { j ->
            if (line[j] == DOOR) {
                doors.add(i to j)
            }
            line[j]
        }
    }

    println(dijkstra(map, doors, n))

    close()
}

private val dy = intArrayOf(1, 0, -1, 0)
private val dx = intArrayOf(0, -1, 0, 1)

private fun nextDir(dir: Int) = when(dir) {
    0 -> 1 to 3
    1 -> 2 to 0
    2 -> 3 to 1
    3 -> 0 to 2
    else -> -1 to -1
}

private fun dijkstra(
    map: Array<CharArray>,
    doors: ArrayList<Pair<Int, Int>>,
    n: Int
): Int {
    // y, x, (direction, mirrorCount)
    val pq = PriorityQueue<Info>(compareBy { it.mirrorCount })
    val dists = Array(n) { Array(n) { IntArray(4) { Int.MAX_VALUE } } }

    val (entryY, entryX) = doors[0]
    val (exitY, exitX) = doors[1]

    for (dir in 0 until 4) {
        dists[entryY][entryX][dir] = 0
        pq.add(Info(entryY, entryX, dir, 0))
    }

    while (pq.isNotEmpty()) {
        val (y, x, dir, mirrorCount) = pq.poll()

        if (mirrorCount > dists[y][x][dir]) {
            continue
        }

        val ny = y + dy[dir]
        val nx = x + dx[dir]

        if (ny in 0 until n && nx in 0 until n && map[ny][nx] != WALL) {
            // 1. 거울을 설치하지 않고 직진하는 경우
            if (dists[ny][nx][dir] > mirrorCount) {
                dists[ny][nx][dir] = mirrorCount
                pq.add(Info(ny, nx, dir, mirrorCount))
            }

            // 2. 거울을 설치하고 방향을 바꾸는 경우
            if (map[ny][nx] == MIRROR) {
                val (opt1, opt2) = nextDir(dir)
                val nextMirrorCount = mirrorCount + 1

                // 방향 전환 1
                if (dists[ny][nx][opt1] > nextMirrorCount) {
                    dists[ny][nx][opt1] = nextMirrorCount
                    pq.add(Info(ny, nx, opt1, nextMirrorCount))
                }
                // 방향 전환 2
                if (dists[ny][nx][opt2] > nextMirrorCount) {
                    dists[ny][nx][opt2] = nextMirrorCount
                    pq.add(Info(ny, nx, opt2, nextMirrorCount))
                }
            }
        }
    }

    return dists[exitY][exitX].minOrNull() ?: -1
}