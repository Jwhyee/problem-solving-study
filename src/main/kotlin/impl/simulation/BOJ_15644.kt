package impl.simulation

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer
import kotlin.math.abs

private val dirs = arrayOf("R", "L", "D", "U")
private val dx = arrayOf(1, -1, 0, 0)
private val dy = arrayOf(0, 0, 1, -1)
private var n = 0
private var m = 0

private lateinit var map: Array<CharArray>
// 빨간색 y, x
private lateinit var red: Pair<Int, Int>
// 파란색 y, x
private lateinit var blue: Pair<Int, Int>

/**
 * 문제 이름(난이도) : 구슬 탈출3(GOL1)
 * 시간 : 104 ms
 * 메모리 : 13036 KB
 * 링크 : https://www.acmicpc.net/problem/15644
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val st = StringTokenizer(readLine())
    n = st.nextToken().toInt()
    m = st.nextToken().toInt()
    map = Array(n) { CharArray(m) }

    repeat(n) { i ->
        val split = readLine().toCharArray()
        repeat(m) { j ->
            val cur = split[j]
            map[i][j] = cur
            when (cur) {
                'R' -> red = i to j
                'B' -> blue = i to j
            }
        }
    }
    go()
}

data class Location(val ry: Int, val rx: Int, val by: Int, val bx: Int, val cnt: Int, val route: String)

fun go() {
    // 위치를 저장할 큐
    val queue: Queue<Location> = LinkedList()
    // 방문 배열
    val visited = Array(n) { Array(m) { Array(n) { BooleanArray(m) } } }
    // 빨간공의 첫 위치
    val (ry, rx) = red.first to red.second
    // 파란공의 첫 위치
    val (by, bx) = blue.first to blue.second
    // 위치 정보 큐에 추가
    queue.add(Location(ry, rx, by, bx, 0, ""))
    visited[ry][rx][by][bx] = true

    while (queue.isNotEmpty()) {
        val cur = queue.poll()
        val (cry, crx) = cur.ry to cur.rx
        val (cby, cbx) = cur.by to cur.bx
        val curCnt = cur.cnt
        val curRoute = cur.route

        visited[cry][crx][cby][cbx] = true

        if (curCnt > 10) {
            println(-1)
            return
        }

        if (map[cry][crx] == 'O') {
            println(curCnt)
            println(curRoute)
            return
        }

        for (i in 0 until 4) {
            var (nry, nrx) = cry to crx
            var (nby, nbx) = cby to cbx

            while (true) {
                nry += dy[i]
                nrx += dx[i]
                if (map[nry][nrx] == '#') {
                    nry -= dy[i]
                    nrx -= dx[i]
                    break
                }
                if (isGoal(nry, nrx)) break
            }
            while (true) {
                nby += dy[i]
                nbx += dx[i]
                if (map[nby][nbx] == '#') {
                    nby -= dy[i]
                    nbx -= dx[i]
                    break
                }
                if (isGoal(nby, nbx)) break
            }

            // 파란공만 들어갔다면 통과
            if (isGoal(nby, nbx)) continue

            // 빨간공과 파란공의 위치가 같을 경우
            if (nry == nby && nrx == nbx) {
                val rb = abs(nry - cry) + abs(nrx - crx)
                val bb = abs(nby - cby) + abs(nbx - cbx)
                if (rb > bb) {
                    nry -= dy[i]
                    nrx -= dx[i]
                } else {
                    nby -= dy[i]
                    nbx -= dx[i]
                }
            }

            // 다음 좌표를 방문하지 않았을 경우
            if (visited[nry][nrx][nby][nbx].not()) {
                queue += (Location(nry, nrx, nby, nbx, curCnt + 1, curRoute + dirs[i]))
            }
        }
    }
    println(-1)
}

fun isGoal(y: Int, x: Int): Boolean = map[y][x] == 'O'