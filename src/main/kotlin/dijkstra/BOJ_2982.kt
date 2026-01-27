package dijkstra

import java.util.PriorityQueue
import java.util.StringTokenizer

private class Position(
    val index: Int,
    var time: Int,
) : Comparable<Position> {
    override fun compareTo(other: Position) = time - other.time
}

private class King(
    val startTime: Int,
    val endTime: Int
) {
    val diff = endTime - startTime
    val isNotPass = startTime == 0 && endTime == 0
}

fun main() = with(System.`in`.bufferedReader()) {
    // N는 교차로의 수, M은 도로의 수
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    // A는 상근이가 배달을 시작하는 교차로
    // B는 상근이가 배달을 마치는 교차로
    // K는 고둘라가 출발한 시간과 상근이가 출발한 시간의 차이
    // G는 고둘라가 방문하는 교차로의 개수
    var st = StringTokenizer(readLine())
    val a = st.nextToken().toInt()
    val b = st.nextToken().toInt()
    val k = st.nextToken().toInt()
    val g = st.nextToken().toInt()

    val roadGraph = Array(n + 1) { arrayListOf<Position>() }

    st = StringTokenizer(readLine())
    val kingInfosTemp = Array(g + 1) { if (it == 0) 0 else st.nextToken().toInt() }

    repeat(m) {
        st = StringTokenizer(readLine())

        val from = st.nextToken().toInt()
        val to = st.nextToken().toInt()
        val time = st.nextToken().toInt()

        roadGraph[from].add(Position(to, time))
        roadGraph[to].add(Position(from, time))
    }

    val kingPositions = Array(n + 1) { Array(n + 1) { King(0, 0) } }
    var prevTime = 0
    for (index in 1 until g) {
        val from = kingInfosTemp[index]
        val to = kingInfosTemp[index + 1]

        val time = roadGraph[from].find { it.index == to }!!.time

        kingPositions[from][to] = King(prevTime, prevTime + time)
        kingPositions[to][from] = King(prevTime, prevTime + time)

        prevTime += time
    }

    println(dijkstra(roadGraph, kingPositions, n, k, a, b))

    close()
}

private fun dijkstra(
    roadGraph: Array<ArrayList<Position>>,
    kingPositions: Array<Array<King>>,
    n: Int, k: Int,
    sStart: Int, sEnd: Int
): Int {
    val pq = PriorityQueue<Position>()
    val dist = IntArray(n + 1) { Int.MAX_VALUE }

    dist[sStart] = k
    pq.add(Position(sStart, k))

    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        val curIndex = cur.index
        val curTime = cur.time

        if(dist[curIndex] < curTime) continue
        if(curIndex == sEnd) return curTime - k

        val nextPositions = roadGraph[curIndex]
        for (next in nextPositions) {
            val nextIndex = next.index
            val nextTime = next.time

            val king = kingPositions[curIndex][nextIndex]

            val resultTime = if (!king.isNotPass && curTime in king.startTime..king.endTime) {
                king.endTime + nextTime
            } else curTime + nextTime

            if (dist[nextIndex] > resultTime) {
                dist[nextIndex] = resultTime
                pq.add(Position(nextIndex, resultTime))
            }
        }
    }

    return -1
}