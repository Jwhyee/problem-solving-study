package pq

import java.util.PriorityQueue
import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 컵라면(GOL2)
 * 시간 : 91768 ms
 * 메모리 : 820 KB
 * 링크 : https://www.acmicpc.net/problem/1781
 */
fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val problems = mutableListOf<Pair<Int, Int>>()

    repeat(n) {
        val (deadline, ramen) = StringTokenizer(readLine()).run {
            Pair(nextToken().toInt(), nextToken().toInt())
        }
        problems.add(deadline to ramen)
    }

    problems.sortBy { it.first }

    val pq = PriorityQueue<Int>()
    for ((deadline, ramen) in problems) {
        pq.add(ramen)
        if (pq.size > deadline) {
            pq.poll()
        }
    }

    println(pq.sum())
}