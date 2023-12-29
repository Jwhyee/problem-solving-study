package ps.dp

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.min

/**
 * 문제 이름(난이도) : 가장 큰 정사각형(GOL4)
 * 시간 : 376 ms
 * 메모리 : 36464 KB
 * 링크 : https://www.acmicpc.net/problem/1915
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (n, m) = readLine().split(" ").map { it.toInt() }
    val dp = Array(n + 1) { IntArray(m + 1) }

    var max = 0

    // 값 넣기
    for (i in 1 .. n) {
        val arr = readLine().toCharArray().map { it.code - 48 }
        for (j in 1 .. m) {
            val value = arr[j - 1]
            if (value > 0 && max == 0) max = 1
            dp[i][j] = value
        }
    }

    // 정사각형 크기 구하기
    for (i in 1 .. n) {
        for (j in 1 .. m) {
            // 최소값 구하기
            // 현재 좌표 기준으로 왼쪽 상단 대각선에 있는 값과 (위쪽 값과 왼쪽 값의 최소값) 중 작은 수
            val min = min(dp[i - 1][j - 1], min(dp[i - 1][j], dp[i][j - 1]))

            // 최소값이 0이 아니고, 현재 좌표까지의 크기가 1일 경우
            if (min != 0 && dp[i][j] == 1) {
                // 최소값의 + 1을 해주고, 현재 좌표의 값을 최신화(사각형 크기 늘리기)
                val currentSize = min + 1
                dp[i][j] = currentSize
                // 최대값이 현재 크기보다 작을 경우 최신화
                if (max < currentSize) {
                    max = currentSize
                }
            }
        }
    }
    // 정사각형이므로 최대값의 제곱
    println(max * max)
}