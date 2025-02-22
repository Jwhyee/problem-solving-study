package simulation

import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    val (r, c) = readLine().split(" ").map { it.toInt() }

    val map = Array(r) { readLine().toCharArray() }
    val vision = Array(r) { CharArray(c) { '#' } } // 초기값: 다 안 보임

    val (hrInit, hcInit) = readLine().split(" ").map { it.toInt() - 1 }
    var hr = hrInit
    var hc = hcInit

    val act = readLine().toCharArray()

    for (action in act) {
        when (action) {
            'W' -> if (vision[hr][hc] == '#') ward(hr, hc, map, vision)
            'U' -> if (hr > 0) hr--
            'D' -> if (hr < r - 1) hr++
            'L' -> if (hc > 0) hc--
            'R' -> if (hc < c - 1) hc++
        }
    }

    vision[hr][hc] = '.'
    val directions = arrayOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

    for ((dr, dc) in directions) {
        val rr = hr + dr
        val cc = hc + dc

        if (rr in 0 until r && cc in 0 until c) {
            vision[rr][cc] = '.'
        }
    }

    println(vision.joinToString("\n") { it.concatToString() })
}

fun ward(nr: Int, nc: Int, map: Array<CharArray>, vision: Array<CharArray>) {
    val area = map[nr][nc]
    val directions = arrayOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
    val queue: Queue<Pair<Int, Int>> = ArrayDeque()

    vision[nr][nc] = '.'
    queue.add(nr to nc)

    while (queue.isNotEmpty()) {
        val (curR, curC) = queue.poll()

        for ((dr, dc) in directions) {
            val rr = curR + dr
            val cc = curC + dc

            if (rr !in map.indices || cc !in map[rr].indices) continue
            if (vision[rr][cc] == '.' || map[rr][cc] != area) continue

            vision[rr][cc] = '.'
            queue.add(rr to cc)
        }
    }
}