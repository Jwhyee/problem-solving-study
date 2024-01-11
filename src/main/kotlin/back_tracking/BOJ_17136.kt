package back_tracking

import java.util.StringTokenizer

lateinit var map: Array<Array<Int>>
val pagerCnt = arrayOf(0, 5, 5, 5, 5, 5)
var result = Int.MAX_VALUE

/**
 * 문제 이름(난이도) : 색종이 붙이기(GOL2)
 * 시간 : 268 ms
 * 메모리 : 21896 KB
 * 링크 : https://www.acmicpc.net/problem/17136
 */
fun main() = with(System.`in`.bufferedReader()) {
    map = Array(10) {
        val st = StringTokenizer(readLine())
        Array(10) { st.nextToken().toInt() }
    }

    backTracking(0, 0, 0)

    println(if (result == Int.MAX_VALUE) -1 else result)
}


private fun backTracking(x: Int, y: Int, depth: Int) {
    // 모든 칸 탐색 완료
    if (x >= 10) {
        result = minOf(result, depth)
        return
    }

    // 현재 depth(사용한 종이 수)가 result 보다 크거나 같다면 탐색할 필요 없음
    if (depth >= result) return

    // 현재 칸이 1일 경우
    if (map[x][y] == 1) {
        paperAttach@ for (i in 5 downTo 1) {
            if (pagerCnt[i] == 0) continue

            // 색종이 붙일 수 있는지 확인
            for (dx in 0 until i) {
                for (dy in 0 until i) {
                    // for문 라벨링을 사용
                    if (x + dx >= 10 || y + dy >= 10 || map[x + dx][y + dy] == 0) continue@paperAttach
                }
            }


            attach(x, y, i, 0)
            pagerCnt[i]--
            if (y + i < 10) {
                // 옆칸으로 이동
                backTracking(x, y + i, depth + 1)
            } else {
                // 마지막 칸이라면 아랫 칸으로 이동
                backTracking(x + 1, 0, depth + 1)
            }
            pagerCnt[i]++
            attach(x, y, i, 1)
        }
    } else {
        // 현재 칸이 0이라면 옆 칸으로 넘어감
        if (y + 1 < 10) {
            backTracking(x, y + 1, depth)
        } else {
            backTracking(x + 1, 0, depth)
        }
    }
}

// 색종이를 붙이는 함수
fun attach(x: Int, y: Int, size: Int, flag: Int) {
    repeat(size) { dx ->
        repeat(size) { dy ->
            map[x + dx][y + dy] = flag
        }
    }
}