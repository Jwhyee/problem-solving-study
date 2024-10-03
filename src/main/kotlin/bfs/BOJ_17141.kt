package bfs

import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 연구소2 (GOL4)
 * 시간 : 336 ms
 * 메모리 : 44620 KB
 * 링크 : https://www.acmicpc.net/problem/17141
 */
private lateinit var map: Array<IntArray>
private val virusList = mutableListOf<Pair<Int, Int>>()
private var answer = Int.MAX_VALUE
private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, 0, -1)

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val bw = System.out.bufferedWriter()

    // n, m 입력 받기
    val (n, m) = st.nextToken().toInt() to st.nextToken().toInt()

    // 지도 배열 초기화
    map = Array(n) { IntArray(n) }
    for (y in 0 until n) {
        st = StringTokenizer(readLine())

        for (x in 0 until n) {
            val cur = st.nextToken().toInt()
            map[y][x] = cur
            if (cur == 2) virusList += (y to x)
        }
    }

    // 조합 시작
    combination(IntArray(m), 0, 0, n, m)

    bw.write("${if (answer == Int.MAX_VALUE) -1 else answer}")
    bw.close()
    close()
}

fun combination(viruses: IntArray, idx: Int, cnt: Int, n: Int, m: Int) {

    if (cnt == m) {
        answer = answer.coerceAtMost(bfs(viruses, n))
        return
    }

    for (i in idx until virusList.size) {
        viruses[cnt] = i
        combination(viruses, i + 1, cnt + 1, n, m)
    }
}

private fun bfs(viruses: IntArray, n: Int): Int {
    val queue: Queue<Pair<Int, Int>> = LinkedList()
    // 총 걸린 시간
    var time = 0

    // 맵 복사본 생성
    val mapCopy = map.map { it.clone() }.toTypedArray()

    // 바이러스 리스트를 돌면서 큐에 추가 후 해당 자리는 벽으로 표시
    for (i in viruses) {
        val (y, x) = virusList[i]
        queue.add(virusList[i])
        mapCopy[y][x] = 1
    }

    // 만약 종료 조건에 맞다면 시간 반환
    if (isFinish(mapCopy, n)) return time

    // BFS 진행
    while (queue.isNotEmpty()) {
        val size = queue.size
        time++
        for (i in 0 until size) {
            val (cy, cx) = queue.poll()

            for (j in 0 until 4) {
                val ny = cy + dy[j]
                val nx = cx + dx[j]

                if (ny !in 0 until n || nx !in 0 until n) continue
                if (mapCopy[ny][nx] == 1) continue
                mapCopy[ny][nx] = 1
                queue.add(Pair(ny, nx))
            }
        }
        if (isFinish(mapCopy, n)) return time
    }
    return Int.MAX_VALUE
}

fun isFinish(tmp: Array<IntArray>, n: Int): Boolean {
    var wallCnt = 0
    for (r in 0 until n) {
        for (c in 0 until n) {
            if (tmp[r][c] == 1) wallCnt++
        }
    }
    return wallCnt == n * n
}