package impl

import java.util.*


/*
5 3
...
.X.
.X.
.X.
...
* */

fun main() = with(System.`in`.bufferedReader()) {
    val (r, c) = StringTokenizer(readLine()).run { nextToken().toInt() to nextToken().toInt() }
    val map = Array(r) { Array(c) { Land(0, 0, '.', false) } }
    val queue: Queue<Land> = LinkedList()
    repeat(r) { y ->
        val chArr = readLine().toCharArray()
        repeat(c) { x ->
            map[y][x].also {
                it.y = y
                it.x = x
                it.state = chArr[x]
            }
            if(chArr[x] == 'X') queue += map[y][x]
        }
    }
    bfs(queue, map)
    val position = findStartEndPoint(map)
    val yP = position.first
    val xP = position.second

    for (y in yP.first..yP.second) {
        for (x in xP.first..xP.second) {
            print(map[y][x].state)
        }
        println()
    }
}

private val dx = intArrayOf(0, 1, 0, -1)
private val dy = intArrayOf(-1, 0, 1, 0)

private data class Land(
    var y: Int,
    var x: Int,
    var state: Char,
    var isChanged: Boolean
)

private fun bfs(queue: Queue<Land>, map: Array<Array<Land>>) {
    while(queue.isNotEmpty()) {
        val cur = queue.poll()
        var cnt = 0
        repeat(4) { idx ->
            val nx = cur.x + dx[idx]
            val ny = cur.y + dy[idx]
            if(ny in map.indices && nx in map[0].indices) {
                val next = map[ny][nx]
                if(next.state == '.' && !next.isChanged) {
                    cnt++
                }
            } else {
                cnt++
            }
        }
        if(cnt >= 3) {
            cur.isChanged = true
            cur.state = '.'
        }
    }
}

private fun findStartEndPoint(map: Array<Array<Land>>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    var startX = 0
    var startY = 0
    var endX = map[0].size - 1
    var endY = map.size - 1

    for (y in map.indices) {
        if(map[y].all { it.state == '.' }) startY++
        else break
    }
    for (y in (map.size - 1) downTo 0) {
        if(map[y].all { it.state == '.' }) endY--
        else break
    }
    for (x in map[0].indices) {
        if(map.all { it[x].state == '.' }) startX++
        else break
    }
    for (x in (map[0].size - 1) downTo 0) {
        if(map.all { it[x].state == '.' }) endX--
        else break
    }
    return (startY to endY) to (startX to endX)
}