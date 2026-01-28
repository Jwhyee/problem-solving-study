package impl

import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

private data class Truck(val time: Int, val weight: Int)

fun main() = with(System.`in`.bufferedReader()) {
    // n : 트럭의 수, w: 다리의 길이, l: 다리의 최대하중(다리 위 트럭 무게 합 <= l 허용)
    val (n, w, l) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val st = StringTokenizer(readLine())
    val trucks = IntArray(n) { st.nextToken().toInt() }

    val bridge: Queue<Int> = LinkedList()

    repeat(w) {
        bridge.add(0)
    }

    // 트럭의 순서는 바꿀 수 없다. 트럭의 무게는 서로 다르거나 같다.
    // 모든 트럭이 다리를 건너는 최단 시간 구하기
    var index = 0
    var time = 0
    var totalWeight = 0

    while (index < n) {
        time++

        totalWeight -= bridge.poll()

        val next = trucks[index]

        if (totalWeight + next <= l) {
            bridge.add(next)
            totalWeight += next
            index++
        } else {
            bridge.add(0)
        }
    }

    println(time + w)

    close()
}