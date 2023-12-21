package ps.bfs

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 경쟁적 전염(GOL5)
 * 시간 : 476 ms
 * 메모리 : 33004 KB
 * 링크 : https://www.acmicpc.net/problem/18405
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine())
    val N = st.nextToken().toInt()
    val K = st.nextToken().toInt()

    // 지도 배열 생성
    val map = Array(N) { IntArray(N) }

    // 번호가 낮은 종류의 바이러스부터 증식하기 때문에 PQ 사용
    // (y좌표, x좌표, 번호)
    val pq = PriorityQueue<Triple<Int, Int, Int>> { a, b -> a.third - b.third }

    // 지도 배열 초기화
    for (i in 0 until N) {
        st = StringTokenizer(readLine())
        for (j in 0 until N) {
            map[i][j] = st.nextToken().toInt()
            val num = map[i][j]
            // 빈 칸이 아닐 경우 PQ에 추가
            if (num != 0) {
                pq.add( Triple(i, j, num) )
            }
        }
    }


    st = StringTokenizer(readLine())
    var s = st.nextToken().toInt()
    val x = st.nextToken().toInt() - 1
    val y = st.nextToken().toInt() - 1

    val dy = arrayOf(0, 1, 0, -1)
    val dx = arrayOf(1, 0, -1, 0)

    while (s-- > 0) {
        // 임시 리스트 생성
        val temp = LinkedList<Triple<Int, Int, Int>>()

        while (!pq.isEmpty()) {
            val cur = pq.poll()

            // first : y좌표, second : x좌표
            for (i in 0..3) {
                val nx = cur.second + dx[i]
                val ny = cur.first + dy[i]

                // 다음 좌표가 범위 내에 있는지 확인
                if (nx in 0 until N && ny in 0 until N) {
                    // 다음 칸이 0일 경우 현재 바이러스의 번호 대입 후 temp에 추가
                    if (map[ny][nx] == 0) {
                        map[ny][nx] = cur.third
                        temp.add(Triple(ny, nx, cur.third))
                    }
                }
            }
        }
        // temp에 추가한 값을 pq에 담기
        pq.addAll(temp)
    }

    println(map[x][y])

}

fun pMap(map: Array<IntArray>) {
    repeat(map.size) { i ->
        repeat(map.size) { j ->
            print("${map[i][j]} ")
        }
        println()
    }
    println()
}