package ps.dijkstra

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.min

/**
 * 문제 이름(난이도) : 지름길(SIL1)
 * 시간 : 136 ms
 * 메모리 : 21244 KB
 * 링크 : https://www.acmicpc.net/problem/1446
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, d) = readLine().split(" ").map { it.toInt() }
    val scList = ArrayList<Triple<Int, Int, Int>>()

    repeat(n) {
        val st = StringTokenizer(readLine())
        val start = st.nextToken().toInt()
        val end = st.nextToken().toInt()
        val cost = st.nextToken().toInt()

        // 목표 위치보다 도착 지점이 작고, 지금길 비용이 원래 소요되는 거리보다 적을 경우
        if(d >= end && end - start > cost)
            scList += Triple(start, end, cost)
    }

    // 첫 값 오름차순 정렬 / 첫 값이 같을 경우 두 번째 값 내림차순 정렬 / 처음과 두번째 모두 같을 경우 세 번째 값 오름차순 정렬
    scList.sortWith(compareBy<Triple<Int, Int, Int>> {it.first}
        .thenByDescending { it.second }
        .thenBy { it.third }
    )

    // 이동한 거리
    var md = 0
    var idx = 0
    val dArr = IntArray(10001) { Int.MAX_VALUE }
    dArr[0] = 0

    // 이동한 거리가 고속도로의 길이보다 짧으면 반복
    while (md < d) {
        // 인덱스가 지름길 리스트의 사이즈보다 작을 경우
        if (idx < scList.size) {
            // 해당 지름길 리스트를 꺼내옴
            val scl = scList[idx]
            // 이동한 거리와 시작 위치가 같을 경우
            if (md == scl.first) {
                // 도착위치에 최소값 저장 (지금까지 이동한 누적 거리 + 지름길의 길이)
                dArr[scl.second] = min(dArr[md] + scl.third, dArr[scl.second])
                idx++
            } else {
                // 이동한 거리의 다음 위치에 기존 거리와 1을 더한 값 중 더 작은 값을 저장
                dArr[md + 1] = min(dArr[md + 1], dArr[md] + 1)
                md++
            }
        } else {
            // 이동한 거리의 다음 위치에 기존 거리와 1을 더한 값 중 더 작은 값을 저장
            dArr[md + 1] = min(dArr[md + 1], dArr[md] + 1)
            md++
        }
    }

    println(dArr[md])

}