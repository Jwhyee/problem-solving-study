import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

private class BabyShark(
    val y: Int,
    val x: Int,
    val ate: Int,
    val size: Int,
    val time: Int,
) {
    fun sizeUp() = if (ate == size) BabyShark(y, x, 0, size + 1, time) else this
}

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    var fishCount = 0

    var sy = 0
    var sx = 0

    val map = Array(n) { y ->
        val st = StringTokenizer(readLine())
        IntArray(n) { x ->
            when(val cur = st.nextToken().toInt()) {
                0 -> { 0 }
                9 -> {
                    sy = y
                    sx = x
                    0
                }
                else -> {
                    fishCount++
                    cur
                }
            }
        }
    }

    if (fishCount == 0) {
        println(0)
    } else {
        println(process(map, n, sy, sx, fishCount))
    }

    close()
}

private val dy = intArrayOf(-1, 0, 1, 0)
private val dx = intArrayOf(0, -1, 0, 1)

private fun process(map: Array<IntArray>, n: Int, sy: Int, sx: Int, fishCount: Int): Int {
    var remainFish = fishCount
    var shark = BabyShark(sy, sx, 0, 2, 0)
    while (true) {
        if (remainFish == 0) break
        val result = bfs(map, n, shark.sizeUp()) ?: break
        if (shark.ate == result.ate) return shark.time

        map[result.y][result.x] = 0

        remainFish--
        shark = result
    }

    return shark.time
}

private fun bfs(map: Array<IntArray>, n: Int, babyShark: BabyShark): BabyShark? {
    val visited = Array(n) { BooleanArray(n) { false } }
    val queue: Queue<BabyShark> = LinkedList()

    queue.add(babyShark)
    visited[babyShark.y][babyShark.x] = true

    var temp: BabyShark? = null

    while (queue.isNotEmpty()) {
        val shark = queue.poll()

        val y = shark.y
        val x = shark.x
        val ate = shark.ate
        val size = shark.size
        val time = shark.time

        if (temp != null) {
            if (temp.time < time) continue
            if (temp.ate < ate) continue
        }

        for (dir in 0 until 4) {
            val ny = y + dy[dir]
            val nx = x + dx[dir]

            if (ny in 0 until n && nx in 0 until n && !visited[ny][nx]) {
                val nextTime = time + 1
                when (val next = map[ny][nx]) {
                    0 -> {
                        queue.add(BabyShark(ny, nx, ate, size, nextTime))
                        visited[ny][nx] = true
                    }
                    else -> {
                        when {
                            next < size -> {
                                if (temp != null) {
                                    if (temp.ate == ate + 1 && temp.time == nextTime) {
                                        if(temp.y < ny) continue
                                        if(temp.y == ny && temp.x < nx) continue
                                        temp = BabyShark(ny, nx, ate + 1, size, nextTime)
                                    }
                                } else {
                                    temp = BabyShark(ny, nx, ate + 1, size, nextTime)
                                }
                                visited[ny][nx] = true
                            }
                            next == size -> {
                                queue.add(BabyShark(ny, nx, ate, size, time + 1))
                                visited[ny][nx] = true
                            }
                        }
                    }
                }
            }
        }
    }

    return temp
}