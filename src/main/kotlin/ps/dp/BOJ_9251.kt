package ps.dp

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max

/**
 * 문제 이름(난이도) : LCS(GOL5)
 * 시간 : 136 ms
 * 메모리 : 16616 KB
 * 링크 : https://www.acmicpc.net/problem/2638
 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val str1 = readLine()
    val str2 = readLine()
    val dp = Array(str1.length + 1) { IntArray(str2.length + 1) }

    for (i in 1..str1.length) {
        for (j in 1..str2.length) {
            // 같은 문자일 경우 좌측 상단 대각선에 위치한 값에 1을 더해줌
            if (str1[i - 1] == str2[j - 1])
                dp[i][j] = dp[i - 1][j - 1] + 1
            // 같지 않을 경우 이전 열, 이전 행 중 큰 값을 가져옴
            else
                dp[i][j] = max(dp[i - 1][j],(dp[i][j - 1]))
        }
    }

    println(dp[str1.length][str2.length])
}