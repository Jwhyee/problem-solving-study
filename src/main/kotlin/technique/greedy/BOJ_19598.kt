package technique.greedy

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 최소 회의실 개수(GOL5)
 * 시간 : 900 ms
 * 메모리 : 83884 KB
 * 링크 : https://www.acmicpc.net/problem/19598
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val N = readLine().toInt()

    // 시작, 끝 시간을 담을 Pair 리스트
    val list = mutableListOf<Pair<Int, Int>>()

    // N번 반복하면서 Pair 추가
    repeat(N) {
        val st = StringTokenizer(readLine())
        val first = st.nextToken().toInt()
        val second = st.nextToken().toInt()
        list += Pair(first, second)
    }

    // 시작 시간이 빠른 순으로 정렬
    list.sortBy { it.first }

    // 큐 PQ 생성 후 종료 시간 큰 값을 기준으로 내림차순 정렬
    val pq = PriorityQueue<Pair<Int, Int>> { a, b -> a.second - b.second }
    pq.offer(list[0])

    for (i in 1 until N) {
        // 이전 회의와 다음 회의를 가져옴
        val prev = pq.peek()
        val next = list[i]

        // 만약 이전 회의의 끝 시간이 다음 회의의 시작 시간보다 작을 경우 꺼내기
        if (prev.second <= next.first) {
            pq.poll()
        }
        // 다음 회의를 큐에 추가
        pq.offer(next)
    }

    println(pq.size)
    pq.clear()
}