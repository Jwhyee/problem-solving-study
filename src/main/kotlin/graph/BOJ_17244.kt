package graph

import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 서울 지하철 2호선(GOL3)
 * 시간 : ? ms
 * 메모리 : ? KB
 * 링크 : https://www.acmicpc.net/problem/16947
 */

private class Station(
    val n: Int,
    var isCycle: Boolean,
    var stationList: MutableList<Station> = mutableListOf()
)

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()
    val stationArr = Array<Station>(n + 1) { Station(it, false) }
    var visited = Array(n + 1) { false }
    repeat(n) {
        StringTokenizer(readLine()).run {
            val a = nextToken().toInt()
            val b = nextToken().toInt()
            stationArr[a].stationList += stationArr[b]
            stationArr[b].stationList += stationArr[a]
        }
    }
    // 자신과 이어진 역이 3개 이상일 경우 해당 역은 순환선의 분기 점임
}
