package impl.simulation

import java.util.*

private val dy = intArrayOf(0, 0, 1, -1)
private val dx = intArrayOf(1, -1, 0, 0)

private data class Robot2(
    val y: Int,
    val x: Int,
    val dir: Int,
    val time: Int,
) {
    fun dirs() = arrayOf(0 to dir, 1 to turnRight(), 1 to turnLeft(), 2 to flip())
    private fun turnRight() = when(dir) {
        0 -> 2
        1 -> 3
        2 -> 1
        3 -> 0
        else -> -1
    }
    private fun turnLeft() = when(dir) {
        0 -> 3
        3 -> 1
        1 -> 2
        2 -> 0
        else -> -1
    }
    private fun flip() = when(dir) {
        0 -> 1
        1 -> 0
        2 -> 3
        3 -> 2
        else -> -1
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val (m, n) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val map = Array(m) {
        val st = StringTokenizer(readLine())
        IntArray(n) { st.nextToken().toInt() }
    }

    val (ry, rx, rd) = position(readLine())
    val (ey, ex, ed) = position(readLine())

    var min = Int.MAX_VALUE
    val dist = Array(m) { Array(n) { IntArray(4) { Int.MAX_VALUE } } }

    val robot = Robot2(ry, rx, rd, 0)

    robot.dirs().forEach { (cost, dir) ->
        dist[ry][rx][dir] = cost
    }

    val queue: Queue<Robot2> = LinkedList()
    queue.add(robot)

    while (queue.isNotEmpty()) {
        val robot = queue.poll()
        val (y, x, dir, time) = robot

        if (dist[y][x][dir] < time) continue
        if (y == ey && x == ex && dir == ed) {
            min = minOf(min, time)
            break
        }

        for ((cost, nd) in robot.dirs()) {
            if (y == ey && x == ex) {
                if (nd == ed) {
                    min = minOf(min, time + cost)
                    break
                } else {
                    continue
                }
            }
            for (k in 1..3) {
                val ny = y + (dy[nd] * k)
                val nx = x + (dx[nd] * k)

                if (ny in 0 until m && nx in 0 until n && map[ny][nx] != 1) {
                    val nextTime = time + cost + 1
                    if (dist[ny][nx][nd] > nextTime) {
                        dist[ny][nx][nd] = nextTime
                        queue.add(Robot2(ny, nx, nd, nextTime))
                    }
                } else {
                    break
                }
            }
        }
    }

    println(min)

    close()
}

private fun position(line: String) = StringTokenizer(line).run {
    Triple(nextToken().toInt() - 1, nextToken().toInt() - 1, nextToken().toInt() - 1)
}