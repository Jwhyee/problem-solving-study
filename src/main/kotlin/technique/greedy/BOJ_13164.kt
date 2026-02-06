package technique.greedy

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 행복 유치원(GOL5)
 * 시간 : 640 ms
 * 메모리 : 71020 KB
 * 링크 : https://www.acmicpc.net/problem/13164
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, k) = readLine().split(" ").map { it.toInt() }
    val st = StringTokenizer(readLine())
    val arr = IntArray(n) { st.nextToken().toInt() }

    // 인접한 학생끼리 키 차이 값 구하기
    for (i in 0 until n - 1) {
        arr[i] = arr[i + 1] - arr[i]
    }

    // 정렬
    arr.sort()

    // 가장 큰 k-1개를 뺀 나머지를 모두 더함
    val min = (0 until (n - 1) - (k - 1)).sumOf { i -> arr[i] }
    println(min)
}