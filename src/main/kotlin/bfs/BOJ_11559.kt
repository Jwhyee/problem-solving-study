package bfs

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Queue
import java.util.LinkedList

private const val n = 12
private const val m = 6
private val map = Array(n) { CharArray(m) }
private val queue: Queue<Pair<Int, Int>> = LinkedList()
private var visited = Array(n) { BooleanArray(m) }

/**
 * 문제 이름(난이도) : Puyo Puyo(GOL4)
 * 시간 : 96 ms
 * 메모리 : 12660 KB
 * 링크 : https://www.acmicpc.net/problem/11559
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {

    repeat(n) { y ->
        map[y] = readLine().toCharArray()
    }

    var chainingCount = 0
    while (true) {
        visited = Array(n) { BooleanArray(m) }

        // 맨 밑에서부터 터트릴 수 있는 애들 찾기
        var isPop = false
        for (y in (n - 1) downTo 0) {
            for (x in 0 until m) {
                val cur = map[y][x]
                if (cur != '.') {
                    queue.clear()
                    val bubbleCnt = dfs(y, x, 1, cur)
                    // 같은 색 뿌요가 4개 이상 상하좌우로 연결되어 있으면 연결된 같은 색 뿌요들이 한꺼번에 없어진다.
                    if (bubbleCnt >= 4) {
                        isPop = true
                        doPop()
                    }
                }
            }
        }
        if (isPop) {
            // 터짐을 반복할 때마다 1연쇄씩 늘어난다.
            chainingCount++
            // 뿌요들이 없어지고 나서 위에 다른 뿌요들이 있다면, 역시 중력의 영향을 받아 차례대로 아래로 떨어지게 된다.
            for (x in 0 until m) {
                downBubble(x)
            }
        } else {
            break
        }
    }
    println(chainingCount)
}

val dx = intArrayOf(1, 0, -1, 0)
val dy = intArrayOf(0, 1, 0, -1)

fun downBubble(x: Int) {
    for (y in (n - 2) downTo 0) {
        val cur = map[y][x]
        if (cur != '.') {
            downDfs(y, x, cur)
        }
    }
}

fun downDfs(y: Int, x: Int, c: Char) {
    val ny = y + 1
    if (ny in 0 until n && map[ny][x] == '.') {
        map[y][x] = '.'
        map[ny][x] = c
        downDfs(ny, x, c)
    }
}

fun doPop() {
    while (queue.isNotEmpty()) {
        val cur = queue.poll()
        map[cur.first][cur.second] = '.'
    }
}

fun dfs(y: Int, x: Int, cnt: Int, color: Char): Int {
    var count = cnt
    visited[y][x] = true
    queue += (y to x)

    for (i in 0..3) {
        val (ny, nx) = y + dy[i] to x + dx[i]
        if (ny in 0 until n && nx in 0 until m) {
            if (visited[ny][nx].not() && map[ny][nx] == color) {
                count = dfs(ny, nx, count + 1, color)
            }
        }
    }
    return count
}