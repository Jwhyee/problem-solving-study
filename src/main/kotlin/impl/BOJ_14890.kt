package impl

import java.util.*

private val dy = intArrayOf(1, 0)
private val dx = intArrayOf(0, 1)

fun main() = with(System.`in`.bufferedReader()) {
    val (n, l) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val check = Array(n) { IntArray(n) { -1 } }
    val roads = Array(n) {
        val st = StringTokenizer(readLine())
        IntArray(n) { st.nextToken().toInt() }
    }

    var count = 0

    // 0번 행의 각 열에서 밑으로 진행
    for (col in 0 until n) {
        val result = check(check, roads, 0, col, 0, n, l)
        if (result) {
            count++
        } else {
            for (row in 0 until n) {
                check[row][col] = -1
            }
        }
    }
    // 0번 열의 각 행에서 우측으로 진행
    for (row in 0 until n) {
        val result = check(check, roads, row, 0, 1, n, l)
        if (result) {
            count++
        } else {
            for (col in 0 until n) {
                if(check[row][col] != -1) {
                    continue
                } else {
                    check[row][col] = -1
                }
            }
        }
    }

    println(count)

    close()
}

private class Road(
    val row: Int, val col: Int, val useRamp: Boolean, val rampSize: Int,
)

private fun check(
    check: Array<IntArray>,
    roads: Array<IntArray>,
    row: Int, col: Int, dir: Int,
    n: Int, l: Int
): Boolean {
    val queue: Queue<Road> = LinkedList()

    queue.add(Road(row, col, false, 0))

    while(queue.isNotEmpty()) {
        val road = queue.poll()

        val row = road.row
        val col = road.col
        val useRamp = road.useRamp
        val rampSize = road.rampSize

        val height = roads[row][col]

        if (useRamp) {
            if (check[row][col] == dir) return false
            check[row][col] = dir
        }

        val (ny, nx) = row + dy[dir] to col + dx[dir]

        if (ny in 0 until n && nx in 0 until n) {
            val nextHeight = roads[ny][nx]

            val diff = height - nextHeight

            when(diff) {
                -1 -> {
                    val (targetY, targetX) = when(dir) {
                        0 -> { ny - l to nx }
                        1 -> { ny to nx - l }
                        else -> return false
                    }
                    if (targetY in 0 until n && targetX in 0 until n) {
                        when(dir) {
                            0 -> {
                                for (y in targetY until ny) {
                                    if(check[y][targetX] == 0) return false
                                    else check[y][targetX] = 0
                                }
                            }
                            1 -> {
                                for (x in targetX until nx) {
                                    if(check[targetY][x] == 1) return false
                                    else check[targetY][x] = 1
                                }
                            }
                            else -> return false
                        }
                        queue.add(Road(ny, nx, false, 0))
                    } else {
                        return false
                    }
                }
                0 -> {
                    if(useRamp) {
                        when {
                            rampSize == l -> queue.add(Road(ny, nx, false, 0))
                            rampSize < l -> {
                                queue.add(Road(ny, nx, true, rampSize + 1))
                            }
                        }
                    } else {
                        queue.add(Road(ny, nx, false, 0))
                    }
                }
                1 -> {
                    if(useRamp) {
                        when {
                            rampSize == l -> queue.add(Road(ny, nx, true, 1))
                            rampSize < l -> return false
                        }
                    } else {
                        queue.add(Road(ny, nx, true, rampSize + 1))
                    }
                }
                else -> return false
            }
        } else {
            if(useRamp && rampSize < l) return false
        }
    }

    return true
}