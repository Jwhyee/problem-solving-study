package ps.bfs

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 뱀과 사다리 게임(GOL5)
 * 시간 : 88 ms
 * 메모리 : 12560 KB
 * 링크 : https://www.acmicpc.net/problem/16928
 */
val cnt = Array(101) { 0 }
val ladder = Array(101) { 0 }
private val visited = Array(101) { false }
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {

    var st = StringTokenizer(readLine())
    // 사다리의 수
    val n = st.nextToken().toInt()
    // 뱀의 수
    val m = st.nextToken().toInt()

    // 그래프 생성
    for (i in 0 until n + m) {
        st = StringTokenizer(readLine())
        ladder[st.nextToken().toInt()] = st.nextToken().toInt()
    }

    bfs()
}

fun bfs() {
    val queue: Queue<Int> = LinkedList()

    // 1번 칸부터 진행
    queue += 1
    cnt[1] = 0
    visited[1] = true

    // 큐가 빌 때까지 진행
    while (!queue.isEmpty()) {
        val cur = queue.poll()

        // 현재 칸이 100이라면 횟수를 출력 후 종료
        if (cur == 100) {
            println(cnt[cur])
            return
        }

        // 주사위 1 ~ 6까지 돌림
        for (i in 1 .. 6) {
            // 다음 좌표 next point
            val np = cur + i

            // 다음 좌표가 100 이상이거나 방문했다면 넘어가기
            if (100 < np || visited[np]) continue

            // 다음 좌표 방문 처리
            visited[np] = true

            // 사다리를 타고 갔을 때 값이 0이 아닐 경우
            if (ladder[np] != 0) {
                // 이동한 칸이 방문한 곳이 아닐 경우
                if (!visited[ladder[np]]) {
                    // 다음에 탐색할 좌표로 등록 후 방문 처리
                    queue += ladder[np]
                    visited[ladder[np]] = true

                    // 다음 위치에 현재까지 던진 횟수 + 1
                    cnt[ladder[np]] = cnt[cur] + 1
                }
            }
            // 0일 경우
            else {
                // 다음 좌표를 큐에 저장
                queue += np
                // 다음 위치에 현재까지 던진 횟수 + 1
                cnt[np] = cnt[cur] + 1
            }
        }
    }
}