package dfs

import java.util.PriorityQueue
import java.util.StringTokenizer

private lateinit var map: Array<IntArray>
private lateinit var visited: Array<BooleanArray>
private data class Pos(val y: Int, val x: Int, val h: Int)

/**
 * 문제 이름(난이도) : 농장 관리(GOL5)
 * 시간 : 288 ms
 * 메모리 : 20164 KB
 * 링크 : https://www.acmicpc.net/problem/1245
 */
fun main() {
    val br = System.`in`.bufferedReader()
    val bw = System.out.bufferedWriter()
    var st = StringTokenizer(br.readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    // 지도 및 방문 배열 생성
    map = Array(n) { IntArray(m) }
    visited = Array(n) { BooleanArray(m) }
    val pq = PriorityQueue<Pos> {a, b -> b.h - a.h}

    // 배열 초기화 및 산봉우리의 높이가 0이 아닐 경우 좌표 추가
    for (y in 0 until n) {
        st = StringTokenizer(br.readLine())
        for (x in 0 until m) {
            val h = st.nextToken().toInt()
            map[y][x] = h
            if (h != 0) pq += Pos(y, x, h)
        }
    }


    var cnt = 0
    // pq를 순회하면서 방문하지 않은 곳 dfs
    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val y = cur.y
        val x = cur.x
        if (!visited[y][x]) {
            dfs(y, x, n, m)
            cnt++
        }
    }

    bw.append("$cnt")
    bw.flush()
    bw.close()
    br.close()
}

private val dx = intArrayOf(1, 0, -1, 0, -1, -1, 1, 1)
private val dy = intArrayOf(0, 1, 0, -1, -1, 1, -1, 1)

private fun dfs(y: Int, x: Int, n: Int, m: Int) {
    // 현재 좌표 방문 처리
    visited[y][x] = true

    // 대각선까지 포함해서 탐색
    for (i in 0..7) {
        val ny = y + dy[i]
        val nx = x + dx[i]
        // 다음 좌표가 지도 범위 내에 있고 방문하지 않았으며, 0이 아닐 경우
        if (ny in 0 until n && nx in 0 until m) {
            if (visited[ny][nx].not() && map[ny][nx] != 0) {
                val diff = map[y][x] - map[ny][nx]
                // 현재 좌표의 값과 다음 좌표의 값의 차가 양수일 경우에만 dfs 진행
                if (diff >= 0) {
                    dfs(ny, nx, n, m)
                }
            }
        }
    }
}