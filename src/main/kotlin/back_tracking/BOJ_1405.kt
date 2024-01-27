package back_tracking

import java.util.StringTokenizer

private var n = 0
private val visited = Array(30) { BooleanArray(30) }
private val percent = DoubleArray(4)
private val dx = intArrayOf(1, -1, 0, 0)
private val dy = intArrayOf(0, 0, -1, 1)
private var result = 0.0

/**
 * 문제 이름(난이도) : 미친 로봇(GOL4)
 * 시간 : 140 ms
 * 메모리 : 12992 KB
 * 링크 : https://www.acmicpc.net/problem/1405
 */
fun main() = with(System.`in`.bufferedReader()){
    val st = StringTokenizer(readLine())
    n = st.nextToken().toInt()

    // 동, 서, 남, 북에 확률 기입
    for (idx in 0 until 4) {
        percent[idx] = st.nextToken().toInt() * 0.01
    }

    // 백트래킹으로 확률 구하기
    backtracking(0, 15, 15, 1.0)
    println(result)
    close()
}

private fun backtracking(t: Int, y: Int, x: Int, percentage: Double) {
    // 이동한 횟수가 조건으로 주어진 수와 같을 경우 확률 계산
    if (t == n) {
        result += percentage
        return
    }
    // 현재 좌표 방문 처리
    visited[y][x] = true
    for (i in 0 until 4) {
        // 다음 좌표 구하기
        val ny = y + dy[i]
        val nx = x + dx[i]
        // 다음 좌표가 범위 내에 있고, 방문하지 않았을 경우
        if (ny in 0 until 30 && nx in 0 until 30) {
            if (!visited[ny][nx]) {
                // 다음 좌표 방문 처리
                visited[ny][nx] = true
                // 현재까지의 퍼센트에 다음 좌표 방향에 대한 확률 곱해주기
                backtracking(t + 1, ny, nx, percentage * percent[i])
                // 다음 좌표 방문 처리 해제
                visited[ny][nx] = false
            }
        }
    }
}