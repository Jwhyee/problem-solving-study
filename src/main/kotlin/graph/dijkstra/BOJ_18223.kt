package graph.dijkstra

import java.util.*
import kotlin.collections.ArrayList

private lateinit var graph: Array<MutableList<Pair<Int, Int>>>
private lateinit var distance: IntArray
private var v = 0
private var e = 0
private var p = 0
/**
 * 문제 이름(난이도) : 민준이와 마산 그리고 건우(GOL4)
 * 시간 : 420 ms
 * 메모리 : 25376 KB
 * 링크 : https://www.acmicpc.net/problem/18223
 */
fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    var st = StringTokenizer(readLine())
    // 정점의 개수
    v = st.nextToken().toInt()
    // 간선의 개수
    e = st.nextToken().toInt()
    // 건우의 위치
    p = st.nextToken().toInt()

    // 그래프
    graph = Array(v + 1) { ArrayList() }

    for (i in 1..e) {
        st = StringTokenizer(readLine())
        val start = st.nextToken().toInt()
        val end = st.nextToken().toInt()
        val cost = st.nextToken().toInt()

        graph[start] += (end to cost)
        graph[end] += (start to cost)
    }

    // 출발지로부터 최소 거리 구하기
    dijkstra(1)
    // 마산까지의 거리
    val len1 = distance[v]

    // 건우까지의 거리
    val len2 = distance[p]

    // 건우의 위치로부터 최소 거리 구하기
    dijkstra(p)
    // 마산까지의 거리
    val len3 = distance[v]

    // 마산까지의 최소 거리가 건우를 픽업하고, 마산까지 가는 거리의 합과 같을 경우
    if(len1 == (len2 + len3)) bw.append("SAVE HIM")
    else bw.append("GOOD BYE")
    bw.flush()
    bw.close()
    close()
}

private fun dijkstra(start: Int) {
    // 정점, 거리에 대한 PQ (거리가 짧은 순서로 정렬)
    val pq: Queue<Pair<Int, Int>> = PriorityQueue { a, b -> a.second - b.second }

    // 거리 배열 최댓값으로 초기화
    distance = IntArray(v + 1) { 10_001 }

    // 시작 위치 추가
    pq += (start to 0)
    distance[start] = 0

    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val curNode = cur.first

        // 현재 정점과 이어진 정점 탐색
        for (next in graph[curNode]) {
            val nextNode = next.first
            val nextCost = next.second
            // 다음 정점까지의 누적 거리
            val d = distance[curNode] + nextCost

            // 다음 정점까지 최소 거리가 (현재 정점까지의 최소 거리 + 다음 정점까지의 거리)보다 클 경우
            // 다음 정점 최소 거리 초기화 후 pq에 다음 정점과 그에 대한 최소 거리 추가
            if (distance[nextNode] > d) {
                distance[nextNode] = d
                pq += (nextNode to distance[nextNode])
            }
        }
    }

}