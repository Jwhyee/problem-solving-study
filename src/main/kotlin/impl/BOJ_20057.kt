package impl

import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

private data class Position2(val y: Int, val x: Int, val dir: Int)

private val dsx = intArrayOf(1, 1, 0, 0, -2, 0, 0, -1, -1, -1)
private val dsy = intArrayOf(-1, 1, -2, 2, 0, -1, 1, -1, 1, 0)
private val ratios = intArrayOf(1, 1, 2, 2, 5, 7, 7, 10, 10, 0)

// 좌, 하, 우, 상 (반시계 방향)
private val dy = intArrayOf(0, 1, 0, -1)
private val dx = intArrayOf(-1, 0, 1, 0)

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()

    val map = Array(n) {
        val st = StringTokenizer(readLine())
        IntArray(n) { st.nextToken().toInt() }
    }

    println(move(map, n))
}

private fun move(map: Array<IntArray>, n: Int): Int {
    val mid = (n - 1) / 2

    var y = mid
    var x = mid
    var totalOutSand = 0

    var moveLength = 1
    var dir = 0

    while (true) {
        repeat(2) {
            repeat(moveLength) {
                y += dy[dir]
                x += dx[dir]

                if (y !in 0 until n || x !in 0 until n) {
                    return totalOutSand
                }

                totalOutSand += spread(map, n, y, x, dir)
            }
            dir = (dir + 1) % 4
        }
        moveLength++
    }
}

private fun spread(
    map: Array<IntArray>, n: Int,
    y: Int, x: Int, dir: Int
): Int {
    val sand = map[y][x]
    map[y][x] = 0

    var outSand = 0
    var spreadSum = 0

    for (i in 0 until 10) {
        var nr = 0
        var nc = 0

        when (dir) {
            0 -> { // 좌
                nr = y + dsy[i]
                nc = x + dsx[i]
            }
            1 -> { // 하
                nr = y - dsx[i]
                nc = x + dsy[i]
            }
            2 -> { // 우
                nr = y - dsy[i]
                nc = x - dsx[i]
            }
            3 -> { // 상
                nr = y + dsx[i]
                nc = x - dsy[i]
            }
        }

        val amount = if (i == 9) {
            sand - spreadSum
        } else {
            (sand * ratios[i]) / 100
        }

        // 격자 밖으로 나가는지 확인
        if (nr in 0 until n && nc in 0 until n) {
            map[nr][nc] += amount
        } else {
            outSand += amount
        }

        if (i < 9) spreadSum += amount
    }

    return outSand
}