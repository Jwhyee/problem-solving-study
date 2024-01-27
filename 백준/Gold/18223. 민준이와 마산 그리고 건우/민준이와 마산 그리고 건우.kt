import java.util.*
import kotlin.collections.ArrayList

private lateinit var graph: Array<MutableList<Pair<Int, Int>>>
private lateinit var distance: IntArray
private var v = 0
private var e = 0
private var p = 0

fun main() = with(System.`in`.bufferedReader()) {
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

    dijkstra(1)
    val len1 = distance[v]
    val len2 = distance[p]
    dijkstra(p)
    val len3 = distance[v]

    if(len1 == (len2 + len3)) println("SAVE HIM")
    else println("GOOD BYE")
    close()
}

private fun dijkstra(start: Int) {
    // 정점, 거리에 대한 PQ (거리가 짧은 순서로 정렬)
    val pq: Queue<Pair<Int, Int>> = PriorityQueue {a, b -> a.second - b.second}

    // 거리 배열 최댓값으로 초기화
    distance = IntArray(v + 1) { Int.MAX_VALUE }

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
            // 다음 정점까지 최소 거리가 (현재 정점까지의 최소 거리 + 다음 정점까지의 거리)보다 클 경우
            // 다음 정점 최소 거리 초기화 후 pq에 다음 정점과 그에 대한 최소 거리 추가
            if (distance[nextNode] > distance[curNode] + nextCost) {
                distance[nextNode] = distance[curNode] + nextCost
                pq += (nextNode to distance[nextNode])
            }
        }
    }

}